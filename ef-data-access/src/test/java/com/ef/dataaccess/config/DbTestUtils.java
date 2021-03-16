package com.ef.dataaccess.config;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public class DbTestUtils {

  public DbTestUtils() {
  }

  public EmbeddedDatabaseBuilder addCreateScripts(EmbeddedDatabaseBuilder embeddedDatabaseBuilder) {
    embeddedDatabaseBuilder.addScript("classpath:com/ef/dataaccess/member/createMemberLoginControlTable.sql")
        .addScript("classpath:com/ef/dataaccess/member/createMemberCriteriaDataTable.sql")
        .addScript("classpath:com/ef/dataaccess/member/createMemberTable.sql")
        .addScript("classpath:com/ef/dataaccess/member/createMemberTypeTable.sql")
        .addScript("classpath:com/ef/dataaccess/member/createMemberRegistrationControlTable.sql")
        .addScript("classpath:com/ef/dataaccess/member/createMemberAddressTable.sql")
        .addScript("classpath:com/ef/dataaccess/member/createMemberDomainTable.sql")
        .addScript("classpath:com/ef/dataaccess/core/createDomainTable.sql")
        .addScript("classpath:com/ef/dataaccess/core/createForumTable.sql")
        .addScript("classpath:com/ef/dataaccess/core/createDomainForumTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createEventCriteriaMetaTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createEventCriteriaDataTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createEventDeliverableMetaTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createEventDeliverableDataTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createEventTypeTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createVenueTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createEventTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createEventScheduleTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createEventTimeSlotTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createEventStatusMetaTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/schedule/subscription/createEventScheduleSubscriptionTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/createMemberEventDeliverableDataTable.sql")
        .addScript("classpath:com/ef/dataaccess/event/schedule/subscription/createMemberDeliverableDataRejection.sql");
    return embeddedDatabaseBuilder;
  }
}
