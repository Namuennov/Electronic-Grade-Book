package com.example.paoim_lab6;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgramFXController implements Initializable {

    public ObservableList<Student> loginListItems = FXCollections.observableArrayList(Student.allStudents);

    public ListView<Student> loginList;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        try
        {
            ProgramFX.loggedStudent = loginList.getSelectionModel().getSelectedItem();
            ProgramFX.screenController.addScreen("studentMainPanel-view", FXMLLoader.load(getClass().getResource( "studentMainPanel-view.fxml" )));
            ProgramFX.screenController.addScreen("currentSubjectPanel-view", FXMLLoader.load(getClass().getResource( "currentSubjectPanel-view.fxml" )));
            ProgramFX.screenController.addScreen("enrollingPanel-view", FXMLLoader.load(getClass().getResource( "enrollingPanel-view.fxml" )));
            ProgramFX.screenController.addScreen("changeDataPanel-view", FXMLLoader.load(getClass().getResource( "changeDataPanel-view.fxml" )));
            ProgramFX.screenController.activate("studentMainPanel-view");
        }
        catch (Exception e)
        {
            welcomeText.setText("No one has been chosen!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        loginList.setItems(loginListItems);
    }

}
