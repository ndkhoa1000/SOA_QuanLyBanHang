package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import model.HoaDon;
import util.ServiceUrlConfig;

@WebServlet("/hoadon")
public class HoaDonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public static String callServiceText(WebTarget target, String service, String op, String... params) {
        WebTarget temp = target.path(service).path(op);
        for (String p : params) {
            temp = temp.path(p);
        }
        return temp.request()
                .accept(MediaType.TEXT_PLAIN)
                .get(String.class);
    }

    public static <T> T callServiceObj(WebTarget target, Class<T> clazz, String service, String op, String... params) {
        WebTarget temp = target.path(service).path(op);
        for (String p : params) {
            temp = temp.path(p);
        }
        return temp.request()
                .accept(MediaType.APPLICATION_JSON)
                .get(clazz);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

		Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget target = client.target(UriBuilder.fromUri(ServiceUrlConfig.hoaDonBaseUrl()).build());

        String op = request.getParameter("op");
        String message = "";

        if (op != null) {
            if (op.equals("create")) {
                String cccd = request.getParameter("cccd");
                String nvien = request.getParameter("nvien");
                message = callServiceText(target, "hoadon", "create", cccd, nvien);
            } else if (op.equals("update")) {
                String idhd = request.getParameter("idhd");
                String nvien = request.getParameter("nvien");
                message = callServiceText(target, "hoadon", "update", idhd, nvien);
            } else if (op.equals("delete")) {
                String idhd = request.getParameter("idhd");
                message = callServiceText(target, "hoadon", "delete", idhd);
            } else if (op.equals("additem")) {
                String idhd = request.getParameter("idhd");
                String idhh = request.getParameter("idhh");
                String sl = request.getParameter("sl");
                String gia = request.getParameter("gia");
                message = callServiceText(target, "hoadon", "additem", idhd, idhh, sl, gia);
                request.setAttribute("message", message);
                response.sendRedirect("hoadon?op=detail&idhd=" + idhd);
                return;
            } else if (op.equals("updateitem")) {
                String idhd = request.getParameter("idhd");
                String idhh = request.getParameter("idhh");
                String sl = request.getParameter("sl");
                String gia = request.getParameter("gia");
                message = callServiceText(target, "hoadon", "updateitem", idhd, idhh, sl, gia);
                request.setAttribute("message", message);
                response.sendRedirect("hoadon?op=detail&idhd=" + idhd);
                return;
            } else if (op.equals("removeitem")) {
                String idhd = request.getParameter("idhd");
                String idhh = request.getParameter("idhh");
                message = callServiceText(target, "hoadon", "removeitem", idhd, idhh);
                request.setAttribute("message", message);
                response.sendRedirect("hoadon?op=detail&idhd=" + idhd);
                return;
            } else if (op.equals("detail")) {
                String idhd = request.getParameter("idhd");
                List<?> chiTietList = callServiceObj(target, List.class, "hoadon", "detail", idhd);
                String tong = callServiceText(target, "hoadon", "tong", idhd);
                String tongSauGiam = callServiceText(target, "hoadon", "tongsaugiam", idhd);
                
                request.setAttribute("idhd", idhd);
                request.setAttribute("chiTietList", chiTietList);
                request.setAttribute("tong", tong);
                request.setAttribute("tongSauGiam", tongSauGiam);
                request.getRequestDispatcher("/chitiethoadon.jsp").forward(request, response);
                return;
            }
        }

        HoaDon[] ds = callServiceObj(target, HoaDon[].class, "hoadon", "all");
        request.setAttribute("dsHoaDon", ds);
        request.setAttribute("message", message);
		request.getRequestDispatcher("/hoadon.jsp").forward(request, response);
	}
}
