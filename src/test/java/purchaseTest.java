import controller.*;
import model.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;


public class purchaseTest {

    @Test
    public void discountCodeTest(){
        Database.initializeAddress();
        HashMap<String,String> Info = new HashMap<>();
        Info.put("username","reza");
        Customer customer = new Customer(Info, null, 0);
        customer.setCredit(100000);
        PersonController.getInstance().addPerson(customer);
      //  PersonController.getInstance().login("reza");
        Product product1 = new Product("lip stick","beauty","",Info);
        Product product2 = new Product("comb","beauty","",Info);
        HashMap<String,String> info = new HashMap<>();
        info.put("username","ogy");
        Salesperson salesperson = new Salesperson(info, null, 0);
        salesperson.addToOfferedProducts(product1,5,6000);
        salesperson.addToOfferedProducts(product2,5,2000);
        customer.getCart().addProduct(product1,salesperson);
        customer.getCart().addProduct(product2,salesperson);
        customer.getCart().setProductCount(product1,salesperson,3);
        DiscountCode discountCode = DiscountCodeController.getInstance().addNewDiscountCode(LocalDateTime.MIN,LocalDateTime.MAX,10,100000,5,null);
        DiscountCodeController.getInstance().addToCustomer(discountCode,customer);
        DiscountCodeController.getInstance().editDiscountCode(discountCode,3,"50");
        CartController.getInstance().manageDiscountCode(discountCode);
        Assert.assertEquals(customer.getCart().getTotalPriceAfterDiscountCode(), 13000, 1);
    }

    @Test
    public void discountCodeMaxTest()  {
        Database.initializeAddress();
        HashMap<String,String> Info = new HashMap<>();
        Info.put("username","reza");
        Customer customer = new Customer(Info, null ,0);
        customer.setCredit(100000);
        PersonController.getInstance().addPerson(customer);
        //PersonController.getInstance().login("reza");
        Product product1 = new Product("lip stick","beauty","",Info);
        Product product2 = new Product("comb","beauty","",Info);
        HashMap<String,String> info = new HashMap<>();
        info.put("username","ogy");
        Salesperson salesperson = new Salesperson(info, null, 0);
        salesperson.addToOfferedProducts(product1,5,5000);
        salesperson.addToOfferedProducts(product2,5,2000);
        customer.getCart().addProduct(product1,salesperson);
        customer.getCart().addProduct(product2,salesperson);
        customer.getCart().setProductCount(product1,salesperson,3);
        DiscountCode discountCode = DiscountCodeController.getInstance().addNewDiscountCode(LocalDateTime.MIN,LocalDateTime.MAX,10,100,5,null);
        DiscountCodeController.getInstance().addToCustomer(discountCode,customer);
        CartController.getInstance().manageDiscountCode(discountCode);
        Assert.assertEquals(customer.getCart().getTotalPriceAfterDiscountCode(), 21900, 1);
    }

    @Test
    public void purchaseTest() throws CartController.NoLoggedInPersonException, CartController.NotEnoughCreditMoney, CartController.AccountIsNotCustomerException, IOException {
        Database.initializeAddress();
        HashMap<String,String> Info = new HashMap<>();
        Info.put("username","reza");
        Customer customer = new Customer(Info, null, 0);
        customer.setCredit(100000);
        PersonController.getInstance().addPerson(customer);
      //  PersonController.getInstance().login("reza");
        Product product1 = new Product("lip stick","beauty","",Info);
        Product product2 = new Product("comb","beauty","",Info);
        HashMap<String,String> info = new HashMap<>();
        info.put("username","ogy");
        Salesperson salesperson = new Salesperson(info, null, 0);
        salesperson.addToOfferedProducts(product1,5,5000);
        salesperson.addToOfferedProducts(product2,5,2000);
        ArrayList<Product> products = new ArrayList<>();
        Discount discount = new Discount(LocalDateTime.MIN,LocalDateTime.MAX,10,products);
        discount.setDiscountState(Discount.DiscountState.BUILD_IN_PROGRESS);
        salesperson.addToDiscounts(discount);
        new DiscountRequest(discount, salesperson, Request.RequestState.ADD);
        DiscountController.getInstance().addDiscount(salesperson,discount);
        products.add(product1);
        products.add(product2);
        DiscountController.getInstance().editDiscount(50,null,null,products,discount,salesperson);
        customer.getCart().addProduct(product1,salesperson);
        customer.getCart().addProduct(product2,salesperson);
        customer.getCart().setProductCount(product1,salesperson,3);
        CartController.getInstance().purchase();
        Assert.assertEquals(22000,salesperson.getCredit(),1);
        Assert.assertEquals(78000,customer.getCredit(),1);
    }
}
