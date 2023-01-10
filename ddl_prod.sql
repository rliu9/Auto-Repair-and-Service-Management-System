CREATE TABLE DB_Users(
	username varchar2(15),
	password varchar2(15),
	role varchar2(15),
	PRIMARY KEY (username),
	CONSTRAINT role_check CHECK(role IN ('Admin','Manager','Receptionist','Mechanic','Customer'))
)

CREATE TABLE Service_Centers (
	scid char(8),
	address char(100),
	telephone char(10),
	min_wage float,
	max_wage float,
	saturdays char(1),
	PRIMARY KEY (scid),
  CONSTRAINT sc_phone_ten_digit CHECK(REGEXP_LIKE(telephone,'[[:digit:]]{10}')),
	CONSTRAINT saturdays_domain CHECK(saturdays IN ('Y','N'))
)

CREATE TABLE Employees(
	eid char(9),
	scid char(8),
	first_name varchar2(30),
	last_name varchar2(30),
	address varchar2(100),
	email varchar2(30),
	telephone char(10),
	title varchar2(15),
	username varchar2(15),
	PRIMARY KEY (eid,scid),
	FOREIGN KEY (scid) REFERENCES Service_Centers,
	FOREIGN KEY (username) REFERENCES DB_Users,
	CONSTRAINT eid_nine_digit CHECK(REGEXP_LIKE(eid,'[[:digit:]]{9}')),
	CONSTRAINT emp_phone_ten_digit CHECK(REGEXP_LIKE(telephone,'[[:digit:]]{10}')),
	CONSTRAINT title_role CHECK(title IN ('Manager','Receptionist','Mechanic'))
)

CREATE TABLE Services(
	service_id char(8),
	car_manuf char(8),
	duration int,
	service_name varchar2(120),
	subtype varchar2(40),
	schedule char(1),
	repair_service char(1),
	PRIMARY KEY (service_name, car_manuf),
	CONSTRAINT services_car_manufacturer_check CHECK(car_manuf IN ('Honda','Nissan','Toyota','Lexus','Infiniti')),
	CONSTRAINT service_subtype_one_of_six CHECK(subtype IN ('Schedule', 'Engine','Exhaust','Electrical','Transmission','Tire','Heating and AC')),
	CONSTRAINT service_schedule_domain CHECK(schedule IN ('N','A','B','C')),
	CONSTRAINT service_repair_domain CHECK(repair_service IN ('Y','N'))
)

CREATE TABLE Schedule_Prices(
	scid char(8),
	schedule char(1),
	price float,
	service_id char(8),
	duration int,
	car_manuf char(8),
	PRIMARY KEY (scid, schedule, car_manuf),
	FOREIGN KEY (scid) REFERENCES Service_Centers(scid),
	CONSTRAINT schedule_car_manufacturer_check CHECK(car_manuf IN ('Honda','Nissan','Toyota','Lexus','Infiniti')),
	CONSTRAINT schedule_table_domain CHECK(schedule IN ('A','B','C'))
)

-- CREATE TRIGGER schedule_service_id
-- BEFORE UPDATE ON Schedule_Prices
-- FOR EACH ROW
-- BEGIN
-- 	:new.service_id = :old.service_id;
-- 	:new.duration = :old.duration;
-- 	:new.car_manuf = :old.car_manuf;
-- END;

CREATE TABLE Offer(
	scid char(8),
	service_name varchar2(120),
	car_manuf char(8),
	price float,
	PRIMARY KEY (scid, service_name, car_manuf),
	FOREIGN KEY (scid) REFERENCES Service_Centers(scid),
	FOREIGN KEY (service_name, car_manuf) REFERENCES Services(service_name, car_manuf)
)

CREATE TABLE Contract_Emp(
	eid char(9),
	scid char(8),
	salary float,
	PRIMARY KEY (eid,scid),
	FOREIGN KEY (eid, scid) REFERENCES Employees(eid, scid) ON DELETE CASCADE
)

