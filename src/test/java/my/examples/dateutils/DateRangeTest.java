package my.examples.dateutils;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class DateRangeTest {

    DateRange source = new DateRange(
            DateCommons.getDate(1, 1, 2012),
            DateCommons.getDate(31, 12, 2016)
    );


    //@Test
    public void checkPerformanceofDoesDateFallInRange(){
       Random randomGenerator = new Random();
        for( int i = 0; i < 10000000; i++) {
            if(i%2==0) {
                assertEquals(DateRange.DateStatus.DATE_WITHIN_DATE_RANGE, source.checkWithDate(DateCommons.getDate(12, 12, 2012),true));
            }
            else
                //assertEquals(DateRange.DateStatus.DATE_WITHIN_DATE_RANGE, source.checkWithDate(DateCommons.getDate(11, 11, 2012), true));
               assertNotNull(source.checkWithDate(DateCommons.getDate(randomGenerator.nextInt(13), randomGenerator.nextInt(13),        randomGenerator.nextInt(2020)),true));
        }
        System.out.println(new StringBuilder().append("the total time taken = ").append(source.getTime_taken()).toString());
        System.out.println(new StringBuilder().append("Cache size = ").append(source.getCasheDSSize()).toString());
    }


    @Test
    public void checkPerformanceofDoesDateRangeFallInRange(){

        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2011),
                DateCommons.getDate(31, 12, 2017));
        for( int i = 0; i < 10; i++) {
            if(i%2==0) {
                assertEquals(DateRange.DateRangeStatus.DATERANGE_ENVELOPPING, source.checkWithDateRange(target,true));
            }
            else
                assertEquals(DateRange.DateRangeStatus.DATERANGE_ENVELOPPING, source.checkWithDateRange(target,true));

        }
        System.out.println(new StringBuilder().append("the total time taken = ").append(source.getTime_taken()).toString());
        System.out.println(new StringBuilder().append("Cache size = ").append(source.getCasheDSSize()).toString());
    }


    @Test
    public void doesDateFallInRange(){
        assertEquals(DateRange.DateStatus.DATE_WITHIN_DATE_RANGE, source.checkWithDate(DateCommons.getDate(12, 12, 2012),false));
    }

    @Test
    public void isDateBeforeStartDate(){
        assertEquals(DateRange.DateStatus.DATE_BEFORE_START_DATE, source.checkWithDate(DateCommons.getDate(12, 12, 2010),false));
    }

    @Test
    public void isDateAfterEndDate(){
        assertEquals(DateRange.DateStatus.DATE_AFTER_END_DATE, source.checkWithDate(DateCommons.getDate(12, 12, 2017),false));
    }

    @Test
    public void isDateEqualToEndDate(){
        assertEquals(DateRange.DateStatus.DATE_IS_END_DATE, source.checkWithDate(DateCommons.getDate(31, 12, 2016),false));
    }

    @Test
    public void isDateEqualToStartDate(){
        assertEquals(DateRange.DateStatus.DATE_IS_START_DATE, source.checkWithDate(DateCommons.getDate(1, 1, 2012),false));
    }

/************************************************************************************************************************/
/*****************************************DATE RANGE CHECK***************************************************************/
   @Test
    public void isTargetDateRangeEnveloppingWithSource(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2011),
                DateCommons.getDate(31, 12, 2017));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_ENVELOPPING, source.checkWithDateRange(target, false));
    }

    @Test
    public void isTargetDateRangeEnveloppingWithSourceWhenStartDateIsSame(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2012),
                DateCommons.getDate(31, 12, 2017));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_ENVELOPPING, source.checkWithDateRange(target, false));
    }

    @Test
    public void isTargetDateRangeEnveloppingWithSourceWhenEndDateIsSame(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2011),
                DateCommons.getDate(31, 12, 2016));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_ENVELOPPING, source.checkWithDateRange(target, false));
    }

    @Test
    public void isSourceDateRangeExactMatchesWithTarget(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2012),
                DateCommons.getDate(31, 12, 2016));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_EXACT_MATCH, source.checkWithDateRange(target, false));
    }

    @Test
    public void isTargetDateRangeLiesWithinSource(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2013),
                DateCommons.getDate(31, 12, 2015));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_LIES_WITHIN, source.checkWithDateRange(target, false));
    }

    @Test
    public void isTargetDateRangeLiesWithinSourceWhenStartDateIsSame(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2012),
                DateCommons.getDate(31, 12, 2015));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_LIES_WITHIN, source.checkWithDateRange(target, false));
    }

    @Test
    public void isTargetDateRangeLiesWithinSourceWhenEndDateIsSame(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2013),
                DateCommons.getDate(31, 12, 2016));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_LIES_WITHIN, source.checkWithDateRange(target, false));
    }

    @Test
    public void isTargetDateRangeOutSideFromEndDateWithSource(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2017),
                DateCommons.getDate(31, 12, 2017));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_OUTSIDE_FROM_END_DATE, source.checkWithDateRange(target, false));
    }

    @Test
    public void isTargetDateRangeOutSideFromStartDateWithSource(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2010),
                DateCommons.getDate(31, 12, 2010));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_OUTSIDE_FROM_START_DATE, source.checkWithDateRange(target, false));
    }


    @Test
    public void isTargetDateRangeOverlappingFromStartDateWithSource(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2010),
                DateCommons.getDate(31, 12, 2014));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_OVERLAPPING_FROM_START_DATE, source.checkWithDateRange(target, false));
    }

    @Test
    public void isTargetDateRangeOverlappingFromEndDateWithSource(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2015),
                DateCommons.getDate(31, 12, 2017));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_OVERLAPPING_FROM_END_DATE, source.checkWithDateRange(target, false));
    }
}