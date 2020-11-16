package org.uplifteds.entity;

public class ExamResult {
    private String student_id;
    private String subject;
    private int mark;

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public ExamResult(String student_id, String subject) {
        this.student_id = student_id;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "ExamResult{" +
                "student_id='" + student_id + '\'' +
                ", subject='" + subject + '\'' +
                ", mark=" + mark +
                '}';
    }
}
