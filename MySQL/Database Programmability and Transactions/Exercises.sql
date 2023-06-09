#1
DELIMITER $$

CREATE PROCEDURE `usp_get_employees_salary_above_35000`()
BEGIN
	SELECT 
    first_name, last_name
FROM
    employees
WHERE
    salary > 35000
ORDER BY first_name , last_name , employee_id ASC;
END$$

DELIMITER ;


CALL usp_get_employees_salary_above_35000;

#2
DELIMITER $$
USE `soft_uni`$$
CREATE PROCEDURE `usp_get_employees_salary_above`(n DECIMAL(10, 4))
BEGIN

	SELECT 
    first_name, last_name
FROM
    employees
WHERE
    salary >= n
ORDER BY first_name , last_name , employee_id;

END$$

DELIMITER ;

CALL `usp_get_employees_salary_above`(45000);

#3
DELIMITER $$

CREATE PROCEDURE `usp_get_towns_starting_with`(str VARCHAR(20))
BEGIN

	SELECT 
    name
FROM
    towns
WHERE
    name LIKE CONCAT(str, '%')
ORDER BY name;

END$$

DELIMITER ;

CALL usp_get_towns_starting_with('b');

#4
DELIMITER $$

CREATE PROCEDURE `usp_get_employees_from_town`(town_name VARCHAR(20))
BEGIN

SELECT 
    first_name, last_name
FROM
    employees AS e
        JOIN
    addresses AS a USING (address_id)
        JOIN
    towns AS t USING (town_id)
WHERE
    t.name = town_name
ORDER BY e.first_name , e.last_name , e.employee_id;

END$$

DELIMITER ;

CALL `usp_get_employees_from_town`('Sofia');

#5
DELIMITER $$

CREATE FUNCTION `ufn_get_salary_level`(e_salary DECIMAL)
RETURNS VARCHAR(10)
DETERMINISTIC

BEGIN

	RETURN (CASE
		WHEN e_salary < 30000 THEN 'Low'
		WHEN e_salary BETWEEN 30000 AND 50000 THEN 'Average'
		WHEN e_salary > 50000 THEN 'High'
	END
	);
END$$

DELIMITER ;

#6
DELIMITER $$
CREATE PROCEDURE `usp_get_employees_by_salary_level`(salary_level VARCHAR(10))

BEGIN

	SELECT first_name, last_name
	FROM employees
	WHERE `ufn_get_salary_level`(salary) = salary_level
	ORDER BY first_name DESC, last_name DESC;

END$$

DELIMITER ;

CALL `usp_get_employees_by_salary_level`('High');

#7
DELIMITER $$
CREATE FUNCTION  `ufn_is_word_comprised`(set_of_letters VARCHAR(50), word VARCHAR(50))
RETURNS INT
DETERMINISTIC

	RETURN word REGEXP (concat('^[', set_of_letters, ']+$'))$$

DELIMITER ;

SELECT `ufn_is_word_comprised`('oistmiahf', 'Sofia');
SELECT `ufn_is_word_comprised`('oistmiahf', 'halves');

#8
DELIMITER $$

CREATE PROCEDURE `usp_get_holders_full_name`()
BEGIN
	SELECT CONCAT_WS(' ', first_name, last_name) AS full_name
	FROM account_holders
	ORDER BY full_name;
END$$

DELIMITER ;

#9
DELIMITER $$

CREATE PROCEDURE `usp_get_holders_with_balance_higher_than`(n DECIMAL)
BEGIN

	SELECT ah.first_name, ah.last_name
	FROM account_holders AS ah
	JOIN accounts AS a
	ON ah.id = a.account_holder_id
	GROUP BY a.account_holder_id
	HAVING SUM(a.balance) > n;
    
END$$

DELIMITER ;

CALL `usp_get_holders_with_balance_higher_than`(7000);

#10
DELIMITER $$

CREATE FUNCTION `ufn_calculate_future_value`(`sum` DECIMAL(10, 4), 
											yearly_interest_rate DOUBLE,
											number_of_years INT)
RETURNS DECIMAL(10, 4)
DETERMINISTIC

