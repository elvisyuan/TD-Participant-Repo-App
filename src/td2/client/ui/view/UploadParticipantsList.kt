package td2.client.ui.view

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.SelectionMode
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.paint.Color
import td2.client.ui.controller.UploadController
import td2.client.ui.model.Participant
import tornadofx.SmartResize
import tornadofx.View
import tornadofx.bindSelected
import tornadofx.c
import tornadofx.cellFormat
import tornadofx.column
import tornadofx.style
import tornadofx.tableview
import java.time.LocalDate

class UploadParticipantsList : View() {
	val controller: UploadController by inject()

    override val root = tableview(controller.participants) {
		setPrefSize(1400.0, 1080.0)
		autosize()
        column("Id", Participant::idProperty)
		column("Last Project", Participant::lastProjectProperty)
		column("Email", Participant::emailProperty)
		column("Country", Participant::countryProperty)
		column("Date", Participant::lastContactedDateProperty).cellFormat {
			val today = LocalDate.now().minusMonths(3)
			style {
				if (today.compareTo(it) <= 0) {
					backgroundColor += c("#cd3232")
				} else {
					backgroundColor += c("#32cd32")
				}
				textFill = Color.WHITE
			}
		}
		column("Gender", Participant::genderProperty)
		column("Age", Participant::ageProperty)
		column("Schedule", Participant::scheduleProperty)
		column("Location", Participant::locationProperty)
		column("Online Banking", Participant::onlineBankingProperty)
		column("Online Investment", Participant::onlineInvestmentProperty)
		getSelectionModel().setCellSelectionEnabled(true);
		getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		var item = MenuItem("Copy");
		item.setOnAction(EventHandler<ActionEvent>() {
			fun handle(event: ActionEvent) {
				var posList = getSelectionModel().getSelectedCells();
				var old_r = -1;
				var clipboardString = StringBuilder();
				for (p in posList) {
					val r = p.getRow();
					val c = p.getColumn();
					var cell = getColumns().get(c).getCellData(r);
					if (cell == null) {
						cell = "";
					}
					if (old_r == r) {
						clipboardString.append('\t');
					}
					else if (old_r != -1) {
						clipboardString.append('\n');
						clipboardString.append(cell);
						old_r = r;
					}
				}
				val content = ClipboardContent();
				content.putString(clipboardString.toString());
				Clipboard.getSystemClipboard().setContent(content);
			}
		});
		val menu = ContextMenu();
		menu.getItems().add(item);
		setContextMenu(menu);
		
        bindSelected(controller.selectedParticipant)
        columnResizePolicy = SmartResize.POLICY
    }
	
	fun refresh() {
		root.items = controller.participants
	}
}