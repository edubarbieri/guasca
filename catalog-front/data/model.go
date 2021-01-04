package data

// Product definition
type Product struct {
	ID              string `json:"id"`
	DisplayName     string `json:"displayName"`
	LongDescription string `json:"longDescription"`
	Enabled         bool   `json:"enabled"`
	Skus            []Sku  `json:"skus"`
}

// Sku definition
type Sku struct {
	ID         string      `json:"id"`
	Enabled    bool        `json:"enabled"`
	Attributes []Attribute `json:"attributes"`
	MediaSets  []MediaSet  `json:"mediaSets"`
	Price      Price       `json:"price"`
}

// Attribute definition
type Attribute struct {
	Code          string   `json:"code"`
	DisplayName   string   `json:"displayName"`
	Label         string   `json:"label"`
	Priority      int      `json:"priority"`
	Enabled       bool     `json:"enabled"`
	InternalName  string   `json:"internalName"`
	HexCode       string   `json:"hexCode"`
	AttributeType string   `json:"attributeType"`
	Name          string   `json:"name"`
	AttributeName string   `json:"attributeName"`
	MediaSet      MediaSet `json:"mediaSet"`
}

//MediaSet definition
type MediaSet struct {
	SmallImageURL  string `json:"smallImageUrl"`
	ZoomImageURL   string `json:"zoomImageUrl"`
	MediumImageURL string `json:"mediumImageUrl"`
	ThumbImageURL  string `json:"thumbImageUrl"`
	LargeImageURL  string `json:"largeImageUrl"`
	ID             string `json:"id"`
}

//Price definition
type Price struct {
	OnSale          bool    `json:"onSale"`
	ListPrice       float64 `json:"listPrice"`
	SalePrice       float64 `json:"salePrice"`
	PercentDiscount int     `json:"percentDiscount"`
}

//Inventory definition
type Inventory struct {
	Purchasable   bool `json:"purchasable"`
	Discountinued bool `json:"discountinued"`
	OutOfStock    bool `json:"outOfStock"`
	PreOrder      bool `json:"preOrder"`
	PreLaunch     bool `json:"preLaunch"`
	BackOrder     bool `json:"backOrder"`
}
