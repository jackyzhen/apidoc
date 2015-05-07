package lib

import com.gilt.apidoc.spec.v0.models._
import org.scalatest.{FunSpec, ShouldMatchers}

class ServiceDiffSpec extends FunSpec with ShouldMatchers {

  new play.core.StaticApplication(new java.io.File("."))

  private lazy val service = TestHelper.readService("../spec/api.json")

  it("no changes") {
    ServiceDiff(service, service).differences should be(Nil)
  }

  it("apidoc version") {
    ServiceDiff(service, service.copy(apidoc = Apidoc(version = "0.0.1"))).differences should be(
      Seq(
        Difference.NonBreaking(s"apidoc/version changed from ${service.apidoc.version} to 0.0.1")
      )
    )
  }

  it("name") {
    ServiceDiff(service, service.copy(name = "test")).differences should be(
      Seq(
        Difference.NonBreaking(s"name changed from ${service.name} to test")
      )
    )
  }

  it("organization key") {
    ServiceDiff(service, service.copy(organization = Organization(key = "foo"))).differences should be(
      Seq(
        Difference.NonBreaking(s"organization/key changed from ${service.organization.key} to foo")
      )
    )
  }

  it("application key") {
    ServiceDiff(service, service.copy(application = Application(key = "foo"))).differences should be(
      Seq(
        Difference.NonBreaking(s"application/key changed from ${service.application.key} to foo")
      )
    )
  }

  it("namespace") {
    ServiceDiff(service, service.copy(namespace = "test")).differences should be(
      Seq(
        Difference.Breaking(s"namespace changed from ${service.namespace} to test")
      )
    )
  }


}
