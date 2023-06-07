#1
SELECT 
    e.`employee_id`,
    e.`job_title`,
    a.`address_id`,
    a.`address_text`
FROM
    `employees` AS e
        JOIN
    `addresses` AS a ON e.`address_id` = a.`address_id`
ORDER BY a.`address_id` ASC
LIMIT 5;

#2
SELECT 
    e.`first_name`,
    e.`last_name`,
    t.`name` AS town,
    a.`address_text`
FROM
    `employees` AS e
        JOIN
    `addresses` AS a ON e.`address_id` = a.`address_id`
        JOIN
    `towns` AS t ON a.`town_id` = t.`town_id`
ORDER BY e.`first_name` ASC , `last_name` ASC
LIMIT 5;

#3
SELECT
    e.`employee_id`,
    e.`first_name`,
    e.`last_name`,
    d.`name`
FROM
    `employees` AS e
JOIN
    `departments` AS d
     ON e.`department_id` = d.`department_id`
WHERE
    d.`name` = 'Sales'
ORDER BY
    e.`employee_id` DESC;

#4
SELECT
    `e`.`employee_id`,
    `e`.`first_name`,
    `e`.`salary`,
    `d`.`name` AS `department_name`
FROM
    `employees` AS `e`
JOIN
    `departments` AS `d` USING(`department_id`)
WHERE
    `e`.`salary` > 15000
ORDER BY
    `d`.`department_id` DESC
LIMIT 5;


#5 Solution with JOIN
SELECT e.employee_id, e.first_name
FROM employees AS e
LEFT JOIN employees_projects AS ep
ON e.employee_id = ep.employee_id
WHERE project_id IS NULL
ORDER BY e.employee_id DESC
LIMIT 3;

# 5 Solution with subquery
SELECT employee_id, first_name
FROM employees
WHERE employee_id NOT IN (SELECT employee_id FROM employees_projects)
ORDER BY employee_id DESC
LIMIT 3;

#6
SELECT e.first_name, e.last_name, e.hire_date, d.name AS dept_name
FROM employees AS e
JOIN departments AS d
ON e.department_id = d.department_id
WHERE e.hire_date > '1999-01-01' AND
d.name = 'Sales' OR d.name = 'Finance'
ORDER BY e.hire_date ASC;

#7
SELECT 
    e.`employee_id`, e.`first_name`, p.`name` AS project_name
FROM
    `employees` AS e
        JOIN
    `employees_projects` AS ep ON ep.`employee_id` = e.`employee_id`
        JOIN
    projects AS p ON p.`project_id` = ep.`project_id`
WHERE
    DATE(p.`start_date`) > '2002-08-13'
        AND p.`end_date` IS NULL
ORDER BY e.`first_name`, p.`name`
LIMIT 5;

#8
SELECT 
e.employee_id,
e.first_name,
IF (YEAR(p.start_date) >= 2005, NULL, p.name) AS project_name
FROM employees AS e
JOIN employees_projects AS ep
ON e.employee_id = ep.employee_id
JOIN projects AS p
ON p.project_id = ep.project_id
WHERE e.employee_id = 24
ORDER BY project_name;

#9
SELECT 
 e.employee_id,
 e.first_name,
 e.manager_id,
 m.first_name AS manager_name
FROM employees AS e
JOIN employees AS m
ON e.manager_id = m.employee_id
WHERE e.manager_id IN (3, 7) 
ORDER BY e.first_name;

#10
SELECT e.employee_id,
CONCAT_WS(' ', e.first_name, e.last_name) AS employee_name,
CONCAT_WS(' ', m.first_name, m.last_name) AS manager_name,
d.name AS department_name
FROM employees AS e
JOIN employees AS m
ON e.manager_id = m.employee_id
JOIN departments AS d
ON d.department_id = e.department_id
ORDER BY e.employee_id
LIMIT 5;

#11
SELECT AVG(salary) AS min_average_salary
FROM employees
GROUP BY department_id
ORDER BY min_average_salary
LIMIT 1;

#12
SELECT c.country_code, m.mountain_range, p.peak_name, p.elevation
FROM countries AS c
JOIN mountains_countries AS mc
ON mc.country_code = c.country_code
JOIN mountains AS m
ON mc.mountain_id = m.id
JOIN peaks AS p
ON m.id = p.mountain_id
WHERE c.country_name = 'Bulgaria' AND p.elevation > 2835
ORDER BY p.elevation DESC;

#13
SELECT mc.country_code, COUNT(m.mountain_range) AS mountain_range
FROM mountains_countries AS mc
JOIN mountains AS m
ON mc.mountain_id = m.id
WHERE mc.country_code IN ('BG', 'RU', 'US')
GROUP BY mc.country_code
ORDER BY mountain_range DESC;

# 13 Solution without JOIN
SELECT country_code, COUNT(*) AS mountain_range
FROM mountains_countries
WHERE country_code IN ('BG', 'RU', 'US')
GROUP BY country_code
ORDER BY mountain_range DESC;

#14
SELECT c.country_name, r.river_name
FROM countries AS c
LEFT JOIN countries_rivers AS cr
USING (country_code)
LEFT JOIN rivers AS r
ON cr.river_id = r.id
WHERE c.continent_code = 'AF'
ORDER BY c.country_name
LIMIT 5;

#15
SELECT
continent_code, 
currency_code,
COUNT(country_code) AS currency_usage
FROM countries AS c
GROUP BY continent_code, currency_code
HAVING currency_usage = (
	SELECT COUNT(country_code) AS count
	FROM countries AS c1
	WHERE c1.continent_code = c.continent_code
	GROUP BY currency_code
	ORDER BY count DESC
	LIMIT 1
) AND currency_usage > 1
ORDER BY continent_code, currency_code;

# 16 Solution with JOIN
SELECT COUNT(*) AS country_count
FROM countries AS c
LEFT JOIN mountains_countries AS mc
USING (country_code)
WHERE mc.mountain_id IS NULL;

# 16 Solution with subquery
SELECT COUNT(*) AS country_count
FROM countries AS c
WHERE c.country_code NOT IN
(SELECT country_code FROM mountains_countries);

#17
SELECT 
    c.country_name,
    MAX(p.elevation) AS highest_peak_elevation,
    MAX(r.length) AS longest_river_length
FROM
    countries AS c
        LEFT JOIN
    mountains_countries AS mc USING (country_code)
        LEFT JOIN
    mountains AS m ON mc.mountain_id = m.id
        LEFT JOIN
    peaks AS p ON m.id = p.mountain_id
        LEFT JOIN
    countries_rivers AS cr USING (country_code)
        LEFT JOIN
    rivers AS r ON cr.river_id = r.id
GROUP BY c.country_name
ORDER BY highest_peak_elevation DESC , longest_river_length DESC , c.country_name ASC
LIMIT 5;