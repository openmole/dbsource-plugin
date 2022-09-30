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

import org.openmole.core.dsl._
import org.openmole.core.dsl.extension._

object DBSource {

  def apply[T](url: FromContext[String], prototype: Val[T])(implicit name: sourcecode.Name, definitionScope: DefinitionScope, networkService: NetworkService) =
    Source("DBSource") { p ⇒
      import p._

      val response = ""

      val value: AnyRef = prototype.`type`.runtimeClass match {
        case s if s == classOf[String] ⇒ response
        case _ ⇒ throw new UserBadDataError(s"DB variable can not be mapped to a ${prototype.`type`} prototype")
      }

      Variable.unsecure(prototype, value)
    } set (outputs += prototype)

}

