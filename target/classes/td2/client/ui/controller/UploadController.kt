package td2.client.ui.controller

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import td2.client.ui.model.Participant
import td2.client.ui.model.ParticipantModel
import td2.client.ui.view.UploadParticipantsList
import tornadofx.Controller

class UploadController : Controller() {
	var participants = FXCollections.observableArrayList<Participant>()
    val selectedParticipant = ParticipantModel()
	val participantsList: UploadParticipantsList by inject()
	
	@Deprecated("This function is deprecated, it will be removed in the final version", ReplaceWith("this participants"))
	val demoParticipants = FXCollections.observableArrayList<Participant>() 

    init {
        // Add some test persons for the demo
        demoParticipants.add(Participant(1, "UBI", td2.client.utils.toLocalDate("2015-Dec"), "Canada", "Male", "18-22", "elvis.yc@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
        demoParticipants.add(Participant(2, "UBI", td2.client.utils.toLocalDate("2014-Nov"), "Canada", "Male", "20-25", "pietrogarieri@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		demoParticipants.add(Participant(3, "UBI", td2.client.utils.toLocalDate("2016-Dec"), "USA", "Male", "25-30", "dave@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		demoParticipants.add(Participant(4, "UBI", td2.client.utils.toLocalDate("2017-Mar"), "Canada", "Male", "25-30", "rahul@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
	 }

	fun addParticipant(participant: Participant) {
		participants.add(participant)
		participantsList.refresh()
	}
	
	fun addParticipants(newParticipants: ObservableList<Participant>) {
		participants = FXCollections.observableArrayList<Participant>(newParticipants)
		participantsList.refresh()
	}
	
	fun clear() {
		participants.clear()
		participantsList.refresh()
	}
	
	// TODO
	fun presistToDatabase() {
		
	}
}