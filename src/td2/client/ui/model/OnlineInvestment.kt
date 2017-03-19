package td2.client.ui.model

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.ItemViewModel
import tornadofx.getProperty
import tornadofx.property

object OnlineInvestmentConstants {
	const val NO_ONLINE_INVESTMENT = "No"
	const val TD_WEB_BROKER = "TD WebBroker"
	const val BMO_INVESTOR_LINE = "BMO InvestorLine"
	const val CIBC_INVESTOR_EDGE = "CIBC Investor's Edge"
	const val RBC_DIRECT_INVESTING = "RBC Direct Investing"
	const val SCOTIA_ITRADE = "Scotia iTrade"
	const val HSBC_INVESTDIRECT = "HSBC InvestDirect"
	const val NATIONALBANK_DIRECT_BROKERAGE= "National Bank Direct Brokerage"
	const val OTHER = "Other"
}

fun getAllOnlineInvestments(): ObservableList<String> {
	return FXCollections.observableArrayList(OnlineInvestmentConstants.NO_ONLINE_INVESTMENT, OnlineInvestmentConstants.TD_WEB_BROKER, OnlineInvestmentConstants.BMO_INVESTOR_LINE,
			OnlineInvestmentConstants.CIBC_INVESTOR_EDGE, OnlineInvestmentConstants.RBC_DIRECT_INVESTING, OnlineInvestmentConstants.HSBC_INVESTDIRECT,
			OnlineInvestmentConstants.SCOTIA_ITRADE, OnlineInvestmentConstants.NATIONALBANK_DIRECT_BROKERAGE, OnlineInvestmentConstants.OTHER) 
}


class OnlineInvestment(isOnlineInvesting: Boolean, isTDWebBroker: Boolean, isBMOInvestorLine:Boolean,
					isCIBCInvestorEdge: Boolean, isRBCDirectInvesting: Boolean, isScotiaiTrade: Boolean,
					isHSBCInvestDirect: Boolean, isNationalBankDirectBrokerage: Boolean, isOtherIB: Boolean, otherInvestmentBanking: String) {
    var isOnlineInvesting by property(isOnlineInvesting)
    fun isOnlineInvestingProperty() = getProperty(OnlineInvestment::isOnlineInvesting)
	
	var isTDWebBroker by property(isTDWebBroker)
	fun isTDWebBrokerProperty() = getProperty(OnlineInvestment::isTDWebBroker)
	
	var isBMOInvestorLine by property(isBMOInvestorLine)
	fun isBMOInvestorLineProperty() = getProperty(OnlineInvestment::isBMOInvestorLine)

    var isCIBCInvestorEdge by property(isCIBCInvestorEdge)
    fun isCIBCInvestorEdgeProperty() = getProperty(OnlineInvestment::isCIBCInvestorEdge)

    var isRBCDirectInvesting by property(isRBCDirectInvesting)
    fun isRBCDirectInvestingProperty() = getProperty(OnlineInvestment::isRBCDirectInvesting)
	
	var isScotiaiTrade by property(isScotiaiTrade)
	fun isScotiaiTradeProperty() = getProperty(OnlineInvestment::isScotiaiTrade)
	
	var isHSBCInvestDirect by property(isHSBCInvestDirect)
	fun isHSBCInvestDirectProperty() = getProperty(OnlineInvestment::isHSBCInvestDirect)
	
	var isNationalBankDirectBrokerage by property(isNationalBankDirectBrokerage)
	fun isNationalBankDirectBrokerageProperty() = getProperty(OnlineInvestment::isNationalBankDirectBrokerage)
	
	var isOtherIB by property(isOtherIB)
	fun isOtherIBProperty() = getProperty(OnlineInvestment::isOtherIB)
	
	var otherInvestmentBanking by property(otherInvestmentBanking)
	fun otherInvestmentBankingProperty() = getProperty(OnlineInvestment::otherInvestmentBanking)	
}

class OnlineInvestmentModel : ItemViewModel<OnlineInvestment>() {
    val isOnlineInvesting = bind { item?.isOnlineInvestingProperty() }
	val isTDWebBroker = bind { item?.isTDWebBrokerProperty() }
	val isBMOInvestorLine = bind { item?.isBMOInvestorLineProperty() }
    val isCIBCInvestorEdge = bind { item?.isCIBCInvestorEdgeProperty() }
    val isRBCDirectInvesting = bind { item?.isRBCDirectInvestingProperty() }
	val isScotiaiTrade = bind { item?.isScotiaiTradeProperty() }
	val isHSBCInvestDirect = bind { item?.isHSBCInvestDirectProperty() }
	val isNationalBankDirectBrokerage = bind { item?.isNationalBankDirectBrokerageProperty() }
	val isOtherIB = bind { item?.isOtherIBProperty() }
	val otherInvestmentBanking = bind { item?.otherInvestmentBankingProperty() }
}