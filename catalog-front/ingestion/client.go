package ingestion

import (
	"fmt"
	"net/http"
	"time"
)

//ForceLoadProduct method call ingestion ap to force load product
func ForceLoadProduct(productID string) error {
	var client = &http.Client{
		Timeout: time.Second * 10,
	}
	request, err := http.NewRequest("PUT", fmt.Sprintf("http://localhost:8081/event/force/%s", productID), nil)
	if err != nil {
		return err
	}
	response, err := client.Do(request)
	if err != nil {
		return err
	}
	if response.StatusCode == 200 {
		return nil
	}
	return fmt.Errorf("Request return error stattus code %d", response.StatusCode)

}
