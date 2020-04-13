package view;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import fachlogik.Patient;
import fachlogik.Personal;
import fachlogik.Untersuchung;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.MainApp;

public class ExaminationEditDialogController {

    @FXML
    private DatePicker dateField;
    @FXML
    private TextField startField;
    @FXML
    private TextField endField;
    @FXML
    private Label typeField;
    @FXML
    private ComboBox<Patient> patientField;
    @FXML
    private TextField mediumField;
    @FXML
    private TextField quantityField;
    @FXML
    private ListView<Personal> staffField;

    private Stage dialogStage;
    private Untersuchung examination;
    private boolean okClicked = false;
    private MainApp mainApp;

	@FXML
    private void initialize() {
		staffField.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

    public void setExamination(Untersuchung exam) {
        examination = exam;

        //LocalDateTime start = exam.getBeginn();
    	// Implementation SQLite:
        LocalDateTime start = LocalDateTime.parse(exam.getBeginn(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        dateField.setValue(start.toLocalDate());
        String startTime = "";
        if (start.getHour() < 10) startTime += "0";
        startTime += start.getHour()+":"; 
        if (start.getMinute() < 10) startTime += "0";
        startTime += start.getMinute(); 
        startField.setText(startTime);
        //LocalDateTime end = exam.getEnde();
    	// Implementation SQLite:
        LocalDateTime end = LocalDateTime.parse(exam.getEnde(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String endTime = "";
        if (end.getHour() < 10) endTime += "0";
        endTime += end.getHour()+":"; 
        if (end.getMinute() < 10) endTime += "0";
        endTime += end.getMinute(); 
        endField.setText(endTime);
        typeField.setText(exam.getBezeichnung());
        patientField.setItems(mainApp.getPatientData());
        if (exam.getPatient() != null) patientField.setValue(exam.getPatient());
    	if (examination.getKm()!=null)
    		mediumField.setText(exam.getKm());
    	else
    		mediumField.setText("");
    	if (examination.getMengeKM()!=null)
    		quantityField.setText(exam.getMengeKM().toString());
    	else
        	quantityField.setText("");
        staffField.setItems(mainApp.getPersonalData());
        if (exam.getPersonal() != null)
        	for (Personal p:exam.getPersonal())
        		staffField.getSelectionModel().select(p);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
	    	//examination.setBeginn(LocalDateTime.of(dateField.getValue().getYear(),dateField.getValue().getMonth(),dateField.getValue().getDayOfMonth(),Integer.parseInt(startField.getText(0, 2)),Integer.parseInt(startField.getText(3, 5))));
	    	//examination.setEnde(LocalDateTime.of(dateField.getValue().getYear(),dateField.getValue().getMonth(),dateField.getValue().getDayOfMonth(),Integer.parseInt(endField.getText(0, 2)),Integer.parseInt(endField.getText(3, 5))));
        	// Implementation SQLite:
	    	examination.setBeginn(LocalDateTime.of(dateField.getValue().getYear(),dateField.getValue().getMonth(),dateField.getValue().getDayOfMonth(),Integer.parseInt(startField.getText(0, 2)),Integer.parseInt(startField.getText(3, 5))).toString());
	    	examination.setEnde(LocalDateTime.of(dateField.getValue().getYear(),dateField.getValue().getMonth(),dateField.getValue().getDayOfMonth(),Integer.parseInt(endField.getText(0, 2)),Integer.parseInt(endField.getText(3, 5))).toString());
	    	if (patientField.getValue() != null) examination.setPatient(patientField.getValue());
	    	examination.setKm(mediumField.getText());
	        if (quantityField.getText() != null && quantityField.getText().length() != 0)
	        	examination.setMengeKM(quantityField.getText());
	    	examination.setPersonal(staffField.getSelectionModel().getSelectedItems());
	
	        okClicked = true;
	        dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (dateField.getValue() == null) {
            errorMessage += "No valid date!\n"; 
        }
        if (startField.getText() == null || startField.getText().length() == 0) {
            errorMessage += "No valid begin!\n"; 
        } else {
            try {
            	Integer.parseInt(startField.getText(0, 2));
            	Integer.parseInt(startField.getText(3, 5));
            } catch (NumberFormatException e) {
                errorMessage += "No valid begin - format HH:MM must be used (for example '09:05')!\n"; 
            }
        }
        if (endField.getText() == null || endField.getText().length() == 0) {
            errorMessage += "No valid end!\n"; 
        } else {
            try {
            	Integer.parseInt(endField.getText(0, 2));
            	Integer.parseInt(endField.getText(3, 5));
            } catch (NumberFormatException e) {
                errorMessage += "No valid end - format HH:MM must be used (for example '09:05')!\n"; 
            }
        }
        if (patientField.getValue() == null) {
            errorMessage += "No valid patient!\n"; 
        }
        if (quantityField.getText() != null && quantityField.getText().length() != 0) {
            try {
            	new BigDecimal(quantityField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid quantity (for example '5.5')!\n"; 
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}