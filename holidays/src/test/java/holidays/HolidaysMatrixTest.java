package holidays;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map.Entry;

import org.junit.Test;

import central.HolidaysOfAllCountriesBuilder;
import central.builder.HolidaysCollectionBuilder;
import central.builder.HolidaysMatrix;
import org.junit.Assert;

public class HolidaysMatrixTest {

	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EE dd.MM.yyyy", Locale.GERMANY);
	private static String[] deMatrix = {
			"Fr 01.01.2021|Neujahr|BB|BE|BW|BY|HB|HE|HH|MV|NI|NRW|RP|SH|SL|SN|ST|TH|",
			"Mi 06.01.2021|Heilige drei Könige|BW|BY|ST|",
			"Mo 08.03.2021|Internationaler Frauentag|BE|",
			"Fr 02.04.2021|Karfreitag|BB|BE|BW|BY|HB|HE|HH|MV|NI|NRW|RP|SH|SL|SN|ST|TH|",
			"Mo 05.04.2021|Ostermontag|BB|BE|BW|BY|HB|HE|HH|MV|NI|NRW|RP|SH|SL|SN|ST|TH|",
			"Sa 01.05.2021|1. Mai|BB|BE|BW|BY|HB|HE|HH|MV|NI|NRW|RP|SH|SL|SN|ST|TH|",
			"Do 13.05.2021|Christi Himmelfahrt|BB|BE|BW|BY|HB|HE|HH|MV|NI|NRW|RP|SH|SL|SN|ST|TH|",
			"Mo 24.05.2021|Pfingstmontag|BB|BE|BW|BY|HB|HE|HH|MV|NI|NRW|RP|SH|SL|SN|ST|TH|",
			"Do 03.06.2021|Fronleichnam|BW|BY|HE|NRW|RP|SL|",
			"So 15.08.2021|Mariä Himmelfahrt|SL|",
			"So 03.10.2021|Tag der deutschen Einheit|BB|BE|BW|BY|HB|HE|HH|MV|NI|NRW|RP|SH|SL|SN|ST|TH|",
			"So 31.10.2021|Reformationstag|BB|MV|SN|ST|TH|",
			"Mo 01.11.2021|Allerheiligen|BW|BY|NRW|RP|SL|",
			"Mi 17.11.2021|Buß- und Bettag|SN|",
			"Sa 25.12.2021|1. Weihnachtsfeiertag|BB|BE|BW|BY|HB|HE|HH|MV|NI|NRW|RP|SH|SL|SN|ST|TH|",
			"So 26.12.2021|2. Weihnachtsfeiertag|BB|BE|BW|BY|HB|HE|HH|MV|NI|NRW|RP|SH|SL|SN|ST|TH|"
	};
	
	private static String[] ukMatrix = {
			"Fr 01.01.2021|New Year|ENG|NIR|SCO|WAL|",
			"Mo 04.01.2021|2nd January|SCO|",
			"Mi 17.03.2021|St. Patrick's Day|NIR|",
			"Fr 02.04.2021|Good Friday|ENG|NIR|SCO|WAL|",
			"Mo 05.04.2021|Easter Monday|ENG|NIR|WAL|",
			"Mo 03.05.2021|Bank Holiday|ENG|NIR|SCO|WAL|",
			"Mo 31.05.2021|Spring Bank Holiday|ENG|NIR|SCO|WAL|",
			"Mo 12.07.2021|Orangemen's Day|NIR|",
			"Mo 02.08.2021|Summer Bank Holiday|SCO|",
			"Mo 30.08.2021|Summer Bank Holiday|ENG|NIR|WAL|",
			"Di 30.11.2021|St Andrew's Day|SCO|",
			"Mo 27.12.2021|1st Christmas Holiday|ENG|NIR|SCO|WAL|",
			"Di 28.12.2021|2nd Christmas Holiday|ENG|NIR|SCO|WAL|"
	
	};
	
	@Test
	public void testMatrixDE() {
		HolidaysCollectionBuilder hcb = HolidaysOfAllCountriesBuilder.createBuilder(Locale.GERMANY);
		doMatrixTest(hcb, 2021, deMatrix);
	}
	
	@Test
	public void testMatrixUK() {
		HolidaysCollectionBuilder hcb = HolidaysOfAllCountriesBuilder.createBuilder(Locale.UK);
		doMatrixTest(hcb, 2021, ukMatrix);
	}
	
	private void doMatrixTest(HolidaysCollectionBuilder hcb, int year, String[] matrix) {
		HolidaysMatrix hm = hcb.getHolidaysMatrix(year);
		System.out.println(hm.toString());
		int index = 0;
		for (Entry<LocalDate, String> entry : hm.getHolidays()) {
			StringBuilder sb = new StringBuilder();
			sb.append(TestUtilities.formatDate(entry.getKey()))
				.append("|")
				.append(entry.getValue())
				.append("|");
			hm.getHolidayCollections(entry.getKey(), entry.getValue()).stream().forEach(key -> {
				sb.append(key).append("|");
			});
			System.out.println(sb.toString());
			Assert.assertEquals(matrix[index], sb.toString());
			index++;
		}
	}
}
