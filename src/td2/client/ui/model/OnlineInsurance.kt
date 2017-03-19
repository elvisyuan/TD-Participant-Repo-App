package td2.client.ui.model

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.ItemViewModel
import tornadofx.getProperty
import tornadofx.property

object OnlineInsuranceConstants {
	const val NO_ONLINE_INSURANCE = "No"
	const val TD_MY_INSURANCE = "TD MyInsurance"
	const val RBC_ONLINE_INSURANCE = "RBC Online Insurance"
	const val OTHER = "Other"
}

fun getAllOnlineInsurances(): ObservableList<String> {
	return FXCollections.observableArrayList(OnlineInsuranceConstants.NO_ONLINE_INSURANCE, OnlineInsuranceConstants.TD_MY_INSURANCE,
			OnlineInsuranceConstants.RBC_ONLINE_INSURANCE, OnlineInsuranceConstants.OTHER)
}

class OnlineInsurance(isOnlineInsurance: Boolean, isTDMyInsurance: Boolean, isRBCOnlineInsurance:Boolean,
					isOtherInsurance: Boolean, otherInsuranceProduct: String) {
    var isOnlineInsurance by property(isOnlineInsurance)
    fun isOnlineInsuranceProperty() = getProperty(OnlineInsurance::isOnlineInsurance)
	
	var isTDMyInsurance by property(isTDMyInsurance)
	fun isTDMyInsuranceProperty() = getProperty(OnlineInsurance::isTDMyInsurance)
	
	var isRBCOnlineInsurance by property(isRBCOnlineInsurance)
	fun isRBCOnlineInsuranceProperty() = getProperty(OnlineInsurance::isRBCOnlineInsurance)

    var isOtherInsurance by property(isOtherInsurance)
    fun isOtherInsuranceProperty() = getProperty(OnlineInsurance::isOtherInsurance)

    var otherInsuranceProduct by property(otherInsuranceProduct)
    fun otherInsuranceProductProperty() = getProperty(OnlineInsurance::otherInsuranceProduct)
}

class OnlineInsuranceModel : ItemViewModel<OnlineInsurance>() {
    val isOnlineInsurance = bind { item?.isOnlineInsuranceProperty() }
	val isTDMyInsurance = bind { item?.isTDMyInsuranceProperty() }
	val isRBCOnlineInsurance = bind { item?.isRBCOnlineInsuranceProperty() }
    val isOtherInsurance = bind { item?.isOtherInsuranceProperty() }
    val otherInsuranceProduct = bind { item?.otherInsuranceProductProperty() }
}