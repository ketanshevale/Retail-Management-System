set echo on
SET SERVEROUTPUT ON

drop view monthly_sale_view;
create view monthly_sale_view AS select A.PID AS PID,PNAME,QTY,TO_CHAR(B.PTIME,'YYYY') AS PYear, 
TO_CHAR(B.PTIME,'MONTH') AS PMonth,
TOTAL_PRICE from products A INNER JOIN purchases B on A.PID = B.PID;

-- RBMS package decelartion Start
-- drop package package_rbms;
create or replace package package_rbms as
type ref_cursor_employees is ref cursor;
type ref_cursor_customers is ref cursor;
type ref_cursor_products is ref cursor;
type ref_cursor_purchases is ref cursor;
type ref_cursor_suppliers is ref cursor;
type ref_cursor_supply is ref cursor;
type ref_cursor_monthly_report is ref cursor;
status boolean;
-- employees function start
function getemployees
return ref_cursor_employees;
-- employees function end
-- customers function start
function getcustomers
return ref_cursor_customers;
-- customers function end
-- products function start
function getproducts
return ref_cursor_products;
-- products function end
-- purchases function start
function getpurchases
return ref_cursor_purchases;
-- purchases function end
-- suppliers function start
function getsuppliers
return ref_cursor_suppliers;
-- suppliers function end
-- supply function start
function getsupply
return ref_cursor_supply;
-- supply function end
-- report_monthly_sale procedure start
function getmonthly_sale(prod_id in products.PID%TYPE)
return ref_cursor_monthly_report;
-- report_monthly_sale procedure end
-- add_Purchase procedure start
PROCEDURE addpurchase(
e_id in purchases.EID%TYPE,
p_id in purchases.PID%TYPE,
c_id in purchases.CID%TYPE,
pur_qty in purchases.QTY%TYPE,
pur_update_status out number);
-- add_Purchase procedure end
-- add_Products procedure start
procedure addproducts
(p_name in products.PNAME%TYPE,
p_qoh in products.QOH%TYPE,
p_qoh_thresh in products.QOH_THRESHOLD%TYPE,
p_original_price in products.ORIGINAL_PRICE%TYPE,
p_discnt_rate in products.DISCNT_RATE%TYPE,
pro_update_status out number);
-- add_Products procedure end
end;
/
-- RBMS package decelartion END
show errors

-- RBMS package definition start
create or replace package body package_rbms as
-- employees function start 
function getemployees
return ref_cursor_employees is rc_e ref_cursor_employees;
begin
/* Open a ref cursor for a given query */
open rc_e for select * from employees order by EID;
return rc_e;
end;/* END of employees function */
-- employees function end
-- customers function start
function getcustomers
return ref_cursor_customers is rc_c ref_cursor_customers;
begin
/* Open a ref cursor for a given query */
open rc_c for select * from customers order by CID;
return rc_c;
end;/* END of customers function */
-- customers function end
-- products function start
function getproducts
return ref_cursor_products is rc_products ref_cursor_products;
begin
/* Open a ref cursor for a given query */
open rc_products for select * from products order by PID;
return rc_products;
end;/* END of products function */
-- customers function end
-- purchases function start
function getpurchases
return ref_cursor_purchases is rc_p ref_cursor_purchases;
begin
/* Open a ref cursor for a given query */
open rc_p for select * from purchases order by PUR#;
return rc_p;
end;/* END of purchases function */
-- purchases function END
-- suppliers function start
function getsuppliers
return ref_cursor_suppliers is rc_suppliers ref_cursor_suppliers;
begin
/* Open a ref cursor for a given query */
open rc_suppliers for select * from suppliers order by SID;
return rc_suppliers;
end;/* END of suppliers function */
-- suppliers function END
-- supply function START
function getsupply
return ref_cursor_supply is rc_supply ref_cursor_supply;
begin
/* Open a ref cursor for a given query */ 
-- open rc_supply for select SUP#,PID,SID,to_date(sdate,'DD-MON-YYYY'),QUANTITY from supply order by SUP#;
open rc_supply for select * from supply order by SUP#;
-- END IF;
return rc_supply;
end;/* END of supply function */
-- supply function END

