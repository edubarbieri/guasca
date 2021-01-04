package br.com.edubarbieri.guasca.ingestion.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	private String id;
	
	private String displayName;

	private String longDescription;
	
	private boolean enabled;
	
	private List<Sku> skus;
	
	public void addSku(Sku pSku) {
		if(this.skus == null) {
			this.skus = new ArrayList<>();
		}
		this.skus.add(pSku);
	}
}
