CREATE TABLE member_registration_control ( 
	member_id INT, 
	registration_code VARCHAR(100), 
	expiry_timestamp TIMESTAMP DEFAULT NOW(), 
	confirmation_timestamp TIMESTAMP
);