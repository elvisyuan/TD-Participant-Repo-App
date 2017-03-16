package td2.client.ui.view

import javafx.collections.FXCollections
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.SelectionMode
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
import tornadofx.SmartResize
import tornadofx.View
import tornadofx.bind
import tornadofx.bindSelected
import tornadofx.borderpane
import tornadofx.button
import tornadofx.choicebox
import tornadofx.column
import tornadofx.combobox
import tornadofx.datepicker
import tornadofx.fieldset
import tornadofx.form
import tornadofx.hbox
import tornadofx.listview
import tornadofx.tableview
import tornadofx.textfield

class FilterView : View("Filter Editor") {
	val controller: ParticipantController by inject()
	val integerStringConverter: IntegerStringConverter = IntegerStringConverter()
	val connection = Ucanaccess(fileRepos.DATA_PATH)

	override val root = borderpane {
		left = form {
			setPrefSize(400.0, 100.0)
			fieldset("Filter Participant Record") {
				field("Country") {
					val countries = connection.getAllCountries()
					listview(countries) {
						setPrefHeight(48.0)
						selectionModel.selectionMode = SelectionMode.MULTIPLE
					}
					CheckComboBox<String>(FXCollections.observableArrayList(countries))
				}
				field("Last Project") {
					val projects = connection.getAllProjects()
					CheckComboBox<String>(FXCollections.observableArrayList(projects))
				}
				field("Email") {
					textfield()
				}
				field("Date") {
					datepicker()
				}
				field("Gender") {
					val genders = FXCollections.observableArrayList("Male", "Female", "Other")
					combobox(values = genders)
				}
				field("Age") {
					textfield()
				}
				field("Schedule") {
					val schedule = FXCollections.observableArrayList("Manager", "Self")
					val combobox = combobox(values = schedule)
					//combobox.bind(controller.selectedParticipant.schedule)
				}
				field("Location") {
					val locations = FXCollections.observableArrayList("Downtown Toronto", "Ajax", "North York", "Vancouver")
					val combobox = combobox(values = locations)
					//combobox.bind(controller.selectedParticipant.location)
				}
				field("Online Banking") {
					textfield()
				}
				field("Online Investment") {
					textfield()
				}

				field("Filter Option") {
					val options = FXCollections.observableArrayList("Exact Match", "Any Matches")
					choicebox(options)
				}

				field() {
					hbox(15.0) {
						val filterIcon = Image(ImageRepos.FILTER_ICON2, 16.0, 16.0, false, false)
						val filterButton = button("Filter Record", ImageView(filterIcon)) {
							setOnAction {
								//controller.selectedParticipant.commit()
							}
						}
						filterButton.setMinSize(70.0, 25.0)

						val resetIcon = Image(ImageRepos.RESET_ICON, 16.0, 16.0, false, false)
						button("Reset Filter", ImageView(resetIcon)) {
							setOnAction {
								//controller.selectedParticipant.rollback()
							}
						}
						filterButton.setMinSize(70.0, 25.0)
					}
				}
			}
		}
		center = tableview(controller.participants) {
			setPrefSize(1400.0, 1080.0)
			autosize()
			column("Id", Participant::idProperty)
			column("Last Project", Participant::lastProjectProperty)
			column("Email", Participant::emailProperty)
			column("Country", Participant::countryProperty)
			column("Last Contacted Date", Participant::lastContactedDateProperty)
			column("Gender", Participant::genderProperty)
			column("Age", Participant::ageProperty)//.cellFormat{
				/*text = it.toString()
				style {
					if (it < 18) {
						backgroundColor += c("#8b0000")
						textFill = Color.WHITE
					}
				}*/
			//}
			column("Schedule", Participant::scheduleProperty)
			column("Location", Participant::locationProperty)
			column("Online Banking", Participant::onlineBankingProperty)
			column("Online Investment", Participant::onlineInvestmentProperty)

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
		}

		right = form {
			setPrefSize(400.0, 100.0)
			fieldset("Edit Participant Information") {
				field("Id") {
					setDisable(true)
					textfield(controller.selectedParticipant.id, integerStringConverter)
				}
				field("Last Project") {
					textfield(controller.selectedParticipant.lastProject)
				}
				field("Email") {
					textfield(controller.selectedParticipant.email)
				}
				field("Country") {
					setDisable(true)
					textfield(controller.selectedParticipant.country)
				}
				field("Date") {
					datepicker().bind(controller.selectedParticipant.lastContactedDate)
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
