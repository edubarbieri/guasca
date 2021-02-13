package main

import (
	"catalog-ingestion/controller"

	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()
	catalogActor := r.Group("ingestion")
	{
		catalogActor.POST("/product", controller.ReceiveProduct)
	}
	r.Run(":8081") // listen and serve on 0.0.0.0:8080
}
