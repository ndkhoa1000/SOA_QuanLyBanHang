package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import model.HangHoa;
import util.ServiceUrlConfig;

@WebServlet("/hanghoa")
public class HangHoaServlet extends HttpServlet {
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
        System.out.println("Calling URL: " + temp.getUri());
        return temp.request()
                .accept(MediaType.APPLICATION_JSON)
                .get(clazz);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

		Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget target = client.target(UriBuilder.fromUri(ServiceUrlConfig.hangHoaBaseUrl()).build());

        String op = request.getParameter("op");
        String message = "";

        if (op != null) {
            if (op.equals("add")) {
                String ten = request.getParameter("ten");
                String nsx = request.getParameter("nsx");
                message = callServiceText(target, "hanghoa", "add", ten, nsx);
            } else if (op.equals("update")) {
                String mso = request.getParameter("mso");
                String ten = request.getParameter("ten");
                String nsx = request.getParameter("nsx");
                message = callServiceText(target, "hanghoa", "update", mso, ten, nsx);
            } else if (op.equals("delete")) {
                String mso = request.getParameter("mso");
                message = callServiceText(target, "hanghoa", "delete", mso);
            }
        }

        HangHoa[] ds = callServiceObj(target, HangHoa[].class, "hanghoa", "all");
        request.setAttribute("dsHangHoa", ds);
        request.setAttribute("message", message);
		request.getRequestDispatcher("/hanghoa.jsp").forward(request, response);
	}
}
