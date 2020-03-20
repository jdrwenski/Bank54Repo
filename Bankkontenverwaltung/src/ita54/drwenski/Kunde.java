package ita54.drwenski;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Kunde {
	@Id
	private int kundenNr;
	private String nachname;
	
	/* Default-Konstruktor */
	public Kunde() {}

	/* Source > 
	 * Generate Getters and Setters */
	
	public int getKundenNr() {
		return kundenNr;
	}

	public void setKundenNr(int kundenNr) {
		this.kundenNr = kundenNr;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

}
