package td2.client.ui.email

import java.awt.Desktop
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URI
import java.net.URISyntaxException
import java.net.URLEncoder

public fun mailto(recipients: String, subject: String, body: String) {
	var uriStr = String.format("mailto:%s?subject=%s&body=%s",
			recipients, // use semicolon ";" for Outlook!
			urlEncode(subject),
			urlEncode(body));
	if (Desktop.isDesktopSupported()) {
		var thread = Thread() {
			try {
				Desktop.getDesktop().browse(URI(uriStr));
			} catch (e1: IOException) {
				print(e1)
				e1.printStackTrace();
			} catch (e1: URISyntaxException) {
				print(e1)
				e1.printStackTrace();
			}
		}
		thread.start();
	}
}

private fun urlEncode(str: String): String {
	try {
		return URLEncoder.encode(str, "UTF-8").replace("+", "%20");
	} catch (ex: UnsupportedEncodingException) {
		throw RuntimeException(ex);
	}
}

public fun join(sep: String, objs: Iterable<Any>): String {
	var sb = StringBuilder();
	for (obj in objs) {
		if (sb.length > 0) {
			sb.append(sep);
			sb.append(obj);
		}
	}
	return sb.toString();
}