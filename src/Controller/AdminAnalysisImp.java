/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interface.AdminAnalysisInterface;
import Model.BuySeat;
import Model.Cinema;
import Model.Expense;
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

/**
 *
 * @author DELL
 */
public class AdminAnalysisImp extends UnicastRemoteObject implements AdminAnalysisInterface {

    
    public AdminAnalysisImp() throws RemoteException {
    }

    @Override
    public DefaultCategoryDataset getCinemaAnalysis(int year, String[] monthList) throws RemoteException {
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Cinema> cinemaList = new CinemaImp().getAllCinema();
        for (Cinema cinema : cinemaList) {
            List<BuySeat> buySeatList = new BuySeatImp().getAllShowBuySeat(cinema.getId());
            for (String month : monthList) {
                double price = getCinemaPrice(year, month, buySeatList);
                dataset.addValue(price, cinema.getBranch(), month.substring(0, 3));
            }
        }
        return getReduceDataSet(dataset);
    }

    @Override
    public DefaultPieDataset getCinemaAnalysisPieChart(int year, String[] monthList) throws RemoteException {
       
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<Cinema> cinemaList = new CinemaImp().getAllCinema();
        for (Cinema cinema : cinemaList) {
            double price = 0;
            List<BuySeat> buySeatList = new BuySeatImp().getAllShowBuySeat(cinema.getId());
            for (String month : monthList) {
                price += getCinemaPrice(year, month, buySeatList);
            }
            if (price > 0) {
                dataset.setValue(cinema.getBranch(), price);
            }

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
                price += salary.getSalary() + salary.getBonus();
            }

        }
        return price;
    }

    @Override
    public DefaultCategoryDataset getCinemaProfitAnalysis(int year, String[] monthList) throws RemoteException {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Cinema> cinemaList = new CinemaImp().getAllCinema();
        for (Cinema cinema : cinemaList) {
            List<BuySeat> buySeatList = new BuySeatImp().getAllShowBuySeat(cinema.getId());
            List<Expense> expenseList = new ExpenseImp().getAllExpense(cinema.getId());
            List<Salary> salaryList = new SalaryImp().getAllSalary(cinema.getId());
            double incomePrice = 0;
            double outcomePrice = 0;
            for (String month : monthList) {
                incomePrice += getCinemaPrice(year, month, buySeatList);
                outcomePrice += getExpensePrice(year, month, expenseList)+getSalaryPrice(year, month, salaryList);
            }
            dataset.addValue(incomePrice, "Income", cinema.getBranch());
            dataset.addValue(outcomePrice, "Outcome", cinema.getBranch());

        }

        return dataset;
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

        return dataset;
    }
}
