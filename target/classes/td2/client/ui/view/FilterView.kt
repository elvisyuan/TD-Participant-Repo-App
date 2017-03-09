package td2.client.ui.view

import td2.client.ui.editor.FilterEditor
import tornadofx.View
import tornadofx.hbox

class FilterView : View("Filter Editor") {
    override val root = hbox {
		this += FilterEditor::class
        this += FilterParticipantsList::class
    }
}
