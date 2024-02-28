package app

import cats.effect.IO
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Server
import org.http4s._
import org.http4s.implicits._
import org.http4s.dsl.Http4sDsl
import services.EmailService
import routes.RegistrationRoutes

object Main extends App {
  // Create an instance of EmailService
  val emailService = new EmailService("mail.example.com", 587, "username", "password")

  // Create an instance of RegistrationRoutes using the EmailService instance
  val httpApp = RegistrationRoutes.registrationRoutes(emailService)

  // Build the EmberServerBuilder with the correct IO type and HTTP app
  val serverBuilder = {
    EmberServerBuilder.default[IO].withHttpApp(httpApp)
  }

  // Set the host and port for the server
  val server: IO[Server] = serverBuilder.withHost(port = 8080, "0.0.0.0").build

  // Start the server
  server.useForever
}
