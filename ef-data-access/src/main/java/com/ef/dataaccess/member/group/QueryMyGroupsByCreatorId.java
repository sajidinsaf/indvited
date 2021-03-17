package com.ef.dataaccess.member.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.member.group.MyGroup;

@Component(value = "queryMyGroupsByCreatorId")
public class QueryMyGroupsByCreatorId implements Query<Integer, Map<Integer, MyGroup>> {

  private final String SELECT_MYGROUP = "select * from my_group where creator_id=%d";

  private final JdbcTemplate jdbcTemplate;
  private final MyGroupTypeCache myGroupTypeCache;

  @Autowired
  public QueryMyGroupsByCreatorId(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      MyGroupTypeCache myGroupTypeCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.myGroupTypeCache = myGroupTypeCache;
  }

  @Override
  public Map<Integer, MyGroup> data(final Integer creatorId) {

    List<MyGroup> myGroups = jdbcTemplate.query(String.format(SELECT_MYGROUP, creatorId),
        (rs, rowNum) -> new MyGroup(rs.getInt("ID"), rs.getInt("CREATOR_ID"),
            myGroupTypeCache.getMemberType(rs.getInt("MY_GROUP_TYPE_ID")), rs.getString("NAME"),
            rs.getString("DESCRIPTION"), rs.getTimestamp("CREATION_TIMESTAMP")));

    Map<Integer, MyGroup> map = new HashMap<Integer, MyGroup>();

    for (MyGroup group : myGroups) {
      map.put(group.getId(), group);
    }
    return map;
  }

}
