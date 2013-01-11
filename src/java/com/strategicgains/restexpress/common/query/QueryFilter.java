/*
 * Copyright 2011, Strategic Gains, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.strategicgains.restexpress.common.query;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Supports the concept of filtering a result based on the 'filter' query parameter.
 * a list of field name/value pairs separated by a vertical bar ('|') and the field name
 * separated from the value with two colons ('::').
 * <p/>
 * To filter on name: ?filter=name::todd
 * <p/>
 * To filter on name and description: ?filter=name::todd|description::amazing
 * 
 * @author toddf
 * @since Apr 12, 2011
 */
public class QueryFilter
{
	private Map<String, String> filters = null;
	
	public QueryFilter()
	{
		super();
	}
	
	/**
	 * Create a QueryFilter instance from a Map of name/value pairs.
	 * 
	 * @param filters name/value pairs to match on.
	 */
	public QueryFilter(Map<String, String> filters)
	{
		this();
		this.filters = new HashMap<String, String>(filters);
	}

	/**
	 * Add a filter criteria to this QueryFilter instance.
	 * 
	 * @param name the property name to filter on.  Cannot be null.
	 * @param value the value to match.  Cannot be null.
	 * @return a reference to this QueryFilter to facilitate method chaining.
	 */
	public QueryFilter addCriteria(String name, String value)
	{
		if (filters == null)
		{
			filters = new HashMap<String, String>();
		}

		filters.put(name, value);
		return this;
	}

	/**
	 * Returns true if this QueryFilter instance would affect the query (has effective filters).
	 * 
	 * @return true if filters exist within this QueryFilter instance
	 */
	public boolean hasFilters()
	{
		return (filters != null && !filters.isEmpty());
	}
	
	/**
	 * Iterate the filter criteria within this QueryFilter, invoking the FilterCallback
	 * to presumably construct a query.
	 * 
	 * @param callback a FilterCallback instance
	 */
	public void iterate(FilterCallback callback)
	{
		if (callback == null || !hasFilters()) return;

		for (Entry<String, String> entry : filters.entrySet())
		{
			callback.filterOn(new FilterComponent(entry.getKey(), entry.getValue()));
		}
	}
}
