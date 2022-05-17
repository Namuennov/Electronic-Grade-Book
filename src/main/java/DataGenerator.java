public class DataGenerator {

    DataGenerator()
    {
        Student s1 = new Student("Heniek", "Sienkiewicz", StudentCondition.Absent, 1990, 0., 2013, false);
        Student s2 = new Student("Jose", "Conrad", StudentCondition.Ill, 1999, 13., 2017, false);
        Student s3 = new Student("Gabson", "Marquez", StudentCondition.MakeUp, 2001, 12.5, 2021, true);
        Student s4 = new Student("Ali", "Camus", StudentCondition.Present, 2000, 24.375, 2019, true);
        Student s5 = new Student("Fiodonor", "Dostojewski", StudentCondition.Ill, 1999, 9., 2020, false);
        Student s6 = new Student("Danon", "Alighieri", StudentCondition.Present, 1997, 27.99, 2017, false);
        Student s7 = new Student("Wolfram", "Goethe", StudentCondition.Present, 1997, 9., 2022, true);

        Class group = new Class("Blast Furnace", 10);
        group.addStudent(s1);
        group.addStudent(s2);
        group.addStudent(s3);
        group.addStudent(s4);
        group.addStudent(s5);
        group.addStudent(s6);
        group.addStudent(s7);

        Class alfa = new Class("Alfa", 3);
        new Class("Beta", 5).addStudent(new Student("Karlus", "Wojtyla", StudentCondition.Present, 2000, 21.37, 2019, true));
        Class gamma = new Class("Gamma", 10);
        Class omega = new Class("Omega", 30);

        alfa.addStudent(new Student("Miroslaw", "Escher", 1992, 1970, 13.333));
        alfa.addStudent(new Student("Yamamo", "Amano", 2010, 1989, 8.754));

        gamma.addStudent(new Student("Iwo", "Bergman", 2005, 1985, 19.99));
        gamma.addStudent(new Student("Queflas", "Tarantino", 2018, 1999, 2.5));
        gamma.addStudent(new Student("Damian", "Lynch", 2016, 1990, 34.43));
        gamma.addStudent(new Student("Selen", "Sono", 2021, 2002, 0.111));
        gamma.addStudent(new Student("Lewis", "von Trier", 2022, 2000, 5.765));
        gamma.addStudent(new Student("Abraham", "Tarkovsky", 2008, 1990, 0.111));

        omega.addStudent(new Student("Ted", "Henson", 2022, 2004, 42.06));
        omega.addStudent(new Student("Tadeusz", "Abasi", 2018, 1999, 68.13));
        omega.addStudent(new Student("Arek", "Barrios", 1995, 1981, 115.96));
        omega.addStudent(new Student("Juliusz", "Becker", 2013, 1997, 87));
    }

}
