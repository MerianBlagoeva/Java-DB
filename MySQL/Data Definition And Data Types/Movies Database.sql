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