CREATE TABLE IF NOT EXISTS log
(
    log_id INT NOT NULL,
    endpoint varchar (250) NOT NULL,
    response varchar (250) NOT NULL,
    http_status INT NOT NULL,
    PRIMARY KEY (log_id)
);