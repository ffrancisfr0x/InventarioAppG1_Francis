package cesur.dam.inventarioappg1;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador de navegación de la aplicación.
 * <p>
 * Guarda una referencia al {@link Stage} principal y cambia la escena que se
 * muestra en él cada vez que se solicita una vista nueva. Todos sus miembros son
 * estáticos, de modo que puede usarse desde cualquier controlador sin necesidad
 * de crear una instancia.
 * </p>
 */
public class SceneNavigator {

    /**
     * Vistas disponibles en la aplicación, con su fichero FXML y el título de la ventana.
     */
    public enum View {
        LOGIN("login-view.fxml", "Login"),
        MAIN("main-view.fxml", "Inventario de equipos"),
        CREATE("create-view.fxml", "Nuevo equipo");

        private final String fxml;
        private final String title;

        View(String fxml, String title) {
            this.fxml = fxml;
            this.title = title;
        }

        public String getFxml() {
            return fxml;
        }

        public String getTitle() {
            return title;
        }
    }

    /**
     * Stage principal de la aplicación sobre el que se muestran las escenas.
     */
    private static Stage stage;

    /**
     * Registra el stage que se usará en las siguientes navegaciones.
     *
     * @param stage {@link Stage} principal de la aplicación
     */
    public static void setStage(Stage stage) {
        SceneNavigator.stage = stage;
    }

    /**
     * Muestra la vista indicada en el stage principal.
     *
     * @param view vista a la que se quiere navegar
     */
    public static void navigateTo(View view) {
        navigateTo(view.getFxml(), view.getTitle());
    }

    /**
     * Carga la vista FXML indicada y la muestra en el stage registrado.
     * <p>
     * El fichero FXML se busca dentro del paquete {@code views/} de {@link AppG1}.
     * Cada navegación crea una escena nueva, así que el controlador de la vista
     * se vuelve a inicializar (por ejemplo, la tabla de la vista main se recarga
     * desde la base de datos al volver a ella).
     * </p>
     *
     * @param fxml  nombre del fichero FXML (dentro de {@code views/})
     * @param title título que se pondrá a la ventana
     * @throws RuntimeException si no se puede cargar el FXML (envuelve la {@link IOException})
     */
    public static void navigateTo(String fxml, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader(AppG1.class.getResource("views/" + fxml));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle(title);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
