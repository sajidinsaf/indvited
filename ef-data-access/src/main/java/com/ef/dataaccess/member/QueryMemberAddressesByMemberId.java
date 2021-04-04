package com.ef.dataaccess.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.member.MemberAddress;

@Component(value = "queryMemberAddressesByMemberId")
public class QueryMemberAddressesByMemberId implements Query<Integer, List<MemberAddress>> {

  private final String SELECT_MEMBER_CRITERIA_DATA = "select * from member_address where member_id=%d";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QueryMemberAddressesByMemberId(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;

  }

  @Override
  public List<MemberAddress> data(Integer memberId) {

    List<MemberAddress> memberAddresses = jdbcTemplate.query(String.format(SELECT_MEMBER_CRITERIA_DATA, memberId),
        (rs, rowNum) -> new MemberAddress(rs.getInt("ID"), memberId, rs.getString("ADDR_LINE1"),
            rs.getString("ADDR_LINE2"), rs.getString("ADDR_LINE3"), rs.getString("CITY"), rs.getString("COUNTRY"),
            rs.getString("PINCODE"), rs.getBoolean("IS_CURRENT")));

    return memberAddresses;
  }

}