CREATE TABLE Hourly_Emp(
	eid char(9),
	scid char(8),
	hours_worked int,
	wage float,
	PRIMARY KEY (eid,scid),
	FOREIGN KEY (eid, scid) REFERENCES Employees(eid, scid) ON DELETE CASCADE,
	CONSTRAINT hours_cap CHECK(hours_worked <= 50)
)

CREATE TABLE Customers(
	scid char(8),
	cid int,
	first_name char(30),
	last_name char(30),
	address varchar2(100),
	email varchar2(30),
	telephone char(10),
	outstanding_balance char(1),
	active_customer char(1),
	num_vehicles int,
	username varchar2(15),
	FOREIGN KEY (scid) REFERENCES Service_Centers ON DELETE CASCADE,
	FOREIGN KEY (username) REFERENCES DB_Users,
	PRIMARY KEY (scid,cid),
	CONSTRAINT num_vehicles_non_negative CHECK(num_vehicles >= 0)
)

CREATE TRIGGER customer_username
BEFORE UPDATE OR INSERT ON Customers
FOR EACH ROW
DECLARE
cust_role varchar2(10);
not_customer EXCEPTION;
BEGIN
	 SELECT U.role INTO cust_role FROM DB_Users U WHERE U.username = :new.username;

	 IF cust_role NOT IN ('Customer') THEN
		RAISE not_customer;
	 END IF;

	 EXCEPTION
	 WHEN not_customer THEN
	 dbms_output.put_line('Given username is not a Customer username.');
	 raise_application_error(-20000, 'Given username is not a Customer username.');
	 WHEN NO_DATA_FOUND THEN
	 null;
END;

CREATE TABLE Cars(
	vin char(8),
	manuf varchar2(15),
	mileage int,
	year int,
	last_mant_sch char(1),
	PRIMARY KEY (vin),
	CONSTRAINT vin_eight_alnum CHECK(REGEXP_LIKE(vin,'[[:alnum:]]{8}')),
	CONSTRAINT last_mant_sch_check CHECK(last_mant_sch IN ('A','B','C')),
	CONSTRAINT car_manufacturer_check CHECK(manuf IN ('Honda','Nissan','Toyota','Lexus','Infiniti')),
	CONSTRAINT mileage_non_negative CHECK(mileage >= 0)
)

CREATE TABLE Owns(
	vin char(8) NOT NULL,
	scid char(8) NOT NULL,
	cid int NOT NULL,
	PRIMARY KEY (vin),
	FOREIGN KEY (vin) REFERENCES Cars ON DELETE CASCADE,
	FOREIGN KEY (scid,cid) REFERENCES Customers ON DELETE CASCADE
)

CREATE TRIGGER new_customer_defaults_to_inactive_and_no_outstanding_balance
BEFORE INSERT
ON Customers
FOR EACH ROW
BEGIN
	:new.active_customer := 'N';
	:new.outstanding_balance := 'N';
	:new.num_vehicles := 0;
END;

CREATE TRIGGER customer_active_field_consistent_with_cars
BEFORE UPDATE
ON Customers
FOR EACH ROW
DECLARE
manual_owner_active_update EXCEPTION;
BEGIN
	IF :new.num_vehicles = 0 THEN
		:new.active_customer := 'N';
	ELSE
		:new.active_customer := 'Y';
	END IF;
END;

CREATE TRIGGER car_insert_makes_customer_active
AFTER INSERT
ON Owns
FOR EACH ROW
BEGIN
	UPDATE Customers
		SET num_vehicles = num_vehicles+1
		WHERE scid=:new.scid AND cid=:new.cid;
END;

CREATE TRIGGER car_delete_checks_customer_active_status
AFTER DELETE
ON Owns
FOR EACH ROW
BEGIN
	UPDATE Customers
		SET num_vehicles = num_vehicles-1
		WHERE scid=:old.scid AND cid=:old.cid;
END;

