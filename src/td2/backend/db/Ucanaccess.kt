/*
Copyright (c) 2012 Marco Amadei.
This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.
This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
Lesser General Public License for more details.
You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
USA
You can contact Marco Amadei at amadei.mar@gmail.com.
*/
package td2.backend.db

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import net.ucanaccess.jdbc.UcanaccessConnection
import net.ucanaccess.jdbc.UcanaccessDriver
import td2.client.security.toMD5Hash
import td2.client.ui.model.Device
import td2.client.ui.model.MobileBankingApp
import td2.client.ui.model.MobileBankingConstants
import td2.client.ui.model.MobileInsuranceApp
import td2.client.ui.model.MobileInsuranceConstants
import td2.client.ui.model.OnlineBanking
import td2.client.ui.model.OnlineBankingConstants
import td2.client.ui.model.OnlineInsurance
import td2.client.ui.model.OnlineInsuranceConstants
import td2.client.ui.model.OnlineInvestment
import td2.client.ui.model.OnlineInvestmentConstants
import td2.client.ui.model.getAllMobileBrands
import td2.client.ui.model.Participant
import td2.client.ui.view.Filter
import td2.client.ui.view.FilterOptions
import td2.client.utils.toLocalDate
import td2.client.ui.view.FilteredTable.Person
import java.io.IOException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.time.LocalDate

class Ucanaccess(pathNewDB: String?) {
	private var ucaConn: Connection? = null

	init {
		try {
			ucaConn = getUcanaccessConnection(pathNewDB)
			this.ucaConn!!.setAutoCommit(true)
		} catch (e: SQLException) {
			e!!.printStackTrace()
		} catch (e: IOException) {
			e!!.printStackTrace()
		}
	}

	@Throws(SQLException::class)
	private fun createTablesExample() {
		var st = this.ucaConn!!.createStatement()
		st!!.execute("CREATE TABLE example1 (id COUNTER PRIMARY KEY,descr text(400), number numeric(12,3), date0 datetime) ")
		st!!.close()
		st = this.ucaConn!!.createStatement()
		st!!.execute("CREATE TABLE example2 (id COUNTER PRIMARY KEY,descr text(400))")
		st!!.close()
		st = this.ucaConn!!.createStatement()
		st!!.execute("CREATE TABLE example3 (id LONG PRIMARY KEY,descr text(400))")
		st!!.close()
		st = this.ucaConn!!.createStatement()
		st!!.execute("CREATE TABLE example4 (id LONG PRIMARY KEY,descr text(400))")
		st!!.close()
	}

	@Throws(SQLException::class)
	private fun executeLikeExample() {
		var st: Statement? = null
		try {
			st = this.ucaConn!!.createStatement()
			var rs = st!!.executeQuery("select descr from example2 where descr like 'P%'")
			dump(rs, "executeLikeExample STEP 1: like with standard % jolly")
			st = this.ucaConn!!.createStatement()
			rs = st!!.executeQuery("select descr from example2 where descr like 'P*'")
			dump(rs, "executeLikeExample STEP 2: like with access * jolly")
			st = this.ucaConn!!.createStatement()
			rs = st!!.executeQuery("select descr from example2 where descr like 'P[A-F]###'")
			dump(rs, "executeLikeExample STEP 3: number and interval patterns")
			rs = st!!.executeQuery("select descr from example2 where descr like 'C#V##'")
			dump(rs, "executeLikeExample STEP 4: number pattern")
		} finally {
			if (st != null)
				st!!.close()
		}
	}

	@Throws(SQLException::class)
	private fun executeQuery() {
		var st: Statement? = null
		try {
			st = this.ucaConn!!.createStatement()
			val rs = st!!.executeQuery("SELECT * from example1")
			dump(rs, "executeQuery")
		} finally {
			if (st != null)
				st!!.close()
		}
	}

	@Throws(SQLException::class)
	private fun executeQueryWithCustomFunction() {
		var st: Statement? = null
		try {
			val uc = this.ucaConn as UcanaccessConnection?
			st = this.ucaConn!!.createStatement()
			val rs = st!!.executeQuery("SELECT justConcat(descr,''&now()) from example1")
			dump(rs, "executeQueryWithCustomFunction")
		} finally {
			if (st != null)
				st!!.close()
		}
	}

