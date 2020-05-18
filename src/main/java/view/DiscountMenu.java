package view;

import controller.DiscountController;
import controller.ProductController;
import model.Discount;
import model.OwnedProduct;
import model.Product;
import model.wagu.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiscountMenu extends Menu {
    public DiscountMenu(Menu parent) {
        super("Discount Menu", parent);
        subMenus.put ( 1 , getDiscountMenu() );
        subMenus.put ( 2 , getShowProductMenu() ); //ToDo fk knm zdin shma
    }

    public Menu getDiscountMenu() {
        return new Menu ( "Offs",this ) {
            @Override
            public void show () {
                for (Discount discount : DiscountController.getAllDiscounts ()) {
                    List<String> headersList = Arrays.asList("Product ID", "Name" , "Price Before Off", "Price After Off");
                    List < List <String>> rowsList = new ArrayList <> (  );
                    for (Product product : discount.getProducts ( )) {
                        for (OwnedProduct ownedProduct : ProductController.getInstance ().getProductsOfProduct(product)) {
                            if (product.equals ( ownedProduct.getProduct () )) {
                                List <String> row = new ArrayList <> ( 4 );
                                row.add ( product.getID () );
                                row.add ( product.getName () );
                                row.add ( String.valueOf ( ownedProduct.getPrice () ) );
                                row.add ( String.valueOf ( discount.getPriceAfterDiscount ( ownedProduct.getPrice () ) ) );
                                rowsList.add ( row );
                                break;
                            }
                        }
                    }
                    Board board = new Board (75);
                    Table table = new Table (board, 75, headersList, rowsList);
                    List<Integer> colAlignList = Arrays.asList(
                            Block.DATA_CENTER,
                            Block.DATA_CENTER,
                            Block.DATA_CENTER,
                            Block.DATA_CENTER);
                    table.setColAlignsList(colAlignList);
                    Block tableBlock = table.tableToBlocks();
                    board.setInitialBlock(tableBlock);
                    board.build();
                    String tableString = board.getPreview();
                    System.out.println(tableString);
                }
            }

            @Override
            public void execute () {

            }
        };
    }

    public Menu getShowProductMenu() {
        return new Menu ( "Show Product Menu" , this ) {
            @Override
            public void show () {
                System.out.println ( "Enter Product ID To See The Product Menu : " );
            }

            @Override
            public void execute () {
                String productId = DiscountMenu.this.getValidProductId ();
                if (productId.equals ( BACK_BUTTON ))
                    return;
                ViewProductMenu viewProductMenu = new ViewProductMenu ( this );
                viewProductMenu.setProduct ( ProductController.getInstance().getProductById ( productId ) );
                viewProductMenu.run ();
            }
        };
    }



//    public void showProductDigest() {
//        String firstTableFormat = "|%-36s|%-38s|";
//        String sellersTableFormat = "|%-30s|%-30s|%-30s|";
//        System.out.println(String.format("%s", LINE));
//        System.out.println(String.format(firstTableFormat, "product ID", "product Name"));
//        System.out.println(String.format("%s", LINE));
//        System.out.println(String.format(firstTableFormat, product.getID(), product.getName()));
//        System.out.println(String.format("%s", LINE));
//        System.out.println(String.format(firstTableFormat, "product average score", product.getAverageScore()));
//        System.out.println(String.format("%s", LINE));
//        System.out.println(String.format(firstTableFormat, product.getID(), product.getName()));
//        System.out.println(String.format("%s", LINE));
//        System.out.println(String.format(firstTableFormat, "property", "value"));
//        for (String s : product.getProperties().keySet()) {
//            System.out.println(String.format("%s", LINE));
//            System.out.println(String.format(firstTableFormat, s, product.getProperties().get(s)));
//        }
//        System.out.println(String.format("%s", LINE));
//        System.out.println();
//        System.out.println("all salesperson");
//        for (OwnedProduct ownedProduct : ProductController.getProductsOfProduct(product)) {
//            System.out.println(String.format("%s", LINE));
//            if (ownedProduct.getSalesperson().isInDiscount(product)) {
//                System.out.println(String.format("%s %s %20s %s %20s %s %20s", "|", ownedProduct.getSellerName(), "|", ownedProduct.getPrice(), "|", ownedProduct.getSalesperson().getDiscountPrice(product), "|"));
//
//            } else
//                System.out.println(String.format("%s %s %20s %s %20s %s %20s", "|", ownedProduct.getSellerName(), "|", ownedProduct.getPrice(), "|", "", "|"));
//        }
//        System.out.println(String.format("%s", LINE));
//
//    }

//    public void showCartTable() {
//        helpMessage = "shows products and their detailed information in cart";
//        Cart cart = CartController.getInstance().getCart();
//        String firstRowFormat = "|%-27s|%-27s|";
//        String onFormat = "|%-55s|";
//        for (Product product : cart.getProducts().keySet()) {
//            System.out.println(String.format("%s",LINE));
//            System.out.println(String.format(firstRowFormat, "product ID: "+product.getID(), "product Name: "+product.getName()));
//            System.out.println(String.format("%s",LINE));
//            System.out.println(String.format(onFormat,"Order Details"));
//            System.out.println(String.format("%s",STRAIGHT_LINE));
//            for (Salesperson salesperson : cart.getProducts().get(product).keySet()) {
//                System.out.println(String.format(onFormat, "seller: " + salesperson.getUsername()));
//                System.out.println(String.format(onFormat, "count: "+cart.getProducts().get(product).get(salesperson).getCount()));
//                System.out.println(String.format(onFormat, "product price: "+cart.getProducts().get(product).get(salesperson).getPrice()));
//                if (cart.getProducts().get(product).get(salesperson).isInDiscount())
//                    System.out.println(String.format(onFormat, "price after discount"+cart.getProducts().get(product).get(salesperson).getPriceAfterDiscount()));
//                System.out.println(String.format(onFormat,"total price:"+cart.getProducts().get(product).get(salesperson).getFinalPrice()));
//                System.out.println(String.format("%s",STRAIGHT_LINE));
//            }
//            System.out.println("\n");
//        }
//    }

}
