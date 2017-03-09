package td2.client.ui.view

import td2.client.ui.controller.ParticipantController
import td2.client.ui.model.Participant
import tornadofx.SmartResize
import tornadofx.View
import tornadofx.bindSelected
import tornadofx.column
import tornadofx.tableview


class ParticipantsList : View() {
	val controller: ParticipantController by inject()

    override val root = tableview(controller.participants) {
		setPrefSize(1400.0, 1080.0)
        column("Id", Participant::idProperty)
		column("Last Project", Participant::lastProjectProperty)
		column("Email", Participant::emailProperty)
		column("Country", Participant::countryProperty)
		column("Date", Participant::dateProperty)
		column("Gender", Participant::genderProperty)
		column("Age", Participant::ageProperty)
		column("Schedule", Participant::scheduleProperty)
		column("Location", Participant::locationProperty)
		column("Online Banking", Participant::onlineBankingProperty)
		column("Online Investment", Participant::onlineInvestmentProperty)

        bindSelected(controller.selectedParticipant)
        columnResizePolicy = SmartResize.POLICY
    }
}