package br.com.edubarbieri.guasca.ingestion.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCatalog {
    private List<Sibling> siblings;
    private String productId;
    private String displayName;
    private boolean enabled;
}
