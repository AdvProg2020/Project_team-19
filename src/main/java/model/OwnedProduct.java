package model;

public  class OwnedProduct {
    private Product product;
    private Salesperson salesperson;
    double price;
    double priceAfterDiscount;
    int count;
    boolean inDiscount;

    public OwnedProduct(Product product, Salesperson salesperson, double price) {
        this.price = price;
        this.product = product;
        this.salesperson = salesperson;
    }

    public OwnedProduct(ProductStateInCart productStateInCart,Product product){
        price = productStateInCart.getPrice();
        this.product = product;
        salesperson = productStateInCart.salesperson;
        inDiscount = productStateInCart.inDiscount;
        if(productStateInCart.isInDiscount()){
            priceAfterDiscount = productStateInCart.getPriceAfterDiscount();}
        count = productStateInCart.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPriceAfterDiscount(double priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public double getPrice() {
        return price;
    }

    public Product getProduct() {
        return product;
    }

    public Salesperson getSalesperson() {
        return salesperson;
    }

    public String getSellerName() {
        return salesperson.getUsername();
    }

    public double getPriceForShow() {
        return inDiscount ? priceAfterDiscount : price;
    }

    public boolean getInDiscount() {
        return inDiscount;
    }

    @Override
    public String toString() {
        return "OwnedProduct{" +
                ", price=" + price +
                "seller= " + salesperson.getUsername() +
                '}';
    }
}