-- report_monthly_sale function start
function getmonthly_sale(prod_id in products.PID%TYPE)
return ref_cursor_monthly_report is rc_monthly_report ref_cursor_monthly_report;
INVALID_PID_EXCEPTION EXCEPTION;
begin
IF(prod_id is not null) then
    open rc_monthly_report for 
    select PYear, SUBSTR(PMonth,0,3), PNAME, SUM(QTY) ,SUM(TOTAL_PRICE), 
    ROUND(SUM(TOTAL_PRICE)/SUM(QTY),2) AS AVERAGE_SALE from 
    monthly_sale_view 
    where PID = prod_id group by PYear, PMonth, PNAME;
    status := true;
ELSE
status := false;
raise INVALID_PID_EXCEPTION;
END IF;
return rc_monthly_report;
EXCEPTION 
    WHEN NO_DATA_FOUND THEN 
        dbms_output.put_line('No Data Found');
        INSERT INTO TRIGGER_EXCEPTION VALUES('ORA-1403','Caught raised exception NO_DATA_FOUND',CURRENT_TIMESTAMP);
    WHEN INVALID_PID_EXCEPTION THEN
        raise_application_error (-20000,'Invalid Product PID');
        INSERT INTO TRIGGER_EXCEPTION VALUES('-20000','Caught raised exception : Invalid Product PID Exception',CURRENT_TIMESTAMP);
        dbms_output.put_line('Invalid Product PID');
end; /*END of report_monthly_sale_view procedure */
-- report_monthly_sale procedure end

-- add_Purchase procedure -- NEW start
PROCEDURE addpurchase(
e_id in purchases.EID%TYPE,
p_id in purchases.PID%TYPE,
c_id in purchases.CID%TYPE,
pur_qty in purchases.QTY%TYPE,
pur_update_status out number) IS
product_unit_price purchases.total_price%TYPE;
product_qoh products.QOH%TYPE;
LOW_QOH_EXCEPTION EXCEPTION;
BEGIN
select qoh into product_qoh from products where pid = p_id;
IF product_qoh >= pur_qty THEN
    SELECT ROUND(ORIGINAL_PRICE*(1-DISCNT_RATE),2) into product_unit_price from products WHERE PID = p_id;
    INSERT INTO purchases(pur#,eid,pid,cid,qty,ptime,total_price) values (pur_seq.nextval, e_id, p_id, c_id, pur_qty,CURRENT_TIMESTAMP, product_unit_price*pur_qty);
    pur_update_status := 100;
-- ELSE
--     pur_update_status := -100;
--     raise LOW_QOH_EXCEPTION;
--     dbms_output.put_line('Insufficient Quantity in Stock.');
END IF;
-- EXCEPTION 
-- WHEN LOW_QOH_EXCEPTION then
--     INSERT INTO TRIGGER_EXCEPTION VALUES('-20001','Caught raised exception Insufficient Quantity in Stock',CURRENT_TIMESTAMP);
--     raise_application_error (-200011,'Insufficient Quantity in Stock.');
-- WHEN TOO_MANY_ROWS THEN
--     dbms_output.put_line('Your SELECT statement retrieved multiple rows.');
--     INSERT INTO TRIGGER_EXCEPTION VALUES('ORA-04122','Your SELECT statement retrieved multiple rows.',CURRENT_TIMESTAMP);
COMMIT;
END;
-- add_Purchase procedure -- NEW end
-- add_Products procedure start
procedure addproducts
(p_name in products.PNAME%TYPE,
p_qoh in products.QOH%TYPE,
p_qoh_thresh in products.QOH_THRESHOLD%TYPE,
p_original_price in products.ORIGINAL_PRICE%TYPE,
p_discnt_rate in products.DISCNT_RATE%TYPE,
pro_update_status out number) IS
-- pid_nextvalue products.log#%TYPE;
BEGIN
INSERT INTO PRODUCTS(PID,PNAME,QOH,QOH_THRESHOLD,ORIGINAL_PRICE,DISCNT_RATE) 
values('p'||LPAD(pid_seq.nextval,3,0),p_name,p_qoh,p_qoh_thresh,p_original_price,p_discnt_rate);
-- values('p'||LPAD(pid_seq.nextval,3,0),p_name,p_qoh,p_qoh_thresh,p_original_price,p_discnt_rate);
pro_update_status := 200;
COMMIT;
EXCEPTION
WHEN DUP_VAL_ON_INDEX THEN
pro_update_status := -200;
dbms_output.put_line('EXCEPTION - Cannot Insert Record with duplicate PID');
INSERT INTO TRIGGER_EXCEPTION VALUES('ORA-00001','Cannot Insert Record with duplicate PID',CURRENT_TIMESTAMP); 
END;
-- add_Products procedure end
end; /* FINAL END */
/
show errors 
-- RBMS package definition END

