package org.uplifteds.crud;

import org.uplifteds.CDPDBLauncher;
import org.uplifteds.entity.ExamResult;
import org.uplifteds.entity.Student;
import org.uplifteds.entity.Subject;

import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

//    public static void doDropLinkedTableIfExists(Statement stmt, String table_name) throws SQLException {
////        for (int i = 0; i< CDPDatabaseLauncher.listOfTables.size(); i++){
//        String delTable = "DROP TABLE IF EXISTS " + table_name;
//        stmt.executeUpdate(delTable);
////        }
//        System.out.println("Existing Table was DROPPED: " + table_name);
//    }

    public static void doDeleteValuesInAllTables(Statement stmt) throws SQLException {
        for (int i = 0; i< CDPDBLauncher.listOfTables.size(); i++) {
            String tableToClear = "DELETE FROM " + CDPDBLauncher.listOfTables.get(i);
            stmt.executeUpdate(tableToClear);
        }
        System.out.println("Previous values were deleted in all tables ");
    }

    public static Student getStudentBySurnamePartial(Statement stmt, String surname) throws SQLException {
        // case-insensitive partial search
        ResultSet resultSet = stmt.executeQuery(
                "select * from " + CDPDBLauncher.listOfTables.get(2) +
                    " where LOWER(" + Student.surnameFieldName + ") LIKE LOWER('%" + surname + "%')");

        Student stud = findStudentInResultSet(resultSet);
        return stud;
    }

    public static Student getStudentByNameExact(Statement stmt, String name) throws SQLException {
        // case-sensitive exact search
        ResultSet resultSet = stmt.executeQuery(
                "select * from " + CDPDBLauncher.listOfTables.get(2) +
                    " where " + Student.nameFieldName + " = '" + name + "'");

        Student stud = findStudentInResultSet(resultSet);
        return stud;
    }

    public static Student getStudentByPhonePartial(Statement stmt, String phone) throws SQLException {
        // case-insensitive partial search
        ResultSet resultSet = stmt.executeQuery(
                "select * from " + CDPDBLauncher.listOfTables.get(2) +
                        " where " + Student.phoneFieldName + "::text LIKE '%" + phone + "%'"); // sql cast bigint to string

        Student stud = findStudentInResultSet(resultSet);
        return stud;
    }

    public static Student getStudentWithMarkBySurnamePartial(Statement stmt, String surname) throws SQLException {
        // case-insensitive partial search
        ResultSet resultSet = stmt.executeQuery(
        "select " + CDPDBLauncher.listOfTables.get(2) + ".* " +
                "from " + CDPDBLauncher.listOfTables.get(2) +
            " INNER JOIN " + CDPDBLauncher.listOfTables.get(0) + " ON " + CDPDBLauncher.listOfTables.get(2) + "." +
                Student.idFieldName + " = " + CDPDBLauncher.listOfTables.get(0) + "." + ExamResult.student_idFieldName +
            " where LOWER(" + Student.surnameFieldName + ") LIKE LOWER('%" + surname + "%') " +
                "and " + CDPDBLauncher.listOfTables.get(0) + "." + ExamResult.markFieldName + " > 0" +
                " LIMIT 1");

        //SELECT Students.*
        //FROM students
        //INNER JOIN ExamResults ON students.id = ExamResults.student_id
        //where LOWER(students.surname) like LOWER('%bloch%') and ExamResults.mark > 0
        //LIMIT 1

        Student stud = findStudentInResultSet(resultSet);
        return stud;
    }

    private static Student findStudentInResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        System.out.println("\n# Searching ... ");
        List<String> l = new CopyOnWriteArrayList<>();
        Student stud = new Student();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                String columnValue = resultSet.getString(i);
                l.add(columnValue);
            }
            stud.setId(Integer.parseInt(l.get(0)));
            stud.setName(l.get(1));
            stud.setSurname(l.get(2));
            stud.setDob(Date.valueOf(l.get(3)));
            stud.setPhone(Long.parseLong(l.get(4)));
            stud.setSkill(l.get(5));
            stud.setCreated(Timestamp.valueOf(l.get(6)));
            stud.setUpdated(Timestamp.valueOf(l.get(7)));
        }
        resultSet.close();

        System.out.println("# Found: " + stud.toString());
        return stud;
    }
}
