package models

/**
  * Company Information for Users
  *
  * @param name
  * @param catchPhrase
  * @param bs
  */
case class Company(name: String,
                   catchPhrase: String,
                   bs: String)

object Company {
  def empty = Company ("empty", "empty", "empty")
}
