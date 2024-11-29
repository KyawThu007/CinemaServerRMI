/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interface.RoomInterface;
import Model.Room;
import Model.Seat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.rmi.*;
import java.rmi.server.*;

/**
 *
 * @author DELL
 */
public class RoomImp extends UnicastRemoteObject implements RoomInterface {

    static Connection con = null;

    public RoomImp() throws RemoteException {

    }

    @Override
    public int addRoom(Room room) throws RemoteException {
        int n = 0;
        try {
            con = new DatabaseConnection().getConnection();
            String query = "insert into room (cinema_id,room) values (?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, room.getCinema_id());
            ps.setString(2, room.getRoom());
            n = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    @Override
    public void updateRoom(Room room) throws RemoteException {
        try {
            con = new DatabaseConnection().getConnection();
            String query = "update room set room=? where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, room.getRoom());
            ps.setInt(2, room.getId());
            ps.executeUpdate();
            ps.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRoom(int id) throws RemoteException {
        try {
            con = new DatabaseConnection().getConnection();
            String query = "delete from room where id=?";
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
    public List<Room> getAllRoom(int cinema_id) throws RemoteException {
        List<Room> list = new ArrayList<>();
        try {
            con = new DatabaseConnection().getConnection();
            String query = "select * from room where cinema_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cinema_id);
            // Execute the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setCinema_id(rs.getInt("cinema_id"));
                room.setId(rs.getInt("id"));
                room.setRoom(rs.getString("room"));
                list.add(room);
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
    public Room getRoom(int id) throws RemoteException {
        Room room = null;
        try {
            con = new DatabaseConnection().getConnection();
            String query = "select * from room where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            // Execute the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                room = new Room();
                room.setCinema_id(rs.getInt("cinema_id"));
                room.setId(rs.getInt("id"));
                room.setRoom(rs.getString("room"));

            }
            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }

    @Override
    public void logMessage(String log) throws RemoteException {
        JOptionPane.showMessageDialog(null, log, "", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public int addSeat(Seat seat) throws RemoteException {
        int n = 0;
        try {
            con = new DatabaseConnection().getConnection();
            String query = "insert into seat (cinema_id,room_id,seat,seat_row,seat_column,type,price) values (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, seat.getCinema_id());
            ps.setInt(2, seat.getRoom_id());
            ps.setString(3, seat.getSeat());
            ps.setString(4, seat.getSeat_row());
            ps.setInt(5, seat.getSeat_column());
            ps.setString(6, seat.getType());
            ps.setDouble(7, seat.getPrice());
            n = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    @Override
    public void updateSeat(Seat seat) throws RemoteException {
        try {
            con = new DatabaseConnection().getConnection();
            String query = "update seat set seat=?,seat_row=?,seat_column=?,type=?,price=? where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, seat.getSeat());
            ps.setString(2, seat.getSeat_row());
            ps.setInt(3, seat.getSeat_column());
            ps.setString(4, seat.getType());
            ps.setDouble(5, seat.getPrice());
            ps.setInt(6, seat.getId());
            ps.executeUpdate();
            ps.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSeat(int id) throws RemoteException {
        try {
            con = new DatabaseConnection().getConnection();
            String query = "delete from seat where id=?";
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
    public List<Seat> getAllSeat(int cinema_id, int room_id) throws RemoteException {
        List<Seat> list = new ArrayList<>();
        try {
            con = new DatabaseConnection().getConnection();
            String query = "select * from seat where cinema_id=?  and room_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cinema_id);
            ps.setInt(2, room_id);
            // Execute the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Seat seat = new Seat();
                seat.setCinema_id(rs.getInt("cinema_id"));
                seat.setRoom_id(rs.getInt("room_id"));
                seat.setId(rs.getInt("id"));
                seat.setSeat(rs.getString("seat"));
                seat.setSeat_row(rs.getString("seat_row"));
                seat.setSeat_column(rs.getInt("seat_column"));
                seat.setType(rs.getString("type"));
                seat.setPrice(rs.getDouble("price"));
                list.add(seat);
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
    public List<Seat> getAllSeatBySort(int cinema_id, int room_id, String seat_row) throws RemoteException {
        List<Seat> list = new ArrayList<>();
        try {
            con = new DatabaseConnection().getConnection();
            String query = "select * from seat where cinema_id=?  and room_id=? and seat_row=? "
                    + "order by seat_column ASC";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cinema_id);
            ps.setInt(2, room_id);
            ps.setString(3, seat_row);
            // Execute the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Seat seat = new Seat();
                seat.setCinema_id(rs.getInt("cinema_id"));
                seat.setRoom_id(rs.getInt("room_id"));
                seat.setId(rs.getInt("id"));
                seat.setSeat(rs.getString("seat"));
                seat.setSeat_row(rs.getString("seat_row"));
                seat.setSeat_column(rs.getInt("seat_column"));
                seat.setType(rs.getString("type"));
                seat.setPrice(rs.getDouble("price"));
                list.add(seat);
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
    public Seat getSeat(int id) throws RemoteException {
        Seat seat = null;
        try {
            con = new DatabaseConnection().getConnection();
            String query = "select * from seat where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            // Execute the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                seat = new Seat();
                seat.setCinema_id(rs.getInt("cinema_id"));
                seat.setRoom_id(rs.getInt("room_id"));
                seat.setId(rs.getInt("id"));
                seat.setSeat(rs.getString("seat"));
                seat.setSeat_row(rs.getString("seat_row"));
                seat.setSeat_column(rs.getInt("seat_column"));
                seat.setType(rs.getString("type"));
                seat.setPrice(rs.getDouble("price"));
            }
            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seat;
    }
}
