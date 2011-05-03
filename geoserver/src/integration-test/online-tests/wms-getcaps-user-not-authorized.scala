new Test (args) {
  val queryParams = Map(
    "request" -> "GetCapabilities")

  "wms getcaps" inService "geoserver/wms" loginWith BasicLogin("jesseichar", "jesseichar") doGet queryParams should (result => {
    result has ('responseCode -> 200)
    
    val found = (result.xml \\ "Layer").exists { layer =>
      val name = (layer \ "Name").text
      name == "geob_test:tasmania_cities"
    }
    assert(!found, "geob_test:tasmania_cities is in getcaps doc")
  })
}