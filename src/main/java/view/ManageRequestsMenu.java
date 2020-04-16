package view;

import controller.RequestController;
import model.Request;

import java.util.List;

public class ManageRequestsMenu extends Menu {
    public ManageRequestsMenu(Menu parent) {
        super("Manage Requests", parent);
        submenus.put(1, getShowRequestsMenu());
        submenus.put(2, getHelpMenu(this));
    }

    private Menu getShowRequestsMenu () {
        return new Menu("Show Requests", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
                System.out.println(
                        "1.show all requests\n" +
                        "2.show salesperson request\n" +
                        "3.show product requests\n" +
                        "4.show discount requests");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.matches("[1234]")) {
                    showRequests(RequestController.processGetSpecificRequests(getRequestTypeByInput(input)));
                }
                else
                    System.out.println("Please Enter Valid Number.");
            }
        };
    }

    private RequestController.FilterType getRequestTypeByInput (String input) {
        switch (input) {
            case "1":
                return RequestController.FilterType.ALL;
            case "2":
                return RequestController.FilterType.SALESPERSON;
            case "3":
                return RequestController.FilterType.PRODUCT;
            default:
                return RequestController.FilterType.DISCOUNT;
        }
    }

    private void showRequests (List<Request> requests) {
        for (Request request : requests) {
            System.out.println(request.show());
        }
    }

    private Menu getAcceptMenu () {
        return new Menu("Accept Menu", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

}
