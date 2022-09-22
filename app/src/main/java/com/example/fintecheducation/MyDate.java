package com.example.fintecheducation;

public class MyDate {



///////////////////////////////////////////////////////////////////
//
// private fields
//

    //these are the variables (state) of the class
    private int _year;
    private int _month;
    private int _day;

    //this is the daysInMonth Variable
//index 0 is month 1
//index 1 is month 2 etc
//each element is the amount of days in the corresponding month
    private int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};



///////////////////////////////////////////////////////////////////
//
// constructors
//

//these are constructors that create the actual object

    //three parameters
    public MyDate(int years, int month, int days) {
        setYear(years);
        setMonth(month);
        setDay(days);
    }

    //no parameter
    public MyDate() {
        this(1970, 1, 1);
    }

    //one date parameter
    public MyDate(MyDate other) {
        this(other.getYear(), other.getMonth(), other.getDay());
    }




///////////////////////////////////////////////////////////////////
//
// "getter" methods
//

    //methods that will return the value of the field the user wants
    public int getDay() {
        return this._day;
    }

    public int getMonth() {
        return this._month;
    }

    public int getYear() {
        return this._year;
    }
///////////////////////////////////////////////////////////////////
//
// private "setter" methods (see specification for details)
//

    //methods that will change the value of the field the user wants
    private void setYear(int year) {
        if(year < 0) {
            throw new IllegalArgumentException();
        }
        else {
            this._year = year;
        }
    }

    private void setMonth(int month) {
        if(month <= 0) {
            throw new IllegalArgumentException();
        }
        int counter = 0;
        while(month > 12) {
            counter++;
            month -= 12;
        }
        this._month = month;
        setYear(counter + this._year);
    }



    private void setDay(int day) {
        int leapYearCounter = 0;
        if(day <= 0) {
            throw new IllegalArgumentException();
        }
//filtering the years
        if(day > 365) {

            while (day > 364) {

                if(MyDate.isLeapYear(this.getYear())) {
                    leapYearCounter++;
                    day -= 366;
                }else {
                    day -= 365;
                }
                setYear(this._year + 1);

            }
        }




        int monthCounter = 0;
        int indexCounter = this._month - 1;
//filtering the months
        while(day > daysInMonth[indexCounter]) {
            monthCounter++;
            day -= daysInMonth[indexCounter];
            indexCounter++;
            if(indexCounter > 11) {
                indexCounter = 0;
            }
        }

        setMonth(this._month + monthCounter);
        this._day = day ;
    }



///////////////////////////////////////////////////////////////////
//
// instance methods
//

    //adds a certain amounts of days to the current date
    public void addDays(int days) {
        if(days < 0) {
            throw new IllegalArgumentException();
        }else {
            setDay(this._day + days);
        }
    }


    //adds a certain amount of weeks (one week = 7 days) to the current date
    public void addWeeks(int weeks) {
        if(weeks < 0) {
            throw new IllegalArgumentException();
        }

        addDays(weeks * 7);
    }

    //checks if one date is equal to the other date
    public int compareTo(MyDate other) {
        if(other.getYear() == this.getYear()) {
            if(other.getMonth() == this.getMonth()) {
                if(other.getDay() == this.getDay()) {
                    return 0;
                }
                else if(other.getDay() > this.getDay()) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
            else if(other.getMonth() > this.getMonth()) {
                return -1;
            }
            else {
                return 1;
            }
        }
        else if(other.getYear() > this.getYear()) {
            return -1;
        }
        else {
            return 1;
        }
    }

    public int daysTo(MyDate other) {
        int dayCounter = 0;
        MyDate thisDate = new MyDate(this);
        if(thisDate.equals(other)) {
            return 0;
        }

        if(thisDate.compareTo(other) == -1) {
            while(thisDate.equals(other) == false) {
                if(thisDate.isLeapYear() == true && thisDate.getMonth() == 3 && thisDate.getDay() == 1) {
                    dayCounter++;

                }
                dayCounter++;
                thisDate.addDays(1);
            }
        }
        else {
            while(thisDate.equals(other) == false) {
                if(other.isLeapYear() == true && other.getMonth() == 3 && other.getDay() == 1) {
                    dayCounter++;

                }
                dayCounter++;
                other.addDays(1);
            }
            dayCounter *= -1;
        }

        return dayCounter;


    }

    public boolean equals(MyDate other) {
        if(other.getYear() == this.getYear() && other.getMonth() == this.getMonth() && other.getDay() == this.getDay()) {
            return true;
        }

        return false;
    }
    public boolean isLeapYear() {
        return MyDate.isLeapYear(this.getYear());
    }

    public String toString() {
        String monthVal = "";
        String dayVal = "";
        if(this.getMonth() < 10) {
            monthVal = "0" + this._month;
        }
        else {
            monthVal = "" + this._month;
        }

        if(this.getDay() < 10) {
            dayVal = "0" + this._day;
        }
        else {
            dayVal = "" + this._day;
        }

        return this._year + "/" + monthVal + "/" + dayVal;
    }


///////////////////////////////////////////////////////////////////
//
// static methods
//



    public static boolean isLeapYear(int year) {
        if(year % 100 == 0) {
            if(year % 400 == 0) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if(year % 4 == 0) {
                return true;
            }
            else {
                return false;
            }
        }

    }

    public static int daysInMonth(int year, int month) {
        int[] daysInEachMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if(MyDate.isLeapYear(year) == true && month == 2) {
            return 29;
        }
        else {
            return daysInEachMonth[month - 1];
        }
    }

    public static int daysInYear(int year) {
        if(MyDate.isLeapYear(year)) {
            return 366;
        }
        else {
            return 365;
        }
    }



}