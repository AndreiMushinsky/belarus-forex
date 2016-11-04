package models

case class Currency(id: Int, number: String, code: String, scale: Int, name: String)

case class Rate(date: String, rate: Double)