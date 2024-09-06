INSERT INTO DATA_SOURCE_CONFIGS(TENANT, DRIVER_CLASS_NAME, URL, USERNAME, PASSWORD)
VALUES ('multi_one', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5432/multi_one', 'postgres', '12345');
INSERT INTO DATA_SOURCE_CONFIGS(TENANT, DRIVER_CLASS_NAME, URL, USERNAME, PASSWORD)
VALUES ('multi_two', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3306/multi_two', 'root', '12345');