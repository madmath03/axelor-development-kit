/**
 * Axelor Business Solutions
 *
 * Copyright (C) 2012-2014 Axelor (<http://axelor.com>).
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.axelor.db;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.axelor.db.mapper.Mapper;
import com.axelor.db.mapper.Property;
import com.axelor.inject.Beans;

public class JpaRepository<T extends Model> implements Repository<T> {

	protected Class<T> modelClass;

	public JpaRepository(Class<T> modelClass) {
		this.modelClass = modelClass;
	}

	@Override
	public List<Property> fields() {
		final Property[] fields = JPA.fields(modelClass);
		if (fields == null) {
			return null;
		}
		return Arrays.asList(fields);
	}

	@Override
	public Query<T> all() {
		return JPA.all(modelClass);
	}

	@Override
	public T create(Map<String, Object> values) {
		return Mapper.toBean(modelClass, values);
	}

	@Override
	public T copy(T entity, boolean deep) {
		return JPA.copy(entity, deep);
	}

	@Override
	public T find(Long id) {
		return JPA.find(modelClass, id);
	}

	@Override
	public T save(T entity) {
		return JPA.save(entity);
	}

	@Override
	public void remove(T entity) {
		JPA.remove(entity);
	}

	@SuppressWarnings("unchecked")
	public static  <U extends Model>  JpaRepository<U> of(Class<U> type) {
		final Class<?> klass = JpaScanner.findRepository(type.getSimpleName() + "Repository");
		if (klass == null) {
			return null;
		}
		return (JpaRepository<U>) Beans.get(klass);
	}
}