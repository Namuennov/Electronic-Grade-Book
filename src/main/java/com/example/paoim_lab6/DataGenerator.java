package com.example.paoim_lab6;

public class DataGenerator {

    DataGenerator()
    {
        Student s0 = new Student("Janusz", "Petrucci", 2016, 1998, 15.33);

        Student s1 = new Student("Heniek", "Sienkiewicz", StudentCondition.Absent, 1990, 0., 2013, false);
        Student s2 = new Student("Jose", "Conrad", StudentCondition.Ill, 1999, 13., 2017, false);
        Student s3 = new Student("Gabson", "Marquez", StudentCondition.MakeUp, 2001, 12.5, 2021, true);
        Student s4 = new Student("Ali", "Camus", StudentCondition.Present, 2000, 24.375, 2019, true);
        Student s5 = new Student("Fiodonor", "Dostojewski", StudentCondition.Ill, 1999, 9., 2020, false);
        Student s6 = new Student("Danon", "Alighieri", StudentCondition.Present, 1997, 27.99, 2017, false);
        Student s7 = new Student("Wolfram", "Goethe", StudentCondition.Present, 1997, 9., 2022, true);

        Class mat = new Class("Matematyka", 10);
        mat.addStudent(s1);
        mat.addStudent(s2);
        mat.addStudent(s3);
        mat.addStudent(s4);
        mat.addStudent(s5);
        mat.addStudent(s6);
        mat.addStudent(s7);

        Class fran = new Class("Francuski", 4);
        new Class("Angielski", 5).addStudent(new Student("Karlus", "Wojtyla", StudentCondition.Present, 2000, 21.37, 2019, true));
        Class pol = new Class("Polski", 10);
        Class chem = new Class("Chemia", 30);

        fran.addStudent(new Student("Miroslaw", "Escher", 1992, 1970, 13.333));
        fran.addStudent(new Student("Yamamo", "Amano", 2010, 1989, 8.754));

        pol.addStudent(new Student("Iwo", "Bergman", 2005, 1985, 19.99));
        pol.addStudent(new Student("Queflas", "Tarantino", 2018, 1999, 2.5));
        pol.addStudent(new Student("Damian", "Lynch", 2016, 1990, 34.43));
        pol.addStudent(new Student("Selen", "Sono", 2021, 2002, 0.111));
        pol.addStudent(new Student("Lewis", "von Trier", 2022, 2000, 5.765));
        pol.addStudent(new Student("Abraham", "Tarkovsky", 2008, 1990, 0.111));

        chem.addStudent(new Student("Ted", "Henson", 2022, 2004, 42.06));
        chem.addStudent(new Student("Tadeusz", "Abasi", 2018, 1999, 68.13));
        chem.addStudent(new Student("Arek", "Barrios", 1995, 1981, 115.96));
        chem.addStudent(new Student("Juliusz", "Becker", 2013, 1997, 87));



        mat.addStudent(s0);
        chem.addStudent(s0);
        fran.addStudent(s0);
        s0.addGrade(mat, 5.0F);
        s0.addGrade(mat, 3.5F);
        s0.addGrade(mat, 4.5F);
        s0.addGrade(mat, 4.0F);
        s0.addGrade(mat, 5.0F);
        s0.addGrade(chem, 3.0F);
        s0.addGrade(chem, 3.5F);
        s0.addGrade(chem, 3.0F);
        s0.addGrade(fran, 4.0F);
        s0.addGrade(fran, 4.5F);
        s0.addGrade(fran, 4.5F);
        s0.addGrade(fran, 5.0F);
    }

}
