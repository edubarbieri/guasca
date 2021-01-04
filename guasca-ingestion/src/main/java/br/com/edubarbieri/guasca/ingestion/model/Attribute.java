package br.com.edubarbieri.guasca.ingestion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attribute {
	private String code;
    private String displayName;
    private String label;
    private int priority;
    private boolean enabled;
    private MediaSet mediaSet;
    private String internalName;
    private String hexCode;
    private String attributeType;
    private String name;
    private String attributeName;
}
