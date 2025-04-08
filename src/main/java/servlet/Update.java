package servlet;

import database.JdbcConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Task;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/update")
public class Update extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String title = request.getParameter("taskTitle");
        String targetDate = request.getParameter("taskDate");
        String status = request.getParameter("taskStatus");
        Connection conn = JdbcConnection.getDatabaseConnection("root","root","jdbc:mysql://localhost:3306/todo_app");
        try {
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO TASKS (username, title, target_date, todo_status) VALUES (?, ?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE target_date = VALUES(target_date), todo_status = VALUES(todo_status)");
            ps.setString(1, username);
            ps.setString(2, title);
            ps.setDate(3, Date.valueOf(targetDate));
            ps.setString(4, status);
            ps.executeUpdate();
            // Redirect to the profile page after updating
            request.setAttribute("success_message", "Task added/updated successfully: " + title);
            PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM TASKS WHERE username = ?");
            ps1.setString(1, username);
            ResultSet rs1 = ps1.executeQuery();
            // Store user details in the session
            ArrayList<Task> tasks = new ArrayList<>();
            while (rs1.next()) {
                String title1 = rs1.getString("title");
                Date targetDate1 = rs1.getDate("target_date");
                String status1 = rs1.getString("todo_status");
                tasks.add(new Task(title1,targetDate1,status1));
            }
            session.setAttribute("taskList",tasks);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}