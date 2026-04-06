package org.elnix.dragonlauncher.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.elnix.dragonlauncher.settings.bases.BaseSettingsStore
import org.elnix.dragonlauncher.settings.bases.DatastoreProvider


enum class DataStoreName(
    override val value: String,
    override val backupKey: String,
    override val userBackup: Boolean = true
) : DatastoreProvider {

}


object SettingsStoreRegistry {
    val byName: Map<DatastoreProvider, BaseSettingsStore<*, *>> = mapOf(

    )
}

val allStores = SettingsStoreRegistry.byName


val backupableStores =
    SettingsStoreRegistry.byName
        .filterKeys { it.userBackup }


/**
 * Datastore, now handled by a conditional function to avoid errors, all private
 */
//private val Context.uiDatastore by preferencesDataStore(name = UI.value)


internal fun Context.resolveDataStore(name: DatastoreProvider): DataStore<Preferences> {
    val appCtx = this.applicationContext
    return when (name) {

        else -> null
    } ?: error("Datastore not found")
}
