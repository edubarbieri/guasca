package br.com.edubarbieri.guasca.ingestion.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sku {
	private String id;

	private List<Attribute> attributes;

	private List<MediaSet> mediaSets;

	private Price price;

	private Inventory inventory;
	
	private boolean enabled;
}
