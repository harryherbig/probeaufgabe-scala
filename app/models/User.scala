package models

/**
  * User information
  *
  * @param id
  * @param username
  * @param email
  * @param phone
  * @param website
  * @param address
  * @param company
  */
case class User(id: Int,
                 username: String,
                 email: String,
                 phone: String,
                 website: String,
                 address: Address,
                 company: Company
               )

object User {
  def empty = User(0, "empty", "empty", "empty", "empty", Address.empty, Company.empty)
}

