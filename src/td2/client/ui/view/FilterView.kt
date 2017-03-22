package td2.client.ui.view

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonType
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.util.converter.IntegerStringConverter
import org.controlsfx.control.CheckComboBox
import td2.backend.db.Ucanaccess
import td2.client.resources.ImageRepos
import td2.client.resources.fileRepos
import td2.client.ui.controller.ParticipantController
import td2.client.ui.email.mailto
import td2.client.ui.model.Participant
import td2.client.ui.model.getAllMobileBankings
import td2.client.ui.model.getAllMobileInsurances
import td2.client.ui.model.getAllOnlineBankings
import td2.client.ui.model.getAllOnlineInsurances
import td2.client.ui.model.getAllOnlineInvestments
import tornadofx.SmartResize
import tornadofx.View
import tornadofx.bind
import tornadofx.bindSelected
import tornadofx.borderpane
import tornadofx.button
import tornadofx.cellFormat
import tornadofx.choicebox
import tornadofx.column
import tornadofx.combobox
import tornadofx.contentWidth
import tornadofx.datepicker
import tornadofx.fieldset
import tornadofx.form
import tornadofx.hbox
import tornadofx.onChange
import tornadofx.plusAssign
import tornadofx.scrollpane
import tornadofx.tableview
import tornadofx.textfield
import java.time.LocalDate

data class Filter(val countries: ObservableList<String>, val lastProjects: ObservableList<String>, val email: String, val lastContactedDate: LocalDate,
				  val genders: ObservableList<String>, val ages: ObservableList<String>, val schedules: ObservableList<String>, val locations: ObservableList<String>,
				  val onlineBankings: ObservableList<String>, val onlineInvestments: ObservableList<String>, val onlineInsurance: ObservableList<String>,
				  val mobileBankings: ObservableList<String>, val mobileInsurance: ObservableList<String>, val devices: ObservableList<String>,
				  val consent: Boolean, val filterOption: String)

object FilterOptions {
	const val EXACT_MATCH = "Exact Match"
	const val ANY_MATCH = "Any Match"
}

class FilterView : View("Filter Editor") {
	val controller: ParticipantController by inject()
	val integerStringConverter: IntegerStringConverter = IntegerStringConverter()
	val connection = Ucanaccess(fileRepos.DATA_PATH)
	var participantTable = tableview(controller.participants){}
	var onlineBankingField = CheckComboBox(FXCollections.observableArrayList("1", "2"))
	var onlineInvestmentField = CheckComboBox(FXCollections.observableArrayList("1", "2"))
	var onlineInsuranceField = CheckComboBox(FXCollections.observableArrayList("1", "2"))
	var mobileBankingField = CheckComboBox(FXCollections.observableArrayList("1", "2"))
	var mobileInsuranceField = CheckComboBox(FXCollections.observableArrayList("1", "2"))
	var mobileDeviceField = CheckComboBox(FXCollections.observableArrayList("1", "2"))
	var consentField = choicebox(FXCollections.observableArrayList("1", "2"))
	
