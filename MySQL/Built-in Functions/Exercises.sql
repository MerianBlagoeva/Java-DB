#1
SELECT first_name, last_name
FROM employees
WHERE first_name LIKE 'Sa%';

#2
SELECT first_name, last_name
FROM employees
WHERE last_name REGEXP('ei');

#3
SELECT first_name
FROM employees
WHERE (department_id = 3 OR department_id = 10)
AND EXTRACT(YEAR FROM hire_date) BETWEEN 1995 AND 2005
ORDER BY employee_id;

#4
SELECT 
    first_name, last_name
FROM
    employees
WHERE
    job_title NOT LIKE '%engineer%';
    
#5
SELECT name
FROM towns
WHERE CHAR_LENGTH(name) = 5 OR CHAR_LENGTH(name) = 6
ORDER BY name;

#6
SELECT town_id, name
FROM towns
WHERE name REGEXP '^[MKBE]'
ORDER BY name;

#7
SELECT town_id, name
FROM towns
WHERE name REGEXP '^[^RDB]'
ORDER BY name;

#8
CREATE VIEW v_employees_hired_after_2000 AS
SELECT first_name, last_name
FROM employees
WHERE YEAR(hire_date) > 2000;

SELECT * FROM v_employees_hired_after_2000;

#9
SELECT first_name, last_name
FROM  employees
WHERE CHAR_LENGTH(last_name) = 5;

#10
USE geography;

#Slowest solution
SELECT country_name, iso_code
FROM countries
WHERE lower(country_name) REGEXP '([^a]*a){3,}'
ORDER BY iso_code;

# Other solution
SELECT country_name, iso_code
FROM countries
WHERE
CHAR_LENGTH(country_name) - CHAR_LENGTH(REPLACE(LOWER(country_name), 'a', '')) >= 3
ORDER BY iso_code;

#11
SELECT
peak_name,
river_name,
LOWER(CONCAT(peak_name, SUBSTRING(river_name, 2))) AS mix
FROM peaks, rivers
WHERE LOWER(SUBSTRING(peak_name, -1)) = LOWER(SUBSTRING(river_name, 1, 1))
ORDER BY mix ASC;

#12
USE diablo;
SELECT name, DATE(start)
FROM games
WHERE YEAR(start) IN (2011, 2012)
ORDER BY start, name
LIMIT 50;

#13
SELECT
user_name,
SUBSTRING_INDEX(email, '@', -1) AS 'email provider'
FROM users
ORDER BY `email provider`, user_name;

#14
SELECT user_name, ip_address
FROM users
WHERE ip_address LIKE'___.1%.%.___'
ORDER BY user_name ASC;

#15
SELECT 
    name AS 'game',
    IF(HOUR(start) >= 0 AND HOUR(start) < 12,
        'Morning',
        IF(HOUR(start) >= 12 AND HOUR(start) < 18,
            'Afternoon',
            'Evening')) AS 'Part of the Day',
    IF(duration <= 3,
        'Extra Short',
        IF(duration > 3 AND duration <= 6,
            'Short',
            IF(duration > 6 AND duration <= 10,
                'Long',
                'Extra Long'))) AS 'Duration'
FROM
    games;
    
#15 with CASE
SELECT 
    name AS 'game',
    CASE
        WHEN HOUR(start) >= 0 AND HOUR(start) < 12 THEN 'Morning'
        WHEN HOUR(start) >= 12 AND HOUR(start) < 18 THEN 'Afternoon'
        ELSE 'Evening'
    END AS 'Part of the Day',
    CASE
        WHEN duration <= 3 THEN 'Extra Short'
        WHEN duration > 3 AND duration <= 6 THEN 'Short'
        WHEN duration > 6 AND duration <= 10 THEN 'Long'
        ELSE 'Extra Long'
    END AS 'Duration'
FROM
    games;

#16
USE orders;
SELECT
product_name,
order_date, 
DATE_ADD(order_date, INTERVAL 3 DAY) AS pay_due,
DATE_ADD(order_date, INTERVAL 1 MONTH) AS deliver_due
FROM orders;