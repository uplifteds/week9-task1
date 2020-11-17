package org.uplifteds.crud;

import org.uplifteds.CDPDatabaseLauncher;
import org.uplifteds.entity.ExamResult;
import org.uplifteds.entity.Student;
import org.uplifteds.entity.Subject;

import java.sql.*;
import java.util.List;

public class CrudMethods {
//    public static void doCreateTable(Statement stmt) throws SQLException {
//        System.out.println("Creating new Table ...");
//        // task: Employee entity is used from Week6-task4
//        String sql =  "CREATE TABLE " + JabaORM.TABLE_NAME +
//                "(" + Employee.idFieldName  + " INTEGER not NULL, " +
//                 Employee.nameFieldName + " VARCHAR(50), " +
//                 Employee.salaryFieldName + " INTEGER, " +
//                " PRIMARY KEY (" + Employee.idFieldName + "))";
//        stmt.executeUpdate(sql);
//    }

    public static void doInsertListOfStudents(Connection conn, String table_name, List<Student> listOfStudents) throws SQLException {
        String sqlQuery = "insert into " + table_name
                + "(" + Student.idFieldName + "," + Student.nameFieldName + "," + Student.surnameFieldName + ","
                + Student.dobFieldName + "," + Student.phoneFieldName + "," + Student.skillFieldName + ","
                + Student.createdFieldName + "," + Student.updatedFieldName
                + ") values (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement prepStmt = null;
        try{
            prepStmt = conn.prepareStatement(sqlQuery);

            for(Student person : listOfStudents) {
                prepStmt.setInt(1, person.getId());
                prepStmt.setString(2, person.getName());
                prepStmt.setString(3, person.getSurname());
                prepStmt.setDate(4, person.getDob());
                prepStmt.setLong(5, person.getPhone());
                prepStmt.setString(6, person.getSkill());
                prepStmt.setTimestamp(7, person.getCreated());
                prepStmt.setTimestamp(8, person.getUpdated());
                prepStmt.addBatch(); //BATCH 'INSERT INTO'
            }
            System.out.println("Writing Students to its Table ...");

            int[] affectedRecords = prepStmt.executeBatch(); // required for BATCH 'INSERT INTO'

        }finally {
                prepStmt.close();
        }
    }

    public static void doInsertListOfSubjects(Connection conn, String table_name, List<Subject> listOfSubj) throws SQLException {
        String sqlQuery = "insert into " + table_name
                + "(" + Subject.idFieldName + "," + Subject.subjectFieldName + "," + Subject.tutorFieldName
                + ") values (?, ?, ?)";

        PreparedStatement prepStmt = null;
        try{
            prepStmt = conn.prepareStatement(sqlQuery);

            for(Subject subj : listOfSubj) {
                prepStmt.setInt(1, subj.getId());
                prepStmt.setString(2, subj.getSubject_name());
                prepStmt.setString(3, subj.getTutor());
                prepStmt.addBatch(); //BATCH 'INSERT INTO'
            }
            System.out.println("Writing Subjects to its Table ...");

            int[] affectedRecords = prepStmt.executeBatch(); // required for BATCH 'INSERT INTO'

        }finally {
            prepStmt.close();
        }
    }

    public static void doInsertListOfExamResults(Connection conn, String table_name, List<ExamResult> listOfExamRes) throws SQLException {
        String sqlQuery = "insert into " + table_name
                + "(" + ExamResult.idFieldName + "," + ExamResult.student_idFieldName + ","
                + ExamResult.subject_idFieldName + "," + ExamResult.markFieldName
                + ") values (?, ?, ?, ?)";

        PreparedStatement prepStmt = null;
        try{
            prepStmt = conn.prepareStatement(sqlQuery);

            for(ExamResult examResTemp : listOfExamRes) {
                prepStmt.setInt(1, examResTemp.getId());
                prepStmt.setInt(2, examResTemp.getStudent_id());
                prepStmt.setInt(3, examResTemp.getSubject_id());
                prepStmt.setInt(4, examResTemp.getMark());
                prepStmt.addBatch(); //BATCH 'INSERT INTO'
            }
            System.out.println("Writing Examresults to its Table ...\n");

            int[] affectedRecords = prepStmt.executeBatch(); // required for BATCH 'INSERT INTO'

        }finally {
            prepStmt.close();
        }
    }

    public static void doReadEntriesFromTable(Statement stmt, String table_name) throws SQLException {
        String sql = "SELECT * FROM " + table_name;
        ResultSet resultSet = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        System.out.println("# Entries were read from table: " + table_name);
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println();
        }
        resultSet.close();
        System.out.println();
    }

    public static void doDropLinkedTableIfExists(Statement stmt, String table_name) throws SQLException {
//        for (int i = 0; i< CDPDatabaseLauncher.listOfTables.size(); i++){
        String delTable = "DROP TABLE IF EXISTS " + table_name;
        stmt.executeUpdate(delTable);
//        }
        System.out.println("Existing Table was DROPPED: " + table_name);
    }

    public static void doDeleteValuesInAllTables(Statement stmt) throws SQLException {
        for (int i = 0; i< CDPDatabaseLauncher.listOfTables.size(); i++) {
            String tableToClear = "DELETE FROM " + CDPDatabaseLauncher.listOfTables.get(i);
            stmt.executeUpdate(tableToClear);
        }
        System.out.println("Previous values were deleted in all tables ");
    }

//    public static int getSalaryFromTableById(Statement stmt, int id) throws SQLException {
//        ResultSet rs = stmt
//                .executeQuery("select * from " + JabaORM.TABLE_NAME + " where id='" + id + "'");
//        int salaryPerYearGrossInUSD = 0;
//        while (rs.next()) {
//            salaryPerYearGrossInUSD = rs.getInt(Student.salaryFieldName);
//        }
//        rs.close();
//        stmt.close();
//        return (salaryPerYearGrossInUSD);
//    }
}
