
-- Tables START --
create table employees(eid char(3) primary key,ename varchar2(15),telephone# char(12));
create table customers(cid char(4) primary key,cname varchar2(15),telephone# char(12),visits_made number(4),last_visit_date date);
 
create table products(pid char(4) primary key,pname varchar2(15),qoh number(5),qoh_threshold number(4),original_price number(6,2),
discnt_rate number(3,2) check(discnt_rate between 0 and 0.8));
create table purchases(pur# number(6) primary key,eid char(3) references employees(eid),pid char(4) references products(pid),
cid char(4) references customers(cid),qty number(5),ptime date,total_price number(7,2));
create table suppliers (sid char(2) primary key,sname varchar2(15) not null unique,city varchar2(15),telephone# char(12));

create table supply (sup# number(4) primary key,pid char(4) references products(pid),sid char(2) references suppliers(sid),sdate date,
quantity number(5));

create table logs (log# number(5) primary key,who varchar2(12) not null,
otime date not null,table_name varchar2(20) not null,operation varchar2(6) not null,key_value varchar2(6));

create table TRIGGER_INFO_LOGS (tlog# number(5) not null PRIMARY KEY,
ACTIVITY varchar2(20) not null,
Activity_Table varchar2(20) not null,
CREATION_TIME date not null,
REMARKS varchar2(255) not null,
STATUS varchar2(6) not null);

create table TRIGGER_EXCEPTION (EXID varchar2(10) not null,REMARKS varchar2(255) not null,RAISE_TIME date not null);
-- Tables END --

-- CREATE SEQUENCES
create sequence eid_seq start with 1 increment by 1 order;
create sequence cid_seq start with 1 increment by 1 order;
create sequence pid_seq start with 1 increment by 1 order;
create sequence sid_seq start with 1 increment by 1 order;
create sequence pur_seq start with 100001 increment by 1 order;
create sequence sup_seq start with 1001 increment by 1 order;
create sequence logseq start with 10001 increment by 1 order;
create sequence triggerinfologseq start with 1 increment by 1 order;

-- Table values start --
insert into employees values ('e'||LPAD(eid_seq.nextval,2,0), 'Peter', '666-555-1234');
insert into employees values ('e'||LPAD(eid_seq.nextval,2,0), 'David', '777-555-2341');
insert into employees values ('e'||LPAD(eid_seq.nextval,2,0), 'Susan', '888-555-3412');
insert into employees values ('e'||LPAD(eid_seq.nextval,2,0), 'Anne', '666-555-4123');
insert into employees values ('e'||LPAD(eid_seq.nextval,2,0), 'Mike', '444-555-4231');

insert into customers values ('c'||LPAD(cid_seq.nextval,3,0), 'Kathy', '666-555-4567', 3, '12-OCT-15');
insert into customers values ('c'||LPAD(cid_seq.nextval,3,0), 'John', '888-555-7456', 1, '08-OCT-15');
insert into customers values ('c'||LPAD(cid_seq.nextval,3,0), 'Chris', '666-555-6745', 3, '18-SEP-15');
insert into customers values ('c'||LPAD(cid_seq.nextval,3,0), 'Mike', '999-555-5674', 1, '20-OCT-15');
insert into customers values ('c'||LPAD(cid_seq.nextval,3,0), 'Mike', '777-555-4657', 2, '30-AUG-15');
insert into customers values ('c'||LPAD(cid_seq.nextval,3,0), 'Connie', '777-555-7654', 2, '16-OCT-15');
insert into customers values ('c'||LPAD(cid_seq.nextval,3,0), 'Katie', '888-555-6574', 1, '12-OCT-15');
insert into customers values ('c'||LPAD(cid_seq.nextval,3,0), 'Joe', '666-555-5746', 1, '18-OCT-15');

insert into products values ('p'||LPAD(pid_seq.nextval,3,0), 'stapler', 60, 20, 9.99, 0.1);
insert into products values ('p'||LPAD(pid_seq.nextval,3,0), 'TV', 6, 5, 249, 0.15);
insert into products values ('p'||LPAD(pid_seq.nextval,3,0), 'camera', 20, 5, 148, 0.2);
insert into products values ('p'||LPAD(pid_seq.nextval,3,0), 'pencil', 100, 10, 0.99, 0.0);
insert into products values ('p'||LPAD(pid_seq.nextval,3,0), 'chair', 10, 8, 12.98, 0.3);
insert into products values ('p'||LPAD(pid_seq.nextval,3,0), 'lamp', 10, 6, 19.95, 0.1);
insert into products values ('p'||LPAD(pid_seq.nextval,3,0), 'tablet', 50, 10, 149, 0.2);
insert into products values ('p'||LPAD(pid_seq.nextval,3,0), 'computer', 5, 3, 499, 0.3);
insert into products values ('p'||LPAD(pid_seq.nextval,3,0), 'powerbank', 20, 5, 49.95, 0.1);
insert into products values ('p'||LPAD(pid_seq.nextval,3,0), 'tot', 20, 5, 49.95, 0.1);

insert into suppliers values('s'||sid_seq.nextval,'ABL','New York','666-555-4321');
insert into suppliers values('s'||sid_seq.nextval,'DHL','Syracuse','777-555-1234');
insert into suppliers values('s'||sid_seq.nextval,'FedeL','Broonx','888-555-7878');
insert into suppliers values('s'||sid_seq.nextval,'Rebel','Rochestor','999-777-5678');
insert into suppliers values('s'||sid_seq.nextval,'GDU','Binghamton','607-555-4321');

insert into supply values(sup_seq.nextval,'p001','s1',to_date('10-OCT-2015 10:34:30', 'DD-MON-YYYY HH24:MI:SS'),60);
insert into supply values(sup_seq.nextval,'p002','s1',to_date('09-AUG-2015 08:44:30', 'DD-MON-YYYY HH24:MI:SS'),6);
insert into supply values(sup_seq.nextval,'p003','s2',to_date('10-SEP-2015 12:44:30', 'DD-MON-YYYY HH24:MI:SS'),20);
insert into supply values(sup_seq.nextval,'p004','s3',to_date('06-OCT-2015 12:44:30', 'DD-MON-YYYY HH24:MI:SS'),100);
insert into supply values(sup_seq.nextval,'p005','s4',to_date('21-AUG-2015 12:44:30', 'DD-MON-YYYY HH24:MI:SS'),10);
insert into supply values(sup_seq.nextval,'p006','s5',to_date('15-AUG-2015 12:44:30', 'DD-MON-YYYY HH24:MI:SS'),10);
insert into supply values(sup_seq.nextval,'p007','s4',to_date('10-OCT-2015 12:44:30', 'DD-MON-YYYY HH24:MI:SS'),50);
insert into supply values(sup_seq.nextval,'p008','s5',to_date('15-SEP-2015 12:44:30', 'DD-MON-YYYY HH24:MI:SS'),5);
insert into supply values(sup_seq.nextval,'p009','s3',to_date('17-OCT-2015 12:44:30', 'DD-MON-YYYY HH24:MI:SS'),10);
insert into supply values(sup_seq.nextval,'p009','s2',to_date('18-OCT-2015 12:44:30', 'DD-MON-YYYY HH24:MI:SS'),5);
insert into supply values(sup_seq.nextval,'p009','s1',to_date('19-OCT-2015 12:44:30', 'DD-MON-YYYY HH24:MI:SS'),5);

insert into purchases values (pur_seq.nextval, 'e01', 'p002', 'c001', 1, to_date('12-AUG-2015 10:34:30', 'DD-MON-YYYY HH24:MI:SS'), 211.65);
insert into purchases values (pur_seq.nextval, 'e01', 'p003', 'c001', 1, to_date('20-SEP-2015 11:23:36', 'DD-MON-YYYY HH24:MI:SS'), 118.40);
insert into purchases values (pur_seq.nextval, 'e02', 'p004', 'c002', 5, to_date('08-OCT-2015 09:30:50', 'DD-MON-YYYY HH24:MI:SS'), 4.95);
insert into purchases values (pur_seq.nextval, 'e01', 'p005', 'c003', 2, to_date('23-AUG-2015 16:23:35', 'DD-MON-YYYY HH24:MI:SS'), 18.17);
insert into purchases values (pur_seq.nextval, 'e04', 'p007', 'c004', 1, to_date('20-OCT-2015 13:38:55', 'DD-MON-YYYY HH24:MI:SS'), 119.20);
insert into purchases values (pur_seq.nextval, 'e03', 'p008', 'c001', 1, to_date('12-OCT-2015 15:22:10', 'DD-MON-YYYY HH24:MI:SS'), 349.30);
insert into purchases values (pur_seq.nextval, 'e03', 'p006', 'c003', 2, to_date('10-SEP-2015 17:12:20', 'DD-MON-YYYY HH24:MI:SS'), 35.91);
insert into purchases values (pur_seq.nextval, 'e03', 'p006', 'c005', 1, to_date('16-AUG-2015 12:22:15', 'DD-MON-YYYY HH24:MI:SS'), 17.96);
insert into purchases values (pur_seq.nextval, 'e03', 'p001', 'c007', 1, to_date('12-OCT-2015 14:44:23', 'DD-MON-YYYY HH24:MI:SS'), 8.99);
insert into purchases values (pur_seq.nextval, 'e04', 'p002', 'c006', 1, to_date('19-SEP-2015 17:32:37', 'DD-MON-YYYY HH24:MI:SS'), 211.65);
insert into purchases values (pur_seq.nextval, 'e02', 'p004', 'c006', 10,to_date('16-OCT-2015 16:54:40', 'DD-MON-YYYY HH24:MI:SS'), 9.90);
insert into purchases values (pur_seq.nextval, 'e02', 'p008', 'c003', 2, to_date('18-SEP-2015 15:56:38', 'DD-MON-YYYY HH24:MI:SS'), 698.60);
insert into purchases values (pur_seq.nextval, 'e04', 'p006', 'c005', 2, to_date('30-AUG-2015 10:38:25', 'DD-MON-YYYY HH24:MI:SS'), 35.91);
insert into purchases values (pur_seq.nextval, 'e03', 'p009', 'c008', 3, to_date('18-OCT-2015 10:54:06', 'DD-MON-YYYY HH24:MI:SS'), 134.84);


-- Table values end --
