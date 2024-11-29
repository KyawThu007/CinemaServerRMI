/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Model.Employee;
import java.util.List;
import java.rmi.*;

/**
 *
 * @author DELL
 */
public interface EmployeeInterface extends Remote{

    public int addEmployee(Employee employee) throws RemoteException;

    public void updateEmployee(Employee employee) throws RemoteException;

    public void deleteEmployee(int id) throws RemoteException;
    
    public Employee getEmployee(int id) throws RemoteException;
    
    public List<Employee> getAllEmployee(int cinema_id) throws RemoteException;
        
    public void logMessage(String log) throws RemoteException;
}