CREATE TRIGGER car_update_cannot_change_owner
BEFORE UPDATE
ON Owns
FOR EACH ROW
DECLARE
owner_update EXCEPTION;
BEGIN
	IF :old.scid<>:new.scid OR :old.cid<>:new.cid THEN
		RAISE owner_update;
	END IF;

	EXCEPTION
	WHEN owner_update THEN
	dbms_output.put_line('You cannot change the owner of an existing vehicle. Delete and add new.');
	raise_application_error(-20000, 'You cannot change the owner of an existing vehicle. Delete and add new.');
END;

CREATE TABLE Appointments(
	apt_id char(8),
	scid char(8) NOT NULL,
	eid char(9) NOT NULL,
	vin char(8) NOT NULL,
	apt_date char(8),
	dotw int,
	start_time int,
	duration int,
	work_complete char(1),
	bill_paid char(1),
	total_amount int,
	PRIMARY KEY (apt_id),
	FOREIGN KEY (eid, scid) REFERENCES Hourly_Emp,
	FOREIGN KEY (vin) REFERENCES Cars(vin)
)

CREATE TRIGGER appointment_defaults
BEFORE INSERT
ON Appointments
FOR EACH ROW
DECLARE
current_date_time char(2);
sat char(1);
time_error EXCEPTION;
scheduling_error EXCEPTION;
hours_error EXCEPTION;
duration_error EXCEPTION;
BEGIN
	:new.dotw := to_char(TO_DATE(to_char(:new.apt_date), 'YYYYMMDD'), 'D');

	IF :new.start_time < 0 OR :new.start_time >= 24 THEN
		RAISE time_error;
	END IF;

	SELECT MOD(TO_NUMBER(TO_CHAR(localtimestamp AT TIME ZONE 'EST', 'HH24'))+1,24)
	INTO current_date_time
	FROM dual;

	SELECT SC.saturdays
	INTO sat
	FROM Service_Centers SC
	WHERE SC.scid = :new.scid;

	IF (:new.apt_date < TO_CHAR(localtimestamp, 'YYYYMMDD')) OR
	(:new.apt_date = TO_CHAR(localtimestamp, 'YYYYMMDD') AND
	:new.start_time <= current_date_time) THEN
		RAISE scheduling_error;
	END IF;

	IF (:new.dotw=1)
	OR (sat='N' AND :new.dotw=7)
	OR (sat='Y' AND :new.dotw=7 AND ((:new.start_time < 2) OR (:new.start_time > 4)))
	OR (:new.dotw<>1 AND :new.dotw<>7 AND ((:new.start_time > 11) OR (:new.start_time < 1)))
	THEN
		RAISE hours_error;
	END IF;

	IF (:new.dotw=7 AND (:new.start_time+:new.duration > 12)) OR
	(:new.dotw<>1 AND :new.dotw<>7 AND (:new.start_time+:new.duration > 20)) THEN
		RAISE duration_error;
	END IF;

	EXCEPTION
	WHEN time_error THEN
	dbms_output.put_line('Invalid time.');
	raise_application_error(-20000, 'Invalid time.');
	WHEN scheduling_error THEN
	dbms_output.put_line('You cannot make an appointment in the past.');
	raise_application_error(-20000, 'You cannot make an appointment in the past.');
	WHEN hours_error THEN
	dbms_output.put_line('Service center does not take appointments at the given time.');
	raise_application_error(-20000, 'Service center does not take appointments at the given time.');
	WHEN duration_error THEN
	dbms_output.put_line('Appointment time period exceeds closing time for service center.');
	raise_application_error(-20000, 'Appointment time period exceeds closing time for service center.');
	WHEN NO_DATA_FOUND THEN
	null;
END;

CREATE TABLE Schedule(
	apt_id char(8),
	service_name varchar2(120),
	car_manuf char(8),
	scid char(8),
	PRIMARY KEY (apt_id, service_name),
	FOREIGN KEY (apt_id) REFERENCES Appointments,
	FOREIGN KEY (service_name, car_manuf) REFERENCES Services,
	FOREIGN KEY (scid) REFERENCES Service_Centers(scid)
)

