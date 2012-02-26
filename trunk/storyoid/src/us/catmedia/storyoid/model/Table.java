/*
 
StorYoid: Mobile writing tool for Android
Copyright (C) 2008 Werner Keil

Portions StorYBook: Summary-based tool for novelist and authors.
Copyright (C) 2008 Martin Mustun

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package us.catmedia.storyoid.model;

import android.content.res.Resources;

public abstract class Table {
	protected String tableName;
	protected int id = -1;
	protected boolean isNew;
	private Resources resources;
	
	private Table(String tableName, Resources res) {
		this.tableName = tableName;
		this.resources = res;
	}
	
	public Table(String tableName) {
		this(tableName, null);
	}

	public abstract boolean save() throws Exception;
	
	public abstract String getLabelText();
		
	public String toString() {
		return "" + getId();
	}

	@Override
	public boolean equals(Object obj) {
		return this.getId() == ((Table) obj).getId();
	}

	@Override
	public int hashCode() {
		int hash = 1;
		// hash = hash * 31 + getTablename().hashCode();
		hash = hash * 31 + new Integer(getId()).hashCode();
		return hash;
	}

	public int getId() {
		return id;
	}

	public boolean isNew() {
		return isNew;
	}

	public String getTablename() {
		return tableName;
	}

	/**
	 * @return the resources
	 */
	private Resources getResources() {
		return resources;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(Resources resources) {
		this.resources = resources;
	}

}