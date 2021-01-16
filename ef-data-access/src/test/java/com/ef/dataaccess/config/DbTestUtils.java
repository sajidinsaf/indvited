package com.ef.dataaccess.config;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public class DbTestUtils {

  public DbTestUtils() {
    // TODO Auto-generated constructor stub
  }

  public EmbeddedDatabaseBuilder addCreateScripts(EmbeddedDatabaseBuilder embeddedDatabaseBuilder) {
    embeddedDatabaseBuilder.addScript("classpath:com/ef/dataaccess/event/createDomainTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createEventTypeTable.sql")
        .addScript("classpath:com/ef/dataaccess/member/createMemberCriteriaDataTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createEventCriteriaMetaTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createEventCriteriaDataTable.sql")
        .addScript("classpath:com/ef/dataaccess/member/createMemberTable.sql")
        .addScript("classpath:com/ef/dataaccess/member/createMemberTypeTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createEventTimeSlotTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createVenueTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createEventTable.sql");
    return embeddedDatabaseBuilder;
  }
}
