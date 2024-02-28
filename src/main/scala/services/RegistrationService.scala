package services
import cats.effect.IO
import com.auth0.jwt.JWT
import models.{RegistrationData, User}

import java.util.Date

object RegistrationService {

  def register(emailService: EmailService)(registrationData: RegistrationData): IO[Unit] =
    IO {
      val user = User(registrationData.name, registrationData.email, registrationData.password)
      emailService.sendConfirmationEmail(user)
    }

  def confirmRegistration(token: String): IO[Boolean] =
    IO {
      try {
        val decodedToken = JWT.decode(token)
        val email = decodedToken.getSubject
        val expiration = decodedToken.getExpiresAt

        // Check if the token is valid and not expired
        if (email != null && expiration != null && expiration.after(new Date())) {
          // Save the user to a database or perform other actions
          true
        } else {
          false
        }
      } catch {
        case _: Throwable => false
      }
    }
}
