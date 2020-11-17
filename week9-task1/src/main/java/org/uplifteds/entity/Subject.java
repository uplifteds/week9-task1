package org.uplifteds.entity;

import java.lang.reflect.Field;

public class Subject {
    private int id;
    private String subject_name;
    private String tutor;

    public static String idFieldName;
    public static String subjectFieldName;
    public static String tutorFieldName;

    public String getSubject_name() {
        return subject_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public Subject() {
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", subject_name='" + subject_name + '\'' +
                ", tutor='" + tutor + '\'' +
                '}';
    }

    public static void getFieldNameReflection()  {
        Field field = null;
        try {
            field = Subject.class.getDeclaredField("id");
            idFieldName = field.getName();
            field = Subject.class.getDeclaredField("subject_name");
            subjectFieldName = field.getName();
            field = Subject.class.getDeclaredField("tutor");
            tutorFieldName = field.getName();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
