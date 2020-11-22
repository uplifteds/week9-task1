package org.uplifteds.DML_crud;

import org.uplifteds.CDPDBLauncher;
import org.uplifteds.entity.Student;

import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CRUDMethods {
    public static void doDeleteValuesInAllTables(Statement stmt) throws SQLException {
        for (int i = 0; i< CDPDBLauncher.listOfTables.size(); i++) {
            String tableToClear = "DELETE FROM " + CDPDBLauncher.listOfTables.get(i);
            stmt.executeUpdate(tableToClear);
        }
        System.out.println("Previous values were deleted in all tables ");
    }

    public static void doUpdateFieldOfStudentById(Connection conn, String fieldName ,Object fieldValue, int id) throws SQLException {
        String sqlUpdateQuery = "UPDATE students SET " + fieldName + "=? WHERE id=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sqlUpdateQuery);
            preparedStatement.setObject(1, fieldValue);
            preparedStatement.setInt(2, id);

            int row = preparedStatement.executeUpdate();

            System.out.println("Student's fieldValue was updated. Trigger would be fired.");

        } finally {
            preparedStatement.close();
        }
    }

    public static Double calcAvgMarkByStudentId(Statement stmt, int studentId) throws SQLException {
        String sql = "SELECT avg(mark) " +
                "FROM examresults " +
                "where student_id = " + studentId + ";";
        ResultSet resultSet = stmt.executeQuery(sql);

        Double avg = getAvgMarkInResultSet(resultSet);
        return avg;
    }

    public static Double calcAvgMarkBySubjectName(Statement stmt, String subjectName) throws SQLException {
        String sql = "select avg(examresults.mark) \n" +
                "from examresults\n" +
                "inner join subjects on examresults.subject_id = subjects.id\n" +
                "where subjects.subject_name = '" + subjectName + "';";
        ResultSet resultSet = stmt.executeQuery(sql);

        Double avg = getAvgMarkInResultSet(resultSet);
        return avg;
    }

    private static Double getAvgMarkInResultSet(ResultSet resultSet) throws SQLException {
        Double columnValue = null;
        while (resultSet.next()) {
            columnValue = resultSet.getDouble(1);
        }
        resultSet.close();
        if (columnValue > 0){
            System.out.println("Average mark for a Student: " + columnValue);
        }
        return columnValue;
    }

    public static void findStudentInRedZoneAtLeastTwoMarksBelowThreshold(Statement stmt, int thresholdmark) throws SQLException {
        String tempTableName = "studentswithmarkcount";
        String countThresholdMarkColumnName = "NumberOfMarksBelowThreshold";
        String sql = "drop table if exists " + tempTableName + ";\n" +
                "create table " + tempTableName + "\n" +
                "as \n" +
                "select count(case when mark <=" + thresholdmark + " then 1 else null end) as " + countThresholdMarkColumnName + ",\n" +
                "students.id, students.name, students.surname, students.dob,students.phone,students.skill,students.created,students.updated\n" +
                "from examresults\n" +
                "inner join students on examresults.student_id = students.id\n" +
                "where mark <= " + thresholdmark + "\n" +
                "group by students.id,students.name, students.surname" +
                ";";
        stmt.executeUpdate(sql); // drop,create require executeUpdate or execute

        String sql2 = "select id,name,surname,dob,phone,skill,created,updated \n" +
                "from " + tempTableName + "\n" +
                "where " + countThresholdMarkColumnName + " >=2;";
        ResultSet resultSet2 = stmt.executeQuery(sql2); // select require executeQuery
        findListOfStudentsInResultSet(resultSet2);
    }

    private static void findListOfStudentsInResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        System.out.println("\n# Searching Students in REDZONE... ");
        List<Student> studentList = new CopyOnWriteArrayList<>();
        List<String> columnList = new CopyOnWriteArrayList<>();
        while (resultSet.next()) {
            Student stud = new Student();
            stud.setId(resultSet.getInt(Student.idFieldName));
            stud.setName(resultSet.getString(Student.nameFieldName));
            stud.setSurname(resultSet.getString(Student.surnameFieldName));
            stud.setDob(resultSet.getDate(Student.dobFieldName));
            stud.setPhone(resultSet.getLong(Student.phoneFieldName));
            stud.setSkill(resultSet.getString(Student.skillFieldName));
            stud.setCreated(resultSet.getTimestamp(Student.createdFieldName));
            stud.setUpdated(resultSet.getTimestamp(Student.updatedFieldName));
            studentList.add(stud);
        }
        resultSet.close();
        if (studentList.size() > 0) {
            for (Student tempStud : studentList) {
                System.out.println(tempStud.toString());
            }
        }
    }

}
