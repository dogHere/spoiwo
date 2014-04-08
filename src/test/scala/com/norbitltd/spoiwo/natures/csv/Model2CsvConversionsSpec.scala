package com.norbitltd.spoiwo.natures.csv

import org.scalatest.FlatSpec
import com.norbitltd.spoiwo.model.{Row, Sheet}
import Model2CsvConversions._
import org.joda.time.LocalDate

class Model2CsvConversionsSpec extends FlatSpec {

  "Model to CSV conversion" should "correctly convert the single text-only sheet" in {
    val sheet = Sheet(name = "CSV conversion").withRows(
      Row().withCellValues("EUROPE", "Poland", "Wroclaw"),
      Row().withCellValues("EUROPE", "United Kingdom", "London"),
      Row().withCellValues("ASIA", "China", "Tianjin"))

    val csvText = "EUROPE,Poland,Wroclaw\nEUROPE,United Kingdom,London\nASIA,China,Tianjin"
    assert(csvText === sheet.convertAsCsv())
  }

  it should "correctly convert the single text-only sheet with '|' separator" in {
    val sheet = Sheet(name = "CSV conversion").withRows(
      Row().withCellValues("EUROPE", "Poland", "Wroclaw"),
      Row().withCellValues("EUROPE", "United Kingdom", "London"),
      Row().withCellValues("ASIA", "China", "Tianjin"))
    val properties = CsvProperties(separator = "|")

    val csvText = "EUROPE|Poland|Wroclaw\nEUROPE|United Kingdom|London\nASIA|China|Tianjin"
    assert(csvText === sheet.convertAsCsv(properties))
  }

  it should "correctly format boolean value with default 'true' and 'false'" in {
    val sheet = Sheet(name = "CSV conversion").withRows(
      Row().withCellValues("City", "Is Capital"),
      Row().withCellValues("London", true),
      Row().withCellValues("Wroclaw", false)
    )

    val csvText = "City,Is Capital\nLondon,true\nWroclaw,false"
    assert(csvText === sheet.convertAsCsv())
  }

  it should "correctly format boolean value with 'Y' and 'N'" in {
    val sheet = Sheet(name = "CSV conversion").withRows(
      Row().withCellValues("City", "Is Capital"),
      Row().withCellValues("London", true),
      Row().withCellValues("Wroclaw", false)
    )
    val properties = CsvProperties(defaultBooleanTrueString = "Y", defaultBooleanFalseString = "N")

    val csvText = "City,Is Capital\nLondon,Y\nWroclaw,N"
    assert(csvText === sheet.convertAsCsv(properties))
  }

  it should "correctly format date value to yyyy-MM-dd by default" in {
      val sheet = Sheet(name = "CSV conversion").withRows(
        Row().withCellValues("Albert Einstein", new LocalDate(1879, 03, 14)),
        Row().withCellValues("Thomas Edison", new LocalDate(1847, 02, 11))
      )

    val csvText = "Albert Einstein,1879-03-14\nThomas Edison,1847-02-11"
    assert(csvText === sheet.convertAsCsv())
  }

  it should "correctly format date value to yyyy/MMM/dd by default" in {
    val sheet = Sheet(name = "CSV conversion").withRows(
      Row().withCellValues("Albert Einstein", new LocalDate(1879, 03, 14)),
      Row().withCellValues("Thomas Edison", new LocalDate(1847, 02, 11))
    )
    val properties = CsvProperties(defaultDateFormat = "yyyy/MMM/dd")

    val csvText = "Albert Einstein,1879/Mar/14\nThomas Edison,1847/Feb/11"
    assert(csvText === sheet.convertAsCsv(properties))
  }

}