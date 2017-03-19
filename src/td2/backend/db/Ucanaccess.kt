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
import td2.client.ui.model.MobileInsuranceApp
import td2.client.ui.model.OnlineBanking
import td2.client.ui.model.OnlineInsurance
import td2.client.ui.model.OnlineInvestment
import td2.client.ui.model.Participant
import td2.client.ui.view.Filter
import td2.client.ui.view.FilterOptions
import td2.client.utils.toLocalDate
import java.io.IOException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class Ucanaccess(pathNewDB: String?) {
	private var ucaConn: Connection? = null

	init {
		try {
			ucaConn = getUcanaccessConnection(pathNewDB)
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

	@Throws(SQLException::class)
	public fun getAllParticipants(): Array<Participant> {
		var st: Statement? = null
		var participantsArray = arrayOf<Participant>()
		try {
			this.ucaConn!!.setAutoCommit(false)
			st = this.ucaConn!!.createStatement()
			var rs = st!!.executeQuery("SELECT * FROM ParticipantRecord ")
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
				if (otherTablet == null) {
					otherTablet = "N/A"
				}
				
				val device = Device(appleiPhone, appleiPad, appleiPodTouch, blackberryPlaybook, htc,
						lg, motorola, nexus, nokia, samsung, sony, otherTablet)
				
				// Consent
				val isConsent = rs.getBoolean("Consent")
				
				val participant = Participant(id, country, lastProject, lastContactDate, gender, age, email,
						schedule, location, onlineBanking, onlineInvestment, onlineInsurance, mobileBankingApp,
						mobileInsuranceApp, device, isConsent)
				participantsArray += participant
			}
        
		} finally {
			if (st != null)
				st.close()
		}
		
		return participantsArray
	}
	
	@Throws(SQLException::class)
	public fun isUserAuthorized(username: String, password: String): Boolean {
		var st: Statement? = null
		try {
			this.ucaConn!!.setAutoCommit(false)
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
	public fun getAllCountries(): ObservableList<String> {
		var st: Statement? = null
		var countries = FXCollections.observableArrayList<String>()
		try {
			this.ucaConn!!.setAutoCommit(false)
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
			this.ucaConn!!.setAutoCommit(false)
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
	public fun filterParticipants(filter: Filter): ObservableList<Participant> {
		var st: Statement? = null
		var participantList = FXCollections.observableArrayList<Participant>()
		try {
			this.ucaConn!!.setAutoCommit(false)
			st = this.ucaConn!!.createStatement()
			var andOr = "OR"
			if (filter.filterOption == FilterOptions.EXACT_MATCH) {
				andOr = "AND"
			}
			var query = StringBuilder()
			query.append("SELECT * FROM ParticipantRecord WHERE ")
			
			if (!filter.countries.isEmpty()) {
				for (country in filter.countries) {
					query.append("Country = '" + country + "' " + andOr)
				}
			}
			if (!filter.lastProjects.isEmpty()) {
				for (lastProject in filter.lastProjects) {
					query.append("LastProject = '" + lastProject + "' " + andOr)
				}
			}
			if (filter.email != "") {
				query.append("Email = '" + filter.email + "' " + andOr)
			}
			if (filter.lastContactedDate != null) {
				query.append("Email = '" + filter.email + "' " + andOr)
			}
			
			
			
			
				
				
				
				
				
				
				
				
				
			var rs = st!!.executeQuery("SELECT DISTINCT(LastProject) FROM ParticipantRecord")
			while (rs.next()) {
				//lastProjects.add(rs.getString("LastProject"))
			}
        
		} finally {
			if (st != null)
				st.close()
		}
		return participantList
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