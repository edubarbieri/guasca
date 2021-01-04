package br.com.edubarbieri.guasca.ingestion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaSet {
    private String smallImageUrl;
    private String zoomImageUrl;
    private String mediumImageUrl;
    private String thumbImageUrl;
    private String largeImageUrl;
    private String id;
}
