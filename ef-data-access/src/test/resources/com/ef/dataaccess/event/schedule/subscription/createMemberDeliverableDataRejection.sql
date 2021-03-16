CREATE TABLE member_deliverable_data_rejection (
  event_id INTEGER,
  member_id INTEGER,
  rejection_comment varchar(500),
  created_timestamp timestamp DEFAULT NOW()
);