package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Controller class for the registration form.
 */
public class ControllerRegister implements Initializable {

    @FXML
    private Text titleRegister;

    @FXML
    private Text messageRegister;

    @FXML
    private TextField firstNameInput;

    @FXML
    private TextField lastNameInput;

    @FXML
    private TextField securityNumberInput;

    @FXML
    private TextField birthCityInput;

    @FXML
    private DatePicker birthDateInput;

    @FXML
    private RadioButton rButton1, rButton2, rButton3;

    /**
     * Initializes the controller.
     *
     * @param arg0 the location used to resolve relative paths for the root object, or null if the location is not known
     * @param arg1 the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    /**
     * Handles the registration process when the register button is clicked.
     *
     * @param e the ActionEvent triggered by the button click
     */
    public void register(ActionEvent e) {
        String fName = firstNameInput.getText();
        String lName = lastNameInput.getText();
        String securityNumber = securityNumberInput.getText();
        String birthCity = birthCityInput.getText();
        LocalDate birthDate = birthDateInput.getValue();
        Gender gender = Gender.OTHER;

        

        boolean isSelected = false;

        if (rButton1.isSelected()) {
            gender = Gender.MALE;
            isSelected = true;
        } else if (rButton2.isSelected()) {
            gender = Gender.FEMALE;
            isSelected = true;
        } else if (rButton3.isSelected()) {
            gender = Gender.OTHER;
            isSelected = true;
        }

        if (isSelected && isAlpha(fName) && isAlpha(lName) && isAlpha(birthCity) && !fName.isEmpty() && !lName.isEmpty() && !securityNumber.isEmpty() && birthCityInput.getText() != null) {
        	Date utilDate = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        	int minNameLength = 2;
            int maxNameLength = 30;

            if (fName.length() < minNameLength || fName.length() > maxNameLength) {
                messageRegister.setText("Invalid first name");
                return;
            }

            if (lName.length() < minNameLength || lName.length() > maxNameLength) {
                messageRegister.setText("Invalid last name");
                return;
            }

            // Check the format of the security number
            if (!isValidSecurityNumber(securityNumber)) {
                messageRegister.setText("Invalid security number");
                return;
            }

            // Additional validation checks according to your requirements

            // All validations passed, proceed with the registration
            ManageDB mDB = new ManageDB();
            mDB.addPerson(fName, lName, birthCity, utilDate, gender, securityNumber);
            messageRegister.setText("User was added successfully!");

            // Reset fields
            firstNameInput.setText("");
            lastNameInput.setText("");
            securityNumberInput.setText("");
            birthCityInput.setText("");
            birthDateInput.setValue(null);
            rButton1.setSelected(false);
            rButton2.setSelected(false);
            rButton3.setSelected(false);
        } else {
            messageRegister.setText("Please fill in all the fields with valid values!");
        }
    }

    /**
     * Handles the event when the user clicks the "Return to Menu" button.
     * Closes the current window and returns to the main menu.
     *
     * @param e the ActionEvent triggered by the button click
     */
    public void returnMenu(ActionEvent e) {
        try {
            // Close previous MENU
            Stage stage = (Stage) titleRegister.getScene().getWindow();
            stage.close();

            // Create new stage for register
            AnchorPane root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
            Stage registerStage = new Stage();
            Scene scene = new Scene(root);
            registerStage.setScene(scene);
            registerStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Checks if a string contains only alphabetic characters.
     *
     * @param str the string to check
     * @return true if the string contains only alphabetic characters, false otherwise
     */
    private boolean isAlpha(String str) {
        return str.matches("^[a-zA-Z\\s-]+$");
    }

    /**
     * Checks if the provided security number is valid.
     *
     * @param securityNumber the security number to validate
     * @return true if the security number is valid, false otherwise
     */
    private boolean isValidSecurityNumber(String securityNumber) {
        // Check the format of the security number
        return securityNumber.matches("\\d{13}");
    }
}
