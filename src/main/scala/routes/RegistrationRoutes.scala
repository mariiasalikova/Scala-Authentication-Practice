package routes

import cats.effect.IO
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.circe._
import services._
import models._

object RegistrationRoutes {
  def registrationRoutes(emailService: EmailService): HttpRoutes[IO] = HttpRoutes.of[IO] {
    case req @ POST -> Root / "register" =>
      for {
        registrationData <- req.as[RegistrationData]
        _ <- RegistrationService.register(emailService)(registrationData)
        response <- Ok("Please confirm your registration by clicking the link in your email.")
      } yield response

    case GET -> Root / "confirm" / token =>
      for {
        confirmed <- RegistrationService.confirmRegistration(token)
        response <-
          if (confirmed) Ok("Registration successful! Welcome to the forum.")
          else Ok("Invalid or expired confirmation link. Please register again.")
      } yield response
  }
}
