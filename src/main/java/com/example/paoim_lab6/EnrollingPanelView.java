package com.example.paoim_lab6;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EnrollingPanelView implements Initializable {
    public Label enrollText;
    public ComboBox<Class> comboSubjects;
    public ObservableList<Class> comboSubjectsItems;

    private void initializeSubjectsItems()
    {
        List<Class> subjectsItems = new ArrayList<>();
        for(int i=0; i<Class.allClasses.size(); i++)
            if(!Class.allClasses.get(i).students.contains(ProgramFX.loggedStudent))
                subjectsItems.add(Class.allClasses.get(i));
        comboSubjectsItems = FXCollections.observableArrayList(subjectsItems);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        initializeSubjectsItems();
        comboSubjects.setItems(comboSubjectsItems);
    }

    public void onEnrollButtonClick()
    {
        try
        {
            comboSubjects.getSelectionModel().getSelectedItem().addStudent(ProgramFX.loggedStudent);
            ProgramFX.screenController.removeScreen("enrollingPanel-view");
            ProgramFX.screenController.addScreen("enrollingPanel-view", FXMLLoader.load(getClass().getResource( "enrollingPanel-view.fxml" )));
            ProgramFX.screenController.activate("enrollingPanel-view");
        }
        catch (Exception e)
        {
            enrollText.setText("No subject has been chosen!");
        }
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
