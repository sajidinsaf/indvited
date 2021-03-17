CREATE TABLE my_group (
  id INTEGER IDENTITY,
  creator_id INTEGER,
  name varchar(30),
  my_group_type_id INTEGER,
  description varchar(200),
  creation_timestamp timestamp DEFAULT NOW()
);