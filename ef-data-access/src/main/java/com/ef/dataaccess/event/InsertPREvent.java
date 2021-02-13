package com.ef.dataaccess.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.ef.common.LRPair;
import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.dataaccess.core.DomainCache;
import com.ef.dataaccess.member.MemberTypeCache;
import com.ef.model.event.EventType;
import com.ef.model.event.EventVenue;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventBindingModel;
import com.ef.model.event.PREventLocationBindingModel;
import com.ef.model.member.Member;
import com.ef.model.member.MemberType;

@Component("insertPREvent")
public class InsertPREvent implements Insert<PREventBindingModel, PREvent> {

  private static final Logger logger = LoggerFactory.getLogger(InsertPREvent.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String INSERT_EVENT_STATEMENT = "INSERT INTO event(event_type_id, domain_id, cap, exclusions, member_id, event_venue_id, notes) VALUES (?,?,?,?,?,?,?)";

  private final JdbcTemplate jdbcTemplate;
  private final Query<Integer, PREvent> queryEventById;
  private final EventTypeCache eventTypeCache;
  private final DomainCache domainCache;
  private final Insert<Pair<PREventBindingModel, PREvent>, PREvent> insertEventCriteria;
  private final Insert<Pair<PREventBindingModel, PREvent>, PREvent> insertEventDeliverables;
  private final Query<PREventLocationBindingModel, EventVenue> queryVenueByKeyFieldOrInsert;
  private final Query<Integer, Member> queryMemberById;
  private final MemberTypeCache memberTypeCache;

  @Autowired
  public InsertPREvent(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryEventById") Query<Integer, PREvent> queryEventById, EventTypeCache eventTypeCache,
      DomainCache domainCache,
      @Qualifier("insertPREventCriteria") Insert<Pair<PREventBindingModel, PREvent>, PREvent> insertEventCriteria,
      @Qualifier("insertPREventDeliverables") Insert<Pair<PREventBindingModel, PREvent>, PREvent> insertEventDeliverables,
      @Qualifier("queryVenueByKeyFieldOrInsert") Query<PREventLocationBindingModel, EventVenue> queryVenueByKeyFieldOrInsert,
      @Qualifier("queryMemberById") Query<Integer, Member> queryMemberById, MemberTypeCache memberTypeCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryEventById = queryEventById;
    this.eventTypeCache = eventTypeCache;
    this.domainCache = domainCache;
    this.insertEventCriteria = insertEventCriteria;
    this.insertEventDeliverables = insertEventDeliverables;
    this.queryVenueByKeyFieldOrInsert = queryVenueByKeyFieldOrInsert;
    this.queryMemberById = queryMemberById;
    this.memberTypeCache = memberTypeCache;
  }

  @Override
  public PREvent data(final PREventBindingModel input) {

    logUtil.debug(logger, " Input Event Details: ", input);
    Member member = null;
    int memberId = input.getMemberId();
    try {
      member = queryMemberById.data(memberId);
    } catch (EmptyResultDataAccessException e) {
      logUtil.warn(logger, "No member information found for memberId: ", memberId, " Input Event Details: ", input);
      throw new RuntimeException(
          "No member information found for memberId: " + memberId + " Input Event Details: " + input, e);
    }

    if (!memberTypeCache.getMemberType(MemberType.KNOWN_MEMBER_TYPE_PR).equals(member.getMemberType())) {
      throw new RuntimeException("Invalid member type attempting to create event: " + input);
    }
    final EventVenue eventVenue = queryVenueByKeyFieldOrInsert.data(input.getEventLocation());

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      return getInsertEventPreparedStatement(input, eventVenue, connection);
    }, keyHolder);

    int eventId = keyHolder.getKey().intValue();

    final PREvent event = queryEventById.data(eventId);

    event.setEventType(eventTypeCache.getEventType(getEventTypeId(input)));

    Pair<PREventBindingModel, PREvent> eventPair = new LRPair<PREventBindingModel, PREvent>(input, event);
    insertEventCriteria.data(eventPair);
    insertEventDeliverables.data(eventPair);

    event.setMember(member);
    event.setEventVenue(eventVenue);
    return event;
  }

  private final PreparedStatement getInsertEventPreparedStatement(PREventBindingModel input, EventVenue eventVenue,
      Connection connection) throws SQLException {
    int memberId = input.getMemberId();
    input.getEventLocation().setId(eventVenue.getId());

    Integer eventTypeId = getEventTypeId(input);
    logUtil.debug(logger, "EventType Id:", eventTypeId);

    EventType eventType = eventTypeCache.getEventType(input.getEventType());
    if (eventTypeId == -1) {
      logUtil.warn(logger, "Invalid event type. No mapping entry found. ", input.getEventType(),
          " Input Event Details: ", input);
      throw new RuntimeException(
          "Invalid event type. No mapping entry found. " + input.getEventType() + " Input Event Details: " + input);

    }

    logUtil.debug(logger, eventType);

    Integer domainId = domainCache.getDomain(eventType.getDomainId()).getId();

    logUtil.debug(logger, "Domain Id:", domainId);

    String cap = input.getCap() == null ? "" : input.getCap();
    String exclusions = input.getExclusions();

    Integer eventVenueId = eventVenue.getId();
    String notes = input.getNotes() == null ? "" : input.getNotes();

    PreparedStatement ps = connection.prepareStatement(INSERT_EVENT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
    ps.setInt(1, eventTypeId);
    ps.setInt(2, domainId);
    ps.setString(3, cap);
    ps.setString(4, exclusions);
    ps.setInt(5, memberId);
    ps.setInt(6, eventVenueId);
    ps.setString(7, notes);
    return ps;
  }

  private int getEventTypeId(PREventBindingModel input) {
    EventType eventType = eventTypeCache.getEventType(input.getEventType());
    if (eventType == null) {
      logUtil.warn(logger, "Invalid event type. No mapping entry found. ", input.getEventType(),
          " Input Event Details: ", input);
      return -1;

    }
    return eventType.getId();
  }

}
