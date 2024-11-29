/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.sql.Connection;
import Interface.BuySeatInterface;
import Model.BuySeat;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Collections;

/**
 *
 * @author DELL
 */
public class BuySeatImp extends UnicastRemoteObject implements BuySeatInterface {

    static Connection con = null;

    public BuySeatImp() throws RemoteException {

    }

    @Override
    public int addBuySeat(BuySeat buySeat) throws RemoteException {
        int n = 0;
        try {
            con = new DatabaseConnection().getConnection();
            String query = "insert into buySeat(id,cinema_id,show_id,seat_id,date,price,time) values (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, buySeat.getId());
            ps.setInt(2, buySeat.getCinema_id());
            ps.setInt(3, buySeat.getShow_id());
            ps.setInt(4, buySeat.getSeat_id());
            ps.setDate(5, Date.valueOf(buySeat.getDate()));
            ps.setDouble(6, buySeat.getPrice());
            ps.setString(7, buySeat.getTime());
            n = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return n;
    }

    @Override
    public List<BuySeat> getShowBuySeat(int show_id) throws RemoteException {

        List<BuySeat> list = new ArrayList<>();
        try {
            con = new DatabaseConnection().getConnection();
            String query = "select * from buySeat where show_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, show_id);

            // Execute the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BuySeat buySeat = new BuySeat();
                buySeat.setId(rs.getInt("id"));
                buySeat.setCinema_id(rs.getInt("cinema_id"));
                buySeat.setShow_id(rs.getInt("show_id"));
                buySeat.setSeat_id(rs.getInt("seat_id"));
                buySeat.setDate(rs.getDate("date").toString());
                buySeat.setPrice(rs.getDouble("price"));
                buySeat.setTime(rs.getString("time"));
                list.add(buySeat);
            }
            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.reverse(list);
        return list;

    }

    @Override
    public List<BuySeat> getAllShowBuySeat(int cinema_id) throws RemoteException {

        List<BuySeat> list = new ArrayList<>();
        try {
            con = new DatabaseConnection().getConnection();
            String query = "select * from buySeat where cinema_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cinema_id);

            // Execute the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BuySeat buySeat = new BuySeat();
                buySeat.setId(rs.getInt("id"));
                buySeat.setCinema_id(rs.getInt("cinema_id"));
                buySeat.setShow_id(rs.getInt("show_id"));
                buySeat.setSeat_id(rs.getInt("seat_id"));
                buySeat.setDate(rs.getDate("date").toString());
                buySeat.setPrice(rs.getDouble("price"));
                buySeat.setTime(rs.getString("time"));
                list.add(buySeat);
            }
            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.reverse(list);
        return list;
    }
    
    @Override
    public BuySeat getBuySeat(int id) throws RemoteException{

        BuySeat buySeat=null;
        try {
            con = new DatabaseConnection().getConnection();
            String query = "select * from buySeat where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            // Execute the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                buySeat = new BuySeat();
                buySeat.setId(rs.getInt("id"));
                buySeat.setCinema_id(rs.getInt("cinema_id"));
                buySeat.setShow_id(rs.getInt("show_id"));
                buySeat.setSeat_id(rs.getInt("seat_id"));
                buySeat.setDate(rs.getDate("date").toString());
                buySeat.setPrice(rs.getDouble("price"));
                buySeat.setTime(rs.getString("time"));
            }
            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return buySeat;

    }
}
