CREATE TABLE event_schedule_subscription (
 id bigint IDENTITY,
 event_schedule_id bigint,
 subscriber_id int,
 schedule_date date,
 preferred_time varchar(4),
 status_id int
 );
