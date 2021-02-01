CREATE TABLE member_login_control (
  member_id INTEGER IDENTITY, 
  token varchar(100), 
  creation_timestamp TIMESTAMP DEFAULT NOW(),
  expiry_timestamp TIMESTAMP
);
