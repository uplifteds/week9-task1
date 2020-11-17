package org.uplifteds.entitygenerator;

import org.uplifteds.entity.ExamResult;
import org.uplifteds.entity.Student;
import org.uplifteds.entity.Subject;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityGenerator {
    public static List<Student> listOfStudents;
    private static List<String> nameList;

    public static List<Subject> listOfSubjects;
    private static List<String> subjectNameList;

    public static List<ExamResult> listOfExamResults;

    public static List<Student> setListOfStudents(){
        nameList = fillStudentNameInList();
        listOfStudents = new CopyOnWriteArrayList<>();

        long phone = 7010000000l;
        final int FIRST_ID = 1; // may be any positive Integer

        for (long i = FIRST_ID; (i - FIRST_ID) < nameList.size(); i++){
            Student studentTemp = new Student();
            studentTemp.setId((int) i);
            studentTemp.setName(nameList.get((int) (i - FIRST_ID)));
            studentTemp.setPhone(phone + (i));
            listOfStudents.add(studentTemp);
        }
        System.out.println("Students were generated");
        return listOfStudents;
    }

    public static List<Subject> setListOfSubjects(){
        subjectNameList = fillSubjectNameInList();
        listOfSubjects = new CopyOnWriteArrayList<>();

        final int FIRST_ID = 1; // may be any positive Integer

        for (int i = FIRST_ID; (i - FIRST_ID) < subjectNameList.size(); i++){
            Subject subjectTemp = new Subject();
            subjectTemp.setId( i);
            subjectTemp.setSubject_name(subjectNameList.get(i - FIRST_ID));

            listOfSubjects.add(subjectTemp);
        }
        System.out.println("Subjects were generated");
        return listOfSubjects;
    }

    public static List<ExamResult> setListOfExamResults() {
        listOfExamResults = new CopyOnWriteArrayList<>();
        long numberOfExams = listOfStudents.size() * listOfSubjects.size();

        int index = 1; // may be any positive Integer
        int counter = -1;

        int randomRangeStart = 10;
        int randRangeEnd = 100;

        for (int i = index; i <= listOfStudents.size(); i++){
            for (int j = index; j <= listOfSubjects.size(); j++) {
                ExamResult examResultTemp = new ExamResult();
                examResultTemp.setId(i + j + counter);
                examResultTemp.setStudent_id(listOfStudents.get(i - 1).getId());
                examResultTemp.setSubject_id(listOfSubjects.get(j - 1).getId());

                int randMark = randomRangeStart + (int) (Math.random() * randRangeEnd);
                examResultTemp.setMark(randMark);

                listOfExamResults.add(examResultTemp);
            }
            counter++;
        }
        System.out.println("ExamResults were generated");
        return listOfExamResults;
    }

    private static List<String> fillStudentNameInList() {
        List<String> studentNameList = new CopyOnWriteArrayList<>();
        // this is also linked to PreparedStatement in doInsertListOfStudents() method
        studentNameList.add("John");
        studentNameList.add("Cay");
        studentNameList.add("Joshua");
        studentNameList.add("Alexey");
        return studentNameList;
    }

    private static List<String> fillSubjectNameInList() {
        List<String> subjectNameList = new CopyOnWriteArrayList<>();
        subjectNameList.add("Math");
        subjectNameList.add("Computer Science");
        return subjectNameList;
    }


}
