package org.powerscala

/**
 * Precision is an enum defining numeric precisions and conversions.
 *
 * @author Matt Hicks <mhicks@powerscala.org>
 */
sealed class Precision(val conversion: Double, f: () => Long) extends EnumEntry[Precision] {
  def time = f()
}

object Precision extends Enumerated[Precision] {
  val Milliseconds = new Precision(1000.0, () => System.currentTimeMillis)
  val Nanoseconds = new Precision(1000000000.0, () => System.nanoTime)
}