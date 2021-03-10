INSERT INTO event_status_meta (id, name, display_name, description) VALUES 
(1, 'CREATED', 'AVAILABLE', 'The event has been created'),
(2, 'ELIGIBLE', 'ELIGIBLE', 'Event timeslot has been subscribed'),
(3, 'APPLIED', 'APPLIED', 'Blogger has applied for an event timeslot'),
(4, 'APPROVED', 'APPROVED', 'PR has approved blogger application'),
(5, 'REJECTED', 'UNAVAILABLE', 'The PR has rejected the application for the event timeslot'),
(6, 'APPROVAL_CANCELLED', 'APPROVAL_REVOKED', 'The PR has cancelled the approval of the application for timeslot'),
(7, 'EVENT_CANCELLED', 'EVENT_CANCELLED', 'The entire event or schedule has been cancelled'),
(8, 'CLOSED', 'CLOSED', 'The subscription has been closed');