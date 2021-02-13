package com.ef.dataaccess.spring.rowmapper.event;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.dataaccess.event.DomainCache;
import com.ef.dataaccess.event.ForumCache;
import com.ef.model.core.DomainForum;

public class DomainForumRowMapper implements RowMapper<DomainForum> {

  private final DomainCache domainCache;
  private final ForumCache forumCache;

  public DomainForumRowMapper(DomainCache domainCache, ForumCache forumCache) {
    super();
    this.domainCache = domainCache;
    this.forumCache = forumCache;
  }

  @Override
  public DomainForum mapRow(ResultSet rs, int rowNum) throws SQLException {
    int domainId = rs.getInt("domain_id");
    int forumId = rs.getInt("forum_id");

    DomainForum domainForum = new DomainForum(rs.getInt("ID"), domainCache.getDomain(domainId),
        forumCache.getForum(forumId));
    return domainForum;
  }

}
