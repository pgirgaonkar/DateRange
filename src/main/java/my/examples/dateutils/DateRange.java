package my.examples.dateutils;

/**
 * Created by pgirga on 9/7/2014.
 */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class DateRange
{
    private Map <Date, DateStatus> cacheDS = null ;
    private Map <DateRange, DateRangeStatus> cacheDRS = null ;

    public long getCasheDSSize(){
        return cacheDS.size();
    }

    public long getCasheDRSSize(){
        return cacheDRS.size();
    }

    public enum DateStatus{
        DATE_BEFORE_START_DATE ,
        DATE_IS_START_DATE,
        DATE_WITHIN_DATE_RANGE,
        DATE_IS_END_DATE,
        DATE_AFTER_END_DATE,
        DATE_RANGE_DEFAULT
    }


    public enum DateRangeStatus{
        DATE_RANGE_DEFAULT,
        DATERANGE_LIES_WITHIN,
        DATERANGE_ENVELOPPING,
        DATERANGE_OVERLAPPING_FROM_START_DATE,
        DATERANGE_OVERLAPPING_FROM_END_DATE,
        DATERANGE_EXACT_MATCH,
        DATERANGE_OUTSIDE_FROM_START_DATE,
        DATERANGE_OUTSIDE_FROM_END_DATE
    }


    public long getTime_taken() {
        return time_taken;
    }

    private long time_taken = 0;
    private long start_time =0;

    private		Date	startDate	= null;
    private		Date	endDate		= null;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateRange)) return false;

        DateRange dateRange = (DateRange) o;

        return endDate.equals(dateRange.endDate) && startDate.equals(dateRange.startDate);

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
        cacheDS = new HashMap<Date, DateStatus>();
        cacheDRS = new HashMap<DateRange, DateRangeStatus>();
    }

    public DateRange(Date StartDate, int periodInDays)
    {
        startDate = new Date(StartDate.getTime());
        endDate   = DateCommons.addDays(startDate, periodInDays);
        cacheDS = new HashMap<Date, DateStatus>();
        cacheDRS = new HashMap<DateRange, DateRangeStatus>();
    }

    public Date getStartDate()
    {
        return (startDate);
    }

    public Date getEndDate()
    {
        return (endDate);
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

    DateStatus ds = null;

    public  DateStatus checkWithDate(Date target, boolean genStat){
        return !genStat ? checkWithDateInt(target) : checkWithDateWithStat(target);
    }


    private  DateStatus checkWithDateWithStat(Date target)
    {
        start_time = System.nanoTime();
        ds = checkWithDateInt(target);
        time_taken =  (System.nanoTime() - start_time) + time_taken;
        return ds;
    }

    private DateStatus checkWithDateInt(Date target)
    {
        ds = cacheDS.get(target);
        if (null == ds ){
            ds = getDateStatus(target);
            updateCacheDS(target, ds);
        }
        return ds;
    }

    private DateStatus getDateStatus(Date target) {

        int startValue = checkStartDate(target);
        int endValue   = checkEndDate(target);

        return finsDateStatus(startValue, endValue);

    }

    private DateStatus finsDateStatus(int startValue, int endValue) {
        if (startValue > 0)
            return DateStatus.DATE_BEFORE_START_DATE;
        else if (startValue == 0 )
            return DateStatus.DATE_IS_START_DATE;
        else if (startValue < 0 && endValue > 0)
            return DateStatus.DATE_WITHIN_DATE_RANGE;
        else if (endValue == 0 )
            return DateStatus.DATE_IS_END_DATE;
        else if (endValue < 0)
            return DateStatus.DATE_AFTER_END_DATE;
        else return DateStatus.DATE_RANGE_DEFAULT;
    }

    private void updateCacheDS(Date d, DateStatus ds){
        cacheDS.put(d, ds);
    }

    private void updateCacheDRS(DateRange dr, DateRangeStatus ds){
        cacheDRS.put(dr, ds);
    }

    /*
    Returns::
    0  :: If target date range lies within this.date range		- DATERANGE_LIES_WITHIN
    1  :: If target date range is envoloping this.Date range	- DATERANGE_ENVELOPPING
    2  :: If target date range is overlapping from start date	- DATERANGE_OVERLAPPING_FROM_START_DATE
    3  :: If target date range is overlapping from end date		- DATERANGE_OVERLAPPING_FROM_END_DATE
    4  :: If target date range matches this.date range			- DATERANGE_EXACT_MATCH
    5  :: If target date range is out of this.date range		- DATERANGE_OUTSIDE_FROM_START_DATE
             from start date side
    6  :: If target date range is out of this.date range		- DATERANGE_OUTSIDE_FROM_END_DATE
             from end date side
    */
    DateRangeStatus drs = null;

    public DateRangeStatus checkWithDateRange(DateRange target, boolean genStat) {
        return !genStat ? checkWithDateRangeInt(target) : checkWithDateRangeWithStat(target);
    }

    private DateRangeStatus checkWithDateRangeInt(DateRange target)
    {
        drs = cacheDRS.get(target);
        if (null == drs ){
            drs = getDateRangeStatus(target);
            updateCacheDRS(target, drs);
        }
        return drs;
    }

    private DateRangeStatus checkWithDateRangeWithStat(DateRange target)
    {
        start_time = System.nanoTime();
        drs = checkWithDateRangeInt(target);
        time_taken =  (System.nanoTime() - start_time) + time_taken;
        return drs;
    }

    private DateRangeStatus getDateRangeStatus(DateRange target) {

        int startValueStart = checkStartDate(target.getStartDate());
        int startValueEnd   = checkEndDate(target.getStartDate());
        int endValueStart = checkStartDate(target.getEndDate());
        int endValueEnd   = checkEndDate(target.getEndDate());

        return findDateRangeStatus(startValueStart, startValueEnd, endValueStart, endValueEnd);
    }

    private DateRangeStatus findDateRangeStatus(int startValueStart, int startValueEnd, int endValueStart, int endValueEnd) {

        if (startValueStart == 0 && endValueEnd == 0)
            return DateRangeStatus.DATERANGE_EXACT_MATCH;
        else if (startValueStart <= 0 && endValueEnd >= 0)
            return DateRangeStatus.DATERANGE_LIES_WITHIN;
        else if (startValueStart >= 0 && endValueEnd <= 0)
            return DateRangeStatus.DATERANGE_ENVELOPPING;
        else if ((startValueStart < 0 && startValueEnd > 0) && endValueEnd < 0)
            return DateRangeStatus.DATERANGE_OVERLAPPING_FROM_END_DATE;
        else if ((endValueStart < 0 && endValueEnd > 0) && startValueStart > 0)
            return DateRangeStatus.DATERANGE_OVERLAPPING_FROM_START_DATE;
        else if (endValueStart > 0)
            return DateRangeStatus.DATERANGE_OUTSIDE_FROM_START_DATE;
        else if (startValueEnd < 0)
            return DateRangeStatus.DATERANGE_OUTSIDE_FROM_END_DATE; //
        else   return DateRangeStatus.DATE_RANGE_DEFAULT;
    }


}

