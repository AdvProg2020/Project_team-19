package view;

import controller.CartController;
import controller.PersonController;
import controller.ProductController;
import model.Cart;
import model.Product;
import model.Salesperson;

public class CartMenu extends Menu {
    public CartMenu(Menu parent) {
        super("Cart", parent);
        subMenus.put(1, getShowCartMenu());
        subMenus.put(2, new ViewProductMenu(this));
        subMenus.put(3, getDecreaseProductMenu());
        subMenus.put(4, getIncreaseProductMenu());
        subMenus.put(5, getShowTotalPriceMenu());
        subMenus.put(6, new PurchaseMenu(this));
    }

    public Menu getShowCartMenu() {
        helpMessage = "This shows products in cart and their information in cart.";
        return new Menu("Show Cart", this) {
            @Override
            public void show() {
                showCartTable();
                System.out.println("1. Back");
            }

            @Override
            public void execute() {
                getValidMenuNumber(1,1);
            }
        };
    }

    public Menu getIncreaseProductMenu() {
        return new Menu("Increase Product", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                String sellerUsername;
                Product product;
                System.out.println("you can enter '..' to return.");
                System.out.println("Please enter product id:");
                do {
                    String id = getValidProductId();
                    if (id.equals(BACK_BUTTON))
                        return;
                    product = ProductController.getInstance().getProductById(id);
                    if(!CartController.getInstance().getCart(null).getProducts().containsKey(product))
                        System.out.println("You do not have such product in your cart.");
                    System.out.println("Please enter salesperson's username:");
                    sellerUsername = getValidSellerUsername(product);
                    if (sellerUsername.equals(BACK_BUTTON))
                        return;
                    if(ProductController.getInstance().isProductAvailable(product,(Salesperson)PersonController.getInstance().getPersonByUsername(sellerUsername)));
                }while (!CartController.getInstance().getCart(null).getProducts().containsKey(product));
                //CartController.getInstance().setProductCount(product, 1, ((Salesperson) PersonController.getInstance().getPersonByUsername (sellerUsername)));
                System.out.println("Product with id "+product.getID()+" is increased to "+CartController.getInstance().getCart(null).getProducts().get(product).get((PersonController.getInstance().getPersonByUsername (sellerUsername))).getCount());
            }
        };
    }

    public Menu getDecreaseProductMenu() {
        return new Menu("Decrease Product", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                String sellerUsername = null;
                Product product;
                System.out.println("you can enter '..' to return.");
                System.out.println("Please enter product id:");
//                do {
//                    String id = getValidProductId();
//                    if (id.equals(BACK_BUTTON))
//                        return;
//                    product = ProductController.getInstance().getProductById(id);
//                    if(!CartController.getInstance().getCart().getProducts().containsKey(product)) {
//                        System.out.println("You do not have such product in your cart.");
//                        continue;
//                    }
//                    System.out.println("Please enter salesperson's username:");
//                    sellerUsername = getValidSellerUsername(product);
//                    if (sellerUsername.equals(BACK_BUTTON))
//                        return;
//                }while (!CartController.getInstance().getCart().getProducts().containsKey(product));
//                CartController.getInstance().setProductCount(product, -1, ((Salesperson) PersonController.getInstance().getPersonByUsername (sellerUsername)));
//                if((PersonController.getInstance().getPersonByUsername (sellerUsername))!= null)
//                System.out.println("Product with id "+product.getID()+" is decreased to "+CartController.getInstance().getCart().getProducts().get(product).get((PersonController.getInstance().getPersonByUsername (sellerUsername))).getCount());
            }
        };
    }

    public Menu getShowTotalPriceMenu() {
        helpMessage = "You can see total price.";
        return new Menu("Total Price", this) {
            @Override
            public void show() {
              //  System.out.println("Subtotal(" + CartController.getInstance().itemNumber() + " items): " + CartController.getInstance().calculateTotalPrice() + "Toman");
                System.out.println("press \"..\" to return");
            }

            @Override
            public void execute() {
                String input;
                do {
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase(BACK_BUTTON)) {
                        return;
                    }
                } while (true);
            }
        };
    }

    public void showCartTable() {
        helpMessage = "shows products and their detailed information in cart";
        Cart cart = null;// CartController.getInstance().getCart();
        String firstRowFormat = "|%-27s|%-27s|";
        String onFormat = "|%-55s|";
        for (Product product : cart.getProducts().keySet()) {
            System.out.println(String.format("%s", LINE));
            System.out.println(String.format(firstRowFormat, "product ID: " + product.getID(), "product Name: " + product.getName()));
            System.out.println(String.format("%s", LINE));
            System.out.println(String.format(onFormat, "Order Details"));
            System.out.println(String.format("%s", STRAIGHT_LINE));
            for (Salesperson salesperson : cart.getProducts().get(product).keySet()) {
                System.out.println(String.format(onFormat, "seller: " + salesperson.getUsername()));
                System.out.println(String.format(onFormat, "count: " + cart.getProducts().get(product).get(salesperson).getCount()));
                System.out.println(String.format(onFormat, "product price: " + cart.getProducts().get(product).get(salesperson).getPrice()));
                if (cart.getProducts().get(product).get(salesperson).isInDiscount())
                    System.out.println(String.format(onFormat, "price after discount" + cart.getProducts().get(product).get(salesperson).getPriceAfterDiscount()));
                System.out.println(String.format(onFormat, "total price:" + cart.getProducts().get(product).get(salesperson).getFinalPrice()));
                System.out.println(String.format("%s", STRAIGHT_LINE));
            }
            System.out.println("\n");
        }
    }

    public String getValidSellerUsername(Product product) {
        String input;
        boolean check = false;
        do {
            input = scanner.nextLine();
            if (input.equals(".."))
                return input;
            if (!(PersonController.getInstance().isTherePersonByUsername(input) && PersonController.getInstance().checkValidPersonType(input, Salesperson.class))) {
                System.out.println("the username is not valid or is not salesperson.");
            } else if (!ProductController.getInstance().doesSellerHasProduct(product,(Salesperson)PersonController.getInstance().getPersonByUsername(input))){
                System.out.println("this product does not have such seller.");
            }else {
                check= true;
            }

        } while (!check);
        return input;
    }
}