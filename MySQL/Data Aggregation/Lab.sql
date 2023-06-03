 #1
 SELECT department_id, COUNT(*) AS 'Number of employees'
 FROM employees
 GROUP BY department_id
 ORDER BY department_id;
 
 #2
 SELECT department_id, ROUND(AVG(salary), 2) AS 'Average Salary'
 FROM employees
 GROUP BY department_id
 ORDER BY department_id;
 
 #3
 SELECT department_id, ROUND(MIN(salary), 2) AS 'Min Salary'
 FROM employees
 GROUP BY department_id
 HAVING MIN(salary) > 800;
 
 #4
 SELECT COUNT(*)
 FROM products
 WHERE category_id = 2 AND price > 8;
 
 #5
 SELECT
 category_id,
 ROUND(AVG(price), 2) AS 'Average Price',
 ROUND(MIN(price), 2) AS 'Cheapest Product',
 ROUND(MAX(price), 2) AS 'Most Expensive Product'
 FROM products
 GROUP BY category_id;
 
 # Examples from Lab
 SELECT department_id, SUM(salary) AS 'Total Salaries'
 FROM employees
 GROUP BY department_id;
 
 SELECT COUNT(*)
 FROM employees;
 
 SELECT COUNT(last_name)
 FROM employees;
 
 SELECT * FROM employees;
 
 ALTER TABLE employees
 CHANGE COLUMN last_name last_name VARCHAR(30);
 
 UPDATE employees
 SET last_name = NULL
 WHERE id IN (2, 4, 6, 8);
 
 SELECT department_id, COUNT(*)
 FROM employees
 GROUP BY department_id;

UPDATE employees
SET salary = NULL
WHERE id IN (2, 4, 6);

ALTER TABLE employees
CHANGE COLUMN salary salary DOUBLE;

 SELECT department_id, SUM(salary)
 FROM employees
 WHERE id IN (2, 4, 6)
 GROUP BY department_id;
 
 SELECT MAX(first_name)
 FROM employees;
 
 SELECT MIN(id)
 FROM employees;
 
 SELECT AVG(salary)
 FROM employees;
 
 SELECT
 SUM(salary) AS 'Total Salaries',
 COUNT(*) AS 'Total Employees',
 SUM(salary) / COUNT(*) AS 'Total / Total',
 AVG(salary) AS 'AVG',
 SUM(salary) / COUNT(salary) AS 'Total / salary'
 FROM employees;
 
 SELECT department_id, AVG(salary)
 FROM employees
 GROUP BY department_id;