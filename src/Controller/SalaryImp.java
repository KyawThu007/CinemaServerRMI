/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interface.SalaryInterface;
import Model.Salary;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.rmi.*;
import java.rmi.server.*;

/**
 *
 * @author DELL
 */
public class SalaryImp extends UnicastRemoteObject implements SalaryInterface {

    static Connection con;

    public SalaryImp() throws RemoteException{
        
    }
    @Override
    public int addSalary(Salary salary) throws RemoteException{
        int n = 0;
        try {
            con = new DatabaseConnection().getConnection();
            String query = "insert into salary(cinema_id,employee_id,salary,bonus,date) values (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, salary.getCinema_id());
            ps.setInt(2, salary.getEmployee_id());
            ps.setDouble(3, salary.getSalary());
            ps.setDouble(4, salary.getBonus());
            ps.setDate(5, Date.valueOf(salary.getDate()));
            n = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    @Override
    public void updateSalary(Salary salary) throws RemoteException{
        try {
            con = new DatabaseConnection().getConnection();
            String query = "update salary set salary=?,bonus=?,date=? where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setDouble(1, salary.getSalary());
            ps.setDouble(2, salary.getBonus());
            ps.setDate(3, Date.valueOf(salary.getDate()));
            ps.setInt(4, salary.getId());
            ps.executeUpdate();
            ps.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSalary(int id) throws RemoteException{
        try {
            con = new DatabaseConnection().getConnection();
            String query = "delete from salary where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            con.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public List<Salary> getAllSalary(int cinema_id) throws RemoteException{
        List<Salary> list = new ArrayList<>();
        try {
            con = new DatabaseConnection().getConnection();
            String query = "select * from salary where cinema_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cinema_id);

            // Execute the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Salary salary = new Salary();
                salary.setId(rs.getInt("id"));
                salary.setCinema_id(rs.getInt("cinema_id"));
                salary.setEmployee_id(rs.getInt("employee_id"));
                salary.setSalary(rs.getDouble("salary"));
                salary.setBonus(rs.getDouble("bonus"));
                salary.setDate(rs.getDate("date").toString());
                list.add(salary);
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
    public void logMessage(String log) throws RemoteException{
        JOptionPane.showMessageDialog(null, log, "", JOptionPane.INFORMATION_MESSAGE);
    }

}
