package persistence;

import java.io.Closeable;
import java.util.List;

import fachlogik.Patient;
import fachlogik.Personal;
import fachlogik.Untersuchung;

public interface IExaminationDao extends Closeable {

	// Auslesen von allen Untersuchungen
	public List<Untersuchung> allExaminations();
	
	// Auslesen von allen Untersuchungen des aktuellen Jahres
	public List<Untersuchung> allActualExaminations();
	
	// Einfügen einer neuen Untersuchung
	public void newExamination(Untersuchung u);
	
	// Ändern einer Untersuchung
	public void editExamination(Untersuchung u);
	
	// Löschen einer Untersuchung
	public void deleteExamination(Untersuchung u);

	// Auslesen von allen Patienten
	public List<Patient> allPatients();
	
	// Einfügen eines neuen Patienten
	public void newPatient(Patient p);
	
	// Ändern eines Patienten
	public void editPatient(Patient p);
	
	// Löschen eines Patienten
	public void deletePatient(Patient p);

	// Auslesen des gesamten Personals
	public List<Personal> allPersons();
	
	// Auslesen des volljährigen Personals
	public List<Personal> allOldPersons();
	
	// Einfügen von neuem Personal
	public void newPerson(Personal p);
	
	// Ändern von Personal
	public void editPerson(Personal p);
	
	// Löschen von Personal
	public void deletePerson(Personal p);

}
