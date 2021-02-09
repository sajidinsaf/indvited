package com.ef.dataaccess.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.spring.member.rowmapper.DomainRowMapper;
import com.ef.model.core.Domain;

@Component
public class DomainCache {

  private final String SELECT_DOMAIN = "select id, name from domain";

  private final JdbcTemplate jdbcTemplate;

  private final Map<String, Domain> nameToDomainMap;
  private final Map<Integer, Domain> idToDomainMap;

  @Autowired
  public DomainCache(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    nameToDomainMap = new HashMap<String, Domain>();
    idToDomainMap = new HashMap<Integer, Domain>();
    refreshCache();
  }

  public Domain getDomain(String domainName) {
    return nameToDomainMap.get(domainName);
  }

  public Domain getDomain(int domainId) {
    return idToDomainMap.get(domainId);
  }

  private void refreshCache() {
    List<Domain> domainList = jdbcTemplate.query(SELECT_DOMAIN, new DomainRowMapper());
    for (Domain domain : domainList) {
      nameToDomainMap.put(domain.getName(), domain);
      idToDomainMap.put(domain.getId(), domain);
    }
  }

  public List<Domain> getDomains() {
    return new ArrayList<Domain>(idToDomainMap.values());
  }
}
