package ita54.drwenski;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Konto {
	@Id
	private String KontoNr;
	private BigDecimal guthaben;
	@ManyToOne	/* aus Sicht des Kontos handelt es sich um eine N:1-Beziehung */
	@JoinColumn(name="InhaberNr")	/* Spaltenname aus der DB */
	private Kunde kunde;
	
	/* Default-Konstruktor */
	public Konto() {}

	/* Source > 
	 * Generate Getters and Setters */
	
	public String getKontoNr() {
		return KontoNr;
	}

	public void setKontoNr(String kontoNr) {
		KontoNr = kontoNr;
	}

	public BigDecimal getGuthaben() {
		return guthaben;
	}

	public void setGuthaben(BigDecimal guthaben) {
		this.guthaben = guthaben;
	}

	public Kunde getKunde() {
		return kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}
		
}
