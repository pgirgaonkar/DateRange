package my.examples.dateutils;

/**
 * Created by pgirga on 9/7/2014.
 */


import java.util.Date;


public class DateRange
{
    public static final	int DATE_RANGE_DEFAULT	 					= -100;


    public enum DateStatus{
        DATE_BEFORE_START_DATE , DATE_IS_START_DATE, DATE_WITHIN_DATE_RANGE, DATE_IS_END_DATE, DATE_AFTER_END_DATE,  DATE_RANGE_DEFAULT
    };


    public enum DateRangeStatus{
        DATE_RANGE_DEFAULT, DATERANGE_LIES_WITHIN, DATERANGE_EVELOPPING,DATERANGE_OVERLAPPING_FROM_START_DATE,
        DATERANGE_OVERLAPPING_FROM_END_DATE, DATERANGE_EXACT_MATCH, DATERANGE_OUTSIDE_FROM_START_DATE, DATERANGE_OUTSIDE_FROM_END_DATE
    }   ;

    public static final	int DATE_BEFORE_START_DATE 					= 1;
    public static final	int DATE_IS_START_DATE						= 2;
    public static final	int DATE_WITHIN_DATE_RANGE					= 3;
    public static final	int DATE_IS_END_DATE						= 4;
    public static final	int DATE_AFTER_END_DATE						= 5;


    public static final	int DATERANGE_LIES_WITHIN					= 0;
    public static final	int DATERANGE_EVELOPPING					= 1;
    public static final	int DATERANGE_OVERLAPPING_FROM_START_DATE	= 2;
    public static final	int DATERANGE_OVERLAPPING_FROM_END_DATE		= 3;
    public static final	int DATERANGE_EXACT_MATCH					= 4;
    public static final	int DATERANGE_OUTSIDE_FROM_START_DATE		= 5;
    public static final	int DATERANGE_OUTSIDE_FROM_END_DATE 		= 6;



