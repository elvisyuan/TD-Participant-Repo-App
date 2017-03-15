package no.tornado.fxsample.login

import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px

class Styles : Stylesheet() {
    companion object {
        val loginScreen by cssclass()
    }

    init {
        select(loginScreen) {
            padding = box(45.px, 60.px)
            vgap = 25.px
            hgap = 20.px
        }
    }
}