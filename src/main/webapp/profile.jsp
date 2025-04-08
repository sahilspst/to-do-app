<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<div class="color-strip">
    <span class="app-title">To-Do App</span>
</div>
<div class="profile-container">
    <h2>Welcome, <%= session.getAttribute("username") %></h2>
    <button id="addTaskBtn" style="margin-bottom: 20px;">Add Task</button>
    <table>
        <thead>
        <tr>
            <th>Title</th>
            <th>Target Date</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%@ page import="java.util.*" %>
        <%@ page import="model.Task" %>
        <%
        List<Task> taskList = (List<Task>) session.getAttribute("taskList");
            if (taskList != null) {
            for (Task task : taskList) {
            %>
            <tr>
                <td><%= task.getTitle() %></td>
                <td><%= task.getTargetDate() %></td>
                <td><%= task.getStatus() %></td>
                <td>
                    <button class="editBtn" data-title="<%= task.getTitle() %>" data-date="<%= task.getTargetDate() %>" data-status="<%= task.getStatus() %>">Edit</button>
                    <button class="deleteBtn" data-title="<%= task.getTitle() %>">Delete</button>
                </td>
            </tr>
            <%
            }
            }
            %>
        </tbody>
    </table>
</div>
<div class="color-strip">
    <div style="position: fixed; bottom: 10px; right: 10px;">
        <form action="logout" method="post" style="margin: 0;">
            <button type="submit" class="logout-btn">Logout</button>
        </form>
    </div>
</div>

<!-- Modal Window -->
<div id="taskModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2>Add/Edit Task</h2>
        <form id="taskForm" action="update" method="post">
            <div class="form-group">
                <label for="taskTitle">Title:</label>
                <input type="text" id="taskTitle" name="taskTitle" required readonly>
            </div>
            <div class="form-group">
                <label for="taskDate">Target Date:</label>
                <input type="date" id="taskDate" name="taskDate" required>
            </div>
            <div class="form-group">
                <label for="taskStatus">Status:</label>
                <input type="text" id="taskStatus" name="taskStatus" required>
            </div>
            <button type="submit" >Save</button>
        </form>
    </div>
</div>

<script src="js/profile.js"></script>
</body>
</html>