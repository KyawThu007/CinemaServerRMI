/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Model.Movie;
import java.util.List;
import java.rmi.*;

/**
 *
 * @author DELL
 */
public interface MovieInterface extends Remote{

    public int addMovie(Movie movie) throws RemoteException;

    public void updateMovie(Movie movie) throws RemoteException;

    public void deleteMovie(int id) throws RemoteException;

    public List<Movie> getAllMovie() throws RemoteException;
    
    public Movie getMovie(int id) throws RemoteException;
    
    public void logMessage(String log) throws RemoteException;
}
