package td2.client.ui.editor

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import td2.client.ui.controller.UploadController
import tornadofx.FX
import tornadofx.View
import tornadofx.button
import tornadofx.fieldset
import tornadofx.form
import tornadofx.hbox
import tornadofx.textfield

class UploadEditor : View() {
	var filePath = textfield("")
	var uploadBtn = button("")
	var clearBtn = button("")
	val uploadController: UploadController by inject()
	
	override val root = form {
		setPrefSize(400.0, 200.0)
		fieldset("Upload Data Set") {
			field ("File Path") {
				hbox(10.0) {
					filePath = textfield("")
					filePath.setPrefSize(360.0, 15.0)
					setDisable(true)
				}
			}
			field() {
				button("Select Upload File") {
					setOnAction() {
						val fileChooser = FileChooser();
						fileChooser.setTitle("Open Resource File");
						fileChooser.getExtensionFilters().addAll(
							ExtensionFilter("Text Files (*.txt)", "*.txt"),
							ExtensionFilter("Image Files (*.png", "*.jpg", "*.gif)", "*.png", "*.jpg", "*.gif"),
							ExtensionFilter("Audio Files (*.wav", "*.mp3", "*.aac)", "*.wav", "*.mp3", "*.aac"),
							ExtensionFilter("All Files (*.*)", "*.*"));
						val selectedFile = fileChooser.showOpenDialog(FX.primaryStage);
						if (selectedFile != null) {
							// TODO: needs to be removed
							uploadController.addParticipants(uploadController.demoParticipants)
							uploadBtn.isDisable = false
							
							filePath.setText(selectedFile.absolutePath)
							val fileUploadMsgDlg = Alert(AlertType.INFORMATION)
							fileUploadMsgDlg.setTitle("Upload Successful");
							fileUploadMsgDlg.setHeaderText(selectedFile.name);
							fileUploadMsgDlg.setContentText("Uploaded Successful.");
							fileUploadMsgDlg.showAndWait();
						}
					}
				}
			}
			
			field() {
				clearBtn = button("Clear Records") {
					setOnAction() {
						uploadController.clear()
					}
				}
			}
			field() {
				uploadBtn = button("Upload to Repository") {
					setOnAction() {
						uploadDataToRepo()
					}
				}
			}
			uploadBtn.isDisable = true
	 	}
	}
	
	//TODO: Database presist
	fun uploadDataToRepo() {
		val presistMsgDlg = Alert(AlertType.INFORMATION)
		presistMsgDlg.setTitle("Update Successful");
		presistMsgDlg.setHeaderText(null);
		presistMsgDlg.setContentText("Participants Repository has been Updated.");
		presistMsgDlg.showAndWait();
	}
}