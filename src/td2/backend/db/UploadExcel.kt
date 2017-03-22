package td2.backend.db

import td2.client.ui.model.Device
import td2.client.ui.model.MobileBankingApp
import td2.client.ui.model.MobileInsuranceApp
import td2.client.ui.model.OnlineBanking
import td2.client.ui.model.OnlineInsurance
import td2.client.ui.model.OnlineInvestment
import td2.client.ui.model.Participant
import td2.client.utils.toLocalDate
import java.io.IOException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDate

class ExcelConnection(pathNewDB: String?) {
	private var ucaConn: Connection? = null
	private val url = "jdbc:odbc:"
	private val username = "sa";
    private val password = "";
	
	init {
		try {
			ucaConn = getExcelConnection(pathNewDB)
			this.ucaConn!!.setAutoCommit(true)
		} catch (e: SQLException) {
			e.printStackTrace()
		} catch (e: IOException) {
			e.printStackTrace()
		}
	}
	
	@Throws(SQLException::class, IOException::class)
	private fun getExcelConnection(pathNewDB: String?): Connection? {
		val url = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ=" + pathNewDB + ";"
		return DriverManager.getConnection(url, username, password)
	}	
	
	private fun getParticipantsFromRS(rs: ResultSet): Array<Participant> {
		var participantsArray = arrayOf<Participant>()
		while (rs.next()) {
			// Basic Info
			val id = rs.getInt("ID")
			val country = rs.getString("Country")
			val lastProject = rs.getString("LastProject")
			val lastContactDate = toLocalDate(rs.getDate("LastContactDate"))
			val email = rs.getString("Email")
			val gender = rs.getString("Gender")
			val age = rs.getString("Age")
			val schedule = rs.getString("Schedule")
			val isDowntown = rs.getBoolean("Downtown")
			var location: String?
			if (isDowntown) {
				location = "Downtown"
			} else {
				location = "Other"
			}

			// Online Banking
			val isOnlineBanking = rs.getBoolean("OnlineBanking")
			val isTDEasyWeb = rs.getBoolean("TDEasyWeb")
			val isBMOOnlineBanking = rs.getBoolean("BMOOnlineBanking")
			val isCIBCOnlineBanking = rs.getBoolean("CIBCOnlineBanking")
			val isRBCOnlineBanking = rs.getBoolean("RBCOnlineBanking")
			val isScotiaOnlineBanking = rs.getBoolean("ScotiaOnlineBanking")
			val isTangerineOnlineBanking = rs.getBoolean("TangerineOnlineBanking")
			val isPCFinancialOnlineBanking = rs.getBoolean("PCFinancialOnlineBanking")
			val isHSBCOnlineBanking = rs.getBoolean("HSBCOnlineBanking")
			val isNationalBankOnlineBanking = rs.getBoolean("NationalBankOnlineBanking")
			val isOtherOB = rs.getBoolean("OtherOB")
			var otherOnlineBanking = rs.getString("OtherOnlineBanking")

			if (otherOnlineBanking == null) {
				otherOnlineBanking = "N/A"
			}

			val onlineBanking = OnlineBanking(isOnlineBanking, isTDEasyWeb, isBMOOnlineBanking,
					isCIBCOnlineBanking, isRBCOnlineBanking, isScotiaOnlineBanking, isTangerineOnlineBanking,
					isPCFinancialOnlineBanking, isHSBCOnlineBanking, isNationalBankOnlineBanking,
					isOtherOB, otherOnlineBanking)

			// Online Investment
			val isOnlineInvesting = rs.getBoolean("OnlineInvesting")
			val isTDWebBroker = rs.getBoolean("TDWebBroker")
			val isBMOInvestorLine = rs.getBoolean("BMOInvestorLine")
			val isCIBCInvestorEdge = rs.getBoolean("CIBCInvestorEdge")
			val isRBCDirectInvesting = rs.getBoolean("RBCDirectInvesting")
			val isScotiaiTrade = rs.getBoolean("ScotiaiTrade")
			val isHSBCInvestDirect = rs.getBoolean("HSBCInvestDirect")
			val isNationalBankDirectBrokerage = rs.getBoolean("NationalBankDirectBrokerage")
			val isOtherIB = rs.getBoolean("OtherIB")
			var otherInvestmentBanking = rs.getString("OtherInvestmentBanking")

			if (otherInvestmentBanking == null) {
				otherInvestmentBanking = "N/A"
			}

			val onlineInvestment = OnlineInvestment(isOnlineInvesting, isTDWebBroker, isBMOInvestorLine,
					isCIBCInvestorEdge, isRBCDirectInvesting, isScotiaiTrade, isHSBCInvestDirect,
					isNationalBankDirectBrokerage, isOtherIB, otherInvestmentBanking)

			// Online Insurance
			val isOnlineInsurance = rs.getBoolean("OnlineInsurance")
			val isTDMyInsurance = rs.getBoolean("TDMyInsurance")
			val isRBCOnlineInsurance = rs.getBoolean("RBCOnlineInsurance")
			val isOtherInsurance = rs.getBoolean("OtherInsurance")
			var otherInsuranceProduct = rs.getString("OtherInsuranceProduct")

			if (otherInsuranceProduct == null) {
				otherInsuranceProduct = "N/A"
			}

			val onlineInsurance = OnlineInsurance(isOnlineInsurance, isTDMyInsurance, isRBCOnlineInsurance,
					isOtherInsurance, otherInsuranceProduct)

			// Mobile banking apps
			val isApp = rs.getBoolean("App")
			val isTD = rs.getBoolean("TD")
			val isBMO = rs.getBoolean("BMO")
			val isCIBC = rs.getBoolean("CIBC")
			val isRBC = rs.getBoolean("RBC")
			val isScotiabank = rs.getBoolean("Scotiabank")
			val isTangerine = rs.getBoolean("Tangerine")
			val isPCFinancial = rs.getBoolean("PCFinancial")
			val isHSBC = rs.getBoolean("HSBC")
			val isNationalBank = rs.getBoolean("NationalBank")
			val isOtherApp = rs.getBoolean("OtherApp")
			var otherAppName = rs.getString("OtherAppName")

			if (otherAppName == null) {
				otherAppName = "N/A"
			}

			val mobileBankingApp = MobileBankingApp(isApp, isTD, isBMO, isCIBC, isRBC, isScotiabank, isTangerine,
					isPCFinancial, isHSBC, isNationalBank, isOtherApp, otherAppName)

			// Mobile Insurance App
			val isTD2 = rs.getBoolean("TD2")
			val isRBC2 = rs.getBoolean("RBC2")
			val isOther2 = rs.getBoolean("Other2")
			var other2Name = rs.getString("Other2Name")

			if (other2Name == null) {
				other2Name = "N/A"
			}

			val mobileInsuranceApp = MobileInsuranceApp(isTD2, isRBC2, isOther2, other2Name)

			// Device
			var appleiPhone = rs.getString("AppleiPhone")
			var appleiPad = rs.getString("AppleiPad")
			var appleiPodTouch = rs.getString("AppleiPodTouch")
			var blackberryPlaybook = rs.getString("BlackberryPlaybook")
			var htc = rs.getString("HTC")
			var lg = rs.getString("LG")
			var motorola = rs.getString("Motorola")
			var nexus = rs.getString("Nexus")
			var nokia = rs.getString("Nokia")
			var samsung = rs.getString("Samsung")
			var sony = rs.getString("Sony")
			var otherPhone = rs.getString("OtherPhone")
			var otherTablet = rs.getString("OtherTablet")

			if (appleiPhone == null) {
				appleiPhone = "N/A"
			}
			if (appleiPad == null) {
				appleiPad = "N/A"
			}
			if (appleiPodTouch == null) {
				appleiPodTouch = "N/A"
			}
			if (blackberryPlaybook == null) {
				blackberryPlaybook = "N/A"
			}
			if (htc == null) {
				htc = "N/A"
			}
			if (lg == null) {
				lg = "N/A"
			}
			if (motorola == null) {
				motorola = "N/A"
			}
			if (nexus == null) {
				nexus = "N/A"
			}
			if (nokia == null) {
				nokia = "N/A"
			}
			if (samsung == null) {
				samsung = "N/A"
			}
			if (sony == null) {
				sony = "N/A"
			}
			if (otherPhone == null) {
				otherPhone = "N/A"
			}
			if (otherTablet == null) {
				otherTablet = "N/A"
			}

			val device = Device(appleiPhone, appleiPad, appleiPodTouch, blackberryPlaybook, htc,
					lg, motorola, nexus, nokia, samsung, sony, otherPhone, otherTablet)

			// Consent
			val isConsent = rs.getBoolean("Consent")

			val participant = Participant(id, country, lastProject, lastContactDate, gender, age, email,
					schedule, location, onlineBanking, onlineInvestment, onlineInsurance, mobileBankingApp,
					mobileInsuranceApp, device, isConsent)
			participantsArray += participant
		}
		return participantsArray
	}

}