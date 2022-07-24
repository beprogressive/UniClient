package beprogressive.uniclient

import androidx.datastore.core.Serializer
import com.gmail.beprogressive.it.uniclient.ProtoSettings
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object SettingsSerializer : Serializer<ProtoSettings> {
    override val defaultValue: ProtoSettings = ProtoSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ProtoSettings {
        return try {
            ProtoSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            log("Cannot read proto. Create default.")
            defaultValue
        }
    }

    override suspend fun writeTo(t: ProtoSettings, output: OutputStream) = t.writeTo(output)
}