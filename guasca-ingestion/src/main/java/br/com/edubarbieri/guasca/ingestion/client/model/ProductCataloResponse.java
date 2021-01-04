package br.com.edubarbieri.guasca.ingestion.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCataloResponse {
	private ProductCatalog data;
}
