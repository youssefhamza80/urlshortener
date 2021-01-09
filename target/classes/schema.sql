-- URL data table
CREATE TABLE URL(
  URL_ID BIGINT AUTO_INCREMENT PRIMARY KEY,  
  LONG_URL VARCHAR(4000) UNIQUE
);

-- User data table
CREATE TABLE USER
(
	USER_NAME VARCHAR(20) PRIMARY KEY,
	USER_ID BIGINT UNIQUE,
	FULL_NAME VARCHAR(4000),
	EMAIL	VARCHAR2(100),	
	CREATION_DATE TIMESTAMP
);

-- Each operation done on a URL is recorded in this table.
CREATE TABLE URL_OPERATION(
	URL_OPERATION_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
	URL_ID BIGINT,
	USER_NAME VARCHAR(40),
	OPERATION_DATE TIMESTAMP,	
	OPERATION VARCHAR(1)	
);