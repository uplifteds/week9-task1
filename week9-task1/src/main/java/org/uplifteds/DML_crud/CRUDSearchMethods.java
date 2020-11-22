package org.uplifteds.DML_crud;

import org.uplifteds.CDPDBLauncher;
import org.uplifteds.DDL.CloneTableDropColumns;
import org.uplifteds.entity.ExamResult;
import org.uplifteds.entity.Student;

import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CRUDSearchMethods {

    public static Student getStudentByNameExact(Statement stmt, String name) throws SQLException {
        // case-sensitive exact search
        String sql = "select * from " + CDPDBLauncher.listOfTables.get(2) +
                " where " + Student.nameFieldName + " = '" + name + "'";
        ResultSet resultSet = stmt.executeQuery(sql);

        setExplainAnalyzeSQLQuery(sql);

        Student stud = findStudentInResultSet(resultSet);

        return stud;
    }

    public static Student getStudentBySurnamePartial(Statement stmt, String surname) throws SQLException {
        // case-insensitive partial search
        String sql = "select * from " + CDPDBLauncher.listOfTables.get(2) +
                " where LOWER(" + Student.surnameFieldName + ") LIKE LOWER('%" + surname + "%')";
        ResultSet resultSet = stmt.executeQuery(sql);

        setExplainAnalyzeSQLQuery(sql);
        Student stud = findStudentInResultSet(resultSet);
        return stud;
    }

    public static Student getStudentByPhonePartial(Statement stmt, String phone) throws SQLException {
        // case-insensitive partial search
        String sql = "select * from " + CDPDBLauncher.listOfTables.get(2) +
                " where " + Student.phoneFieldName + "::text LIKE '%" + phone + "%'"; // sql cast bigint to string
        ResultSet resultSet = stmt.executeQuery(sql);

        setExplainAnalyzeSQLQuery(sql);
        Student stud = findStudentInResultSet(resultSet);
        return stud;
    }

    public static Student getStudentWithMarkBySurnamePartial(Statement stmt, String surname) throws SQLException {
        // case-insensitive partial search
        String sql = "select " + CDPDBLauncher.listOfTables.get(2) + ".* " +
                "from " + CDPDBLauncher.listOfTables.get(2) +
                " INNER JOIN " + CDPDBLauncher.listOfTables.get(0) + " ON " + CDPDBLauncher.listOfTables.get(2) + "." +
                Student.idFieldName + " = " + CDPDBLauncher.listOfTables.get(0) + "." + ExamResult.student_idFieldName +
                " where LOWER(" + Student.surnameFieldName + ") LIKE LOWER('%" + surname + "%') " +
                " and " + CDPDBLauncher.listOfTables.get(0) + "." + ExamResult.markFieldName + " > 0" +
                " LIMIT 1";
        ResultSet resultSet = stmt.executeQuery(sql);

        /*
        SELECT Students.*
        FROM students
        INNER JOIN ExamResults ON students.id = ExamResults.student_id
        where LOWER(students.surname) like LOWER('%" + surname + "%') and ExamResults.mark > 0
        LIMIT 1
         */

        setExplainAnalyzeSQLQuery(sql);
        Student stud = findStudentInResultSet(resultSet);
        return stud;
    }

    public static Student getStudentByIdExact(Statement stmt, int id) throws SQLException {
        // case-sensitive exact search
        String sql = "select * from " + CDPDBLauncher.listOfTables.get(2) +
                " where " + Student.idFieldName + " = '" + id + "'";
        ResultSet resultSet = stmt.executeQuery(sql);

        System.out.println("Updated timestamp should be Now");
        Student stud = findStudentInResultSet(resultSet);
        return stud;
    }

    public static ExamResult getExamResultByMark(Statement stmt, String mark) throws SQLException {
        String sql = "SELECT * " +
                "FROM examresults " +
                "where ExamResults.mark = " + mark + ";";
        ResultSet resultSet = stmt.executeQuery(sql);

        setExplainAnalyzeSQLQuery(sql);
        ExamResult er = findExamResInResultSet(resultSet);
        return er;
    }

    public static void doFoundCondition(int id, String s) {
        if (id > 0) {
            System.out.println("# Found: " + s);
        } else {
            System.out.println("...nothing is found");
        }
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
        doFoundCondition(stud.getId(), stud.toString());
        return stud;
    }

    private static ExamResult findExamResInResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        System.out.println("\n# Searching ... ");
        List<Integer> l = new CopyOnWriteArrayList<>();
        ExamResult er = new ExamResult();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                Integer columnValue = resultSet.getInt(i);
                l.add(columnValue);
            }
            er.setId(l.get(0));
            er.setStudent_id(l.get(1));
            er.setSubject_id(l.get(2));
            er.setMark(l.get(3));
        }
        doFoundCondition(er.getId(), er.toString());
        resultSet.close();

        return er;
    }

    private static void setExplainAnalyzeSQLQuery(String sql) {
        ExplainAnalyzeSelectMethods.explainSQLQuery = "explain analyze " + sql;
    }
}
