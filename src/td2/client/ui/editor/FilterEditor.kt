package td2.client.ui.editor

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.util.converter.IntegerStringConverter
import td2.client.resources.ImageRepos
import td2.client.ui.controller.ParticipantController
import tornadofx.View
import tornadofx.bind
import tornadofx.button
import tornadofx.choicebox
import tornadofx.combobox
import tornadofx.datepicker
import tornadofx.fieldset
import tornadofx.form
import tornadofx.hbox
import tornadofx.textfield

class FilterEditor : View() {
	val integerStringConverter: IntegerStringConverter = IntegerStringConverter()
			
    override val root = form {
		setPrefSize(400.0, 100.0)
        fieldset("Filter Participant Record") {
			field("Id") {
				textfield()
			}
			field("Last Project") {
				textfield()
			}
			field("Email") {
				textfield()
			}	
			field("Country") {
				textfield()
			}
			field("Date") {
				datepicker()
            }	
			field("Gender") {
				val genders = FXCollections.observableArrayList("Male", "Female")
				val combobox = combobox(values = genders)
				//combobox.bind(controller.selectedParticipant.gender)
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
}