package ita54.drwenski;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gui extends Application {
	/* DB-Verbindung */
	EntityManagerFactory factory = 
			Persistence.createEntityManagerFactory("Bank54DB");
	EntityManager manager = factory.createEntityManager();
	
	/* Layouts */
	VBox root = new VBox();
	GridPane grid = new GridPane();
	
	/* Grafische Komponenten */
	Button ok = new Button("Suche");
	TextField kdnrFeld = new TextField();
	Label nameAusgabe = new Label();
	
	
	@Override
	public void start(Stage stage) {
		/* Gitter mit Komponenten füllen */
		grid.add(new Label("KundenNr"), 0, 0);
		grid.add(kdnrFeld, 1, 0);
		grid.add(ok, 2, 0);
		grid.add(new Label("Name:"), 0, 1);
		grid.add(nameAusgabe, 1, 1);
		
		/* Button mit handle()-Methode verknüpfen */
		ok.setOnAction(event -> handle(event));
		
		root.getChildren().add(grid);
		Scene scene = new Scene(root,600,400);
		stage.setTitle("Kontenübersicht");
		stage.setScene(scene);
		stage.show();
	}
	
	
	/* Ereignisbehandlung für Button "Suche" */
	public void handle(ActionEvent event) {
		String eingabe = kdnrFeld.getText();
		int nummer = Integer.parseInt(eingabe);
		Kunde kunde = manager.find(Kunde.class, nummer);
		nameAusgabe.setText(kunde.getNachname());
	}
	
	
	@Override
	/* automatisches Schließen der DB-Verbindung */
	public void stop() {
		manager.close();
		factory.close();
	}
	
	
	public static void main(String[] args) {
		Logger.getLogger("").setLevel(Level.OFF);
		launch(args);
	}

}
