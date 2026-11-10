package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.SceneNavigator;
import cesur.dam.inventarioappg1.data.DataSourceFactory;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import cesur.dam.inventarioappg1.models.Equipo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateController implements Initializable {

    @FXML
    private Label welcomeText;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<String> comboCategory;
    @FXML
    private CheckBox chkAvailable;


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*
            ChoiceBox  \
            ListView    \ Observable .getItems() .setItems()
            TableView   /

         */

        comboCategory.getItems().addAll("Keyboard", "Laptop", "Gamepad", "Monitor", "Computer", "Printer");
    }

    /**
     * Guarda los datos introducidos en el formulario.
     * <p>
     * Comprueba que los campos de id, nombre, categoría y disponible no estén vacíos.
     * Si todos tienen contenido, muestra una alerta informativa con el nombre del
     * artículo, limpia los campos y deja el formulario listo para un nuevo registro.
     * Si algún campo está vacío, no realiza ninguna acción.
     * </p>
     *
     * @param actionEvent evento de acción generado al pulsar el botón guardar
     */
    @FXML
    public void save(ActionEvent actionEvent) {
        if (!isValid()) {
            return;
        }
        Equipo equipo = new Equipo();
        equipo.setNombre(txtName.getText().trim());
        equipo.setCategoria(comboCategory.getValue());
        equipo.setDisponible(chkAvailable.isSelected());

        try {
            EquipoJdbcDAO dao = new EquipoJdbcDAO(DataSourceFactory.getDataSource());
            dao.save(equipo);
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha podido guardar el equipo");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guardado");
        alert.setHeaderText("Equipo guardado correctamente");
        alert.setContentText("Id " + equipo.getId() + " | " + equipo.getNombre() + " | " + equipo.getCategoria());
        alert.showAndWait();

        SceneNavigator.navigateTo(SceneNavigator.View.MAIN);
    }

    /**
     * Comprueba si alguno de los campos del formulario está vacío.
     *
     * @return {@code true} si al menos uno de los campos id, nombre, categoría
     *         o disponible está vacío; {@code false} en caso contrario
     */
    private boolean isValid() {
        List<String> errors = new ArrayList<>();
        if (txtName.getText() == null || txtName.getText().isBlank()) {
            errors.add("- El nombre es obligatorio.");
        }
        if (comboCategory.getValue() == null) {
            errors.add("- Selecciona una categoría.");
        }
        if (!errors.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Datos incorrectos");
            alert.setHeaderText("Revisa el formulario");
            alert.setContentText(String.join("\n", errors));
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Cierra la aplicación.
     * <p>
     * Se invoca al pulsar el botón volver y finaliza el proceso con
     * {@link System#exit(int)} usando el código de salida {@code 0}.
     * </p>
     *
     * @param actionEvent evento de acción generado al pulsar el botón volver
     */
    @FXML
    public void goBack(ActionEvent actionEvent) {
        SceneNavigator.navigateTo("login-view.fxml","Login");
    }
}
