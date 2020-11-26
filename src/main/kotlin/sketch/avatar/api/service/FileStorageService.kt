package sketch.avatar.api.service

import io.micronaut.http.multipart.CompletedFileUpload
import java.io.InputStream

interface FileStorageService {
    fun get(bucket: String, key: String): InputStream
    fun put(key: String, file: CompletedFileUpload)
}
