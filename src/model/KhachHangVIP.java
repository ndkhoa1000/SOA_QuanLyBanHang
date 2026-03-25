package model;

public class KhachHangVIP extends KhachHang {

    private double tleGiam;
    private MyDate ngayTao;

    public KhachHangVIP() {
    }

    public KhachHangVIP(String cccd, String hten, String dchi, double tleGiam) {
        super(cccd, hten, dchi);
        this.tleGiam = tleGiam;
        this.ngayTao = new MyDate();
    }

    public KhachHangVIP(String cccd, String hten, String dchi, double tleGiam, MyDate ngayTao) {
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

    public MyDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(MyDate ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getNgayTaoString() {
        if (ngayTao == null)
            return null;
        return ngayTao.toString();
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