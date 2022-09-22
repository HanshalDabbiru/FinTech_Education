package com.example.fintecheducation;

import yahoofinance.Stock;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.stock.StockDividend;
import yahoofinance.YahooFinance;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
//Main Object

class UserClass {

    //Attributes of the UserStock is the data shown in the stock screener
    private Stock stock;
    private String name;
    private StockDividend dividendYield;
    private BigDecimal current_price;



    public UserClass(String stockName) throws IOException{
        this.name = stockName;
        Stock stock = YahooFinance.get(this.name);
        this.dividendYield = stock.getDividend();
        this.current_price = stock.getQuote().getPrice();
        this.stock = stock;

    }

    private String formatDate(Calendar c) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = format.format(c.getTime());
        return formattedDate;
    }

    private MyDate getCurrentDate(Calendar c) {
        String holder = formatDate(c);
        String[] list = holder.split("-");
        MyDate date = new MyDate(Integer.parseInt(list[0]), Integer.parseInt(list[1]), Integer.parseInt(list[2]));
        return date;
    }

    //filling up the historical data attributes with values
    public double getMovingAverage(int days, MyDate date) throws IOException {

        ArrayList<String> dates = new ArrayList();
        ArrayList<BigDecimal> closePrices = new ArrayList();
        //getting the start date and end date


        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        MyDate holderDate = getCurrentDate(endDate);
        int numberOfDays = date.daysTo(holderDate);
        startDate.add(Calendar.DAY_OF_YEAR, -1 * (numberOfDays + days));
        endDate.add(Calendar.DAY_OF_YEAR, -1 * numberOfDays);


        List<HistoricalQuote> history = stock.getHistory(startDate, endDate, Interval.DAILY);
        for(HistoricalQuote quote : history){
            dates.add(formatDate(quote.getDate()));
            closePrices.add(quote.getClose());
        }
        double sum = 0;
        for (BigDecimal prices: closePrices) {
            sum += prices.doubleValue();
        }
        System.out.println(dates);
        return sum / closePrices.size();



    }

    //function calculates the moving average of the stock
    //the days parameter is the amount of days you are calculating the moving average for


    //this function looks at a stock
    //if it meets all of the ######## 8 requirements, the function will return true, otherwise false
    public boolean check_stock(){
        return false;
    }


}

