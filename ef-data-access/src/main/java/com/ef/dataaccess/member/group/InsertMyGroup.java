package com.ef.dataaccess.member.group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.model.member.group.MyGroup;
import com.ef.model.member.group.MyGroupBindingModel;

@Component("insertMyGroup")
public class InsertMyGroup implements Insert<MyGroupBindingModel, MyGroup> {

  private final String INSERT_STATEMENT = "INSERT INTO my_group(creator_id, name, my_group_type_id, description) VALUES (?,?,?,?)";
  private final JdbcTemplate jdbcTemplate;
  private final Query<Integer, Map<Integer, MyGroup>> queryMyGroupsByCreatorId;

  @Autowired
  public InsertMyGroup(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryMyGroupsByCreatorId") Query<Integer, Map<Integer, MyGroup>> queryMyGroupsByCreatorId) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryMyGroupsByCreatorId = queryMyGroupsByCreatorId;
  }

  @Override
  public MyGroup data(MyGroupBindingModel input) {

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      return getInsertPreparedStatement(input, connection);
    }, keyHolder);

    int myGroupId = keyHolder.getKey().intValue();

    return queryMyGroupsByCreatorId.data(input.getCreatorId()).get(myGroupId);
  }

  private final PreparedStatement getInsertPreparedStatement(MyGroupBindingModel input, Connection connection)
      throws SQLException {

    int creatorId = input.getCreatorId();
    // insert in upper case so that we can compare names if the same one is used
    String name = input.getName().toUpperCase();
    int myGroupTypeId = input.getMyGroupTypeId();
    String description = input.getDescription();

    PreparedStatement ps = connection.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
    ps.setInt(1, creatorId);
    ps.setString(2, name);
    ps.setInt(3, myGroupTypeId);
    ps.setString(4, description);

    return ps;
  }
}
