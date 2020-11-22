package org.uplifteds;

import org.uplifteds.DDL.CloneTableDropColumns;
import org.uplifteds.DML_crud.*;
import org.uplifteds.entity.ExamResult;
import org.uplifteds.entity.Student;
import org.uplifteds.entity.Subject;
import org.uplifteds.entitygenerator.ExamResultGenerator;
import org.uplifteds.entitygenerator.StudentGenerator;
import org.uplifteds.entitygenerator.SubjectGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CDPDBLauncher {
    public static List<String> listOfTables = new ArrayList<>();
    static String dropColPrefix = " DROP COLUMN ";

    public static void main( String[] args ) throws SQLException {
        String postgresURL = "jdbc:postgresql://127.0.0.1:5432/postgres"; // localhost for Dev-env
        String user = "javauser"; //non-root user. Granted All permissions on DB
        String password = "plaintextshouldbeencrypted"; // in Prod-env should be stored in Encrypted Secret-Vault

        listOfTables.add("ExamResults"); // has foreign key, therefore added 1st (cascade)
        listOfTables.add("Subjects");
        listOfTables.add("Students");
        Student.getFieldNameReflection();
        Subject.getFieldNameReflection();
        ExamResult.getFieldNameReflection();

        List<Student> listOfStud = StudentGenerator.setListOfStudents();
        List<Subject> listOfSubj = SubjectGenerator.setListOfSubjects();
        List<ExamResult> listOfExamRes = ExamResultGenerator.setListOfExamResults();
//generating 1000 students,subjects and 10 mln examresults takes 4 min

        try (Connection conn = DriverManager.getConnection(postgresURL, user, password);
             Statement stmt = conn.createStatement()) {
//Database table/constraint creation – separate file
// Check create3tables.sql

// crud insert (student, subject), read, update
            CRUDMethods.doDeleteValuesInAllTables(stmt); // clean values before inserting

            CRUDInsertMethods.doInsertListOfSubjects   (conn,  listOfTables.get(1), listOfSubj);
            CRUDInsertMethods.doInsertListOfStudents   (conn,  listOfTables.get(2), listOfStud);

            // has foreign key, therefore filled last (cascade)
            CRUDInsertMethods.doInsertListOfExamResults(conn,  listOfTables.get(0), listOfExamRes);

            Student stud2 = CRUDSearchMethods.getStudentByNameExact(stmt,"John");
//Try different kind of indexes (B-tree, Hash, GIN, GIST) for your fields.
// Analyze performance for each of the indexes (use ANALYZE and EXPLAIN)
//see also index_investigation.pdf
            ExplainAnalyzeSelectMethods.doExplainAnalyzeSearchQuery(stmt);

            String searchSurname = "XTUAKNPS";
            Student stud = CRUDSearchMethods.getStudentBySurnamePartial(stmt,searchSurname);
            ExplainAnalyzeSelectMethods.doExplainAnalyzeSearchQuery(stmt);

            Student stud3 = CRUDSearchMethods.getStudentByPhonePartial(stmt, "7010000499");
            ExplainAnalyzeSelectMethods.doExplainAnalyzeSearchQuery(stmt);

            Student stud4 = CRUDSearchMethods.getStudentWithMarkBySurnamePartial(stmt, searchSurname);
            ExplainAnalyzeSelectMethods.doExplainAnalyzeSearchQuery(stmt);

            ExamResult er = CRUDSearchMethods.getExamResultByMark(stmt, "20958");
            ExplainAnalyzeSelectMethods.doExplainAnalyzeSearchQuery(stmt);

//Add trigger that will update column updated_datetime to current date in case of updating any of student. (1 point)
            CRUDTriggers.createFuncUpdateUpdatedColumnWithIdPassedFromTrigger(conn);
            CRUDTriggers.dropSQLTriggerExecFunc(conn);
            int studentIdToUpdate = 1;
            CRUDTriggers.createTriggerOnStudentsUpdateAndPassIdToFuncUpdate(conn, studentIdToUpdate);
            long newPhone = 1200000004L;
            CRUDMethods.doUpdateFieldOfStudentById(conn, Student.phoneFieldName ,newPhone, studentIdToUpdate);
            Student studentTriggered = CRUDSearchMethods.getStudentByIdExact(stmt, studentIdToUpdate);

//Add validation on DB level that will check username on special characters (reject student name with next characters '@', '#', '$')
//...CHECK (name not SIMILAR TO '%(@|#|$)%') => constraint added to Students table. Check create3tables.sql

//Create snapshot that will contain next data: student name, student surname, subject name, mark (snapshot means that in
// case of changing some data in source table – your snapshot should not change) (1 point)
            doSnapshotCloneTables(stmt);
            CRUDMethods.doUpdateFieldOfStudentById(conn, Student.surnameFieldName ,searchSurname + "a", studentIdToUpdate);
            Student studentTriggered2 = CRUDSearchMethods.getStudentByIdExact(stmt, studentIdToUpdate);
            Student studentClone = CRUDSearchMethodsCloneTable.getCloneStudentByIdExact(stmt, studentIdToUpdate);

// Create function that will return average mark for input user (1 point)
            Double avgMarkOfStudent = CRUDMethods.calcAvgMarkByStudentId(stmt, 2);

//Create function that will return average mark for input subject name (1 point)
            Double avgMarkOnSubjectName = CRUDMethods.calcAvgMarkBySubjectName(stmt, "Math");

// Create function that will return student at "red zone" (red zone means at least 2 marks <=3) (1 point)
            int redzoneThreshold = 60; // if 3, then do ExamResultGenerator.setRandRangeEnd(10), because now randRangeEnd = 1000_000
            // since amount of marks for 1000 students & subjects is 1 mln need to generate unique marks

            CRUDMethods.findStudentInRedZoneAtLeastTwoMarksBelowThreshold(stmt, redzoneThreshold);

//Please, add your changes to GIT task by task
// check github.com/uplifteds/week9-task1

//DB design in suitable format
// check ER diagram.png
        }
    }

    private static void doSnapshotCloneTables(Statement stmt) throws SQLException {
        List<String> listOfStudentsTableColumnsToBeDroped = new ArrayList<>();
        listOfStudentsTableColumnsToBeDroped.add(Student.phoneFieldName);
        listOfStudentsTableColumnsToBeDroped.add(Student.dobFieldName);
        listOfStudentsTableColumnsToBeDroped.add(Student.skillFieldName);
        listOfStudentsTableColumnsToBeDroped.add(Student.createdFieldName);
        listOfStudentsTableColumnsToBeDroped.add(Student.updatedFieldName);
        StringBuilder sbStudents = getStringBuilder(listOfStudentsTableColumnsToBeDroped);
        String sbStudentsStr = sbStudents.toString();
        CloneTableDropColumns.doCreateTable(stmt, listOfTables.get(2),sbStudentsStr );
//
        List<String> listOfSubjectsTableColumnsToBeDroped = new ArrayList<>();
        listOfSubjectsTableColumnsToBeDroped.add(Subject.tutorFieldName);
        StringBuilder sbSubjects = getStringBuilder(listOfSubjectsTableColumnsToBeDroped);
        String sbSubjectsStr = sbSubjects.toString();
        CloneTableDropColumns.doCreateTable(stmt, listOfTables.get(1),sbSubjectsStr );
//
        List<String> listOfExamResTableColumnsToBeDroped = new ArrayList<>();
        listOfExamResTableColumnsToBeDroped.add(ExamResult.student_idFieldName);
        listOfExamResTableColumnsToBeDroped.add(ExamResult.subject_idFieldName);
        StringBuilder sbExamRes = getStringBuilder(listOfExamResTableColumnsToBeDroped);
        String sbExamResStr = sbExamRes.toString();
        CloneTableDropColumns.doCreateTable(stmt, listOfTables.get(0),sbExamResStr);
    }

    private static StringBuilder getStringBuilder(List<String> listOfStudentsTableColumnsToBeDroped) {
        StringBuilder sbStudents = new StringBuilder();
        for (String tempStr : listOfStudentsTableColumnsToBeDroped){
            sbStudents.append(dropColPrefix + tempStr + ",");
        }
        sbStudents.setLength(sbStudents.length() - 1); // remove last "," from appended string
        return sbStudents;
    }
}