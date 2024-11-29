/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Model.Salary;
import java.util.List;
import java.rmi.*;

/**
 *
 * @author DELL
 */
public interface SalaryInterface extends Remote{

    public int addSalary(Salary salary) throws RemoteException;

    public void updateSalary(Salary salary) throws RemoteException;

    public void deleteSalary(int id) throws RemoteException;

    public List<Salary> getAllSalary(int cinema_id) throws RemoteException;
    
    public void logMessage(String log) throws RemoteException;
}
