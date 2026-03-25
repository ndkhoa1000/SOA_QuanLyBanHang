package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import model.*;
import org.glassfish.jersey.client.ClientConfig;
import util.ServiceUrlConfig;

@Path("/hoadon")
public class HoaDonService {

	static int AUTO_ID = 1;
    static List<HoaDon> ds = new ArrayList<>();

    static Client client = ClientBuilder.newClient(new ClientConfig());

    static WebTarget targetKH = client.target(
            UriBuilder.fromUri(ServiceUrlConfig.khachHangBaseUrl()).build()
    );
    static WebTarget targetHH = client.target(
            UriBuilder.fromUri(ServiceUrlConfig.hangHoaBaseUrl()).build()
    );

    public static <T> T callServiceObject(Class<T> clazz, String service, String op, String... params) {

    	WebTarget target = service.equals("khachhang") ? targetKH : targetHH;
        WebTarget temp = target.path(service).path(op);

        for(String p : params){
            temp = temp.path(p);
        }

        return temp.request()
                .accept(MediaType.APPLICATION_JSON)
                .get(clazz);
    }

    public static String callServiceText(String service, String op, String... params){

    	WebTarget target = service.equals("khachhang") ? targetKH : targetHH;
        WebTarget temp = target.path(service).path(op);

        for(String p : params){
            temp = temp.path(p);
        }

        return temp.request()
                .accept(MediaType.TEXT_PLAIN)
                .get(String.class);
    }

    @GET
    @Path("/create/{cccd}/{nvien}")
    @Produces(MediaType.TEXT_PLAIN)
    public String create(@PathParam("cccd") String cccd,
                         @PathParam("nvien") String nvien){

        KhachHang kh = callServiceObject(KhachHang.class,"khachhang","get",cccd);

        if(kh == null){
            return "Khong tim thay khach hang";
        }

        HoaDon hd = new HoaDon();
        hd.setMso("HD" + AUTO_ID++);
        hd.setNvien(nvien);
        hd.setKhachHang(kh.getCccd());
        hd.setChiTietList(new ArrayList<>());
        hd.setTde(new Date());
        ds.add(hd);

        return "Da tao hoa don: " + hd.getMso();
    }

    @GET
    @Path("/additem/{idhd}/{idhh}/{sl}/{gia}")
    @Produces(MediaType.TEXT_PLAIN)
    public String addItem(@PathParam("idhd") String idhd,
                          @PathParam("idhh") String idhh,
                          @PathParam("sl") int sl,
                          @PathParam("gia") double gia){

        HoaDon hd = null;

        for(HoaDon h : ds){
            if(h.getMso().equals(idhd)){
                hd = h;
                break;
            }
        }

        if(hd == null){
            return "Khong tim thay hoa don";
        }

        HangHoa hh = callServiceObject(HangHoa.class,"hanghoa","get",idhh);

        if(hh == null){
            return "Khong tim thay hang hoa";
        }

        ChiTiet ct = new ChiTiet();
        ct.setSluong(sl);
        ct.setDgia(gia);
        ct.setMsoHH(idhh);
        
        hd.getChiTietList().add(ct);

        return "Da them san pham vao hoa don";
    }

    @GET
    @Path("/update/{idhd}/{nvien}")
    @Produces(MediaType.TEXT_PLAIN)
    public String update(@PathParam("idhd") String idhd,
                         @PathParam("nvien") String nvien){

        for(HoaDon hd : ds){
            if(hd.getMso().equals(idhd)){
                hd.setNvien(nvien);
                return "Da cap nhat hoa don";
            }
        }

        return "Khong tim thay hoa don";
    }

    @GET
    @Path("/delete/{idhd}")
    @Produces(MediaType.TEXT_PLAIN)
    public String delete(@PathParam("idhd") String idhd){

        for(int i=0;i<ds.size();i++){
            if(ds.get(i).getMso().equals(idhd)){
                ds.remove(i);
                return "Da xoa hoa don";
            }
        }

        return "Khong tim thay hoa don";
    }

