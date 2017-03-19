package td2.client.ui.view

import javafx.collections.FXCollections
import javafx.collections.ObservableList
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
import tornadofx.*
import java.time.LocalDate

data class Filter(val countries: ObservableList<String>, val lastProjects: ObservableList<String>, val email: String, val lastContactedDate: LocalDate,
				  val gender: ObservableList<String>, val age: ObservableList<String>, val schedule: ObservableList<String>, val location: ObservableList<String>,
				  val onlineBankings: ObservableList<String>, val onlineInvestments: ObservableList<String>, val onlineInsurance: ObservableList<String>,
				  val mobileBankings: ObservableList<String>, val mobileInsurance: ObservableList<String>, val consent: Boolean, val filterOption: String)

object FilterOptions {
	const val EXACT_MATCH = "Exact Match"
	const val ANY_MATCH = "Any Match"
}

class FilterView : View("Filter Editor") {
	val controller: ParticipantController by inject()
	val integerStringConverter: IntegerStringConverter = IntegerStringConverter()
	val connection = Ucanaccess(fileRepos.DATA_PATH)
	var participants = controller.participants
	val data = SortedFilteredList(participants)

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
					field("Consent") {
						this += consentBox
					}
					field("Filter Option") {
						this += filterOptionBox
					}
					field() {
						hbox(15.0) {
							val filterIcon = Image(ImageRepos.FILTER_ICON2, 16.0, 16.0, false, false)
							button("Filter Record", ImageView(filterIcon)) {
								setOnAction {
									val countries = countryBox.checkModel.checkedItems
									val lastProjects = lastProjectBox.checkModel.checkedItems
									val email = emailTextField.text
									val lastContactedDate = lastContactedDatePicker.value
									val gender = genderBox.checkModel.checkedItems
									val age = ageBox.checkModel.checkedItems
									val schedule = scheduleBox.checkModel.checkedItems
									val location = locationBox.checkModel.checkedItems
									val onlineBanking = onlineBankingBox.checkModel.checkedItems
									val onlineInvestment = onlineInvestmentBox.checkModel.checkedItems
									val onlineInsurance = onlineInsuranceBox.checkModel.checkedItems
									val mobileBanking = mobileBankingBox.checkModel.checkedItems
									val mobileInsurance = mobileInsuranceBox.checkModel.checkedItems
									var consent: Boolean?
									if (consentBox.value == "Yes") {
										consent = true
									} else {
										consent = false
									}
									val filterOption = filterOptionBox.value
									val appliedFilter = Filter(countries, lastProjects, email, lastContactedDate, gender, age, schedule, location,
											onlineBanking, onlineInvestment, onlineInsurance, mobileBanking, mobileInsurance, consent, filterOption)
									
									print(countries)
									print(email)
									print(lastContactedDate)
									print(consent)
								}
							}

							val resetIcon = Image(ImageRepos.RESET_ICON, 16.0, 16.0, false, false)
							button("Clear Filter", ImageView(resetIcon)) {
								setOnAction {
									countryBox.getCheckModel().clearChecks()
									lastProjectBox.getCheckModel().clearChecks()
									emailTextField.clear()
									lastContactedDatePicker.getEditor().clear();
									genderBox.getCheckModel().clearChecks()
									ageBox.getCheckModel().clearChecks()
									scheduleBox.getCheckModel().clearChecks()
									locationBox.getCheckModel().clearChecks()
									onlineBankingBox.getCheckModel().clearChecks()
									onlineInvestmentBox.getCheckModel().clearChecks()
									onlineInsuranceBox.getCheckModel().clearChecks()
									mobileBankingBox.getCheckModel().clearChecks()
									mobileInsuranceBox.getCheckModel().clearChecks()
									consentBox.selectionModel.clearSelection()
									filterOptionBox.selectionModel.clearSelection()
								}
							}
						}
					}
				}
			}
		}
		val participantTable = scrollpane {
			tableview(data) {
				setPrefSize(1400.0, 1080.0)
				autosize()
				column("Id", Participant::idProperty)
				column("Country", Participant::countryProperty)
				column("Last Project", Participant::lastProjectProperty)
				column("Last Contacted Date", Participant::lastContactedDateProperty).cellFormat {
					val today = LocalDate.now().minusMonths(3)
					style {
						if (it != null && today.compareTo(it) <= 0) {
							setStyle("-fx-background-color:#8B0000;")
							text = it.toString()
						} else if (it != null && today.compareTo(it) > 0) {
							setStyle("-fx-background-color:#32cd32;")
							text = it.toString()
						} else {
							print("Should Not End Here: " + it.toString())
						}
					}
				}
				column("Email", Participant::emailProperty)
				column("Gender", Participant::genderProperty)
				column("Age", Participant::ageProperty)
				column("Schedule", Participant::scheduleProperty)
				column("Location", Participant::locationProperty)
				column("Online Banking", Participant::onlineBankingProperty)
				column("Online Investment", Participant::onlineInvestmentProperty)
				column("Online Insurance", Participant::onlineInsuranceProperty)
				column("Mobile Banking", Participant::mobileBankingApptProperty)
				column("Mobile Insurance", Participant::mobileInsuranceAppProperty)
				column("Device", Participant::deviceProperty)
				column("Consent", Participant::isConsentProperty)

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

				bindSelected(controller.selectedParticipant)
				columnResizePolicy = SmartResize.POLICY
				data.bindTo(this)
			}
		}
		center = participantTable

		right = form {
			setPrefSize(400.0, 100.0)
			fieldset("Edit Participant Information") {
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
					setDisable(true)
					val genders = FXCollections.observableArrayList("Male", "Female")
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
					val locations = FXCollections.observableArrayList("Downtown Toronto", "Ajax", "North York", "Vancouver")
					val combobox = combobox(values = locations)
					combobox.bind(controller.selectedParticipant.location)
				}
				field("Mobile Banking") {
					val mobileBankings = controller.selectedParticipant.mobileBankingApp
					this += CheckComboBox(mobileBankings.value)	
				}
				/*field("Online Banking") {
					textfield(controller.selectedParticipant.onlineBanking)
				}
				field("Online Investment") {
					textfield(controller.selectedParticipant.onlineInvestment)
				}*/

				hbox(15.0) {
					button("Save") {
						setOnAction {
							controller.selectedParticipant.commit()
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
							try {
								var index: Int = 0
								controller.participants.forEach { participant ->
									if (participant.id == controller.selectedParticipant.id.value) {
										controller.participants.removeAt(index)
									}
									index++
								}
							} catch (exception: Exception) {

							}
						}
					}
				}
			}
		}
	}
}
