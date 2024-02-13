package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnector {

    //Method for getting student names
    public static List<String> getStudentsName() {
        List<String> studentsName = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//Connector for database

            Connection con =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "root", "");
            Statement stmt = con.createStatement();
//SQL question, execute query

            ResultSet rs = stmt.executeQuery("SELECT Fname, Lname FROM students");
            while (rs.next()) {
                String fname = rs.getString("Fname");
                String lname = rs.getString("Lname");

                studentsName.add(fname + " " + lname);

            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        //Returns our list of strings
        return studentsName;
    }

    //Method for getting all courses from database
    public static List<String> getCourses() {
        List<String> courses = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//PORT and DbName should be changed

            Connection con =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "root", "");
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT name, yhp, description FROM courses");

            while (rs.next()) {
                String course = rs.getString("name");
                int yhp = rs.getInt("yhp");
                String description = rs.getString("description");

                courses.add(course + " (" + yhp + ") " + ": " + description);
            }


            con.close();
        } catch (Exception e) {
            System.out.println(e);

        }
        return courses;

    }

    public static List<String> getStudentCourses(String fname, String lname) {

        List<String> studentCourses = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "useruser", "");

            String query = "SELECT s.Fname, s.Lname, c.name FROM attendance a JOIN students s ON s.id = a.student_id JOIN courses c ON c.id = a.course_id WHERE s.Fname = ? AND s.Lname = ?";

            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, fname);
                pstmt.setString(2, lname);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String studenName = rs.getString("Fname") + " " + rs.getString("Lname");
                        String courseName = rs.getString("name");
                        studentCourses.add(studenName + ": " + courseName);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return studentCourses;
    }

    public static void insertStudent(String fname, String lname, String town, String hobby) {
        System.out.println(fname + lname);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "root", "");

            String query = "Insert INTO students (Fname, Lname, town, hobby) VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, fname);
                pstmt.setString(2, lname);
                pstmt.setString(3, town);
                pstmt.setString(4, hobby);

                System.out.println(fname + lname);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Insert successful");
                } else {
                    System.out.println("Insert failed");
                }
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException("Error inserting student: " + e.getMessage(), e);

        }
    }

    public static void addCourses(String course, int yhp, String description) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "root", "");

            String query = "Insert INTO courses (name, yhp, description) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, course);
                pstmt.setInt(2, yhp);
                pstmt.setString(3, description);

//If statement to check if any rows in courses has been affected, saves execute update in rowsAffected
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Insert successful");
                } else {
                    System.out.println("Insert failed");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException("Error adding course: " + e.getMessage(), e);
        }
    }

    public static List<String> getAttendance() {
        List<String> attendance = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//PORT and DbName should be changed

            Connection con =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "useruser", "");
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT students.Fname, students.Lname, GROUP_CONCAT(courses.name) AS attendance FROM students INNER JOIN attendance ON students.id = attendance.student_id INNER JOIN courses ON attendance.course_id = courses.id GROUP BY students.id, students.Fname, students.Lname");

            while (rs.next()) {
                String courses = rs.getString("attendance");
                String fname = rs.getString("Fname");
                String lname = rs.getString("Lname");

                attendance.add(fname + " " + lname + ": " + courses);
            }


            con.close();
        } catch (Exception e) {
            System.out.println(e);

        }
        return attendance;

    }

    public static void addStudentToCourse (String fname, String lname, String courseName) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "root", "");
//INSERT INTO attendance (student_id, course_id) SELECT students.id, courses.id FROM students JOIN courses ON students.Fname = 'John' AND students.Lname = 'McClane' AND courses.name = 'Painting101';


                String query = "INSERT INTO attendance (student_id, course_id) SELECT students.id, courses.id FROM students JOIN courses ON students.Fname = ? AND students.Lname = ? AND courses.name = ?";

                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setString(1, fname);
                    pstmt.setString(2, lname);
                    pstmt.setString(3, courseName);


                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Insert successful");
                    } else {
                        System.out.println("Insert failed");
                    }
                }

                System.out.println("Student is already in course");

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException("Error adding student to course" + e.getMessage(), e);

        }
    }


}
