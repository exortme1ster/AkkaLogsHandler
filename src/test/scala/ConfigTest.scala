import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class configTest extends AnyFlatSpec with Matchers {

  behavior of "common configuration parameters"
  val config: Config = ConfigFactory.load("application.conf").getConfig("akkahttpsetup")

  it should "obtain the rest_url" in {
    config.getString("rest_url") shouldBe "https://imh5ufzsd9.execute-api.us-east-1.amazonaws.com/prod/checkifpresent"
  }

  it should "get time" in {
    config.getString("time") shouldBe "23:47:00"
  }

  it should "get delta_time" in {
    config.getString("delta_time") shouldBe "00:00:05"
  }

  it should "get format of time" in {
    config.getString("time").split(':') should have length 3
  }

  it should "check start of url" in {
    config.getString("rest_url") should startWith("https://")
  }



}

