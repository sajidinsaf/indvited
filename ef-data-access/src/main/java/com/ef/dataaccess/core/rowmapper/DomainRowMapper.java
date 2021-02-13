package com.ef.dataaccess.core.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.core.Domain;

public class DomainRowMapper implements RowMapper<Domain> {

  @Override
  public Domain mapRow(ResultSet rs, int rowNum) throws SQLException {

    Domain domain = new Domain(rs.getInt("ID"), rs.getString("NAME"));
    return domain;
  }

}
