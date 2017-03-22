package td2.client.ui.view

import javafx.scene.control.TableView
import td2.backend.db.Ucanaccess
import td2.client.resources.fileRepos
import tornadofx.SmartResize
import tornadofx.SortedFilteredList
import tornadofx.View
import tornadofx.borderpane
import tornadofx.center
import tornadofx.column
import tornadofx.fieldset
import tornadofx.form
import tornadofx.hbox
import tornadofx.label
import tornadofx.onChange
import tornadofx.singleAssign
import tornadofx.tableview
import tornadofx.textfield
import tornadofx.top
import tornadofx.vbox
import tornadofx.weigthedWidth
import java.util.function.Predicate

/**
 * Created by ggreaves on 3/17/2017.
 */
class FilteredTable : View("My View") {
	val dbconnection = Ucanaccess(fileRepos.DATA_PATH)
    var persons = dbconnection.getAllUsers()
    var data = SortedFilteredList( persons )
	var personTable = TableView(data)
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
				personTable = this
                column("username", Person::username).weigthedWidth(1.0)
                column("role", Person::role).weigthedWidth(1.0)
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
