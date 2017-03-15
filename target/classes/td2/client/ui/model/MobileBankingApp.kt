package td2.client.ui.model

import tornadofx.ItemViewModel
import tornadofx.getProperty
import tornadofx.property
import java.time.LocalDate

class MobileBankingApp(isApp: Boolean, isTD: Boolean, isBMO: Boolean,
				  isCIBC: Boolean, isRBC: Boolean, isScotiabank: Boolean, isTangerine: Boolean, 
				  isPCFinancial: Boolean, isHSBC: Boolean, isNationalBank: Boolean,
				  isOtherApp: Boolean, otherAppName: String) {
    var isApp by property(isApp)
    fun isAppProperty() = getProperty(MobileBankingApp::isApp)
	
	var isTD by property(isTD)
	fun isTDProperty() = getProperty(MobileBankingApp::isTD)
	
	var isCIBC by property(isCIBC)
	fun isCIBCProperty() = getProperty(MobileBankingApp::isCIBC)
	
	var isBMO by property(isBMO)
	fun isBMOProperty() = getProperty(MobileBankingApp::isBMO)

    var isRBC by property(isRBC)
    fun isRBCProperty() = getProperty(MobileBankingApp::isRBC)
	
	var isScotiabank by property(isScotiabank)
    fun isScotiabankProperty() = getProperty(MobileBankingApp::isScotiabank)
	
	var isTangerine by property(isTangerine)
    fun isTangerineProperty() = getProperty(MobileBankingApp::isTangerine)
	
	var isPCFinancial by property(isPCFinancial)
    fun isPCFinancialProperty() = getProperty(MobileBankingApp::isPCFinancial)
	
	var isHSBC by property(isHSBC)
    fun isHSBCProperty() = getProperty(MobileBankingApp::isHSBC)
	
	var isNationalBank by property(isNationalBank)
    fun isNationalBankProperty() = getProperty(MobileBankingApp::isNationalBank)

    var isOtherApp by property(isOtherApp)
    fun isOtherAppProperty() = getProperty(MobileBankingApp::isOtherApp)
	
	var otherAppName by property(otherAppName)
    fun otherAppNameProperty() = getProperty(MobileBankingApp::otherAppName)
}

class MobileBankingAppModel : ItemViewModel<MobileBankingApp>() {
    val isApp = bind { item?.isAppProperty() }
	val isTD = bind { item?.isTDProperty() }
	val isCIBC = bind { item?.isCIBCProperty() }
    val isBMO = bind { item?.isBMOProperty() }
    val isRBC = bind { item?.isRBCProperty() }
	val isScotiabank = bind { item?.isScotiabankProperty() }
	val isTangerine = bind { item?.isTangerineProperty() }
	val isPCFinancial = bind { item?.isPCFinancialProperty() }
	val isHSBC = bind { item?.isHSBCProperty() }
	val isNationalBank = bind { item?.isNationalBankProperty() }
	val isOtherApp = bind { item?.isOtherAppProperty() }
	val otherAppName = bind { item?.otherAppNameProperty() }
}