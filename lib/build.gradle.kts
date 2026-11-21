
plugins {
    id("com.android.library")
    id("maven-publish")
}

android {
    namespace = "mr.x.widget"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    // 原始代码编辑器依赖
    api(files("libs/glbjk.jar"))
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "mr.x.widget"
                artifactId = "lib"
                version = "1.0.0"
            }
        }
    }
}
