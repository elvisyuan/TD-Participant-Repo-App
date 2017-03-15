/**
 * Created by Elvis yc on 1/11/2017.
 */
package td2.client.ui.login

import javafx.application.Application
import no.tornado.fxsample.login.LoginApp
import td2.backend.db.Ucanaccess
import td2.client.resources.fileRepos

public class TD2LoginPanel {
	companion object {
		@JvmStatic public fun main(args: Array<String>) {
			Application.launch(LoginApp::class.java, *args)
		}
	}
}