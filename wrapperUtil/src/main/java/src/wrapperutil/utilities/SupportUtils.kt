package src.wrapperutil.utilities

import java.io.*

object SupportUtils {

    fun objectToByteArray(mObject: Any): ByteArray {
        val bos = ByteArrayOutputStream()
        var out: ObjectOutput
        try {
            out = ObjectOutputStream(bos)
            out.writeObject(mObject)
            out.flush()
            return bos.toByteArray()
        } catch (e: Throwable) {
            e.printStackTrace()
        } finally {
            try {
                bos.close()
            } catch (ex: Throwable) {
                ex.printStackTrace()
            }
        }
        return ByteArray(0)
    }

    fun byteArrayToObject(bytes: ByteArray): Any? {
        val bis = ByteArrayInputStream(bytes)
        var input: ObjectInput? = null
        try {
            input = ObjectInputStream(bis)
            return input.readObject()
        } catch (e: Throwable) {
            e.printStackTrace()
        } finally {
            try {
                input?.close()
            } catch (ex: Throwable) {
                ex.printStackTrace()
            }
        }
        return null
    }
}
