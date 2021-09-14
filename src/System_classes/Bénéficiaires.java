/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System_classes;

import java.sql.Date;

/**
 *
 * @author Abdelhamid
 */
public class Bénéficiaires {

    private int id;
    private String Name;
    private Date date_nss;
    private String Adress;
    private String Wilaya;
    private String Commune;
    private String Nationalite;

    public Bénéficiaires(int id, String name, Date date_nss, String adress, String wilaya, String commune, String nationalite) {
        this.id = id;
        Name = name;
        this.date_nss = date_nss;
        Adress = adress;
        Wilaya = wilaya;
        Commune = commune;
        Nationalite = nationalite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getDate_nss() {
        return date_nss;
    }

    public void setDate_nss(Date date_nss) {
        this.date_nss = date_nss;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getWilaya() {
        return Wilaya;
    }

    public void setWilaya(String wilaya) {
        Wilaya = wilaya;
    }

    public String getCommune() {
        return Commune;
    }

    public void setCommune(String commune) {
        Commune = commune;
    }

    public String getNationalite() {
        return Nationalite;
    }

    public void setNationalite(String nationalite) {
        Nationalite = nationalite;
    }
}
