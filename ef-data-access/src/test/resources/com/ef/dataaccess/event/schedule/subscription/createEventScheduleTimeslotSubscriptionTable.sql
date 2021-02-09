CREATE TABLE event_schedule_timeslot_subscription (
 id bigint IDENTITY,
 event_schedule_timeslot_id bigint,
 subscriber_id int,
 priority int,
 status_id int
 );
