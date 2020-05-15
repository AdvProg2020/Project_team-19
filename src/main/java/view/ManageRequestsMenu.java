package view;

import model.*;

import java.io.IOException;
import java.util.ArrayList;

import static controller.RequestController.*;

public class ManageRequestsMenu extends Menu {

    public ManageRequestsMenu(Menu parent) {
        super("Manage Requests", parent);
        subMenus.put(1, getShowAllRequestsMenu());
        subMenus.put(2, getShowSalespersonRequests());
        subMenus.put(3, getShowProductRequests());
        subMenus.put(4, getShowDiscountRequests());
        subMenus.put(5, getAcceptOrDeclineMenu());
        subMenus.put(6, getHelpMenu(this));
    }

    private Menu getShowAllRequestsMenu () {
        return new Menu("Show All Requests", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                showRequests(getSpecificTypeOfRequests(Request.class));
            }
        };
    }

    private Menu getShowSalespersonRequests() {
        return new Menu("Show Salesperson Requests", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                showRequests(getSpecificTypeOfRequests(SalespersonRequest.class));
            }
        };
    }

    private Menu getShowProductRequests() {
        return new Menu("Show Product Requests", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                showRequests(getSpecificTypeOfRequests(ProductRequest.class));
            }
        };
    }

    private Menu getShowDiscountRequests() {
        return new Menu("Show Discount Requests", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                showRequests(getSpecificTypeOfRequests(DiscountRequest.class));
            }
        };
    }

    private Menu getAcceptOrDeclineMenu() {
        return new Menu("Accept Or Decline Menu", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " :");
            }

            @Override
            public void execute() {
                System.out.println("Enter requestId to see :");
                String input;
                Request request;
                while ((request = Request.getRequestById(input = scanner.nextLine())) == null && !input.equals(".."))
                    System.out.println("Enter Valid Id :( or \"..\" to Fucking Back");
                if (input.equals(".."))
                    return;
                showSingleRequest(request);
                System.out.println("Enter 1 to accept, 2 to decline :");
                input = scanner.nextLine();
                while (!input.matches("[12]") && !input.equals(".."))
                    System.out.println("Enter 1 or 2 coskhol or \"..\" to fucking back");

                if (input.equals(".."))
                    return;
                if (input.equals("1")) {
                    try {
                        acceptRequest(request);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Accepted.");
                }
                else if (input.equals("2")) {
                    try {
                        declineRequest(request);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Declined.");
                }
            }
        };
    }

    public void showRequests (ArrayList<Request> requests) {
        for (Request request : requests) {
            showSingleRequest(request);
            System.out.println();
        }
    }

    public void showSingleRequest(Request request) {
        System.out.println(LINE);
        System.out.println(String.format("|%-26s%-18s%-11s|", "", request.getRequestId(), ""));
        System.out.println(LINE);
        System.out.println(String.format("%-55s",request.show()));
        System.out.println(LINE);
    }
}