CREATE TABLE Day_Schedules(
	eid char(9),
	scid char(8),
	sch_date char(8),
	dotw int,
	slot_time int,
	slot char(1),
	PRIMARY KEY (eid,scid,sch_date,slot_time),
	FOREIGN KEY (eid, scid) REFERENCES Hourly_Emp(eid, scid)
)

CREATE TABLE Swaps(
	swap_id int,
	scid char(8),
	eid_requestor char(9),
	requestor_week int,
	requestor_day int,
	requestor_slot_start int,
	requestor_slot_end int,
	eid_requestee char(9),
	requestee_week int,
	requestee_day int,
	requestee_slot_start int,
	requestee_slot_end int,
	status char(1),
	PRIMARY KEY (swap_id),
	FOREIGN KEY (eid_requestor, scid) REFERENCES Hourly_Emp(eid, scid),
	FOREIGN KEY (eid_requestee, scid) REFERENCES Hourly_Emp(eid, scid)
)

CREATE TRIGGER title_role_contract
BEFORE UPDATE OR INSERT ON Contract_Emp
FOR EACH ROW
DECLARE
emp_role varchar(15);
not_contract_employee EXCEPTION;
BEGIN
	 SELECT E.title INTO emp_role FROM Employees E WHERE E.eid = :new.eid AND E.scid = :new.scid;
	 IF emp_role IN ('Mechanic') THEN
		RAISE not_contract_employee;
	 END IF;
	 EXCEPTION
	 WHEN not_contract_employee THEN
	 dbms_output.put_line('Employee is not a Contract Employee.');
	 raise_application_error(-20000, 'Employee is not a Contract Employee.');
	 WHEN NO_DATA_FOUND THEN
	 null;
END;

CREATE TRIGGER title_role_hourly
BEFORE UPDATE OR INSERT ON Hourly_Emp
FOR EACH ROW
DECLARE
emp_role varchar(15);
not_hourly_employee EXCEPTION;
BEGIN
	 SELECT E.title INTO emp_role FROM Employees E WHERE E.eid = :new.eid AND E.scid = :new.scid;

	 IF emp_role IN ('Manager','Receptionist') THEN
		RAISE not_hourly_employee;
	 END IF;

	 EXCEPTION
	 WHEN not_hourly_employee THEN
	 dbms_output.put_line('Employee is not an Hourly Employee.');
	 raise_application_error(-20000, 'Employee is not an Hourly Employee.');
	 WHEN NO_DATA_FOUND THEN
	 null;
END;

CREATE TRIGGER update_title_contract_to_hourly
BEFORE UPDATE
ON Employees
FOR EACH ROW
DECLARE
emp_eid char(9);
emp_scid char(8);
contract_to_hourly EXCEPTION;
BEGIN
	 SELECT CE.eid, CE.scid
	 INTO emp_eid, emp_scid
	 FROM Contract_Emp CE
	 WHERE CE.eid = :new.eid AND CE.scid = :new.scid;

	 IF (:old.title IN ('Manager','Receptionist') AND :new.title IN ('Mechanic') AND
	 emp_eid IS NOT NULL AND emp_scid IS NOT NULL) THEN
		RAISE contract_to_hourly;
	 END IF;

	 EXCEPTION
	 WHEN contract_to_hourly THEN
	 dbms_output.put_line('Deletion from Contract_Emp must precede update on employee title.');
	 raise_application_error(-20000, 'Deletion from Contract_Emp must precede update on employee title.');
	 WHEN NO_DATA_FOUND THEN
	 null;
END;

