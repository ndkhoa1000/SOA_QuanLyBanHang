<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map" %>
<%@ page import="model.HangHoa" %>
<%@ page import="servlet.HoaDonServlet" %>
<%@ page import="javax.ws.rs.client.*, javax.ws.rs.core.*" %>
<%@ page import="org.glassfish.jersey.client.ClientConfig" %>
<% 
    String idhd = (String)request.getAttribute("idhd"); 
    String tong = (String)request.getAttribute("tong"); 
    String tongSauGiam = (String)request.getAttribute("tongSauGiam"); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Invoice Details - <%= idhd != null ? idhd : "" %></title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<div class="container">

<h1>Invoice Details <%= idhd != null ? idhd : "" %></h1>

<div class="nav-links">
    <a href="hoadon?op=all">Back to Invoice List</a> |
    <a href="index">Back to Home</a>
</div>

<% String message = (String)request.getAttribute("message");
   if(message != null && !message.isEmpty()){ %>
    <p style="color: blue;"><strong>Notification:</strong> <%= message %></p>
<% } %>

<div style="text-align: center;">
    <h3>Total Amount: <%= tong != null ? tong : "" %></h3>
    <h3>Total Amount (after VIP discount): <%= tongSauGiam != null ? tongSauGiam : "" %></h3>
</div>

<table border="1">
    <tr>
        <th>Product ID</th>
        <th>Product Name</th>
        <th>Manufacturer</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Action</th>
    </tr>
    <% List<?> chiTietList = (List<?>)request.getAttribute("chiTietList");
       if(chiTietList != null) {
           for(Object obj : chiTietList) { 
               if(obj instanceof Map) {
                   Map<?, ?> ct = (Map<?, ?>) obj;
    %>
        <tr>
            <td><%= ct.get("mso") %></td>
            <td><%= ct.get("ten") %></td>
            <td><%= ct.get("nsxuat") %></td>
            <td><%= ct.get("sluong") %></td>
            <td><%= ct.get("dgia") %></td>
            <td>
                <a href="hoadon?op=removeitem&idhd=<%= idhd %>&idhh=<%= ct.get("mso") %>">Remove</a>
            </td>
        </tr>
    <%         } 
           } 
       } 
    %>
</table>

<br>

<%
    HangHoa[] dsHangHoa = null;
    try {
        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget target = client.target(UriBuilder.fromUri("http://localhost:8080/QuanLyBanHang3/rest").build());
        dsHangHoa = HoaDonServlet.callServiceObj(target, HangHoa[].class, "hanghoa", "all");
    } catch(Exception e) {}
%>

<h3>Add / Update Product in Invoice</h3>
<form action="hoadon" method="get">
    <input type="hidden" name="idhd" value="<%= idhd != null ? idhd : "" %>">
    <div class="form-row">
        <label>Product:</label> 
        <select name="idhh" required>
            <option value="">-- Choose Product --</option>
            <% if(dsHangHoa != null) { 
                   for(HangHoa hh : dsHangHoa) { %>
                       <option value="<%= hh.getMso() %>"><%= hh.getTen() %> (<%= hh.getMso() %>)</option>
            <%     }
               } %>
        </select>
    </div>
    <div class="form-row">
        <label>Quantity:</label> <input type="number" name="sl" required>
    </div>
    <div class="form-row">
        <label>Price:</label> <input type="number" step="0.01" name="gia" required>
    </div>
    <div class="btn-group">
        <button type="submit" name="op" value="additem">Add</button>
        <button type="submit" name="op" value="updateitem">Update</button>
    </div>
</form>

</div>
</body>
</html>
