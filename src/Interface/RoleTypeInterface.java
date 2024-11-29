/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interface;

import Model.RoleType;
import java.util.List;
import java.rmi.*;

/**
 *
 * @author DELL
 */
public interface RoleTypeInterface extends Remote{

    public int addRoleType(RoleType role_type) throws RemoteException;

    public void updateRoleType(RoleType role_type) throws RemoteException;

    public void deleteRoleType(int id) throws RemoteException;

    public List<RoleType> getAllRoleType() throws RemoteException;
    
    public RoleType getRoleType(int id) throws RemoteException;
    
    public void logMessage(String log) throws RemoteException;

}
