package com.ef.dataaccess.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.spring.rowmapper.event.ForumRowMapper;
import com.ef.model.core.Forum;

@Component
public class ForumCache {

  private final String SELECT_FORUM = "select id, name, base_url from forum";

  private final JdbcTemplate jdbcTemplate;

  private final Map<String, Forum> nameToForumMap;
  private final Map<Integer, Forum> idToForumMap;

  @Autowired
  public ForumCache(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    nameToForumMap = new HashMap<String, Forum>();
    idToForumMap = new HashMap<Integer, Forum>();
    refreshCache();
  }

  public Forum getForum(String forumName) {
    return nameToForumMap.get(forumName);
  }

  public Forum getForum(int forumId) {
    return idToForumMap.get(forumId);
  }

  private void refreshCache() {
    List<Forum> forumList = jdbcTemplate.query(SELECT_FORUM, new ForumRowMapper());
    for (Forum forum : forumList) {
      nameToForumMap.put(forum.getName(), forum);
      idToForumMap.put(forum.getId(), forum);
    }
  }

  public List<Forum> getForums() {
    return new ArrayList<Forum>(idToForumMap.values());
  }

  @Override
  public String toString() {
    return "ForumCache [nameToForumMap=" + nameToForumMap + ", idToForumMap=" + idToForumMap + "]";
  }

}
