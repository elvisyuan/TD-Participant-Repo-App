package td2.client.ui.view

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.scene.control.SelectionMode
import tornadofx.*
import java.util.function.Predicate
import td2.backend.db.Ucanaccess
import td2.client.resources.fileRepos

/**
 * Created by ggreaves on 3/17/2017.
 */
class FilteredTable : View("My View") {
	val dbconnection = Ucanaccess(fileRepos.DATA_PATH)
    var persons = dbconnection.getAllUsers()
    var data = SortedFilteredList( persons )
    lateinit var compositePredicate: Predicate<Person>

    override val root = borderpane {

        top{
            vbox {

                form {

                    fieldset {

                        field {
                            hbox {
                                spacing = 5.0
                                label("username")
                                textfield {
                                    compositePredicate = Predicate{ item -> item.username.contains( this.text, ignoreCase = true ) }
                                    textProperty().onChange { value -> refilter()  }
                                }
                                label("password")
                                textfield {
                                    compositePredicate = compositePredicate.and(Predicate{ item -> item.role.contains( this.text, ignoreCase = true ) })
                                    textProperty().onChange { value -> refilter()  }
                                }

                                refilter()
                            }
                        }
                    }

                }

            }
        }
        center {

            tableview<Person>( data ){
                column("username", Person::username)
                column("role", Person::role)
                columnResizePolicy = SmartResize.POLICY
                data.bindTo(this)
            }
        }

    }

    fun refilter(){
        data.predicate = { person ->  compositePredicate.test(person) }
    }

    data class Person (val username: String, val role: String )

}
