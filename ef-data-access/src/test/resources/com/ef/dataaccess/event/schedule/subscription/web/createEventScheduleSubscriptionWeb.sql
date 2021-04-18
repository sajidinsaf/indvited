CREATE TABLE event_schedule_subscription_web (
  id Integer IDENTITY,
  event_id INTEGER,
  event_schedule_id bigint,
  first_name varchar(25),
  last_name varchar(25),
  email varchar(75),
  phone varchar(10),
  address varchar(500),
  city varchar(30),
  gender char(1),
  preferred_date date,
  preferred_time char(5),
  criteria_string varchar(500),
  status_id INTEGER,
  entry_timestamp timestamp
);