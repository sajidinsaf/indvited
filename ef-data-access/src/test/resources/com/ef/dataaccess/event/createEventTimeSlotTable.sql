CREATE TABLE event_schedule_timeslot (
 id BIGINT IDENTITY,
 event_schedule_id BIGINT,
 start_time char(4),
 end_time char(4)
 );