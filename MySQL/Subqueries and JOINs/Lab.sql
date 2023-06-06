#1 
SELECT e.employee_id, 
CONCAT(e.first_name, ' ', e.last_name) AS full_name,
d.department_id,
d.name
FROM departments AS d
LEFT JOIN employees AS e
ON d.manager_id = e.employee_id
ORDER BY e.employee_id
LIMIT 5;

#2
SELECT t.town_id, t.name AS town_name, a.address_text
FROM addresses AS a
LEFT JOIN towns AS t
USING(town_id)
WHERE t.name IN ('San Francisco', 'Sofia', 'Carnation')
ORDER BY t.town_id, a.address_id;

#3
SELECT employee_id, first_name, last_name, department_id, salary
FROM employees
WHERE manager_id IS NULL;

#4
SELECT COUNT(*) AS count
FROM employees
WHERE salary > 
(SELECT AVG(salary)
FROM employees
);

# JOIN example
SELECT e.first_name, d.name
FROM employees AS e
JOIN departments AS d
ON e.department_id = d.department_id;

# UNION example
SELECT * FROM employees
WHERE first_name LIKE 'A%'

UNION

SELECT * FROM employees
WHERE first_name LIKE 'B%';

# There's no FULL JOIN in MySQL, so we use UNION of LEFT and RIGHT JOIN
SELECT e.first_name, d.*
FROM employees as e
LEFT JOIN departments AS d
	USING (department_id)
    
    UNION
    
SELECT e.first_name, d.*
FROM employees as e
RIGHT JOIN departments AS d
	USING (department_id);