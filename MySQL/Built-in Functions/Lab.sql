#1
SELECT title
FROM books
WHERE SUBSTRING(title, 1, 3) = 'The'
ORDER BY id;

#2
SELECT REPLACE (title, 'The', '***')
FROM books
WHERE SUBSTRING(title, 1, 3) = 'The'
ORDER BY id;

#3
SELECT ROUND(SUM(cost), 2)
FROM books;

#4
SELECT
CONCAT_WS(' ',  first_name, last_name) AS 'Full Name',
TIMESTAMPDIFF(DAY, born, died) AS 'Days Lived'
FROM authors;

#5
SELECT title
FROM books
WHERE title REGEXP '^Harry Potter'
ORDER BY id;

# LRRIM & RTRIM example
SELECT RTRIM(LTRIM('    something    '));

#CHAR_LENGTH & LENGTH example
SELECT CHAR_LENGTH('abcd'), LENGTH('абвг'); # LENGTH is the number of bytes used (double for Unicode)

# LEFT & RIGHT example
SELECT LEFT('01234', 2), SUBSTRING('01234', 1, 2);
SELECT RIGHT('01234', 2), SUBSTRING('01234', -2);

# LOWER & UPPER example
SELECT LOWER('Hi, My name is'), UPPER ('Hi, My name is');

SELECT title
FROM books
WHERE LOWER(SUBSTRING(title, 1, 3)) = 'the';

# REVERSE example
SELECT REVERSE('01234');

# REPEAT example
SELECT REPEAT('012', 4);

# LOCATE example
SELECT title
FROM books
WHERE LOCATE(' the', title, 3) > 0;

# INSERT example
SELECT INSERT('01234', 2, 0, 'abcs');

# Arithmetic operations and math functions examples
SELECT 5 DIV 2;
SELECT 5 / 2;
SELECT 5 MOD 2, 5 % 2;
SELECT 13 MOD 5;
SELECT PI();
SELECT ABS(-6);
SELECT SQRT(49);
SELECT POW(3, 2);
SELECT CONV(10, 10, 2);
SELECT CONV(1010, 2, 10);
SELECT CONV (1010, 2, 16);

SELECT ROUND(2.356, 2), FLOOR(2.356), CEILING(2.356);
SELECT ROUND(2.356, 2), FLOOR(-2.356), CEILING(-2.356);

SELECT SIGN(-1);

SELECT FLOOR(RAND() * 11);
/*
[0; 1) -> [0; 10]
*11 -> [0; 11)
FLOOR >- [0; 10]

[0;1) -> [12; 25); ?
* (25 + 1 - 12) * 14
[0; 14)
[11; 25]
*/
 
 -- Date functions
 SELECT EXTRACT(HOUR_MICROSECOND FROM '2023-05-16 19:58:04.4565');
 SELECT * FROM books;
 SELECT title, EXTRACT(YEAR FROM year_of_release) AS 'Year of release'
 FROM books;
 
 SELECT ABS(TIMESTAMPDIFF(DAY, '2023-05-16', '2022-05-17'));
 
 SELECT * FROM authors;
 
 SELECT
 id, first_name,
 TIMESTAMPDIFF(YEAR, born, NOW())
 from authors;
 
 INSERT INTO authors(first_name, last_name, born)
 VALUES('Test', 'Last', NOW());
 
 ALTER TABLE authors
 ADD COLUMN stamp TIMESTAMP;
 
 UPDATE authors
 SET stamp = NOW()
 WHERE id = 10;
 
 SELECT DATE_FORMAT(born, '%d %M %Y'), DATE_FORMAT(stamp, '%d %M %Y')
 FROM authors
 WHERE id = 10;
 
 SELECT *
 FROM books
 WHERE title REGEXP '^The';