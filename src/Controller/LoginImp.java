/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Interface.LoginInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.server.*;
import java.rmi.*;

/**
 *
 * @author DELL
 */
public class LoginImp extends UnicastRemoteObject implements LoginInterface {

    static Connection con = null;
    

    public LoginImp() throws RemoteException {
        super();
    }

    @Override
    public int loginAdmin(String username, String password) throws RemoteException {

        int n = 0;
        try {
            con = new DatabaseConnection().getConnection();
            String query = "select * from admin where username=? and password=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            // Execute the query
            ResultSet rs = ps.executeQuery();  // Use executeQuery for SELECT statements

            while (rs.next()) {
                n = 1;
                break;
            }

            // Close the ResultSet and PreparedStatement
            rs.close();
            ps.close();

            con.close();

        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return n;
    }

    @Override
    public int loginUser(int roleID, String username, String password) throws RemoteException {
        int n = 0;
        try {
            con = new DatabaseConnection().getConnection();
            String query = "select * from employee where name=? and password=? and role_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, roleID);
            // Execute the query
            ResultSet rs = ps.executeQuery();  // Use executeQuery for SELECT statements

            while (rs.next()) {
                n = rs.getInt("cinema_id");
                break;
            }

            // Close the ResultSet and PreparedStatement
            rs.close();
            ps.close();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

}
