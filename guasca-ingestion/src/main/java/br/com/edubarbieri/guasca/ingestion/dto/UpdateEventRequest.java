package br.com.edubarbieri.guasca.ingestion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEventRequest {
	private String type;
	private String productId;
	private String skuId;
}
