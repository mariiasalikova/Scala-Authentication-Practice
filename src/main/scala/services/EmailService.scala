package services
import org.http4s.dsl.io._

import java.util.{Date, Properties, UUID}
import javax.mail._
import javax.mail.internet._
import cats.effect.IO
import org.http4s._
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import models.User

class EmailService(mailServer: String, mailPort: Int, username: String, password: String) {

  private val props = new Properties()
  props.put("mail.smtp.auth", "true")
  props.put("mail.smtp.starttls.enable", "true")
  props.put("mail.smtp.host", mailServer)
  props.put("mail.smtp.port", mailPort)

  private val session = Session.getInstance(props, new Authenticator() {
    override def getPasswordAuthentication(): PasswordAuthentication = {
      new PasswordAuthentication(username, password)
    }
  })

  def sendConfirmationEmail(user: User): IO[Unit] =
    IO {
      val confirmationLink = s"http://yourforum.com/confirm/${generateConfirmationToken(user)}"
      val message = createEmailMessage(user, confirmationLink)

      Transport.send(message)
    }

  private def createEmailMessage(user: User, confirmationLink: String): MimeMessage = {
    val message = new MimeMessage(session)

    message.setFrom(new InternetAddress("no-reply@yourforum.com"))
    message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.email))
    message.setSubject("Confirm your registration")
    message.setText(s"To confirm your registration, click this link: $confirmationLink")

    message
  }

  private def generateConfirmationToken(user: User): String = {
    val expiration = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000) // 24 hours
    val token = JWT.create()
      .withSubject(user.email)
      .withExpiresAt(expiration)
      .sign(Algorithm.HMAC256("your_secret_key".getBytes("UTF-8")))
    token
  }
}
