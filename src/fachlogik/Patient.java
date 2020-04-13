package fachlogik;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "u_patients")
@NamedQuery(name="Patient.findAll", query="SELECT p FROM Patient p")
public class Patient extends Person// implements Serializable
{

	private String krankenkasse;

    public Patient() {
        super();
    }

    public Patient(long svnr, String geburtsdatum, String vorname, String nachname, Geschlecht geschlecht, String adresse, String krankenkasse) {
    	super(svnr, geburtsdatum, vorname, nachname, geschlecht, adresse);
    	setKrankenkasse(krankenkasse);
    }
	
	public String getKrankenkasse()
	{
		return this.krankenkasse;
	}

	public void setKrankenkasse(String krankenkasse)
	{
		this.krankenkasse = krankenkasse;
	}

	@Override
	public String toString() {
		return this.getNachname()+" "+this.getVorname();
	}
}
