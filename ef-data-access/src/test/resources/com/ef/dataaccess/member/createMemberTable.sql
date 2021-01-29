CREATE TABLE member (
  id INTEGER IDENTITY, 
  firstname varchar(16), 
  lastname varchar(16),
  password varchar(250),
  email varchar(75),
  gender varchar(1),
  phone VARCHAR(12),
  member_type_id INTEGER,
  date_registered TIMESTAMP DEFAULT NOW(),
  timestamp_of_last_login TIMESTAMP DEFAULT NOW(),
  is_enabled BOOLEAN DEFAULT FALSE NOT NULL
);
