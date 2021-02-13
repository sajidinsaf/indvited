package com.ef.dataaccess.core.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.core.Forum;

public class ForumRowMapper implements RowMapper<Forum> {

  @Override
  public Forum mapRow(ResultSet rs, int rowNum) throws SQLException {

    Forum forum = new Forum(rs.getInt("ID"), rs.getString("NAME"), rs.getString("BASE_URL"));
    return forum;
  }

}
