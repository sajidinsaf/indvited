CREATE TABLE event_schedule (
 id BIGINT IDENTITY,
 event_id INTEGER,
 start_date Date,
 end_date Date,
 schedule_time varchar(100),
 bloggers_per_day INTEGER, 
 days_of_the_week varchar(13),
 publish_to_inner_circle BOOLEAN DEFAULT FALSE NOT NULL,
 publish_to_my_bloggers BOOLEAN DEFAULT FALSE NOT NULL,
 publish_to_all_eligible BOOLEAN DEFAULT FALSE NOT NULL,
 creation_timestamp TIMESTAMP DEFAULT NOW(),
 scheduled_for_timestamp TIMESTAMP DEFAULT NOW(),
 published_on_timestamp TIMESTAMP
 );
