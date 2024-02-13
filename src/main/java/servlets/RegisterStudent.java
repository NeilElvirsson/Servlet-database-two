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


@WebServlet(urlPatterns = "/register-students")
public class RegisterStudent extends HttpServlet {

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
        List<String> studentsName =  MySQLConnector.getStudentsName();

        out.println("<table>");
        out.println("<tr><th>Register students</th></tr>");

        for(String studentName : studentsName) {
            out.println("<tr><td>" + studentName + "</td></tr>");
            System.out.println(studentName);
        }
        System.out.println(studentsName);

        out.println("</table>");

        out.println("<form action=\"/register-students\" method=\"POST\">");
        out.println("<label for=\"fname\">First name: </label>");
        out.println("<input type=\"text\" id=\"fname\" name=\"Fname\" required></input>");
        out.println("<label for=\"lname\">Last name: </label>");
        out.println("<input type=\"text\" id=\"lname\" name=\"Lname\" required></input>");

        out.println("<label for=\"town\">Town: </label>");
        out.println("<input type=\"text\" id=\"town\" name=\"town\"></input>");

        out.println("<label for=\"hobby\">Hobby: </label>");
        out.println("<input type=\"text\" id=\"hobby\" name=\"hobby\"></input>");

        out.println("<br></br>");
        out.println("<input type=\"submit\" name=\"register\" value=\"register\"></input>");
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


        out.println("</body>");
        out.println("</html>");

        //MySQLConnector.getStudentsName();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String address = "";

        if(req.getParameter("register") != null) {

            String fname = req.getParameter("Fname");
            String lname = req.getParameter("Lname");
            String town = req.getParameter("town");
            String hobby = req.getParameter("hobby");

            try {
                MySQLConnector.insertStudent(fname, lname, town, hobby);
                System.out.println(fname + lname);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            address = "/register-students";
        }
        resp.sendRedirect(address);
    }
}
