package service.client;

import java.net.URI;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import org.glassfish.jersey.client.ClientConfig;

import util.ServiceUrlConfig;

public class TestServiceClient {

    public static String callService(WebTarget target, String service, String op, String... params) {

        WebTarget temp = target.path(service).path(op);

        for (String p : params) {
            temp = temp.path(p);
        }

        return temp.request()
                .accept(MediaType.TEXT_PLAIN)
                .get(String.class);
    }

    public static String callServiceJSON(WebTarget target, String service, String op, String... params) {

        WebTarget temp = target.path(service).path(op);

        for (String p : params) {
            temp = temp.path(p);
        }

        return temp.request()
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);
    }

    public static void main(String[] args) {

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        URI uri1 = UriBuilder
			.fromUri(ServiceUrlConfig.hoaDonBaseUrl())
                .build();
        URI uri2 = UriBuilder
			.fromUri(ServiceUrlConfig.khachHangBaseUrl())
                .build();
        URI uri3 = UriBuilder
			.fromUri(ServiceUrlConfig.hangHoaBaseUrl())
                .build();

        WebTarget target1 = client.target(uri1);
        WebTarget target2 = client.target(uri2);
        WebTarget target3 = client.target(uri3);

		 // =========================
		 // KHACH HANG SERVICE
		 // =========================
		
        System.out.println("===== TEST KHACH HANG =====");

        System.out.println(callService(target2,"khachhang","add","003","Binh","CanTho"));
        System.out.println(callService(target2,"khachhang","addnewvip","004","Lan","SocTrang","0.2"));

        System.out.println("\nAll:");
        System.out.println(callServiceJSON(target2,"khachhang","all"));

        System.out.println("\nVIP 004:");
        System.out.println(callServiceJSON(target2,"khachhang","getvip","004"));

        System.out.println("\nVIP 003:");
        System.out.println(callServiceJSON(target2,"khachhang","getvip","003"));

        System.out.println(callService(target2,"khachhang","upgradevip","003","0.15"));

        System.out.println("\nAll sau khi upgrade:");
        System.out.println(callServiceJSON(target2,"khachhang","all"));

        System.out.println("\nVIP 003:");
        System.out.println(callServiceJSON(target2,"khachhang","getvip","003"));
		
		
		  // =========================
		  // HANG HOA SERVICE
		  // =========================
		
		  System.out.println("\n===== TEST HANG HOA =====");
		
		  System.out.println("Them hang hoa:");
		  System.out.println(callService(target3,"hanghoa","add","Laptop","Dell"));
		  System.out.println(callService(target3,"hanghoa","add","Mouse","Logitech"));
		  System.out.println(callService(target3,"hanghoa","add","Keyboard","Razer"));
		
		  System.out.println("Danh sach hang hoa:");
		  System.out.println(callServiceJSON(target3,"hanghoa","all"));
		
		  System.out.println("Cap nhat HH1:");
		  System.out.println(callService(target3,"hanghoa","update","HH1","LaptopPro","Dell"));
		
		  System.out.println("Lay hang hoa HH1:");
		  System.out.println(callServiceJSON(target3,"hanghoa","get","HH1"));
		
		
			//=========================
			//HOA DON SERVICE
			//=========================
			
		  System.out.println("\n===== TEST HOA DON =====");

			System.out.println(callService(target1,"hoadon","create","003","NV01")); // HD1
			System.out.println(callService(target1,"hoadon","create","004","NV02")); // HD2 (VIP)
	
			System.out.println("\n-- Them san pham --");
	
			// HD1
			System.out.println("\nHoa don 1:");
			System.out.println(callService(target1,"hoadon","additem","HD1","HH1","1","2000"));
			System.out.println(callService(target1,"hoadon","additem","HD1","HH2","2","10"));
	
			// HD2 (VIP)
			System.out.println("\nHoa don 2:");
			System.out.println(callService(target1,"hoadon","additem","HD2","HH1","1","2000"));
			System.out.println(callService(target1,"hoadon","additem","HD2","HH3","1","500"));
	
			System.out.println("\n-- Chi tiet hoa don --");
			System.out.println(callServiceJSON(target1,"hoadon","detail","HD1"));
			System.out.println(callServiceJSON(target1,"hoadon","detail","HD2"));
	
			System.out.println("\n-- Cap nhat san pham HH1 trong HD1 --");
			System.out.println(callService(target1,"hoadon","updateitem","HD1","HH1","3","1800"));
	
			System.out.println("\n-- Tong tien --");
			System.out.println("HD1: " + callService(target1,"hoadon","tong","HD1"));
			System.out.println("HD2: " + callService(target1,"hoadon","tong","HD2"));
	
			System.out.println("\n-- Tong sau giam (VIP) --");
			System.out.println(callService(target1,"hoadon","tongsaugiam","HD1"));
	
			System.out.println("\n-- Xoa san pham HH2 trong HD1 --");
			System.out.println(callService(target1,"hoadon","removeitem","HD1","HH2"));
	
			System.out.println("\n-- Cap nhat nhan vien --");
			System.out.println(callService(target1,"hoadon","update","HD1","NV03"));
 
			System.out.println("\n-- Danh sach hoa don --");
			System.out.println(callServiceJSON(target1,"hoadon","all"));
	
			System.out.println("\n-- Xoa hoa don --");
			System.out.println(callService(target1,"hoadon","delete","HD1"));
	
			System.out.println("\n-- Danh sach sau khi xoa --");
			System.out.println(callServiceJSON(target1,"hoadon","all"));
    }
}