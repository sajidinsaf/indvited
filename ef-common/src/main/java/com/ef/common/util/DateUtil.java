package com.ef.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
  public static final String EVENT_DISPLAY_DATE_FORMAT = System
      .getProperty("event.schedule.display.date.format") != null
          ? System.getProperty("event.schedule.display.date.format")
          : "EEE d MMM yyyy";

  public static final String yyyy_dash_MM_dash_dd_format = "yyyy-MM-dd";

  public String formatDateForEventDisplay(Date date) {
    return formatDate(date, EVENT_DISPLAY_DATE_FORMAT);
  }

  public String formatDate(Date date, String format) {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    return formatter.format(date);
  }

  public java.sql.Date parseSqlDateFromEventDisplayString(String date, String dateFormat) {
    return new java.sql.Date(parseDate(date, dateFormat).getTime());
  }

  public java.sql.Date parseSqlDateFromEventDisplayString(String date) {
    return new java.sql.Date(parseDateFromEventDisplayString(date).getTime());
  }

  public Date parseDateFromEventDisplayString(String date) {
    return parseDate(date, EVENT_DISPLAY_DATE_FORMAT);
  }

  public Date parseDate(String date, String dateFormat) {
    SimpleDateFormat format = new SimpleDateFormat(dateFormat);
    java.util.Date parsed = null;
    try {
      parsed = format.parse(date);
    } catch (ParseException e) {
      throw new RuntimeException("Exception while parsing event time slot date: " + date + " with format" + dateFormat
          + "The date format can be specified with the system property 'event.schedule.date.format'");
    }
    return new Date(parsed.getTime());
  }

  public String convertDateString(String timeString, String origFormat, String newFormat) {
    try {
      final Date dateObj = new SimpleDateFormat(origFormat).parse(timeString);
      return new SimpleDateFormat(newFormat).format(dateObj);
    } catch (final ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
