/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Model.Room;
import Model.Seat;
import java.util.List;
import java.rmi.*;

/**
 *
 * @author DELL
 */
public interface RoomInterface extends Remote{

    public int addRoom(Room room) throws RemoteException;

    public void updateRoom(Room room) throws RemoteException;

    public void deleteRoom(int id) throws RemoteException;

    public List<Room> getAllRoom(int cinema_id) throws RemoteException;

    public Room getRoom(int id) throws RemoteException;

    public void logMessage(String log) throws RemoteException;

    public int addSeat(Seat seat) throws RemoteException;

    public void updateSeat(Seat seat) throws RemoteException;

    public void deleteSeat(int id) throws RemoteException;

    public List<Seat> getAllSeat(int cinema_id, int room_id) throws RemoteException;
    
    public Seat getSeat(int id) throws RemoteException;

    public List<Seat> getAllSeatBySort(int cinema_id, int room_id, String seat_row) throws RemoteException;

}
