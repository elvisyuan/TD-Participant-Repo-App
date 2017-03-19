package td2.client.ui.model

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.ItemViewModel
import tornadofx.getProperty
import tornadofx.property

object MobileBankingConstants {
	const val NO_MOBILE_BANKING = "No"
	const val TD = "TD"
	const val BMO = "BMO"
	const val CIBC = "CIBC"
	const val RBC = "RBC"
	const val SCOTIABANK = "Scotiabank"
	const val TANGERINE = "Tangerine"
	const val PC_FINANCIAL = "PC Financial"
	const val HSBC = "HSBC"
	const val NATIONAL_BANK = "National Bank"
	const val OTHER = "Other"
}

fun getAllMobileBankings(): ObservableList<String> {
	return FXCollections.observableArrayList(MobileBankingConstants.NO_MOBILE_BANKING, MobileBankingConstants.TD, MobileBankingConstants.BMO,
			MobileBankingConstants.CIBC, MobileBankingConstants.RBC, MobileBankingConstants.SCOTIABANK, MobileBankingConstants.TANGERINE,
			MobileBankingConstants.PC_FINANCIAL, MobileBankingConstants.HSBC, MobileBankingConstants.NATIONAL_BANK, MobileBankingConstants.OTHER)
}

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
	
	fun getMobileBankings(): ObservableList<String> {
		var mobileBankings = FXCollections.observableArrayList<String>()
		if (isTD) {
			mobileBankings.add(MobileBankingConstants.TD)
		}
		if (isCIBC) {
			mobileBankings.add(MobileBankingConstants.CIBC)
		}
		if (isBMO) {
			mobileBankings.add(MobileBankingConstants.BMO)
		}
		if (isBMO) {
			mobileBankings.add(MobileBankingConstants.BMO)
		}
		if (isRBC) {
			mobileBankings.add(MobileBankingConstants.RBC)
		}
		if (isScotiabank) {
			mobileBankings.add(MobileBankingConstants.SCOTIABANK)
		}
		if (isTangerine) {
			mobileBankings.add(MobileBankingConstants.TANGERINE)
		}
		if (isPCFinancial) {
			mobileBankings.add(MobileBankingConstants.PC_FINANCIAL)
		}
		if (isHSBC) {
			mobileBankings.add(MobileBankingConstants.HSBC)
		}
		if (isNationalBank) {
			mobileBankings.add(MobileBankingConstants.NATIONAL_BANK)
		}
		if (isOtherApp) {
			mobileBankings.add(otherAppName)
		}
		return mobileBankings
	}
	var participantMobileBankings by property(getMobileBankings())
    fun participantMobileBankingsProperty() = getProperty(MobileBankingApp::participantMobileBankings)
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
	val participantMobileBankings = bind{ item?.participantMobileBankingsProperty()}
}