package org.rmaftei.functionalpatterns

import scala.annotation.tailrec

object P16_FunctionBuilder extends App {

  def discount(percent: Double) = {
    if(percent < 0.0 || percent > 100.0) {
      (originalPrice: Double) => {
        originalPrice
      }
    } else {
      (originalPrice: Double) => {
        originalPrice - (originalPrice * percent * 0.01)
      }

    }
  }

  println(discount(50)(100))

  val discountZero: Double => Double = discount(0)
  println(discountZero(200))

  val seventyDiscount: Double => Double = discount(70)
  println(seventyDiscount(200))

  private val minutDiscountReturnTheOriginalPrice: Double => Double = discount(-10)
  println(minutDiscountReturnTheOriginalPrice(300))

  // map selector

  def selector(path: Symbol*): (Map[Symbol, Any] => Option[Any]) = {

    if(path.size <= 0) None

    @tailrec
    def selectorHelper(path: Seq[Symbol], ds: Map[Symbol,Any]): Option[Any] =
      if(path.size == 1) {
        ds.get(path.head)
      } else {
        ds.get(path.head) match {
          case Some(current: Map[Symbol, Any]) => selectorHelper(path.tail, current)
          case None => None
          case _ => None
        }
      }

    (ds: Map[Symbol, Any]) => selectorHelper(path, ds)
  }

  val person = Map('name -> "Gigel",
      'allname -> Map('firstname -> "Gigel first", 'secondname -> "Gigel second", 'thirdname -> "Gigel third"))

  println(selector('allname)(person))
  println(selector('allname, 'secondname)(person))

  val getSecondName = selector('allname, 'secondname)
  println(getSecondName(person))

  val getFourthName = selector('allname, 'fourthname)
  println(getFourthName(person))

  // create function with function composition

  val appendA = (s: String) => s + "a"
  val appendB = (s: String) => s + "b"
  val appendC = (s: String) => s + "c"

  val appendCBA = appendA compose appendB compose appendC

  println(appendCBA("test"))

  case class HttpRequest(
                        headers: Map[String, String],
                        payload: String,
                        principal: Option[String] = None
                        )

  def checkAuthorization(request: HttpRequest): HttpRequest = {
    val authHeader = request.headers.get("Authorization")
    val mockAuth = authHeader match {
      case Some(_) => Some("AUser")
      case _ => None
    }

    request.copy(principal = mockAuth)
  }

  def logFinderPrint(request: HttpRequest): HttpRequest = {
    val fingerprint = request.headers.getOrElse("X-RequestFingerPrint", "")
    println("FINGERPRINT=" + fingerprint)

    request
  }

  def composedFilters(filters: Seq[(HttpRequest) => HttpRequest]) =
    filters.reduce {
      (allFilters, currentFilter) => allFilters compose currentFilter
    }

  val filters = Vector(checkAuthorization(_), logFinderPrint(_))

  val filterChain = composedFilters(filters)

  val requestHeaders = Map("Authorization" -> "Auth", "X-RequestFingerPrint" -> "fingerprint")

  val request = HttpRequest(requestHeaders, "body")

  println(filterChain(request))

  // partial functions

  def taxPerCountry(amount: Double, state: Symbol) = state match {
    case('RO) => amount * 0.16
    case('FR) => amount * 0.26
    case('AU) => amount * 0.10
  }

  val roTaxes = taxPerCountry(_: Double, 'RO)
  val auTaxes = taxPerCountry(_: Double, 'AU)

  println(s"Value of the tax by amount ${roTaxes(300)} for RO ")
  println(s"Value of the tax by amount ${auTaxes(300)} for AU")
}
