-- It will be triggered after the insertion of the tuple into the purchase table
set echo on
SET SERVEROUTPUT ON
create or replace trigger update_log_after_purcshase
AFTER INSERT ON PURCHASES FOR EACH ROW
DECLARE
begin
INSERT INTO logs values (logseq.nextval, user, CURRENT_TIMESTAMP, 'PURCHASES', 'INSERT', :new.pur#);
END;
/
show errors

-- It will be triggered after the update of QOH attribute of the purchase table.
create or replace trigger update_log_after_prod_update
AFTER UPDATE OF QOH ON PRODUCTS FOR EACH ROW
DECLARE
begin
INSERT INTO logs values (logseq.nextval, user, CURRENT_TIMESTAMP, 'PRODUCTS', 'UPDATE', :new.pid);
END;
/
show errors

-- It will be triggered after the update of QOH attribute of the purchase table.
create or replace trigger update_log_after_prod_insert
AFTER INSERT ON PRODUCTS FOR EACH ROW
DECLARE
begin
INSERT INTO logs values (logseq.nextval, user, CURRENT_TIMESTAMP, 'PRODUCTS', 'INSERT', :new.pid);
END;
/
show errors

-- It will be triggered after the update of visits_made attribute of the customers table.
create or replace trigger update_log_after_cust_visit
AFTER UPDATE OF visits_made ON CUSTOMERS FOR EACH ROW
DECLARE
begin
INSERT INTO logs values (logseq.nextval, user, CURRENT_TIMESTAMP, 'CUSTOMERS', 'UPDATE', :new.cid);
END;
/
show errors

-- It will be triggered after the insertion of a tuple in the supply table
create or replace trigger update_log_after_supply
AFTER INSERT ON SUPPLY FOR EACH ROW
DECLARE
begin
INSERT INTO logs values (logseq.nextval, user, CURRENT_TIMESTAMP, 'SUPPLY', 'INSERT', :new.sup#);
END;
/
show errors

SET SERVEROUTPUT ON
CREATE OR REPLACE TRIGGER check_qoh
AFTER INSERT ON PURCHASES FOR EACH ROW
DECLARE 
product_new_qoh number(10);
-- product_old_qoh number(10);
product_qoh_threshold number(10);
new_sid varchar2(3);
m number(10);
new_quantity_supply number(10);
qoh_after_supply number (10);
count1 varchar2(20);
cust_last_visit_date date;
product_update_info varchar2(255);
supply_info varchar2(255);
new_qoh_info varchar2(255);
-- INSUF_QOH_EXCEPTION EXCEPTION;
REC_MISSING_EXCEPTION EXCEPTION;
-- QOH_UPDATE_FAILED_EX EXCEPTION;
-- CUST_UPDATE_FAILED_EX EXCEPTION;
BEGIN
update products set products.qoh = products.qoh - :new.qty where products.pid = :new.pid;
-- IF SQL%NOTFOUND THEN
--     raise QOH_UPDATE_FAILED_EX;
-- ELSE
    select products.qoh,products.qoh_threshold INTO product_new_qoh,product_qoh_threshold FROM products where pid = :new.pid;
    IF(product_new_qoh < product_qoh_threshold) THEN 
        product_update_info := 'qoh of the product '|| :new.pid ||' is below the required threshold of '|| product_qoh_threshold ||'. So a new supply is required';
        dbms_output.put_line('Product Update ' || product_update_info);
        INSERT INTO TRIGGER_INFO_LOGS VALUES(triggerinfologseq.nextval,'Update','Products',CURRENT_TIMESTAMP,product_update_info,'UNREAD');
        select SID INTO new_sid FROM (SELECT SID from supply where PID =:new.pid order by SID) where ROWNUM = 1;
        IF(new_sid is not null) THEN
            m := :new.qty;
            new_quantity_supply := m + 10 + product_qoh_threshold;
            INSERT INTO supply values(sup_seq.nextval,:new.pid,new_sid,CURRENT_TIMESTAMP,new_quantity_supply);
            supply_info := 'A new order for '|| :new.pid ||' is placed from supplier ' || sup_seq.currval;
            dbms_output.put_line('supply_info ' || supply_info);
            INSERT INTO TRIGGER_INFO_LOGS VALUES(triggerinfologseq.nextval,'Insert','Supply',CURRENT_TIMESTAMP,supply_info,'UNREAD');
            update products set qoh = qoh + new_quantity_supply where pid = :new.pid; 
            select qoh INTO qoh_after_supply from products where pid = :new.pid;		
            new_qoh_info := 'New QOH of the product is '|| qoh_after_supply;
            dbms_output.put_line('new_qoh_info ' || new_qoh_info);
            INSERT INTO TRIGGER_INFO_LOGS VALUES(triggerinfologseq.nextval,'Update','Products',CURRENT_TIMESTAMP,new_qoh_info,'UNREAD');
         ELSE
            raise REC_MISSING_EXCEPTION;
         END IF;
--     ELSE
--     raise INSUF_QOH_EXCEPTION;
    END IF;
    select last_visit_date INTO cust_last_visit_date from customers where cid = :new.cid;
    IF(:new.ptime > cust_last_visit_date) THEN
        update customers set visits_made = visits_made +1, last_visit_date = :new.ptime  where cid=:new.cid;
--         IF SQL%NOTFOUND THEN
--             raise CUST_UPDATE_FAILED_EX;
--         END IF;
    END IF;
-- END IF;
EXCEPTION 
-- WHEN INSUF_QOH_EXCEPTION THEN
--     dbms_output.put_line('Insufficient Quantity in Stock');
--     raise_application_error (-20003,'Insufficient Quantity in Stock');
--     INSERT INTO TRIGGER_EXCEPTION VALUES('-20001','Caught raised exception Insufficient Quantity in Stock',CURRENT_TIMESTAMP);
WHEN REC_MISSING_EXCEPTION THEN
    dbms_output.put_line('Record Missing in Supplier Table for the entered product');
    raise_application_error (-20004,'Record Missing in Supplier Table for the entered product');
    INSERT INTO TRIGGER_EXCEPTION VALUES('-20004','Record Missing in Supplier Table for the entered product',CURRENT_TIMESTAMP);
-- WHEN QOH_UPDATE_FAILED_EX THEN
--     dbms_output.put_line('Error in updating QOH');
--     raise_application_error (-20005,'Error in updating QOH');
--     INSERT INTO TRIGGER_EXCEPTION VALUES('-20005','Error in updating QOH',CURRENT_TIMESTAMP);
-- WHEN CUST_UPDATE_FAILED_EX THEN
--     dbms_output.put_line('Error in updating visits made of customer');
--     raise_application_error (-20006,'Error in updating visits made of customer');
--     INSERT INTO TRIGGER_EXCEPTION VALUES('-20006','Error in updating visits made of customer',CURRENT_TIMESTAMP);
END;
/
show errors