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
            }

            @Override
            public void execute() {
                super.execute();
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
                System.out.println("you can enter '..' to return.");
                System.out.println("Please enter product id:");
                String id = getValidProductId();
                if(id.equals(".."))
                    return;
                System.out.println("Please enter salesperson's username:");
                String sellerUsername = getValidSellerUsername();
                if (sellerUsername.equals(".."))
                    return;
                Product product = ProductController.searchProduct(id);
                CartController.getInstance().setProductCount(product, 1,((Salesperson)PersonController.findPersonByUsername(sellerUsername)));
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
                System.out.println("you can enter '..' to return.");
                System.out.println("Please enter product id:");
                String id = getValidProductId();
                if(id.equals(".."))
                    return;
                System.out.println("Please enter salesperson's username:");
                String sellerUsername = getValidSellerUsername();
                if (sellerUsername.equals(".."))
                    return;
                Product product = ProductController.searchProduct(id);
                CartController.getInstance().setProductCount(product, -1,((Salesperson)PersonController.findPersonByUsername(sellerUsername)));
            }
        };
    }

    public Menu getShowTotalPriceMenu() {
        helpMessage = "You can see total price.";
        return new Menu("Total Price", this) {
            @Override
            public void show() {
                System.out.println("Subtotal(" + CartController.getInstance().itemNumber() + " items): " + CartController.getInstance().calculateTotalPrice() + "Toman");
                System.out.println("press back to return");
            }

            @Override
            public void execute() {
                String input;
                do {
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("back")) {
                        return;
                    }
                } while (true);
            }
        };
    }

    public void showCartTable() {
        helpMessage = "shows products and their detailed information in cart";
        Cart cart = CartController.getInstance().getCart();
        String firstRowFormat = "|%-50s|%-50s|";
        String secondRowFormat = "|%-20s||%-15s|%-15s|%-31s|%-15s|";
        for (Product product : cart.getProducts().keySet()) {
            System.out.println(String.format("%s", "-------------------------------------------------------------------------------------"));
            System.out.println(String.format(firstRowFormat, "product ID", "product Name"));
            System.out.println(String.format("%s", "-------------------------------------------------------------------------------------"));
            System.out.println(String.format(firstRowFormat, product.getID(), product.getName()));
            for (Salesperson salesperson : cart.getProducts().get(product).keySet()) {
                System.out.println(String.format("%s", "-------------------------------------------------------------------------------------"));
                System.out.println(String.format(secondRowFormat,"seller","count", "product price", "price after discount", "total price"));
                System.out.println(String.format("%s", "-------------------------------------------------------------------------------------"));
                if (cart.getProducts().get(product).get(salesperson).isInDiscount())
                    System.out.println(String.format(secondRowFormat, salesperson.getUsername(),cart.getProducts().get(product).get(salesperson).getPrice(), cart.getProducts().get(product).get(salesperson).getPriceAfterDiscount(), cart.getProducts().get(product).get(salesperson).getFinalPrice()));
                else
                    System.out.println(String.format(secondRowFormat, salesperson.getUsername(),cart.getProducts().get(product).get(salesperson).getPrice(), "product is not in discount", cart.getProducts().get(product).get(salesperson).getFinalPrice()));
                System.out.println(String.format("%s", "-------------------------------------------------------------------------------------"));


            }
        }
        System.out.println(String.format("%s", "-------------------------------------------------------------------------------------"));



    }

    public String getValidSellerUsername(){
        String input;
        boolean check = false;
        do {
            input = scanner.nextLine();
            if (input.equals(".."))
                return input;
            if(PersonController.isTherePersonByUsername(input)&&PersonController.checkValidPersonType(input, Salesperson.class)){
                check = true;
            }else {
                System.out.println("the username is not valid.");
            }

        }while(!check);
        return input;
    }

}
