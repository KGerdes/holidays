package holidays;

import org.junit.Test;
import org.junit.Assert;

import central.calc.AdventHolidays;
import central.calc.SingleYearlyHolidays;

public class CalculationTest {
	
	@Test
	public void adventHoliday01Test() {
		String res = testHoliday(new AdventHolidays("BuB", "Buﬂ- und Bettag", -32), 2017, 2020, 2023);
		Assert.assertEquals("2017:Mi 22.11.2017|2020:Mi 18.11.2020|2023:Mi 22.11.2023|", res);
	}
	
	private String testHoliday(SingleYearlyHolidays h, int... years) {
		StringBuilder sb = new StringBuilder();
		for (int year : years) {
			sb.append(year).append(":").append(TestUtilities.formatDate(h.calculateDateOfAYear(year))).append("|");
		}
		return sb.toString();
	}

}
