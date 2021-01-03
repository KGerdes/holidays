package holidays;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.Assert;

import central.HolidaysCollection;
import central.HolidaysOfAllCountriesBuilder;
import central.builder.HolidaysCollectionBuilder;
import central.builder.HolidaysMatrix;



public class DECollectionTest {
	
	private static final HolidaysCollectionBuilder hcb = HolidaysOfAllCountriesBuilder.createBuilder(Locale.GERMANY);
	
	@Test
	public void collection01Test() {
		HolidaysCollection hc = hcb.createCollection("use=NRW");
		Assert.assertEquals("Nordrhein-Westfalen", hc.getAllUsedDescriptions());
	}
	
	@Test
	public void collection02Test() {
		HolidaysCollection hc = hcb.createCollection("use=NRW");
		Assert.assertEquals("[1stChristmasHoliday, 1stMay, 2stChristmasHoliday, All Saints Day, Ascension of Christ, Corpus Christi, Day of German unity, EasterMonday, Good Friday, NewYear, Whit Monday]", hc.getAllNames().toString());
	}
	
	@Test
	public void collection03Test() {
		StringBuilder sb = new StringBuilder();
		HolidaysCollection hc = hcb.createCollection("use=NRW");
		LocalDate ld = hc.getSingleHolidayOfAYear("Good Friday", 2021, sb);
		Assert.assertEquals("02.04.2021", TestUtilities.formatDateShort(ld));
		Assert.assertEquals("Karfreitag", sb.toString());
	}
	
	@Test
	public void collection03aTest() {
		HolidaysCollection hc = hcb.createCollection("use=NRW");
		LocalDate ld = hc.getSingleHolidayOfAYear("Good Friday", 2021, null);
		Assert.assertEquals("02.04.2021", TestUtilities.formatDateShort(ld));
	}
	
	@Test
	public void collection04Test() {
		StringBuilder sb = new StringBuilder();
		HolidaysCollection hc = hcb.createCollection("use=NRW");
		LocalDate ld = hc.getSingleHolidayOfAYear("unexistent", 2021, sb);
		Assert.assertNull(ld);
		Assert.assertEquals("", sb.toString());
	}
	
	@Test
	public void collection05Test() {
		StringBuilder sb = new StringBuilder();
		HolidaysCollection hc = hcb.createCollection("use=NRW,Weekend");
		Assert.assertEquals("[NRW, Weekend]", hc.getUsedKeys().toString());
		Assert.assertEquals("Nordrhein-Westfalen, Wochenende", hc.getAllUsedDescriptions());
		LocalDate ld = hc.getSingleHolidayOfAYear("Saturday", 2021, sb);
		Assert.assertNull(ld);
		Assert.assertEquals("", sb.toString());
	}
	
	@Test
	public void collection06Test() {
		HolidaysCollection hc = hcb.createCollection("use=NRW,Weekend");
		LocalDate ld = hc.getSingleHolidayOfAYear("Saturday", 2021, null);
		Assert.assertNull(ld);
	}
	
}
