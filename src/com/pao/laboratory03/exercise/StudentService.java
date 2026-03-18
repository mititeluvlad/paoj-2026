package com.pao.laboratory03.exercise;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentService{
    private List<Student> students;

    private StudentService(){
        this.students = new ArrayList<>();
    }

    private static class Holder{
        private static final StudentService INSTANCE = new StudentService();
    }

    public static StudentService getInstance(){
        return Holder.INSTANCE;
    }

    public void addStudent(String name, int age){
        for(int i = 0; i < students.size(); ++i){
            if(students.get(i).getName().equals(name))
                throw new RuntimeException("Exista deja studentul");
        }
        Student st = new Student(name, age);
        students.add(st);
    }
    
    public Student findByName(String name){
        for(int i = 0; i < students.size(); ++i){
            if(students.get(i).getName().equals(name))
                return students.get(i);
        }
        throw new RuntimeException("Nu exista studentul");
    }

    public void addGrade(String studentName, Subject subject, double grade){
        Student stud = findByName(studentName);
        stud.addGrade(subject,grade);
    }

    public void printAllStudents(){
        for(int i = 0; i < students.size(); ++i){
            System.out.println(students.get(i).toString());
        }
    }

    public void printTopStudents(){
        List<Student> lst = new ArrayList<>(students);
        lst.sort((o1, o2) -> Double.compare(o2.getAverage(), o1.getAverage()));
        for(int i = 0; i < lst.size(); ++i){
            System.out.println(lst.get(i).toString());
        }
    }

    public Map<Subject, Double> getAveragePerSubject(){
        Map<Subject, Double> map = new HashMap<>();
        Map<Subject, Integer> count = new HashMap<>();
        for(int i = 0; i < students.size(); ++i){
            Map<Subject, Double> note = students.get(i).getGrades();
            for (Map.Entry<Subject, Double> entry : note.entrySet()){
                if(!map.containsKey(entry.getKey())){
                    map.put(entry.getKey(),entry.getValue());
                    count.put(entry.getKey(),1);
                }
                else{
                    map.put(entry.getKey(), map.get(entry.getKey()) + entry.getValue());
                    count.put(entry.getKey(),count.get(entry.getKey()) + 1);
                }
            }
        }
            Map<Subject, Double> fin = new HashMap<>();
            for (Map.Entry<Subject, Double> entry : map.entrySet()){
                fin.put(entry.getKey(), entry.getValue()/count.get(entry.getKey()));
            }
            return fin;
    }
}