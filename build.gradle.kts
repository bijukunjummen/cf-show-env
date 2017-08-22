import io.pivotal.services.plugin.CfPluginExtension
import io.pivotal.services.plugin.CfService
import io.pivotal.services.plugin.CfUserProvidedService

buildscript {
    repositories {
        mavenCentral()
        jcenter()
        maven { setUrl("https://repo.spring.io/milestone") }
        maven { setUrl("https://repo.spring.io/snapshot") }
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap-1.1") }
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.1.4-eap-69")
        classpath("com.github.jengelman.gradle.plugins:shadow:2.0.1")
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.0-RC2")
        classpath("org.springframework.boot:spring-boot-gradle-plugin:1.5.6.RELEASE")
    }
}

repositories {
    mavenCentral()
    maven { setUrl("https://repo.spring.io/milestone") }
    maven { setUrl("https://repo.spring.io/snapshot") }
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap-1.1") }
}

plugins {
    id("com.github.pivotalservices.cf-app").version("1.0.9")
}

apply {
    plugin("kotlin")
    plugin("org.springframework.boot")
    plugin("java")
    from("gradle/gatling.gradle")
}

configure< CfPluginExtension> {
    //CF Details
    ccHost = "api.local.pcfdev.io"
    ccUser = "admin"
    ccPassword = "admin"
    org = "pcfdev-org"
    space = "pcfdev-space"

    //App Details
    name = "cf-show-env"
    hostName = "cf-show-env"
    filePath = "build/libs/cf-show-env-1.0.0-M1.jar"
    path = ""
    domain = "local.pcfdev.io"
    instances = 2
    memory = 1024
    timeout = 180

    //Env and services
    buildpack = "https://github.com/cloudfoundry/java-buildpack.git"

    environment = mapOf(
            "JAVA_OPTS" to "-Djava.security.egd=file:/dev/./urandom", 
            "SPRING_PROFILES_ACTIVE" to "cloud"
    )

    cfService(closureOf<CfService> {
        name = "p-mysql"
        plan = "512mb"
        instanceName = "test-db"
    })
    
    cfUserProvidedService(closureOf<CfUserProvidedService> { 
        instanceName = "myups"
        credentials = mapOf(
                "user" to "someuser",
                "uri" to "someuri"
        )
    })

}


dependencies {
    val prometheus_client_version = "0.0.21"

    compile("org.springframework.boot:spring-boot-starter-actuator")
    compile("org.springframework.boot:spring-boot-devtools")
    compile("org.springframework.boot:spring-boot-starter-thymeleaf")
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("com.google.guava:guava:19.0")
    compile("org.webjars:bootstrap:3.3.7")
    compile("org.webjars:jquery:3.1.1")
    compile("io.prometheus:simpleclient:${prometheus_client_version}")
    compile("io.prometheus:simpleclient_spring_boot:${prometheus_client_version}")
    compile("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}

val cfConfig by project

task("showAppUrls").dependsOn("cf-get-app-detail").doLast {
       println(cfConfig);
}

task("hello-world") {
    doLast {
        println("Hello World")
    }
}