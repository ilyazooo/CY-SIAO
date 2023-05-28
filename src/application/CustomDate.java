package application;

import java.sql.Date;
import java.util.Calendar;

/**
 * The CustomDate class represents a custom date in the format "dd/MM/yyyy" (day/month/year).
 * It provides methods to manipulate, compare, and convert custom dates.
 */
public class CustomDate {

	
    private String date;
    
    /**
     * Constructs a `CustomDate` object with the specified date string.
     * 
     * @param date the date string (format: "dd/MM/yyyy")
     */
    public CustomDate(String date) {
        this.date = date;
    }

    /**
     * Returns the date string of this `CustomDate` object.
     * 
     * @return the date string
     */
    public String getDate() {
        return date;
    }


    /**
     * Sets the date string of this `CustomDate` object.
     * 
     * @param date the date string to set (format: "dd/MM/yyyy")
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns the day of the month of this `CustomDate`.
     * 
     * @return the day of the month
     */
    public int getDay() {
        String[] parts = date.split("/");
        return Integer.parseInt(parts[0]);
    }

    /**
     * Returns the month of this `CustomDate`.
     * 
     * @return the month
     */
    public int getMonth() {
        String[] parts = date.split("/");
        return Integer.parseInt(parts[1]);
    }

    /**
     * Returns the year of this `CustomDate`.
     * 
     * @return the year
     */
    public int getYear() {
        String[] parts = date.split("/");
        return Integer.parseInt(parts[2]);
    }
    
    /**
     * Checks if this `CustomDate` is before the specified `CustomDate`.
     * 
     * @param otherDate the `CustomDate` to compare
     * @return `true` if this `CustomDate` is before the specified `CustomDate`, `false` otherwise
     */
    public boolean isBefore(CustomDate otherDate) {
        int day1 = this.getDay();
        int month1 = this.getMonth();
        int year1 = this.getYear();

        int day2 = otherDate.getDay();
        int month2 = otherDate.getMonth();
        int year2 = otherDate.getYear();

        if (year1 < year2) {
            return true;
        } else if (year1 > year2) {
            return false;
        } else {
            if (month1 < month2) {
                return true;
            } else if (month1 > month2) {
                return false;
            } else {
                return day1 < day2;
            }
        }
    }

    
    /**
     * Checks if this `CustomDate` is after the specified `CustomDate`.
     * 
     * @param otherDate the `CustomDate` to compare
     * @return `true` if this `CustomDate` is after the specified `CustomDate`, `false` otherwise
     */
    public boolean isAfter(CustomDate otherDate) {
        return !isBefore(otherDate) && !equals(otherDate);
    }
    
    
    /**
     * Converts a `java.util.Date` object to a `CustomDate` object.
     * 
     * @param utilDate the `java.util.Date` object to convert
     * @return the corresponding `CustomDate` object
     */
    public static CustomDate convertFromUtilDate(java.util.Date utilDate) {
		int day = utilDate.getDate();
        int month = utilDate.getMonth() + 1; 
        int year = utilDate.getYear() + 1900; 
        String dateString = String.format("%02d/%02d/%04d", day, month, year);
        return new CustomDate(dateString);
    }
    
    /**
     * Converts the CustomDate object to a java.util.Date object.
     *
     * @return a java.util.Date representation of the CustomDate
     */
    public Date toUtilDate() {
        int day = getDay();
        int month = getMonth();
        int year = getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month - 1); 
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long timeInMillis = calendar.getTimeInMillis();
        return new Date(timeInMillis);
    }
    

	/**
	 * Adds a specified number of days to the current CustomDate and returns a new CustomDate object.
	 *
	 * @param days the number of days to add
	 * @return a new CustomDate object with the added days
	 */
    public CustomDate addDays(int days) {
        String[] components = date.split("/");

        int day = Integer.parseInt(components[0]);
        int month = Integer.parseInt(components[1]);
        int year = Integer.parseInt(components[2]);

        day += days;

        while (day > getDaysInMonth(month, year)) {
            day -= getDaysInMonth(month, year);
            month++;

            if (month > 12) {
                month = 1;
                year++;
            }
        }

        String modifiedDate = String.format("%02d/%02d/%04d", day, month, year);
        return new CustomDate(modifiedDate);
    }


	/**
	 * Calculates the number of days in a given month of a specified year.
	 *
	 * @param month the month value (1-12)
	 * @param year  the year value
	 * @return the number of days in the specified month of the specified year
	 */
    private static int getDaysInMonth(int month, int year) {
        int[] daysInMonth = {
                31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        };

        if (month == 2 && isLeapYear(year)) {
            return 29;
        } else {
            return daysInMonth[month - 1];
        }
    }
    /**
     * Checks if a given year is a leap year.
     *
     * @param year the year value
     * @return true if the year is a leap year, false otherwise
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    
	/**
	* Find the day between the two dates.
	*/
    
    public static CustomDate calculateMiddleDate(CustomDate date1, CustomDate date2) {
        int day1 = date1.getDay();
        int month1 = date1.getMonth();
        int year1 = date1.getYear();

        int day2 = date2.getDay();
        int month2 = date2.getMonth();
        int year2 = date2.getYear();

        int totalDays = calculateDateDifference(date1, date2);
        int middleDay = day1 + totalDays / 2;
        int middleMonth;
        int middleYear;

        if (middleDay > date1.getDaysInMonth(month1, year1)) {
            middleDay = middleDay - date1.getDaysInMonth(month1, year1);
            middleMonth = month1 + 1;
            middleYear = year1;
        } else {
            middleMonth = month1;
            middleYear = year1;
        }

        if (middleMonth > 12) {
            middleMonth = 1;
            middleYear++;
        }

        String middleDate = String.format("%02d/%02d/%04d", middleDay, middleMonth, middleYear);
        return new CustomDate(middleDate);
    }
    
	/**
	* Calculate the difference between two dates in days.
	*/
    
    public static int calculateDateDifference(CustomDate date1, CustomDate date2) {
        CustomDate startDate;
        CustomDate endDate;

        // Determine the start and end dates based on their chronological order
        if (date1.isBefore(date2)) {
            startDate = date1;
            endDate = date2;
        } else {
            startDate = date2;
            endDate = date1;
        }

        int difference = 0;
        CustomDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            currentDate = currentDate.addDays(1);
            difference++;
        }

        return difference - 1; // Subtract 1 to exclude the end date itself
    }
    
    
}
