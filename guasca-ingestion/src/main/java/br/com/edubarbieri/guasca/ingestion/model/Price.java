package br.com.edubarbieri.guasca.ingestion.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {
	private boolean onSale;
	private BigDecimal listPrice;
	private String listPriceFormatted;
	private BigDecimal salePrice;
	private String salePriceFormatted;
	
	
	private int installment;
	private int installmentWithoutInterest;
	private String installmentValueFormatted;
	private String installmentValueWithoutInterestFormatted;
	private int percentDiscount;
	private int ccInstallment;
	private String ccInstallmentValueFormatted;
}
