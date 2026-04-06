package org.elnix.dragonlauncher.settings.bases

interface DatastoreProvider {
    val backupKey: String
    val userBackup: Boolean
}