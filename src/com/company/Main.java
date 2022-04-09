package com.company;

import com.company.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        Connection connection = ConnectionUtils.getMySQLConnection();
//
//        Statement statement = connection.createStatement();
//
//        String sql = "SELECT * FROM account_roles";
//
//        ResultSet rs = statement.executeQuery(sql);
//
//        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
//            Long empId = rs.getLong(1);
//            Date empNo = rs.getDate(2);
//            System.out.println("--------------------");
//            System.out.println("Id:" + empId);
//            System.out.println("CreatedDate:" + empNo);
//        }
//        connection.close();
//
//
//
//        String sql2 = "Insert into Salary_Grade (Grade, High_Salary, Low_Salary) "
//                + " values (2, 20000, 10000) ";
//
//
//        int rowCount = statement.executeUpdate(sql);
//
//        System.out.println("Row Count affected = " + rowCount);
//
//        String sql3 = "Select emp.Emp_Id, emp.Emp_No, emp.Emp_Name, emp.Dept_Id from Employee emp "
//                + " where emp.Emp_Name like ? and emp.Dept_Id = ? ";
//
//        PreparedStatement pstm = connection.prepareStatement(sql);


        getAllStudent ();
        insertStudent(5, "Thai thi B", 9.0, 1);
    }

    /*
        Get All Data Student
    */
    public static void getAllStudent () throws SQLException {
        Connection conn = ConnectionUtils.getMySQLConnection();

        String sql1 = "SELECT * FROM student";

        Statement stm = conn.createStatement();

        ResultSet rs = stm.executeQuery(sql1);

        List<Student> data = new ArrayList<>();
        Student student;
        while (rs.next()) {
            Integer id  = rs.getInt("id");
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

    public static void insertStudent (Integer id , String name, Double dht, Integer classId) throws SQLException {
        // Validate
        Connection conn = ConnectionUtils.getMySQLConnection();

        String sql1 = "SELECT * FROM studentclass";

        Statement stm = conn.createStatement();

        ResultSet rs = stm.executeQuery(sql1);

        List<Integer> data = new ArrayList<>();
        while (rs.next()) {
            Integer receivedId  = rs.getInt("id");
            data.add(receivedId);
        }


        var obj = data.stream()
                .filter(item -> item.equals(classId))
                .findFirst()
                .orElse(null);

        if (obj == null) {
            System.out.println("Loi validation");
            return;
        }

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
    }
}
