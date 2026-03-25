package model;

import java.time.LocalDate;

public class MyDate {

    private int ngay;
    private int thang;
    private int nam;

    public MyDate() {

        LocalDate now = LocalDate.now();
        this.ngay = now.getDayOfMonth();
        this.thang = now.getMonthValue();
        this.nam = now.getYear();
    }

    public MyDate(int ngay, int thang, int nam) {
        this.ngay = ngay;
        this.thang = thang;
        this.nam = nam;
    }

    public int getNgay() {
        return ngay;
    }

    public void setNgay(int ngay) {
        this.ngay = ngay;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public int compareTo(MyDate other) {
        if (this.nam != other.nam) {
            return this.nam - other.nam;
        }
        if (this.thang != other.thang) {
            return this.thang - other.thang;
        }
        return this.ngay - other.ngay;
    }

    public boolean after(MyDate other) {
        return this.compareTo(other) > 0;
    }

    public boolean before(MyDate other) {
        return this.compareTo(other) < 0;
    }

    public void nhap() {
    }

    public void in() {
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", ngay, thang, nam);
    }
}