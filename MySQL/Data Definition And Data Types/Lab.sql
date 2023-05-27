CREATE DATABASE minions;
USE minions;

-- 1
CREATE TABLE minions (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(47),
    `age` INT
);

CREATE TABLE towns (
    town_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(47)
);

-- 2
ALTER TABLE minions
ADD COLUMN town_id INT;

ALTER TABLE minions
ADD CONSTRAINT fk_minions_towns FOREIGN KEY minions(town_id)
REFERENCES towns(id);

-- 3
INSERT INTO `towns`(`id`, `name`)
VALUES (1, 'Sofia'),
       (2, 'Plovdiv'),
       (3, 'Varna');
       
INSERT INTO `minions`(`id`, `name`, `age`, `town_id`)
VALUES (1, 'Kevin', 22, 1),
	   (2, 'Bob', 15, 3),
	   (3, 'Steward', null, 2);

SELECT * FROM minions;

-- 4
TRUNCATE TABLE minions;

-- 5
DROP TABLE minions;
DROP TABLE towns;

-- 6
CREATE TABLE people (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    picture BLOB,
    height DOUBLE(10 , 2 ),
    weight DOUBLE(10 , 2 ),
    gender CHAR(1) NOT NULL,
    birthdate DATE NOT NULL,
    biography TEXT
);

INSERT INTO people (name, gender, birthdate)
VALUES ('Pesho', 'm', '2023-05-20'),
('Meri', 'f', '2001-05-20'),
('Asen', 'm', '2004-05-20'),
('Ivan', 'm', '1995-05-20'),
('Gosho','m', '2006-05-20'),
('Asen', 'm', '2023-05-20');

SELECT *FROM people;

-- 7
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL,
    password VARCHAR(26) NOT NULL,
    profile_picture BLOB,
    last_login_time TIMESTAMP,
    is_deleted BOOLEAN
);

INSERT INTO users (username, password, last_login_time, is_deleted)
VALUES ('user1', 'password1', CURRENT_TIMESTAMP, false),
       ('user2', 'password2', '2023-05-19 10:30:00', false),
       ('user3', 'password3', '2023-05-18 15:45:00', true),
       ('user4', 'password4', NULL, false),
       ('user5', 'password5', '2023-05-17 08:00:00', true);
       
-- 8
ALTER TABLE users
DROP PRIMARY KEY,
ADD CONSTRAINT pk_users PRIMARY KEY users(id, username);

-- 9
ALTER TABLE users
CHANGE COLUMN last_login_time
last_login_time DATETIME DEFAULT NOW();

-- 10
ALTER TABLE users
DROP PRIMARY KEY,
ADD CONSTRAINT pk_users
PRIMARY KEY users(id),
CHANGE COLUMN username
username VARCHAR(30) UNIQUE NOT NULL;

-- 11
CREATE DATABASE `movies`;-- without this row for judge

CREATE TABLE `directors` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `director_name` VARCHAR(50) NOT NULL,
    `notes` TEXT
);

INSERT INTO `directors`
VALUES
(1, 'Robert', 'some text'),
(2, 'Gosho', 'some text'),
(3, 'Mihail', 'some text'),
(4, 'James', 'some text'),
(5, 'Arthur', 'some text');

CREATE TABLE `genres` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `genre_name` VARCHAR(20) NOT NULL,
    `notes` TEXT
);

 INSERT INTO `genres`
 VALUES
 (1, 'crime', 'some text'),
 (2, 'horror', 'some text'),
 (3, 'action', 'some text'),
 (4, 'comedy', 'some text'),
 (5, 'fantasy', 'some text');
 
CREATE TABLE `categories` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `category_name` VARCHAR(20) NOT NULL,
    `notes` TEXT
);

 INSERT INTO `categories`
 VALUES
 (1, 'drama', 'some text'),
 (2, 'documentary', 'some text'),
 (3, 'adventure', 'some text'),
 (4, 'mystery', 'some text'),
 (5, 'romantic comedy', 'some text');

