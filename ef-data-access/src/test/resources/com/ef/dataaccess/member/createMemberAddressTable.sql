CREATE TABLE member_address (
  id INTEGER IDENTITY,
  member_id INTEGER,
  addr_line1 varchar(100),
  addr_line2 varchar(100),
  addr_line3 varchar(100),
  city varchar(50),
  country varchar(30),
  pincode varchar(20),
  creation_timestamp timestamp,
  is_current BOOLEAN DEFAULT FALSE NOT NULL
);
