package com.example.paoim_lab6;

import java.util.*;

public class Student
        implements Comparable<Student>{

    //=========
    Map<Class, List<Float>> grades = new HashMap<>();
    //=========

    //=========
    static List<Student> allStudents = new ArrayList<>();
    //=========

    String name;
    String surname;
    StudentCondition state;
    int birthYear;
    double points;
    int studiesYear;        //rok rozpoczęcia studiów
    boolean scholarship;    //stypendium

    Student(String Name, String Surname, StudentCondition State, int Birth, double Points, int Studies, boolean Scholarship)
    {
        name = Name;
        surname = Surname;
        state = State;
        birthYear = Birth;
        points = Points;
        studiesYear = Studies;
        scholarship = Scholarship;

        allStudents.add(this);
        //grades.put(Class.any, new ArrayList<Float>());
    }

    //przeładowany konstruktor do szybszego i łatwego inicjalizowania
    Student(String Name, String Surname, StudentCondition State, int Birth, double Points)
    {
        name = Name;
        surname = Surname;
        state = State;
        birthYear = Birth;
        points = Points;
        studiesYear = 2019;
        scholarship = false;

        allStudents.add(this);
        //grades.put(Class.any, new ArrayList<Float>());
    }

    //konstruktor do Swinga
    Student(String Name, String Surname, int StudiesYear, int Birth, double Points)
    {
        name = Name;
        surname = Surname;
        state = StudentCondition.Present;
        birthYear = Birth;
        points = Points;
        studiesYear = StudiesYear;
        scholarship = false;

        allStudents.add(this);
        //Class.any.addStudent(this);
        //grades.put(Class.any, new ArrayList<Float>());
    }

    Student()
    {
        ;
    }

    void print()
    {
        if(scholarship==true)
            System.out.println("Student: "+name+" "+surname+"\nState: "+state+"\nYear of birth: "
                    +birthYear+"\nPoints: "+points+"\nFirst year of studies: "+studiesYear+"\nScholarship: yes");
        else
            System.out.println("Student: "+name+" "+surname+"\nState: "+state+"\nYear of birth: "
                    +birthYear+"\nPoints: "+points+"\nFirst year of studies: "+studiesYear+"\nScholarship: no");
    }

    String convertToString()
    {
        //return (name + " " + surname + " " + birthYear);
        return toString();
    }
    public String toString()
    {
        return (name + " " + surname + " " + birthYear);
    }

    @Override
    public int compareTo(Student o) {

        return surname.compareTo(o.surname);
    }

    float average(Class c)
    {
        float sum = 0.0F;
        int counter = 0;
        while(counter<grades.get(c).size())
        {
            sum += grades.get(c).get(counter);
            counter++;
        }
        if(counter==0) return 0.0F;
        else return sum/(float)counter;
    }

    float globalAverage()
    {
        float sum = 0.0F;
        int counter = 0;
        int globalCounter = 0;
        for(int i=0; i<Class.allClasses.size(); i++)
        {
            if(Class.allClasses.get(i).students.contains(this))
            {
                counter = 0;
                while (counter < grades.get(Class.allClasses.get(i)).size())
                {
                    sum += grades.get(Class.allClasses.get(i)).get(counter);
                    counter++;
                    globalCounter++;
                }
            }
        }
        if(globalCounter==0) return 0.0F;
        else return sum/(float)globalCounter;
    }

    void addGrade(Class c, float grade)
    {
        grades.get(c).add(grade);
        //grades.get(Class.any).add(grade);
    }
}
