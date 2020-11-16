package org.uplifteds.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Student {
    private int id;
    private String name;
    private String surname;
    private Date dob;
    private int phone;
    private String primarySkill;
    private Timestamp created;
    private Timestamp updated;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPrimarySkill() {
        return primarySkill;
    }

    public void setPrimarySkill(String primarySkill) {
        this.primarySkill = primarySkill;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public Student(int id, String name, String surname, Date dob, int phone, String primarySkill, Timestamp created, Timestamp updated) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.phone = phone;
        this.primarySkill = primarySkill;
        this.created = created;
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dob=" + dob +
                ", phone=" + phone +
                ", primarySkill='" + primarySkill + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
