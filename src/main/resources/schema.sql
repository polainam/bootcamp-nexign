DROP TABLE IF EXISTS abonents;
DROP TABLE IF EXISTS calls;

CREATE TABLE abonents (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          full_name VARCHAR(100) NOT NULL,
                          number VARCHAR(11) UNIQUE,
                          CONSTRAINT check_number_length CHECK (LENGTH(number) = 11)
);

CREATE TABLE calls (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       abonents_id INT REFERENCES abonents(id),
                       type VARCHAR(2)CHECK (type IN ('01', '02')),
                       begin_time BIGINT,
                       end_time BIGINT
)
