#1
SELECT
`id` AS 'No.', 
`first_name` AS 'First Name',
`last_name` AS 'Last Name',
`job_title` AS 'Job Title'
FROM `employees` AS e 
ORDER BY `id`;

#2
SELECT id,
CONCAT_WS(' ', first_name, last_name) AS 'full_name',
job_title,
salary
FROM employees
WHERE salary > 1000;

 #3
 UPDATE employees
 SET salary = salary + 100
 WHERE job_title = 'Manager';
 
 SELECT salary FROM employees;
 
  #4
 CREATE VIEW v_top_paid_employee AS 
 SELECT *
 FROM employees
 ORDER BY salary DESC
 LIMIT 1;
 
 #5
-- ORDER should be after WHERE
 SELECT * 
 FROM employees
 WHERE department_id = 4 AND salary >= 1000
 ORDER BY id;
 
 #6
 DELETE FROM employees
 WHERE department_id = 2 OR department_id = 1;
 
 SELECT * FROM employees
 ORDER BY id;

-- Aliases and Concatenation Example
SELECT
`id` AS 'No.',
concat_ws(' ', `first_name`, `last_name`) AS 'Full Name',
`job_title` AS 'Job Title'
FROM `employees` AS e 
ORDER BY `id`;

-- Distinct Example
SELECT DISTINCT last_name FROM employees;

-- NOT, AND, OR Example
SELECT id, first_name, department_id
FROM employees
WHERE NOT (department_id = 2 AND first_name = 'John');

-- BETWEEN Example
SELECT id, first_name, department_id
FROM employees
WHERE id BETWEEN 1 AND 5;

-- IN/NOT IN Example
SELECT id, first_name, department_id
FROM employees
WHERE department_id IN (1, 5, 7, 9, 8, 3, 712);

 -- Comparison with null example
 ALTER TABLE employees
 CHANGE COLUMN last_name last_name VARCHAR(50);
 
 SELECT *
 FROM employees
 WHERE last_name IS NOT NULL;
 
 SELECT *
 FROM employees
 ORDER BY department_id DESC, salary DESC;
 
 -- Insert Examples
 SELECT * FROM v_top_paid_employee;
 
 INSERT INTO employees VALUES(11, 'First', 'Last', 'Job', 2, 2200);
 
 INSERT INTO employees(first_name, job_title, department_id, salary)
 VALUES ('George1', 'Cook1', 1, 2200),
		('George2', 'Cook2', 2, 3000),
		('George3', 'Cook3', 3, 5000),
		('George4', 'Cook4', 4, 4000);
 
 SELECT * FROM employees;
 
 -- Create table from existing records
 CREATE TABLE created_from_select
 AS SELECT id, department_id, job_title
 FROM employees;
 
 -- Resources
CREATE DATABASE IF NOT EXISTS `hotel`; 
USE `hotel`;

CREATE TABLE departments (
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50)
);

INSERT INTO departments(name) VALUES('Front Office'), ('Support'), ('Kitchen'), ('Other');

CREATE TABLE employees (
	id INT PRIMARY KEY AUTO_INCREMENT,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	job_title VARCHAR(50) NOT NULL,
	department_id INT NOT NULL,
	salary DOUBLE NOT NULL,
	CONSTRAINT `fk_department_id` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`)
);

INSERT INTO `employees` (`first_name`,`last_name`, `job_title`,`department_id`,`salary`) VALUES
	('John', 'Smith', 'Manager',1, 900.00),
	('John', 'Johnson', 'Customer Service',2, 880.00),
	('Smith', 'Johnson', 'Porter', 4, 1100.00),
	('Peter', 'Petrov', 'Front Desk Clerk', 1, 1100.00),
	('Peter', 'Ivanov', 'Sales', 2, 1500.23),
	('Ivan' ,'Petrov', 'Waiter', 3, 990.00),
	('Jack', 'Jackson', 'Executive Chef', 3, 1800.00),
	('Pedro', 'Petrov', 'Front Desk Supervisor', 1, 2100.00),
	('Nikolay', 'Ivanov', 'Housekeeping', 4, 1600.00);
	
CREATE TABLE rooms (
	id INT PRIMARY KEY AUTO_INCREMENT,
	`type` VARCHAR(30)
);

INSERT INTO rooms(`type`) VALUES('apartment'), ('single room');

CREATE TABLE clients (
	id INT PRIMARY KEY AUTO_INCREMENT,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	room_id INT NOT NULL,
    CONSTRAINT fk_clients_rooms
    FOREIGN KEY (room_id)
    REFERENCES rooms(id)
);

INSERT INTO clients(`first_name`,`last_name`,`room_id`) 
VALUES('Pesho','Petrov', 1),('Gosho','Georgiev', 2),
('Mariya','Marieva', 2), ('Katya','Katerinova', 1), ('Nikolay','Nikolaev', 2);
