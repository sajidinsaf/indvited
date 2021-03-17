package com.ef.dataaccess.member.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.member.group.MyGroup;

@Component(value = "queryMyGroupByGroupId")
public class QueryMyGroupByGroupId implements Query<Integer, MyGroup> {

  private final String SELECT_MYGROUP = "select * from my_group where id=%d";

  private final JdbcTemplate jdbcTemplate;
  private final MyGroupTypeCache myGroupTypeCache;

  @Autowired
  public QueryMyGroupByGroupId(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      MyGroupTypeCache myGroupTypeCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.myGroupTypeCache = myGroupTypeCache;
  }

  @Override
  public MyGroup data(final Integer groupId) {

    MyGroup myGroup = jdbcTemplate.query(String.format(SELECT_MYGROUP, groupId),
        (rs, rowNum) -> new MyGroup(rs.getInt("ID"), rs.getInt("CREATOR_ID"),
            myGroupTypeCache.getMemberType(rs.getInt("MY_GROUP_TYPE_ID")), rs.getString("NAME"),
            rs.getString("DESCRIPTION"), rs.getTimestamp("CREATION_TIMESTAMP")))
        .get(0);

    return myGroup;
  }

}
