package com.pao.laboratory03.exercise;

import java.util.HashMap;
import java.util.Map;

public class Student{
    private String name;
    private int age;
    private Map<Subject, Double> grades;

    Student(String name, int age){
        this.name = name;
        if(age < 18 || age > 60){
            throw new InvalidStudentException("Varsta invalida");
        }
        this.age = age;
        grades = new HashMap<>();
    }

    public int getAge(){return age;}
    public String getName(){return name;}
    public Map<Subject, Double> getGrades(){return grades;}

    public void addGrade(Subject subject, double grade){
        if(grade < 1 || grade > 10)
            throw new InvalidGradeException("Nota invalida");
        else{
            grades.put(subject, grade);
        }
    }

    public double getAverage(){
        double sum = 0;
        int nr = 0;
        for (Map.Entry<Subject, Double> entry : grades.entrySet()) {
            sum += entry.getValue();
            nr += 1;
        }
        if(nr != 0)
            return sum/nr;
        else return 0;
    }
    @Override
    public String toString(){
        return "Student{name='" + name + "', age=" + age + ", avg=" + getAverage() + "}";
    }
}