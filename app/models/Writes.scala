package models

import play.api.libs.json.{Json, Writes}

/**
  * Created by harry on 06.02.2017.
  */
object Writes {

  implicit lazy val userWrites: Writes[User] = Json.writes[User]
  implicit lazy val addressWrites: Writes[Address] = Json.writes[Address]
  implicit lazy val geoWrites: Writes[Geo] = Json.writes[Geo]
  implicit lazy val companyWrites: Writes[Company] = Json.writes[Company]
}
