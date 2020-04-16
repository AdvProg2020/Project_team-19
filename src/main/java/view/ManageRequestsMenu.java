package view;

import controller.RequestController;
import model.Request;

import java.util.List;

public class ManageRequestsMenu extends Menu {
    public ManageRequestsMenu(Menu parent) {
        super("Manage Requests", parent);
        subMenus.put(1, getShowRequestsMenu());
        subMenus.put(2, getHelpMenu(this));
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
        if ("1".equals(input)) {
            return RequestController.FilterType.ALL;
        } else if ("2".equals(input)) {
            return RequestController.FilterType.SALESPERSON;
        } else if ("3".equals(input)) {
            return RequestController.FilterType.PRODUCT;
        }
        return RequestController.FilterType.DISCOUNT;
    }

    private void showRequests (List<Request> requests) {
        for (Request request : requests) {
            System.out.println(request.show());
        }
    }


}
