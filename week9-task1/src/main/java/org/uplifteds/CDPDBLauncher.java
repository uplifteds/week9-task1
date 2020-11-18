package org.uplifteds;

import org.uplifteds.crud.CrudMethods;
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

            long startInsert = System.currentTimeMillis();
//            CrudMethods.doInsertListOfSubjects   (conn,  listOfTables.get(1), listOfSubj);
//            CrudMethods.doInsertListOfStudents   (conn,  listOfTables.get(2), listOfStud);
//            CrudMethods.doInsertListOfExamResults(conn,  listOfTables.get(0), listOfExamRes); // has foreign key, therefore filled last
            long stopInsert = System.currentTimeMillis();
            long diffInsert = (stopInsert - startInsert);

            long startRead = System.currentTimeMillis();
//            CrudMethods.doReadEntriesFromTable(stmt, listOfTables.get(2));
//            CrudMethods.doReadEntriesFromTable(stmt, listOfTables.get(0));
            long stopRead = System.currentTimeMillis();
            long diffRead = (stopRead - startRead);

//            System.out.println("Inserting " + StudentGenerator.NUMBER_OF_STUDENTS +
//                    " students, subjects into Tables took (millisec): " + diffInsert);

//            System.out.println("Reading entries from: " + listOfTables.get(0) +
//                    " table took (millisec): " + diffRead);

            long startSearchExact = System.nanoTime();
            Student stud2 = CrudMethods.getStudentByNameExact(stmt,"John");
            long stopSearchExact = System.nanoTime();
            long diffSearchExact = (stopSearchExact - startSearchExact) / NANO_TO_MICROSEC_DIVIDER;
            System.out.println("Exact-Searching Student in: " + listOfTables.get(2) + " table took (microsec): " +
                    diffSearchExact);
            // = search around 900 microsec; with B-tree, and GIN (pg_trgm) indexes same time

            long startSearch = System.nanoTime();
            Student stud = CrudMethods.getStudentBySurnamePartial(stmt,"shipilev");
            long stopSearch = System.nanoTime();
            long diffSearch = (stopSearch - startSearch) / NANO_TO_MICROSEC_DIVIDER;
            System.out.println("Partial-Searching Student in: " + listOfTables.get(2) + " table took (microsec): " +
                    diffSearch);
            // LIKE search around 11 millisec; with B-tree, and GIN (pg_trgm) index same

            long startSearchPart = System.nanoTime();
            Student stud3 = CrudMethods.getStudentByPhonePartial(stmt, "010000499");
            long stopSearchPart = System.nanoTime();
            long diffSearchPart = (stopSearchPart - startSearchPart) / NANO_TO_MICROSEC_DIVIDER;
            System.out.println("Partial-Searching Student in: " + listOfTables.get(2) + " table took (microsec): " +
                    diffSearchPart);

            long startSearchPartJoin = System.nanoTime();
            Student stud4 = CrudMethods.getStudentWithMarkBySurnamePartial(stmt, "bloch");
            long stopSearchPartJoin = System.nanoTime();
            long diffSearchPartJoin = (stopSearchPartJoin - startSearchPartJoin) / 1000;
            System.out.println("Partial-Searching Student in: " + listOfTables.get(2) + " table with join to " + listOfTables.get(0) + " table took (microsec): " +
                    diffSearchPartJoin);


            //psql : CREATE EXTENSION pg_trgm;

            // 1000 student took 4 min without

            //Test queries:
            //done a. Find user by name (exact match)
            //done b. Find user by surname (partial match)
            //done c. Find user by phone number (partial match) // bigint GIN
            //done d. Find user with marks by user surname (partial match)

            // Try different kind of indexes (B-tree, Hash, GIN, GIST) for your fields.
            // Analyze performance for each of the indexes (use ANALYZE and EXPLAIN).
            // Check the size of the index.
            // Try to set index before inserting test data and after.
            // What was the time? Test data: 1K of users, subject, 1M of marks

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


        }
    }
}

// demo - transitive join from table1 to table3 via transit-table2
// SELECT           subjects.*
//FROM             students
//RIGHT OUTER JOIN examresults ON students.id = examresults.student_id
//RIGHT OUTER JOIN subjects ON examresults.subject_id = subjects.id