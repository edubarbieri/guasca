package data

import (
	"context"
	"encoding/json"
	"fmt"
	"log"
	"strings"

	"github.com/elastic/go-elasticsearch/v7"
	"github.com/elastic/go-elasticsearch/v7/esapi"
)

//SaveInElasticSearch save product data in elastic Search
func SaveInElasticSearch(p Product) error {
	es, err := elasticsearch.NewDefaultClient()
	if err != nil {
		log.Fatalf("Error creating the client: %s", err)
	}
	if err := testConnection(es); err != nil {
		return err
	}
	if err := indexJSONProduct(p, es); err != nil {
		return err
	}

	return nil
}

func testConnection(es *elasticsearch.Client) error {
	_, err := es.Info()
	if err != nil {
		log.Fatalf("Error getting response: %s", err)
	}
	return err
}

func indexJSONProduct(product Product, es *elasticsearch.Client) error {
	productJSON, err := json.Marshal(product)
	if err != nil {
		log.Fatalf("Error marshall json: %s", err)
		return err
	}
	ctx := context.Background()
	idxRequest := esapi.IndexRequest{
		Index:      "products",
		DocumentID: product.ID,
		Body:       strings.NewReader(string(productJSON)),
	}

	idxResp, err3 := idxRequest.Do(ctx, es)

	if err3 != nil {
		log.Fatalf("IndexRequest ERROR: %s", err3)
	}
	defer idxResp.Body.Close()
	if idxResp.IsError() {
		log.Printf("%s ERROR indexing product ID=%s", idxResp.Status(), product.ID)
		return fmt.Errorf("%s ERROR indexing product ID=%s", idxResp.Status(), product.ID)
	}
	// Deserialize the response into a map.
	var resMap map[string]interface{}
	if err := json.NewDecoder(idxResp.Body).Decode(&resMap); err != nil {
		log.Printf("Error parsing the response body: %s", err)
	} else {
		log.Println("IndexRequest() RESPONSE:", idxResp.Status())
	}
	return nil
}
