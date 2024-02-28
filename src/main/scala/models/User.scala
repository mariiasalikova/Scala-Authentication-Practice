package models

import io.circe.generic.auto._

case class User(name: String, email: String, password: String)
case class RegistrationData(name: String, email: String, password: String)
