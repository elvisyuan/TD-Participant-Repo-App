package td2.client.ui.model

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.ItemViewModel
import tornadofx.getProperty
import tornadofx.property

object MobileInsuranceConstants {
	const val NO_MOBILE_BANKING = "No"
	const val TD = "TD"
	const val RBC = "RBC"
	const val OTHER = "Other"
}

fun getAllMobileInsurances(): ObservableList<String> {
	return FXCollections.observableArrayList(MobileBankingConstants.NO_MOBILE_BANKING, MobileBankingConstants.TD,
			MobileBankingConstants.RBC, MobileBankingConstants.OTHER)
}

class MobileInsuranceApp(isTD2: Boolean, isRBC2: Boolean, isOther2: Boolean, other2Name: String) {
    var isTD2 by property(isTD2)
    fun isTD2Property() = getProperty(MobileInsuranceApp::isTD2)
	
	var isRBC2 by property(isRBC2)
	fun isRBC2Property() = getProperty(MobileInsuranceApp::isRBC2)
	
	var isOther2 by property(isOther2)
	fun isOther2Property() = getProperty(MobileInsuranceApp::isOther2)
	
	var other2Name by property(other2Name)
	fun other2NameProperty() = getProperty(MobileInsuranceApp::other2Name)
}

class MobileInsuranceAppModel : ItemViewModel<MobileInsuranceApp>() {
    val isTD2 = bind { item?.isTD2Property() }
	val isRBC2 = bind { item?.isRBC2Property() }
	val isOther2 = bind { item?.isOther2Property() }
    val other2Name = bind { item?.other2NameProperty() }
}