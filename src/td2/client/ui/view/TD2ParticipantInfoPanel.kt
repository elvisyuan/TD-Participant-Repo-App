package td2.client.ui.view

import no.tornadofx.fxsamples.withpojo.controller.MainController
import no.tornadofx.fxsamples.withpojo.model.Category
import no.tornadofx.fxsamples.withpojo.model.Entry
import tornadofx.*

class participantsInfo : View("Participants' Info Panel") {
    val categoryListView: CategoryListView by inject()
    override val root = borderpane {
        left = categoryListView.root
    }
}

class CategoryListView : View() {
    val controller: MainController by inject()
    override val root = listview<Category> {
        prefWidth = 100.0
        items = controller.categories.observable()

        cellFormat { text = it.title }
        bindSelected(controller.categoryModel)
    }

}
