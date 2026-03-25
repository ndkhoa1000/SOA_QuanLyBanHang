<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.KhachHang, model.KhachHangVIP" %>
<%@ page import="servlet.KhachHangServlet" %>
<%@ page import="javax.ws.rs.client.*, javax.ws.rs.core.*" %>
<%@ page import="org.glassfish.jersey.client.ClientConfig" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer Management</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<div class="container">

<h1>Customer Management</h1>

<div class="nav-links">
    <a href="index">Back to Home</a>
</div>

<% String message = (String)request.getAttribute("message");
   if(message != null && !message.isEmpty()){ %>
    <p style="color: blue;"><strong>Notification:</strong> <%= message %></p>
<% } %>

<table border="1">
    <tr>
        <th>ID Card (CCCD)</th>
        <th>Full Name</th>
        <th>Address</th>
        <th>Discount Rate (VIP)</th>
        <th>VIP Creation Date</th>
        <th>Action</th>
    </tr>
    <% 
       KhachHang[] dsKhachHang = (KhachHang[])request.getAttribute("dsKhachHang");
       if(dsKhachHang != null) {
           Client client = ClientBuilder.newClient(new ClientConfig());
           WebTarget target = client.target(UriBuilder.fromUri("http://localhost:8080/QuanLyBanHang2/rest").build());
           
           for(KhachHang kh : dsKhachHang) { 
               String tleGiam = "";
               String ngayTao = "";
               boolean isVip = false;
               
               try {
                   KhachHangVIP vip = KhachHangServlet.callServiceObj(target, KhachHangVIP.class, "khachhang", "getvip", kh.getCccd());
                   if(vip != null) {
                       tleGiam = String.valueOf(vip.getTleGiam());
                       if(vip.getNgayTaoString() != null) ngayTao = vip.getNgayTaoString();
                       isVip = true;
                   }
               } catch (Exception e) {}
    %>
        <tr>
            <td><%= kh.getCccd() %></td>
            <td><%= kh.getHten() %></td>
            <td><%= kh.getDchi() %></td>
            <td><%= tleGiam %></td>
            <td><%= ngayTao %></td>
            <td>
                <a href="khachhang?op=delete&cccd=<%= kh.getCccd() %>">Delete</a>
                <% if(!isVip) { %>
                    | <a href="khachhang?op=upgrade&cccd=<%= kh.getCccd() %>&giam=0.1">Upgrade VIP (10%)</a>
                <% } %>
            </td>
        </tr>
    <%     } 
       } 
    %>
</table>

<br>

<h3>Add / Update Regular Customer</h3>
<form action="khachhang" method="get">
    <div class="form-row">
        <label>ID Card:</label> <input type="text" name="cccd" required>
    </div>
    <div class="form-row">
        <label>Full Name:</label> <input type="text" name="ten" required>
    </div>
    <div class="form-row">
        <label>Address:</label> <input type="text" name="dc" required>
    </div>
    <div class="btn-group">
        <button type="submit" name="op" value="add">Add New</button>
        <button type="submit" name="op" value="update">Update</button>
    </div>
</form>

<br>

<h3>Add New VIP Customer</h3>
<form action="khachhang" method="get">
    <div class="form-row">
        <label>ID Card:</label> <input type="text" name="cccd" required>
    </div>
    <div class="form-row">
        <label>Full Name:</label> <input type="text" name="ten" required>
    </div>
    <div class="form-row">
        <label>Address:</label> <input type="text" name="dc" required>
    </div>
    <div class="form-row">
        <label>Discount Rate:</label> <input type="number" step="0.01" name="giam" required>
    </div>
    <div class="btn-group">
        <button type="submit" name="op" value="addvip">Add VIP</button>
    </div>
</form>

</div>
</body>
</html>
