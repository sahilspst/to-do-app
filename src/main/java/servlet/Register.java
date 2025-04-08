package servlet;

import database.JdbcConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class Register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Connection conn = JdbcConnection.getDatabaseConnection("root","root","jdbc:mysql://localhost:3306/todo_app");
        try {
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);
            int count = ps.executeUpdate();
            if (count == 0) {
                request.setAttribute("error_message", "User registration failed: " + username);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else if (count >= 1) {
                request.setAttribute("success_message", "User registered successfully: " + username);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}