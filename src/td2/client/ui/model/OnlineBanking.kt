package td2.client.ui.model

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.ItemViewModel
import tornadofx.getProperty
import tornadofx.property

object OnlineBankingConstants {
	const val NO_ONLINE_BANKING = "No"
	const val TD_EASYWEBR = "TD EasyWebr"
	const val BMO_ONLINE_BANKING = "BMO Online Banking"
	const val CIBC_ONLONE_BANKING = "CIBC Online Banking"
	const val RBC_ONLINE_BANKING = "RBC Online Banking"
	const val SCOTIA_ONLINE = "Scotia OnLine"
	const val TANGERINE_ONLINE_BANKING = "Tangerine Online Banking"
	const val PC_FINANCIAL_ONLINE_BANKING= "PC Financial Online Banking"
	const val HSBC_INTERNET_BANKING= "HSBC Internet Banking"
	const val NATIONAL_BANK_INTERNET_BANKING= "National Bank Internet Banking"
	const val OTHER = "Other"
}

fun getAllOnlineBankings(): ObservableList<String> {
	return FXCollections.observableArrayList(OnlineBankingConstants.NO_ONLINE_BANKING, OnlineBankingConstants.TD_EASYWEBR,
			OnlineBankingConstants.BMO_ONLINE_BANKING, OnlineBankingConstants.CIBC_ONLONE_BANKING, OnlineBankingConstants.RBC_ONLINE_BANKING,
			OnlineBankingConstants.SCOTIA_ONLINE, OnlineBankingConstants.TANGERINE_ONLINE_BANKING, OnlineBankingConstants.PC_FINANCIAL_ONLINE_BANKING,
			OnlineBankingConstants.HSBC_INTERNET_BANKING, OnlineBankingConstants.NATIONAL_BANK_INTERNET_BANKING, OnlineBankingConstants.OTHER)
}

class OnlineBanking(isOnlineBanking: Boolean, isTDEasyWeb: Boolean, isBMOOnlineBanking:Boolean,
					isCIBCOnlineBanking: Boolean, isRBCOnlineBanking: Boolean, isScotiaOnlineBanking: Boolean,
					isTangerineOnlineBanking: Boolean, isPCFinancialOnlineBanking: Boolean,
					isHSBCOnlineBanking: Boolean, isNationalBankOnlineBanking: Boolean, isOtherOB: Boolean,
					otherOnlineBanking: String) {
    var isOnlineBanking by property(isOnlineBanking)
    fun isOnlineBankingProperty() = getProperty(OnlineBanking::isOnlineBanking)
	
	var isTDEasyWeb by property(isTDEasyWeb)
	fun isTDEasyWebProperty() = getProperty(OnlineBanking::isTDEasyWeb)
	
	var isBMOOnlineBanking by property(isBMOOnlineBanking)
	fun isBMOOnlineBankingProperty() = getProperty(OnlineBanking::isBMOOnlineBanking)

    var isCIBCOnlineBanking by property(isCIBCOnlineBanking)
    fun isCIBCOnlineBankingProperty() = getProperty(OnlineBanking::isCIBCOnlineBanking)

    var isRBCOnlineBanking by property(isRBCOnlineBanking)
    fun isRBCOnlineBankingProperty() = getProperty(OnlineBanking::isRBCOnlineBanking)
	
	var isScotiaOnlineBanking by property(isScotiaOnlineBanking)
	fun isScotiaOnlineBankingProperty() = getProperty(OnlineBanking::isScotiaOnlineBanking)
	
	var isTangerineOnlineBanking by property(isTangerineOnlineBanking)
	fun isTangerineOnlineBankingProperty() = getProperty(OnlineBanking::isTangerineOnlineBanking)
	
	var isPCFinancialOnlineBanking by property(isPCFinancialOnlineBanking)
	fun isPCFinancialOnlineBankingProperty() = getProperty(OnlineBanking::isPCFinancialOnlineBanking)
	
	var isHSBCOnlineBanking by property(isHSBCOnlineBanking)
	fun isHSBCOnlineBankingProperty() = getProperty(OnlineBanking::isHSBCOnlineBanking)
	
	var isNationalBankOnlineBanking by property(isNationalBankOnlineBanking)
	fun isNationalBankOnlineBankingProperty() = getProperty(OnlineBanking::isNationalBankOnlineBanking)
	
	var isOtherOB by property(isOtherOB)
	fun isOtherOBProperty() = getProperty(OnlineBanking::isOtherOB)
	
	var otherOnlineBanking by property(otherOnlineBanking)
	fun otherOnlineBankingProperty() = getProperty(OnlineBanking::otherOnlineBanking)
}

class OnlineBankingModel : ItemViewModel<OnlineBanking>() {
    val isOnlineBanking = bind { item?.isOnlineBankingProperty() }
	val isTDEasyWeb = bind { item?.isTDEasyWebProperty() }
	val isBMOOnlineBanking = bind { item?.isBMOOnlineBankingProperty() }
    val isCIBCOnlineBanking = bind { item?.isCIBCOnlineBankingProperty() }
    val isRBCOnlineBanking = bind { item?.isRBCOnlineBankingProperty() }
	val isScotiaOnlineBanking = bind { item?.isScotiaOnlineBankingProperty() }
	val isTangerineOnlineBanking = bind { item?.isTangerineOnlineBankingProperty() }
	val isPCFinancialOnlineBanking = bind { item?.isPCFinancialOnlineBankingProperty() }
	val isHSBCOnlineBanking = bind { item?.isHSBCOnlineBankingProperty() }
	val isNationalBankOnlineBanking = bind { item?.isNationalBankOnlineBankingProperty() }
	val isOtherOB = bind { item ?.isOtherOBProperty() }
	val otherOnlineBanking = bind { item ?.otherOnlineBankingProperty() }
}