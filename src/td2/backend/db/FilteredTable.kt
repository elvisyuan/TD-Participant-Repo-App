package com.casebank.example.view

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.scene.control.SelectionMode
import tornadofx.*
import java.util.function.Predicate

/**
 * Created by ggreaves on 3/17/2017.
 */
class FilteredTable : View("My View") {

    var persons = FXCollections.observableList( listOf( Person("George","Smith"),Person("Harry","Jones"),Person("Sally","Wilson")) )
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
                                label("First")
                                textfield {
                                    compositePredicate = Predicate{ item -> item.firstName.contains( this.text, ignoreCase = true ) }
                                    textProperty().onChange { value -> refilter()  }
                                }
                                label("Last")
                                textfield {
                                    compositePredicate = compositePredicate.and(Predicate{ item -> item.lastName.contains( this.text, ignoreCase = true ) })
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
                column("First Name", Person::firstName)
                column("Last Name", Person::lastName)
                columnResizePolicy = SmartResize.POLICY
                data.bindTo( this )
            }
        }

    }

    fun refilter(){
        data.predicate = { person ->  compositePredicate.test(person) }
    }

    data class Person( val firstName: String, val lastName: String )

}
