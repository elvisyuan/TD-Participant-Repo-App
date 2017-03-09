package td2.client.ui.editor

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.util.converter.IntegerStringConverter
import td2.client.ui.controller.ParticipantController
import tornadofx.View
import tornadofx.bind
import tornadofx.button
import tornadofx.datepicker
import tornadofx.fieldset
import tornadofx.form
import tornadofx.hbox
import tornadofx.textfield
import tornadofx.*

class ParticipantEditor : View() {
    val controller: ParticipantController by inject()
	val integerStringConverter: IntegerStringConverter = IntegerStringConverter()
			
    override val root = form {
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
				datepicker().bind(controller.selectedParticipant.date)
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
			field("Online Banking") {
				textfield(controller.selectedParticipant.onlineBanking)
			}
			field("Online Investment") {
				textfield(controller.selectedParticipant.onlineInvestment)
			}
			
			hbox( 15.0 ) {				
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
