package org.uplifteds.entitygenerator;

import org.uplifteds.entity.ExamResult;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class ExamResultGenerator {
    private static int randRangeEnd = 1000_000; // since amount of marks for 1000 students, subjects is 1 mln need to generate unique marks

    public static void setRandRangeEnd(int newRandRangeEnd) {
        randRangeEnd = newRandRangeEnd;
    }

    public static List<ExamResult> listOfExamResults;

    public static List<ExamResult> setListOfExamResults() {
        listOfExamResults = new CopyOnWriteArrayList<>();

        int index = StudentGenerator.GLOBAL_FIRST_ID; // may be any positive Integer
        int studentCounter = -1; // ensure that first ExamResult_Id will start from same Id as Students' first Id
        int k;
        int randomRangeStart = 1;

        for (int i = index; i <= StudentGenerator.listOfStudents.size(); i++){
            for (int j = index; j <= SubjectGenerator.listOfSubjects.size(); j++) {
                ExamResult examResultTemp = new ExamResult();
                // ensure that first ExamResult_Id will start from same Id as Students' first Id
                k = i + j + studentCounter;
                examResultTemp.setId(k);
                examResultTemp.setStudent_id(StudentGenerator.listOfStudents.get(i - 1).getId());
                examResultTemp.setSubject_id(SubjectGenerator.listOfSubjects.get(j - 1).getId());

                Random r = new Random();
                examResultTemp.setMark(r.ints(randomRangeStart, (randRangeEnd)).limit(1).findFirst().getAsInt());

                listOfExamResults.add(examResultTemp);
            }
            studentCounter = studentCounter + SubjectGenerator.listOfSubjects.size() - 1; //
        }
        System.out.println("Students passed subject-exams. Marks were set.");
        return listOfExamResults;
    }

}
