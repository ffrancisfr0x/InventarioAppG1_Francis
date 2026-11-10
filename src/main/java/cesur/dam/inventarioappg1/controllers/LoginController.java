package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.SceneNavigator;
import cesur.dam.inventarioappg1.data.DataSourceFactory;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @javafx.fxml.FXML
    public void login(ActionEvent actionEvent) {
        SceneNavigator.navigateTo(SceneNavigator.View.MAIN);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
