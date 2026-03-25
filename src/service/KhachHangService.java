package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import model.*;

@Path("/khachhang")
public class KhachHangService {

    static List<KhachHang> ds = new ArrayList<>();

    static {
        ds.add(new KhachHang("001","Nam","CanTho"));
        ds.add(new KhachHangVIP("002","An","HauGiang",0.1));
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<KhachHang> getAll(){
        return ds;
    }

    @GET
    @Path("/get/{cccd}")
    @Produces(MediaType.APPLICATION_JSON)
    public KhachHang get(@PathParam("cccd") String cccd){

        for(KhachHang kh : ds){
            if(kh.getCccd().equals(cccd))
                return kh;
        }
        return null;
    }

    @GET
    @Path("/add/{cccd}/{ten}/{dc}")
    @Produces(MediaType.TEXT_PLAIN)
    public String add(@PathParam("cccd") String cccd,
                      @PathParam("ten") String ten,
                      @PathParam("dc") String dc){
    	for(KhachHang kh : ds){
            if(kh.getCccd().equals(cccd))
            	return "trung cccd";
            }
        ds.add(new KhachHang(cccd,ten,dc));
        return "Da them khach hang";
    }

    @GET
    @Path("/addnewvip/{cccd}/{ten}/{dc}/{giam}")
    @Produces(MediaType.TEXT_PLAIN)
    public String addNewVIP(@PathParam("cccd") String cccd,
                            @PathParam("ten") String ten,
                            @PathParam("dc") String dc,
                            @PathParam("giam") double giam){

        for(KhachHang kh : ds){
            if(kh.getCccd().equals(cccd)){
                return "Khach hang da ton tai";
            }
        }

        KhachHangVIP vip = new KhachHangVIP(cccd, ten, dc, giam);
        vip.setNgayTao(new Date());
        ds.add(vip);

        return "Da them khach hang VIP moi";
    }
    
    @GET
    @Path("/updatevip/{cccd}/{ten}/{dc}/{giam}")
    @Produces(MediaType.TEXT_PLAIN)
    public String updateVIP(@PathParam("cccd") String cccd,
                            @PathParam("ten") String ten,
                            @PathParam("dc") String dc,
                            @PathParam("giam") double giam){

        for(KhachHang kh : ds){
            if(kh.getCccd().equals(cccd) && kh instanceof KhachHangVIP){
                KhachHangVIP vip = (KhachHangVIP) kh;
                vip.setHten(ten);
                vip.setDchi(dc);
                vip.setTleGiam(giam);
                return "Da cap nhat khach hang VIP";
            }
        }

        return "Khong tim thay khach hang VIP";
    }
    
    @GET
    @Path("/upgradevip/{cccd}/{giam}")
    @Produces(MediaType.TEXT_PLAIN)
    public String upgradeToVIP(@PathParam("cccd") String cccd,
                               @PathParam("giam") double giam){

        for(int i = 0; i < ds.size(); i++){
            KhachHang kh = ds.get(i);
            if(kh.getCccd().equals(cccd) && !(kh instanceof KhachHangVIP)){
                KhachHangVIP vip = new KhachHangVIP(kh.getCccd(), kh.getHten(), kh.getDchi(), giam);
                vip.setNgayTao(new Date());
                ds.set(i, vip);
                return "Da nang cap khach hang thanh VIP";
            }
        }

        return "Khong tim thay khach hang thuong de nang cap";
    }
    
    @GET
    @Path("/update/{cccd}/{ten}/{dc}")
    @Produces(MediaType.TEXT_PLAIN)
    public String update(@PathParam("cccd") String cccd,
                         @PathParam("ten") String ten,
                         @PathParam("dc") String dc){

        for(KhachHang kh : ds){
            if(kh.getCccd().equals(cccd)){
                kh.setHten(ten);
                kh.setDchi(dc);
                return "Da cap nhat khach hang";
            }
        }

        return "Khong tim thay khach hang";
    }
    
    @GET
    @Path("/getvip/{cccd}")
    @Produces(MediaType.APPLICATION_JSON)
    public KhachHangVIP getVIP(@PathParam("cccd") String cccd){

        for(KhachHang kh : ds){
            if(kh.getCccd().equals(cccd) && kh instanceof KhachHangVIP){
                return (KhachHangVIP) kh;
            }
        }

        return null;
    }

    @GET
    @Path("/delete/{cccd}")
    @Produces(MediaType.TEXT_PLAIN)
    public String delete(@PathParam("cccd") String cccd){

        for(KhachHang kh : ds){
            if(kh.getCccd().equals(cccd)){
                ds.remove(kh);
                return "Da xoa khach hang";
            }
        }

        return "Khong tim thay khach hang";
    }
}