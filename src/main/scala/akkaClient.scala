import com.typesafe.config.{Config, ConfigFactory}

import scala.util.{Failure, Success, Try}

object akkaClient {
  private val config = ConfigFactory.load()

  val amazon_link: String = config.getString("akkahttpsetup.rest_url")
  val T: String = config.getString("akkahttpsetup.time")
  val dT: String = config.getString("akkahttpsetup.delta_time")

  def main(args: Array[String]): Unit = {
    println(amazon_link)
  }
}