CREATE TABLE `movies` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `title` VARCHAR(20) NOT NULL,
    `director_id` INT NOT NULL,
    `copyright_year` YEAR,
    `length` FLOAT NOT NULL,
    `genre_id` INT NOT NULL,
    `category_id` INT NOT NULL,
    `rating` INT NOT NULL,
    `notes` TEXT
);

INSERT INTO `movies`
VALUES
(1, 'RANGO', 1, 2003, 1.36, 1, 1, 100, 'some text'),
(2, 'RANGO', 1, 2003, 1.36, 1, 1, 100, 'some text'),
(3, 'RANGO', 1, 2003, 1.36, 1, 1, 100, 'some text'),
(4, 'RANGO', 1, 2003, 1.36, 1, 1, 100, 'some text'),
(5, 'RANGO', 1, 2003, 1.36, 1, 1, 100, 'some text');

-- 12
CREATE DATABASE `car_rental`;-- without this row for judge
USE car_rental;

CREATE TABLE `categories` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `category` VARCHAR(50) NOT NULL,
    `daily_rate` INT NOT NULL,
    `weekly_rate` INT NOT NULL,
    `monthly_rate` INT NOT NULL,
    `weekend_rate` INT NOT NULL
);

INSERT INTO `categories`
VALUES
  (1, 'Economy', 10, 50, 100, 80),
  (2, 'Compact', 10, 50, 100, 80),
  (3, 'SUV', 10, 50, 100, 80);

CREATE TABLE `cars` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `plate_number` VARCHAR(15) NOT NULL,
    `make` VARCHAR(50) NOT NULL,
    `model` VARCHAR(50) NOT NULL,
    `car_year` YEAR NOT NULL,
    `category_id` INT NOT NULL,
    `doors` INT NOT NULL,
    `picture` BLOB,
    `car_condition` VARCHAR(50) NOT NULL,
    `available` BOOL
);

  INSERT INTO `cars`
VALUES
  (1, 'ABC123', 'Toyota', 'Corolla', 2010, 1, 5, NULL, 'NEW', 1),
  (2, 'DEF456', 'Honda', 'Civic', 2010, 1, 5, NULL, 'NEW', 1),
  (3, 'GHI789', 'Ford', 'Escape', 2010, 1, 5, NULL, 'NEW', 1);

CREATE TABLE `employees` (
    id INT PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `title` VARCHAR(50) NOT NULL,
    `notes` TEXT
);

  INSERT INTO `employees`
VALUES
  (1, 'John', 'Doe', 'Sales manager', 'SOME TEXT'),
  (2, 'Jane', 'Smith', 'Operator', 'SOME TEXT'),
  (3, 'Michael', 'George', 'Director', 'SOME TEXT');

CREATE TABLE `customers` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `driver_license_number` VARCHAR(20) NOT NULL,
    `full_name` VARCHAR(50) NOT NULL,
    `adress` VARCHAR(100) NOT NULL,
    `city` VARCHAR(50) NOT NULL,
    `zip_code` VARCHAR(20) NOT NULL,
    notes TEXT
);

INSERT INTO `customers`
VALUES
(1, '545646464', 'IVAN IVANOV', 'STR JGOOIJTR', 'VARNA', '9000','SOME TEXT'),
(2, '545646464', 'IVAN IVANOV', 'STR JGOOIJTR', 'VARNA', '9000','SOME TEXT'),
(3, '545646464', 'IVAN IVANOV', 'STR JGOOIJTR', 'VARNA', '9000','SOME TEXT');

CREATE TABLE `rental_orders` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `employee_id` INT NOT NULL,
    `customer_id` INT NOT NULL,
    `car_id` INT NOT NULL,
    `car_condition` VARCHAR(50) NOT NULL,
    `tank_level` VARCHAR(10) NOT NULL,
    `kilometrage_start` INT NOT NULL,
    `kilometrage_end` INT NOT NULL,
    `total_kilometrage` INT NOT NULL,
    `start_date` DATE NOT NULL,
    `end_date` DATE NOT NULL,
    `total_days` INT NOT NULL,
    `rate_applied` INT NOT NULL,
    `tax_rate` FLOAT NOT NULL,
    `order_status` VARCHAR(50) NOT NULL,
    `notes` TEXT
