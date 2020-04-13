package fachlogik;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "u_persons")
@NamedQueries({
		@NamedQuery(name="Person.findAll", query="SELECT per FROM Person per"),
		@NamedQuery(name="Person.byBirth", query="SELECT per FROM Person per "
				+ "WHERE per.geburtsdatum >= :dateFrom AND per.geburtsdatum <= :dateTo")
})
@Access(AccessType.FIELD)
public class Person implements Serializable
{
    @Id
	private long svnr;
    //private LocalDate geburtsdatum;
	// Implementation SQLite:
    private String geburtsdatum;
    @Transient
	private StringProperty vorname;
    @Transient
	private StringProperty nachname;
	private Geschlecht geschlecht;
	private String adresse;

    public Person() {
        this.vorname = new SimpleStringProperty();
        this.nachname = new SimpleStringProperty();
    }

    public Person(long svnr, String geburtsdatum, String vorname, String nachname, Geschlecht geschlecht, String adresse) {
    	this.svnr = svnr;
    	//this.geburtsdatum = LocalDate.parse(geburtsdatum, DateTimeFormatter.ISO_LOCAL_DATE);
    	// Implementation SQLite:
    	this.geburtsdatum = geburtsdatum;
        this.vorname = new SimpleStringProperty(vorname);
        this.nachname = new SimpleStringProperty(nachname);
    	this.geschlecht = geschlecht;
        this.adresse = adresse;
    }
	
	@Override
	public String toString()
	{
		return this.getNachname()+" "+this.getVorname();
	}


	public StringProperty getVornameProperty()
	{
		return vorname;
	}

	public StringProperty getNachnameProperty()
	{
		return nachname;
	}

	public long getSvnr()
	{
		return svnr;
	}

	public LocalDate getGeburtsdatum()
	{
		//return geburtsdatum;
		// Implementation SQLite:
		return LocalDate.parse(geburtsdatum, DateTimeFormatter.ofPattern("dd.MM.yyyy"));// DateTimeFormatter.ISO_LOCAL_DATE);
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "vorname_text")
	public String getVorname()
	{
		return vorname.get();
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "nachname_text")
	public String getNachname()
	{
		return nachname.get();
	}

	public Geschlecht getGeschlecht()
	{
		return geschlecht;
	}

	public String getAdresse()
	{
		return adresse;
	}

	public void setSvnr(long svnr)
	{
		this.svnr = svnr;
	}

	public void setGeburtsdatum(LocalDate geburtsdatum)
	{
		//this.geburtsdatum = geburtsdatum;
		// Implementation SQLite:
		//this.geburtsdatum = geburtsdatum.toString();
		this.geburtsdatum = geburtsdatum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
	}

	public void setVorname(String vorname)
	{
		this.vorname.set(vorname);
	}

	public void setNachname(String nachname)
	{
		this.nachname.set(nachname);
	}

	public void setGeschlecht(Geschlecht geschlecht)
	{
		this.geschlecht = geschlecht;
	}

	public void setAdresse(String adresse)
	{
		this.adresse = adresse;
	}
}
