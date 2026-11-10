package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.data.DataSourceFactory;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import cesur.dam.inventarioappg1.models.Equipo;
import cesur.dam.inventarioappg1.SceneNavigator;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableColumn<Equipo,String> cAvailability;
    @FXML
    private TableView<Equipo> table;
    @FXML
    private TableColumn<Equipo,String> cCategory;
    @FXML
    private TableColumn<Equipo,String> cName;
    @FXML
    private TableColumn<Equipo,String> cId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* Inicialización de la tabla */
        cId.setCellValueFactory( (row)->{
            Equipo equipment = row.getValue();
            Long  id = equipment.getId();
            return new SimpleStringProperty(String.valueOf(id));
        });
        cName.setCellValueFactory((row) -> {
            String nombre = row.getValue().getNombre();
            return new SimpleStringProperty(nombre == null || nombre.isBlank() ? "--" : nombre);
        });
        cCategory.setCellValueFactory((row) -> {
            String categoria = row.getValue().getCategoria();
            return new SimpleStringProperty(categoria == null ? "--" : categoria.toUpperCase());
        });
        cAvailability.setCellValueFactory( (row)->{
            var availability = row.getValue().getDisponible();
            String output = availability? "disponible":"no disponible";
            return new SimpleStringProperty(output);
        });

        loadData();
    }

    private void loadData() {
        try {
            EquipoJdbcDAO dao = new EquipoJdbcDAO(DataSourceFactory.getDataSource());
            table.getItems().setAll(dao.findAll());
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se han podido cargar los equipos");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        SceneNavigator.navigateTo(SceneNavigator.View.LOGIN);
    }

    @FXML
    public void addNew(ActionEvent actionEvent) {
        SceneNavigator.navigateTo(SceneNavigator.View.CREATE);
    }
}
