CREATE TABLE event_schedule (
 id BIGINT IDENTITY,
 event_id INTEGER,
 start_date Date,
 end_date Date,
 days_of_the_week varchar(13),
 creation_timestamp TIMESTAMP DEFAULT NOW(),
 scheduled_for_timestamp TIMESTAMP DEFAULT NOW(),
 published_on_timestamp TIMESTAMP
 );
