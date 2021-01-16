package controller

import (
	"fmt"
	"log"
	"net/http"
	"strings"

	"github.com/gin-gonic/gin"
	"github.com/go-redis/redis/v8"

	"catalog-front/data"
	"catalog-front/ingestion"

	"github.com/thoas/go-funk"
)

//ProductBoxDataDesk handler method
func ProductBoxDataDesk(c *gin.Context) {

	productID := c.Query("productId")
	skuID := c.Query("skuId")
	skuList := c.Query("skuList")

	product, err := data.GetProduct(productID)
	if err == redis.Nil {
		log.Printf("not found product with id %s in redis", productID)
		err2 := ingestion.ForceLoadProduct(productID)
		if err2 != nil {
			log.Printf("Error forcing load product: %v", err2)
		}
		c.JSON(404, gin.H{})
		return
	} else if err != nil {
		log.Printf("Error getting product from redis %v", err)
		c.String(http.StatusInternalServerError, err.Error())
		return
	}
	sku := getSku(product, skuID, skuList)
	resp := ProductBoxDataDeskResp{
		ProductID:          productID,
		SkuID:              sku.ID,
		Selector:           productID + "-" + skuID,
		DisplayName:        product.DisplayName,
		Purchasable:        sku.Inventory.Purchasable,
		ListPriceFormatted: formattCurrency(sku.Price.ListPrice),
		SalePriceFormatted: formattCurrency(sku.Price.SalePrice),
		OnSale:             sku.Price.OnSale,
		OmniStock:          sku.Inventory.OmniStock,
		BackOrder:          sku.Inventory.BackOrder,
		OutOfStock:         sku.Inventory.OutOfStock,
		PreOrder:           sku.Inventory.PreOrder,
		Discountinued:      sku.Inventory.Discountinued,
		PreLaunch:          sku.Inventory.PreLaunch,
	}
	for _, attr := range sku.Attributes {
		if attr.AttributeType == "color" {
			resp.AttrVlrID = attr.Code
		}
		if resp.Variants == "" {
			resp.Variants = attr.Name
		} else {
			resp.Variants = resp.Variants + "|" + attr.Name
		}
	}
	for _, media := range sku.MediaSets {
		resp.MediaSet = append(resp.MediaSet, MediaSet{
			SmallImageURL:  media.SmallImageURL,
			ZoomImageURL:   media.ZoomImageURL,
			MediumImageURL: media.MediumImageURL,
			ThumbImageURL:  media.ThumbImageURL,
			LargeImageURL:  media.LargeImageURL,
			ID:             media.ID,
		})
	}

	c.JSON(200, resp)
}

func formattCurrency(value float64) string {
	return "R$ " + fmt.Sprintf("%.2f", value)
}

func getSku(pData data.Product, pSkuID string, pSkuList string) data.Sku {
	//valida se a sku da url esta diponivel
	var best data.Sku
	if len(pSkuID) > 0 {
		for _, sku := range pData.Skus {
			if sku.ID == pSkuID {
				if sku.Inventory.Purchasable {
					return sku
				}
				best = sku
				break
			}
		}
	}
	var skuList []string
	var skus []data.Sku
	if len(pSkuList) != 0 {
		skuList = strings.Split(pSkuList, ",")
		skus = filterSkus(pData.Skus, skuList)
	} else {
		skus = pData.Skus
	}
	for _, sku := range skus {
		if best.IsEmpty() {
			best = sku
		}
		if sku.ID == pSkuID && sku.Inventory.Purchasable {
			return sku
		}
	}

	return best
}

func filterSkus(pSkus []data.Sku, pSkuList []string) []data.Sku {
	results := []data.Sku{}
	for _, sku := range pSkus {
		if funk.ContainsString(pSkuList, sku.ID) {
			results = append(results, sku)
		}
	}
	return results
}