BEGIN
	RETURN `sum` * (POW((1 + yearly_interest_rate), number_of_years));
END$$

DELIMITER ;

SELECT `ufn_calculate_future_value`(1000, 0.5, 5);

#11
DELIMITER $$

CREATE PROCEDURE `usp_calculate_future_value_for_account`(account_id INT, interest_rate DOUBLE(19,4))

BEGIN

	SELECT a.id, ah.first_name, ah.last_name, a.balance,
	`ufn_calculate_future_value`(a.balance, interest_rate, 5)
	FROM accounts AS a
	JOIN account_holders AS ah
	ON a.account_holder_id = ah.id
    WHERE a.id = account_id;

END$$

DELIMITER ;

CALL `usp_calculate_future_value_for_account`(1, 0.1);

#12
DELIMITER $$
CREATE PROCEDURE `usp_deposit_money`(account_id INT, money_amount DECIMAL(19,4))

BEGIN
	START TRANSACTION;
    
		IF (SELECT COUNT(id) FROM accounts
		WHERE id = account_id) != 1 
		OR money_amount <= 0 
		THEN ROLLBACK;
	ELSE   
		UPDATE accounts
		SET balance = balance + money_amount
		WHERE id = account_id;
    END IF;
END$$

DELIMITER ;

CALL `usp_deposit_money`(1, 10);

#13
DELIMITER $$
CREATE PROCEDURE usp_withdraw_money(account_id INT, money_amount DECIMAL(19, 4))

BEGIN
    START TRANSACTION;
		IF (SELECT COUNT(id) FROM accounts
		WHERE id = account_id) != 1 
		OR (SELECT balance FROM accounts WHERE id = account_id) < money_amount
        OR money_amount <= 0
		THEN ROLLBACK;
	ELSE   
		UPDATE accounts
		SET balance = balance - money_amount
		WHERE id = account_id;
    END IF;

END$$

DELIMITER ;

CALL usp_withdraw_money(1, 10);

#14
DELIMITER $$
CREATE PROCEDURE usp_transfer_money(from_account_id INT, to_account_id INT, amount DECIMAL(19, 4))

BEGIN
    START TRANSACTION;
		IF (SELECT COUNT(id) FROM accounts
		WHERE id IN (from_account_id, to_account_id)) != 2
        OR amount <= 0
        OR from_account_id = to_account_id
		OR (SELECT balance FROM accounts WHERE id = from_account_id) < amount
		THEN ROLLBACK;
	ELSE   
		UPDATE accounts
		SET balance = balance - amount
		WHERE id = from_account_id;
        
        UPDATE accounts
		SET balance = balance + amount
        WHERE id = to_account_id;
        
    END IF;

END$$

DELIMITER ;

CALL usp_transfer_money(1, 2, 10);

#15
CREATE TABLE `logs` (
	log_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT,
    old_sum DECIMAL(19,4),
    new_sum DECIMAL(19,4)
);

DELIMITER $$

CREATE TRIGGER tr_update_accounts
AFTER UPDATE
ON accounts
FOR EACH ROW

BEGIN
	INSERT INTO `logs`(account_id, old_sum, new_sum)
    VALUES
    (OLD.id, OLD.balance, NEW.balance);
END$$

DELIMITER ;

DROP TRIGGER tr_new_record_inserted_in_logs;
DROP TABLE notification_emails;
DROP TRIGGER tr_new_record_inserted;

#16
CREATE TABLE notification_emails
(id INT PRIMARY KEY AUTO_INCREMENT,
recipient INT NOT NULL,
`subject` VARCHAR(100),
body VARCHAR(255)
);

DELIMITER $$

CREATE TRIGGER tr_new_record_inserted_in_logs
AFTER INSERT
ON `logs`
FOR EACH ROW

BEGIN
	INSERT INTO `notification_emails`(recipient, `subject`, body)
    VALUES
    (NEW.account_id,
    CONCAT('Balance change for account: ', NEW.account_id),
    CONCAT('On ', NOW(), 
	' your balance was changed from ', 
    (NEW.old_sum), ' to ', 
    (NEW.new_sum), '.')
    );
    
END$$

DELIMITER ;