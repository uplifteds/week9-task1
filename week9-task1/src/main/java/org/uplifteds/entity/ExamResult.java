package org.uplifteds.entity;

import java.lang.reflect.Field;

public class ExamResult {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private int student_id;
    private int subject_id;
    private int mark;

    public static String idFieldName;
    public static String student_idFieldName;
    public static String subject_idFieldName;
    public static String markFieldName;

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public ExamResult() {
    }

    @Override
    public String toString() {
        return "ExamResult{" +
                "id=" + id +
                ", student_id=" + student_id +
                ", subject_id=" + subject_id +
                ", mark=" + mark +
                '}';
    }

    public static void getFieldNameReflection()  {
        Field field = null;
        try {
            field = ExamResult.class.getDeclaredField("id");
            idFieldName = field.getName();
            field = ExamResult.class.getDeclaredField("student_id");
            student_idFieldName = field.getName();
            field = ExamResult.class.getDeclaredField("subject_id");
            subject_idFieldName = field.getName();
            field = ExamResult.class.getDeclaredField("mark");
            markFieldName = field.getName();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
