/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interface.SystemAnalysisInterface;
import Model.BuySeat;
import Model.Expense;
import Model.FeedBack;
import Model.Movie;
import Model.Salary;
import Model.Show;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class SystemAnalysisImp extends UnicastRemoteObject implements SystemAnalysisInterface {

    public SystemAnalysisImp() throws RemoteException {

    }

    @Override
    public DefaultCategoryDataset getCinemaProfitAnalysis(int cinema_id, int year, String[] monthList) throws RemoteException {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<BuySeat> buySeatList = new BuySeatImp().getAllShowBuySeat(cinema_id);
        List<Expense> expenseList = new ExpenseImp().getAllExpense(cinema_id);
        List<Salary> salaryList = new SalaryImp().getAllSalary(cinema_id);
        for (String month : monthList) {
            double incomePrice = 0;
            double outcomePrice = 0;
            incomePrice += getCinemaPrice(year, month, buySeatList);
            outcomePrice += getExpensePrice(year, month, expenseList) + getSalaryPrice(year, month, salaryList);
            dataset.addValue(incomePrice, "Income", month.substring(0, 3));
            dataset.addValue(outcomePrice, "Outcome", month.substring(0, 3));
        }

        return dataset;
    }

    @Override
    public DefaultCategoryDataset getOnlyCinemaAnalysis(int cinema_id, String[] genreList, int year, String[] monthList) throws RemoteException {
       
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<BuySeat> buySeatList = new BuySeatImp().getAllShowBuySeat(cinema_id);
        for (String genre : genreList) {
            double price = 0;
            for (String month : monthList) {
                price += getCinemaGenrePrice(year, month, genre, buySeatList);
            }
            dataset.addValue(price, genre, "");
        }
        return getReduceDataSet(dataset);
    }

    public double getCinemaGenrePrice(int setYear, String setMonthName, String genre, List<BuySeat> buySeatList) {
        double price = 0;
        try {
            for (BuySeat buySeat : buySeatList) {
                String dateString = buySeat.getDate();
                LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String getMonthName = date.getMonth().name();
                int getYear = date.getYear();

                if (setYear == getYear && setMonthName.toLowerCase().equals(getMonthName.toLowerCase())) {
                    Show show = new ShowImp().getShowMovie(buySeat.getShow_id());
                    Movie movie = new MovieImp().getMovie(show.getMovie_id());
                    if (movie.getGenre().equals(genre)) {
                        price += buySeat.getPrice();
                    }
                }
            }
        } catch (RemoteException e) {
        }
        return price;

    }

    public double getCinemaPrice(int setYear, String setMonthName, List<BuySeat> buySeatList) {
        double price = 0;
        for (BuySeat buySeat : buySeatList) {
            String dateString = buySeat.getDate();
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String getMonthName = date.getMonth().name();
            int getYear = date.getYear();

            if (setYear == getYear && setMonthName.toLowerCase().equals(getMonthName.toLowerCase())) {
                price += buySeat.getPrice();
            }

        }
        return price;

    }

    public double getExpensePrice(int setYear, String setMonthName, List<Expense> expenseList) {
        double price = 0;
        for (Expense expense : expenseList) {
            String dateString = expense.getDate();
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String getMonthName = date.getMonth().name();
            int getYear = date.getYear();

            if (setYear == getYear && setMonthName.toLowerCase().equals(getMonthName.toLowerCase())) {
                price += expense.getAmount();
            }

        }

        return price;
    }

    public double getSalaryPrice(int setYear, String setMonthName, List<Salary> salaryList) {
        double price = 0;
        for (Salary salary : salaryList) {
            String dateString = salary.getDate();
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String getMonthName = date.getMonth().name();
            int getYear = date.getYear();

            if (setYear == getYear && setMonthName.toLowerCase().equals(getMonthName.toLowerCase())) {
                price += salary.getSalary();
            }

        }

        return price;
    }

    @Override
    public DefaultCategoryDataset getYearMovieAnalysis(int cinema_id, String[] genreList, int year, String[] monthList) throws RemoteException {
       
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<BuySeat> buySeatList = new BuySeatImp().getAllShowBuySeat(cinema_id);
        for (String genre : genreList) {
            for (String month : monthList) {
                double price = getMovieGenre(year, month, genre, buySeatList);
                dataset.addValue(price, genre, month.substring(0, 3));
            }
        }
        return getReduceDataSet(dataset);
    }

    @Override
    public DefaultPieDataset getYearMovieAnalysisPieChart(int cinema_id, String[] genreList, int year, String[] monthList) throws RemoteException {
       
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<BuySeat> buySeatList = new BuySeatImp().getAllShowBuySeat(cinema_id);
        for (String genre : genreList) {
            double price = 0;
            for (String month : monthList) {
                price += getMovieGenre(year, month, genre, buySeatList);
            }
            if (price > 0) {
                dataset.setValue(genre, price);
            }
        }
        return dataset;
    }

    @Override
    public DefaultCategoryDataset getMonthGenreMovieAnalysis(int cinema_id, String genre, int year, String month) throws RemoteException {
       
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<BuySeat> buySeatList = new BuySeatImp().getAllShowBuySeat(cinema_id);
        List<Movie> movieList = new MovieImp().getAllMovie();
        for (Movie movie : movieList) {
            double price = getMovie(year, month, genre, movie.getId(), buySeatList);
            dataset.addValue(price, movie.getTitle(), "");
        }
        return getReduceDataSet(dataset);
    }

    public DefaultCategoryDataset getReduceDataSet(DefaultCategoryDataset dataset) {
        DefaultCategoryDataset reduce_dataset = new DefaultCategoryDataset();
        // Iterate over all series
        for (int i = 0; i < dataset.getRowCount(); i++) {
            String rowKey = dataset.getRowKey(i).toString();

            boolean flag = false;
            // Iterate over all categories
            for (int j = 0; j < dataset.getColumnCount(); j++) {
                double value = 0;
                if (dataset.getValue(i, j) != null) {
                    value = (double) dataset.getValue(i, j);
                }

                if (value > 0) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                // Iterate over all categories
                for (int j = 0; j < dataset.getColumnCount(); j++) {
                    String colKey = dataset.getColumnKey(j).toString();
                    double value = 0;
                    if (dataset.getValue(i, j) != null) {
                        value = (double) dataset.getValue(i, j);
                    }
                    reduce_dataset.addValue(value, rowKey, colKey);
                }
            }
        }

        return reduce_dataset;
    }

    public double getMovie(int setYear, String setMonthName, String genre, int movie_id, List<BuySeat> buySeatList) {
        
        double price = 0;
        try {
            for (BuySeat buySeat : buySeatList) {
                String dateString = buySeat.getDate();
                LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String getMonthName = date.getMonth().name();
                int getYear = date.getYear();

                if (setYear == getYear && setMonthName.toLowerCase().equals(getMonthName.toLowerCase())) {
                    Show show = new ShowImp().getShowMovie(buySeat.getShow_id());
                    Movie movie = new MovieImp().getMovie(show.getMovie_id());
                    if (movie_id == movie.getId() && movie.getGenre().equals(genre)) {
                        price += buySeat.getPrice();
                    }
                }

            }
        } catch (RemoteException e) {
        }
        return price;

    }

    public double getMovieGenre(int setYear, String setMonthName, String genre, List<BuySeat> buySeatList) {
        
        double price = 0;
        try {
            for (BuySeat buySeat : buySeatList) {
                String dateString = buySeat.getDate();
                LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String getMonthName = date.getMonth().name();
                int getYear = date.getYear();

                if (setYear == getYear && setMonthName.toLowerCase().equals(getMonthName.toLowerCase())) {
                    Show show = new ShowImp().getShowMovie(buySeat.getShow_id());
                    Movie movie = new MovieImp().getMovie(show.getMovie_id());
                    if (movie.getGenre().equals(genre)) {
                        price += buySeat.getPrice();
                    }
                }

            }
        } catch (RemoteException e) {
        }
        return price;
    }

    @Override
    public DefaultCategoryDataset getOnlyExpenseAnalysis(int cinema_id, int year, String[] monthList) throws RemoteException {
       
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Expense> expenseList = new ExpenseImp().getAllExpense(cinema_id);
        List<Salary> salaryList = new SalaryImp().getAllSalary(cinema_id);
        double price = 0;
        double salary = 0;
        for (String month : monthList) {
            price += getExpensePrice(year, month, expenseList);
            salary += getSalaryPrice(year, month, salaryList);
        }
        dataset.addValue(price, "Expense", "");
        dataset.addValue(salary, "Salary", "");

        return getReduceDataSet(dataset);
    }
    public List<FeedBack> getAllFeedback(int movie_id) {
        List<FeedBack> list = new ArrayList<>();
        try {
            Connection con = new DatabaseConnection().getConnection();
            String query = "select * from rating where m_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, movie_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FeedBack feedBack = new FeedBack();
                feedBack.setMovie_id(rs.getInt("m_id"));
                feedBack.setEmoji("emoji");
                list.add(feedBack);
            }
            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public DefaultCategoryDataset getFeedbackAnalysis(int movie_id) throws RemoteException{
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<FeedBack> feedbackList = getAllFeedback(movie_id);
        
        String[] categorys= {"အရမ်းကောင်း","ကောင်းသည်","အသင့်အတင့်","ရယ်ရသည်","ကြောက်ဖို့ကောင်း","မကောင်း"};
        for (String category : categorys) {
            double value = getFeedBack(category, feedbackList);
            dataset.addValue(value, category, "");
        }
        

        return getReduceDataSet(dataset);    
    }
    
   public double getFeedBack(String category,List<FeedBack> feedbackList) {
        double count = 0;
        for (FeedBack feedBack : feedbackList) {
            if (feedBack.equals(category)) {
                count ++;
            }
        }

        return count;
    } 
}
