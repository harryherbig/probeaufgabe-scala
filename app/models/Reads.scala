package models

import play.api.libs.json.{Json, Reads}

/**
  * Created by harry on 06.02.2017.
  */
object Reads {
  implicit lazy val userReads: Reads[User] = Json.reads[User]
  implicit lazy val addressReads: Reads[Address] = Json.reads[Address]
  implicit lazy val geoReads: Reads[Geo] = Json.reads[Geo]
  implicit lazy val companyReads: Reads[Company] = Json.reads[Company]
}
