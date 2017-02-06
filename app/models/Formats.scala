package models

import play.api.libs.json.{Json, Format}

/**
  * Created by harry on 06.02.2017.
  */
object Formats {
  implicit lazy val userFormat: Format[User] = Json.format[User]
  implicit lazy val addressFormat: Format[Address] = Json.format[Address]
  implicit lazy val geoFormat: Format[Geo] = Json.format[Geo]
  implicit lazy val companyFormat: Format[Company] = Json.format[Company]
}
