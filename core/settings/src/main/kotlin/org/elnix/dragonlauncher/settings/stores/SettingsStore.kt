package org.elnix.dragonlauncher.settings.stores

import org.elnix.dragonlauncher.settings.DataStoreName
import org.elnix.dragonlauncher.settings.bases.BaseSettingObject
import org.elnix.dragonlauncher.settings.bases.DatastoreProvider
import org.elnix.dragonlauncher.settings.bases.MapSettingsStore
import org.elnix.dragonlauncher.settings.bases.Settings

object SettingsStore : MapSettingsStore() {
    override val name: String
        get() = "Settings Store"
    override val dataStoreName: DatastoreProvider
        get() = DataStoreName.SETTINGS
    override val ALL: List<BaseSettingObject<*, *>>
        get() = listOf()


    val debugMode = Settings.boolean(
        key = "debugMode",
        dataStoreName = dataStoreName,
        default = false
    )
}