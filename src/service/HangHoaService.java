package service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import model.HangHoa;

@Path("/hanghoa")
public class HangHoaService {
//	Dung GET cho tat ca API de de dang test tren trinh duyet hoac client
	
   static List<HangHoa> ds = new ArrayList<>();
   static int AUTO_ID = 1;

   @GET
   @Path("/all")
   @Produces(MediaType.APPLICATION_JSON)
   public List<HangHoa> getAll(){
       return ds;
   }

   @GET
   @Path("/add/{ten}/{nsx}")
   @Produces(MediaType.TEXT_PLAIN)
   public String add(@PathParam("ten") String ten,
                     @PathParam("nsx") String nsx){

       String id = "HH" + AUTO_ID++;

       HangHoa hh = new HangHoa(id, ten, nsx);
       ds.add(hh);

       return "Da them hang hoa: " + id;
   }

   @GET
   @Path("/get/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public HangHoa get(@PathParam("id") String id){

       for(HangHoa hh : ds){
           if(hh.getMso().equals(id))
               return hh;
       }

       return null;
   }
   
   @GET
   @Path("/update/{id}/{ten}/{nsx}")
   @Produces(MediaType.TEXT_PLAIN)
   public String update(@PathParam("id") String id,
                        @PathParam("ten") String ten,
                        @PathParam("nsx") String nsx){

       for(HangHoa hh : ds){
           if(hh.getMso().equals(id)){
               hh.setTen(ten);
               hh.setNsxuat(nsx);
               return "Da cap nhat hang hoa";
           }
       }

       return "Khong tim thay hang hoa";
   }


   @GET
   @Path("/delete/{id}")
   @Produces(MediaType.TEXT_PLAIN)
   public String delete(@PathParam("id") String id){

       for(HangHoa hh : ds){
           if(hh.getMso().equals(id)){
               ds.remove(hh);
               return "Da xoa hang hoa";
           }
       }

       return "Khong tim thay hang hoa";
   }
}