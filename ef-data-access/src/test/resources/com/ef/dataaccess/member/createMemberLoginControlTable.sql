CREATE TABLE member_login_control (
  member_email_id varchar(100), 
  token varchar(100), 
  creation_timestamp TIMESTAMP DEFAULT NOW(),
  expiry_timestamp TIMESTAMP
);
