package org.uplifteds.DML_crud;

import org.uplifteds.entity.ExamResult;
import org.uplifteds.entity.Student;
import org.uplifteds.entity.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CRUDInsertMethods {
    public static void doInsertListOfStudents(Connection conn, String table_name, List<Student> listOfStudents) throws SQLException {
        String sqlQuery = "insert into " + table_name + " values (?, ?, ?, ?, ?, ?, ?, ?)";

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
        String sqlQuery = "insert into " + table_name + " values (?, ?, ?)";

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
        String sqlQuery = "insert into " + table_name + " values (?, ?, ?, ?)";

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
}
