package td2.client.ui.view

import td2.client.ui.view.FilteredTable
import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TabPane
import javafx.stage.Screen
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.Alert.AlertType
import no.tornado.fxsample.login.LoginController
import tornadofx.*
import td2.backend.db.Ucanaccess
import td2.client.resources.fileRepos


class Mainview : View() {
	
	val loginController: LoginController by inject()
	
    val withFxProperties: ParticipantView by inject()
	
	val filterView: FilterView by inject()
	
	val uploadView: UploadView by inject()
	
	val filteredTable: FilteredTable by inject()
	
	val dbconnection = Ucanaccess(fileRepos.DATA_PATH)
	
	val buttonLogout: Button =  button("Logout") {
    	setOnAction {
    		loginController.logout()
        }
    }
	
	val buttonExit: Button = button("Exit") {
		setOnAction {
			Platform.exit()
        }
	}
    override val root = tabpane {

        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
		tab("Upload Data") {
			hbox {
				this@hbox += uploadView.root
			}
		}
		tab("Filter & Edit Participant") {
			hbox {
				this@hbox += filterView.root
			}
		}
		tab("Manage Account") {
			borderpane {
				center = vbox {
					form {
						fieldset("You are logged in as: ") {
							field("Username") {
								textfield(loginController.userName) {
									setDisable(true)
								}
							}
							field("Role") {
								textfield(loginController.adminstration) {
									setDisable(true)
								}
							}
							field("Change password: ") {
								val newpasswordField = textfield()
								button("Ok") {
									setStyle("-fx-background-color: #829fe9");
									setOnAction {
										dbconnection.changePassword(loginController.userName.toString(), newpasswordField.text)
										val presistMsgDlg = Alert(AlertType.INFORMATION)
										presistMsgDlg.setTitle("Update Successful");
										presistMsgDlg.setHeaderText(null);
										presistMsgDlg.setContentText("Your password has been Updated.");
										presistMsgDlg.showAndWait();
									}
								}
							}
							this += buttonLogout
						}
					}
					form {
						fieldset("Search users: ") {
							this += filteredTable.root
						}
					}
				}
			}
		}
    }
}