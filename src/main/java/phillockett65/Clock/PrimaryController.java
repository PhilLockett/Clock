package phillockett65.Clock;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PrimaryController {
	static final int HALF_DAY = 12 * 60 * 60;
	static final int FULL_DAY = 2 * HALF_DAY;
	static final int EPOCH = 2451545;

	Set<String> allZones = ZoneId.getAvailableZoneIds();

	// Create a List using the set of zones and sort it.
	List<String> zoneList = new ArrayList<String>(allZones);
//	Collections.sort(zoneList);
	
    @FXML    void dumpTimeZones(ActionEvent event) {
    	LocalDateTime dt = LocalDateTime.now();
    	for (String s : zoneList) {
    	    ZoneId zone = ZoneId.of(s);
    	    ZonedDateTime zdt = dt.atZone(zone);
    	    ZoneOffset offset = zdt.getOffset();
    	    int secondsOfHour = offset.getTotalSeconds() % (60 * 60);
    	    String out = String.format("%35s %10s%n", zone, offset);
            System.out.printf(out);
    	}
	}
	
    @FXML    void testZonedDateTime(ActionEvent event) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy  hh:mm a");

		// Leaving from San Francisco on July 20, 2013, at 7:30 p.m.
		LocalDateTime leaving = LocalDateTime.of(2013, Month.JULY, 20, 19, 30);
		ZoneId leavingZone = ZoneId.of("America/Los_Angeles"); 
		ZonedDateTime departure = ZonedDateTime.of(leaving, leavingZone);

		try {
		    String out1 = departure.format(format);
		    System.out.printf("LEAVING:  %s (%s)%n", out1, leavingZone);
		} catch (DateTimeException exc) {
		    System.out.printf("%s can't be formatted!%n", departure);
		    throw exc;
		}

		// Flight is 10 hours and 50 minutes, or 650 minutes
		ZoneId arrivingZone = ZoneId.of("Asia/Tokyo"); 
		ZonedDateTime arrival = departure.withZoneSameInstant(arrivingZone).plusMinutes(650);

		try {
		    String out2 = arrival.format(format);
		    System.out.printf("ARRIVING: %s (%s)%n", out2, arrivingZone);
		} catch (DateTimeException exc) {
		    System.out.printf("%s can't be formatted!%n", arrival);
		    throw exc;
		}

		if (arrivingZone.getRules().isDaylightSavings(arrival.toInstant())) 
		    System.out.printf("  (%s daylight saving time will be in effect.)%n", arrivingZone);
		else
		    System.out.printf("  (%s standard time will be in effect.)%n", arrivingZone);
	}
	
    @FXML    void testOffsetDateTime(ActionEvent event) {
		// Find the last Thursday in July 2013.
		LocalDateTime localDate = LocalDateTime.of(2021, Month.MARCH, 20, 19, 30);
		ZoneOffset offset = ZoneOffset.of("-08:00");

		OffsetDateTime offsetDate = OffsetDateTime.of(localDate, offset);
		OffsetDateTime lastThursday = offsetDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		System.out.printf("The last Thursday in March 2021 is the %sth.%n", lastThursday.getDayOfMonth());
	}
	
    @FXML    void testOffsetTime(ActionEvent event) {
		// Find the last Thursday in July 2013.
		LocalTime localDate = LocalTime.of(19, 30);
		ZoneOffset offsetUT = ZoneOffset.of("Z");

		LocalDateTime dt = LocalDateTime.now();
		ZoneId zone = ZoneId.systemDefault();
	    ZonedDateTime zdt = dt.atZone(zone);
	    ZoneOffset offsetLocal = zdt.getOffset();
	    int seconds = offsetLocal.getTotalSeconds();
	    
	    System.out.println("offsetUT " + offsetUT.getTotalSeconds() + "  offsetLocal " + offsetLocal.getTotalSeconds());

		Instant timestamp = Instant.now();
		long secondsFromEpoch = Instant.ofEpochSecond(0L).until(Instant.now(), ChronoUnit.SECONDS);
		long daysFromEpoch = Instant.ofEpochSecond(0L).until(Instant.now(), ChronoUnit.DAYS);
	    System.out.println("secondsFromEpoch " + secondsFromEpoch);
	    System.out.println("daysFromEpoch " + daysFromEpoch);
		long secondsDelta = secondsFromEpoch - (daysFromEpoch * FULL_DAY);
	    System.out.println("secondsDelta " + secondsDelta);
	    

	    LocalDateTime ldt = LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault());
		System.out.printf("%s %d %d at %d:%02d%n", ldt.getMonth(), ldt.getDayOfMonth(), ldt.getYear(), ldt.getHour(), ldt.getMinute());

		OffsetTime offsetDate = OffsetTime.of(localDate, offsetLocal);
