package net.erickpineda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Proyecto que hace uso del Framework JPA para la plataforma Java EE, con la
 * tecnología JavaFX sobre Gradle.
 * 
 * No es más que un programa que permite crear satélites y planetas del
 * universo, persistirlos en una BDD y poder luego desplegar su información
 * almacenada.
 * 
 * @author Erick Pineda
 *
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(this.getClass()
					.getResource("views/Astrologia.fxml"));

			Scene scene = new Scene(root, 630, 550);

			scene.getStylesheets().add(
					getClass().getResource("content/css/application.css")
							.toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setTitle("¡Astrologia!");

			primaryStage.getIcons().add(
					new Image(getClass().getResource(
							"content/images/mantis.png").toExternalForm()));

			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
