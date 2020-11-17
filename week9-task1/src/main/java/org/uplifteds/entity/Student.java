package org.uplifteds.entity;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;

public class Student {
    private int id;
    private String name;
    private String surname;
    private Date dob;
    private long phone;
    private String skill;
    private Timestamp created;
    private Timestamp updated;

    public static String idFieldName;
    public static String nameFieldName;
    public static String surnameFieldName;
    public static String dobFieldName;
    public static String phoneFieldName;
    public static String skillFieldName;
    public static String createdFieldName;
    public static String updatedFieldName;

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

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
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

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dob=" + dob +
                ", phone=" + phone +
                ", skill='" + skill + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }

    public static void getFieldNameReflection ()  {
        Field field = null;
        try {
            field = Student.class.getDeclaredField("id");
            idFieldName = field.getName();
            field = Student.class.getDeclaredField("name");
            nameFieldName = field.getName();
            field = Student.class.getDeclaredField("surname");
            surnameFieldName = field.getName();
            field = Student.class.getDeclaredField("dob");
            dobFieldName = field.getName();
            field = Student.class.getDeclaredField("phone");
            phoneFieldName = field.getName();
            field = Student.class.getDeclaredField("skill");
            skillFieldName = field.getName();
            field = Student.class.getDeclaredField("created");
            createdFieldName = field.getName();
            field = Student.class.getDeclaredField("updated");
            updatedFieldName = field.getName();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
