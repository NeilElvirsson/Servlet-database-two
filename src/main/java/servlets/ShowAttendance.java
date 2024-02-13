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

@WebServlet(urlPatterns = "/show-attendance")
public class ShowAttendance extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        out.println("<html>");

        out.println("<head>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 0; margin-bottom: 100 px; position: relative; padding: 0; background-color: white; box-sizing: border-box; }");
        out.println("h2 { background-color: black; color: deeppink; padding: 10px; margin: 0; width: 100%; box-sizing: border-box; }");
        out.println("table { border-collapse: collapse; width: 100%; margin: 0; padding: 0; z-index: 1;}");
        out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
        out.println("th { background-color: lightgrey; color: black; }");
        out.println(".footer { position: relative; bottom: 0; width: 100%; background-color: black; padding: 10px; margin-top: 100px; text-align: center; box-sizing: border-box; z-index: 2; }");
        out.println(".footer ul { list-style-type: none; margin: 0; padding: 0; display: flex; justify-content: space-around; }");
        out.println(".footer li { display: inline; margin-right: 15px; }");
        out.println(".footer a { text-decoration: none; color: deeppink; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h2>Grit Academy</h2>");
        List<String> studentsNames =  MySQLConnector.getStudentsName();
        List<String> courses = MySQLConnector.getCourses();
        List<String> attendance = MySQLConnector.getAttendance();

        out.println("<table>");
        out.println("<tr><th>Students</th></tr>");

        for(String studentName : studentsNames) {
            out.println("<tr><td>" + studentName + "</td></tr>");
            System.out.println(studentName);
        }
        System.out.println(studentsNames);

        out.println("</table>");

        out.println("<table>");
        out.println("<tr><th>Courses</th></tr>");

        for(String course : courses) {
            out.println("<tr><td>" + course + "</td></tr>");
            System.out.println(course);
        }
        System.out.println(courses);

        out.println("</table>");

        out.println("<table>");
        out.println("<tr><th>Students Courses</th></tr>");

        for(String attend : attendance) {
            out.println("<tr><td>" + attend + "</td></tr>");
            System.out.println(attend);
        }
        System.out.println(attendance);

        out.println("</table>");

        out.println("<form action=\"/show-attendance\" method=\"POST\">");
        out.println("<label for=\"fname\">First name: </label>");
        out.println("<input type=\"text\" id=\"fname\" name=\"Fname\" required></input>");
        out.println("<label for=\"lname\">Last name: </label>");
        out.println("<input type=\"text\" id=\"lname\" name=\"Lname\" required></input>");
        out.println("<label for=\"course\">Course: </label>");
        out.println("<input type=\"text\" id=\"course\" name=\"course\" required></input>");

        out.println("<br></br>");
        out.println("<input type=\"submit\" name=\"addStudentToCourse\" value=\"addStudentToCourse\"></input>");
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

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String address = "";

        if(req.getParameter("addStudentToCourse") != null) {

            String fname = req.getParameter("Fname");
            String lname = req.getParameter("Lname");
            String course = req.getParameter("course");

            try {
                MySQLConnector.addStudentToCourse(fname, lname, course);
                System.out.println("doPost insert!");
            } catch (Exception e) {
                throw new RuntimeException(e);

            }
            address = "/show-attendance";
        }
        resp.sendRedirect(address);
    }
}
