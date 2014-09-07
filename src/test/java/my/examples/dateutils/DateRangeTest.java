package my.examples.dateutils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class DateRangeTest {

    DateRange source = new DateRange(
            DateCommons.getDate(1, 1, 2012),
            DateCommons.getDate(31, 12, 2016)
    );

    @Test
    public void doesDateFallInRange(){
        assertEquals(DateRange.DateStatus.DATE_WITHIN_DATE_RANGE, source.checkWithDate(DateCommons.getDate(12, 12, 2012)));
    }

    @Test
    public void isDateBeforeStartDate(){
        assertEquals(DateRange.DateStatus.DATE_BEFORE_START_DATE, source.checkWithDate(DateCommons.getDate(12, 12, 2010)));
    }

    @Test
    public void isDateAfterEndDate(){
        assertEquals(DateRange.DateStatus.DATE_AFTER_END_DATE, source.checkWithDate(DateCommons.getDate(12, 12, 2017)));
    }

    @Test
    public void isDateEqualToEndDate(){
        assertEquals(DateRange.DateStatus.DATE_IS_END_DATE, source.checkWithDate(DateCommons.getDate(31, 12, 2016)));
    }

    @Test
    public void isDateEqualToStartDate(){
        assertEquals(DateRange.DateStatus.DATE_IS_START_DATE, source.checkWithDate(DateCommons.getDate(1, 1, 2012)));
    }

/************************************************************************************************************************/
/*****************************************DATE RANGE CHECK***************************************************************/
    @Test
    public void isTargetDateRangeEnveloppingWithSource(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2011),
                DateCommons.getDate(31, 12, 2017));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_EVELOPPING, source.checkWithDateRange(target));
    }

    @Test
    public void isTargetDateRangeEnveloppingWithSourceWhenStartDateIsSame(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2012),
                DateCommons.getDate(31, 12, 2017));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_EVELOPPING, source.checkWithDateRange(target));
    }

    @Test
    public void isTargetDateRangeEnveloppingWithSourceWhenEndDateIsSame(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2011),
                DateCommons.getDate(31, 12, 2016));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_EVELOPPING, source.checkWithDateRange(target));
    }

    @Test
    public void isSourceDateRangeExactMatchesWithTarget(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2012),
                DateCommons.getDate(31, 12, 2016));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_EXACT_MATCH, source.checkWithDateRange(target));
    }

    @Test
    public void isTargetDateRangeLiesWithinSource(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2013),
                DateCommons.getDate(31, 12, 2015));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_LIES_WITHIN, source.checkWithDateRange(target));
    }

    @Test
    public void isTargetDateRangeLiesWithinSourceWhenStartDateIsSame(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2012),
                DateCommons.getDate(31, 12, 2015));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_LIES_WITHIN, source.checkWithDateRange(target));
    }

    @Test
    public void isTargetDateRangeLiesWithinSourceWhenEndDateIsSame(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2013),
                DateCommons.getDate(31, 12, 2016));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_LIES_WITHIN, source.checkWithDateRange(target));
    }

    @Test
    public void isTargetDateRangeOutSideFromEndDateWithSource(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2017),
                DateCommons.getDate(31, 12, 2017));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_OUTSIDE_FROM_END_DATE, source.checkWithDateRange(target));
    }

    @Test
    public void isTargetDateRangeOutSideFromStartDateWithSource(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2010),
                DateCommons.getDate(31, 12, 2010));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_OUTSIDE_FROM_START_DATE, source.checkWithDateRange(target));
    }


    @Test
    public void isTargetDateRangeOverlappingFromStartDateWithSource(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2010),
                DateCommons.getDate(31, 12, 2014));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_OVERLAPPING_FROM_START_DATE, source.checkWithDateRange(target));
    }

    @Test
    public void isTargetDateRangeOverlappingFromEndDateWithSource(){
        DateRange target = new DateRange(DateCommons.getDate(1, 1, 2015),
                DateCommons.getDate(31, 12, 2017));

        assertEquals(DateRange.DateRangeStatus.DATERANGE_OVERLAPPING_FROM_END_DATE, source.checkWithDateRange(target));
    }
}