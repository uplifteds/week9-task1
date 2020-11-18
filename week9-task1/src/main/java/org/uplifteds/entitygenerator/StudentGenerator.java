package org.uplifteds.entitygenerator;


import org.uplifteds.entity.Student;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class StudentGenerator {
    // 1K of subjects
    // 1 million of marks (if 1M marks / 1K subject = then 1K students , not 100K)
    public static final int NUMBER_OF_STUDENTS = 497;

    public static final int GLOBAL_FIRST_ID = 1;
    public static int maxNameLength = 4;
    public static int skillNameLength = 6;
    public static List<Student> listOfStudents;
    public static List<String> skillList;
    private static List<String> nameList;
    private static List<String> surnameList;

    public static List<Student> setListOfStudents(){
        listOfStudents = new CopyOnWriteArrayList<>();

        nameList = fillStudentNameInList();
        surnameList = fillStudSurnameInList();
        skillList = fillStudSkillInList();

        long phone = 7010000000L;

        for (int i = GLOBAL_FIRST_ID; (i - GLOBAL_FIRST_ID) < nameList.size(); i++){
            Student studentTemp = new Student();
            studentTemp.setId(i);
            studentTemp.setName(nameList.get(i - GLOBAL_FIRST_ID));
            studentTemp.setSurname(surnameList.get(i - GLOBAL_FIRST_ID));

            studentTemp.setDob(Date.valueOf(RandomType.generateRandomDate()));

            studentTemp.setPhone(phone + i);
            studentTemp.setSkill(skillList.get(i - GLOBAL_FIRST_ID));

            studentTemp.setCreated(Timestamp.from(RandomType.generateRandomTimestamp()));
            studentTemp.setUpdated(Timestamp.from(RandomType.generateRandomTimestamp()));

            listOfStudents.add(studentTemp);
        }
        System.out.println("Students were generated");
        return listOfStudents;
    }

    private static List<String> fillStudentNameInList() {

        List<String> studentNameList = new CopyOnWriteArrayList<>();
        // this is also linked to PreparedStatement in doInsertListOfStudents() method
        studentNameList.add("John");
        studentNameList.add("Cay");
        studentNameList.add("Joshua");
        studentNameList.add("Alexey");

        for (int i = 1; i < NUMBER_OF_STUDENTS; i++){
            studentNameList.add(RandomType.generateRandomString(maxNameLength));
        }
        return studentNameList;
    }

    private static List<String> fillStudSurnameInList() {
        List<String> studSurnameList = new CopyOnWriteArrayList<>();
        studSurnameList.add("Doe");
        studSurnameList.add("Horstmann");
        studSurnameList.add("Bloch");
        studSurnameList.add("Shipilev");

        for (int i = 1; i < NUMBER_OF_STUDENTS; i++){
            studSurnameList.add(RandomType.generateRandomString(8));
        }
        return studSurnameList;
    }

    private static List<String> fillStudSkillInList() {
        List<String> studSkillList = new CopyOnWriteArrayList<>();
        studSkillList.add("Math");
        studSkillList.add("Computer Science");
        studSkillList.add("Math");
        studSkillList.add("Computer Science");

        for (int i = 1; i < NUMBER_OF_STUDENTS; i++){
            studSkillList.add(RandomType.generateRandomString(skillNameLength));
        }

        return studSkillList;
    }

}
