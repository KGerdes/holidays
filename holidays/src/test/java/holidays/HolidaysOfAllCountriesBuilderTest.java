package holidays;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Locale;

import org.junit.Test;

import central.HolidaysCollection;
import central.HolidaysOfAllCountriesBuilder;
import central.ObjectContainer;
import central.builder.HolidaysCollectionBuilder;
import org.junit.Assert;

public class HolidaysOfAllCountriesBuilderTest {
	
	@Test
	public void getOrCreateBuilder01Test() {
		HolidaysCollectionBuilder hcb = HolidaysOfAllCountriesBuilder.createBuilder(Locale.GERMANY);
		HolidaysCollectionBuilder hcb2 = HolidaysOfAllCountriesBuilder.createBuilder(Locale.GERMANY);
		Assert.assertEquals(true, hcb != hcb2);
	}
	
	@Test
	public void getOrCreateBuilder02Test() {
		InputStream strm = HolidaysOfAllCountriesBuilderTest.class.getClassLoader().getResourceAsStream("holidays_de.xml");
		HolidaysCollectionBuilder hcb = HolidaysOfAllCountriesBuilder.createBuilder(Locale.GERMANY, strm);
		Assert.assertEquals("[BY_Augsburg, BY_MariaeHimmelfahrt, ChristmasEve, NRW and Carnival Monday, SN_CorpusChristi, TH_CorpusChristi, Weekend]", hcb.getPossibleCollectionKeys(false).toString());
	}
	
	@Test
	public void createHolidays01Test() {
		ObjectContainer<HolidaysCollectionBuilder> oc = new ObjectContainer<>();
		HolidaysCollection hc = HolidaysOfAllCountriesBuilder.createHolidays(Locale.GERMANY, "use=NRW", oc);
		Assert.assertEquals(false, hc.isHoliday(LocalDate.of(2021, 1, 6)));
		hc = oc.getTheObject().createCollection("use=BY");
		Assert.assertEquals(true, hc.isHoliday(LocalDate.of(2021, 1, 6)));
	}
	
	@Test
	public void createHolidays02Test() {
		HolidaysCollection hc = HolidaysOfAllCountriesBuilder.createHolidays(Locale.GERMANY, "use=NRW");
		Assert.assertEquals(false, hc.isHoliday(LocalDate.of(2021, 1, 6)));
		hc = HolidaysOfAllCountriesBuilder.createHolidays(Locale.GERMANY, "use=BY");
		Assert.assertEquals(true, hc.isHoliday(LocalDate.of(2021, 1, 6)));
	}
	
	@Test
	public void getPossibleCollectionKeys01Test() {
		Assert.assertEquals("[BB, BE, BW, BY, BY_Augsburg, BY_MariaeHimmelfahrt, ChristmasEve, HB, HE, HH, MV, NI, NRW, NRW and Carnival Monday, RP, SH, SL, SN, SN_CorpusChristi, ST, TH, TH_CorpusChristi, Weekend]",HolidaysOfAllCountriesBuilder.getPossibleCollectionKeys(Locale.GERMANY).toString());
	}
	
	@Test
	public void getPossibleCollectionKeys02Test() {
		Assert.assertEquals("[BY_Augsburg, BY_MariaeHimmelfahrt, ChristmasEve, NRW and Carnival Monday, SN_CorpusChristi, TH_CorpusChristi, Weekend]"
				,HolidaysOfAllCountriesBuilder.getPossibleCollectionKeys(Locale.GERMANY, false).toString());
		Assert.assertEquals("[BB, BE, BW, BY, HB, HE, HH, MV, NI, NRW, RP, SH, SL, SN, ST, TH]",HolidaysOfAllCountriesBuilder.getPossibleCollectionKeys(Locale.GERMANY, true).toString());
	}
	
	@Test
	public void getPossibleCollectionKeys03Test() {
		ObjectContainer<HolidaysCollectionBuilder> oc = new ObjectContainer<>();
		Assert.assertEquals("[BY_Augsburg, BY_MariaeHimmelfahrt, ChristmasEve, NRW and Carnival Monday, SN_CorpusChristi, TH_CorpusChristi, Weekend]"
				,HolidaysOfAllCountriesBuilder.getPossibleCollectionKeys(Locale.GERMANY, false, oc).toString());
		Assert.assertEquals("[BB, BE, BW, BY, HB, HE, HH, MV, NI, NRW, RP, SH, SL, SN, ST, TH]",oc.getTheObject().getPossibleCollectionKeys(true).toString());
	}
	
	
}
