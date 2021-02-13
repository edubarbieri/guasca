package controller

import (
	"catalog-ingestion/data"
	"log"
	"net/http"

	"github.com/gin-gonic/gin"
)

//ReceiveProduct procces product data to update database
func ReceiveProduct(c *gin.Context) {
	var product data.Product
	if err := c.ShouldBindJSON(&product); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	log.Printf("Product received %s", product.ID)
	data.SaveInElasticSearch(product)
	c.Status(200)
}
