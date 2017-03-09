package td2.client.ui.view

import td2.client.ui.editor.ParticipantEditor
import tornadofx.View
import tornadofx.hbox

class ParticipantView : View("Participant Editor") {
    override val root = hbox {
        this += ParticipantsList::class
        this += ParticipantEditor::class
    }
}
