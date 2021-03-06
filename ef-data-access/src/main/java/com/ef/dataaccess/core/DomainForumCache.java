package com.ef.dataaccess.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.core.rowmapper.DomainForumRowMapper;
import com.ef.model.core.Domain;
import com.ef.model.core.DomainForum;
import com.ef.model.core.Forum;

@Component
public class DomainForumCache {

  private final String SELECT_DOMAIN = "select * from domain_forum";

  private final JdbcTemplate jdbcTemplate;

  private Map<Integer, Set<Forum>> domainIdToForumsMap;
  private Map<Integer, Set<Domain>> forumIdToDomainsMap;
  private List<DomainForum> domainForums;
  private Map<String, DomainForum> domainForumIdToDomainForumMap;

  @Autowired
  public DomainForumCache(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate, DomainCache domainCache,
      ForumCache forumCache) {
    this.jdbcTemplate = jdbcTemplate;
    refreshCache(domainCache, forumCache);
  }

  public Set<Forum> getForumsByDomainId(int domainId) {
    return domainIdToForumsMap.get(domainId);
  }

  public Set<Domain> getDomainsByForumId(int forumId) {
    return forumIdToDomainsMap.get(forumId);
  }

  public DomainForum getDomainForum(int domainId, int forumId) {
    String domainForumId = getDomainForumId(domainId, forumId);
    return domainForumIdToDomainForumMap.get(domainForumId);
  }

  private void refreshCache(DomainCache domainCache, ForumCache forumCache) {

    domainIdToForumsMap = new HashMap<Integer, Set<Forum>>();
    forumIdToDomainsMap = new HashMap<Integer, Set<Domain>>();
    domainForumIdToDomainForumMap = new HashMap<String, DomainForum>();

    domainForums = jdbcTemplate.query(SELECT_DOMAIN, new DomainForumRowMapper(domainCache, forumCache));

    for (DomainForum df : domainForums) {
      Set<Domain> domainSet = getOrNew(df.getForum().getId(), forumIdToDomainsMap);
      Set<Forum> forumSet = getOrNew(df.getDomain().getId(), domainIdToForumsMap);
      domainSet.add(df.getDomain());
      forumSet.add(df.getForum());
      domainIdToForumsMap.put(df.getDomain().getId(), forumSet);
      forumIdToDomainsMap.put(df.getForum().getId(), domainSet);

      String domainForumId = getDomainForumId(df.getDomain().getId(), df.getForum().getId());
      domainForumIdToDomainForumMap.put(domainForumId, df);

    }
  }

  private <T> Set<T> getOrNew(int id, Map<Integer, Set<T>> map) {
    Set<T> list = map.get(id);
    if (list == null) {
      list = new HashSet<T>();
    }
    return list;
  }

  private String getDomainForumId(int domainId, int forumId) {
    return domainId + "_" + forumId;
  }

}
