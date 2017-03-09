package td2.client.ui.view

import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TabPane
import javafx.stage.Screen
import no.tornado.fxsample.login.LoginController
import tornadofx.FX
import tornadofx.View
import tornadofx.button
import tornadofx.hbox
import tornadofx.plusAssign
import tornadofx.tab
import tornadofx.tabpane


class Mainview : View() {
	
	val loginController: LoginController by inject()
	
    val withFxProperties: ParticipantView by inject()
	
	val filterView: FilterView by inject()
	
	val uploadView: UploadView by inject()
	
	val buttonLogout: Button =  button("Logout") {
    	setOnAction {
    		loginController.logout()
        }
    }
	
	val buttonExit: Button = button("Exit") {
		setOnAction {
			Platform.exit()
        }
	}
    override val root = tabpane {
		
		//set Stage boundaries to visible bounds of the main screen
		val stage = FX.primaryStage
		val primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());

        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
		tab("Upload Data") {
			hbox {
				this@hbox += uploadView.root
			}
		}
		tab("Filter Participant") {
			hbox {
				this@hbox += filterView.root
			}
		}
        tab("Particiant Records") {
            hbox {
                this@hbox += withFxProperties.root
            }
        }
		tab("Manage Account") {
			hbox {
				this@hbox += buttonLogout
				this@hbox += Label("Under Development")
			}
		}
    }
}