package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import model.KhachHang;
import util.ServiceUrlConfig;

@WebServlet("/khachhang")
public class KhachHangServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public static String callServiceText(WebTarget target, String service, String op, String... params) {
        WebTarget temp = target.path(service).path(op);
        for (String p : params) {
            temp = temp.path(p);
        }
        System.out.println("Calling URL: " + temp.getUri());
        return temp.request()
                .accept(MediaType.TEXT_PLAIN)
                .get(String.class);
    }

    public static <T> T callServiceObj(WebTarget target, Class<T> clazz, String service, String op, String... params) {
        WebTarget temp = target.path(service).path(op);
        for (String p : params) {
            temp = temp.path(p);
        }
        System.out.println("Calling URL: " + temp.getUri());
        return temp.request()
                .accept(MediaType.APPLICATION_JSON)
                .get(clazz);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

		Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget target = client.target(UriBuilder.fromUri(ServiceUrlConfig.khachHangBaseUrl()).build());

        String op = request.getParameter("op");
        String message = "";

        if (op != null) {
            if (op.equals("add")) {
                String cccd = request.getParameter("cccd");
                String ten = request.getParameter("ten");
                String dc = request.getParameter("dc");
                message = callServiceText(target, "khachhang", "add", cccd, ten, dc);
            } else if (op.equals("addvip")) {
                String cccd = request.getParameter("cccd");
                String ten = request.getParameter("ten");
                String dc = request.getParameter("dc");
                String giam = request.getParameter("giam");
                message = callServiceText(target, "khachhang", "addnewvip", cccd, ten, dc, giam);
            } else if (op.equals("update")) {
                String cccd = request.getParameter("cccd");
                String ten = request.getParameter("ten");
                String dc = request.getParameter("dc");
                message = callServiceText(target, "khachhang", "update", cccd, ten, dc);
            } else if (op.equals("upgrade")) {
                String cccd = request.getParameter("cccd");
                String giam = request.getParameter("giam");
                message = callServiceText(target, "khachhang", "upgradevip", cccd, giam);
            } else if (op.equals("delete")) {
                String cccd = request.getParameter("cccd");
                message = callServiceText(target, "khachhang", "delete", cccd);
            }
        }

        KhachHang[] ds = callServiceObj(target, KhachHang[].class, "khachhang", "all");
        request.setAttribute("dsKhachHang", ds);
        request.setAttribute("message", message);
		request.getRequestDispatcher("/khachhang.jsp").forward(request, response);
	}
}