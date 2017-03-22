package td2.client.ui.view

import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.TabPane
import no.tornado.fxsample.login.LoginController
import td2.backend.db.Ucanaccess
import td2.client.resources.fileRepos
import td2.client.ui.view.FilteredTable.Person
import tornadofx.View
import tornadofx.borderpane
import tornadofx.button
import tornadofx.combobox
import tornadofx.fieldset
import tornadofx.form
import tornadofx.hbox
import tornadofx.plusAssign
import tornadofx.tab
import tornadofx.tabpane
import tornadofx.textfield
import tornadofx.vbox


class Mainview : View() {
	
	val loginController: LoginController by inject()
	
    val withFxProperties: ParticipantView by inject()
	
	val filterView: FilterView by inject()
	
	val uploadView: UploadView by inject()
	
	val filteredTable: FilteredTable by inject()
	
	val dbconnection = Ucanaccess(fileRepos.DATA_PATH)
	
	val buttonLogout: Button =  button("Logout") {
		setStyle("-fx-background-color: #829fe9");
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
		/*tab("Upload Data") {
			hbox {
				this@hbox += uploadView.root
			}
		}*/
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
								button("Update Password") {
									setStyle("-fx-background-color: #829fe9");
									setPrefWidth(150.0)
									setOnAction {
										if (newpasswordField.text == "") {
											val warningDialog = Alert(AlertType.WARNING)
											warningDialog.setTitle("ERROR");
											warningDialog.setHeaderText(null);
											warningDialog.setContentText("Please double check your password.");
											warningDialog.showAndWait();
										}
										dbconnection.changePassword(loginController.userName.toString(), newpasswordField.text)
										val presistMsgDlg = Alert(AlertType.INFORMATION)
										presistMsgDlg.setTitle("Update Successful");
										presistMsgDlg.setHeaderText(null);
										presistMsgDlg.setContentText("Your password has been Updated.");
										presistMsgDlg.showAndWait();
										newpasswordField.clear()
									}
								}
							}
							this += buttonLogout
						}
					}
					form {
						fieldset("Add new user: ") {
							val newUsernameField = textfield()
							val newPasswordField = textfield()
							val availableRoles = FXCollections.observableArrayList("admin", "viewer")
							val newRoleField = combobox (values = availableRoles)
							field("Username") {
								this += newUsernameField
							}
							field("Password") {
								this += newPasswordField
							}
							field("Role") {
								this += newRoleField
							}
							field() {
								button("Create User") {
									setStyle("-fx-background-color: #829fe9");
									setOnAction {
										if (newUsernameField.text == "" || newPasswordField.text == "" || newRoleField.value == null) {
											val warningDialog = Alert(AlertType.WARNING)
											warningDialog.setTitle("ERROR");
											warningDialog.setHeaderText(null);
											warningDialog.setContentText("Please double check your input.");
											warningDialog.showAndWait();
										}
										
										dbconnection.addUser(newUsernameField.text, newPasswordField.text, newRoleField.value)
										val presistMsgDlg = Alert(AlertType.INFORMATION)
										presistMsgDlg.setTitle("Creation Successful");
										presistMsgDlg.setHeaderText(null);
										presistMsgDlg.setContentText("New user has been created.");
										presistMsgDlg.showAndWait();
										newUsernameField.clear()
										newPasswordField.clear()
										newRoleField.selectionModel.clearSelection()
										filteredTable.personTable.items.add(Person(newUsernameField.text, newRoleField.value))
									}
								}
							}
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