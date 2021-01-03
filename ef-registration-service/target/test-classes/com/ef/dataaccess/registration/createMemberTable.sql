CREATE TABLE member (
  id INTEGER IDENTITY, 
  firstname varchar(16), 
  lastname varchar(16),
  username varchar(50),
  password varchar(250),
  email varchar(75),
  phone varchar(12),
  member_type_id INTEGER,
  date_registered TIMESTAMP DEFAULT NOW(),
  timestamp_of_last_login TIMESTAMP DEFAULT NOW()

);
