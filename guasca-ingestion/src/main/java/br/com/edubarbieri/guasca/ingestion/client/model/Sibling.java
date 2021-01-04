package br.com.edubarbieri.guasca.ingestion.client.model;

import java.util.List;

import br.com.edubarbieri.guasca.ingestion.model.Attribute;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sibling {
    private List<Attribute> attributes;
    private String skuId;
}
