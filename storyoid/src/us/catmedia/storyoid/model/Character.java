/*
StorYBook: Summary-based tool for novelist and authors.
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

import android.graphics.Color;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import us.catmedia.storyoid.util.Logger;

import us.catmedia.storyoid.util.DbTools;
import us.catmedia.storyoid.util.I18N;
//import us.catmedia.storyoid.util.SwingTools;
import us.catmedia.storyoid.util.UITools;


public class Character extends Table {
	
	private static Logger logger = Logger.getLogger(Character.class);
	
	public static final String TABLE_NAME = "person";
	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_GENDER = "gender";
	public static final String COLUMN_FIRSTNAME = "firstname";
	public static final String COLUMN_LASTNAME = "lastname";
	public static final String COLUMN_ABBREVIATION = "abbreviation";
	public static final String COLUMN_BIRTHDAY = "birthday";
	public static final String COLUMN_DAY_OF_DEATH = "dayofdeath";
	public static final String COLUMN_OCCUPATION = "occupation";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_COLOR = "color";
	
	public static final Boolean GENDER_MALE = true;
	public static final Boolean GENDER_FEMALE = false;
	public static final String GENDER_MALE_STR = "male";
	public static final String GENDER_FEMALE_STR = "female";	
	
	private Boolean gender;
	private String firstname;
	private String lastname;
	private String abbreviation;
	private Date birthday;
	private Date dayOfDeath;
	private String occupation;
	private String description;
	private int color = -1;

	public Character() {
		super(TABLE_NAME);
		isNew = true;
	}

	/**
	 * This method must be packaged private! It is used
	 * by {@link StrandPeer} only.
	 * 
	 * @param id
	 *            the id
	 */
	Character(int id) {
		super(TABLE_NAME);
		this.id = id;
		isNew = false;
	}

	@Override
	public boolean save() throws Exception {
		try {
			String sql;
			if (isNew) {
				// insert
				sql = "insert into "
					+ TABLE_NAME
					+ "(" + COLUMN_GENDER
					+ ", " + COLUMN_FIRSTNAME
					+ ", " + COLUMN_LASTNAME
					+ ", " + COLUMN_ABBREVIATION
					+ ", " + COLUMN_BIRTHDAY
					+ ", " + COLUMN_DAY_OF_DEATH
					+ ", " + COLUMN_OCCUPATION
					+ ", " + COLUMN_DESCRIPTION
					+ ", " + COLUMN_COLOR
					+ ") values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			} else {
				// update
				sql = "update " + TABLE_NAME
					+ " set "
					+ COLUMN_GENDER + " = ?, "					
					+ COLUMN_FIRSTNAME + " = ?, "
					+ COLUMN_LASTNAME + " = ?, "
					+ COLUMN_ABBREVIATION + " = ?, "
					+ COLUMN_BIRTHDAY + " = ?, "
					+ COLUMN_DAY_OF_DEATH + " = ?, "
					+ COLUMN_OCCUPATION + " = ?, "
					+ COLUMN_DESCRIPTION + " = ?, "
					+ COLUMN_COLOR + " = ? "
					+ "where " + COLUMN_ID + " = ?";
			}
			PreparedStatement stmt = PersistenceManager.getInstance()
					.getConnection().prepareStatement(sql);
			// sets for insert & update
			stmt.setBoolean(1, getGender());
			stmt.setString(2, getFirstname());
			stmt.setString(3, getLastname());
			stmt.setString(4, getAbbreviation());
			stmt.setDate(5, getBirthday());
			stmt.setDate(6, getDayOfDeath());
			stmt.setString(7, getOccupation());
			stmt.setString(8, getDescription());
			if (getColor() == -1) {
				stmt.setNull(9, java.sql.Types.INTEGER);
			} else {
				stmt.setInt(9, getColor());
			}
			if (!isNew) {
				// sets for update only
				stmt.setInt(10, getId());
			}
			if (stmt.executeUpdate() != 1) {
				throw new SQLException(isNew ? "insert" : "update" + " failed");
			}
			if (isNew) {
				ResultSet rs = stmt.getGeneratedKeys();
				int count = 0;
				while (rs.next()) {
					if (count > 0) {
						throw new SQLException("error: got more than one id");
					}
					this.id = rs.getInt(1);
					logger.debug("save (insert): " + this);
					++count;
				}
				isNew = false;
			} else {
				logger.debug("save (update): " + this);
			}
			return true;
		} catch (SQLException e) {
			throw e;
		}
	}
	
	@Override
	public String getLabelText(){
		return toString() + " (" + getAbbreviation() + ")";
	}
	
	public Boolean isAlive(Date now) {
		if (getDayOfDeath() == null) {
			return true;
		}
		return now.after(getDayOfDeath()) ? false : true;
	}

	public Boolean getGender() {
		return gender;
	}

	public String getGenderStr() {
		if (gender) {
			return I18N.getMsg("msg.dlg.person.gender.male");
		}
		return I18N.getMsg("msg.dlg.person.gender.female");
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public String getAbbreviation() {
		return abbreviation == null ? "" : abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	public String getName() {
		return getFirstname() + " " + getLastname();
	}
	
	public String getFirstname() {
		return firstname == null ? "" : firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname == null ? "" : lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getBirthdayStr() {
		return birthday == null ? "" : birthday.toString();
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public void setBirthdayStr(String dateStr){
		try {
			Date birthday = DbTools.dateStrToSqlDate(dateStr);
			setBirthday(birthday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Date getDayOfDeath() {
		return dayOfDeath;
	}
	
	public String getDayOfDeathStr() {
		return dayOfDeath == null ? "" : dayOfDeath.toString();
	}

	public void setDayOfDeath(Date dayOfDeath) {
		this.dayOfDeath = dayOfDeath;
	}

	public String getOccupation() {
		return occupation == null ? "" : occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getDescription() {
		return description == null ? "" : description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setColor(String color) {
		this.color = Color.parseColor(String.valueOf(color));
	}
	
	@Override
	public String toString() {
		return getFirstname() + " " + getLastname()
				+ " (" + getAbbreviation() + ")";
	}

	public int calculateAge(Scene scene) {
		if (birthday == null) {
			return -1;
		}
				
		// Create a calendar object with the date of birth
		Calendar dateOfBirth = new GregorianCalendar();
		dateOfBirth.setTime(birthday);

		// character already dead?
		if (!isAlive(scene.getDate())) {
			Calendar death = new GregorianCalendar();
			death.setTime(getDayOfDeath());
			int age = death.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
			Calendar dateOfBirth2 = new GregorianCalendar();
			dateOfBirth2.add(Calendar.YEAR, age);
			if (death.before(dateOfBirth2)) {
				age--;
			}
			return age;
		}
		
		// Create a calendar object with today's date
		Calendar today = new GregorianCalendar();
		today.setTime(scene.getDate());

		// Get age based on year
		int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

		// Add the tentative age to the date of birth to get this year's birthday
		dateOfBirth.add(Calendar.YEAR, age);

		// If this year's birthday has not happened yet, subtract one from age
		if (today.before(dateOfBirth)) {
			age--;
		}
		return age;
	}
	
	public String getInfo() {
		return getInfo(null);
	}
	
	public String getInfo(Scene scene){
		return getInfo(scene, true);
	}

	public String getInfo(Scene scene, boolean shorten){
		StringBuilder buf = new StringBuilder();
		buf.append("<html><b>" + this + "</b>");
		buf.append("<table cellpadding='0'>");
		buf.append("<tr><td>" + I18N.getMsgColon("msg.dlg.person.gender")
				+ "</td><td width='10'>&nbsp;</td>");
		buf.append("<td>" + getGenderStr() + "</td></tr>");
	
		if (scene != null) {
			int age = calculateAge(scene);
			boolean dead = !isAlive(scene.getDate());
			if (age != -1) {
				buf.append("<tr><td>");
				buf.append(I18N.getMsgColon("msg.dlg.person.age"));
				buf.append("</td><td></td>");
				buf.append("<td>");
				buf.append(calculateAge(scene));
				if (dead) {
					buf.append(" (+)");
				}
				buf.append("</td></tr>");
			}
		}
		
		buf.append("<tr><td>" + I18N.getMsgColon("msg.dlg.person.birthday")
				+ "</td><td></td>");
		buf.append("<td>" + getBirthdayStr() + "</td></tr>");
		buf.append("<tr><td>" + I18N.getMsgColon("msg.dlg.person.death")
				+ "</td><td></td>");
		buf.append("<td>" + getDayOfDeathStr() + "</td></tr>");
		buf.append("<tr><td>" + I18N.getMsgColon("msg.dlg.person.occupation")
				+ "</td><td></td>");
		buf.append("<td>" + getOccupation() + "</td></tr>");
		buf.append("</table>");
		String descr = getDescription()
				.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
		buf.append("<div style='padding-top: 4px'>");
		if (shorten) {
			buf.append(UITools.shortenString(descr, 50));
		} else {
			buf.append(descr);
		}
		buf.append("</div>");
		return buf.toString();
	}	
}
