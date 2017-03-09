package td2.client.ui.controller

import javafx.collections.FXCollections
import td2.client.ui.model.Participant
import td2.client.ui.model.ParticipantModel
import td2.client.utils.DateHelper
import tornadofx.Controller

class ParticipantController : Controller() {
	val participants = FXCollections.observableArrayList<Participant>()
    val selectedParticipant = ParticipantModel()

    init {
        // Add some test persons for the demo
        participants.add(Participant(1, "UBI", td2.client.utils.toLocalDate("2015-Dec"), "Canada", "Male", "18-22", "elvis.yc@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
        participants.add(Participant(2, "UBI", td2.client.utils.toLocalDate("2014-Nov"), "Canada", "Male", "20-25", "pietrogarieri@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(3, "UBI", td2.client.utils.toLocalDate("2016-Dec"), "USA", "Male", "25-30", "dave@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(4, "UBI", td2.client.utils.toLocalDate("2017-Mar"), "Canada", "Male", "25-30", "rahul@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(5, "UBI", td2.client.utils.toLocalDate("2010-Apr"), "Canada", "Male", "18-22", "alice@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(6, "UBI", td2.client.utils.toLocalDate("2011-May"), "Canada", "Female", "45-50", "ben@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(7, "UBI", td2.client.utils.toLocalDate("2015-Nov"), "Canada", "Male", "25-30", "alex@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(8, "UBI", td2.client.utils.toLocalDate("2014-Mar"), "USA", "Male", "25-30", "neville@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(9, "UBI", td2.client.utils.toLocalDate("2014-Feb"), "Canada", "Male", "45-50", "eric@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(10, "UBI", td2.client.utils.toLocalDate("2014-Jan"), "Canada", "Female", "25-30", "mandy@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(11, "UBI", td2.client.utils.toLocalDate("2012-Nov"), "Canada", "Female", "25-30", "mim@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(12, "UBI", td2.client.utils.toLocalDate("2016-Jun"), "Canada", "Male", "18-22", "christ@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(13, "UBI", td2.client.utils.toLocalDate("2013-Jul"), "USA", "Male", "25-30", "david@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(14, "UBI", td2.client.utils.toLocalDate("2014-Aug"), "Canada", "Male", "25-30", "alex@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(15, "UBI", td2.client.utils.toLocalDate("2014-Aug"), "USA", "Male", "45-50", "peter@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(16, "UBI", td2.client.utils.toLocalDate("2013-Mar"), "USA", "Male", "25-30", "ken@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(17, "UBI", td2.client.utils.toLocalDate("2013-Apr"), "Canada", "Female", "25-30", "mandy@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(18, "UBI", td2.client.utils.toLocalDate("2011-May"), "Canada", "Male", "18-22", "eric@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(19, "UBI", td2.client.utils.toLocalDate("2013-Dec"), "Canada", "Male", "25-30", "edward@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(20, "UBI", td2.client.utils.toLocalDate("2010-Feb"), "USA", "Male", "45-50", "robert@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(21, "UBI", td2.client.utils.toLocalDate("2012-Nov"), "Canada", "Male", "25-30", "michael@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(22, "UBI", td2.client.utils.toLocalDate("2014-Jan"), "Canada", "Male", "25-30", "william@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(23, "UBI", td2.client.utils.toLocalDate("2016-Oct"), "Canada", "Female", "25-30", "jennier@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(24, "UBI", td2.client.utils.toLocalDate("2016-Oct"), "USA", "Male", "25-30", "maria@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(25, "UBI", td2.client.utils.toLocalDate("2012-Sep"), "Canada", "Female", "25-30", "lisa@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(26, "UBI", td2.client.utils.toLocalDate("2016-Sep"), "Canada", "Male", "25-30", "thomas@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
   		participants.add(Participant(27, "UBI", td2.client.utils.toLocalDate("2010-Apr"), "Canada", "Female", "18-22", "betty@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(28, "UBI", td2.client.utils.toLocalDate("2011-May"), "Canada", "Female", "45-50", "sandra@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(29, "UBI", td2.client.utils.toLocalDate("2015-Nov"), "Canada", "Female", "25-30", "ruth@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(30, "UBI", td2.client.utils.toLocalDate("2014-Mar"), "USA", "Female", "25-30", "angela@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(31, "UBI", td2.client.utils.toLocalDate("2014-Feb"), "Canada", "Female", "45-50", "amy@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(32, "UBI", td2.client.utils.toLocalDate("2014-Jan"), "Canada", "Male", "25-30", "larry@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(33, "UBI", td2.client.utils.toLocalDate("2012-Nov"), "Canada", "Female", "25-30", "purple@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(34, "UBI", td2.client.utils.toLocalDate("2016-Jun"), "Canada", "Male", "18-22", "jan@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(35, "UBI", td2.client.utils.toLocalDate("2013-Jul"), "USA", "Male", "25-30", "david@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(36, "UBI", td2.client.utils.toLocalDate("2014-Aug"), "Canada", "Male", "25-30", "alex@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(37, "UBI", td2.client.utils.toLocalDate("2014-Aug"), "USA", "Male", "45-50", "peter@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(38, "UBI", td2.client.utils.toLocalDate("2013-Mar"), "USA", "Male", "25-30", "ken@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(39, "UBI", td2.client.utils.toLocalDate("2013-Apr"), "Canada", "Male", "25-30", "mandy@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(40, "UBI", td2.client.utils.toLocalDate("2011-May"), "Canada", "Female", "18-22", "eric@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(41, "UBI", td2.client.utils.toLocalDate("2013-Dec"), "Canada", "Male", "25-30", "edward@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(42, "UBI", td2.client.utils.toLocalDate("2010-Feb"), "USA", "Male", "45-50", "hellokitty@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(43, "UBI", td2.client.utils.toLocalDate("2012-Nov"), "Canada", "Male", "25-30", "ironman@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(44, "UBI", td2.client.utils.toLocalDate("2014-Jan"), "Canada", "Male", "25-30", "superman@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(45, "UBI", td2.client.utils.toLocalDate("2016-Oct"), "Canada", "Female", "25-30", "supergirl@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(46, "UBI", td2.client.utils.toLocalDate("2016-Oct"), "USA", "Male", "25-30", "supergrandma@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
		participants.add(Participant(47, "UBI", td2.client.utils.toLocalDate("2012-Sep"), "Canada", "Female", "25-30", "antman@td.com", "Manager", "Downtown Toronto", "TD EasyWeb, CIBC Online Banking", "TD MyInsurance"))
		participants.add(Participant(48, "UBI", td2.client.utils.toLocalDate("2016-Sep"), "Canada", "Male", "25-30", "batman@td.com", "Manager", "Downtown Toronto", "RBC Online Banking", "RBC MyInsurance"))
	 }
}