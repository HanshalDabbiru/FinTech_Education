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

public class UserStock {

    //Attributes of the UserStock is the data shown in the stock screener
    private Stock stock;
    private String name;
    private StockDividend dividendYield;
    private BigDecimal currentPrice;



    public UserStock(String stockName) throws IOException{
        try {
            this.name = stockName;
            Stock stock = YahooFinance.get(this.name);
            this.dividendYield = stock.getDividend();
            this.currentPrice = stock.getQuote().getPrice();
            this.stock = stock;

        }
        catch(NullPointerException n) {
            //probably will like output something to a text output in the app
            System.out.println("hi");

        }
        catch(Exception e) {
            //same thing as above

        }

    }

    public String formatDate(Calendar c) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = format.format(c.getTime());
        return formattedDate;
    }

    public MyDate getCurrentDate(Calendar c) {
        String holder = formatDate(c);
        String[] list = holder.split("-");
        MyDate date = new MyDate(Integer.parseInt(list[0]), Integer.parseInt(list[1]), Integer.parseInt(list[2]));
        return date;
    }

    //filling up the historical data attributes with values
    public double getAnyMovingAverage(Calendar endDate, int movingAverageDay) throws IOException {

        ArrayList<String> dates = new ArrayList();
        ArrayList<BigDecimal> closePrices = new ArrayList();
        GregorianCalendar startDate = new GregorianCalendar(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));
        startDate.add(GregorianCalendar.DAY_OF_MONTH,-1 * movingAverageDay);
        //getting the start date and end date
        try {
            List<HistoricalQuote> history = this.stock.getHistory(startDate, endDate, Interval.DAILY);
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
        catch (IOException e) {
            //tell user there is error reading stock data
            e.printStackTrace();
            return -1;
        }
        catch(ArithmeticException e) {
            //tell user invalid dates or something
            return -3;
        }
        catch (Exception e) {
            //tell the user that data on the stock could not be pulled
            return -2;
        }




    }

    public double getCurrentMovingAverage(int days) throws IOException {
        ArrayList<Double> closePrices = new ArrayList<Double>();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.add(Calendar.DAY_OF_YEAR, -1 * days);
        try {
            List<HistoricalQuote> history = this.stock.getHistory(startDate, endDate, Interval.DAILY);

            double sum = 0;
            for(HistoricalQuote quote : history){
                closePrices.add(quote.getClose().doubleValue());
                sum += quote.getClose().doubleValue();
            }
            try {
                return sum / closePrices.size();
            }
            catch (ArithmeticException E){
                //prolly print something on the app that says: invalid dates
                return 0;
            }

        }
        catch (IOException e){
            //tell user there is error reading stock data
            return -1;
        }
        catch (Exception e) {
            //tell the user that data on the stock could not be pulled
            return -2;
        }

    }

    public ArrayList<Double> getStockPriceData(int days){
        ArrayList<Double> closePrices = new ArrayList<Double>();
        ArrayList<String> dates = new ArrayList<String>();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.add(Calendar.DAY_OF_YEAR, -1 * days);
        try {
            List<HistoricalQuote> history = this.stock.getHistory(startDate, endDate, Interval.DAILY);

            for(HistoricalQuote quote : history){
                closePrices.add(quote.getClose().doubleValue());
                dates.add(formatDate(quote.getDate()));
            }
            System.out.println(dates.toString());
            return closePrices;
        }
        catch (ArithmeticException E){
            //prolly print something on the app that says: invalid dates
            return null;
        }
        catch (IOException e){
            //tell user there is error reading stock data
            return null;
        }
        catch (Exception e) {
            //tell the user that data on the stock could not be pulled
            return null;
        }

    }
    //function calculates the moving average of the stock
    //the days parameter is the amount of days you are calculating the moving average for


    //this function looks at a stock
    //if it meets all of the ######## 8 requirements, the function will return true, otherwise false
    public boolean check_stock() throws IOException{
        //150 day, 200 day, and 50 day moving average
        double movingAverage200 = this.getCurrentMovingAverage(200);
        double movingAverage150 =  this.getCurrentMovingAverage(150);
        double movingAverage50 = this.getCurrentMovingAverage(50);

        //200 day moving average one month ago
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        double movingAverage200MonthAgo = this.getAnyMovingAverage(c, 200);

        //25% of 52 week low and high
        ArrayList<Double> stockData52Weeks = this.getStockPriceData(52 * 7);
        double min = Integer.MAX_VALUE;
        double max = Integer.MIN_VALUE;
        for(int i = 0; i < stockData52Weeks.size(); i++) {
            if(stockData52Weeks.get(i) < min) {
                min = stockData52Weeks.get(i);
            }
            if(stockData52Weeks.get(i) > max) {
                max = stockData52Weeks.get(i);
            }

        }

        min *= 1.25;
        max *= 0.75;



        return true;
    }


}
