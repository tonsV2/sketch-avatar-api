package sketch.avatar.api.service

import java.io.InputStream

interface FileStorageService {
    fun get(bucket: String, key: String): InputStream
    fun copy(sourceBucket: String, sourceKey: String, destinationBucket: String, destinationKey: String)
}
