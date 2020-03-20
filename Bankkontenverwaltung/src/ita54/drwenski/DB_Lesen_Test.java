package ita54.drwenski;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DB_Lesen_Test {

	public static void main(String[] args) throws Exception {
		/* DB-Verbindung herstellen, WARNINGs ignorieren! */
		Logger.getLogger("").setLevel(Level.OFF);
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Bank54DB");
		EntityManager manager = factory.createEntityManager();
		
		/* Kunden anhand Kundennummer auslesen */
		Kunde kunde = manager.find(Kunde.class,12345678);
		System.out.println(kunde.getNachname());

		/* DB-Verbindung sauber schlieﬂen, 
		 * da Eclipse sonst irgendwann immer langsamer wird! */
		manager.close();
		factory.close();
	}

}
