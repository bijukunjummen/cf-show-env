apply {
    plugin("scala")
}

configurations {
    "gatling"
}

dependencies {
    "gatling"("org.scala-lang:scala-library:2.11.8")
    "gatling"("io.gatling:gatling-app:2.2.4")
    "gatling"("io.gatling.highcharts:gatling-charts-highcharts:2.2.4")
}



