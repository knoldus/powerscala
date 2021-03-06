package org.powerscala.datastore

import org.powerscala.event.Listenable
import org.powerscala.hierarchy.Child


/**
 * @author Matt Hicks <mhicks@powerscala.org>
 */
trait DatastoreSession extends Listenable with Child {
  def datastore: Datastore
  def parent = datastore

  override def bus = datastore.bus

  private var collections = Map.empty[String, DatastoreCollection[_]]

  def apply[T <: Identifiable](implicit manifest: Manifest[T]) = collection[T](null)(manifest)

  def delete(): Unit

  final def collection[T <: Identifiable](name: String = null)(implicit manifest: Manifest[T]) = {
    val n = datastore.aliasName(name, manifest.erasure)
    collections.get(n) match {
      case Some(c) => c.asInstanceOf[DatastoreCollection[T]]
      case None => {
        val c = createCollection[T](n)
        collections += n -> c
        c
      }
    }
  }

  protected def createCollection[T <: Identifiable](name: String)(implicit manifest: Manifest[T]): DatastoreCollection[T]

  def disconnect(): Unit
}