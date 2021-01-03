package holidays;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)				
@Suite.SuiteClasses({				
  HolidaysMatrixTest.class,
  HolidaysOfAllCountriesBuilderTest.class,
  DECollectionTest.class,
  RangeTest.class,
  CalculationTest.class
})
public class HolidaysTestSuite {

}
