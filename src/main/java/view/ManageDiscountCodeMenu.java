package view;

import controller.DiscountCodeController;
import controller.PersonController;
import controller.ProductController;
import model.Customer;
import model.DiscountCode;
import model.Person;
import model.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ManageDiscountCodeMenu extends Menu {

    public ManageDiscountCodeMenu(Menu parent) {
        super("Discount Codes",parent);
        subMenus.put(1,getAddDiscountCode());
        subMenus.put(2,getViewDiscountCode());
        subMenus.put(3,getEditDiscountCode());
        subMenus.put(4,getRemoveDiscountCode());
    }

    @Override
    public void show() {
        showAllDiscountCodesTable();
        super.show();
    }

    public Menu getViewDiscountCode(){
        return new Menu("view discount code",this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                System.out.println("Enter discount code: ");
                String input = getValidDiscountCode();
                if(input.equals(".."))
                    return;
                viewDiscountCode(DiscountCodeController.getInstance().findDiscountCodeByCode(input));
            }
        };
    }

    public Menu getAddDiscountCode() {
        return new Menu("Add Discount Code", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                LocalDateTime start;
                LocalDateTime end;
                Double percentage;
                int counter;
                double max;
                ArrayList<Person> customers = new ArrayList<>();
                String input;
                String choice;
                System.out.println("1. Start Time:");
                input = getValidDateTime();
                if (input.equals(BACK_BUTTON))
                    return;
                start = DiscountCodeController.getInstance().changeStringTDataTime(input);
                System.out.println("2. End Time:");
                input = getValidDateTime();
                if (input.equals(BACK_BUTTON))
                    return;
                end = DiscountCodeController.getInstance().changeStringTDataTime(input);
                System.out.println("3. Discount Percentage:");
                input = getValidDouble(100);
                if (input.equals(BACK_BUTTON))
                    return;
                percentage = Double.parseDouble(input);
                System.out.println("4. Maximum Discount Amount:");
                input = getValidDouble(Double.POSITIVE_INFINITY);
                if (input.equals(BACK_BUTTON))
                    return;
                max = Double.parseDouble(input);
                System.out.println("5. Usage Counter:");
                input = getValidMenuNumber(1,Integer.MAX_VALUE);
                if (input.equals(BACK_BUTTON))
                    return;
                counter = Integer.parseInt(input);
                System.out.println("5. Give To Customer:");
                System.out.println("Enter \"..\" to continue.");
                while ( true ) {
                    input = getValidCustomer();
                    if (input.equals ( BACK_BUTTON ))
                        break;
                    customers.add(PersonController.getInstance().getPersonByUsername(input));
                }
                DiscountCodeController.getInstance().addNewDiscountCode(start,end,percentage,max,counter,customers);
                System.out.println("Successful.");
            }
        };
    }

    public Menu getEditDiscountCode(){
        return new Menu("edit discount code",this) {
            @Override
            public void show() {
                System.out.println ( "1. Start Time" );
                System.out.println ( "2. End Time" );
                System.out.println ( "3. Discount Percentage" );
                System.out.println ( "4. Maximum Discount Amount" );
                System.out.println ( "5. Usage Counter" );
                System.out.println("6. Give To Customer");
                System.out.println("7. ~6");
                System.out.println ( "8. Exit" );
            }

            @Override
            public void execute() {
                String choice;
                System.out.println("Enter discount code: ");
                String input = getValidDiscountCode();
                DiscountCode discountCode = DiscountCodeController.getInstance().findDiscountCodeByCode(input);
                if(input.equals(BACK_BUTTON))
                    return;
                do {
                    System.out.println ( "Which field do you want to edit?" );
                    choice = getValidMenuNumber(1,6);
                    switch (Integer.parseInt(choice)){
                        case 1:
                            System.out.println("Enter new start time:");
                            String time = getValidDateTime();
                            DiscountCodeController.getInstance().editDiscountCode(discountCode,1,time);
                            break;
                        case 2:
                            System.out.println("Enter new end time:");
                            time = getValidDateTime();
                            DiscountCodeController.getInstance().editDiscountCode(discountCode,2,time);
                            break;
                        case 3:
                            System.out.println("Enter new end percentage:");
                            String amount = getValidDouble(100);
                            DiscountCodeController.getInstance().editDiscountCode(discountCode,3,amount);
                            break;
                        case 4:
                            System.out.println("Enter new max amount:");
                            amount = getValidDouble(Double.POSITIVE_INFINITY);
                            DiscountCodeController.getInstance().editDiscountCode(discountCode,4,amount);
                            break;
                        case 5:
                            System.out.println("Enter new use counter:");
                            String num = getValidMenuNumber(1,Integer.MAX_VALUE);
                            DiscountCodeController.getInstance().editDiscountCode(discountCode,5,num);
                            break;
                        case 6:
                            System.out.println("Enter customer username:");
                            input = getValidCustomer();
                            DiscountCodeController.getInstance().editDiscountCode(discountCode,6,input);
                        case 7:
                            System.out.println("Enter customer username:");
                            input = getValidCustomer();
                            DiscountCodeController.getInstance().editDiscountCode(discountCode,7,input);
                    }

                } while (!choice.equals("8"));
            }
        };

    }

    public Menu getRemoveDiscountCode(){
        return new Menu("remove discount code",this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                System.out.println("Enter discount code: ");
                String input = getValidDiscountCode();
                if(input.equals(".."))
                    return;
                String c = assertDeletion();
                if(c.equalsIgnoreCase("y")) {
                    DiscountCodeController.getInstance().removeDiscountCode(DiscountCodeController.getInstance().findDiscountCodeByCode(input));
                }
            }
        };
    }

    public String getValidDiscountCode(){
        boolean check;
        String input;
        do {
            System.out.println("you can enter \"..\" to return.");
            input = scanner.nextLine ( );
            if (input.equals(".."))
                return input;
            check = DiscountCodeController.getInstance().isThereDiscountCodeByCode(input);
            if (!check){
                System.out.println("You have entered wrong discount code:");
            }

        }while (!check);
        return input;
    }

    public void showAllDiscountCodesTable() {
        helpMessage = "";
        String onFormat = "|%-55s|";
        for (DiscountCode discountCode : DiscountCodeController.getInstance().getAllDiscountCodes ()) {
            System.out.println(String.format("%s",STRAIGHT_LINE));
            System.out.println(String.format(onFormat, "Code: "+ discountCode.getCode()));
            System.out.println(String.format("%s",STRAIGHT_LINE));
        }
    }

    public void viewDiscountCode(DiscountCode discountCode){
        String onFormat = "|%-55s|";
        System.out.println(String.format("%s",STRAIGHT_LINE));
        System.out.println(String.format(onFormat, "Code: "+ discountCode.getCode()));
        System.out.println(String.format("%s",STRAIGHT_LINE));
        System.out.println(String.format(onFormat,"Order Details"));
        System.out.println(String.format("%s",STRAIGHT_LINE));
        System.out.println(String.format(onFormat, "discount code percentage: " + discountCode.getDiscountPercentage()));
        System.out.println(String.format(onFormat, "discount code usage times: "+discountCode.getUseCounter()));
        System.out.println(String.format(onFormat, "maximum discount amount: "+discountCode.getMaxDiscount()));
        System.out.println(String.format(onFormat, "start time: "+discountCode.getStartTime()));
        System.out.println(String.format(onFormat,"end time: "+discountCode.getEndTime()));
        System.out.println(String.format("%s",STRAIGHT_LINE));
    }

}