	override val root = borderpane {
		left = scrollpane {
			form {
				setPrefSize(400.0, 100.0)
				fieldset("Filter Participant Record") {
					val countryBox = CheckComboBox(connection.getAllCountries())
					val lastProjectBox = CheckComboBox(connection.getAllProjects())
					val emailTextField = textfield()
					val lastContactedDatePicker = datepicker()
					val genderBox = CheckComboBox(FXCollections.observableArrayList("Male", "Female", "Other"))
					val ageBox = CheckComboBox(FXCollections.observableArrayList("Under 19", "18-24", "25-34", "35-44", "45-54", "55-64", "65+"))
					val scheduleBox = CheckComboBox(FXCollections.observableArrayList("Manager", "Self"))
					val locationBox = CheckComboBox(FXCollections.observableArrayList("Downtown", "Other"))
					val onlineBankingBox = CheckComboBox(getAllOnlineBankings())
					val onlineInvestmentBox = CheckComboBox(getAllOnlineInvestments())
					val onlineInsuranceBox = CheckComboBox(getAllOnlineInsurances())
					val mobileBankingBox = CheckComboBox(getAllMobileBankings())
					val mobileInsuranceBox = CheckComboBox(getAllMobileInsurances())
					val mobileDeviceBox = CheckComboBox(connection.getAllDevices())
					val consentBox = choicebox(FXCollections.observableArrayList("Yes", "No"))
					val filterOptionBox = choicebox(FXCollections.observableArrayList(FilterOptions.EXACT_MATCH, FilterOptions.ANY_MATCH))
				
					field("Country") {
						this += countryBox
					}
					field("Last Project") {
						this += lastProjectBox
					}
					field("Email") {
						this += emailTextField
					}
					field("Last Contacted Date") {
						this += lastContactedDatePicker
					}
					field("Gender") {
						this += genderBox
					}
					field("Age") {
						this += ageBox
					}
					field("Schedule") {
						this += scheduleBox
					}
					field("Location") {
						this += locationBox
					}
					field("Online Banking") {
						this += onlineBankingBox
					}
					field("Online Investment") {
						this += onlineInvestmentBox
					}
					field("Online Insurance") {
						this += onlineInsuranceBox
					}
					field("Mobile Banking") {
						this += mobileBankingBox
					}
					field("Mobile Insurance") {
						this += mobileInsuranceBox
					}
					field("Available Tablets/Phones") {
						this += mobileDeviceBox
					}
					field("Consent") {
						this += consentBox
					}
					field("Filter Option") {
						this += filterOptionBox
						filterOptionBox.selectionModel.select(FilterOptions.EXACT_MATCH)
					}
					field() {
						hbox(15.0) {
							val filterIcon = Image(ImageRepos.FILTER_ICON2, 16.0, 16.0, false, false)
							button("Filter Record", ImageView(filterIcon)) {
								setOnAction {
									val countries = countryBox.checkModel.checkedItems
									val lastProjects = lastProjectBox.checkModel.checkedItems
									val email = emailTextField.text
									var lastContactedDate = lastContactedDatePicker.value
									if (lastContactedDate == null) {
										lastContactedDate = LocalDate.now().plusDays(1)
									} 
									val gender = genderBox.checkModel.checkedItems
									val age = ageBox.checkModel.checkedItems
									val schedule = scheduleBox.checkModel.checkedItems
									val location = locationBox.checkModel.checkedItems
									val onlineBanking = onlineBankingBox.checkModel.checkedItems
									val onlineInvestment = onlineInvestmentBox.checkModel.checkedItems
									val onlineInsurance = onlineInsuranceBox.checkModel.checkedItems
									val mobileBanking = mobileBankingBox.checkModel.checkedItems
									val mobileInsurance = mobileInsuranceBox.checkModel.checkedItems
									val devices = mobileDeviceBox.checkModel.checkedItems
									var consent: Boolean?
									if (consentBox.value == "Yes") {
										consent = true
									} else {
										consent = false
									}
									var filterOption = filterOptionBox.value
									if (filterOption == null) {
										filterOption = FilterOptions.EXACT_MATCH
									}
									val appliedFilter = Filter(countries, lastProjects, email, lastContactedDate, gender, age, schedule, location,
											onlineBanking, onlineInvestment, onlineInsurance, mobileBanking, mobileInsurance, devices, consent, filterOption)
									participantTable.items = connection.filterParticipants(appliedFilter)
									participantTable.refresh()
									SmartResize.POLICY.requestResize(participantTable)
								}
							}

							val resetIcon = Image(ImageRepos.RESET_ICON, 16.0, 16.0, false, false)
							button("Clear Filter", ImageView(resetIcon)) {
								setOnAction {
									countryBox.getCheckModel().clearChecks()
									lastProjectBox.getCheckModel().clearChecks()
									emailTextField.clear()
									lastContactedDatePicker.getEditor().clear();
									lastContactedDatePicker.value = null
									genderBox.getCheckModel().clearChecks()
									ageBox.getCheckModel().clearChecks()
									scheduleBox.getCheckModel().clearChecks()
									locationBox.getCheckModel().clearChecks()
									onlineBankingBox.getCheckModel().clearChecks()
									onlineInvestmentBox.getCheckModel().clearChecks()
									onlineInsuranceBox.getCheckModel().clearChecks()
									mobileBankingBox.getCheckModel().clearChecks()
									mobileInsuranceBox.getCheckModel().clearChecks()
									mobileDeviceBox.getCheckModel().clearChecks()
									consentBox.selectionModel.clearSelection()
									filterOptionBox.selectionModel.clearSelection()
								}
							}
						}
					}
				}
			}
		}
		participantTable = tableview(controller.participants) {
				setPrefSize(1400.0, 1080.0)
				// COLUMN INITIALIZATION
				val idcolumn = column("Id", Participant::idProperty)
				val countrycolumn = column("Country", Participant::countryProperty)
				val lastProjectcolumn = column("Last Project", Participant::lastProjectProperty)
				val lastContactDateColumn = column("Last Contacted Date", Participant::lastContactedDateProperty)
				val emailcolumn = column("Email", Participant::emailProperty)
				val gendercolumn = column("Gender", Participant::genderProperty)
				val agecolumn = column("Age", Participant::ageProperty)
				val schedulecolumn = column("Schedule", Participant::scheduleProperty)
				val locationcolumn = column("Location", Participant::locationProperty)
				val onlineBankingColumn = column("Online Banking", Participant::onlineBankingProperty)
				val onlineInvestmentColumn = column("Online Investment", Participant::onlineInvestmentProperty)
				val onlineInsuranceColumn = column("Online Insurance", Participant::onlineInsuranceProperty)
				val mobileBankingColumn = column("Mobile Banking", Participant::mobileBankingApptProperty)
				val mobileInsuranceColumn = column("Mobile Insurance", Participant::mobileInsuranceAppProperty)
				val deviceColumn = column("Device", Participant::deviceProperty)
				val consentColumn = column("Consent", Participant::isConsentProperty)
				
				// COLUMN WIDTH PROPERTIES BIND
				idcolumn.contentWidth(padding = 5.0, useAsMin = true)
				countrycolumn.contentWidth(padding = 20.0, useAsMin = true)
				lastProjectcolumn.contentWidth(padding = 5.0, useAsMin = true)
				lastContactDateColumn.contentWidth(padding = 0.0, useAsMin = true)
				emailcolumn.contentWidth(padding = 30.0, useAsMin = true)
				gendercolumn.contentWidth(padding = 15.0, useAsMin = true)
				agecolumn.contentWidth(padding = 30.0, useAsMin = true)
				schedulecolumn.contentWidth(padding = 30.0, useAsMin = true)
				locationcolumn.contentWidth(padding = 30.0, useAsMin = true)
				onlineBankingColumn.contentWidth(padding = 50.0, useAsMin = true)
				onlineInvestmentColumn.contentWidth(padding = 30.0, useAsMin = true)
				onlineInsuranceColumn.contentWidth(padding = 30.0, useAsMin = true)
				mobileBankingColumn.contentWidth(padding = 30.0, useAsMin = true)
				mobileInsuranceColumn.contentWidth(padding = 20.0, useAsMin = true)
				deviceColumn.contentWidth(padding = 30.0, useAsMin = true)
				consentColumn.contentWidth(padding = 5.0, useAsMin = true)
				
				// COLUMN FORMATTING
				lastContactDateColumn.cellFormat {
					val today = LocalDate.now().minusMonths(3)
					if (it != null && today.compareTo(it) <= 0) {
						setStyle("-fx-background-color:#ff3d3d; -fx-text-fill:white")
						text = it.toString()
					} else if (it != null && today.compareTo(it) > 0) {
						setStyle("-fx-background-color:#32cd32; -fx-text-fill:white")
						text = it.toString()
					} else {
						print("Should Not End Here: " + it.toString())
					}
				}			
				onlineBankingColumn.cellFormat {
					if (!it.isEmpty()) {
						text = it.joinToString()
					}
				}
				onlineInvestmentColumn.cellFormat {
					if (!it.isEmpty()) {
						text = it.joinToString()
					}
				}
				onlineInsuranceColumn.cellFormat {
					if (!it.isEmpty()) {
						text = it.joinToString()
					}
				}
				mobileBankingColumn.cellFormat {
					if (!it.isEmpty()) {
						text = it.joinToString()
					}
				}
				mobileInsuranceColumn.cellFormat {
					if (!it.isEmpty()) {
						text = it.joinToString()
					}
				}
				consentColumn.cellFormat {
					if (it == false) {
						text = "No"
					} else {
						text = "Yes"
					}
				}
				deviceColumn.cellFormat {
					if (!it.isEmpty()) {
						var content = FXCollections.observableArrayList<String>()
						for (device in it) {
							if (device != "N/A") {
								content.add(device)
							}
						}
						text = content.joinToString()
					}
				}
				
				// Right click option on record: email
				var emailItem = MenuItem("Email");
				emailItem.setOnAction() {
					var posList = getSelectionModel()
					var selectedParticipant: Participant = posList.getSelectedItem()
					print(selectedParticipant.email)
					mailto(selectedParticipant.email, "", "")
				}

				val menu = ContextMenu();
				menu.getItems().add(emailItem)
				setContextMenu(menu);
				
				selectionModel.selectedItemProperty().onChange {
                    editPerson(it)
                }
				bindSelected(controller.selectedParticipant)
				//columnResizePolicy = SmartResize.POLICY
		}
		center = participantTable

		right = form {
			setPrefSize(400.0, 100.0)
			fieldset("Edit Participant Information") {
				val onlineBankingBox = CheckComboBox(getAllOnlineBankings())
				val onlineInvestmentBox = CheckComboBox(getAllOnlineInvestments())
				val onlineInsuranceBox = CheckComboBox(getAllOnlineInsurances())
				val mobileBankingBox = CheckComboBox(getAllMobileBankings())
				val mobileInsuranceBox = CheckComboBox(getAllMobileInsurances())
				val mobileDeviceBox = CheckComboBox(connection.getAllDevices())
				val consentBox = choicebox(FXCollections.observableArrayList("Yes", "No"))
				field("Id") {
					setDisable(true)
					textfield(controller.selectedParticipant.id, integerStringConverter)
				}
				field("Country") {
					setDisable(true)
					textfield(controller.selectedParticipant.country)
				}
				field("Last Project") {
					textfield(controller.selectedParticipant.lastProject)
				}
				field("Last Contacted Date") {
					datepicker().bind(controller.selectedParticipant.lastContactedDate)
				}
				field("Email") {
					textfield(controller.selectedParticipant.email)
				}
				field("Gender") {
					val genders = FXCollections.observableArrayList("Male", "Female", "Other")
					val combobox = combobox(values = genders)
					combobox.bind(controller.selectedParticipant.gender)
				}
				field("Age") {
					setDisable(true)
					textfield(controller.selectedParticipant.age)
				}
				field("Schedule") {
					val schedule = FXCollections.observableArrayList("Manager", "Self")
					val combobox = combobox(values = schedule)
					combobox.bind(controller.selectedParticipant.schedule)
				}
				field("Location") {
					val locations = FXCollections.observableArrayList("Downtown", "Other")
					val combobox = combobox(values = locations)
					combobox.bind(controller.selectedParticipant.location)
				}
				field("Online Banking") {
					onlineBankingField = onlineBankingBox
					this += onlineBankingBox					
				}
				field("Online Investment") {
					onlineInvestmentField = onlineInvestmentBox
					this += onlineInvestmentBox
				}
				field("Online Insurance") {
					onlineInsuranceField = onlineInsuranceBox
					this += onlineInsuranceBox
				}
				field("Mobile Banking") {
					mobileBankingField = mobileBankingBox
					this += mobileBankingBox
				}
				field("Mobile Insurance") {
					mobileInsuranceField = mobileInsuranceBox
					this += mobileInsuranceBox
				}
				field("Available Tablets/Phones") {
					mobileDeviceField = mobileDeviceBox
					this += mobileDeviceBox
				}
				field("Consent") {
					consentField = consentBox
					this += consentBox
				}
				hbox(15.0) {
					button("Save") {
						setOnAction {
							controller.selectedParticipant.commit()
							var participant = controller.selectedParticipant.item
							participant.onlineBanking = onlineBankingField.checkModel.checkedItems
							participant.onlineInsurance = onlineInsuranceField.checkModel.checkedItems
							participant.onlineInvestment = onlineInvestmentField.checkModel.checkedItems
							participant.mobileBankingApp = mobileBankingField.checkModel.checkedItems
							participant.mobileInsuranceApp = mobileInsuranceField.checkModel.checkedItems
							participant.device = mobileDeviceField.checkModel.checkedItems
							if (consentField.selectionModel.selectedItem == "Yes") {
								participant.isConsent = true
							} else {
								participant.isConsent = false
							}
							connection.presistParticipantFromUI(participant)
							val participants = FXCollections.observableArrayList<Participant>()
							participants.addAll(connection.getAllParticipants())
							controller.addParticipants(participants)
							participantTable.items = participants
							val presistMsgDlg = Alert(AlertType.INFORMATION)
							presistMsgDlg.setTitle("Update Successful");
							presistMsgDlg.setHeaderText(null);
							presistMsgDlg.setContentText("Participant record has been Updated.");
							presistMsgDlg.showAndWait();
							participantTable.refresh()
						}
					}
					button("Reset") {
						setSpacing(15.0)
						setOnAction {
							controller.selectedParticipant.rollback()
						}
					}
					button("Delete") {
						setSpacing(15.0)
						setStyle("-fx-background-color: #829fe9");
						setOnAction {
							val alert = Alert(AlertType.CONFIRMATION);
							alert.setTitle("Delete Confirmation");
							alert.setHeaderText("Are you sure you want to delete this participant");
							alert.setContentText("Click on OK to continue");

							val result = alert.showAndWait();
							if (result.get() == ButtonType.OK){
								connection.deleteParticipantWithID(controller.selectedParticipant.id.value)
								val participants = FXCollections.observableArrayList<Participant>()
								participants.addAll(connection.getAllParticipants())
								controller.addParticipants(participants)
								participantTable.items = participants
							} else {
							}
							
						}
					}
				}
			}
		}
	}
	
