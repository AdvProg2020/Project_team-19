package view;

import controller.DiscountCodeController;
import model.DiscountCode;

public class ManageDiscountCodeMenu extends Menu {

    public ManageDiscountCodeMenu(Menu parent){
        super("Discount Codes",parent);
        subMenus.put(1,getViewDiscountCode());
        subMenus.put(2,getEditDiscountCode());
        subMenus.put(3,getRemoveDiscountCode());
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

    public Menu getEditDiscountCode(){
        return new Menu("edit discount code",this) {
            @Override
            public void show() {
                System.out.println ( "1. Start Time" );
                System.out.println ( "2. End Time" );
                System.out.println ( "3. Discount Percentage" );
                System.out.println ( "4. Maximum Discount Amount" );
                System.out.println ( "5. Usage Counter" );
                System.out.println("6. Exit");
            }

            @Override
            public void execute() {
                String choice;
                System.out.println("Enter discount code: ");
                String input = getValidDiscountCode();
                DiscountCode discountCode = DiscountCodeController.getInstance().findDiscountCodeByCode(input);
                if(input.equals(".."))
                    return;
                do {
                    System.out.println ( "Which field do you want to edit?" );
                    choice = getValidMenuNumber(6);
                    switch (Integer.parseInt(choice)){
                        case 1:
                            String time = getValidDateTime ();
                            DiscountCodeController.getInstance().editDiscountCode(discountCode,1,time);
                            break;
                        case 2:
                            time = getValidDateTime ();
                            DiscountCodeController.getInstance().editDiscountCode(discountCode,2,time);
                            break;
                        case 3:
                            String amount = getValidDouble(100);
                            DiscountCodeController.getInstance().editDiscountCode(discountCode,3,amount);
                            break;
                        case 4:
                            amount = getValidDouble(Double.POSITIVE_INFINITY);
                            DiscountCodeController.getInstance().editDiscountCode(discountCode,4,amount);
                            break;
                        case 5:
                            String num = getValidMenuNumber(Integer.MAX_VALUE);
                            DiscountCodeController.getInstance().editDiscountCode(discountCode,5,num);
                            break;
                    }

                }while (!choice.equals("6"));
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
                if(c.equalsIgnoreCase("y")){
                    DiscountCodeController.getInstance().removeDiscountCode(DiscountCodeController.getInstance().findDiscountCodeByCode(input));
                }else
                    return;
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

    public String assertDeletion(){
        boolean check;
        String input;
        do {
            System.out.println("Are you sure you want to remove this discount code?(Y|N)");
            input = scanner.nextLine ( );
            check = (input.equalsIgnoreCase("y")||input.equalsIgnoreCase("n"));
        }while (!check);
        return input;
    }
}