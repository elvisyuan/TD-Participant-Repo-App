package td2.client.ui.model

import tornadofx.ItemViewModel
import tornadofx.getProperty
import tornadofx.property
import java.time.LocalDate

class Device(appleiPhone: String, appleiPad: String, appleiPodTouch: String, blackberryPlaybook: String,
			 htc: String, lg: String, motorola: String, nexus: String,
			 nokia: String, samsung: String, sony: String, otherTablet: String) {
    var appleiPhone by property(appleiPhone)
    fun appleiPhoneProperty() = getProperty(Device::appleiPhone)
	
	var appleiPad by property(appleiPad)
	fun appleiPadProperty() = getProperty(Device::appleiPad)
	
	var appleiPodTouch by property(appleiPodTouch)
	fun appleiPodTouchProperty() = getProperty(Device::appleiPodTouch)
	
	var blackberryPlaybook by property(blackberryPlaybook)
	fun blackberryPlaybookProperty() = getProperty(Device::blackberryPlaybook)
	
	var htc by property(htc)
	fun htcProperty() = getProperty(Device::htc)
	
	var lg by property(lg)
	fun lgProperty() = getProperty(Device::lg)
	
	var motorola by property(motorola)
	fun motorolaProperty() = getProperty(Device::motorola)
	
	var nexus by property(nexus)
	fun nexusProperty() = getProperty(Device::nexus)
	
	var nokia by property(nokia)
	fun nokiaProperty() = getProperty(Device::nokia)
	
	var samsung by property(samsung)
	fun samsungProperty() = getProperty(Device::samsung)
	
	var sony by property(sony)
	fun sonyProperty() = getProperty(Device::sony)
	
	var otherTablet by property(otherTablet)
	fun otherTabletProperty() = getProperty(Device::otherTablet)
}

class DeviceModel : ItemViewModel<Device>() {
    val appleiPhone = bind { item?.appleiPhoneProperty() }
	val appleiPad = bind { item?.appleiPadProperty() }
	val appleiPodTouch = bind { item?.appleiPodTouchProperty() }
    val blackberryPlaybook = bind { item?.blackberryPlaybookProperty() }
	val htc = bind { item?.htcProperty() }
	val lg = bind { item?.lgProperty() }
	val motorola = bind { item?.motorolaProperty() }
	val nexus = bind { item?.nexusProperty() }
	val nokia = bind { item?.nokiaProperty() }
	val samsung = bind { item?.samsungProperty() }
	val sony = bind { item?.sonyProperty() }
	val otherTablet = bind { item?.otherTabletProperty() }
}