    @GET
    @Path("/updateitem/{idhd}/{idhh}/{sl}/{gia}")
    @Produces(MediaType.TEXT_PLAIN)
    public String updateItem(@PathParam("idhd") String idhd,
                             @PathParam("idhh") String idhh,
                             @PathParam("sl") int sl,
                             @PathParam("gia") double gia){

        for(HoaDon hd : ds){

            if(hd.getMso().equals(idhd)){
        		for (ChiTiet ct : hd.getChiTietList()) {
        			if (ct.getMsoHH().equals(idhh)) {
	        			ct.setSluong(sl);
	        			ct.setDgia(gia);
	        			return "Da cap nhat san pham" +idhh+ " trong hoa don";
        			}
        		}
        		return "khong tim thay hang hoa "+ idhh;
            }
        }

        return "Khong tim thay hoa don";
    }

    @GET
    @Path("/removeitem/{idhd}/{idhh}")
    @Produces(MediaType.TEXT_PLAIN)
    public String removeItem(@PathParam("idhd") String idhd,
                             @PathParam("idhh") String idhh){
        for (HoaDon hd : ds) {
            if (hd.getMso().equals(idhd)) {
            	Iterator<ChiTiet> it = hd.getChiTietList().iterator();
            	while (it.hasNext()) {
            	    ChiTiet ct = it.next();
            	    if (ct.getMsoHH().equals(idhh)) {
            	        it.remove();
            	        return "Da xoa san pham " + idhh;
            	    }
            	}
                return "Khong tim thay hang hoa " + idhh;
            }
        }
        return "Khong tim thay hoa don";
    }

    @GET
    @Path("/tong/{idhd}")
    @Produces(MediaType.TEXT_PLAIN)
    public String tong(@PathParam("idhd") String idhd){

        for(HoaDon hd : ds){
            if(hd.getMso().equals(idhd)){
                return hd.tong()+"";
            }
        }

        return "Khong tim thay hoa don";
    }

    @GET
    @Path("/tongsaugiam/{idhd}")
    @Produces(MediaType.TEXT_PLAIN)
    public String tongsaugiam(@PathParam("idhd") String idhd){

        for(HoaDon hd : ds){
            if(hd.getMso().equals(idhd)){
            	KhachHangVIP khVIP = callServiceObject(KhachHangVIP.class,"khachhang","getvip",hd.getKhachHang());
                if(khVIP == null){
                    return "Khong phai khach hang VIP";
                }
                if (khVIP.getNgayTao().after(hd.getTde())) {
                	return "Khong ap dung duoc, ngay tao VIP sau ngay tao Hoa Don";
                }
                return hd.tong()*(1-khVIP.getTleGiam())+"";
            }
        }
        return "Khong tim thay hoa don";
    }

    @GET
    @Path("/detail/{idhd}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String,Object>> detail(@PathParam("idhd") String idhd) {
        
    	HoaDon hd = null;
        for(HoaDon h : ds){
            if(h.getMso().equals(idhd)){
                hd = h;
                break;
            }
        }
        if(hd == null) return null;

        List<Map<String,Object>> result = new ArrayList<>();

        for(ChiTiet ct : hd.getChiTietList()){
            HangHoa hh = callServiceObject(HangHoa.class, "hanghoa", "get", ct.getMsoHH());

            if(hh != null){
                Map<String,Object> ctMap = new HashMap<>();
                ctMap.put("mso", hh.getMso());
                ctMap.put("ten", hh.getTen());
                ctMap.put("nsxuat", hh.getNsxuat());
                ctMap.put("sluong", ct.getSluong());
                ctMap.put("dgia", ct.getDgia());
                result.add(ctMap);
            }
        }

        return result;
    }

    @GET
    @Path("/get/{idhd}")
    @Produces(MediaType.APPLICATION_JSON)
    public HoaDon get(@PathParam("idhd") String idhd){

        for(HoaDon hd : ds){
            if(hd.getMso().equals(idhd)){
                return hd;
            }
        }

        return null;
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HoaDon> getAll(){
        return ds;
    }
}