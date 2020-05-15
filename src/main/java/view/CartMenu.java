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
                if (id.equals(BACK_BUTTON))
                    return;
                System.out.println("Please enter salesperson's username:");
                String sellerUsername = getValidSellerUsername();
                if (sellerUsername.equals(BACK_BUTTON))
                    return;
                Product product = ProductController.getInstance().searchProduct(id);
                CartController.getInstance().setProductCount(product, 1, ((Salesperson) PersonController.getInstance().getPersonByUsername (sellerUsername)));
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
                if (id.equals(BACK_BUTTON))
                    return;
                System.out.println("Please enter salesperson's username:");
                String sellerUsername = getValidSellerUsername();
                if (sellerUsername.equals(BACK_BUTTON))
                    return;
                Product product = ProductController.getInstance().searchProduct(id);
                CartController.getInstance().setProductCount(product, -1, ((Salesperson) PersonController.getInstance().getPersonByUsername (sellerUsername)));
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

    public String getValidSellerUsername() {
        String input;
        boolean check = false;
        do {
            input = scanner.nextLine();
            if (input.equals(".."))
                return input;
            if (PersonController.getInstance().isTherePersonByUsername(input) && PersonController.getInstance().checkValidPersonType(input, Salesperson.class)) {
                check = true;
            } else {
                System.out.println("the username is not valid.");
            }

        } while (!check);
        return input;
    }
}