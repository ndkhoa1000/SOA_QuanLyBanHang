<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.HoaDon, model.KhachHang" %>
<%@ page import="servlet.HoaDonServlet" %>
<%@ page import="javax.ws.rs.client.*, javax.ws.rs.core.*" %>
<%@ page import="org.glassfish.jersey.client.ClientConfig" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Invoice Management</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<div class="container">

<h1>Invoice Management</h1>

<div class="nav-links">
    <a href="index">Back to Home</a>
</div>

<% String message = (String)request.getAttribute("message");
   if(message != null && !message.isEmpty()){ %>
    <p style="color: blue;"><strong>Notification:</strong> <%= message %></p>
<% } %>

<table border="1">
    <tr>
        <th>Invoice ID</th>
        <th>Employee Name</th>
        <th>Customer (ID Card)</th>
        <th>Action</th>
    </tr>
    <% HoaDon[] dsHoaDon = (HoaDon[])request.getAttribute("dsHoaDon");
       if(dsHoaDon != null) {
           for(HoaDon hd : dsHoaDon) { %>
        <tr>
            <td><%= hd.getMso() %></td>
            <td><%= hd.getNvien() %></td>
            <td><%= hd.getKhachHang() %></td>
            <td>
                <a href="hoadon?op=all">All</a> |
                <a href="hoadon?op=detail&idhd=<%= hd.getMso() %>">Details</a> |
                <a href="hoadon?op=delete&idhd=<%= hd.getMso() %>">Delete</a>
            </td>
        </tr>
    <% } } %>
</table>

<br>

<%
    KhachHang[] dsKhachHang = null;
    try {
        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget target = client.target(UriBuilder.fromUri("http://localhost:8080/QuanLyBanHang2/rest").build());
        dsKhachHang = HoaDonServlet.callServiceObj(target, KhachHang[].class, "khachhang", "all");
    } catch(Exception e) {}
%>

<h3>Create New Invoice</h3>
<form action="hoadon" method="get">
    <div class="form-row">
        <label>Customer:</label> 
        <select name="cccd" required>
            <option value="">-- Choose Customer --</option>
            <% if(dsKhachHang != null) { 
                   for(KhachHang kh : dsKhachHang) { %>
                       <option value="<%= kh.getCccd() %>"><%= kh.getHten() %> (<%= kh.getCccd() %>)</option>
            <%     }
               } %>
        </select>
    </div>
    <div class="form-row">
        <label>Employee Name:</label> <input type="text" name="nvien" required>
    </div>
    <div class="btn-group">
        <button type="submit" name="op" value="create">Create</button>
    </div>
</form>

<br>

<h3>Update Invoice Employee</h3>
<form action="hoadon" method="get">
    <div class="form-row">
        <label>Invoice ID:</label> <input type="text" name="idhd" required>
    </div>
    <div class="form-row">
        <label>New Employee Name:</label> <input type="text" name="nvien" required>
    </div>
    <div class="btn-group">
        <button type="submit" name="op" value="update">Update</button>
    </div>
</form>

</div>
</body>
</html>
