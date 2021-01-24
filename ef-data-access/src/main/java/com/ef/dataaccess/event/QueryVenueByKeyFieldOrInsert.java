package com.ef.dataaccess.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.member.rowmapper.EventVenueRowMapper;
import com.ef.model.event.EventVenue;
import com.ef.model.event.PREventLocationBindingModel;

@Component(value = "queryVenueByKeyFieldOrInsert")
public class QueryVenueByKeyFieldOrInsert implements Query<PREventLocationBindingModel, EventVenue> {

  private static final Logger logger = LoggerFactory.getLogger(QueryVenueByKeyFieldOrInsert.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String SELECT_VENUE = "select * from venue where %s=?";

  private final JdbcTemplate jdbcTemplate;
  private final Insert<PREventLocationBindingModel, Boolean> insertVenueLocation;

  @Autowired
  public QueryVenueByKeyFieldOrInsert(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("insertVenueData") Insert<PREventLocationBindingModel, Boolean> insertVenueLocation) {

    this.jdbcTemplate = jdbcTemplate;
    this.insertVenueLocation = insertVenueLocation;
  }

  @Override
  public EventVenue data(PREventLocationBindingModel eventData) {

    try {
      EventVenue eventVenue = queryVenue(eventData);

      logUtil.debug(logger, "query found venue: ", eventVenue);

      return eventVenue;

    } catch (EmptyResultDataAccessException e) {

      logUtil.debug(logger, "no venue found", e, "creating new venue entry");

      insertVenue(eventData);

      EventVenue eventVenue = queryVenue(eventData);

      logUtil.debug(logger, "returning newly created venue: ", eventVenue);

      return eventVenue;
    }

  }

  private void insertVenue(PREventLocationBindingModel eventData) {
    insertVenueLocation.data(eventData);
  }

  private EventVenue queryVenue(PREventLocationBindingModel eventVenueData) {

    String columnName = eventVenueData.getZomatoUrl() != null ? "zomato_url"
        : eventVenueData.getVenueUrl() != null ? "venue_url" : "name";

    String value = eventVenueData.getZomatoUrl() != null ? eventVenueData.getZomatoUrl()
        : eventVenueData.getVenueUrl() != null ? eventVenueData.getVenueUrl() : eventVenueData.getVenueName();

    logUtil.debug(logger, "determined key field: ", columnName, " with value: ", value);

    String query = String.format(SELECT_VENUE, columnName);
    EventVenue eventVenue = jdbcTemplate.queryForObject(query, new Object[] { value }, new EventVenueRowMapper());
    return eventVenue;
  }
}
