package controller

//MediaSet response type
type MediaSet struct {
	SmallImageURL  string `json:"smallImageUrl"`
	ZoomImageURL   string `json:"zoomImageUrl"`
	MediumImageURL string `json:"mediumImageUrl"`
	ThumbImageURL  string `json:"thumbImageUrl"`
	LargeImageURL  string `json:"largeImageUrl"`
	ID             string `json:"id"`
}

//Stamp response type
type Stamp struct {
	HexCodeBg   *string `json:"hexCodeBg"`
	Description *string `json:"description"`
	StampModel  *string `json:"stampModel"`
	HexCodeTxt  *string `json:"hexCodeTxt"`
	ImageURL    *string `json:"imageUrl"`
	ID          *string `json:"id"`
}

//ProductBoxDataDeskResp response type
type ProductBoxDataDeskResp struct {
	MediaSet                        []MediaSet `json:"mediaSet"`
	Purchasable                     bool       `json:"purchasable"`
	DisplayName                     string     `json:"displayName"`
	SalePriceFormatted              string     `json:"salePriceFormatted"`
	AttrVlrID                       string     `json:"attrVlrId"`
	Variants                        string     `json:"variants"`
	OmniStock                       bool       `json:"omniStock"`
	BackOrder                       bool       `json:"backOrder"`
	OutOfStock                      bool       `json:"outOfStock"`
	PreOrder                        bool       `json:"preOrder"`
	OnSale                          bool       `json:"onSale"`
	Selector                        string     `json:"selector"`
	InstallmentValueFormatted       string     `json:"installmentValueFormatted"`
	SkuID                           string     `json:"skuId"`
	InstallmentValue                float64    `json:"installmentValue"`
	PercentDiscount                 int        `json:"percentDiscount"`
	ProductID                       string     `json:"productId"`
	InstallmentValueWithoutInterest float64    `json:"installmentValueWithoutInterest"`
	CcInstallment                   int        `json:"ccInstallment"`
	Discountinued                   bool       `json:"discountinued"`
	ListPriceFormatted              string     `json:"listPriceFormatted"`
	Installment                     int        `json:"installment"`
	CcInstallmentValueFormatted     string     `json:"ccInstallmentValueFormatted"`
	InstallmentWithoutInterest      int        `json:"installmentWithoutInterest"`
	CcInstallmentValue              float64    `json:"ccInstallmentValue"`
	ListPrice                       float64    `json:"listPrice"`
	PreLaunch                       bool       `json:"preLaunch"`
}
