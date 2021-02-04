CREATE TABLE event (
 id INTEGER IDENTITY,
 event_type_id INTEGER,
 domain_id INTEGER,
 cap varchar(250),
 exclusions varchar(250),
 created_date TIMESTAMP DEFAULT NOW(),
 member_id INTEGER,
 event_venue_id INTEGER,
 notes varchar(500) 
 );
