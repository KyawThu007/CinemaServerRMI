/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Model.Cinema;
import java.util.List;
import java.rmi.*;

/**
 *
 * @author DELL
 */
public interface CinemaInterface extends Remote{

    public int addCinema(Cinema cinema) throws RemoteException;

    public void updateCinema(Cinema cinema) throws RemoteException;

    public void deleteCinema(int id) throws RemoteException;

    public List<Cinema> getAllCinema() throws RemoteException;

    public Cinema getCinema(int id) throws RemoteException;

    public void logMessage(String log) throws RemoteException;
}
