package org.uplifteds.entitygenerator;

import org.uplifteds.entity.Subject;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SubjectGenerator {
    public static int tutorNameLength = 10;
    public static List<Subject> listOfSubjects;
    private static List<String> subjectNameList;
    private static List<String> tutorList;

    public static List<Subject> setListOfSubjects(){
        subjectNameList = StudentGenerator.skillList;
        tutorList = fillTutorInList();

        listOfSubjects = new CopyOnWriteArrayList<>();

        final int FIRST_ID = StudentGenerator.GLOBAL_FIRST_ID; // may be any positive Integer

        for (int i = FIRST_ID; (i - FIRST_ID) < subjectNameList.size(); i++){
            Subject subjectTemp = new Subject();
            subjectTemp.setId(i);
            subjectTemp.setSubject_name(subjectNameList.get(i - FIRST_ID));
            subjectTemp.setTutor(tutorList.get(i - FIRST_ID));
            listOfSubjects.add(subjectTemp);
        }
        System.out.println("Subjects were generated");
        return listOfSubjects;
    }

    private static List<String> fillTutorInList() {
        List<String> tutorList = new CopyOnWriteArrayList<>();
        tutorList.add("Prof. Perelman");
        tutorList.add("Prof. Dijkstra");
        tutorList.add("Prof. Knut");
        tutorList.add("Prof. Tannenbaum");

        for (int i = 1; i < StudentGenerator.NUMBER_OF_STUDENTS; i++){
            tutorList.add(RandomType.generateRandomString(tutorNameLength));
        }

        return tutorList;
    }

}
