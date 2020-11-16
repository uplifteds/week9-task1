package org.uplifteds.entity;

public class Subject {
    private int id;
    private String subjectName;
    private String tutor;

    public String getSubjectName() {
        return subjectName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public Subject(int id, String subjectName, String tutor) {
        this.id = id;
        this.subjectName = subjectName;
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", subjectName='" + subjectName + '\'' +
                ", tutor='" + tutor + '\'' +
                '}';
    }
}
