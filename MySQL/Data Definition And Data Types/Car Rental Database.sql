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