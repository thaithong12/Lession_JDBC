package com.company;

import com.company.utils.ConnectionUtils;
import com.mysql.cj.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        //getAllStudent ();
        insertStudent(6, "", 9.0, 1);
    }

    /*
        Get All Data Student
    */
    public static void getAllStudent() throws SQLException {
        Connection conn = ConnectionUtils.getMySQLConnection();

        String sql1 = "SELECT * FROM student";

        Statement stm = conn.createStatement();

        ResultSet rs = stm.executeQuery(sql1);

        List<Student> data = new ArrayList<>();
        Student student;
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String name = rs.getString(2);
            Double dht = rs.getDouble(3);

            Integer classId = rs.getInt(4);
            student = new Student(id, name, dht, classId);

            data.add(student);
        }

        // print data
        data.forEach(i -> System.out.println(i));

        // close connection
        conn.close();
    }

    static String createSelectSQL(String tableName) {
        return "SELECT * FROM " + tableName;
    }

    static void throwException(String msg) throws Exception {
        throw new Exception(msg);
    }

    static void updateStudent(Integer id, String name, Double dht, Integer classId) throws Exception {
        Connection conn = ConnectionUtils.getMySQLConnection();
        validateData(id, name, dht, classId, conn, 0);
        try {
            String sql = "UPDATE student SET student_name= ? , dht = ?, student_class_id = ?  WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setDouble(2, dht);
            statement.setInt(3, classId);
            statement.setInt(4, id);
            int rowEffected = statement.executeUpdate();

            if (rowEffected <= 0) {
                System.out.println("Loi update");
            } else {
                System.out.println("Update thanh cong");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            conn.close();
        }
    }

    static void validateData(Integer id, String name, Double dht, Integer classId, Connection conn, int choose) throws Exception {
        // VALIDATE FOR CLASS ID
        String sql1 = createSelectSQL("studentclass");
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql1);
        List<Integer> data = new ArrayList<>();
        while (rs.next()) {
            Integer receivedId = rs.getInt("id");
            data.add(receivedId);
        }
        var obj = data.stream()
                .filter(item -> item.equals(classId))
                .findFirst()
                .orElse(null);


        if (obj == null) {
            throwException("Class Id Exist");
        }

        String sql3 = createSelectSQL("student");
        stm = conn.createStatement();
        rs = stm.executeQuery(sql3);
        var dataStudent = new ArrayList<Integer>();
        while (rs.next()) {
            Integer receivedId = rs.getInt("id");
            dataStudent.add(receivedId);
        }
        obj = dataStudent.stream()
                .filter(item -> item.equals(id))
                .findFirst()
                .orElse(null);

        // INSERT MODE
        if (choose == 1) {
            if (obj != null) {
                throwException("Student Id Exist");
            }
            // UPDATE MODE
        } else {
            if (obj == null) {
                throwException("Student Id Not Exist");
            }
        }

        // VALIDATE NAME != BLANK ("")
        if (StringUtils.isNullOrEmpty(name)) {
            throwException("Name is blank");
        }

        // VALIDATE DHT 0 <= DHT >= 10
        if (dht < 0 || dht > 10) {
            throwException("DHT is not valid");
        }
    }

    public static void insertStudent(Integer id, String name, Double dht, Integer classId) throws SQLException {
        Connection conn = ConnectionUtils.getMySQLConnection();
        try {

            validateData(id, name, dht, classId, conn, 1);

            String sql2 = "INSERT into student VALUES (? , ? , ? , ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql2);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setDouble(3, dht);
            preparedStatement.setInt(4, classId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows <= 0) {
                System.out.println("Loi insert");
            } else {
                System.out.println("Insert thanh cong");
            }

            // close connection
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            conn.close();
        }

    }
}
