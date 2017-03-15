package td2.client.ui.controller

import javafx.collections.FXCollections
import td2.backend.db.Ucanaccess
import td2.client.resources.fileRepos
import td2.client.ui.model.Participant
import td2.client.ui.model.ParticipantModel
import tornadofx.Controller

class ParticipantController : Controller() {
	val participants = FXCollections.observableArrayList<Participant>()
    val selectedParticipant = ParticipantModel()
	val dbconnection = Ucanaccess(fileRepos.DATA_PATH)

    init {
		participants.addAll(dbconnection.getAllParticipants())
	}
}