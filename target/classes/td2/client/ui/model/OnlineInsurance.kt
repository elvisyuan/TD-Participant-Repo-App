package td2.client.ui.model

import tornadofx.ItemViewModel
import tornadofx.getProperty
import tornadofx.property
import java.time.LocalDate

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