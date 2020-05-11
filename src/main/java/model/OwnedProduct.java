package model;

public  class OwnedProduct {
        private Product product;
        private Salesperson salesperson;
        double price;

        public OwnedProduct(Product product, Salesperson salesperson, double price) {
            this.price = price;
            this.product = product;
            this.salesperson = salesperson;
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

        @Override
        public String toString() {
            return "OwnedProduct{" +
                    ", price=" + price +
                    "seller= " + salesperson.getUsername() +
                    '}';
        }
    }
