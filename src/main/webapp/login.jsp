<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div class="color-strip">
    <span class="app-title">To-Do App</span>
</div>
<div class="login-container">
    <h2>Login</h2>
    <% if (request.getAttribute("success_message") != null) { %>
    <h2 style="color: green; font-size: 18px;"><%= request.getAttribute("success_message") %></h2>
    <% } %>
    <% if (request.getAttribute("error_message") != null) { %>
    <h2 style="color: red; font-size: 18px;"><%= request.getAttribute("error_message") %></h2>
    <% } %>
    <form action="login" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit">Login</button>
    </form>
</div>
<div class="color-strip"></div>
</body>
</html>