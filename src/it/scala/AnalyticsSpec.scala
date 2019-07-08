import org.apache.http.HttpResponse
import org.apache.http.client.methods.{HttpGet, HttpPost}
import org.apache.http.impl.client.HttpClients
import org.scalatest.{FlatSpec, Matchers}

class AnalyticsSpec extends FlatSpec with Matchers {
  "Analytics GET/POST methods" should "both return Hello, World" in {
    val httpclient = HttpClients.createDefault
    result (
      httpclient.execute(new HttpGet("http://localhost:8080/analytics")),
      httpclient.execute(new HttpPost("http://localhost:8080/analytics"))
    ) shouldBe List("Hello, World.", "Hello, World.")
  }

  def result (httpResponses: HttpResponse*) :List[String] = {
    httpResponses.map {
      httpResponse =>
        val content = httpResponse.getEntity.getContent
        val bytes = Array.ofDim[Byte](content.available())
        content.read(bytes)
        new String(bytes)
    }.toList
  }
}
