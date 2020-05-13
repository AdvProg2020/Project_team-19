package view;

import controller.CartController;
import controller.ProductController;
import model.Cart;
import model.Product;

public class CartMenu extends Menu {
    public CartMenu(Menu parent) {
        super("Cart", parent);
        subMenus.put(1, getShowCartMenu());
        subMenus.put(2, getViewProductMenu());
        subMenus.put(3, getDecreaseProductMenu());
        subMenus.put(4, getIncreaseProductMenu());
        subMenus.put(5, getShowTotalPriceMenu());
        subMenus.put(6, new PurchaseMenu(this));
    }

    public Menu getShowCartMenu() {
        return new Menu("Show Cart", this) {
            @Override
            public void show() {
                showCartTable();
                super.show ();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    public Menu getViewProductMenu() {
        return new Menu("View Product", this) {
            @Override
            public void show() { }

            @Override
            public void execute() {
                System.out.println("Please enter product id:");
                System.out.println ("Or you can type .. if you want to surrender." );
                String id = getValidProductId();
                if (id.equals ( BACK_BUTTON ))
                    return;
                Product product = ProductController.searchProduct(id);
                ViewProductMenu viewProductMenu = new ViewProductMenu(this, product);
                viewProductMenu.run ();
            }
        };
    }

    public Menu getIncreaseProductMenu() {
        return new Menu("Increase Product", this) {
            @Override
            public void show() { }

            @Override
            public void execute() {
                System.out.println("Please enter product id:");
                System.out.println ("Or you can type .. if you want to surrender." );
                String id = getValidProductId();
                if (id.equals ( BACK_BUTTON ))
                    return;
                Product product = ProductController.searchProduct(id);
                CartController.getInstance().setProductCount(product, 1);
            }
        };
    }

    public Menu getDecreaseProductMenu() {
        return new Menu("Decrease Product", this) {
            @Override
            public void show() { }

            @Override
            public void execute() {
                System.out.println("Please enter product id:");
                System.out.println ("Or you can type .. if you want to surrender." );
                String id = getValidProductId();
                if (id.equals ( BACK_BUTTON ))
                    return;
                Product product = ProductController.searchProduct(id);
                CartController.getInstance().setProductCount(product, 1);
            }
        };
    }

    public Menu getShowTotalPriceMenu() {
        return new Menu("Total Price", this) {
            @Override
            public void show() {
                System.out.println("Subtotal(" + CartController.getInstance().itemNumber() + " items): " + CartController.getInstance().calculateTotalPrice() + "Toman");
                super.show ();
            }

            @Override
            public void execute() {
                super.execute ();
            }
        };
    }

    public void showCartTable() {
        Cart cart = CartController.getInstance().getCart();
        String firstRowFormat = "|%-20s|%-20s|%-20s|%-20s|";
        String secondRowFormat = "|%-20s|%-20s|%-40s|";
        for (Product product : cart.getProducts().keySet()) {
            System.out.println(String.format("%s", "----------------------------------------------------------------------------"));
            System.out.println(String.format(firstRowFormat, "product ID", "product Name", "seller", "count"));
            System.out.println(String.format("%s", "----------------------------------------------------------------------------"));
            System.out.println(String.format(firstRowFormat, product.getID(), product.getName(), cart.getProducts().get(product).getSalesperson().getUsername(), cart.getProducts().get(product).getCount()));
            System.out.println(String.format("%s", "----------------------------------------------------------------------------"));
            System.out.println(String.format(secondRowFormat, "product price", "product price after discount", "total price"));
            System.out.println(String.format("%s", "----------------------------------------------------------------------------"));
            if (cart.getProducts().get(product).isInDiscount())
                System.out.println(String.format(secondRowFormat, cart.getProducts().get(product).getPrice(), cart.getProducts().get(product).getPriceAfterDiscount(), cart.getProducts().get(product).getFinalPrice()));
            else
                System.out.println(String.format(secondRowFormat, cart.getProducts().get(product).getPrice(), "this product is not in discount", cart.getProducts().get(product).getFinalPrice()));
        }
        System.out.println(String.format("%s", "----------------------------------------------------------------------------"));



    }

}
