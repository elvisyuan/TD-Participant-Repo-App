package td2.client.security

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun toMD5Hash(password: String): String {
	try {
		val md = MessageDigest.getInstance("MD5");
        val messageDigest = md.digest(password.toByteArray());
        val number = BigInteger(1, messageDigest);
        var hashtext = number.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while (hashtext.length < 32) {
        	hashtext = "0" + hashtext;
        }
        return hashtext;
	}
	catch (e: NoSuchAlgorithmException) {
		print(e)
		throw(e)
	}
}