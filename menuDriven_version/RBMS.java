/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.d4
 */
//package dbmsproject;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shashiupadhyay
 */
public class RBMS {

    public static String PACKAGE_NAME = "package_rbms";
    public static String GET_EMPLOYEES = "getemployees";
    public static String GET_CUSTOMERS = "getcustomers";
    public static String GET_PRODUCTS = "getproducts";
    public static String GET_PURCHASES = "getpurchases";
    public static String GET_SUPPLIERS = "getsuppliers";
    public static String GET_SUPPLY = "getsupply";
    public static String GET_LOGS = "getlogs";
    public static String GET_MONTHLY_SALE = "getmonthly_sale";
    public static String ADD_PURCHASE = "addpurchase";
    public static String ADD_PRODUCTS = "addproducts";
    public static String GET_STOCK = "getstock";
    public static String message;

    public static boolean validateProductInput(String str_input) {
        if (str_input.startsWith("p") && str_input.length() == 4) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateEmployeeInput(String str_input) {
        if (str_input.startsWith("e") && str_input.length() == 3) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateCustomerInput(String str_input) {
        if (str_input.startsWith("c") && str_input.length() == 4) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validatePurchaseID(int input) {
        if (input > 1000000) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateSupplierID(String input) {
        if (input.startsWith("s") && input.length() == 2) {
            return true;
        } else {
            return false;
        }
    }

    public static void displayProductDetails() {
        try {
            String str_input = "";
            System.out.println("Product Catalogue");
            message = "Kindly Wait Fetching product Information.";
            System.out.println(message);
            RBMSDAO.getProducts(RBMS.PACKAGE_NAME, RBMS.GET_PRODUCTS, str_input);
        } catch (SQLException ex) {
            Logger.getLogger(RBMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void displayCustomersDetails() {
        try {
            String str_input = "";
            System.out.println("Customer Details ");
            RBMSDAO.getCustomers(RBMS.PACKAGE_NAME, RBMS.GET_CUSTOMERS, str_input);
        } catch (SQLException ex) {
            Logger.getLogger(RBMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void displayEmployeeDetails() {
        try {
            String str_input = "";
            System.out.println("Employee Details");
            RBMSDAO.getEmployees(RBMS.PACKAGE_NAME, RBMS.GET_EMPLOYEES, str_input);
        } catch (SQLException ex) {
            Logger.getLogger(RBMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void displayPurchasesDetails() {
        try {
            int str_input = 0;
            System.out.println("Purchase Inquiry");
            RBMSDAO.getPurchases(RBMS.PACKAGE_NAME, RBMS.GET_PURCHASES, str_input);
        } catch (SQLException ex) {
            Logger.getLogger(RBMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void getSuppliersDetails() {
        try {
            String str_input = "";
            System.out.println("Supplier Details");
            RBMSDAO.getSuppliers(RBMS.PACKAGE_NAME, RBMS.GET_SUPPLIERS, str_input);
        } catch (SQLException ex) {
            Logger.getLogger(RBMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void getSupplyDetails() {
        try {
            int str_input = 0;
            System.out.println("Supply Inquiry");
            RBMSDAO.getSupply(RBMS.PACKAGE_NAME, RBMS.GET_SUPPLY, str_input);
        } catch (SQLException ex) {
            Logger.getLogger(RBMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void getMonthly_Sale() {
        try {
            String str_input;
            System.out.println("Monthly Sale Report");
            System.out.println("Provide the productID.\t\t\t\t  Sample PID: p001");
            Scanner input = new Scanner(System.in);
            str_input = input.nextLine();
            if (validateProductInput(str_input)) {
                RBMSDAO.getMonthly_Sale(RBMS.PACKAGE_NAME, RBMS.GET_MONTHLY_SALE, str_input);
            } else {
                System.out.println("Information: Kindly provide the correct Product ID.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(RBMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addPurchase() {
        try {
            String employee;
            String product;
            String customer;
            int quantity;
            System.out.println("Kindly enter the purchase details");
            Scanner input = new Scanner(System.in);
            System.out.println("Kindly enter the employee ID");
            employee = input.nextLine();
            System.out.println("Kindly enter the product ID");
            product = input.nextLine();
            System.out.println("Kindly enter the customer ID");
            customer = input.nextLine();
            System.out.println("Kindly enter the purchase quantity");
            if (input.hasNextInt()) {
                quantity = input.nextInt();
                boolean valid_emp = false;
                boolean valid_prod = false;
                boolean valid_cust = false;
                if (validateProductInput(product)) {
                    valid_prod = true;
                } else {
                    System.out.println("Error: Kindly enter Product ID in correct format");
                }

                if (validateEmployeeInput(employee)) {
                    valid_emp = true;
                } else {
                    System.out.println("Error: Kindly enter Employee ID in correct format");
                }

                if (validateCustomerInput(customer)) {
                    valid_cust = true;
                } else {
                    System.out.println("Error: Kindly enter Customer ID in correct format");
                }

                if (valid_prod && valid_emp && valid_cust) {
                    int emp_records = RBMSDAO.verifyEmp(employee);
                    if (emp_records == 0) {
                        System.out.println("Error: No record for given employee");
                    } else {
                        int prod_records = RBMSDAO.verifyProd(product);
                        if (prod_records == 0) {
                            System.out.println("Error: No record for given product");
                        } else {
                            int cust_records = RBMSDAO.verifyCust(customer);
                            if (cust_records == 0) {
                                System.out.println("Error: No record for given customer");
                            } else {
                                if (emp_records > 0 && prod_records > 0 && cust_records > 0) {
                                    RBMSDAO.addPurchase(RBMS.PACKAGE_NAME, RBMS.ADD_PURCHASE, employee, product, customer, quantity, 5);
                                }
                            }
                        }
                    }
                }
            } else {
                System.out.println("Error: Input is not a valid Integer");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RBMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addProduct() {
        try {
            String prod_name;
            int prod_qty = 0;
            int prod_threshold = 0;
            double prod_originalprice = 0;
            double disnt_rate = 0;
            boolean valid_execution = false;
            System.out.println("Kindly enter the product details");
            Scanner input = new Scanner(System.in);
            System.out.println("Kindly enter the product Name");
            prod_name = input.nextLine();
            System.out.println("Kindly enter the product quantity");
            if (input.hasNextInt()) {
                prod_qty = input.nextInt();
            } else {
                System.out.println("Error: Product Quantity should be a number");
            }
            System.out.println("Kindly enter the product threshold");
            if (input.hasNextInt()) {
                prod_threshold = input.nextInt();
            } else {
                System.out.println("Error: Product Threshold should be a number");
            }

            System.out.println("Kindly enter the product original price");
            if (input.hasNextDouble()) {
                prod_originalprice = input.nextDouble();
            } else {
                System.out.println("Error: Product Original Price should be a decimal number");
            }

            System.out.println("Kindly enter the disct rate on the product\t\t Allowed Rates(0 - 0.9)");
            if (input.hasNextDouble()) {
                disnt_rate = input.nextDouble();
            } else {
                System.out.println("Error: Discount Rate value should be a decimal number");
            }

            if (prod_qty <= 0) {
                valid_execution = false;
                System.out.println("Error: Invalid Purchases Quantity");
            } else {
                valid_execution = true;
            }
            if (disnt_rate <= 0.9) {
                valid_execution = true;
            } else {
                valid_execution = false;
                System.out.println("Error: Invalid Product Discount Rate");
            }

            if (prod_threshold < 0 || prod_threshold > prod_qty) {
                valid_execution = false;
                System.out.println("Invalid Product Threshold Value");
            } else {
                valid_execution = true;
            }
            if (valid_execution) {
                RBMSDAO.addProduct(RBMS.PACKAGE_NAME, RBMS.ADD_PRODUCTS, prod_name, prod_qty, prod_threshold, prod_originalprice, disnt_rate, 6);
            }else{
                System.out.println("Invalid Purchse Figures. Kindly re-enter the values.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(RBMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printMenu() {
        System.out.println("\n");
        System.out.println("============================");
        System.out.println("|   MENU SELECTION          |");
        System.out.println("============================");
        System.out.println("|   Options:                |");
        System.out.println("|  1. Product               |");
        System.out.println("|  2. Employee              |");
        System.out.println("|  3. Customer              |");
        System.out.println("|  4. Purchase              |");
        System.out.println("|  5. Supplier              |");
        System.out.println("|  6. Supply                |");
        System.out.println("|  7. Monthly Sale report   |");
        System.out.println("|  8. Add Purchase          |");
        System.out.println("|  9. Add Product           |");
        System.out.println("| --------------------------|");
        System.out.println("|  0. Exit                  |");
        System.out.println("============================");
        System.out.println("");
        System.out.println("Enter your choice of operation");
    }

    public static void fetchDetails() {

        int user_input = -1;
        do {
            Scanner key_input = new Scanner(System.in);
            if (key_input.hasNextInt()) {
                user_input = key_input.nextInt();
                switch (user_input) {
                    case 1:
                        displayProductDetails();
                        break;
                    case 2:
                        displayEmployeeDetails();
                        break;
                    case 3:
                        displayCustomersDetails();
                        break;
                    case 4:
                        displayPurchasesDetails();
                        break;
                    case 5:
                        getSuppliersDetails();
                        break;
                    case 6:
                        getSupplyDetails();
                        break;
                    case 7:
                        getMonthly_Sale();
                        break;
                    case 8:
                        addPurchase();
                        break;
                    case 9:
                        addProduct();
                        break;
                    default:
                        System.out.println("Error: Invalid Choice");
                        break;
                }
                key_input.reset();
                printMenu();
            } else {
                System.out.println("Error: Input should be a valid Integer");
            }
        } while (user_input != 0);
    }

    private static void logout() {
        System.out.println("You Are Logged Out");
    }

    public static void main(String[] args) {
        printMenu();
        fetchDetails();
        logout();
    }
}
