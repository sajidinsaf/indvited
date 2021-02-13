CREATE TABLE member_domain (
  id INTEGER IDENTITY,
  member_id INTEGER,
  domain_forum_id INTEGER,
  forum_url varchar(200)
);