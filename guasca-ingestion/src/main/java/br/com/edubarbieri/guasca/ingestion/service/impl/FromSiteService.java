package br.com.edubarbieri.guasca.ingestion.service.impl;

import java.util.Optional;

import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.edubarbieri.guasca.ingestion.client.model.ProductCataloResponse;
import br.com.edubarbieri.guasca.ingestion.client.model.ProductCatalog;
import br.com.edubarbieri.guasca.ingestion.client.model.Sibling;
import br.com.edubarbieri.guasca.ingestion.model.Inventory;
import br.com.edubarbieri.guasca.ingestion.model.Price;
import br.com.edubarbieri.guasca.ingestion.model.Product;
import br.com.edubarbieri.guasca.ingestion.model.RefreshProductPageResp;
import br.com.edubarbieri.guasca.ingestion.model.Sku;
import br.com.edubarbieri.guasca.ingestion.service.ForceUpdateService;
import br.com.edubarbieri.guasca.ingestion.service.Status;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FromSiteService implements ForceUpdateService {

	private final ReactiveRedisConnectionFactory redisFactory;
	private final ReactiveRedisOperations<String, Product> productRedisOperation;

	public FromSiteService(ReactiveRedisConnectionFactory redisFactory,
			ReactiveRedisOperations<String, Product> productRedisOperation) {
		this.redisFactory = redisFactory;
		this.productRedisOperation = productRedisOperation;
	}

	@Override
	public Status forceUpdate(String productId) {
		Optional<ProductCatalog> resp = getProduct(productId);
		if (resp.isEmpty()) {
			return Status.NOT_FOUND;
		}

		Product product = new Product();
		product.setId(productId);
		product.setDisplayName(resp.get().getDisplayName());
		product.setEnabled(resp.get().isEnabled());

		if (resp.get().getSiblings() != null) {
			for (Sibling s : resp.get().getSiblings()) {
				product.addSku(fillSku(productId, s));
			}
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			String temp = mapper.writeValueAsString(product);
			System.out.println(temp);
		} catch (JsonProcessingException e) {
			log.error("error parsing json", e);
		}
		
		
		productRedisOperation
			.opsForValue()
			.set(productId, product)
			.subscribe();

		return Status.SUCCESS;
	}

	public Sku fillSku(String productId, Sibling sbl) {
		Sku sku = new Sku();
		sku.setId(sbl.getSkuId());
		sku.setAttributes(sbl.getAttributes());

		Optional<RefreshProductPageResp> skuDataOpt = refreshProductPage(productId, sbl.getSkuId());
		if (skuDataOpt.isEmpty()) {
			return sku;
		}
		RefreshProductPageResp skuData = skuDataOpt.get();
		sku.setMediaSets(skuData.getMediaSets());

		Price price = new Price();
		price.setCcInstallment(skuData.getCcInstallment());
		price.setCcInstallmentValueFormatted(skuData.getCcInstallmentValueFormatted());
		price.setInstallment(skuData.getInstallment());
		price.setInstallmentValueFormatted(skuData.getInstallmentValueFormatted());
		price.setInstallmentValueWithoutInterestFormatted(skuData.getInstallmentWithoutInterestValueFormatted());
		price.setInstallmentWithoutInterest(skuData.getInstallmentWithoutInterest());
		price.setListPrice(skuData.getListPrice());
		price.setOnSale(skuData.isOnSale());
		price.setPercentDiscount(skuData.getPercentDiscount());
		price.setSalePrice(skuData.getSalePrice());
		sku.setPrice(price);

		Inventory inventory = new Inventory();
		inventory.setBackOrder(skuData.isBackOrder());
		inventory.setDiscountinued(skuData.isDiscountinued());
		inventory.setOmniStock(skuData.isOmniStock());
		inventory.setOutOfStock(skuData.isOutOfStock());
		inventory.setPreLaunch(skuData.isPreLaunch());
		inventory.setPurchasable(skuData.isPurchasable());

		sku.setInventory(inventory);

		return sku;
	}

	private Optional<ProductCatalog> getProduct(String productId) {
		try {
			String url = "https://www.lojasrenner.com.br/rest/model/lrsa/api/CatalogActor/product?productId=";
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth("38ba05f2-a547-47ec-b960-452547766415");
			HttpEntity<?> entity = new HttpEntity<>(headers);

			ResponseEntity<ProductCataloResponse> response = restTemplate.exchange(url + productId, HttpMethod.GET,
					entity, ProductCataloResponse.class);

			if (response.getBody() == null || response.getBody().getData() == null) {
				return Optional.empty();
			}

			return Optional.of(response.getBody().getData());
		} catch (RestClientException restError) {
			log.error("error getProduct", restError);
		}
		return Optional.empty();
	}

	private Optional<RefreshProductPageResp> refreshProductPage(String productId, String skuId) {
		try {
			String url = "https://www.lojasrenner.com.br/rest/model/lrsa/api/CatalogActor/refreshProductPage?pushSite=rennerBrasilDesktop&skuId="
					+ skuId + "&productId=" + productId + "&giftJson=%5B%5D&useInventoryCache=false";
			RestTemplate restTemplate = new RestTemplate();
			RefreshProductPageResp resp = restTemplate.getForObject(url, RefreshProductPageResp.class);
			return Optional.of(resp);
		} catch (RestClientException restError) {
			log.error("error getting product data", restError);
		}
		return Optional.empty();
	}

}
