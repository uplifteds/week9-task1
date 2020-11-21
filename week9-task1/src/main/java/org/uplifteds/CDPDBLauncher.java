package org.uplifteds;

import org.uplifteds.crud.CRUDMethods;
import org.uplifteds.crud.CRUDSelectMethods;
import org.uplifteds.crud.CRUDTriggers;
import org.uplifteds.crud.ExplainMethods;
import org.uplifteds.entity.ExamResult;
import org.uplifteds.entity.Student;
import org.uplifteds.entity.Subject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CDPDBLauncher {
    public static List<String> listOfTables = new ArrayList<>();
    public static void main( String[] args ) throws SQLException {
        String postgresURL = "jdbc:postgresql://127.0.0.1:5432/postgres"; // localhost for Dev-env
        String user = "javauser"; //non-root user. Granted All permissions on DB
        String password = "plaintextshouldbeencrypted"; // in Prod-env should be stored in Encrypted Secret-Vault
        final int NANO_TO_MICROSEC_DIVIDER = 1_000;

        listOfTables.add("ExamResults"); // has foreign key, therefore added 1st
        listOfTables.add("Subjects");
        listOfTables.add("Students");
        Student.getFieldNameReflection();
        Subject.getFieldNameReflection();
        ExamResult.getFieldNameReflection();

//        List<Student> listOfStud = StudentGenerator.setListOfStudents();
//        List<Subject> listOfSubj = SubjectGenerator.setListOfSubjects();
//        List<ExamResult> listOfExamRes = ExamResultGenerator.setListOfExamResults();

        try (Connection conn = DriverManager.getConnection(postgresURL, user, password);
             Statement stmt = conn.createStatement()) {

            // crud insert (student, subject), read, update
//            CrudMethods.doDeleteValuesInAllTables(stmt); // clean values before inserting

//            CrudMethods.doInsertListOfSubjects   (conn,  listOfTables.get(1), listOfSubj);
//            CrudMethods.doInsertListOfStudents   (conn,  listOfTables.get(2), listOfStud);
//            CrudMethods.doInsertListOfExamResults(conn,  listOfTables.get(0), listOfExamRes); // has foreign key, therefore filled last

//            CrudMethods.doReadEntriesFromTable(stmt, listOfTables.get(2));
//            CrudMethods.doReadEntriesFromTable(stmt, listOfTables.get(0));

            Student stud2 = CRUDSelectMethods.getStudentByNameExact(stmt,"John");
            ExplainMethods.doExplainAnalyzeSearchQuery(stmt);
//see also index_investigation.pdf

            String searchSurname = "XTUAKNPS";
            Student stud = CRUDSelectMethods.getStudentBySurnamePartial(stmt,searchSurname);
            ExplainMethods.doExplainAnalyzeSearchQuery(stmt);

            Student stud3 = CRUDSelectMethods.getStudentByPhonePartial(stmt, "8010000499");
            ExplainMethods.doExplainAnalyzeSearchQuery(stmt);

            Student stud4 = CRUDSelectMethods.getStudentWithMarkBySurnamePartial(stmt, searchSurname);
            ExplainMethods.doExplainAnalyzeSearchQuery(stmt);

            ExamResult er = CRUDSelectMethods.getExamResultByMark(stmt, "20958");
            ExplainMethods.doExplainAnalyzeSearchQuery(stmt);

            //Add trigger that will update column updated_datetime to current date in case of updating any of student. (1 point)
            CRUDTriggers.createTriggerFuncUpdateUpdatedColumn(conn);
            CRUDTriggers.createSQLTriggerExecFunc(conn);
            CRUDMethods.doUpdateFieldOfStudentById(conn, Student.phoneFieldName ,1180000001L, 1);
            CRUDTriggers.dropSQLTriggerExecFunc(conn);
            
            // ============================================================================================================

            //Add validation on DB level that will check username on special characters (reject student name with next characters '@', '#', '$') (1 point)

            //Create snapshot that will contain next data: student name, student surname, subject name, mark (snapshot means that in case of changing some data in source table – your snapshot should not change) (1 point)

            // Create function that will return average mark for input user (1 point)

            //Create function that will return avarage mark for input subject name (1 point)

            // Create function that will return student at "red zone" (red zone means at least 2 marks <=3) (1 point)

            //Extra point (1 point). Show in tests (java application) transaction isolation phenomena. Describe what kind of phenomena is it and how did you achieve it.

            //Extra point 2 (1 point). Implement immutable data trigger. Create new table student_address. Add several rows with test data and do not give acces to update any information inside it. Hint: you can create trigger that will reject any update operation for target table, but save new row with updated (merged with original) data into separate table

            //Index investigation document
        }
    }
}

// demo - transitive join from table1 to table3 via transit-table2
// SELECT           subjects.*
//FROM             students
//RIGHT OUTER JOIN examresults ON students.id = examresults.student_id
//RIGHT OUTER JOIN subjects ON examresults.subject_id = subjects.id