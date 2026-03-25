<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.HangHoa" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product Management</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<div class="container">

<h1>Product Management</h1>

<div class="nav-links">
    <a href="index">Back to Home</a>
</div>

<% String message = (String)request.getAttribute("message");
   if(message != null && !message.isEmpty()){ %>
    <p style="color: blue;"><strong>Notification:</strong> <%= message %></p>
<% } %>

<table border="1">
    <tr>
        <th>Product ID</th>
        <th>Product Name</th>
        <th>Manufacturer</th>
        <th>Action</th>
    </tr>
    <% HangHoa[] dsHangHoa = (HangHoa[])request.getAttribute("dsHangHoa");
       if(dsHangHoa != null) {
           for(HangHoa hh : dsHangHoa) { %>
        <tr>
            <td><%= hh.getMso() %></td>
            <td><%= hh.getTen() %></td>
            <td><%= hh.getNsxuat() %></td>
            <td>
                <a href="hanghoa?op=delete&mso=<%= hh.getMso() %>">Delete</a>
            </td>
        </tr>
    <% } } %>
</table>

<br>

<h3>Add / Update Product</h3>
<form action="hanghoa" method="get">
    <div class="form-row">
        <label>Product ID (for update):</label> <input type="text" name="mso">
    </div>
    <div class="form-row">
        <label>Product Name:</label> <input type="text" name="ten" required>
    </div>
    <div class="form-row">
        <label>Manufacturer:</label> <input type="text" name="nsx" required>
    </div>
    <div class="btn-group">
        <button type="submit" name="op" value="add">Add New</button>
        <button type="submit" name="op" value="update">Update</button>
    </div>
</form>

</div>
</body>
</html>
