package com.example.paoim_lab6;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChangeDataPanelView implements Initializable {

    public ChoiceBox<StudentCondition> statesList;
    public ObservableList<StudentCondition> statesListItems = FXCollections.observableArrayList(StudentCondition.class.getEnumConstants());
    public TextField nameField;
    public TextField surnameField;
    public Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        statesList.setItems(statesListItems);
        statesList.getSelectionModel().select(ProgramFX.loggedStudent.state);
        nameField.setText(ProgramFX.loggedStudent.name);
        surnameField.setText(ProgramFX.loggedStudent.surname);
    }

    public void onApplyButtonClick()
    {
        try
        {
            if (nameField.getText().trim().isEmpty() || surnameField.getText().trim().isEmpty()) {
                errorLabel.setText("Some data is missing!");
                return;
            }
            ProgramFX.loggedStudent.name = nameField.getText();
            ProgramFX.loggedStudent.surname = surnameField.getText();
            ProgramFX.loggedStudent.state = statesList.getSelectionModel().getSelectedItem();
            ProgramFX.screenController.removeScreen("changeDataPanel-view");
            ProgramFX.screenController.addScreen("changeDataPanel-view", FXMLLoader.load(getClass().getResource("changeDataPanel-view.fxml")));
            ProgramFX.screenController.activate("changeDataPanel-view");
        }
        catch(Exception e)
        {
            e.printStackTrace();
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
