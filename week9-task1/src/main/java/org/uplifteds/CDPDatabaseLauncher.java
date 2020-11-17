package org.uplifteds;

import org.uplifteds.crud.CrudMethods;
import org.uplifteds.entity.ExamResult;
import org.uplifteds.entity.Student;
import org.uplifteds.entity.Subject;
import org.uplifteds.entitygenerator.EntityGenerator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CDPDatabaseLauncher {
    public static List<String> listOfTables = new ArrayList<>();
    public static void main( String[] args ) throws SQLException {
        String postgresURL = "jdbc:postgresql://127.0.0.1:5432/postgres"; // localhost for Dev-env
        String user = "javauser"; //non-root user. Granted All permissions on table
        String password = "plaintextshouldbeencrypted"; // in Prod-env should be stored in Encrypted Secret-Vault

        listOfTables.add("ExamResults"); // has foreign key, therefore added 1st
        listOfTables.add("subjects");
        listOfTables.add("students");
        Student.getFieldNameReflection();
        List<Student> listOfStud = EntityGenerator.setListOfStudents();
        Subject.getFieldNameReflection();
        List<Subject> listOfSubj = EntityGenerator.setListOfSubjects();

        ExamResult.getFieldNameReflection();
        List<ExamResult> listOfExamRes = EntityGenerator.setListOfExamResults();

        try (Connection conn = DriverManager.getConnection(postgresURL, user, password);
             Statement stmt = conn.createStatement()) {


            // crud insert (student, subject), read, update


            CrudMethods.doDeleteValuesInTableIfExists(stmt);
            CrudMethods.doInsertListOfStudents(conn,  listOfTables.get(2), listOfStud);
            CrudMethods.doInsertListOfSubjects(conn,  listOfTables.get(1), listOfSubj);
            CrudMethods.doInsertListOfExamResults(conn,  listOfTables.get(0), listOfExamRes);

            // 100K of users
            //randStop. 1K of subjects
            //c. 1 million of marks

            //add appropriate constraints (primary keys, foreign keys, indexes, etc)

            //Add trigger that will update column updated_datetime to current date in case of updating any of student. (1 point)

            //Add validation on DB level that will check username on special characters (reject student name with next characters '@', '#', '$') (1 point)

            //Create snapshot that will contain next data: student name, student surname, subject name, mark (snapshot means that in case of changing some data in source table – your snapshot should not change) (1 point)

            // Create function that will return average mark for input user (1 point)

            //Create function that will return avarage mark for input subject name (1 point)

            // Create function that will return student at "red zone" (red zone means at least 2 marks <=3) (1 point)

            //Extra point (1 point). Show in tests (java application) transaction isolation phenomena. Describe what kind of phenomena is it and how did you achieve it.

            //Extra point 2 (1 point). Implement immutable data trigger. Create new table student_address. Add several rows with test data and do not give acces to update any information inside it. Hint: you can create trigger that will reject any update operation for target table, but save new row with updated (merged with original) data into separate table

            //Index investigation document

            //Test queries:
            //a. Find user by name (exact match)
            //randStop. Find user by surname (partial match)
            //c. Find user by phone number (partial match)
            //d. Find user with marks by user surname (partial match)
        }
    }
}
