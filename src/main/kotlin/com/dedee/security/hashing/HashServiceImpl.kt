package com.dedee.security.hashing

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

private const val SALT_ALGORITHM = "SHA1PRNG"
class HashServiceImpl : HashService {
    override suspend fun generateSaltedHash(value: String, saltLength: Int): SaltedHash {
        val salt = SecureRandom.getInstance(SALT_ALGORITHM).generateSeed(saltLength)
        val saltAsHex = Hex.encodeHexString(salt)
        val hash = DigestUtils.sha256Hex("$saltAsHex$value")
        return SaltedHash(
            hash = hash,
            salt = saltAsHex
        )
    }

    override suspend fun verify(value: String, salt: String, hashedPassword: String): Boolean {
        return DigestUtils.sha256Hex(salt + value) == hashedPassword
    }


}