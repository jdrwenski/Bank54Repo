package ita54.drwenski;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiExtended extends Application {
	/* DB-Verbindung */
	EntityManagerFactory factory = 
			Persistence.createEntityManagerFactory("Bank54DB");
	EntityManager manager = factory.createEntityManager();
	
	/* Layouts */
	VBox root = new VBox();
	GridPane grid = new GridPane();
	
	/* Grafische Komponenten */
	Button ok = new Button("Suchen");
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
		
		/* Im Folgenden werden einfach ALLE Konten aus der DB abgefragt
		 * und danach in einer Schleife geprüft, welche Konten zum angegebenen
		 * Kunden gehören. Das ist nicht sehr effizient (warum?), aber würde
		 * für eine praktische Prüfung schon ausreichen! Wie können Sie die Abfrage
		 * und Anzeige der Kontoinformationen eleganter gestalten?
		 */
		int counter = 0; /* Zählvariable für Konten des Kunden */
		Query query = manager.createQuery("from Konto");
		ArrayList<Konto> konten = (ArrayList<Konto>)query.getResultList();
		for (Konto konto : konten) {
			if (konto.getKunde() == kunde) {
				String anzeige = "Konto "+konto.getKontoNr()+":  "+konto.getGuthaben()+" EUR";
				grid.add(new Label(anzeige), 0, counter+2, 3, 1);
				counter++;
			}
		}
		
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
