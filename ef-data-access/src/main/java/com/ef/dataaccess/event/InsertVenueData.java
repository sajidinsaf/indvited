package com.ef.dataaccess.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Insert;
import com.ef.model.event.PREventLocationBindingModel;

@Component("insertVenueData")
public class InsertVenueData implements Insert<PREventLocationBindingModel, Boolean> {

  private final String INSERT_STATEMENT = "INSERT INTO venue(name, address, zomato_url, venue_url) VALUES (?,?,?,?)";
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public InsertVenueData(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;

  }

  @Override
  public Boolean data(PREventLocationBindingModel eventVenueData) {

    String venueName = eventVenueData.getVenueName();
    String venueAddress = eventVenueData.getVenueAddress();
    String zomatoUrl = eventVenueData.getZomatoUrl();
    String venueUrl = eventVenueData.getVenueUrl();
    jdbcTemplate.update(INSERT_STATEMENT, new Object[] { venueName, venueAddress, zomatoUrl, venueUrl });

    return true;
  }

}
