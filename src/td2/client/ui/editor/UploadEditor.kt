package td2.client.ui.editor

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import td2.backend.db.ExcelConnection
import td2.client.resources.fileRepos
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
	var excelConnection: ExcelConnection? = null
	
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
						fileChooser.setTitle("Select Upload File");
						fileChooser.getExtensionFilters().addAll(ExtensionFilter("Excel Files (*.xls, *.xlsx, *.xlsm, *.xlsb)",
								"*.xls", "*.xlsx", "*.xlsm", "*.xlsb"))
						val selectedFile = fileChooser.showOpenDialog(FX.primaryStage);
						if (selectedFile != null) {
							uploadBtn.isDisable = false
							filePath.setText(selectedFile.absolutePath)
							println(fileRepos.DATA_PATH)
							println(selectedFile.absolutePath)
							excelConnection = ExcelConnection(selectedFile.absolutePath)
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