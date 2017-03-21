package td2.client.ui.view

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import td2.client.ui.controller.ParticipantController
import td2.client.ui.model.Participant
import td2.client.ui.email.mailto
import tornadofx.SmartResize
import tornadofx.View
import tornadofx.bindSelected
import tornadofx.column
import tornadofx.tableview

class FilterParticipantsList : View() {
	val controller: ParticipantController by inject()

    override val root = tableview(controller.participants) {
		setPrefSize(1400.0, 1080.0)
		autosize()
        column("Id", Participant::idProperty)
		column("Last Project", Participant::lastProjectProperty)
		column("Email", Participant::emailProperty)
		column("Country", Participant::countryProperty)
		column("Last Contacted Date", Participant::lastContactedDateProperty)
		column("Gender", Participant::genderProperty)
		column("Age", Participant::ageProperty)
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
		
		// Right click option on record: edit
		var editItem = MenuItem("Edit")
		editItem.setOnAction() {
			
		}
		
		val menu = ContextMenu();
		menu.getItems().add(editItem)
		menu.getItems().add(emailItem)
		setContextMenu(menu);

        bindSelected(controller.selectedParticipant)
        columnResizePolicy = SmartResize.POLICY
    }
	
	fun refresh() {
		root.items = controller.participants
	}
}