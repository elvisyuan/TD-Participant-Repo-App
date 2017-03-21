package td2.client.ui.model

import tornadofx.ItemViewModel
import tornadofx.getProperty
import tornadofx.property
import java.time.LocalDate

class Participant(id: Int, country: String, lastProject:String, lastContactedDate: LocalDate, gender: String, age: String, email: String, schedule: String,
				  location: String, onlineBanking: OnlineBanking, onlineInvestment: OnlineInvestment,
				  onlineInsurance: OnlineInsurance, mobileBankingApp: MobileBankingApp,
				  mobileInsuranceApp: MobileInsuranceApp, device: Device, isConsent: Boolean) {
    var id by property(id)
    fun idProperty() = getProperty(Participant::id)
	
	var country by property(country)
    fun countryProperty() = getProperty(Participant::country)
	
	var lastProject by property(lastProject)
	fun lastProjectProperty() = getProperty(Participant::lastProject)
	
	var lastContactedDate by property(lastContactedDate)
	fun lastContactedDateProperty() = getProperty(Participant::lastContactedDate)

    var gender by property(gender)
    fun genderProperty() = getProperty(Participant::gender)
	
	var age by property(age)
	fun ageProperty() = getProperty(Participant::age)
	
	var email by property(email)
	fun emailProperty() = getProperty(Participant::email)
	
	var schedule by property(schedule)
	fun scheduleProperty() = getProperty(Participant::schedule)
	
	var location by property(location)
	fun locationProperty() = getProperty(Participant::location)
	
	var onlineBanking by property(onlineBanking.participantOnlineBankings)
	fun onlineBankingProperty() = getProperty(Participant::onlineBanking)
	
	var onlineInvestment by property(onlineInvestment.participantOnlineInvestments)
	fun onlineInvestmentProperty() = getProperty(Participant::onlineInvestment)
	
	var onlineInsurance by property(onlineInsurance.participantOnlineInsurances)
	fun onlineInsuranceProperty() = getProperty(Participant::onlineInsurance)
	
	var mobileBankingApp by property(mobileBankingApp.participantMobileBankings)
	fun mobileBankingApptProperty() = getProperty(Participant::mobileBankingApp)
	
	var mobileInsuranceApp by property(mobileInsuranceApp.participantMobileInsurance)
	fun mobileInsuranceAppProperty() = getProperty(Participant::mobileInsuranceApp)
	
	var device by property(device.participantDevices)
	fun deviceProperty() = getProperty(Participant::device)
	
	var isConsent by property(isConsent)
	fun isConsentProperty() = getProperty(Participant::isConsent)
}

class ParticipantModel : ItemViewModel<Participant>() {
    val id = bind { item?.idProperty() }
	val country = bind { item?.countryProperty() }
	val lastProject = bind { item?.lastProjectProperty() }
	val lastContactedDate = bind { item?.lastContactedDateProperty() }
    val gender = bind { item?.genderProperty() }
	val age = bind { item?.ageProperty() }
	val email = bind { item?.emailProperty() }
	val schedule = bind { item?.scheduleProperty() }
	val location = bind { item?.locationProperty() }
	val onlineBanking = bind { item?.onlineBankingProperty() }
	val onlineInvestment = bind { item ?.onlineInvestmentProperty() }
	val onlineInsurance = bind { item ?.onlineInsuranceProperty() }
	val mobileBankingApp = bind { item ?.mobileBankingApptProperty() }
	val mobileInsuranceApp = bind { item ?.mobileInsuranceAppProperty() }
	val device = bind { item ?.deviceProperty() }
	val isConsent = bind { item ?.isConsentProperty() }
}
