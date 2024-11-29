/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Model.Expense;
import java.util.List;
import java.rmi.*;

/**
 *
 * @author DELL
 */
public interface ExpenseInterface extends Remote{

   public int addExpense(Expense expense) throws RemoteException;

    public void updateExpense(Expense expense) throws RemoteException;

    public void deleteExpense(int id) throws RemoteException;

    public List<Expense> getAllExpense(int cinema_id) throws RemoteException;
    
    public void logMessage(String log) throws RemoteException;

}
