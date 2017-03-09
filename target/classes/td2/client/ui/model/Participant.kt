package td2.client.ui.model

import tornadofx.ItemViewModel
import tornadofx.getProperty
import tornadofx.property
import java.time.LocalDate

class Participant(id: Int, lastProject:String, date: LocalDate, country: String, gender: String, age: String, email: String, schedule: String,
				  location: String, onlineBanking: String, onlineInvestment: String) {
    var id by property(id)
    fun idProperty() = getProperty(Participant::id)
	
	var lastProject by property(lastProject)
	fun lastProjectProperty() = getProperty(Participant::lastProject)
	
	var date by property(date)
	fun dateProperty() = getProperty(Participant::date)

    var country by property(country)
    fun countryProperty() = getProperty(Participant::country)

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
	
	var onlineBanking by property(onlineBanking)
	fun onlineBankingProperty() = getProperty(Participant::onlineBanking)
	
	var onlineInvestment by property(onlineInvestment)
	fun onlineInvestmentProperty() = getProperty(Participant::onlineInvestment)
}

class ParticipantModel : ItemViewModel<Participant>() {
    val id = bind { item?.idProperty() }
	val lastProject = bind { item?.lastProjectProperty() }
	val date = bind { item?.dateProperty() }
    val country = bind { item?.countryProperty() }
    val gender = bind { item?.genderProperty() }
	val age = bind { item?.ageProperty() }
	val email = bind { item?.emailProperty() }
	val schedule = bind { item?.scheduleProperty() }
	val location = bind { item?.locationProperty() }
	val onlineBanking = bind { item?.onlineBankingProperty() }
	val onlineInvestment = bind { item ?.onlineInvestmentProperty() }
}
