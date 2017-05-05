package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class SimpleSimulation extends Simulation {

  val baseUrl = s"http://localhost:8080";

  val httpConf = http.baseURL(baseUrl)

  val headers = Map("Accept" -> """application/json""")

  val healthPage = repeat(25) {
    exec(http("health")
      .get("/health"))
      .pause(2, 2)
  }

  val scn = scenario("Get the Health Page")
    .exec(healthPage)


  setUp(scn.inject(atOnceUsers(5)).protocols(httpConf)).assertions(
    global.successfulRequests.percent.gte(99))
}