    private		Date	startDate	= null;
    private		Date	endDate		= null;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateRange)) return false;

        DateRange dateRange = (DateRange) o;

        if (!endDate.equals(dateRange.endDate)) return false;
        if (!startDate.equals(dateRange.startDate)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }



    public DateRange(Date StartDate, Date EndDate)
    {
        if (StartDate.compareTo(EndDate) > 0)
        {
            startDate = new Date(EndDate.getTime());
            endDate   = new Date(StartDate.getTime());
        }
        else
        {
            startDate = new Date(StartDate.getTime());
            endDate   = new Date(EndDate.getTime());
        }
    }

    public DateRange(Date StartDate, int periodInDays)
    {
        startDate = new Date(StartDate.getTime());
        endDate   = DateCommons.addDays(startDate, periodInDays);
    }

    public Date getStartDate()
    {
        return (startDate);
    }

    public Date getEndDate()
    {
        return (endDate);
    }


    private boolean checkDateRangeEqual(DateRange target)
    {
        if (startDate.equals(target.getStartDate()) && endDate.equals(target.getEndDate()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
    Returns ::
    0, if equal.
    less than 0, if this.startDate is prior to source.startDAte
    greater than 0, if this.startDate is after source.startDAte
    */

    private int checkStartDate(Date target)
    {
        return startDate.compareTo(target);
    }


    /*
    Returns ::
    0, if equal.
    less than 0, if this.startDate is prior to source.startDAte
    greater than 0, if this.startDate is after source.startDAte
    */

    private int checkEndDate(Date target)
    {
        return endDate.compareTo(target);
    }


    /*
    Returns::
    1 :: If target date is before Start Date 	- DATE_BEFORE_START_DATE
    2 :: If target date is on Start Date 		- DATE_IS_START_DATE
    3 :: If target date is within Date range	- DATE_WITHIN_DATE_RANGE
    4 :: If target date is on end date			- DATE_IS_END_DATE
    5 :: If target date is after end date		- DATE_AFTER_END_DATE
    */
    public DateStatus checkWithDate(Date target)
    {
        int startValue = checkStartDate(target);
        int endValue   = checkEndDate(target);

        if (startValue > 0)
        {
            //source is before start date
            return DateStatus.DATE_BEFORE_START_DATE;
        }

        if (startValue == 0 )
        {
            return DateStatus.DATE_IS_START_DATE;
        }

        if (startValue < 0 && endValue > 0)
        {
            return DateStatus.DATE_WITHIN_DATE_RANGE;
        }

        if (endValue == 0 )
        {
            return DateStatus.DATE_IS_END_DATE;
        }

        if (endValue < 0)
        {
            return DateStatus.DATE_AFTER_END_DATE;
        }

        return DateStatus.DATE_RANGE_DEFAULT;
    }


    /*
    Returns::
    0  :: If target date range lies within this.date range		- DATERANGE_LIES_WITHIN
    1  :: If target date range is envoloping this.Date range	- DATERANGE_EVELOPPING
    2  :: If target date range is overlapping from start date	- DATERANGE_OVERLAPPING_FROM_START_DATE
    3  :: If target date range is overlapping from end date		- DATERANGE_OVERLAPPING_FROM_END_DATE
    4  :: If target date range matches this.date range			- DATERANGE_EXACT_MATCH
    5  :: If target date range is out of this.date range		- DATERANGE_OUTSIDE_FROM_START_DATE
             from start date side
    6  :: If target date range is out of this.date range		- DATERANGE_OUTSIDE_FROM_END_DATE
             from end date side
    */
    public DateRangeStatus checkWithDateRange(DateRange target)
    {
        int startValueStart = checkStartDate(target.getStartDate());
        int startValueEnd   = checkEndDate(target.getStartDate());


        int endValueStart = checkStartDate(target.getEndDate());
        int endValueEnd   = checkEndDate(target.getEndDate());


        if (startValueStart == 0 && endValueEnd == 0)
        {
            return DateRangeStatus.DATERANGE_EXACT_MATCH;
        }

        if (startValueStart <= 0 && endValueEnd >= 0)
        {
            return DateRangeStatus.DATERANGE_LIES_WITHIN; //within this.Date range
        }

        if (startValueStart >= 0 && endValueEnd <= 0)
        {
            return DateRangeStatus.DATERANGE_EVELOPPING; //envolpping this.daterange
        }

        if ((startValueStart < 0 && startValueEnd > 0) && endValueEnd < 0)
        {
            return DateRangeStatus.DATERANGE_OVERLAPPING_FROM_END_DATE; //overlapping date range from this.endDate
        }


        if ((endValueStart < 0 && endValueEnd > 0) && startValueStart > 0)
        {
            return DateRangeStatus.DATERANGE_OVERLAPPING_FROM_START_DATE; //overlapping date range from this.startDate
        }


        /********RECHECK********/
        if (endValueStart > 0)
        {
            return DateRangeStatus.DATERANGE_OUTSIDE_FROM_START_DATE; //out of date range from this.startDate
        }

        if (startValueEnd < 0)
        {
            return DateRangeStatus.DATERANGE_OUTSIDE_FROM_END_DATE; //out of date range from this.endDate
        }

        return DateRangeStatus.DATE_RANGE_DEFAULT;
    }


    public static void main(String[] args) {

        // create two dates
        Date date = DateCommons.getDate(98, 5, 21);
        Date date2 = DateCommons.getDate(99, 1, 9);
        Date date3 = DateCommons.getDate(99, 1, 9);

        // make 3 comparisons with them
        int comparison = date.compareTo(date2);
        int comparison2 = date2.compareTo(date);
        int comparison3 = date.compareTo(date);
          comparison3 = date2.compareTo(date3);

        // print the results
        System.out.println("Comparison Result:" + comparison);
        System.out.println("Comparison2 Result:" + comparison2);
        System.out.println("Comparison3 Result:" + comparison3);

    }
}//Class

