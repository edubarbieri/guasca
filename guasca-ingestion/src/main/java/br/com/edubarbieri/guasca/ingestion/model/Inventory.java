package br.com.edubarbieri.guasca.ingestion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
	private boolean purchasable;
	private boolean discountinued;
	private boolean outOfStock;
	private boolean preOrder;
	private boolean preLaunch;
	private boolean omniStock;
	private boolean backOrder;
}
