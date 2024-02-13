package servlets;

import models.MySQLConnector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/add-courses")
public class AddCourses extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        out.println("<html>");

        out.println("<head>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: white; box-sizing: border-box; }");
        out.println("h2 { background-color: black; color: deeppink; padding: 10px; margin: 0; width: 100%; box-sizing: border-box; }");
        out.println("table { border-collapse: collapse; width: 100%; margin: 0; padding: 0; }");
        out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
        out.println("th { background-color: lightgrey; color: black; }");
        out.println(".footer { position: fixed; bottom: 0; width: 100%; background-color: black; padding: 10px; text-align: center; box-sizing: border-box; }");
        out.println(".footer ul { list-style-type: none; margin: 0; padding: 0; display: flex; justify-content: space-around; }");
        out.println(".footer li { display: inline; margin-right: 15px; }");
        out.println(".footer a { text-decoration: none; color: deeppink; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h2>Grit Academy</h2>");

        out.println("<table>");
        out.println("<tr><th>Add courses</th></tr>");



        out.println("</table>");

        out.println("<form action=\"/add-courses\" method=\"POST\">");

        out.println("<label for=\"course\">Course: </label>");
        out.println("<input type=\"text\" id=\"courseName\" name=\"name\" required></input>");

        out.println("<label for=\"yhp\">YHP: </label>");
        out.println("<input type=\"int\" id=\"yhp\" name=\"yhp\" required></input>");

        out.println("<label for=\"description\">Description: </label>");
        out.println("<input type=\"text\" id=\"description\" name=\"description\" required></input>");

        out.println("<br></br>");
        out.println("<input type=\"submit\" name=\"add-course\" value=\"add-course\"></input>");
        out.println("</form>");

        out.println("<div class=\"footer\">");
        out.println("<ul>");
        out.println("<li>" + "<a href = http://localhost:8080/home>" + "Home" + "</a>");
        out.println("<li>" + "<a href = http://localhost:8080/register-students>" + "Register students" + "</a>");
        out.println("<li>" + "<a href = http://localhost:8080/show-attendance>" + "Attendance" + "</a>");
        out.println("<li>" + "<a href = http://localhost:8080/show-student-courses>" + "Show student courses" + "</a>");
        out.println("<li>" + "<a href = http://localhost:8080/add-courses>" + "Add-courses" + "</a>");
        out.println("</ul>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String address = "";



        if (req.getParameter("add-course") != null) {

            String courseName = req.getParameter("name");
            int yhp = Integer.parseInt(req.getParameter("yhp"));
            String description = req.getParameter("description");


            try {
                MySQLConnector.addCourses(courseName,yhp, description);
                address = "/add-courses";
            } catch (Exception e) {
                throw new RuntimeException(e);

            }
        }
        resp.sendRedirect(address);
    }

}
