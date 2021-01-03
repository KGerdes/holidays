package holidays;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.Assert;

import central.HolidaysRuntimeException;
import central.calc.Range;

public class RangeTest {
	
	private static final DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	
	@Test
	public void range01Test() {
		callRange("", "", null, dtf, "HolidaysRuntimeException:Missing range dates");
	}
	
	@Test
	public void range02Test() {
		callRange(null, null, null, dtf, "HolidaysRuntimeException:Missing range dates");
	}
	
	@Test
	public void range03Test() {
		callRange("01.01.2021", "05.05.2020", null, dtf, "HolidaysRuntimeException:start range date is after end range date");
	}
	
	@Test
	public void range03aTest() {
		callRange("01.01.2021", "", null, dtf, "");
	}
	
	@Test
	public void range03bTest() {
		callRange(null, "01.01.2021", null, dtf, "");
	}
	
	@Test
	public void range04Test() {
		callRange("01.01.2021", "05.05.2021", "inner", dtf, "");
	}
	
	@Test
	public void range05Test() {
		callRange("01.01.2021", "05.05.2021", "outer", dtf, "");
	}
	@Test
	
	public void range05aTest() {
		Range r = callRange("01.01.2021", "05.01.2021", "inner", dtf, "");
		Assert.assertEquals(false, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(-1)));
		Assert.assertEquals(true, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(0)));
		Assert.assertEquals(true, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(2)));
		Assert.assertEquals(true, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(4)));
		Assert.assertEquals(false, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(5)));
	}
	
	@Test
	public void range05bTest() {
		Range r = callRange("01.01.2021", "05.01.2021", "outer", dtf, "");
		Assert.assertEquals(true, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(-1)));
		Assert.assertEquals(false, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(0)));
		Assert.assertEquals(false, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(2)));
		Assert.assertEquals(false, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(4)));
		Assert.assertEquals(true, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(5)));
	}
	
	@Test
	public void range05cTest() {
		Range r = callRange(null, "05.01.2021", "outer", dtf, "");
		Assert.assertEquals(false, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(-1)));
		Assert.assertEquals(false, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(4)));
		Assert.assertEquals(true, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(5)));
	}
	
	@Test
	public void range05dTest() {
		Range r = callRange("01.01.2021", null, "inner", dtf, "");
		Assert.assertEquals(false, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(-1)));
		Assert.assertEquals(true, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(0)));
		Assert.assertEquals(true, r.isDateIn(LocalDate.of(2021, 1, 1).plusDays(500)));
	}
	
	@Test
	public void range06Test() {
		callRange("01.01.2021", "05.05.2021", "nonexisting", dtf, "IllegalArgumentException:No enum constant central.calc.Range.Scope.NONEXISTING");
	}
	
	
	
	private Range callRange(String start, String end, String scope, DateTimeFormatter dateform, String exceptionText) {
		Range r = null;
		try {
			r = Range.createRange(start, end, scope, dtf);
		} catch (Exception e) {
			Assert.assertEquals(exceptionText, e.getClass().getSimpleName() + ":" +  e.getMessage());
			return null;
		} 
		Assert.assertEquals("", exceptionText);
		return r;
	}
}
