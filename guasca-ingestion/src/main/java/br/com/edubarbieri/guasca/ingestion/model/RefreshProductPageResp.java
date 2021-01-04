package br.com.edubarbieri.guasca.ingestion.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefreshProductPageResp {
	private List<MediaSet> mediaSets;
    private boolean purchasable;
    private boolean midiaFromSku;
    private String salePriceFormatted;
    private String description;
    private String variants;
    private int maxQuantityItem;
    private boolean omniStock;
    private boolean backOrder;
    private boolean outOfStock;
    private boolean onSale;
    private boolean preOrder;
    private String installmentValueFormatted;
    private List<Stamp> stamps;
    private String skuId;
    private int percentDiscount;
    private List<Attribute> productAttributes;
    private int ccInstallment;
    private String installmentWithoutInterestValueFormatted;
    private boolean discountinued;
    private List<Attribute> skuAttributes;
    private String listPriceFormatted;
    private int installment;
    private String ccInstallmentValueFormatted;
    private int installmentWithoutInterest;
    private BigDecimal listPrice;
    private BigDecimal ccInstallmentValue;
    private BigDecimal installmentValue;
    private boolean preLaunch;
    private BigDecimal installmentValueWithoutInterest;
    private BigDecimal salePrice;
}
