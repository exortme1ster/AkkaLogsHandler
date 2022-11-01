import com.typesafe.config.{Config, ConfigFactory}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import Helpers.{CreateLogger}
import akka.util.ByteString

import scala.concurrent.Future
import scala.util.{Failure, Success}

object akkaClient {
  private val config = ConfigFactory.load()
  val logger = CreateLogger(classOf[akkaClient.type])

  val amazon_link: String = config.getString("akkahttpsetup.rest_url")
  val T: String = config.getString("akkahttpsetup.time")
  val dT: String = config.getString("akkahttpsetup.delta_time")

    def main(args: Array[String]): Unit = {
      implicit val system = ActorSystem(Behaviors.empty, "SingleRequest")
      // needed for the future flatMap/onComplete in the end
      implicit val executionContext = system.executionContext

      val request = HttpRequest(
        method = HttpMethods.GET,
        uri = s"$amazon_link?T=$T&dT=$dT"
      )

      val responseFuture: Future[HttpResponse] = Http().singleRequest(request)

      logger.info("Made request!")
      responseFuture
        .onComplete {
          case Success(res) => {
            val HttpResponse(statusCodes, headers, entity, _) = res
            logger.info(entity.toString)
            logger.info(statusCodes.toString)
            logger.info(headers.toString)
            entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>
              logger.info("Got response, body: " + body.utf8String)
            }
          }
          case Failure(_) => sys.error("something wrong")
        }
    }
}