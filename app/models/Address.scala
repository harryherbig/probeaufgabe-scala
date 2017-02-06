package models

/**
  * Address
  *
  * @param street
  * @param suite
  * @param city
  * @param zipcode
  * @param geo
  */
case class Address(street: String,
                   suite: String,
                   city: String,
                   zipcode: String,
                   geo: Geo)
