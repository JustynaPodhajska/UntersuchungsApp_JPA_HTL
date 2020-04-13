package view;

import fachlogik.Geschlecht;
import fachlogik.Person;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class PersonEditDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private ComboBox<Geschlecht> genderField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField svnrField;
    @FXML
    private TextField krankenkasseField;
    @FXML
    private DatePicker birthdayField;


    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;



    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPerson(Person person) {
        this.person = person;

        svnrField.setText(Long.toString(person.getSvnr()));
        firstNameField.setText(person.getVorname());
        lastNameField.setText(person.getNachname());
        genderField.setItems(FXCollections.observableArrayList(Geschlecht.values()));
        Geschlecht tempGender = person.getGeschlecht();
        if (tempGender != null) genderField.setValue(person.getGeschlecht()); else genderField.setValue(Geschlecht.WEIBLICH);
        addressField.setText(person.getAdresse());
        birthdayField.setValue(person.getGeburtsdatum());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setSvnr(Long.parseLong(svnrField.getText()));
            person.setVorname(firstNameField.getText());
            person.setNachname(lastNameField.getText());
            person.setGeschlecht(genderField.getValue());
            person.setAdresse(addressField.getText());
            person.setGeburtsdatum(birthdayField.getValue());

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

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (addressField.getText() == null || addressField.getText().length() == 0) {
            errorMessage += "No valid address!\n"; 
        }

        if (svnrField.getText() == null || svnrField.getText().length() == 0) {
            errorMessage += "No valid SVNR!\n"; 
        } else {
            try {
                Long.parseLong(svnrField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid SVNR (must be a long)!\n"; 
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