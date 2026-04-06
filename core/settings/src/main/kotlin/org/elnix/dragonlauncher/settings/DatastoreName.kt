package org.elnix.dragonlauncher.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.elnix.dragonlauncher.settings.bases.BaseSettingsStore
import org.elnix.dragonlauncher.settings.bases.DatastoreProvider
import org.elnix.dragonlauncher.settings.stores.SettingsStore


enum class DataStoreName(
    override val backupKey: String,
    override val userBackup: Boolean = true
) : DatastoreProvider {
    SETTINGS("settings")
}


object SettingsStoreRegistry {
    val byName: Map<DatastoreProvider, BaseSettingsStore<*, *>> = mapOf(
        DataStoreName.SETTINGS to SettingsStore
    )
}

val allStores = SettingsStoreRegistry.byName

val backupableStores =
    SettingsStoreRegistry.byName
        .filterKeys { it.userBackup }


/**
 * Datastore, now handled by a conditional function to avoid errors, all private
 */
private val Context.datastore by preferencesDataStore(SettingsStore.name)


internal fun Context.resolveDataStore(name: DatastoreProvider): DataStore<Preferences> {
    val appCtx = this.applicationContext
    return when (name) {
        DataStoreName.SETTINGS -> datastore
        else -> null
    } ?: error("Datastore not found")
}
