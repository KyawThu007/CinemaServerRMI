/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Model.Show;
import java.util.List;
import java.rmi.*;

/**
 *
 * @author DELL
 */
public interface ShowInterface extends Remote{

    public int addShow(Show show) throws RemoteException;

    public void updateShow(Show show) throws RemoteException;

    public void deleteShow(int id) throws RemoteException;

    public List<Show> getAllShowMovie(int cinema_id,String startDate, String endDate, String showTime) throws RemoteException;

    public Show getShowMovie(int id) throws RemoteException;

    public void logMessage(String log) throws RemoteException;
}
