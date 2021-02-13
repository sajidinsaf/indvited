package com.ef.dataaccess.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Insert;
import com.ef.model.member.MemberDomain;
import com.ef.model.member.MemberDomainForumBindingModel;

@Component("insertMemberDomainForum")
public class InsertMemberDomainForum implements Insert<MemberDomainForumBindingModel, MemberDomain> {

  private static final Logger logger = LoggerFactory.getLogger(InsertMemberDomainForum.class);

  private final ServiceLoggingUtil loggingUtil = new ServiceLoggingUtil();
  private final String INSERT_STATEMENT = "INSERT INTO member_domain(member_id, domain_forum_id, forum_url) VALUES (?,?,?) ";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public InsertMemberDomainForum(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public MemberDomain data(MemberDomainForumBindingModel input) {

    KeyHolder keyHolder = new GeneratedKeyHolder();

    // Insert the PR Event schedule and get the schedule id.
    jdbcTemplate.update(connection -> {
      return getPreparedStatement(input, connection);
    }, keyHolder);

    int memberDomainId = keyHolder.getKey().intValue();
    MemberDomain memberDomain = new MemberDomain(memberDomainId, input.getMemberId(), input.getDomainForumId(),
        input.getMemberForumUrl());
    loggingUtil.debug(logger, "Created member domain forum entry: ", memberDomain);

    return memberDomain;
  }

  private final PreparedStatement getPreparedStatement(MemberDomainForumBindingModel input, Connection connection)
      throws SQLException {

    PreparedStatement ps = connection.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
    ps.setInt(1, input.getMemberId());
    ps.setInt(2, input.getDomainForumId());
    ps.setString(3, input.getMemberForumUrl());
    return ps;
  }
}
