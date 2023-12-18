<%@page import="com.example.demo.entity.Flower"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Action Button in Bootstrap Table</title>
<!-- Link thư viện CSS của Bootstrap -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
</head>

<body>


	<div class="container mt-5">
		<a href="/index" style="text-decoration-line: none; color: black;">
            <h2>Search with name Plant</h2>
        </a>
		<!-- Trường tìm kiếm -->
		<form action="/search" method="GET">
			<div class="input-group mb-3">
				<input type="text" class="form-control" id="searchInput" name="name"
					placeholder="Search...">
				<div class="input-group-append">
					<button class="btn btn-primary" type="submit" id="searchButton">Search</button>
				</div>
			</div>
		</form>
		<br>
		<br> <a href="/addnew"
			class="btn btn-primary col-lg-12">New Plant</a> <br>
		<br>
		<table class="table">
			<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Min</th>
					<th>Max</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
			<% 
				List<Flower> list = (List<Flower>) request.getAttribute("list");
				if (list != null && !list.isEmpty()) {
				    for (Flower item : list) {
			%>
					<tr>
						<td><%= item.getId() %></td>
						<td><%= item.getType() %></td>
						<td><%= item.getMin() %></td>
						<td><%= item.getMax() %></td>
						<td>
							<a href="/form?type=<%= item.getType() %>" class="btn btn-primary">Edit</a>
							<a href="/delete?id=<%= item.getId() %>" class="btn btn-danger">Delete</a>
						</td>
					</tr>
			<%}} %>
				<!-- Thêm dữ liệu của bạn vào đây -->
			</tbody>
		</table>
	</div>

	<!-- Link thư viện JavaScript của Bootstrap và jQuery -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>