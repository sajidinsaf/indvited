CREATE TABLE member_deliverable_data (
  member_id INTEGER,
  event_id INTEGER,
  deliverable_id INTEGER,
  deliverable_detail VARCHAR(250)
);
CREATE UNIQUE INDEX idx_mi_ei_di ON member_deliverable_data (member_id, event_id,deliverable_id);