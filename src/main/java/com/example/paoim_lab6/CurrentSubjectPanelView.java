package com.example.paoim_lab6;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class CurrentSubjectPanelView implements Initializable {


    public ListView<Class> subjectsList;
    public ObservableList<Class> subjectsListItems;
    public Label signOutLabel;
    public Label gradesLabel;
    private Class any = new Class("Any", Integer.MAX_VALUE, 1);

    private void initializeSubjectsItems()
    {
        List<Class> subjectsItems = new ArrayList<>();
        for(int i=0; i<Class.allClasses.size(); i++)
            if(Class.allClasses.get(i).students.contains(ProgramFX.loggedStudent))
                subjectsItems.add(Class.allClasses.get(i));
        subjectsItems.add(any);
        subjectsListItems = FXCollections.observableArrayList(subjectsItems);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        initializeSubjectsItems();
        subjectsList.setItems(subjectsListItems);
        subjectsList.setCellFactory(cell -> new ListCell<Class>() {
            final Tooltip tooltip = new Tooltip();
            @Override
            protected void updateItem(Class c, boolean empty) {
                super.updateItem(c, empty);
                if (c == null || empty) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(c.name);
                    if(c==any) tooltip.setText("Average from all subjects: " + ProgramFX.loggedStudent.globalAverage());
                    else tooltip.setText("Grades: " + ProgramFX.loggedStudent.grades.get(c).toString().substring(1, ProgramFX.loggedStudent.grades.get(c).toString().length()-1) + "\nAverage: " + ProgramFX.loggedStudent.average(c) + "\nStatus: enrolled to this subject");
                    setTooltip(tooltip);
                }
            }
        });
        subjectsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    if(subjectsList.getSelectionModel().getSelectedItem()==any) gradesLabel.setText("Average from all subjects: " + ProgramFX.loggedStudent.globalAverage());
                    else gradesLabel.setText("Subject: " + subjectsList.getSelectionModel().getSelectedItem().name + "\nGrades: " + ProgramFX.loggedStudent.grades.get(subjectsList.getSelectionModel().getSelectedItem()).toString().substring(1, ProgramFX.loggedStudent.grades.get(subjectsList.getSelectionModel().getSelectedItem()).toString().length()-1) + "\nAverage: " + ProgramFX.loggedStudent.average(subjectsList.getSelectionModel().getSelectedItem()) + "\nStatus: enrolled to this subject");
                }
            }
        });
    }

    public void onSignOutButtonClick()
    {
        try
        {
            if(subjectsList.getSelectionModel().getSelectedItem()==any) signOutLabel.setText("You can sign out from one subject!");
            else
            {
                subjectsList.getSelectionModel().getSelectedItem().students.remove(ProgramFX.loggedStudent);
                ProgramFX.screenController.removeScreen("currentSubjectPanel-view");
                ProgramFX.screenController.addScreen("currentSubjectPanel-view", FXMLLoader.load(getClass().getResource("currentSubjectPanel-view.fxml")));
                ProgramFX.screenController.activate("currentSubjectPanel-view");
            }
        }
        catch (Exception e)
        {
            signOutLabel.setText("No subject has been chosen!");
        }
    }

    public void onSortByNameButtonClick()
    {
        subjectsList.setItems(subjectsListItems.sorted());
    }

    public void onSortByGradeButtonClick()
    {
        List<Class> subjectsItems = new ArrayList<>();
        for(int i=0; i<Class.allClasses.size(); i++)
            if(Class.allClasses.get(i).students.contains(ProgramFX.loggedStudent))
                subjectsItems.add(Class.allClasses.get(i));
        subjectsItems.sort(new Comparator<Class>() {
            @Override
            public int compare(Class o1, Class o2) {
                return Float.compare(ProgramFX.loggedStudent.average(o1), ProgramFX.loggedStudent.average(o2));
            }
        });
        subjectsItems.add(any);
        subjectsListItems = FXCollections.observableArrayList(subjectsItems);
        subjectsList.setItems(subjectsListItems);
    }

    public void onBackButtonClick()
    {
        try
        {
            ProgramFX.screenController.removeScreen("currentSubjectPanel-view");
            ProgramFX.screenController.removeScreen("enrollingPanel-view");
            ProgramFX.screenController.removeScreen("changeDataPanel-view");
            ProgramFX.screenController.addScreen("currentSubjectPanel-view", FXMLLoader.load(getClass().getResource("currentSubjectPanel-view.fxml")));
            ProgramFX.screenController.addScreen("enrollingPanel-view", FXMLLoader.load(getClass().getResource("enrollingPanel-view.fxml")));
            ProgramFX.screenController.addScreen("changeDataPanel-view", FXMLLoader.load(getClass().getResource("changeDataPanel-view.fxml")));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        ProgramFX.screenController.activate("studentMainPanel-view");
    }
}