-- 	 FOREIGN KEY (employee_id) REFERENCES employees (id),
--   FOREIGN KEY (customer_id)REFERENCES customers (id),
--   FOREIGN KEY (car_id) REFERENCES cars (id) ???????
);

INSERT INTO `rental_orders`
VALUES
(1, 1, 1,1, 'NEW', 'FULL', 1000, 1000, 200000,'2023-01-01','2023-10-01', 10, 100,100,'rent', NULL),
(2, 1, 1,1, 'NEW', 'FULL', 1000, 1000, 200000,'2023-01-01','2023-10-01', 10, 100,100,'rent', NULL),
(3, 1, 1,1, 'NEW', 'FULL', 1000, 1000, 200000,'2023-01-01','2023-10-01', 10, 100,100,'rent', NULL);

-- 13
CREATE DATABASE soft_uni;
USE soft_uni;

CREATE TABLE `towns`(
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL
);

INSERT INTO `towns` (`name`)
VALUES ('Sofia'),
	   ('Plovdiv'),
	   ('Varna'),
	   ('Burgas');

CREATE TABLE `addresses`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`address_text` VARCHAR(50) NOT NULL,
`town_id` INT NOT NULL
);

INSERT INTO `addresses` (`address_text`, `town_id`)
VALUES ('some text', 1),
	   ('some text', 2),
	   ('some text', 3),
	   ('some text', 4);

CREATE TABLE `departments`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(50) NOT NULL
);

INSERT INTO `departments` (`name`)
VALUES ('Engineering'),
	   ('Sales'),
       ('Marketing'),
       ('Software Development'),
       ('Quality Assurance');
       
CREATE TABLE `employees`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`first_name` VARCHAR(50) NOT NULL,
`middle_name` VARCHAR(50) NOT NULL,
`last_name` VARCHAR(50) NOT NULL,
`job_title` VARCHAR(50) NOT NULL,
`department_id` INT NOT NULL,
`hire_date` DATE NOT NULL,
`salary` DECIMAL NOT NULL,
`address_id` INT,
FOREIGN KEY (department_id)
	REFERENCES departments(id),
FOREIGN KEY (address_id)
	REFERENCES addresses(id)
);

INSERT INTO `employees` 
(`first_name`, `middle_name`, `last_name`, `job_title`, `department_id`, `hire_date`, `salary`, `address_id`)
VALUES 
('Ivan', 'Ivanov', 'Ivanov', '.NET Developer', 4, '2013-02-01', 3500.00, 1),
('Petar', 'Petrov', 'Petrov', 'Senior Engineer', 1, '2004-03-02', 4000.00, 1),
('Maria', 'Petrova', 'Ivanova', 'Intern', 5, '2016-08-28', 525.25, 1),
('Georgi', 'Terziev', 'Ivanov', 'CEO', 2, '2007-12-09', 3000.00, 1),
('Peter', 'Pan', 'Pan', 'Intern', 3, '2016-08-28', 599.88, 1);

-- 14
SELECT * FROM `towns`;
SELECT * FROM `departments`;
SELECT * FROM `employees`;

-- 15
SELECT * FROM `towns`
ORDER BY `name`;

SELECT * FROM `departments`
ORDER BY `name`;

SELECT * FROM `employees`
ORDER BY `salary` DESC;

-- 16
SELECT `name` FROM `towns`
ORDER BY `name`;

SELECT `name` FROM `departments`
ORDER BY `name`;

SELECT `first_name`, `last_name`, `job_title`, `salary` FROM `employees`
ORDER BY `salary` DESC;

-- 17

UPDATE `employees`
SET `salary` = `salary` * 1.1;
SELECT `salary` FROM `employees`;