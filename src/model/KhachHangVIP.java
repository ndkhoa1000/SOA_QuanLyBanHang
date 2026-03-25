package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KhachHangVIP extends KhachHang {

    private double tleGiam;
    private Date ngayTao;

    public KhachHangVIP() {
    }

    public KhachHangVIP(String cccd, String hten, String dchi, double tleGiam) {
        super(cccd, hten, dchi);
        this.tleGiam = tleGiam;
        this.ngayTao = new Date();
    }

    public KhachHangVIP(String cccd, String hten, String dchi, double tleGiam, Date ngayTao) {
        super(cccd, hten, dchi);
        this.tleGiam = tleGiam;
        this.ngayTao = ngayTao;
    }

    public double getTleGiam() {
        return tleGiam;
    }

    public void setTleGiam(double tleGiam) {
        this.tleGiam = tleGiam;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getNgayTaoString() {
        if (ngayTao == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(ngayTao);
    }

    @Override
    public void nhap() {
    }

    @Override
    public void in() {
        System.out.println("CCCD: " + getCccd());
        System.out.println("Ten: " + getHten());
        System.out.println("Dia chi: " + getDchi());
        System.out.println("Ti le giam: " + tleGiam);
        System.out.println("Ngay tao: " + ngayTao);
    }
}