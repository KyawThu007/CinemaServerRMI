/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interface.ShowInterface;
import Model.Show;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import javax.swing.JOptionPane;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Collections;

/**
 *
 * @author DELL
 */
public class ShowImp extends UnicastRemoteObject implements ShowInterface {

    static Connection con = null;

    public ShowImp() throws RemoteException {

    }

    @Override
    public int addShow(Show show) throws RemoteException {
        int n = 0;
        try {
            con = new DatabaseConnection().getConnection();
            String query = "insert into show2(cinema_id,room_id,movie_id,start_date,end_date,time) values (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, show.getCinema_id());
            ps.setInt(2, show.getRoom_id());
            ps.setInt(3, show.getMovie_id());
            ps.setDate(4, Date.valueOf(show.getStart_date()));
            ps.setDate(5, Date.valueOf(show.getEnd_date()));
            ps.setString(6, show.getTime());
            n = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    @Override
    public void updateShow(Show show) throws RemoteException {
        try {
            con = new DatabaseConnection().getConnection();
            String query = "update show2 set start_date=?,end_date=?,time=? where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setDate(1, Date.valueOf(show.getStart_date()));
            ps.setDate(2, Date.valueOf(show.getEnd_date()));
            ps.setString(3, show.getTime());
            ps.setInt(4, show.getId());
            ps.executeUpdate();
            ps.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteShow(int id) throws RemoteException {
        try {
            con = new DatabaseConnection().getConnection();
            String query = "delete from show2 where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Show> getAllShowMovie(int cinema_id, String startDate, String endDate, String showTime) throws RemoteException {
        List<Show> list = new ArrayList<>();
        try {
            con = new DatabaseConnection().getConnection();
            String query = "select * from show2 where cinema_id=? and ? <= end_date and start_date <= ? and time = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cinema_id);
            ps.setDate(2, Date.valueOf(startDate));
            ps.setDate(3, Date.valueOf(endDate));
            ps.setString(4, showTime);

            // Execute the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Show show = new Show();
                show.setId(rs.getInt("id"));
                show.setCinema_id(rs.getInt("cinema_id"));
                show.setRoom_id(rs.getInt("room_id"));
                show.setMovie_id(rs.getInt("movie_id"));
                show.setStart_date(rs.getDate("start_date").toString());
                show.setEnd_date(rs.getDate("end_date").toString());
                show.setTime(rs.getString("time"));
                list.add(show);
            }
            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Sort and then reverse the list
        list.sort((s1, s2) -> s1.getStart_date().compareTo(s2.getStart_date())); // or another sorting criterion
        Collections.reverse(list); // Reverse the list to get the desired order

        return list;
    }

    @Override
    public Show getShowMovie(int id) throws RemoteException {
        Show show = null;
        try {
            con = new DatabaseConnection().getConnection();
            String query = "select * from show2 where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            // Execute the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                show = new Show();
                show.setId(rs.getInt("id"));
                show.setCinema_id(rs.getInt("cinema_id"));
                show.setRoom_id(rs.getInt("room_id"));
                show.setMovie_id(rs.getInt("movie_id"));
                show.setStart_date(rs.getDate("start_date").toString());
                show.setEnd_date(rs.getDate("end_date").toString());
                show.setTime(rs.getString("time"));
                break;
            }
            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return show;
    }

    @Override
    public void logMessage(String log) throws RemoteException {

        JOptionPane.showMessageDialog(null, log, "", JOptionPane.INFORMATION_MESSAGE);
    }
}
