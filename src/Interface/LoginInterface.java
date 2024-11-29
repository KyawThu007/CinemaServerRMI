/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.rmi.*;

/**
 *
 * @author DELL
 */
public interface LoginInterface extends Remote {

    public int loginAdmin(String username, String password) throws RemoteException;

    public int loginUser(int roleID, String username, String password) throws RemoteException;

}
