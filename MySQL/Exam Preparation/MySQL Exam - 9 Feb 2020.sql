CREATE DATABASE football_scout;
USE football_scout;

#1
CREATE TABLE countries(
id INT(11) PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(45) NOT NULL
);

CREATE TABLE towns(
id INT(11) PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(45) NOT NULL,
country_id INT NOT NULL,
CONSTRAINT fk_towns_countries
FOREIGN KEY (country_id)
REFERENCES countries(id)
);

CREATE TABLE stadiums(
id INT(11) PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(45) NOT NULL,
capacity INT(11) NOT NULL,
town_id INT NOT NULL,
CONSTRAINT fk_stadiums_towns
FOREIGN KEY (town_id)
REFERENCES towns(id)
); 

CREATE TABLE teams(
id INT(11) PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(45) NOT NULL,
established DATE NOT NULL,
fan_base BIGINT(20) NOT NULL DEFAULT 0,
stadium_id INT NOT NULL,
CONSTRAINT fk_teams_stadiums
FOREIGN KEY (stadium_id)
REFERENCES stadiums(id)
);

CREATE TABLE skills_data(
id INT PRIMARY KEY AUTO_INCREMENT,
dribbling INT(11) DEFAULT 0,
pace INT(11) DEFAULT 0,
passing INT(11) DEFAULT 0,
shooting INT(11) DEFAULT 0,
speed INT(11) DEFAULT 0,
strength INT(11) DEFAULT 0
);

CREATE TABLE players(
id INT PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(10) NOT NULL,
last_name VARCHAR(20) NOT NULL,
age INT(11) NOT NULL DEFAULT 0,
position CHAR(1) NOT NULL,
salary DECIMAL(10,2) NOT NULL DEFAULT 0,
hire_date DATETIME,
skills_data_id INT(11) NOT NULL,
team_id INT,
CONSTRAINT fk_players_skills_data
FOREIGN KEY (skills_data_id)
REFERENCES skills_data(id),
CONSTRAINT fk_players_teams
FOREIGN KEY (team_id)
REFERENCES teams(id)
);

CREATE TABLE coaches(
id INT(11) PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(10) NOT NULL,
last_name VARCHAR(20) NOT NULL,
salary DECIMAL (10, 2) NOT NULL DEFAULT 0,
coach_level INT(11) NOT NULL DEFAULT 0
);

CREATE TABLE players_coaches(
player_id INT(11),
coach_id INT(11),
CONSTRAINT pk_players_coaches
PRIMARY KEY (player_id, coach_id),
CONSTRAINT fk_players_coaches_players
FOREIGN KEY (player_id)
REFERENCES players(id),
CONSTRAINT fk_players_coaches_coaches
FOREIGN KEY (coach_id)
REFERENCES coaches(id)
);

#2
INSERT INTO coaches(first_name, last_name, salary, coach_level)
SELECT first_name, last_name, salary * 2,
CHAR_LENGTH(first_name)
FROM players
WHERE age >= 45;

#3
UPDATE coaches
SET coach_level = coach_level + 1
WHERE LEFT(first_name, 1) = 'A'
AND id IN (SELECT coach_id FROM players_coaches);

#4
DELETE FROM players
WHERE age >= 45;

#5
SELECT first_name, age, salary
FROM players
ORDER BY salary DESC;

#6
SELECT p.id, CONCAT_WS(' ', p.first_name, p.last_name) AS full_name, p.age, p.position, p.hire_date
FROM players AS p
JOIN skills_data AS sd
ON p.skills_data_id = sd.id
WHERE p.age < 23 AND p.position = 'A' AND p.hire_date IS NULL AND sd.strength > 50
ORDER BY p.salary ASC, p.age;

#7
SELECT t.name AS team_name, t.established, t.fan_base,
COUNT(p.id) AS players_count
FROM teams AS t
LEFT JOIN players AS p
ON t.id = p.team_id
GROUP BY t.id
ORDER BY players_count DESC, t.fan_base DESC;

#8
SELECT MAX(sd.speed) AS max_speed, towns.name AS town_name
FROM skills_data AS sd
JOIN players AS p
ON p.skills_data_id = sd.id
RIGHT JOIN teams AS t
ON p.team_id = t.id
JOIN stadiums AS s
ON t.stadium_id = s.id
RIGHT JOIN towns
ON s.town_id = towns.id
WHERE t.name != 'Devify'
GROUP BY towns.id
ORDER BY max_speed DESC, towns.name;

#9
SELECT c.name,
COUNT(p.id) AS total_count_of_players,
SUM(p.salary) AS total_sum_of_salaries
FROM countries AS c
LEFT JOIN towns AS t
ON c.id = t.country_id
LEFT JOIN stadiums AS s
ON t.id = s.town_id
LEFT JOIN teams
ON s.id = teams.stadium_id
LEFT JOIN players AS p
ON teams.id = p.team_id
GROUP BY c.id
ORDER BY total_count_of_players DESC, c.name ASC;

#10
DELIMITER $$

CREATE FUNCTION `udf_stadium_players_count`(stadium_name VARCHAR(30))
RETURNS INT
DETERMINISTIC

BEGIN

	RETURN (
		SELECT COUNT(p.id)
		FROM stadiums AS s
		LEFT JOIN teams AS t
		ON s.id = t.stadium_id
		LEFT JOIN players AS p
		ON t.id = p.team_id
		WHERE s.name = stadium_name
		GROUP BY s.id
    );
    
END$$

DELIMITER ;

SELECT udf_stadium_players_count ('Jaxworks') as `count`; 
SELECT udf_stadium_players_count ('Linklinks') as `count`;

#11
DELIMITER $$

CREATE PROCEDURE `udp_find_playmaker`(min_dribble_points INT, team_name VARCHAR(45))

BEGIN

	SELECT
	CONCAT_WS(' ', p.first_name, p.last_name) AS full_name,
	p.age, p.salary, sd.dribbling, sd.speed, t.name
	FROM skills_data AS sd
	JOIN players AS p
	ON sd.id = p.skills_data_id
	JOIN teams AS t
	ON p.team_id = t.id
	WHERE sd.dribbling > min_dribble_points AND t.name = team_name AND
	sd.speed > (SELECT AVG(speed) FROM skills_data)
	ORDER BY sd.speed DESC
	LIMIT 1;

END$$

DELIMITER ;

CALL udp_find_playmaker (20, 'Skyble');