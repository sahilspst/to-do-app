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

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Connection conn = JdbcConnection.getDatabaseConnection("root","root","jdbc:mysql://localhost:3306/todo_app");
        try {
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users where username = ? and password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                request.setAttribute("error_message", "Invalid username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                // Fetch user details from the database
                PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM TASKS WHERE username = ?");
                ps1.setString(1, username);
                ResultSet rs1 = ps1.executeQuery();
                // Store user details in the session
                ArrayList<Task> tasks = new ArrayList<>();
                while (rs1.next()) {
                    String title = rs1.getString("title");
                    Date targetDate = rs1.getDate("target_date");
                    String status = rs1.getString("todo_status");
                    tasks.add(new Task(title,targetDate,status));
                }
                session.setAttribute("taskList",tasks);
                request.getRequestDispatcher("profile.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}