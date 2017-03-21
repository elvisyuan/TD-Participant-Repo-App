package td2.client.ui.controller

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import td2.backend.db.Ucanaccess
import td2.client.resources.fileRepos
import td2.client.ui.model.Participant
import td2.client.ui.model.ParticipantModel
import td2.client.ui.view.FilterParticipantsList
import tornadofx.Controller

class ParticipantController : Controller() {
	var participants = FXCollections.observableArrayList<Participant>()
    val selectedParticipant = ParticipantModel()
	val dbconnection = Ucanaccess(fileRepos.DATA_PATH)

    init {
		participants.addAll(dbconnection.getAllParticipants())
	}
	
	fun addParticipant(participant: Participant) {
		participants.add(participant)
	}
	
	fun addParticipants(newParticipants: ObservableList<Participant>) {
		participants = FXCollections.observableArrayList<Participant>(newParticipants)
	}
	
	fun clear() {
		participants.clear()
	}
	
	public fun presistParticipant(participant: Participant) {
		
	}
}