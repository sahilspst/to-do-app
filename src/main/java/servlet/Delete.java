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
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@WebServlet("/delete")
public class Delete extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve the task title from the request
        System.out.println("Delete servlet called");
        String taskTitle = req.getParameter("title");
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        Connection conn = JdbcConnection.getDatabaseConnection("root","root","jdbc:mysql://localhost:3306/todo_app");
        try {
            assert conn != null;
            // Prepare a SQL statement to delete the task
            String sql = "DELETE FROM TASKS WHERE title = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, taskTitle);
            ps.executeUpdate();
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
            req.setAttribute("success_message", "Task deleted successfully: " + taskTitle);
            req.getRequestDispatcher("profile.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error_message", "Error deleting task: " + e.getMessage());
            req.getRequestDispatcher("profile.jsp").forward(req, resp);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
