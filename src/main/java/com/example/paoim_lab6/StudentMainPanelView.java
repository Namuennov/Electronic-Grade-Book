package com.example.paoim_lab6;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentMainPanelView {

    public void currentSubjectsButtonOnClick()
    {
        ProgramFX.screenController.activate("currentSubjectPanel-view");
    }

    public void enrollButtonOnClick()
    {
        ProgramFX.screenController.activate("enrollingPanel-view");
    }

    public void changeDataButtonOnClick()
    {
        ProgramFX.screenController.activate("changeDataPanel-view");
    }


}
