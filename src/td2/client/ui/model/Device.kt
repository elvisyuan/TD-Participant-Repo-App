package td2.client.ui.model

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.ItemViewModel
import tornadofx.getProperty
import tornadofx.property

object Devices{
	const val APPLE_IPHONE = "AppleiPhone"
	const val APPLE_IPAD = "AppleiPad"
	const val APPLE_IPOD_TOUCH = "AppleiPodTouch"
	const val BLACKBERRY_PLAY_BOOK = "BlackberryPlaybook"
	const val HTC = "HTC"
	const val LG = "LG"
	const val MOTOROLA = "Motorola"
	const val NEXUS = "Nexus"
	const val NOKIA = "Nokia"
	const val SAMSUNG = "Samsung"
	const val SONY = "Sony"
	const val OTHER_PHONE = "OtherPhone"
	const val OTHER_TABLET = "OtherTablet"
}

public fun getAllMobileBrands(): ObservableList<String> {
	return FXCollections.observableArrayList(Devices.APPLE_IPHONE, Devices.APPLE_IPAD, Devices.APPLE_IPOD_TOUCH, Devices.BLACKBERRY_PLAY_BOOK,
			Devices.HTC, Devices.LG, Devices.MOTOROLA, Devices.NEXUS, Devices.NOKIA, Devices.SAMSUNG, Devices.SONY, Devices.OTHER_PHONE, Devices.OTHER_TABLET)
}

class Device(appleiPhone: String, appleiPad: String, appleiPodTouch: String, blackberryPlaybook: String,
			 htc: String, lg: String, motorola: String, nexus: String,
			 nokia: String, samsung: String, sony: String, otherPhone: String, otherTablet: String) {
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
	
	var otherPhone by property(otherPhone)
	fun otherPhoneProperty() = getProperty(Device::otherPhone)
	
	var otherTablet by property(otherTablet)
	fun otherTabletProperty() = getProperty(Device::otherTablet)
	
	fun getAllAvailableDevices(): ObservableList<String> {
		var allAvailableDevices = FXCollections.observableArrayList<String>()
		if (appleiPhone != "") {
			allAvailableDevices.add(appleiPhone)
		}
		if (appleiPad != "") {
			allAvailableDevices.add(appleiPad)
		}
		if (appleiPodTouch != "") {
			allAvailableDevices.add(appleiPodTouch)
		}
		if (blackberryPlaybook != "") {
			allAvailableDevices.add(blackberryPlaybook)
		}
		if (htc != "") {
			allAvailableDevices.add(htc)
		}
		if (lg != "") {
			allAvailableDevices.add(lg)
		}
		if (motorola != "") {
			allAvailableDevices.add(motorola)
		}
		if (nexus != "") {
			allAvailableDevices.add(nexus)
		}
		if (nokia != "") {
			allAvailableDevices.add(nokia)
		}
		if (samsung != "") {
			allAvailableDevices.add(samsung)
		}
		if (sony != "") {
			allAvailableDevices.add(sony)
		}
		if (otherPhone != "") {
			allAvailableDevices.add(otherPhone)
		}
		if (otherTablet != "") {
			allAvailableDevices.add(otherTablet)
		}
		return allAvailableDevices
	}
	var participantDevices by property(getAllAvailableDevices())
    fun participantDevicesProperty() = getProperty(Device::participantDevices)
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
	val otherPhone = bind { item?.otherPhoneProperty() }
	val otherTablet = bind { item?.otherTabletProperty() }
	val participantDevices = bind { item?.participantDevicesProperty() }
}