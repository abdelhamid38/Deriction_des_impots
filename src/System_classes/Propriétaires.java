package System_classes;

import java.sql.Date;

public class Propriétaires {

    private int id;
    private String Name;
    private Date date_nss;
    private String Adress;
    private String Tlf;

    public Propriétaires(int id, String name, Date date_nss, String adress, String tlf) {
        this.id = id;
        Name = name;
        this.date_nss = date_nss;
        Adress = adress;
        Tlf = tlf;
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

    public String getTlf() {
        return Tlf;
    }

    public void setTlf(String tlf) {
        Tlf = tlf;
    }
}
