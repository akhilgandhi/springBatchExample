DROP TABLE springBatchData IF EXISTS;

CREATE TABLE springBatchData (
    data_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    subscriber_id VARCHAR(8),
    prod_name VARCHAR(10),
    amount NUMERIC(10,2)
);

