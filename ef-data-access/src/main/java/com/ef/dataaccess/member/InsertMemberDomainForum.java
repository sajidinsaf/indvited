package com.ef.dataaccess.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
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
  private final String UPDATE_STATEMENT = "UPDATE member_domain SET forum_url=? where member_id=? and domain_forum_id=?";

  private final String QUERY = "SELECT id from member_domain where member_id=? and domain_forum_id=?";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public InsertMemberDomainForum(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public MemberDomain data(MemberDomainForumBindingModel input) {

    int memberId = input.getMemberId();
    int domainForumId = input.getDomainForumId();

    int memberDomainId = -1;
    try {
      jdbcTemplate.queryForObject(QUERY, new Object[] { memberId, domainForumId }, Integer.class);
      // Update the PR Event schedule and get the schedule id.
      memberDomainId = jdbcTemplate.update(connection -> {
        return getUpdateStatement(input, connection);
      });
    } catch (EmptyResultDataAccessException e) {
      // Insert the PR Event schedule and get the schedule id.
      KeyHolder keyHolder = new GeneratedKeyHolder();

      jdbcTemplate.update(connection -> {
        return getInsertStatement(input, connection);
      }, keyHolder);
      memberDomainId = keyHolder.getKey().intValue();
    }

    MemberDomain memberDomain = new MemberDomain(memberDomainId, memberId, domainForumId, input.getMemberForumUrl());
    loggingUtil.debug(logger, "Created member domain forum entry: ", memberDomain);

    return memberDomain;
  }

  private final PreparedStatement getInsertStatement(MemberDomainForumBindingModel input, Connection connection)
      throws SQLException {

    PreparedStatement ps = connection.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
    ps.setInt(1, input.getMemberId());
    ps.setInt(2, input.getDomainForumId());
    ps.setString(3, input.getMemberForumUrl());
    return ps;
  }

  private final PreparedStatement getUpdateStatement(MemberDomainForumBindingModel input, Connection connection)
      throws SQLException {

    PreparedStatement ps = connection.prepareStatement(UPDATE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
    ps.setString(1, input.getMemberForumUrl());
    ps.setInt(2, input.getMemberId());
    ps.setInt(3, input.getDomainForumId());

    return ps;
  }
}
