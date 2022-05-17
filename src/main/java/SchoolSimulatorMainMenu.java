import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SchoolSimulatorMainMenu {

    JFrame menuFrame;
    int selectedIndex;
    SessionFactory sessionFactory;

    JPanel createMainMenuPanel() {
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new FlowLayout());

        JButton studentsButton = new JButton("Students");
        studentsButton.setActionCommand("displayStudentsList");
        studentsButton.addActionListener(new ButtonClickListener());
        mainMenuPanel.add(studentsButton);

        JButton classesButton = new JButton("Classes");
        classesButton.setActionCommand("displayClassesList");
        classesButton.addActionListener(new ButtonClickListener());
        mainMenuPanel.add(classesButton);

        JButton serializeButton = new JButton("Serialize data");
        serializeButton.setActionCommand("serializeData");
        serializeButton.addActionListener(new ButtonClickListener());
        mainMenuPanel.add(serializeButton);

        JButton saveToCSVButton = new JButton("Save data to .csv file");
        saveToCSVButton.setActionCommand("saveToCSV");
        saveToCSVButton.addActionListener(new ButtonClickListener());
        mainMenuPanel.add(saveToCSVButton);

        selectedIndex = -1;

        return mainMenuPanel;
    }
    public SchoolSimulatorMainMenu() {
        menuFrame = new JFrame("School Simulator");
        JFrame.setDefaultLookAndFeelDecorated(true);
        menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainMenuPanel = createMainMenuPanel();
        menuFrame.setContentPane(mainMenuPanel);
        menuFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(menuFrame, "Do you want to save data to .csv before exit?",
                        "Saving data", JOptionPane.YES_NO_OPTION);
                if(result==JOptionPane.YES_OPTION) saveToCSV();
            }
        });
        menuFrame.setVisible(true);

        //deserializeData();
        //readFromCSV();
        try {
            setUpSessionFactory();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(menuFrame, "Something went wrong during setting up connection to database!", "Connection to database failed", JOptionPane.WARNING_MESSAGE);
        }
    }
    JPanel createStudentsListMenuPanel() {
        JPanel studentsListMenuPanel = new JPanel();
        studentsListMenuPanel.setLayout(new BoxLayout(studentsListMenuPanel, BoxLayout.Y_AXIS));

//        DefaultListModel<Student> listOfStudents = new DefaultListModel<>();
//        for(int i=0; i<Student.allStudents.size(); i++)
//            listOfStudents.add(i, Student.allStudents.get(i));
        DefaultListModel<Student> listOfStudents = DAOschool.getAllStudentsDefaultListModel(sessionFactory);
        JList studentsList = new JList(listOfStudents);

        GenericTableStudent genericStudent;
        JTable tableStudent;
        genericStudent = new GenericTableStudent(new Student());
        tableStudent = new JTable(genericStudent);
        final Student[] s = {new Student()};
        studentsList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                    //selectedIndex = Student.allStudents.indexOf(studentsList.getSelectedValue());
                    s[0] = (Student)studentsList.getSelectedValue();
                    selectedIndex = s[0].id;
                    //genericStudent.set(Student.allStudents.get(selectedIndex));
                    genericStudent.set(DAOschool.getStudent(sessionFactory, selectedIndex));
                    tableStudent.valueChanged(arg0);
            }
        });
        studentsListMenuPanel.add(studentsList);

        JButton addStudent = new JButton("Add student");
        addStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
        addStudent.setActionCommand("addStudent");
        addStudent.addActionListener(new ButtonClickListener());
        studentsListMenuPanel.add(addStudent);

        JButton editStudent = new JButton("Edit student");
        editStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
        editStudent.setActionCommand("editStudent");
        editStudent.addActionListener(new ButtonClickListener());
        studentsListMenuPanel.add(editStudent);

        JButton removeStudent = new JButton("Remove student");
        removeStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeStudent.setActionCommand("removeStudent");
        removeStudent.addActionListener(new ButtonClickListener());
        studentsListMenuPanel.add(removeStudent);

        JButton sort = new JButton("Sort students");
        sort.setAlignmentX(Component.CENTER_ALIGNMENT);
        sort.setActionCommand("sortStudents");
        sort.addActionListener(new ButtonClickListener());
        studentsListMenuPanel.add(sort);

        JButton search = new JButton("Search students by surname");
        search.setAlignmentX(Component.CENTER_ALIGNMENT);
        search.setActionCommand("searchStudents");
        search.addActionListener(new ButtonClickListener());
        studentsListMenuPanel.add(search);

        JButton backToMainMenu = new JButton("Back to main menu");
        backToMainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToMainMenu.setActionCommand("backToMainMenu");
        backToMainMenu.addActionListener(new ButtonClickListener());
        studentsListMenuPanel.add(backToMainMenu);

        tableStudent.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        tableStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
        studentsListMenuPanel.add(tableStudent);

        return studentsListMenuPanel;
    }
    JPanel createClassesListMenuPanel() {
        JPanel classesListMenuPanel = new JPanel();
        classesListMenuPanel.setLayout(new BoxLayout(classesListMenuPanel, BoxLayout.Y_AXIS));

//        DefaultListModel<Class> listOfClasses = new DefaultListModel<>();
//        for(int i=0; i<Class.allClasses.size(); i++)
//            listOfClasses.add(i, Class.allClasses.get(i));

        DefaultListModel<Class> listOfClasses = DAOschool.getAllClassesDefaultListModel(sessionFactory);
        JList classesList = new JList(listOfClasses);
        JLabel labelTest = new JLabel();
        JLabel labelRating = new JLabel();

        GenericTableClass genericClass;
        JTable tableClass;
        genericClass = new GenericTableClass(new ArrayList<Student>());
        tableClass = new JTable(genericClass);

        final Class[] c = {new Class()};

        classesList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                //selectedIndex = Class.allClasses.indexOf(classesList.getSelectedValue());
                c[0] = (Class)classesList.getSelectedValue();
                selectedIndex = c[0].id;
                //labelTest.setText(Class.allClasses.get(selectedIndex).toString());
                //genericClass.set(Class.allClasses.get(selectedIndex).students);
                labelTest.setText(DAOschool.getClass(sessionFactory, selectedIndex).toString());
                labelRating.setText(DAOschool.getRatingForClass(sessionFactory, selectedIndex));
                genericClass.set(DAOschool.getClass(sessionFactory, selectedIndex).students);
                tableClass.valueChanged(e);
            }
        });
        classesListMenuPanel.add(classesList);

        JButton addStudentToClass = new JButton("Add student to class");
        addStudentToClass.setAlignmentX(Component.CENTER_ALIGNMENT);
        addStudentToClass.setActionCommand("addStudentToClass");
        addStudentToClass.addActionListener(new ButtonClickListener());
        classesListMenuPanel.add(addStudentToClass);

        JButton removeStudentFromClass = new JButton("Remove student from class");
        removeStudentFromClass.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeStudentFromClass.setActionCommand("removeStudentFromClass");
        removeStudentFromClass.addActionListener(new ButtonClickListener());
        classesListMenuPanel.add(removeStudentFromClass);

        JButton addClass = new JButton("Add class");
        addClass.setAlignmentX(Component.CENTER_ALIGNMENT);
        addClass.setActionCommand("addClass");
        addClass.addActionListener(new ButtonClickListener());
        classesListMenuPanel.add(addClass);

        JButton editClass = new JButton("Edit class");
        editClass.setAlignmentX(Component.CENTER_ALIGNMENT);
        editClass.setActionCommand("editClass");
        editClass.addActionListener(new ButtonClickListener());
        classesListMenuPanel.add(editClass);

        JButton removeClass = new JButton("Remove class");
        removeClass.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeClass.setActionCommand("removeClass");
        removeClass.addActionListener(new ButtonClickListener());
        classesListMenuPanel.add(removeClass);

        JButton sort = new JButton("Sort classes");
        sort.setAlignmentX(Component.CENTER_ALIGNMENT);
        sort.setActionCommand("sortClasses");
        sort.addActionListener(new ButtonClickListener());
        classesListMenuPanel.add(sort);

        JButton backToMainMenu = new JButton("Back to main menu");
        backToMainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToMainMenu.setActionCommand("backToMainMenu");
        backToMainMenu.addActionListener(new ButtonClickListener());
        classesListMenuPanel.add(backToMainMenu);

        labelTest.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        labelTest.setAlignmentX(Component.CENTER_ALIGNMENT);
        classesListMenuPanel.add(labelTest);

        tableClass.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        tableClass.setAlignmentX(Component.CENTER_ALIGNMENT);
        classesListMenuPanel.add(tableClass);

        labelRating.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        labelRating.setAlignmentX(Component.CENTER_ALIGNMENT);
        classesListMenuPanel.add(labelRating);

        return classesListMenuPanel;
    }
    void displayStudentsList() {
        menuFrame.getContentPane().removeAll();
        JPanel studentsListMenuPanel = createStudentsListMenuPanel();
        menuFrame.add(studentsListMenuPanel);
        selectedIndex = -1;
        menuFrame.revalidate();
        menuFrame.repaint();
    }
    void displayClassesList() {
        menuFrame.getContentPane().removeAll();
        JPanel classesListMenuPanel = createClassesListMenuPanel();
        menuFrame.add(classesListMenuPanel);
        selectedIndex = -1;
        menuFrame.revalidate();
        menuFrame.repaint();
    }
    void addStudent() {
        JTextField name = new JTextField(20);
        JTextField surname = new JTextField(20);
        JTextField studiesYear = new JTextField(20);
        JTextField birth = new JTextField(20);
        JTextField points = new JTextField(20);

        JPanel addStudentPanel = new JPanel();
        addStudentPanel.setLayout(new BoxLayout(addStudentPanel, BoxLayout.Y_AXIS));
        addStudentPanel.add(new JLabel("Student's name:"));
        addStudentPanel.add(name);
        addStudentPanel.add(new JLabel("Student's surname:"));
        addStudentPanel.add(surname);
        addStudentPanel.add(new JLabel("First year of studies:"));
        addStudentPanel.add(studiesYear);
        addStudentPanel.add(new JLabel("Student's birth year:"));
        addStudentPanel.add(birth);
        addStudentPanel.add(new JLabel("Student's points:"));
        addStudentPanel.add(points);

        int result = JOptionPane.showConfirmDialog(null, addStudentPanel,
                "=Enter student's data=", JOptionPane.OK_CANCEL_OPTION);
        if(result==JOptionPane.OK_OPTION)
        {
            if(name.getText().isEmpty() || surname.getText().isEmpty() || birth.getText().isEmpty() || studiesYear.getText().isEmpty() || points.getText().isEmpty())
                JOptionPane.showMessageDialog(menuFrame, "Some arguments are empty!", "Alert", JOptionPane.WARNING_MESSAGE);
            else {
                try {
                    Student s = new Student(name.getText(), surname.getText(), Integer.parseInt(studiesYear.getText()),
                            Integer.parseInt(birth.getText()), Double.parseDouble(points.getText()));
                    DAOschool.addStudent(sessionFactory, s);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(menuFrame, "Wrong arguments!", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        displayStudentsList();
    }
    void editStudent() {
        if(selectedIndex==-1) return;
        //Student s = Student.allStudents.get(selectedIndex);
        Student s = DAOschool.getStudent(sessionFactory, selectedIndex);

        JTextField name = new JTextField(s.name , 20);
        JTextField surname = new JTextField(s.surname , 20);
        JTextField studiesYear = new JTextField(String.valueOf(s.studiesYear), 20);
        JTextField birth = new JTextField(String.valueOf(s.birthYear), 20);
        JTextField points = new JTextField(String.valueOf(s.points), 20);

        JPanel addStudentPanel = new JPanel();
        addStudentPanel.setLayout(new BoxLayout(addStudentPanel, BoxLayout.Y_AXIS));
        addStudentPanel.add(new JLabel("Student's name:"));
        addStudentPanel.add(name);
        addStudentPanel.add(new JLabel("Student's surname:"));
        addStudentPanel.add(surname);
        addStudentPanel.add(new JLabel("First year of studies:"));
        addStudentPanel.add(studiesYear);
        addStudentPanel.add(new JLabel("Student's birth year:"));
        addStudentPanel.add(birth);
        addStudentPanel.add(new JLabel("Student's points:"));
        addStudentPanel.add(points);

        int result = JOptionPane.showConfirmDialog(null, addStudentPanel,
                "=Edit student's data=", JOptionPane.OK_CANCEL_OPTION);
        if(result==JOptionPane.OK_OPTION)
        {
            if(name.getText().isEmpty() || surname.getText().isEmpty() || birth.getText().isEmpty() || studiesYear.getText().isEmpty() || points.getText().isEmpty())
                JOptionPane.showMessageDialog(menuFrame, "Some arguments are empty!", "Alert", JOptionPane.WARNING_MESSAGE);
            else {
                try {
                    s.name=name.getText();
                    s.surname=surname.getText();
                    s.studiesYear=Integer.parseInt(studiesYear.getText());
                    s.birthYear=Integer.parseInt(birth.getText());
                    s.points=Double.parseDouble(points.getText());
                    DAOschool.updateStudent(sessionFactory, s, selectedIndex);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(menuFrame, "Wrong arguments!", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        displayStudentsList();
    }
    void removeStudent() {
        if(selectedIndex==-1) return;
////        Student s = Student.allStudents.get(selectedIndex);
//        Student s = DAOschool.getStudent(sessionFactory, selectedIndex);
//        Student.allStudents.remove(selectedIndex);
//        for(Class c : Class.allClasses)
//        {
//            c.getStudent(s);
//        }
        DAOschool.deleteStudent(sessionFactory, selectedIndex);
        displayStudentsList();
    }
    void backToMainMenu() {
        menuFrame.getContentPane().removeAll();
        JPanel mainMenuPanel = createMainMenuPanel();
        menuFrame.add(mainMenuPanel);
        menuFrame.revalidate();
        menuFrame.repaint();
    }
    void addStudentToClass() {
        if(selectedIndex==-1) return;
//        Class c = Class.allClasses.get(selectedIndex);
        int classIndex = selectedIndex;
        selectedIndex=-1;
        JPanel addStudentPanel = new JPanel();
        addStudentPanel.setLayout(new BoxLayout(addStudentPanel, BoxLayout.Y_AXIS));

        //DefaultListModel<String> listOfStudents = new DefaultListModel<>();
//        int j=0;
//        for(int i=0; i<Student.allStudents.size(); i++)
//        {
//            if(!c.students.contains(Student.allStudents.get(i)))
//                //listOfStudents.add(j++, (i + 1) + "." + Student.allStudents.get(i).convertToString());
//                listOfStudents.add(j++, Student.allStudents.get(i));
//        }
        DefaultListModel<Student> listOfStudents = DAOschool.getStudentsNOTFromClassDefaultListModel(sessionFactory, classIndex);
        JList studentsList = new JList(listOfStudents);

        final Student[] s = {new Student()};
        studentsList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                //selectedIndex = getNumericValue(studentsList.getSelectedValue().toString().charAt(0)-1);
                //selectedIndex = Student.allStudents.indexOf(studentsList.getSelectedValue());
                s[0] = (Student)studentsList.getSelectedValue();
                selectedIndex = s[0].id;
            }
        });
        addStudentPanel.add(studentsList);

        int result = JOptionPane.showConfirmDialog(null, addStudentPanel,
                "=Choose student to add to class "+ DAOschool.getClass(sessionFactory, classIndex).name +"=", JOptionPane.OK_CANCEL_OPTION);
        if(result==JOptionPane.OK_OPTION)
        {
            if(selectedIndex==-1)
                JOptionPane.showMessageDialog(menuFrame, "You didn't chose anyone!", "Alert", JOptionPane.WARNING_MESSAGE);
            else {
                try {
//                    c.addStudent(Student.allStudents.get(selectedIndex));
                    DAOschool.addStudentToClass(sessionFactory, classIndex, selectedIndex);
                }
                catch(IllegalArgumentException e)
                {
                    JOptionPane.showMessageDialog(menuFrame, "This class is full!", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        displayClassesList();
    }
    void removeStudentFromClass() {
        if(selectedIndex==-1) return;
//        Class c = Class.allClasses.get(selectedIndex);
        int classIndex = selectedIndex;
        selectedIndex=-1;
        JPanel removeStudentPanel = new JPanel();
        removeStudentPanel.setLayout(new BoxLayout(removeStudentPanel, BoxLayout.Y_AXIS));

//        DefaultListModel<Student> listOfStudents = new DefaultListModel<>();
//        for(int i=0; i<c.students.size(); i++)
//        {
//                listOfStudents.add(i, c.students.get(i));
//        }

        DefaultListModel<Student> listOfStudents = DAOschool.getStudentsFromClassDefaultListModel(sessionFactory, classIndex);
        JList studentsList = new JList(listOfStudents);
        final Student[] s = {new Student()};
        studentsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
//                selectedIndex = Student.allStudents.indexOf(studentsList.getSelectedValue());
                s[0] = (Student)studentsList.getSelectedValue();
                selectedIndex = s[0].id;

            }
        });
        removeStudentPanel.add(studentsList);

        int result = JOptionPane.showConfirmDialog(null, removeStudentPanel,
                "=Choose student to remove from class "+ DAOschool.getClass(sessionFactory, classIndex).name +"=", JOptionPane.OK_CANCEL_OPTION);
        if(result==JOptionPane.OK_OPTION)
        {
            if(selectedIndex==-1)
                JOptionPane.showMessageDialog(menuFrame, "You didn't chose anyone!", "Alert", JOptionPane.WARNING_MESSAGE);
            else {
//                c.students.remove(Student.allStudents.get(selectedIndex));
                DAOschool.removeStudentFromClass(sessionFactory, classIndex, selectedIndex);
            }
        }

        displayClassesList();
    }
    void addClass() {
        JTextField name = new JTextField(20);
        JTextField limit = new JTextField(20);

        JPanel addClassPanel = new JPanel();
        addClassPanel.setLayout(new BoxLayout(addClassPanel, BoxLayout.Y_AXIS));
        addClassPanel.add(new JLabel("Class' name:"));
        addClassPanel.add(name);
        addClassPanel.add(new JLabel("Limit of students:"));
        addClassPanel.add(limit);

        int result = JOptionPane.showConfirmDialog(null, addClassPanel,
                "=Enter class' data=", JOptionPane.OK_CANCEL_OPTION);
        if(result==JOptionPane.OK_OPTION)
        {
            if(name.getText().isEmpty() || limit.getText().isEmpty())
                JOptionPane.showMessageDialog(menuFrame, "Some arguments are empty!", "Alert", JOptionPane.WARNING_MESSAGE);
            else {
                try {
                    Class c = new Class(name.getText(), Integer.parseInt(limit.getText()));
                    DAOschool.addClass(sessionFactory, c);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(menuFrame, "Wrong arguments!", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        displayClassesList();
    }
    void editClass() {
        if(selectedIndex==-1) return;
//        Class c = Class.allClasses.get(selectedIndex);
        Class c = DAOschool.getClass(sessionFactory, selectedIndex);

        JTextField name = new JTextField(c.name , 20);
        JTextField limit = new JTextField(String.valueOf(c.limitOfStudents) , 20);

        JPanel editClassPanel = new JPanel();
        editClassPanel.setLayout(new BoxLayout(editClassPanel, BoxLayout.Y_AXIS));
        editClassPanel.add(new JLabel("Class' name:"));
        editClassPanel.add(name);
        editClassPanel.add(new JLabel("Limit of students:"));
        editClassPanel.add(limit);

        int result = JOptionPane.showConfirmDialog(null, editClassPanel,
                "=Edit class' data=", JOptionPane.OK_CANCEL_OPTION);
        if(result==JOptionPane.OK_OPTION)
        {
            if(name.getText().isEmpty() || limit.getText().isEmpty())
                JOptionPane.showMessageDialog(menuFrame, "Some arguments are empty!", "Alert", JOptionPane.WARNING_MESSAGE);
            else {
                try {
                    if(Integer.parseInt(limit.getText()) <= 0)
                        JOptionPane.showMessageDialog(menuFrame, "Given limit of students is negative!", "Alert", JOptionPane.WARNING_MESSAGE);
                    else if(Integer.parseInt(limit.getText()) < c.students.size())
                        JOptionPane.showMessageDialog(menuFrame, "Given limit of students is less than amount of students in this class!", "Alert", JOptionPane.WARNING_MESSAGE);
                    else {
                        c.name = name.getText();
                        c.limitOfStudents = Integer.parseInt(limit.getText());
                        DAOschool.updateClass(sessionFactory, c, selectedIndex);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(menuFrame, "Wrong arguments!", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        displayClassesList();
    }
    void removeClass() {
        if(selectedIndex==-1) return;
//        Class.allClasses.remove(selectedIndex);
        DAOschool.deleteClass(sessionFactory, selectedIndex);
        displayClassesList();
    }
    void sortStudents() {
        JPanel sortedPanel = new JPanel();
        sortedPanel.setLayout(new BoxLayout(sortedPanel, BoxLayout.Y_AXIS));

//        List<Student> sorted = new ArrayList<>();
//        for(int i=0; i<Student.allStudents.size(); i++) sorted.add(Student.allStudents.get(i));
//        Collections.sort(sorted);
//
//        DefaultListModel<String> listOfStudents = new DefaultListModel<>();
//        int j=0;
//        for(int i=0; i<sorted.size(); i++)
//        {
//            j=Student.allStudents.indexOf(sorted.get(i));
//            listOfStudents.add(i, (j + 1) + "." + sorted.get(i).convertToString());
//        }

        DefaultListModel<Student> listOfStudents = DAOschool.getAllStudentsDefaultListModelSORTED(sessionFactory);
        JList studentsList = new JList(listOfStudents);

        sortedPanel.add(studentsList);
        JOptionPane.showConfirmDialog(null, sortedPanel,
                "=Sorted list of students=", JOptionPane.CLOSED_OPTION);
    }
    void sortClasses() {
        JPanel sortedPanel = new JPanel();
        sortedPanel.setLayout(new BoxLayout(sortedPanel, BoxLayout.Y_AXIS));

//        List<Class> sorted = new ArrayList<>();
//        for(int i=0; i<Class.allClasses.size(); i++) sorted.add(Class.allClasses.get(i));
//        Collections.sort(sorted);
//
//        DefaultListModel<String> listOfClasses = new DefaultListModel<>();
//        int j=0;
//        for(int i=0; i<sorted.size(); i++)
//        {
//            j=Class.allClasses.indexOf(sorted.get(i));
//            listOfClasses.add(i, (j + 1) + "." + sorted.get(i).convertToString());
//        }

        DefaultListModel<Class> listOfClasses = DAOschool.getAllClassesDefaultListModelSORTED(sessionFactory);
        JList classesList = new JList(listOfClasses);

        sortedPanel.add(classesList);
        JOptionPane.showConfirmDialog(null, sortedPanel,
                "=Sorted list of classes=", JOptionPane.CLOSED_OPTION);
    }
    void search() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        JTextField searched = new JTextField(20);
        searchPanel.add(searched);

        int result = JOptionPane.showConfirmDialog(null, searchPanel,
                "=Type the surname you are looking for=", JOptionPane.OK_CANCEL_OPTION);
        if(result==JOptionPane.OK_OPTION)
        {
            JPanel foundPanel = new JPanel();
            foundPanel.setLayout(new BoxLayout(foundPanel, BoxLayout.Y_AXIS));

//            List<Student> found = new ArrayList<>();
//            for(int i=0; i<Student.allStudents.size(); i++)
//                if(Student.allStudents.get(i).surname.contains(searched.getText()))
//                    found.add(Student.allStudents.get(i));
            List<Student> found = DAOschool.getStudentsWithGivenSurname(sessionFactory, searched.getText());

            GenericTableClass genericClass = new GenericTableClass(found);
            JTable tableFound;
            tableFound = new JTable(genericClass);
            foundPanel.add(tableFound);
            JOptionPane.showConfirmDialog(null, foundPanel,
                    "=Found students=", JOptionPane.CLOSED_OPTION);
        }
    }
    //=================================
    void serializeID(String filename) {
        try
        {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(Student.idStatic);
            out.close();
            file.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    void deserializeID(String filename) {
        try
        {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
            Student.idStatic = (int) in.readObject();
            in.close();
            file.close();
        }
        catch(IOException |ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    void serializeData() {
        String filename = new String("serializedDataStudents.txt");
        try
        {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(Student.allStudents);

            filename = "serializedDataClasses.txt";
            file = new FileOutputStream(filename);
            out = new ObjectOutputStream(file);
            out.writeObject(Class.allClasses);

            out.close();
            file.close();
            JOptionPane.showConfirmDialog(null, "Serialization has been done correctly!", "Serialization succesful", JOptionPane.CLOSED_OPTION);

            serializeID("serializedID.txt");
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(menuFrame, "Something went wrong during serialization!", "Serialization failed", JOptionPane.WARNING_MESSAGE);
        }
    }
    void deserializeData() {
        String filename = new String("serializedDataStudents.txt");
        try
        {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
            Student.allStudents = (ArrayList) in.readObject();

            filename = "serializedDataClasses.txt";
            file = new FileInputStream(filename);
            in = new ObjectInputStream(file);
            Class.allClasses = (ArrayList) in.readObject();

            in.close();
            file.close();

            //CZY DA SIĘ SKRÓCIĆ TĘ PĘTLĘ? Xd
            for(int i=0; i<Class.allClasses.size(); i++)
            {
                for(int j=0; j<Class.allClasses.get(i).students.size(); j++)
                {
                    for(int k=0; k<Student.allStudents.size(); k++)
                    {
                        if (Class.allClasses.get(i).students.get(j).id == Student.allStudents.get(k).id)
                        {
                            Class.allClasses.get(i).students.remove(j);
                            Class.allClasses.get(i).addStudent(Student.allStudents.get(k));
                        }
                    }
                }
            }
            deserializeID("serializedID.txt");
        }
        catch(IOException |ClassNotFoundException e)
        {
            JOptionPane.showMessageDialog(menuFrame, "Something went wrong during deserialization!", "Deserialization failed", JOptionPane.WARNING_MESSAGE);
        }
    }
    void saveToCSV() {
        String filename = new String("csvStudents.csv");
        File csvOut = new File(filename);
        List<String> data = new ArrayList<>();

        ArrayList<Student> s = DAOschool.getAllStudentsArrayList(sessionFactory);
        try (PrintWriter pw = new PrintWriter(csvOut))
        {
            for (int i = 0; i < s.size(); i++)
            {
                data.add(s.get(i).name + "," + s.get(i).surname
                        + "," + s.get(i).birthYear + "," + s.get(i).points
                        + "," + s.get(i).studiesYear + "," + s.get(i).scholarship
                        + "," + s.get(i).id + ",");
            }
            data.stream().forEach(pw::println);
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(menuFrame, "Something went wrong during saving to .csv file!", "Saving to .csv failed", JOptionPane.WARNING_MESSAGE);
        }

        filename = new String("csvClasses.csv");
        csvOut = new File(filename);
        data = new ArrayList<>();

        ArrayList<Class> c = DAOschool.getAllClassesArrayList(sessionFactory);
        try(PrintWriter pw = new PrintWriter(csvOut))
        {
            for (int i = 0; i < c.size(); i++)
            {
                data.add(c.get(i).name + "," + c.get(i).limitOfStudents+ ",");
            }
            data.stream().forEach(pw::println);


            serializeID("serializedIDcsv.txt");
            JOptionPane.showConfirmDialog(null, "Saving to .csv file has been done correctly!", "Saving to .csv succesful", JOptionPane.CLOSED_OPTION);
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(menuFrame, "Something went wrong during saving to .csv file!", "Saving to .csv failed", JOptionPane.WARNING_MESSAGE);
        }
    }
    void readFromCSV() {
        try
        {
        String filename = new String("csvStudents.csv");
        File csvIn = new File(filename);
        FileReader reader = new FileReader(csvIn);
        LineNumberReader lnr = new LineNumberReader(reader);
        String line;
        List<String> data = new ArrayList<>();
        int counter;

        LineNumberReader getNumberOfLines = new LineNumberReader(new FileReader(new File(filename)));
        int numberOfLines = 0;
        while(getNumberOfLines.readLine()!=null) numberOfLines++;

        //while(lnr.readLine() != null)     //to czyta co drugie xD
        for(int k=0; k<numberOfLines; k++)
        {
            line = lnr.readLine();
            counter = 0;
            data.clear();
            for(int i=0; i<line.length(); i++)
            {
                if(line.charAt(i)==',')
                {
                    data.add(line.substring(counter, i));
                    counter = i+1;
                }
            }
            new Student(data.get(0), data.get(1), StudentCondition.Present,
                    Integer.parseInt(data.get(2)), Double.parseDouble(data.get(3)) ,
                    Integer.parseInt(data.get(4)), Boolean.parseBoolean(data.get(5)),
                    Integer.parseInt(data.get(6)));
        }

            filename = new String("csvClasses.csv");
            csvIn = new File(filename);
            reader = new FileReader(csvIn);
            lnr = new LineNumberReader(reader);
            data = new ArrayList<>();
            List<Integer> IDs = new ArrayList<>();
            Class c;

            getNumberOfLines = new LineNumberReader(new FileReader(new File(filename)));
            numberOfLines = 0;
            while(getNumberOfLines.readLine()!=null) numberOfLines++;

            for(int k=0; k<numberOfLines; k++)
            {
                line = lnr.readLine();
                counter = 0;
                data.clear();
                for(int i=0; i<line.length(); i++)
                {
                    if(line.charAt(i)==',')
                    {
                        data.add(line.substring(counter, i));
                        counter = i+1;
                    }
                }

                counter=0;
                IDs.clear();
                for(int i=0; i<data.get(2).length(); i++)
                {
                    if(data.get(2).charAt(i)=='|')
                    {
                        IDs.add(Integer.parseInt(data.get(2).substring(counter, i)));
                        counter = i+1;
                    }
                }

                c = new Class(data.get(0), Integer.parseInt(data.get(1)));
                for(int i=0; i<IDs.size(); i++)
                    for(int j=0; j<Student.allStudents.size(); j++)
                        if(Student.allStudents.get(j).id==IDs.get(i))
                            c.addStudent(Student.allStudents.get(j));
            }

            deserializeID("serializedIDcsv.txt");
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(menuFrame, "Something went wrong during reading from .csv file!", "Reading from .csv failed", JOptionPane.WARNING_MESSAGE);
        }
    }
    //=================================
    void setUpSessionFactory(){
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    //=================================
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent event)
        {
            String command = event.getActionCommand();
            if (command.equals("displayStudentsList"))
            {
                displayStudentsList();
            }
            else if (command.equals("displayClassesList"))
            {
                displayClassesList();
            }
            else if (command.equals("addStudent"))
            {
                addStudent();
            }
            else if (command.equals("editStudent"))
            {
                editStudent();
            }
            else if (command.equals("removeStudent"))
            {
                removeStudent();
            }
            else if (command.equals("backToMainMenu"))
            {
                backToMainMenu();
            }
            else if (command.equals("addStudentToClass"))
            {
                addStudentToClass();
            }
            else if (command.equals("removeStudentFromClass"))
            {
                removeStudentFromClass();
            }
            else if (command.equals("removeClass"))
            {
                removeClass();
            }
            else if (command.equals("addClass"))
            {
                addClass();
            }
            else if (command.equals("editClass"))
            {
                editClass();
            }
            else if (command.equals("sortStudents"))
            {
                sortStudents();
            }
            else if (command.equals("sortClasses"))
            {
                sortClasses();
            }
            else if (command.equals("searchStudents"))
            {
                search();
            }
            else if (command.equals("serializeData"))
            {
                serializeData();
            }
            else if (command.equals("saveToCSV"))
            {
                saveToCSV();
            }
        }
    }

}
