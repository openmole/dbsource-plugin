/*
 * Copyright (C) 30/09/2022 Juste Raimbault
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openmole.plugin.source.dbsource

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

import scala.collection.mutable.ArrayBuffer

import org.openmole.core.dsl._
import org.openmole.core.dsl.extension._

object DBSource {

  def apply[T](dbsystem: FromContext[String], prototype: Val[T])(implicit name: sourcecode.Name, definitionScope: DefinitionScope) =
    Source("DBSource") { p ⇒
      import p._

      val response = ""

      val value: AnyRef = prototype.`type`.runtimeClass match {
        case s if s == classOf[String] ⇒ response
        case _ ⇒ throw new UserBadDataError(s"DB variable can not be mapped to a ${prototype.`type`} prototype")
      }

      Variable.unsecure(prototype, value)
    } set (outputs += prototype)


  def jcdbRequest(
                   dbsystem: String,
                   dbname: String,
                   dbtable: String,
                   dbvars: Array[String],
                   dbaddress: String = "127.0.0.1",
                   dbport: String = "3306",
                   dbuser: String = "root",
                   dbpwd: String = "root"
                 ): DBResult = {
    Class.forName("com.mysql.jdbc.Driver")
    val conn = DriverManager.getConnection(s"jdbc:$dbsystem://$dbaddress:$dbport/$dbname?characterEncoding=utf8",dbuser ,dbpwd)
    val query = s"SELECT ${dbvars.mkString(",")} FROM $dbtable;" // no condition for now
    val res: ResultSet = conn.createStatement().executeQuery(query)
    val arrayRes = ArrayBuffer[Array[AnyRef]]()
    while(res.next){
      val currentRow = ArrayBuffer[AnyRef]()
      dbvars.zipWithIndex.foreach{case (_,i) => currentRow += res.getObject(i+1)}
      arrayRes += currentRow.toArray
    }
    arrayRes.toArray
  }

}

