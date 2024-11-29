/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interface;

import Model.BuySeat;
import java.util.List;
import java.rmi.*;

/**
 *
 * @author DELL
 */
public interface BuySeatInterface extends Remote{

    public int addBuySeat(BuySeat buySeat) throws RemoteException;
    public List<BuySeat> getShowBuySeat(int show_id) throws RemoteException;
    public List<BuySeat> getAllShowBuySeat(int cinema_id) throws RemoteException;
    public BuySeat getBuySeat(int id) throws RemoteException;
}
