/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author DELL
 */
public class Ticket implements Serializable{
    int id,cinema_id,show_id;
    List<Integer> seat_id_list;
    String date;
    
    public Ticket() {
    }

    public Ticket(int id, int cinema_id, int show_id, List<Integer> seat_id_list, String date) {
        this.id = id;
        this.cinema_id = cinema_id;
        this.show_id = show_id;
        this.seat_id_list = seat_id_list;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(int cinema_id) {
        this.cinema_id = cinema_id;
    }

    public int getShow_id() {
        return show_id;
    }

    public void setShow_id(int show_id) {
        this.show_id = show_id;
    }

    public List<Integer> getSeat_id_list() {
        return seat_id_list;
    }

    public void setSeat_id_list(List<Integer> seat_id_list) {
        this.seat_id_list = seat_id_list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
