package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.data.DataSourceFactory;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import cesur.dam.inventarioappg1.models.Equipo;
import cesur.dam.inventarioappg1.SceneNavigator;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        cName.setCellValueFactory( (row)->{
            if(row.getValue().getNombre().isBlank()){
                return new SimpleStringProperty("--");
            } else  {
                return new SimpleStringProperty(String.valueOf(row.getValue().getNombre()));
            }
        });
        cCategory.setCellValueFactory( (row)->{
            return new SimpleStringProperty(String.valueOf(row.getValue().getCategoria()).toUpperCase());
        });
        cAvailability.setCellValueFactory( (row)->{
            var availability = row.getValue().getDisponible();
            String output = availability? "disponible":"no disponible";
            return new SimpleStringProperty(output);
        });

        var dao = new EquipoJdbcDAO(DataSourceFactory.getDataSource());
        dao.findAll().forEach( (equipo)->{
            System.out.println(equipo);
            table.getItems().add(equipo);
        });
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
