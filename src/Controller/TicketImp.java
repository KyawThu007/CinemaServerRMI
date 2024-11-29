/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interface.TicketInterface;
import Model.Ticket;
import java.sql.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.rmi.*;
import java.rmi.server.*;

/**
 *
 * @author DELL
 */
public class TicketImp extends UnicastRemoteObject implements TicketInterface {

    static Connection con;

    public TicketImp() throws RemoteException {

    }

    @Override
    public int addTicket(Ticket ticket) throws RemoteException {
        int n = 0;
        try {
            con = new DatabaseConnection().getConnection();
            String query = "insert into ticket(cinema_id,show_id,seat_id_list,date) values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            Gson gson = new Gson();
            // Convert List<Integer> to JSON String
            String jsonString = gson.toJson(ticket.getSeat_id_list());
            ps.setInt(1, ticket.getCinema_id());
            ps.setInt(2, ticket.getShow_id());
            ps.setString(3, jsonString);
            ps.setDate(4, Date.valueOf(ticket.getDate()));
            n = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    @Override
    public List<Ticket> getAllShowTicket(int cinema_id, String startDate, String endDate) throws RemoteException {
        List<Ticket> list = new ArrayList<>();
        try {

            con = new DatabaseConnection().getConnection();
            String query = "select * from ticket where cinema_id=? and date >= ? and date <= ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cinema_id);
            ps.setDate(2, Date.valueOf(startDate));
            ps.setDate(3, Date.valueOf(endDate));
            // Execute the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Type listType = new TypeToken<List<Integer>>() {
                }.getType();
                List<Integer> newList = new Gson().fromJson(rs.getString("seat_id_list"), listType);

                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("id"));
                ticket.setCinema_id(rs.getInt("cinema_id"));
                ticket.setShow_id(rs.getInt("show_id"));
                ticket.setSeat_id_list(newList);
                ticket.setDate(rs.getDate("date").toString());

                list.add(ticket);
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
    public List<Ticket> getShowTicket(int show_id) throws RemoteException {
        List<Ticket> list = new ArrayList<>();
        try {
            con = new DatabaseConnection().getConnection();
            String query = "select * from ticket where show_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, show_id);

            // Execute the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Type listType = new TypeToken<List<Integer>>() {
                }.getType();
                List<Integer> newList = new Gson().fromJson(rs.getString("seat_id_list"), listType);

                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("id"));
                ticket.setCinema_id(rs.getInt("cinema_id"));
                ticket.setShow_id(rs.getInt("show_id"));
                ticket.setSeat_id_list(newList);
                ticket.setDate(rs.getDate("date").toString());
                list.add(ticket);
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

}
