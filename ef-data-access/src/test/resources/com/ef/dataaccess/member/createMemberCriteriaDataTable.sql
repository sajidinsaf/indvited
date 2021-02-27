CREATE TABLE member_criteria_data (
  id INTEGER IDENTITY, 
  member_id INT, 
  criteria_meta_id INT,
  member_criteria_value INT,
  timestamp_of_last_update TIMESTAMP DEFAULT NOW()
);
