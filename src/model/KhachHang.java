package model;

public class KhachHang {

    private String cccd;
    private String hten;
    private String dchi;

    public KhachHang() {
    }

    public KhachHang(String cccd, String hten, String dchi) {
        this.cccd = cccd;
        this.hten = hten;
        this.dchi = dchi;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getHten() {
        return hten;
    }

    public void setHten(String hten) {
        this.hten = hten;
    }

    public String getDchi() {
        return dchi;
    }

    public void setDchi(String dchi) {
        this.dchi = dchi;
    }

    public void nhap() {
    }

    public void in() {
    }
}