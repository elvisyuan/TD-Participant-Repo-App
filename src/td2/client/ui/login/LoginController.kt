package no.tornado.fxsample.login

import javafx.application.Platform
import td2.backend.db.Ucanaccess
import td2.client.resources.fileRepos
import td2.client.ui.view.Mainview
import tornadofx.Controller
import tornadofx.FX
import kotlin.collections.Collection

class LoginController : Controller() {
    val loginScreen: LoginScreen by inject()
    val workbench: Mainview by inject()
	val dbconnection = Ucanaccess(fileRepos.DATA_PATH)

    fun init() {
        with (config) {
            if (containsKey(USERNAME) && containsKey(PASSWORD))
                tryLogin(string(USERNAME), string(PASSWORD), true)
            else
                showLoginScreen("TD Participant Data Repository Login")
        }
    }

    fun showLoginScreen(message: String, shake: Boolean = false) {
        if (FX.primaryStage.scene.root != loginScreen.root) {
            FX.primaryStage.scene.root = loginScreen.root
            FX.primaryStage.sizeToScene()
            FX.primaryStage.centerOnScreen()
        }

        loginScreen.title = message

        Platform.runLater {
            loginScreen.username.requestFocus()
            if (shake) loginScreen.shakeStage()
        }
    }

    fun showWorkbench() {
        if (FX.primaryStage.scene.root != workbench.root) {
            FX.primaryStage.scene.root = workbench.root
            FX.primaryStage.sizeToScene()
            FX.primaryStage.centerOnScreen()
        }
    }
	
    fun tryLogin(username: String, password: String, remember: Boolean) {
        runAsync {
        	dbconnection.isUserAuthorized(username, password)
        } ui { successfulLogin ->

            if (successfulLogin) {
                loginScreen.clear()

                if (remember) {
                    with (config) {
                        set(USERNAME to username)
                        set(PASSWORD to password)
                        save()
                    }
                }

                showWorkbench()
				loginScreen.title = "TD Participant Data Repository"
            } else {
                showLoginScreen("Login failed. Please try again.", true)
            }
        }
    }

    fun logout() {
        with (config) {
            remove(USERNAME)
            remove(PASSWORD)
            save()
        }

        showLoginScreen("Log in as another user")
    }

    companion object {
        val USERNAME = "admin"
        val PASSWORD = "admin"
    }

}