	private fun editPerson(participant: Participant?) {
        if (participant != null) {
			onlineBankingField.getCheckModel().clearChecks()
			onlineInvestmentField.getCheckModel().clearChecks()
			onlineInsuranceField.getCheckModel().clearChecks()
			mobileBankingField.getCheckModel().clearChecks()
			mobileInsuranceField.getCheckModel().clearChecks()
			mobileDeviceField.getCheckModel().clearChecks()
			consentField.selectionModel.clearSelection()
			for (onlineBanking in participant.onlineBanking) {
				onlineBankingField.getCheckModel().check(onlineBanking)
			}
			for (onlineInvestment in participant.onlineInvestment) {
				onlineInvestmentField.getCheckModel().check(onlineInvestment)
			}
			for (onlineInsurance in participant.onlineInsurance) {
				onlineInsuranceField.getCheckModel().check(onlineInsurance)
			}
			for (mobileBanking in participant.mobileBankingApp) {
				mobileBankingField.getCheckModel().check(mobileBanking)
			}
			for (mobileInsurance in participant.mobileInsuranceApp) {
				mobileInsuranceField.getCheckModel().check(mobileInsurance)
			}
			for (mobileDevice in participant.device) {
				mobileDeviceField.getCheckModel().check(mobileDevice)
			}
			if (participant.isConsent) {
				consentField.selectionModel.select("Yes")
			} else {
				consentField.selectionModel.select("No")
			}
        }
    }
}
