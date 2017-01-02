/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author shashiupadhyay
 */
public class RBMSAppServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
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

    @Override
    public void init() throws ServletException {
        // Servlet initialization code here
        super.init();
    }

    private static String decimalformatter(Double d) {
        DecimalFormat df = new DecimalFormat(".##");
//        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(d);
    }

    private static String Dateformatter(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        return formatter.format(d);
    }

    public void displayProductDetails(PrintWriter out) {
        try {
            List list = new ArrayList();
            list = new RBMSAppDAO().getProducts(RBMSAppServlet.PACKAGE_NAME, RBMSAppServlet.GET_PRODUCTS);
            System.out.println("fetch List Size " + list.size());
            Iterator it = list.iterator();
            int index = 0;
            out.println("<h3>Product Details</h3>");
            out.println("<tr>");
            out.println("<td>PID</td>");
            out.println("<td>PNAME</td>");
            out.println("<td>QOH</td>");
            out.println("<td>THRESHOLD</td>");
            out.println("<td>ORIGINAL_PRICE</td>");
            out.println("<td>DISCOUNT RATE</td>");
            out.println("</tr>");
            while (it.hasNext() && index < list.size()) {
                if (list.get(index) != null) {
                    Product prod = new Product();
                    prod = (Product) list.get(index);
                    index++;
                    out.println("<tr>");
                    out.println("<td>" + prod.getPID() + "</td>");
                    out.println("<td>" + prod.getPNAME() + "</td>");
                    out.println("<td>" + prod.getQOH() + "</td>");
                    out.println("<td>" + prod.getQOH_THRESHOLD() + "</td>");
                    out.println("<td>" + decimalformatter(prod.getORIGINAL_PRICE()) + "</td>");
                    out.println("<td>" + decimalformatter(prod.getDISCNT_RATE()) + "</td>");
                    out.println("</tr>");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(RBMSAppServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayCustomerDetails(PrintWriter out) {
        try {
            List list = new ArrayList();
            list = new RBMSAppDAO().getCustomers(RBMSAppServlet.PACKAGE_NAME, RBMSAppServlet.GET_CUSTOMERS);
            System.out.println("fetch List Size " + list.size());
            Iterator it = list.iterator();
            int index = 0;
            out.println("<h3>Customer Details</h3>");
            out.println("<tr>");
            out.println("<td>CID</td>");
            out.println("<td>Customer Name</td>");
            out.println("<td>Telephone</td>");
            out.println("<td>Visits Made</td>");
            out.println("<td>Last Visit Date</td>");
            out.println("</tr>");
            while (it.hasNext() && index < list.size()) {
                if (list.get(index) != null) {
                    Customer cust = new Customer();
                    cust = (Customer) list.get(index);
                    System.out.println(cust.getCID());
                    System.out.println(cust.getCID());
                    index++;
                    out.println("<tr>");
                    out.println("<td>" + cust.getCID() + "</td>");
                    out.println("<td>" + cust.getCNAME() + "</td>");
                    out.println("<td>" + cust.getTELEPHONE() + "</td>");
                    out.println("<td>" + cust.getVISITS_MADE() + "</td>");
                    out.println("<td>" + Dateformatter(cust.getLAST_VISIT_DATE()) + "</td>");
                    out.println("</tr>");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RBMSAppServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayEmployeeDetails(PrintWriter out) {
        try {
            List list = new ArrayList();
            list = new RBMSAppDAO().getEmployees(RBMSAppServlet.PACKAGE_NAME, RBMSAppServlet.GET_EMPLOYEES);
            System.out.println("fetch List Size " + list.size());
            Iterator it = list.iterator();
            int index = 0;
            out.println("<h3>Employee Details</h3>");
            out.println("<tr>");
            out.println("<td>EID</td>");
            out.println("<td>EName</td>");
            out.println("<td>Telephone</td>");
            out.println("</tr>");
            while (it.hasNext() && index <= list.size()) {
                if (list.get(index) != null) {
                    Employee emp = new Employee();
                    emp = (Employee) list.get(index);
                    System.out.println(emp.getEID());
                    System.out.println(emp.getEName());
                    System.out.println(emp.getTelephone());
                    index++;
                    out.println("<tr>");
                    out.println("<td>" + emp.getEID() + "</td>");
                    out.println("<td>" + emp.getEName() + "</td>");
                    out.println("<td>" + emp.getTelephone() + "</td>");
                    out.println("</tr>");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RBMSAppServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayPurchaseDetails(PrintWriter out) {
        try {
            List list = new ArrayList();
            list = new RBMSAppDAO().getPurchases(RBMSAppServlet.PACKAGE_NAME, RBMSAppServlet.GET_PURCHASES);
            System.out.println("fetch List Size " + list.size());
            Iterator it = list.iterator();
            int index = 0;
            out.println("<h3>Purchase Details</h3>");
            out.println("<tr>");
            out.println("<td>PUR</td>");
            out.println("<td>EID</td>");
            out.println("<td>PID</td>");
            out.println("<td>CID()</td>");
            out.println("<td>QTY</td>");
            out.println("<td>Purchase Time</td>");
            out.println("<td>TOTAL PRICE</td>");
            out.println("</tr>");
            while (it.hasNext() && index < list.size()) {
                if (list.get(index) != null) {
                    Purchases pur = new Purchases();
                    pur = (Purchases) list.get(index);
                    index++;
                    out.println("<tr>");
                    out.println("<td>" + pur.getPUR() + "</td>");
                    out.println("<td>" + pur.getEID() + "</td>");
                    out.println("<td>" + pur.getPID() + "</td>");
                    out.println("<td>" + pur.getCID() + "</td>");
                    out.println("<td>" + pur.getQTY() + "</td>");
                    out.println("<td>" + Dateformatter(pur.getPTIME()) + "</td>");
                    out.println("<td>" + decimalformatter(pur.getTOTAL_PRICE()) + "</td>");
                    out.println("</tr>");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RBMSAppServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displaySupplierDetails(PrintWriter out) {
        try {
            List list = new ArrayList();
            list = new RBMSAppDAO().getSuppliers(RBMSAppServlet.PACKAGE_NAME, RBMSAppServlet.GET_SUPPLIERS);
            System.out.println("fetch List Size " + list.size());
            Iterator it = list.iterator();
            int index = 0;
            out.println("<h3>Supplier Details</h3>");
            out.println("<tr>");
            out.println("<td>SID</td>");
            out.println("<td>SNAME</td>");
            out.println("<td>City</td>");
            out.println("<td>Telephone</td>");
            out.println("</tr>");
            while (it.hasNext() && index < list.size()) {
                if (list.get(index) != null) {
                    Suppliers sup = new Suppliers();
                    sup = (Suppliers) list.get(index);
                    index++;
                    out.println("<tr>");
                    out.println("<td>" + sup.getSID() + "</td>");
                    out.println("<td>" + sup.getSNAME() + "</td>");
                    out.println("<td>" + sup.getCity() + "</td>");
                    out.println("<td>" + sup.getTelephone() + "</td>");
                    out.println("</tr>");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RBMSAppServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displaySupplyDetails(PrintWriter out) {
        try {
            List list = new ArrayList();
            list = new RBMSAppDAO().getSupply(RBMSAppServlet.PACKAGE_NAME, RBMSAppServlet.GET_SUPPLY);
            System.out.println("fetch List Size " + list.size());
            Iterator it = list.iterator();
            int index = 0;
            out.println("<h3>Supply Details</h3>");
            out.println("<tr>");
            out.println("<td>SUP</td>");
            out.println("<td>PID</td>");
            out.println("<td>SID</td>");
            out.println("<td>SDATE</td>");
            out.println("<td>Quantity</td>");
            out.println("</tr>");
            while (it.hasNext() && index < list.size()) {
                if (list.get(index) != null) {
                    Supply supply = new Supply();
                    supply = (Supply) list.get(index);
                    index++;
                    out.println("<tr>");
                    out.println("<td>" + supply.getSUP() + "</td>");
                    out.println("<td>" + supply.getPID() + "</td>");
                    out.println("<td>" + supply.getSID() + "</td>");
                    out.println("<td>" + Dateformatter(supply.getSDATE()) + "</td>");
                    out.println("<td>" + supply.getQuantity() + "</td>");
                    out.println("</tr>");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RBMSAppServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayMonthlySale(PrintWriter out, String SupplyPID) {
        try {
            List list = new ArrayList();
            list = new RBMSAppDAO().getMonthly_Sale(RBMSAppServlet.PACKAGE_NAME, RBMSAppServlet.GET_MONTHLY_SALE, SupplyPID);
            System.out.println("fetch List Size " + list.size());
            if (list.isEmpty()) {
                out.println("<tr>");
                out.println("<td></td>");
                out.println("<td>Error: No record for given product</td>");
                out.println("</tr>");
                System.out.println("Error: No record for given product");
            } else {
                Iterator it = list.iterator();
                int index = 0;
                out.println("<h3>Monthly Sale Report</h3>");
                out.println("<tr>");
                out.println("<td>Year</td>");
                out.println("<td>Month</td>");
                out.println("<td>Product Name</td>");
                out.println("<td>Quantity</td>");
                out.println("<td>Total Price</td>");
                out.println("<td>Average Sale</td>");
                out.println("</tr>");
                while (it.hasNext() && index < list.size()) {
                    if (list.get(index) != null) {
                        MonthlySale monthsale = new MonthlySale();
                        monthsale = (MonthlySale) list.get(index);
                        index++;
                        out.println("<tr>");
                        out.println("<td>" + monthsale.getPYEAR() + "</td>");
                        out.println("<td>" + monthsale.getPMONTH() + "</td>");
                        out.println("<td>" + monthsale.getPNAme() + "</td>");
                        out.println("<td>" + monthsale.getQTY() + "</td>");
                        out.println("<td>" + decimalformatter(monthsale.getTOTAL_PRICE()) + "</td>");
                        out.println("<td>" + decimalformatter(monthsale.getAverageSale()) + "</td>");
                        out.println("</tr>");
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RBMSAppServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addPurchase(PrintWriter out, String eid, String pid, String cid, int qty) {
        try {
            int emp_records = RBMSAppDAO.verifyEmp(eid);
            if (emp_records == 0) {
                out.println("<tr>");
                out.println("<td></td>");
                out.println("<td> Error: No record for given employee </td>");
                out.println("</tr>");
                System.out.println("Error: No record for given employee");
            } else {
                int prod_records = RBMSAppDAO.verifyProd(pid);
                if (prod_records == 0) {
                    out.println("<tr>");
                    out.println("<td></td>");
                    out.println("<td>Error: No record for given product</td>");
                    out.println("</tr>");
                    System.out.println("Error: No record for given product");
                } else {
                    int cust_records = RBMSAppDAO.verifyCust(cid);
                    if (cust_records == 0) {
                        out.println("<tr>");
                        out.println("<td></td>");
                        out.println("<td>Error: No record for given customer</td>");
                        out.println("</tr>");
                        System.out.println("Error: No record for given customer");
                    } else if (emp_records > 0 && prod_records > 0 && cust_records > 0) {
                        Map map = new HashMap<String, String>();
                        map = RBMSAppDAO.addPurchase(RBMSAppServlet.PACKAGE_NAME, RBMSAppServlet.ADD_PURCHASE, eid, pid, cid, qty, 5);
                        displayPurchaseDetails(out);
                        Iterator it = map.keySet().iterator();
                        while (it.hasNext()) {
                            String key = (String) it.next();
                            if (map.get(key) != null || !map.get(key).equals("")) {
                                out.println("<tr>");
                                out.println("<td>" + key + "</td>");
                                out.println("<td>" + map.get(key) + "</td>");
                                out.println("</tr>");
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RBMSAppServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addProduct(PrintWriter out,
            String prod_name, int prod_qty, int prod_threshold, double prod_originalprice, double disnt_rate) {
        try {
            String status = "";
            System.out.println("leaving  addProduct");
            status = RBMSAppDAO.addProduct(RBMSAppServlet.PACKAGE_NAME, RBMSAppServlet.ADD_PRODUCTS,
                    prod_name, prod_qty, prod_threshold, prod_originalprice, disnt_rate, 6);
            out.println("<tr>");
            out.println("<td></td>");
            out.println("<td>Information: Record Successfully Inserted into the Product Table</td>");
            out.println("</tr>");
            System.out.println("Information: Record Successfully Inserted into the Product Table");
            displayProductDetails(out);
        } catch (SQLException ex) {
            Logger.getLogger(RBMSAppServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String dropdownval = request.getParameter("dropdownval");
            System.out.println("Attribute value -> " + dropdownval);
            List list = new ArrayList();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>RBMS Application</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<table border=5>");
            out.println("<h1>RBMS Application</h1>");
            if (dropdownval.equals("product")) {
                displayProductDetails(out);
            }
            if (dropdownval.equals("employee")) {
                displayEmployeeDetails(out);
            }
            if (dropdownval.equals("customer")) {
                displayCustomerDetails(out);
            }
            if (dropdownval.equals("purchase")) {
                displayPurchaseDetails(out);
            }
            if (dropdownval.equals("supplier")) {
                displaySupplierDetails(out);
            }
            if (dropdownval.equals("supply")) {
                displaySupplyDetails(out);
            }
            if (dropdownval.equals("monthlysale")) {
                String SupplyPID = request.getParameter("SupplyPID");
                displayMonthlySale(out, SupplyPID);
            }
            if (dropdownval.equals("addpurchase")) {
                String eid = request.getParameter("apueid");
                String pid = request.getParameter("apupid");
                String cid = request.getParameter("apucid");
                int qty = Integer.parseInt(request.getParameter("apuqty"));
                addPurchase(out, eid, pid, cid, qty);
            }
            if (dropdownval.equals("addproduct")) {
                String prodname = request.getParameter("aprpname");
                int prodqty = Integer.parseInt(request.getParameter("aprpqoh"));
                int prodthhold = Integer.parseInt(request.getParameter("aprthresh"));
                double prodprice = Double.parseDouble(request.getParameter("aproprice"));
                double discntrate = Double.parseDouble(request.getParameter("aprdisrate"));
                System.out.println("Prod Name " + prodname);
                System.out.println("Quantity " + prodqty);
                System.out.println("Threshhole " + prodthhold);
                System.out.println("Prod Price " + prodprice);
                System.out.println("Discount Rate " + discntrate);
                System.out.println("leaving  Servelt");
                addProduct(out, prodname, prodqty, prodthhold, prodprice, discntrate);
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RBMSAppServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RBMSAppServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
