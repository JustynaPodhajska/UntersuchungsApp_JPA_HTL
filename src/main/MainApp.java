package main;

import java.io.IOException;
import java.util.List;

import fachlogik.Patient;
import fachlogik.Person;
import fachlogik.Personal;
import fachlogik.Untersuchung;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.ExaminationEditDialogController;
import view.ExaminationOverviewController;
import view.PersonEditDialogController;

import javax.persistence.*;

public class MainApp extends Application {

    public Stage primaryStage;
    private ExaminationOverviewController examinationsController;
    private ObservableList<Patient> patientData = FXCollections.observableArrayList();
    private ObservableList<Personal> personalData = FXCollections.observableArrayList();
    private ObservableList<Untersuchung> examinationData = FXCollections.observableArrayList();


    private EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("jpaUntersuchungen");
    private EntityManager em = emfactory.createEntityManager();

	public MainApp() {

	    TypedQuery<Patient> queryPatient = em.createNamedQuery("Patient.findAll", Patient.class);
        List<Patient> patientList = queryPatient.getResultList();
        patientList.forEach(p -> patientData.add(p));

        TypedQuery<Personal> queryPersonal = em.createNamedQuery("Personal.findAll", Personal.class);
        List<Personal> personalList = queryPersonal.getResultList();
        personalList.forEach(p -> personalData.add(p));

        TypedQuery<Untersuchung> queryUntesuchung = em.createNamedQuery("Untersuchung.findAll", Untersuchung.class);
        List<Untersuchung> untersuchungList = queryUntesuchung.getResultList();
        untersuchungList.forEach(u -> examinationData.add(u));





	/*	// test data
	    patientData.add(new Patient(1234, "01.01.1980", "Thomas", "Maier", Geschlecht.MAENNLICH, "1010 Wien, Stephansplatz 13", "WGKK"));
	    patientData.add(new Patient(4321, "01.01.1980", "Margit", "Schmidt", Geschlecht.WEIBLICH, "1050 Wien, Spengergasse 27", "BVA"));
	    personData.add(new Personal(5678, "01.01.1980", "Hans", "Lang", Geschlecht.MAENNLICH, "1010 Wien, Stephansplatz 1", 0));
	    personData.add(new Personal(8765, "01.01.1980", "Ruth", "Kurz", Geschlecht.WEIBLICH, "1050 Wien, Spengergasse 20", 0));

		Ultraschall u = new Ultraschall();
		u.setPatient(patientData.get(0));
		//u.setBeginn(LocalDateTime.of(2017,11,21,7,15));
		//u.setEnde(LocalDateTime.of(2017,11,21,7,25));
        u.setBeginn("2017-11-21T07:15:00");
        u.setEnde("2017-11-21T07:25:00");
		examinationData.add(u);

		MagnetResonanz m1 = new MagnetResonanz();
		m1.setPatient(patientData.get(1));
		m1.setKm("Artirem Injektionslösung");
		m1.setMengeKM("3");
		m1.setBeginn("2017-11-21T10:23:00");
		m1.setEnde("2017-11-21T10:51:00");
		examinationData.add(m1);*/

	}
	
	public ObservableList<Patient> getPatientData() {
		return patientData;
	}

	public ObservableList<Personal> getPersonalData() {
	    return personalData;
	}

	public ObservableList<Untersuchung> getExaminationData() {
	    return examinationData;
	}

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("UntersuchungsApp");

        showExaminationOverview();
    }

    @Override
    public void stop()
    {
        em.close();
        emfactory.close();
    	this.primaryStage.close();
    }

    public void showExaminationOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/ExaminationOverview.fxml"));
            TabPane examinationOverview = (TabPane) loader.load();

            examinationsController = loader.getController();
            examinationsController.setMainApp(this);

            primaryStage.setScene(new Scene(examinationOverview));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean showPersonEditDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Person editieren");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showExaminationEditDialog(Untersuchung exam) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/ExaminationEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Untersuchung editieren");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ExaminationEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            controller.setExamination(exam);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

	public ExaminationOverviewController getExaminationsController()
	{
		return examinationsController;
	}



    public EntityManagerFactory getEmfactory() {
        return emfactory;
    }

    /* public void setEmfactory(EntityManagerFactory emfactory) {
         this.emfactory = emfactory;
     }
 */
    public EntityManager getEm() {
        return em;
    }

 /*   public void setEm(EntityManager em) {
        this.em = em;
    }*/


    public void DBspeichern(Object o){
	    em.getTransaction().begin();
	    em.persist(o);
        em.getTransaction().commit();
    }

    public void DBloeschen(Object o){
        em.getTransaction().begin();
        em.remove(o);
        em.getTransaction().commit();
    }

}