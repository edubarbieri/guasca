package br.com.edubarbieri.guasca.ingestion.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stamp {
    private String hexCodeBg;
    private String description;
    private String stampModel;
    private String hexCodeTxt;
    private String imageUrl;
    private String id;
}
