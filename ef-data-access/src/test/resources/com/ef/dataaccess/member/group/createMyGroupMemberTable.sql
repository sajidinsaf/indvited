CREATE TABLE my_group_member (
  my_group_id INTEGER,
  member_id INTEGER,
  added_timestamp TIMESTAMP DEFAULT NOW()
);