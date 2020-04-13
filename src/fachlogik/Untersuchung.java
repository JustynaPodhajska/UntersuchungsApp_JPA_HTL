package fachlogik;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.Serializable;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;


@Entity
@Table(name = "u_untersuchungens")
@NamedQuery(name = "Untersuchung.findAll", query = "SELECT u from Untersuchung u")
@Access(AccessType.FIELD)

public class Untersuchung implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Transient
	private StringProperty bezeichnung = new SimpleStringProperty();
	@Transient
	private ObjectProperty<LocalDateTime> beginn;
	@Transient
	private ObjectProperty<LocalDateTime> ende;
    @ManyToOne
	@JoinColumn(name = "patient_nr")
	private Patient patient;
	@Transient
	private StringProperty vorname = new SimpleStringProperty();
	@Transient
	private StringProperty nachname = new SimpleStringProperty();
	private String kontrastmittel;
	private BigDecimal mengeKM;
	@ManyToMany
	private List<Personal> personal;
	
	public Untersuchung()
	{
		beginn = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.now());
		ende = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.now());
		vorname = new SimpleStringProperty();
		nachname = new SimpleStringProperty();
	}

	public BigDecimal dauer() 
	{
		return new BigDecimal(Duration.between(beginn.get(), ende.get()).toMinutes());
	}

//	public LocalDateTime getBeginn()
//	{
//		return beginn.get();
//	}
//
//	public LocalDateTime getEnde()
//	{
//		return ende.get();
//	}
//
//	public void setBeginn(LocalDateTime beginn)
//	{
//		this.beginn.set(beginn);
//	}
//
//	public void setEnde(LocalDateTime ende)
//	{
//		this.ende.set(ende);
//	}

	// Implementation SQLite:
	public String getBeginn()
	{
		return beginn.get().toString();
	}

	public String getEnde()
	{
		return ende.get().toString()	;
	}

	public void setBeginn(String beginn)
	{
		this.beginn.set(LocalDateTime.parse(beginn, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}

	public void setEnde(String ende)
	{
		this.ende.set(LocalDateTime.parse(ende, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}

	public ObjectProperty<LocalDateTime> getBeginnProperty()
	{
		return beginn;
	}

	public ObjectProperty<LocalDateTime> getEndeProperty()
	{
		return ende;
	}

	public void setKm(String km)
	{
		this.kontrastmittel = km;
	}

	public Patient getPatient()
	{
		return patient;
	}

	public void setPatient(Patient patient)
	{
		this.patient = patient;
		vorname.set(patient.getVorname());
		nachname.set(patient.getNachname());
	}

	public StringProperty getVornameProperty() {
		return vorname;
	}


	@Access(AccessType.PROPERTY)
	@Column(name = "vorname_text")
	public String getVorname(){
		return vorname.get();
	}
	public void setVorname(String vorname){
		this.vorname.setValue(vorname);
	}

	public StringProperty getNachnameProperty() {
		return nachname;
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "nachname_text")
	public String getNachname(){
		return nachname.get();
	}
	public void setNachname(String nachname){
		this.nachname.setValue(nachname);
	}

	public BigDecimal getMengeKM()
	{
		return mengeKM;
	}

	public void setMengeKM(String mengeKM)
	{
		this.mengeKM = new BigDecimal(mengeKM);
	}

	public String getKm()
	{
		return kontrastmittel;
	}


	@Access(AccessType.PROPERTY)
	@Column(name = "bezeichnung_text")
	public String getBezeichnung()
	{
		return bezeichnung.get();
	}

	public StringProperty getBezeichnungProperty()
	{
		return bezeichnung;
	}

	public void setBezeichnung(String name)
	{
		this.bezeichnung.setValue(name);
	}

	public List<Personal> getPersonal() {
		return personal;
	}

	public void setPersonal(List<Personal> personal) {
		this.personal = personal;
	}
}
