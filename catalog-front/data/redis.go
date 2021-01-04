package data

import (
	"context"
	"encoding/json"
	"fmt"
	"log"
	"os"

	"github.com/go-redis/redis/v8"
)

var client *redis.Client
var ctx = context.Background()

func init() {

	redisURL := os.Getenv("REDIS_URL")
	redisPwd := os.Getenv("REDIS_PASSWORD")
	log.Println("Initializing redis connection with url", redisURL)
	client = redis.NewClient(&redis.Options{
		Addr:         redisURL,
		Password:     redisPwd, // no password set
		DB:           0,        // use default DB
		PoolSize:     200,
		MinIdleConns: 20,
	})
	_, err := client.Ping(ctx).Result()
	if err != nil {
		fmt.Println("ERROR testing redis connections")
	}
}

//GetClient - Return redis client
func GetClient() *redis.Client {
	return client
}

//GetProduct Load product from redis
func GetProduct(productID string) (Product, error) {
	var product Product
	productJSON, err := GetClient().Get(ctx, productID).Result()
	if err != nil {
		return product, err
	}
	json.Unmarshal([]byte(productJSON), &product)

	return product, nil

}
