package td2.client.ui.view

import td2.client.ui.editor.UploadEditor
import tornadofx.View
import tornadofx.hbox

class UploadView : View("Upload View") {
    override val root = hbox {
		this += UploadEditor::class
		this += UploadParticipantsList::class
    }
}