	@Throws(SQLException::class)
	private fun executeQueryWithFunctions() {
		var st: Statement? = null
		try {
			st = this.ucaConn!!.createStatement()
			val rs = st!!.executeQuery("SELECT IIf(descr='Show must go off','tizio','caio&sempronio'&'&Marco Amadei'&' '&now()& RTRIM(' I''m proud of you ')) from example1")
			dump(rs, "executeQueryWithFunctions")
		} finally {
			if (st != null)
				st!!.close()
		}
	}

	@Throws(SQLException::class)
	private fun insertData() {
		var st: Statement? = null
		try {
			st = this.ucaConn!!.createStatement()
			st!!
					.execute("INSERT INTO example1 (descr,number,date0) VALUES( 'Show must go off',-1110.55446,#11/22/2003 10:42:58 PM#)")
			st!!
					.execute("INSERT INTO example1 (descr,number,date0) VALUES( \"Show must go up and down\",-113.55446,#11/22/2003 10:42:58 PM#)")
			st!!.execute("INSERT INTO example2 (descr) VALUES( 'dsdsds')")
			st!!.execute("INSERT INTO example2 (descr) VALUES( 'aa')")
			st!!.execute("INSERT INTO example2 (descr) VALUES( 'aBa')")
			st!!.execute("INSERT INTO example2 (descr) VALUES( 'aBBBa')")
			st!!.execute("INSERT INTO example2 (descr) VALUES( 'PB123')")
			st!!.execute("INSERT INTO example2 (descr) VALUES( 'C1V23')")
			st!!.execute("INSERT INTO example2 (descr) VALUES( 'aca')")
			st!!.execute("INSERT INTO example2 (descr) VALUES( 'Ada')")
			st!!.execute("INSERT INTO example3 (ID, descr) VALUES(1,'DALLAS')")
			st!!.execute("INSERT INTO example3 (ID, descr) VALUES(2,'MILANO')")
			st!!.execute("INSERT INTO example4 (ID, descr) VALUES(2,'PARIS')")
			st!!.execute("INSERT INTO example4 (ID, descr) VALUES(3,'LONDON')")
		} finally {
			if (st != null)
				st!!.close()
		}
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

	@Throws(SQLException::class)
	public fun getAllParticipants(): Array<Participant> {
		var st: Statement? = null
		try {
			st = this.ucaConn!!.createStatement()
			var rs = st!!.executeQuery("SELECT * FROM ParticipantRecord ")
			return getParticipantsFromRS(rs)
		} finally {
			if (st != null)
				st.close()
		}
	}
	
	@Throws(SQLException::class)
	public fun isUserAuthorized(username: String, password: String): Boolean {
		var st: Statement? = null
		try {
			st = this.ucaConn!!.createStatement()
			var rs = st!!.executeQuery("SELECT * FROM Authorization"
									 + " WHERE username = '" + username + "'")
			while (rs.next()) {
				val pass = rs.getString("password")
				return pass == toMD5Hash(password)
			}
        
		} finally {
			if (st != null)
				st.close()
		}
		return false;
	}
	
	@Throws(SQLException::class)
	public fun changePassword(username: String, password: String){
		var st: Statement? = null
		try {
			st = this.ucaConn!!.createStatement()
			val newpassword = toMD5Hash(password)
			var rs = st!!.execute("UPDATE Authorization SET password = '" + newpassword + "'"
									 + " WHERE username = '" + username + "'")
		} finally {
			if (st != null)
				st.close()
		}
	}
	
	@Throws(SQLException::class)
	public fun getUserRole(username: String): String {
		var st: Statement? = null
		try {
			st = this.ucaConn!!.createStatement()
			var rs = st!!.executeQuery("SELECT * FROM Authorization"
									 + " WHERE username = '" + username + "'")
			while (rs.next()) {
				val role = rs.getString("role")
				return role
			}
        
		} finally {
			if (st != null)
				st.close()
		}
		return ""
	}
	
	@Throws(SQLException::class)
	public fun getAllUsers(): ObservableList<Person> {
		var st: Statement? = null
		var persons = FXCollections.observableArrayList<Person>()
		try {
			st = this.ucaConn!!.createStatement()
			var rs = st!!.executeQuery("SELECT * FROM Authorization")
			while (rs.next()) {
				val person = Person(rs.getString("username"), rs.getString("role"))
				persons.add(person)
			}
        
		} finally {
			if (st != null)
				st.close()
		}
		return persons
	}
	
	@Throws(SQLException::class)
	public fun getAllCountries(): ObservableList<String> {
		var st: Statement? = null
		var countries = FXCollections.observableArrayList<String>()
		try {
			st = this.ucaConn!!.createStatement()
			var rs = st!!.executeQuery("SELECT DISTINCT(Country) FROM ParticipantRecord")
			while (rs.next()) {
				countries.add(rs.getString("Country"))
			}
        
		} finally {
			if (st != null)
				st.close()
		}
		return countries;
	}
	
	@Throws(SQLException::class)
	public fun getAllProjects(): ObservableList<String> {
		var st: Statement? = null
		var lastProjects = FXCollections.observableArrayList<String>()
		try {
			st = this.ucaConn!!.createStatement()
			var rs = st!!.executeQuery("SELECT DISTINCT(LastProject) FROM ParticipantRecord")
			while (rs.next()) {
				lastProjects.add(rs.getString("LastProject"))
			}
        
		} finally {
			if (st != null)
				st.close()
		}
		return lastProjects;
	}
	
	@Throws(SQLException::class)
	public fun getAllDevices(): ObservableList<String> {
		var st: Statement? = null
		var allDevices = FXCollections.observableArrayList<String>()
		try {
			st = this.ucaConn!!.createStatement()
			var allBrands = getAllMobileBrands()
			for (brand in allBrands) {
				devicesFromRS(st, brand, allDevices)
			}
		} finally {
			if (st != null)
				st.close()
		}
		return allDevices;
	}
	
	private fun devicesFromRS(st: Statement, device: String, allDevices: ObservableList<String>) {
		var rs = st!!.executeQuery("SELECT DISTINCT(" + device + ") FROM ParticipantRecord")
		while (rs.next()) {
			if (rs.getString(device) != "" && rs.getString(device) != null) {
				allDevices.add(rs.getString(device))
			}
		}
	}
	
	@Throws(SQLException::class)
	public fun deleteParticipantWithID(id: Int) {
		var st: Statement? = null
		try {
			st = this.ucaConn!!.createStatement()
			st!!.execute("DELETE FROM ParticipantRecord WHERE ID = " + id)
        
		} finally {
			if (st != null)
				st.close()
		}
	}
	
	@Throws(SQLException::class)
	public fun presistParticipantFromUI(participant: Participant) {
		var st: Statement? = null
		try {
			st = this.ucaConn!!.createStatement()
			var query = StringBuilder()
			
			var location:String?
			if (participant.location == "Downtown") {
				location = "Yes"
			} else {
				location = "No"
			}
			val onlineBanking = participant.onlineBanking
			val onlineInvestment = participant.onlineInvestment
			val onlineInsurance = participant.onlineInsurance
			val mobileBanking = participant.mobileBankingApp
			val mobileInsurance = participant.mobileInsuranceApp
			val devices = participant.device
			
			query.append("UPDATE ParticipantRecord SET Country = '" + participant.country + "', ")
			query.append("LastProject = '" + participant.lastProject + "', ")
			query.append("LastContactDate = #" + participant.lastContactedDate + "#, ")
			query.append("Email = '" + participant.email + "', ")
			query.append("Gender = '" + participant.gender + "', ")
			query.append("Age = '" + participant.age + "', ")
			query.append("Schedule = '" + participant.schedule + "', ")
			query.append("Downtown = " + location + ", ")
			
			// Online Banking
			if (onlineBanking.size > 0) {
				query.append("OnlineBanking = Yes, ")
			}
			if (onlineBanking.contains(OnlineBankingConstants.TD_EASYWEBR)) {
				query.append("TDEasyWeb = Yes, ")
			}
			if (onlineBanking.contains(OnlineBankingConstants.BMO_ONLINE_BANKING)) {
				query.append("BMOOnlineBanking = Yes, ")
			}
			if (onlineBanking.contains(OnlineBankingConstants.CIBC_ONLONE_BANKING)) {
				query.append("CIBCOnlineBanking = Yes, ")
			}
			if (onlineBanking.contains(OnlineBankingConstants.RBC_ONLINE_BANKING)) {
				query.append("RBCOnlineBanking = Yes, ")
			}
			if (onlineBanking.contains(OnlineBankingConstants.SCOTIA_ONLINE)) {
				query.append("ScotiaOnlineBanking = Yes, ")
			}
			if (onlineBanking.contains(OnlineBankingConstants.TANGERINE_ONLINE_BANKING)) {
				query.append("TangerineOnlineBanking = Yes, ")
			}
			if (onlineBanking.contains(OnlineBankingConstants.PC_FINANCIAL_ONLINE_BANKING)) {
				query.append("PCFinancialOnlineBanking = Yes, ")
			}
			if (onlineBanking.contains(OnlineBankingConstants.HSBC_INTERNET_BANKING)) {
				query.append("HSBCOnlineBanking = Yes, ")
			}
			if (onlineBanking.contains(OnlineBankingConstants.NATIONAL_BANK_INTERNET_BANKING)) {
				query.append("NationalBankOnlineBanking = Yes, ")
			}
			if (onlineBanking.contains(OnlineBankingConstants.OTHER)) {
				query.append("OtherOB = Yes, ")
			}
			
			// Online Investment
			if (onlineInvestment.size > 0) {
				query.append("OnlineInvesting = Yes, ")
			}
			if (onlineInvestment.contains(OnlineInvestmentConstants.TD_WEB_BROKER)) {
				query.append("TDWebBroker = Yes, ")
			}
			if (onlineInvestment.contains(OnlineInvestmentConstants.BMO_INVESTOR_LINE)) {
				query.append("BMOInvestorLine = Yes, ")
			}
			if (onlineInvestment.contains(OnlineInvestmentConstants.CIBC_INVESTOR_EDGE)) {
				query.append("CIBCInvestorEdge = Yes, ")
			}
			if (onlineInvestment.contains(OnlineInvestmentConstants.RBC_DIRECT_INVESTING)) {
				query.append("RBCDirectInvesting = Yes, ")
			}
			if (onlineInvestment.contains(OnlineInvestmentConstants.SCOTIA_ITRADE)) {
				query.append("ScotiaiTrade = Yes, ")
			}
			if (onlineInvestment.contains(OnlineInvestmentConstants.HSBC_INVESTDIRECT)) {
				query.append("HSBCInvestDirect = Yes, ")
			}
			if (onlineInvestment.contains(OnlineInvestmentConstants.NATIONALBANK_DIRECT_BROKERAGE)) {
				query.append("NationalBankDirectBrokerage = Yes, ")
			}
			if (onlineInvestment.contains(OnlineInvestmentConstants.OTHER)) {
				query.append("OtherIB = Yes, ")
			}
			
			// Online Insurance
			if (onlineInsurance.size > 0) {
				query.append("OnlineInsurance = Yes, ")
			}
			if (onlineInsurance.contains(OnlineInsuranceConstants.TD_MY_INSURANCE)) {
				query.append("TDMyInsurance = Yes, ")
			}
			if (onlineInsurance.contains(OnlineInsuranceConstants.RBC_ONLINE_INSURANCE)) {
				query.append("RBCOnlineInsurance = Yes, ")
			}
			if (onlineInsurance.contains(OnlineInsuranceConstants.OTHER)) {
				query.append("OtherInsurance = Yes, ")
			}
			
			// Mobile Banking
			if (mobileBanking.size > 0 && mobileInsurance.size > 0) {
				query.append("App = Yes, ")
			}
			if (mobileBanking.contains(MobileBankingConstants.TD)) {
				query.append("TD = Yes, ")
			}
			if (mobileBanking.contains(MobileBankingConstants.BMO)) {
				query.append("BMO = Yes, ")
			}
			if (mobileBanking.contains(MobileBankingConstants.CIBC)) {
				query.append("CIBC = Yes, ")
			}
			if (mobileBanking.contains(MobileBankingConstants.RBC)) {
				query.append("RBC = Yes, ")
			}
			if (mobileBanking.contains(MobileBankingConstants.SCOTIABANK)) {
				query.append("Scotiabank = Yes, ")
			}
			if (mobileBanking.contains(MobileBankingConstants.TANGERINE)) {
				query.append("Tangerine = Yes, ")
			}
			if (mobileBanking.contains(MobileBankingConstants.PC_FINANCIAL)) {
				query.append("PCFinancial = Yes, ")
			}
			if (mobileBanking.contains(MobileBankingConstants.HSBC)) {
				query.append("HSBC = Yes, ")
			}
			if (mobileBanking.contains(MobileBankingConstants.NATIONAL_BANK)) {
				query.append("NationalBank = Yes, ")
			}
			if (mobileBanking.contains(MobileBankingConstants.OTHER)) {
				query.append("OtherApp = Yes, ")
			}
			
			if (mobileInsurance.contains(MobileInsuranceConstants.TD)) {
				query.append("TD2 = Yes, ")
			}
			if (mobileInsurance.contains(MobileInsuranceConstants.RBC)) {
				query.append("RBC2 = Yes, ")
			}
			if (mobileInsurance.contains(MobileInsuranceConstants.OTHER)) {
				query.append("Other2 = Yes, ")
			}
			
			// Devices
			for (device in devices) {
				if (device.toLowerCase().contains("iphone")) {
					query.append("AppleiPhone = '" + device + "', ")
				}
				else if (device.toLowerCase().contains("ipad")) {
					query.append("AppleiPad = '" + device + "', ")
				}
				else if (device.toLowerCase().contains("ipod")) {
					query.append("AppleiPodTouch = '" + device + "', ")
				}
				else if (device.toLowerCase().contains("blackberry")) {
					query.append("BlackberryPlaybook = '" + device + "', ")
				}
				else if (device.toLowerCase().contains("htc")) {
					query.append("HTC = '" + device + "', ")
				}
				else if (device.toLowerCase().contains("moto")) {
					query.append("Motorola = '" + device + "', ")
				}
				else if (device.toLowerCase().contains("nexus")) {
					query.append("Nexus = '" + device + "', ")
				}
				else if (device.toLowerCase().contains("nokia")) {
					query.append("Nokia = '" + device + "', ")
				}
				else if (device.toLowerCase().contains("samsung")) {
					query.append("Samsung = '" + device + "', ")
				}
				else if (device.toLowerCase().contains("sony")) {
					query.append("Sony = '" + device + "', ")
				} else {
					query.append("OtherPhone = '" + device + "', ")
				}
			}
			
			// Consent
			if (participant.isConsent) {
				query.append("Consent = Yes ")
			} else {
				query.append("Consent = No ")
			}
			
			query.append("WHERE ID = " + participant.id)
			
			st!!.execute(query.toString())
		} finally {
			if (st != null)
				st.close()
		}
	}
	
	
	@Throws(SQLException::class)
	public fun filterParticipants(filter: Filter): ObservableList<Participant> {
		var st: Statement? = null
		try {
			st = this.ucaConn!!.createStatement()
			var andOr = "OR "
			if (filter.filterOption == FilterOptions.EXACT_MATCH) {
				andOr = "AND "
			}
			var query = StringBuilder()
			query.append("SELECT * FROM ParticipantRecord WHERE ")
			
			var whereclause = false
			
			if (!filter.countries.isEmpty()) {
				whereclause = true
				for (country in filter.countries) {
					query.append("Country = '" + country + "' " + andOr)
				}
			}
			if (!filter.lastProjects.isEmpty()) {
				whereclause = true
				for (lastProject in filter.lastProjects) {
					query.append("LastProject = '" + lastProject + "' " + andOr)
				}
			}
			if (filter.email != "") {
				whereclause = true
				query.append("Email = '" + filter.email + "' " + andOr)
			}
			if (filter.lastContactedDate != LocalDate.now().plusDays(1)) {
				whereclause = true
				query.append("LastContactDate = #" + filter.lastContactedDate + "# " + andOr)
			}
			if (!filter.genders.isEmpty()) {
				whereclause = true
				for (gender in filter.genders) {
					query.append("Gender = '" + gender + "' " + andOr)
				}
			}
			if (!filter.ages.isEmpty()) {
				whereclause = true
				for (age in filter.ages) {
					query.append("Age = '" + age + "' " + andOr)
				}
			}
			if (!filter.schedules.isEmpty()) {
				whereclause = true
				for (schedule in filter.schedules) {
					query.append("Schedule = '" + schedule + "' " + andOr)
				}
			}
			if (!filter.locations.isEmpty()) {
				whereclause = true
				for (location in filter.locations) {
					if (location == "Downtown") {
						query.append("Downtown = Yes " + andOr)
					}
					if (location == "Other") {
						query.append("Downtown = No " + andOr)
					}
				}
			}
			if (!filter.onlineBankings.isEmpty()) {
				whereclause = true
				for (onlineBanking in filter.onlineBankings) {
					if (onlineBanking == OnlineBankingConstants.NO_ONLINE_BANKING) {
						query.append("OnlineBanking = No " + andOr)
					}
					else if (onlineBanking == OnlineBankingConstants.TD_EASYWEBR) {
						query.append("TDEasyWeb = Yes " + andOr)
					}
					else if (onlineBanking == OnlineBankingConstants.BMO_ONLINE_BANKING) {
						query.append("BMOOnlineBanking = Yes " + andOr)
					}
					else if (onlineBanking == OnlineBankingConstants.CIBC_ONLONE_BANKING) {
						query.append("CIBCOnlineBanking = Yes " + andOr)
					}
					else if (onlineBanking == OnlineBankingConstants.RBC_ONLINE_BANKING) {
						query.append("RBCOnlineBanking = Yes " + andOr)
					}
					else if (onlineBanking == OnlineBankingConstants.SCOTIA_ONLINE) {
						query.append("ScotiaOnlineBanking = Yes " + andOr)
					}
					else if (onlineBanking == OnlineBankingConstants.TANGERINE_ONLINE_BANKING) {
						query.append("TangerineOnlineBanking = Yes " + andOr)
					}
					else if (onlineBanking == OnlineBankingConstants.PC_FINANCIAL_ONLINE_BANKING) {
						query.append("PCFinancialOnlineBanking = Yes " + andOr)
					}
					else if (onlineBanking == OnlineBankingConstants.HSBC_INTERNET_BANKING) {
						query.append("HSBCOnlineBanking = Yes " + andOr)
					}
					else if (onlineBanking == OnlineBankingConstants.NATIONAL_BANK_INTERNET_BANKING) {
						query.append("NationalBankOnlineBanking = Yes " + andOr)
					}
					else if (onlineBanking == OnlineBankingConstants.OTHER) {
						query.append("OtherOB = Yes " + andOr)
					} else {
						print("Unexpected Online Banking: " + onlineBanking)
					}
				}
			}
			if (!filter.onlineInvestments.isEmpty()) {
				whereclause = true
				for (onlineInvestment in filter.onlineInvestments) {
					if (onlineInvestment == OnlineInvestmentConstants.NO_ONLINE_INVESTMENT) {
						query.append("OnlineInvesting = No " + andOr)
					}
					else if (onlineInvestment == OnlineInvestmentConstants.TD_WEB_BROKER) {
						query.append("TDWebBroker = Yes " + andOr)
					}
					else if (onlineInvestment == OnlineInvestmentConstants.BMO_INVESTOR_LINE) {
						query.append("BMOInvestorLine = Yes " + andOr)
					}
					else if (onlineInvestment == OnlineInvestmentConstants.CIBC_INVESTOR_EDGE) {
						query.append("CIBCInvestorEdge = Yes " + andOr)
					}
					else if (onlineInvestment == OnlineInvestmentConstants.RBC_DIRECT_INVESTING) {
						query.append("RBCDirectInvesting = Yes " + andOr)
					}
					else if (onlineInvestment == OnlineInvestmentConstants.SCOTIA_ITRADE) {
						query.append("ScotiaiTrade = Yes " + andOr)
					}
					else if (onlineInvestment == OnlineInvestmentConstants.HSBC_INVESTDIRECT) {
						query.append("HSBCInvestDirect = Yes " + andOr)
					}
					else if (onlineInvestment == OnlineInvestmentConstants.NATIONALBANK_DIRECT_BROKERAGE) {
						query.append("NationalBankDirectBrokerage = Yes " + andOr)
					}
					else if (onlineInvestment == OnlineInvestmentConstants.OTHER) {
						query.append("OtherIB = Yes " + andOr)
					}
					else {
						print("Unexpected Online Investment: " + onlineInvestment)
					}
				}
			}
			if (!filter.onlineInsurance.isEmpty()) {
				whereclause = true
				for (onlineInsurance in filter.onlineInsurance) {
					if (onlineInsurance == OnlineInsuranceConstants.NO_ONLINE_INSURANCE) {
						query.append("OnlineInsurance = No " + andOr)
					}
					else if (onlineInsurance == OnlineInsuranceConstants.TD_MY_INSURANCE) {
						query.append("TDMyInsurance = Yes " + andOr)
					}
					else if (onlineInsurance == OnlineInsuranceConstants.RBC_ONLINE_INSURANCE) {
						query.append("RBCOnlineInsurance = Yes " + andOr)
					}
					else if (onlineInsurance == OnlineInsuranceConstants.OTHER) {
						query.append("OtherInsurance = Yes " + andOr)
					}
					else {
						print("Unexpected Online Insurance: " + onlineInsurance)
					}
				}
			}
			if (!filter.mobileBankings.isEmpty()) {
				whereclause = true
				for (mobileBanking in filter.mobileBankings) {
					if (mobileBanking == MobileBankingConstants.NO_MOBILE_BANKING) {
						query.append("App = No " + andOr)
					}
					else if (mobileBanking == MobileBankingConstants.TD) {
						query.append("TD = Yes " + andOr)
					}
					else if (mobileBanking == MobileBankingConstants.BMO) {
						query.append("BMO = Yes " + andOr)
					}
					else if (mobileBanking == MobileBankingConstants.CIBC) {
						query.append("CIBC = Yes " + andOr)
					}
					else if (mobileBanking == MobileBankingConstants.RBC) {
						query.append("RBC = Yes " + andOr)
					}
					else if (mobileBanking == MobileBankingConstants.SCOTIABANK) {
						query.append("Scotiabank = Yes " + andOr)
					}
					else if (mobileBanking == MobileBankingConstants.TANGERINE) {
						query.append("Tangerine = Yes " + andOr)
					}
					else if (mobileBanking == MobileBankingConstants.PC_FINANCIAL) {
						query.append("PCFinancial = Yes " + andOr)
					}
					else if (mobileBanking == MobileBankingConstants.HSBC) {
						query.append("HSBC = Yes " + andOr)
					}
					else if (mobileBanking == MobileBankingConstants.NATIONAL_BANK) {
						query.append("NationalBank = Yes " + andOr)
					}
					else if (mobileBanking == MobileBankingConstants.OTHER) {
						query.append("OtherApp = Yes " + andOr)
					}
					else {
						print("Unexpected Mobile Banking: " + mobileBanking)
					}
				}
			}
			if (!filter.mobileInsurance.isEmpty()) {
				whereclause = true
				for (mobileInsurance in filter.mobileInsurance) {
					if (mobileInsurance == MobileInsuranceConstants.TD) {
						query.append("TD2 = Yes " + andOr)
					}
					else if (mobileInsurance == MobileInsuranceConstants.RBC) {
						query.append("RBC2 = Yes " + andOr)
					}
					else if (mobileInsurance == MobileInsuranceConstants.OTHER) {
						query.append("Other2 = Yes " + andOr)
					}
					else {
						print("Unexpected Mobile Insurance: " + mobileInsurance)
					}
				}
			}
			if (!filter.devices.isEmpty()) {
				whereclause = true
				for (device in filter.devices) {
					if (device.toLowerCase().contains("iphone")) {
						query.append("AppleiPhone = '" + device + "' " + andOr)
					}
					else if (device.toLowerCase().contains("ipad")) {
						query.append("AppleiPad = '" + device + "' " + andOr)
					}
					else if (device.toLowerCase().contains("ipod")) {
						query.append("AppleiPodTouch = '" + device + "' " + andOr)
					}
					else if (device.toLowerCase().contains("blackberry")) {
						query.append("BlackberryPlaybook = '" + device + "' " + andOr)
					}
					else if (device.toLowerCase().contains("htc")) {
						query.append("HTC = '" + device + "' " + andOr)
					}
					else if (device.toLowerCase().contains("moto")) {
						query.append("Motorola = '" + device + "' " + andOr)
					}
					else if (device.toLowerCase().contains("nexus")) {
						query.append("Nexus = '" + device + "' " + andOr)
					}
					else if (device.toLowerCase().contains("nokia")) {
						query.append("Nokia = '" + device + "' " + andOr)
					}
					else if (device.toLowerCase().contains("samsung")) {
						query.append("Samsung = '" + device + "' " + andOr)
					}
					else if (device.toLowerCase().contains("sony")) {
						query.append("Sony = '" + device + "' " + andOr)
					}
					else {
						query.append("OtherPhone = '" + device + "' OR OtherTablet = '" + device + "' " + andOr)
					}
				}
			}
			if (filter.consent) {
				whereclause = true
				query.append("Consent = Yes " + andOr)
			}
			var qryString = query.toString()
			if (whereclause) {
				if (andOr == "AND") {
					qryString = qryString.substring(0, query.length - 5)
				} else {
					qryString = qryString.substring(0, query.length - 4)
				}
			} else {
				qryString = "SELECT * FROM ParticipantRecord"
			}

			var rs = st!!.executeQuery(qryString)
			var participants = FXCollections.observableArrayList<Participant>()
			participants.addAll(getParticipantsFromRS(rs))
			return participants
		} finally {
			if (st != null)
				st.close()
		}
	}

	@Throws(SQLException::class)
	private fun showExtensions() {
		var st: Statement? = null
		try {
			st = this.ucaConn!!.createStatement()
			var rs = st!!.executeQuery("SELECT * FROM example3 full outer join example4 on (example3.id=example4.id)")
			dump(rs, "showExtensions: full outer join")
			st = this.ucaConn!!.createStatement()
			rs = st!!.executeQuery("SELECT * FROM example2 order by id desc limit 5 offset 1")
			dump(rs, "showExtensions: limit and offset ")
		} finally {
			if (st != null)
				st!!.close()
		}
	}

	companion object {

		@Throws(SQLException::class)
		private fun dump(rs: ResultSet?, exName: String?) {
			System.out.println("-------------------------------------------------")
			System.out.println()
			System.out.println(exName!! + " result:")
			System.out.println()
			while (rs!!.next()) {
				System.out.print("| ")
				val j = rs!!.getMetaData().getColumnCount()
				for (i in 1..j) {
					val o = rs!!.getObject(i)
					print(o!!.toString() +  " | ")
				}
				System.out.println()
				System.out.println()
			}
		}

		@Throws(SQLException::class, IOException::class)
		private fun getUcanaccessConnection(pathNewDB: String?): Connection? {
			val url = UcanaccessDriver.URL_PREFIX + pathNewDB + ";newDatabaseVersion=V2003"
			return DriverManager.getConnection(url, "sa", "")
		}

		@Throws(ClassNotFoundException::class, SQLException::class)
		fun main(args: Array<String>?) {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver")
			try {
				if (args!!.size == 0) {
					System.err.println("You must specify new Database Access location (full path)")
					return
				}
				val ex = Ucanaccess(args!![0])
				ex!!.createTablesExample()
				ex!!.insertData()
				ex!!.executeQuery()
				ex!!.executeQueryWithFunctions()
				ex!!.executeQueryWithCustomFunction()
				ex!!.executeLikeExample()
				ex!!.showExtensions()
			} catch (e: Exception) {
				e!!.printStackTrace()
			}
		}
	}
}