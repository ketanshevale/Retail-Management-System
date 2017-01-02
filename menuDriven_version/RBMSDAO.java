
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.text.SimpleDateFormat;
import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author shashiupadhyay
 */
public class RBMSDAO {

    private static int Stock;
    private static int suppliedBy;

    /**
     * @return the Stock
     */
    public static int getStock() {
        return Stock;
    }

    /**
     * @param aStock the Stock to set
     */
    public static void setStock(int aStock) {
        Stock = aStock;
    }
    public static String URL = "jdbc:oracle:thin:@localhost:1521:oracle";
    public static String USERID = "SCOTT";
    public static String PASSWORD = "oracle";

    private static Connection getConnection() {
        Connection conn = null;
        try {
            OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
            ds.setURL(RBMSDAO.URL);
            conn = ds.getConnection(RBMSDAO.USERID, RBMSDAO.PASSWORD);
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        return conn;
    }

    public static String getFunctionCallWithParameter(String Package_name, String Function_name) {
        return "begin ? := " + Package_name + "." + Function_name + "(?); end;";
    }

    public static String getFunctionCallWithoutParameter(String Package_name, String Function_name) {
        return "begin ? := " + Package_name + "." + Function_name + "(); end;";
    }

    public static String getProcedureCall(String Package_name, String Function_name, int place_holder_count) {

        String place_holder = "(";
        for (int i = 1; i <= place_holder_count; i++) {
            place_holder += ":" + Integer.toString(i).concat(",");
        }
        place_holder = place_holder.substring(0, place_holder.length() - 1);
        place_holder += ");";
        String procedure_fetch_str = "begin " + Package_name + "." + Function_name + place_holder + " end;";
        System.out.println("procedure_fetch_str " + procedure_fetch_str);
        return procedure_fetch_str;
    }

    public static void getEmployees(String Package_name, String Function_name, String emp_id) throws SQLException {
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            conn = getConnection();
            cs = conn.prepareCall(RBMSDAO.getFunctionCallWithoutParameter(Package_name, Function_name));
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            rs = (ResultSet) cs.getObject(1);
            System.out.println("\nEmployees Details:\nEID" + "\t" + "ENAME" + "\t" + "TELEPHONE#");
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t"
                        + rs.getString(2) + "\t"
                        + rs.getString(3));
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void getCustomers(String Package_name, String Function_name, String c_id) throws SQLException {
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            conn = getConnection();
            cs = conn.prepareCall(RBMSDAO.getFunctionCallWithoutParameter(Package_name, Function_name));
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            rs = (ResultSet) cs.getObject(1);
            System.out.println("\nCustomers Details:\nCID" + "\t" + "CNAME" + "\t" + "TELEPHONE#" + "\t" + "Visits_Made#" + "\t" + "Last_Visit_Date");
            while (rs.next()) {
                System.out.println(rs.getString("cid") + "\t"
                        + rs.getString("cname") + "\t"
                        + rs.getString("telephone#") + "\t\t"
                        + rs.getInt("visits_made") + "\t"
                        + rs.getDate("last_visit_date"));
            }

        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        } finally {
            //close the result set, statement, and the connection
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void getProducts(String Package_name, String Function_name, String p_id) throws SQLException {

        Connection conn = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            conn = getConnection();
            cs = conn.prepareCall(RBMSDAO.getFunctionCallWithoutParameter(Package_name, Function_name));
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            rs = (ResultSet) cs.getObject(1);

            System.out.println("\nProduct Details:\nPID" + "\t" + "PNAME" + "\t"
                    + "QOH" + "\t" + "QOH_THRESHOLD" + "\t" + "ORIGINAL_PRICE" + "\t" + "DISCNT_RATE");
            while (rs.next()) {
                System.out.println(
                        rs.getString("pid") + "\t"
                        + rs.getString("pname") + "\t"
                        + rs.getInt("qoh") + "\t\t"
                        + rs.getInt("qoh_threshold") + "\t"
                        + rs.getDouble("original_price") + "\t\t"
                        + rs.getDouble("discnt_rate"));
            }

        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        } finally {
            //close the result set, statement, and the connection
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void getPurchases(String Package_name, String Function_name, int purchase_id) throws SQLException {
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            conn = getConnection();
            cs = conn.prepareCall(RBMSDAO.getFunctionCallWithoutParameter(Package_name, Function_name));
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            rs = (ResultSet) cs.getObject(1);

            // print the results
            System.out.println("\nPurchases Details:\nPUR#" + "\t" + "EID" + "\t"
                    + "PID" + "\t" + "CID" + "\t" + "QTY" + "\t" + "PTIME" + "\t\t" + "Total_Price");
            while (rs.next()) {
                System.out.println(rs.getInt("pur#") + "\t"
                        + rs.getString("eid") + "\t"
                        + rs.getString("pid") + "\t"
                        + rs.getString("cid") + "\t"
                        + rs.getInt("qty") + "\t"
                        + rs.getDate("ptime") + "\t"
                        + rs.getDouble("total_price"));
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        } finally {
            //close the result set, statement, and the connection
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void getSuppliers(String Package_name, String Function_name, String supplier_id) throws SQLException {

        Connection conn = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            conn = getConnection();
            cs = conn.prepareCall(RBMSDAO.getFunctionCallWithoutParameter(Package_name, Function_name));
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            rs = (ResultSet) cs.getObject(1);

            // print the results
            System.out.println("\nSUPPLIERS Details:\n"
                    + "SID" + "\t" + "SNAME" + "\t" + "CITY" + "\t\t" + "TELEPHONE#");
            while (rs.next()) {
                System.out.println(
                        rs.getString("sid") + "\t"
                        + rs.getString("sname") + "\t"
                        + rs.getString("city") + "\t\t"
                        + rs.getString("telephone#"));
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        } finally {
            //close the result set, statement, and the connection
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void getSupply(String Package_name, String Function_name, int supply_id) throws SQLException {

        Connection conn = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            conn = getConnection();
            cs = conn.prepareCall(RBMSDAO.getFunctionCallWithoutParameter(Package_name, Function_name));
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            rs = (ResultSet) cs.getObject(1);

            // print the results
            System.out.println("\nSUPPLY Details:\n"
                    + "SUP#" + "\t" + "PID" + "\t" + "SID" + "\t" + "SDATE" + "\t\t" + "QUANTITY");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("sup#") + "\t"
                        + rs.getString("pid") + "\t"
                        + rs.getString("sid") + "\t"
                        + rs.getDate("sdate") + "\t"
                        + rs.getInt("quantity"));
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        } finally {
            //close the result set, statement, and the connection
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    public static void getMonthly_Sale(String Package_name, String Function_name, String p_id) throws SQLException {

        Connection conn = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            conn = getConnection();
            cs = conn.prepareCall(RBMSDAO.getFunctionCallWithParameter(Package_name, Function_name));
            //register the out parameter (the first parameter)
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            //set the in parameter (the second parameter) 
            cs.setString(2, p_id);
            // execute and retrieve the result set
            cs.execute();
            rs = (ResultSet) cs.getObject(1);
            int rowcount;
            rowcount = -1;
            // print the results
            System.out.println("\nMonthly Sale Report:\n"
                    + "ProductName" + "\t" + "Month" + "\t" + "YEAR" + "\t" + "Quantity" + "\t" + "Total Price" + "\t" + "Average Sale");
            if (rs != null) {
                while (rs.next()) {
                    rowcount++;
                    System.out.println(
                            rs.getString(3) + "\t\t"
                            + rs.getString(2) + "\t"
                            + rs.getString(1) + "\t"
                            + rs.getInt(4) + "\t\t"
                            + rs.getDouble(5) + "\t\t"
                            + rs.getDouble(6));
                }
            }
            if (rowcount < 0) {
                System.out.println("Information : No record for given PID");
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        } finally {
            //close the result set, statement, and the connection
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (conn != null) {
                conn.close();
            }
            fetchTriggerException();
        }
    }

    public static int productSupplierCount(String p_id) throws SQLException {
        Connection conn_prod_avail = null;
        Statement stmt_prod_avail = null;
        ResultSet rst_prod_avail = null;

        try {
            conn_prod_avail = getConnection();
            String sql_query = "select count(*) from supply where PID = '" + p_id + "'";
            stmt_prod_avail = conn_prod_avail.createStatement();
            rst_prod_avail = stmt_prod_avail.executeQuery(sql_query);
            if (rst_prod_avail.next()) {
                setSuppliedBy(rst_prod_avail.getInt(1));
//                System.out.println("Supplied By COunt " + getSuppliedBy());
            }
        } catch (Exception e) {
            System.out.println("EXception in productSupplierCount: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rst_prod_avail != null) {
                rst_prod_avail.close();
            }
            if (stmt_prod_avail != null) {
                stmt_prod_avail.close();
            }
            if (conn_prod_avail != null) {
                conn_prod_avail.close();
            }
        }
//        System.out.println("Supplied By COunt " + getSuppliedBy());
        return getSuppliedBy();
    }

    public static int verifyEmp(String e_id) throws SQLException {
        Connection conn = null;
        Statement stmt_emp = null;
        ResultSet rst_emp = null;

        int count = -1;
        try {
            conn = getConnection();
            String sql_query = "";

            sql_query = "select count(*) from employees where EID = '" + e_id + "'";
            stmt_emp = conn.createStatement();
            rst_emp = stmt_emp.executeQuery(sql_query);
            if (rst_emp.next()) {
                count = rst_emp.getInt(1);
//                System.out.println("Employee Count " + count);
            }

        } catch (Exception e) {
            System.out.println("EXception in productSupplierCount: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rst_emp != null) {
                rst_emp.close();
            }
            if (stmt_emp != null) {
                stmt_emp.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
//        System.out.println("Employee Count : " + count);
        return count;
    }

    public static int verifyProd(String p_id) throws SQLException {
        Connection conn = null;
        Statement stmt_prod = null;
        ResultSet rst_prod = null;
        int count = -1;
        try {
            conn = getConnection();
            String sql_query = "";
            sql_query = "select count(*) from products where PID = '" + p_id + "'";
            stmt_prod = conn.createStatement();
            rst_prod = stmt_prod.executeQuery(sql_query);
            if (rst_prod.next()) {
                count = rst_prod.getInt(1);
//                System.out.println("Product Count " + count);
            }


        } catch (Exception e) {
            System.out.println("EXception in productSupplierCount: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rst_prod != null) {
                rst_prod.close();
            }
            if (stmt_prod != null) {
                stmt_prod.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
//        System.out.println("Product Count " + count);
        return count;
    }

    public static int verifyCust(String c_id) throws SQLException {
        Connection conn = null;
        Statement stmt_prod = null;
        ResultSet rst_prod = null;
        int count = -1;
        try {
            conn = getConnection();
            String sql_query = "";
            sql_query = "select count(*) from customers where CID = '" + c_id + "'";


            stmt_prod = conn.createStatement();
            rst_prod = stmt_prod.executeQuery(sql_query);
            if (rst_prod.next()) {
                count = rst_prod.getInt(1);
                System.out.println("Customers Count " + count);
            }


        } catch (Exception e) {
            System.out.println("EXception in verifyCust: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rst_prod != null) {
                rst_prod.close();
            }
            if (stmt_prod != null) {
                stmt_prod.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        System.out.println("Customers Count " + count);
        return count;
    }

    public static int StockVerification(String p_id) throws SQLException {
        System.out.println("Veryfing stock");
        Connection conn_stock = null;
//        PreparedStatement pstmt = null;
        Statement stmt_stock = null;
        ResultSet rst_stock = null;
//        RBMSContainer rbmscontainer = new RBMSContainer();
        try {
            OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
            ds.setURL(RBMSDAO.URL);
            conn_stock = ds.getConnection(RBMSDAO.USERID, RBMSDAO.PASSWORD);
            stmt_stock = conn_stock.createStatement();
            String sql_query = "select QOH from Products where PID = '" + p_id + "'";
            System.out.println("URL : " + sql_query);
            rst_stock = stmt_stock.executeQuery(sql_query);
//            System.out.println("result set "+ rst_stock.toString());
            if (rst_stock.next()) {
                RBMSDAO.setStock(rst_stock.getInt(1));
                System.out.println("Stock is " + rst_stock.getInt(1));

//                return Stock;
            }

        } catch (Exception e) {
            System.out.println("EXception in StockVerification: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rst_stock != null) {
                rst_stock.close();
            }
            if (stmt_stock != null) {
                stmt_stock.close();
            }
            if (conn_stock != null) {
                conn_stock.close();
            }
            fetchTriggerException();
        }
//        System.out.println("Available Stock " + RBMSDAO.getStock());
        return RBMSDAO.getStock();
    }

    public static void addPurchase(String Package_name, String Function_name,
            String e_id, String p_id, String c_id, int pur_qty, int place_holder_count) throws SQLException {
//        int suppliedby = RBMSDAO.productSupplierCount(p_id);
//        if (suppliedby <= 0) {
//            System.out.println("Error:  Invalid product");
//        } else {
            int current_stock = RBMSDAO.StockVerification(p_id);
            if (current_stock >= pur_qty) {
                Connection conn = null;
//                System.out.println("Stock : " + current_stock + " And Purchase Quantity : " + pur_qty);
//                System.out.println("Stock : " + RBMSDAO.getStock());
                conn = getConnection();
                String remarks = "";
                String URL = "";
                CallableStatement cs = null;
                ResultSet rs_addp = null;

                try {
                    cs = conn.prepareCall(RBMSDAO.getProcedureCall(Package_name, Function_name, place_holder_count));
//                    System.out.println("Procedure Call " + RBMSDAO.getProcedureCall(Package_name, Function_name, place_holder_count));
                    // register the out parameter (the first parameter)
                    cs.setString(1, e_id);
                    cs.setString(2, p_id);
                    cs.setString(3, c_id);
                    cs.setInt(4, pur_qty);
                    cs.registerOutParameter(5, OracleTypes.INTEGER);
                    // execute and retrieve the result set
                    // int executeStatus = cs.executeUpdate();
                    cs.executeQuery();
//                rs_addp = (ResultSet) cs.getObject(5);
                    int executeStatus = cs.getInt(5);
//                int executeStatus = cs.executeUpdate();

                    System.out.println("Execute Status after purchase : " + executeStatus);
                    if (executeStatus == 100) {
                        remarks = fetchActivityLogs(conn);
                        if (remarks != null) {
                            System.out.println("Remarks : " + remarks);
//                            System.out.println("Balle Balle ");
                        }
                    } else if (executeStatus == -100) {
                        System.out.println("Error: Insufficient Quantity in Stock.");
                    }

                } catch (SQLException se) {
                    System.err.println(se.getMessage());
                } catch (Exception e) {
                    System.out.println("Exception : " + e.getMessage());
                } finally {
                    //close the result set, statement, and the connection
                    if (cs != null) {
                        cs.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                    fetchTriggerException();
                }
            } else {
//                System.out.println("Available Stock : " + current_stock + " And Purchase Quantity : " + pur_qty);
                System.out.println("Information: Purchase Rejected.");
                System.out.println("Exception: Not Sufficient quantity in Stock.");
            }
//        }
    }

    public static String fetchActivityLogs(Connection conn) {

        String remarks = null;
        int logId = 0;
        try {
            Statement stmt = null;
            ResultSet rst = null;
            try {
                System.out.println("1111");
                String query = "select TLOG#,REMARKS from TRIGGER_INFO_LOGS where STATUS = 'UNREAD'";
//                System.out.println("URL : " + query);
                stmt = conn.createStatement();
//                System.out.println(" Going to execute");
                rst = stmt.executeQuery(query);
//                System.out.println("After execution");
                while (rst != null && rst.next()) {
                    logId = rst.getInt(1);
                    remarks = rst.getString(2);
                    System.out.println("Remarks: " + remarks);
                    Statement stmt_u = null;
                    try {
//                        System.out.println("Updating TRIGGER_INFO_LOGS after READ");
                        String updatequery = "Update TRIGGER_INFO_LOGS set STATUS = 'READ' where TLOG# = " + logId + "";
//                        System.out.println("Trigger Logs Update Query " + updatequery);
                        stmt_u = conn.createStatement();
//                        System.out.println(" Going to execute with input " + logId);
                        int status = stmt_u.executeUpdate(updatequery);
//                        if (status == 1) {
//                            System.out.println("TRIGGER_INFO_LOGS Updated Successfully");
//                        } else {
//                            System.out.println("Error in TRIGGER_INFO_LOGS Update");
//                        }
                    } catch (Exception e) {
                        System.out.println("Exception in update trigger logs : " + e.getMessage());
                    } finally {
                        if (stmt_u != null) {
                            stmt_u.close();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception in FETCHING TRIGGER LOGS: " + e.getMessage());
            } finally {
                if (rst != null) {
                    rst.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }



        } catch (Exception e) {
            System.out.println("EXception : " + e.getMessage());
        }
        return remarks;
    }

    public static void addProduct(String Package_name, String Function_name,
            String p_name, int p_qoh, int p_qoh_thresh, double p_original_price, double p_discnt_rate, int place_holder_count) throws SQLException {
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            conn = getConnection();
            cs = conn.prepareCall(RBMSDAO.getProcedureCall(Package_name, Function_name, place_holder_count));
//        cs.setString(1, p_id);
            cs.setString(1, p_name);
            cs.setInt(2, p_qoh);
            cs.setInt(3, p_qoh_thresh);
            cs.setDouble(4, p_original_price);
            cs.setDouble(5, p_discnt_rate);
            cs.registerOutParameter(6, OracleTypes.INTEGER);
            // execute and retrieve the result set
            rs = cs.executeQuery();
//            int executeStatus = rs.getInt(6);
//                if(executeStatus == 200){
//                    System.out.println("Record Successfully Inserted in the Database");
//                }else{
//                   System.out.println("Error in Insertion of the record");
//                }

        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        } finally {
            //close the result set, statement, and the connection
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (conn != null) {
                conn.close();
            }
            fetchTriggerException();
        }
    }
//  

    /**
     * @return the suppliedBy
     */
    public static int getSuppliedBy() {
        return suppliedBy;
    }

    /**
     * @param aSuppliedBy the suppliedBy to set
     */
    public static void setSuppliedBy(int aSuppliedBy) {
        suppliedBy = aSuppliedBy;
    }

    public static void fetchTriggerException() throws SQLException {
        Connection conn_trigger_exception = null;
        Statement stmt_trigger_exception = null;
        ResultSet rst_trigger_exception = null;
        int readStatus = -1;
        try {
            conn_trigger_exception = getConnection();
            String sql_query = "select REMARKS from TRIGGER_EXCEPTION";
            stmt_trigger_exception = conn_trigger_exception.createStatement();
            rst_trigger_exception = stmt_trigger_exception.executeQuery(sql_query);
            while (rst_trigger_exception.next()) {
                readStatus++;
                System.out.println("Error : " + rst_trigger_exception.getString("REMARKS"));
            }
        } catch (Exception e) {
            System.out.println("EXception in fetchTriggerException: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rst_trigger_exception != null) {
                rst_trigger_exception.close();
            }
            if (stmt_trigger_exception != null) {
                stmt_trigger_exception.close();
            }
            if (conn_trigger_exception != null) {
                conn_trigger_exception.close();
            }
            if (readStatus > 0) {
                clearTriggerException();
            }
        }
    }

    public static void clearTriggerException() throws SQLException {
        Connection conn_trigger_exception = null;
        Statement stmt_trigger_exception = null;
        ResultSet rst_trigger_exception = null;

        try {
            conn_trigger_exception = getConnection();
            String sql_query = "delete from TRIGGER_EXCEPTION";
            stmt_trigger_exception = conn_trigger_exception.createStatement();
            int remove_status = stmt_trigger_exception.executeUpdate(sql_query);
        } catch (Exception e) {
            System.out.println("EXception in clearTriggerException: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rst_trigger_exception != null) {
                rst_trigger_exception.close();
            }
            if (stmt_trigger_exception != null) {
                stmt_trigger_exception.close();
            }
            if (conn_trigger_exception != null) {
                conn_trigger_exception.close();
            }
        }
    }
}