//		OffsetTime lastThursday = offsetDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
//		System.out.printf("The last Thursday in March 2021 is the %sth.%n", lastThursday.getDayOfMonth());
	}
	
    @FXML    void testBirthday(ActionEvent event) {
		LocalDate today = LocalDate.now();
		LocalDate birthday = LocalDate.of(1965, Month.DECEMBER, 24);

		Period p = Period.between(birthday, today);
		long p2 = ChronoUnit.DAYS.between(birthday, today);
		System.out.println("You are " + p.getYears() + " years, " + p.getMonths() + " months, and " + p.getDays() + " days old. (" +
							p2 + " days total)");
	}
	
    @FXML    void testNextBirthday(ActionEvent event) {
		LocalDateTime now = LocalDateTime.now();
		System.out.printf("now: %s %d %d at %d:%02d:%02d%n", now.getMonth(), now.getDayOfMonth(), now.getYear(), now.getHour(), now.getMinute(), now.getSecond());

		LocalDate today = LocalDate.now();
		LocalDate birthday = LocalDate.of(1965, Month.DECEMBER, 24);

		LocalDate nextBDay = birthday.withYear(today.getYear());

		//If your birthday has occurred this year already, add 1 to the year.
		if (nextBDay.isBefore(today) || nextBDay.isEqual(today)) {
		    nextBDay = nextBDay.plusYears(1);
		}

		Period p = Period.between(today, nextBDay);
		long p2 = ChronoUnit.DAYS.between(today, nextBDay);
		System.out.println("There are " + p.getMonths() + " months, and " + p.getDays() + " days until your next birthday. (" + 
							p2 + " total)");
	}
	
    private String julianDate(double date) {

    	date += 0.5;
    	int days = (int)(date) - EPOCH;
    	int year = 2000 + (int)((double)days / 365.25);
    	
    	days %= 365.25;

		LocalDateTime epochDate = LocalDateTime.of(2000, Month.JANUARY, 1, 12, 0);
		ZoneId epochZone = ZoneId.of("UTC"); 
		ZonedDateTime epoch = ZonedDateTime.of(epochDate, epochZone);
		ZonedDateTime day = epoch.withZoneSameInstant(epochZone).plusDays(days);

    	Month month = day.getMonth();
    	int dayOfMonth = day.getDayOfMonth();

    	return String.format("%s %d %d", month, dayOfMonth, year);
    }
    private String julianTime(double date) {

    	double fraction = date % 1;

    	// get hour.
    	fraction *= 24;
    	int hour = (int)(fraction);

    	// get minute.
    	fraction -= hour;
    	fraction *= 60;
    	int minute = (int)(fraction);

    	// get second.
    	fraction -= minute;
    	fraction *= 60;
    	int second = (int)(fraction);

    	return String.format("%d:%02d:%02d", hour, minute, second);
    }
    private String julianString(double date) {
    	return String.format("%f -> %s at %s", date, julianDate(date), julianTime(date));
    }
    @FXML    void testJulianDay(ActionEvent event) {
    	double example = 2456293.520833;
		System.out.println("example: " + julianString(example));
    	
		LocalDateTime epochDate = LocalDateTime.of(2000, Month.JANUARY, 1, 12, 0);
		ZoneId epochZone = ZoneId.of("UTC"); 
		ZonedDateTime epoch = ZonedDateTime.of(epochDate, epochZone);
		System.out.printf("epoch: %s %d %d at %d:%02d:%02d%n", epoch.getMonth(), epoch.getDayOfMonth(), epoch.getYear(), epoch.getHour(), epoch.getMinute(), epoch.getSecond());

		LocalTime now = LocalTime.now(epochZone);
		LocalDate today = LocalDate.now(epochZone);
//		Period p = Period.between(departure.toLocalDate(), today);
		long days = ChronoUnit.DAYS.between(epoch.toLocalDate(), today);
		long seconds = now.toSecondOfDay();
		if (seconds < HALF_DAY) {
			seconds += HALF_DAY;
			days--;
		} else {
			seconds -= HALF_DAY;
		}
		System.out.println("seconds " + seconds);
		double fraction = (double)seconds / FULL_DAY;
		
		long jDay = 2451545 + days;
		double jDate = (double)jDay + fraction;
		double n = (double)days + fraction + 0.0008;
		System.out.println("There are " + days + " days from epoch to today. Julian Day: " + jDay + " Julian Date: " + jDate);
		System.out.println("Julian Date: " + julianString(jDate));
		System.out.printf("today: %s %d %d at %d:%02d:%02d%n", today.getMonth(), today.getDayOfMonth(), today.getYear(), now.getHour(), now.getMinute(), now.getSecond());

		double td = jDate - 1;
		for (int i = 0; i < 20; ++i) {
			System.out.println("Test Date: " + julianString(td));
			td += 0.1;
		}
		double longitude = -75.8586;
		double rotation = longitude / 360;

		// Mean solar time.
		double J = n - rotation;

		// Solar mean anomaly.
		double M = (357.5291 + (0.98560028 * J)) % 360;
		double Mr = Math.toRadians(M);

		// Equation of the center.
		double C = 1.9148 * Math.sin(Mr) + 0.02 * Math.sin(2 * Mr) + 0.0003 * Math.sin(3 * Mr);

		// Ecliptic longitude.
		double L = (M + C + 180 + 102.9372) % 360;

		// Solar transit.
		double Jt = 2451545.0 + J + 0.0053 * Math.sin(Mr) - 0.0069 * Math.sin(2 * Math.toRadians(L));
	}
	
	@FXML    private TextField txtJulian;
    @FXML    private Button primaryButton;
    @FXML    private Label lblDate;
    @FXML    private Label lblTime;

    @FXML
    private void recalculate() throws IOException {
    	String input = txtJulian.getText();
    	if (input.length() == 0)
    		return;
    	
    	double date = Double.parseDouble(input);
    	lblDate.setText(julianDate(date));
    	lblTime.setText(julianTime(date));
    }
}
