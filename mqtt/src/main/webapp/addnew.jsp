<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bootstrap Form</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
    <div class="container mt-5">
        <form action="/add/-1" method="GET">
            <div class="form-group">
                <label for="type">Type:</label>
                <input type="text" class="form-control" id="name" placeholder="Enter type" name="type" required>
            </div>
            <div class="form-group">
                <label for="min">Min:</label>
                <input type="number" class="form-control" id="email" placeholder="Enter min" name="min" required>
            </div>
            <div class="form-group">
                <label for="max">Max:</label>
                <input type="number" class="form-control" id="email" placeholder="Enter max" name="max" required>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>