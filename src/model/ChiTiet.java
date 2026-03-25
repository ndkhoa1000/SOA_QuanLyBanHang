package model;

public class ChiTiet {

    private int sluong;
    private double dgia;
    private String msoHH;
    
    public ChiTiet() {}

    public ChiTiet(int sluong, double dgia, String msoHH) {
        this.sluong = sluong;
        this.dgia = dgia;
        this.msoHH = msoHH;
    }

    public int getSluong() {
        return sluong;
    }

    public void setSluong(int sluong) {
        this.sluong = sluong;
    }

    public double getDgia() {
        return dgia;
    }

    public void setDgia(double dgia) {
        this.dgia = dgia;
    }

    public String getMsoHH() {
		return msoHH;
	}

	public void setMsoHH(String msoHH) {
		this.msoHH = msoHH;
	}

	public void nhap() {}

    public void in() {}
}