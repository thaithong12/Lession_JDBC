package com.company;

public class Student {
    private int id;

    private String student_name;

    private Double dht;

    private int student_class_id;

    public Student() {
    }

    public Student(int id, String student_name, Double dht, int student_class_id) {
        this.id = id;
        this.student_name = student_name;
        this.dht = dht;
        this.student_class_id = student_class_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public Double getDht() {
        return dht;
    }

    public void setDht(Double dht) {
        this.dht = dht;
    }

    public int getStudent_class_id() {
        return student_class_id;
    }

    public void setStudent_class_id(int student_class_id) {
        this.student_class_id = student_class_id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", student_name='" + student_name + '\'' +
                ", dht=" + dht +
                ", student_class_id=" + student_class_id +
                '}';
    }
}
