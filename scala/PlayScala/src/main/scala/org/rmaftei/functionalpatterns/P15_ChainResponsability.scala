package org.rmaftei.functionalpatterns

object P15_ChainResponsability extends App {

  case class User(name: String, id: String)
  case class Movie(name: String, id: String)
  case class Video(name: String, id: String)

  def getUserByid(id: String) = id match {
    case "1" => Some(User("Gică", "1"))
    case _ => None
  }

  def getFavoriteMovieForUser(user: User) = user match {
    case User(_, "1") => Some(Movie("Gigli", "101"))
    case _ => None
  }

  def getVideosForMovie(movie: Movie) = movie match {
    case Movie(_, "101") => Some(Vector(Video("Inverview","123"),Video("Cast presentation","333")))
    case _ => None
  }

  def getFavoriteVideosForUser(userId: String) =
    for {
      user <- getUserByid(userId)
      favoriteMovie <- getFavoriteMovieForUser(user)
      favoriteVideos = getVideosForMovie(favoriteMovie)
    } yield {
      favoriteVideos
    }

  println(getFavoriteVideosForUser("1"))
  println(getFavoriteVideosForUser("x"))

}
