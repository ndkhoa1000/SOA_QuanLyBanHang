package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {

    private Date date;

    public MyDate() {
        this.date = new Date();
    }

    public MyDate(int ngay, int thang, int nam) {
        // java.util.Date uses 0-based months and year since 1900
        this.date = new Date(nam - 1900, thang - 1, ngay);
    }

    public int getNgay() {
        return date.getDate();
    }

    public void setNgay(int ngay) {
        date.setDate(ngay);
    }

    public int getThang() {
        return date.getMonth() + 1;
    }

    public void setThang(int thang) {
        date.setMonth(thang - 1);
    }

    public int getNam() {
        return date.getYear() + 1900;
    }

    public void setNam(int nam) {
        date.setYear(nam - 1900);
    }

    public int compareTo(MyDate other) {
        return this.date.compareTo(other.date);
    }

    public boolean after(MyDate other) {
        return this.date.after(other.date);
    }

    public boolean before(MyDate other) {
        return this.date.before(other.date);
    }

    public void nhap() {
    }

    public void in() {
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}