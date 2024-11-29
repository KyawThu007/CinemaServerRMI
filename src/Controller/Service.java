package Controller;

import Interface.AdminAnalysisInterface;
import Interface.BuySeatInterface;
import Interface.CinemaInterface;
import Interface.EmployeeInterface;
import Interface.ExpenseInterface;
import Interface.LoginInterface;
import Interface.MovieInterface;
import Interface.RoleTypeInterface;
import Interface.RoomInterface;
import Interface.SalaryInterface;
import Interface.ShowInterface;
import Interface.SystemAnalysisInterface;
import Interface.TicketInterface;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JTextArea;

public class Service {

    private LoginInterface loginStub;
    private CinemaInterface cinemaStub;
    private EmployeeInterface employeeStub;
    private ExpenseInterface expenseStub;
    private SalaryInterface salaryStub;
    private RoleTypeInterface roleTypeStub;
    private RoomInterface roomStub;
    private AdminAnalysisInterface adminAnalysisStub;
    private MovieInterface movieStub;
    private ShowInterface showStub;
    private BuySeatInterface buySeatStub;
    private TicketInterface ticketStub;
    private SystemAnalysisInterface systemAnalysisStub;

    private Registry registry;
    private final int port;

    public Service(int port) {
        this.port = port;
        try {
            if (registry == null) {
                registry = LocateRegistry.createRegistry(port);
                //System.out.println("Registry created on port " + port);
            }
        } catch (RemoteException e) {
            //System.out.println("Registry creation failed: " + e.getMessage());
        }
    }

    public void startService(JTextArea logArea) {
        try {
            loginStub = new LoginImp();
            cinemaStub = new CinemaImp();
            employeeStub = new EmployeeImp();
            expenseStub = new ExpenseImp();
            salaryStub = new SalaryImp();
            roleTypeStub = new RoleTypeImp();
            roomStub = new RoomImp();
            adminAnalysisStub = new AdminAnalysisImp();
            movieStub = new MovieImp();
            showStub = new ShowImp();
            buySeatStub = new BuySeatImp();
            ticketStub = new TicketImp();
            systemAnalysisStub = new SystemAnalysisImp();

            Naming.rebind("rmi://localhost:" + port + "/login", loginStub);
            Naming.rebind("rmi://localhost:" + port + "/cinema", cinemaStub);
            Naming.rebind("rmi://localhost:" + port + "/employee", employeeStub);
            Naming.rebind("rmi://localhost:" + port + "/expense", expenseStub);
            Naming.rebind("rmi://localhost:" + port + "/salary", salaryStub);
            Naming.rebind("rmi://localhost:" + port + "/role", roleTypeStub);
            Naming.rebind("rmi://localhost:" + port + "/room", roomStub);
            Naming.rebind("rmi://localhost:" + port + "/show", showStub);
            Naming.rebind("rmi://localhost:" + port + "/movie", movieStub);
            Naming.rebind("rmi://localhost:" + port + "/buySeat", buySeatStub);
            Naming.rebind("rmi://localhost:" + port + "/ticket", ticketStub);
            Naming.rebind("rmi://localhost:" + port + "/adminAnalysis", adminAnalysisStub);
            Naming.rebind("rmi://localhost:" + port + "/systemAnalysis", systemAnalysisStub);

            logArea.append("Port : "+port+", Server is ready."+"\n");
        } catch (RemoteException | MalformedURLException e) {
            //System.out.println("Service startup failed: " + e.getMessage());
        }
    }

    public void stopService(JTextArea logArea) {
        try {
            // Unbind all objects from the registry
            unbindObject("login");
            unbindObject("cinema");
            unbindObject("employee");
            unbindObject("expense");
            unbindObject("salary");
            unbindObject("role");
            unbindObject("room");
            unbindObject("show");
            unbindObject("movie");
            unbindObject("buySeat");
            unbindObject("ticket");
            unbindObject("adminAnalysis");
            unbindObject("systemAnalysis");
            logArea.append("Port : "+port+", Server is stop."+"\n");
            
        } catch (Exception e) {
            //System.out.println("Service stop failed: " + e.getMessage());
        }
    }

    private void unbindObject(String name) {
        try {
            Naming.unbind("rmi://localhost:" + port + "/" + name);
            // System.out.println("Unbound: " + name);
        } catch (Exception e) {
            // System.out.println("Error unbinding " + name + ": " + e.getMessage());
        }
    }
}
