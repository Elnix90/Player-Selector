import com.android.build.api.dsl.ApplicationExtension
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}


val dotenv = Properties().apply {
    val envFile = rootProject.file(".env")
    if (envFile.exists()) {
        envFile.inputStream().use { load(it) }
    }
}

fun env(name: String): String? =
    System.getenv(name) ?: dotenv.getProperty(name)


kotlin {
    jvmToolchain(17)
}

extensions.configure<ApplicationExtension> {
    namespace = "org.elnix.player.selector"
    compileSdk {
        version = release(36)
    }

    signingConfigs {
        create("release") {
            val keystore = env("KEYSTORE_FILE")
            val storePass = env("KEYSTORE_PASSWORD")
            val alias = env("KEY_ALIAS")
            val keyPass = env("KEY_PASSWORD")

            if (
                !keystore.isNullOrBlank() &&
                !storePass.isNullOrBlank() &&
                !alias.isNullOrBlank() &&
                !keyPass.isNullOrBlank()
            ) {
                storeFile = file(keystore)
                storePassword = storePass
                keyAlias = alias
                keyPassword = keyPass

            } else {
                println("WARNING: Release signingConfig not fully configured.")
            }
        }
    }

    defaultConfig {
        applicationId = "org.elnix.player.selector"
        minSdk = 28
        targetSdk = 36
        versionName = "${property("VERSION_NAME") as String} (${property("CODE_NAME") as String})"
        versionCode = (property("VERSION_CODE") as String).toInt()
    }

    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }


    flavorDimensions += "channel"
    productFlavors {
        create("stable") {
            dimension = "channel"
            versionNameSuffix = ""
        }
        create("beta") {
            dimension = "channel"
            versionNameSuffix = "-beta"
        }
        create("fdroid") {
            dimension = "channel"
            versionNameSuffix = ""
        }
    }


    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            val isFdroidBuild = System.getenv("FDROID_BUILD") == "true"

            signingConfig = if (!isFdroidBuild) {

                val hasSigning =
                    env("KEYSTORE_FILE") != null &&
                            env("KEYSTORE_PASSWORD") != null &&
                            env("KEY_ALIAS") != null &&
                            env("KEY_PASSWORD") != null

                if (hasSigning) {
                    println("Signing release using release signing")
                    signingConfigs.getByName("release")
                } else {
                    println("No signing config found, apk will be unsigned!")
                    null
                }

            } else {
                println("FDroid build - not using release signing config")
                null
            }

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }


        create("unminifiedRelease") {
            initWith(getByName("release"))
            isMinifyEnabled = false
            isShrinkResources = false
        }
        create("debuggableRelease") {
            initWith(getByName("release"))
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = true
        }

        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        jniLibs.keepDebugSymbols.add("**/*.so")
    }

    dependenciesInfo {
        // Disables dependency metadata when building APKs (for IzzyOnDroid/F-Droid)
        includeInApk = false
        // Disables dependency metadata when building Android App Bundles (for Google Play)
        includeInBundle = false
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
}