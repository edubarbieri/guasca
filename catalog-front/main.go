package main

import (
	"context"
	"net/http"

	"catalog-front/data"

	"github.com/gin-gonic/gin"
	"github.com/prometheus/common/log"
)

var ctx = context.Background()

func main() {
	r := gin.Default()
	r.GET("/ping", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "pong",
		})
	})
	r.GET("/product/:id", func(c *gin.Context) {
		productID := c.Param("id")
		product, err := data.GetProduct(productID)
		if err != nil {
			log.Error(err)
			c.String(http.StatusInternalServerError, err.Error())
			return
		}
		c.JSON(200, product)
	})
	r.Run() // listen and serve on 0.0.0.0:8080
}
