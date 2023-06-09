#1
DELIMITER $$
CREATE FUNCTION `ufn_count_employees_by_town`(town_name VARCHAR(20))
RETURNS VARCHAR(40)
DETERMINISTIC
BEGIN

RETURN (SELECT 
    COUNT(*)
FROM
    employees AS e
        JOIN
    addresses AS a USING (address_id)
        JOIN
    towns AS t USING (town_id)
WHERE
    t.name = town_name);
    
END$$

DELIMITER ;

SELECT `ufn_count_employees_by_town`('Sofia');

#2
DELIMITER $$
CREATE PROCEDURE `usp_raise_salaries`(department_name VARCHAR(40))
DETERMINISTIC
BEGIN

		UPDATE employees AS e
		JOIN departments AS d
		ON e.department_id = d.department_id
		SET salary = salary * 1.05
		WHERE d.name = department_name;

END$$

DELIMITER ;

CALL usp_raise_salaries('Engineering');

#3
DELIMITER $$

CREATE PROCEDURE `usp_raise_salary_by_id`(emp_id INT)
BEGIN

IF(
	(SELECT COUNT(employee_id) FROM employees
	WHERE employee_id = emp_id) != 1) THEN
	ROLLBACK;
	ELSE
    
		UPDATE employees
		SET salary = salary * 1.05
		WHERE employee_id = emp_id;
        
	END IF; 
	
END$$

DELIMITER ;

CALL usp_raise_salary_by_id(17);

#4 Trigger

CREATE DATABASE asd;

CREATE TABLE `deleted_employees` (
  `employee_id` int NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `middle_name` varchar(50) DEFAULT NULL,
  `job_title` varchar(50) NOT NULL,
  `department_id` int(10) NOT NULL,
  `salary` decimal(19,4) NOT NULL,
  PRIMARY KEY (`employee_id`)
  );
  
  
CREATE TABLE IF NOT EXISTS `employees` (
  `employee_id` int(10) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `middle_name` varchar(50) DEFAULT NULL,
  `job_title` varchar(50) NOT NULL,
  `department_id` int(10) NOT NULL,
  `manager_id` int(10) DEFAULT NULL,
  `hire_date` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `salary` decimal(19,4) NOT NULL,
  `address_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`employee_id`));
  
  INSERT INTO `employees` (`employee_id`, `first_name`, `last_name`, `middle_name`, `job_title`, `department_id`, `manager_id`, `hire_date`, `salary`, `address_id`) VALUES
	(1, 'Guy', 'Gilbert', 'R', 'Production Technician', 7, 16, '1998-07-31 00:00:00.000000', 12500.0000, 166),
	(2, 'Kevin', 'Brown', 'F', 'Marketing Assistant', 4, 6, '1999-02-26 00:00:00.000000', 13500.0000, 102),
	(3, 'Roberto', 'Tamburello', NULL, 'Engineering Manager', 1, 12, '1999-12-12 00:00:00.000000', 43300.0000, 193),
	(4, 'Rob', 'Walters', NULL, 'Senior Tool Designer', 2, 3, '2000-01-05 00:00:00.000000', 29800.0000, 155),
	(5, 'Thierry', 'D\'Hers', 'B', 'Tool Designer', 2, 263, '2000-01-11 00:00:00.000000', 25000.0000, 40),
	(6, 'David', 'Bradley', 'M', 'Marketing Manager', 5, 109, '2000-01-20 00:00:00.000000', 37500.0000, 199),
	(7, 'JoLynn', 'Dobney', 'M', 'Production Supervisor', 7, 21, '2000-01-26 00:00:00.000000', 25000.0000, 275),
	(8, 'Ruth', 'Ellerbrock', 'Ann', 'Production Technician', 7, 185, '2000-02-06 00:00:00.000000', 13500.0000, 108),
	(9, 'Gail', 'Erickson', 'A', 'Design Engineer', 1, 3, '2000-02-06 00:00:00.000000', 32700.0000, 22),
	(10, 'Barry', 'Johnson', 'K', 'Production Technician', 7, 185, '2000-02-07 00:00:00.000000', 13500.0000, 285),
	(11, 'Jossef', 'Goldberg', 'H', 'Design Engineer', 1, 3, '2000-02-24 00:00:00.000000', 32700.0000, 214),
	(12, 'Terri', 'Duffy', 'Lee', 'Vice President of Engineering', 1, 109, '2000-03-03 00:00:00.000000', 63500.0000, 209),
	(13, 'Sidney', 'Higa', 'M', 'Production Technician', 7, 185, '2000-03-05 00:00:00.000000', 13500.0000, 73),
	(14, 'Taylor', 'Maxwell', 'R', 'Production Supervisor', 7, 21, '2000-03-11 00:00:00.000000', 25000.0000, 82),
	(15, 'Jeffrey', 'Ford', 'L', 'Production Technician', 7, 185, '2000-03-23 00:00:00.000000', 13500.0000, 156),
	(16, 'Jo', 'Brown', 'A', 'Production Supervisor', 7, 21, '2000-03-30 00:00:00.000000', 25000.0000, 70),
	(17, 'Doris', 'Hartwig', 'M', 'Production Technician', 7, 185, '2000-04-11 00:00:00.000000', 13500.0000, 144);

DELIMITER $$

CREATE TRIGGER `employees_BEFORE_DELETE` AFTER DELETE ON `employees` FOR EACH ROW
BEGIN

INSERT INTO `deleted_employees` (employee_id, first_name, last_name, middle_name, job_title, department_id, salary)
	VALUES
    (OLD.employee_id,
    OLD.first_name, 
    OLD.last_name,
    OLD.middle_name, 
    OLD.job_title,
    OLD.department_id,
    OLD.salary);
    
END$$

DELIMITER ;

DELETE FROM employees
WHERE employee_id = 5;