CREATE TRIGGER update_title_hourly_to_contract
BEFORE UPDATE
ON Employees
FOR EACH ROW
DECLARE
emp_eid char(9);
emp_scid char(8);
hourly_to_contract EXCEPTION;
BEGIN
	 SELECT HE.eid, HE.scid
	 INTO emp_eid, emp_scid
	 FROM Hourly_Emp HE
	 WHERE HE.eid = :new.eid AND HE.scid = :new.scid;

	 IF (:old.title IN ('Mechanic') AND :new.title IN ('Manager','Receptionist') AND
	 emp_eid IS NOT NULL AND emp_scid IS NOT NULL) THEN
		 RAISE hourly_to_contract;
	 END IF;

	 EXCEPTION
	 WHEN hourly_to_contract THEN
	 dbms_output.put_line('Deletion from Hourly_Emp must precede update on employee title.');
	 raise_application_error(-20000, 'Deletion from Hourly_Emp must precede update on employee title.');
	 WHEN NO_DATA_FOUND THEN
	 null;
END;

CREATE TRIGGER employee_username
BEFORE UPDATE OR INSERT ON Employees
FOR EACH ROW
DECLARE
emp_role varchar2(15);
not_correct_role EXCEPTION;
BEGIN
	 SELECT U.role INTO emp_role FROM DB_Users U WHERE U.username = :new.username;

	 IF emp_role <> :new.title THEN
		RAISE not_correct_role;
	 END IF;

	 EXCEPTION
	 WHEN not_correct_role THEN
	 dbms_output.put_line('Given username is not of same role as Employee.');
	 raise_application_error(-20000, 'Given username is not of same role as Employee.');
	 WHEN NO_DATA_FOUND THEN
	 null;
END;

CREATE TRIGGER one_manager
BEFORE INSERT OR UPDATE
ON Employees
FOR EACH ROW
DECLARE
manager_id char(9);
manager_exists EXCEPTION;
BEGIN
	 SELECT E.eid
	 INTO manager_id
	 FROM Employees E
	 WHERE E.scid = :new.scid AND E.title = 'Manager';

	 IF (:new.title IN ('Manager') AND manager_id IS NOT NULL) THEN
		 RAISE manager_exists;
	 END IF;

	 EXCEPTION
	 WHEN manager_exists THEN
	 dbms_output.put_line('There is already a manager at this store.');
	 raise_application_error(-20000, 'There is already a manager at this store.');
	 WHEN NO_DATA_FOUND THEN
	 null;
END;

CREATE TRIGGER one_receptionist
BEFORE INSERT OR UPDATE
ON Employees
FOR EACH ROW
DECLARE
receptionist_id char(9);
receptionist_exists EXCEPTION;
BEGIN
	 SELECT E.eid
	 INTO receptionist_id
	 FROM Employees E
	 WHERE E.scid = :new.scid AND E.title = 'Receptionist';

	 IF (:new.title IN ('Receptionist') AND receptionist_id IS NOT NULL) THEN
		 RAISE receptionist_exists;
	 END IF;

	 EXCEPTION
	 WHEN receptionist_exists THEN
	 dbms_output.put_line('There is already a receptionist at this store.');
	 raise_application_error(-20000, 'There is already a receptionist at this store.');
	 WHEN NO_DATA_FOUND THEN
	 null;
END;

CREATE TRIGGER hourly_employee_wage_check
BEFORE UPDATE OR INSERT ON Hourly_Emp
FOR EACH ROW
DECLARE
scid_min_wage float;
scid_max_wage float;
wage_inconsistent EXCEPTION;
BEGIN
	 SELECT SC.min_wage, SC.max_wage
	 INTO scid_min_wage, scid_max_wage
	 FROM Service_Centers SC
	 WHERE SC.scid = :new.scid;

	 IF ((:new.wage < scid_min_wage) OR (:new.wage > scid_max_wage)) THEN
		RAISE wage_inconsistent;
	 END IF;

	 EXCEPTION
	 WHEN wage_inconsistent THEN
	 dbms_output.put_line('Hourly employee wage not within service center min/max wages.');
	 raise_application_error(-20000, 'Hourly employee wage not within service center min/max wages.');
	 WHEN NO_DATA_FOUND THEN
	 null;
END;
