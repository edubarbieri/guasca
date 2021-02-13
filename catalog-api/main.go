package main

import (
	"catalog-api/controller"
	"context"

	"github.com/gin-gonic/gin"
)

var ctx = context.Background()

func main() {
	r := gin.Default()
	r.GET("/ping", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "pong",
		})
	})

	catalogActor := r.Group("rest/model/lrsa/api/CatalogActor")
	{
		catalogActor.GET("/productBoxDataDesk", controller.ProductBoxDataDesk)
	}
	r.Run() // listen and serve on 0.0.0.0:8080
}
