package no.tornadofx.showcase

import tornadofx.*

class Styles : Stylesheet(){
    companion object {
        val mainview by cssclass()
    }

    init {
        select(mainview) {
            prefWidth = 1900.px
            prefHeight = 1080.px
        }
    }
}