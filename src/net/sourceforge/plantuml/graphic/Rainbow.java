/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2017, Arnaud Roques
 *
 * Project Info:  http://plantuml.com
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * Original Author:  Arnaud Roques
 * 
 * Revision $Revision: 19109 $
 *
 */
package net.sourceforge.plantuml.graphic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.plantuml.ISkinParam;

public class Rainbow {

	private final List<HtmlColorAndStyle> colors = new ArrayList<HtmlColorAndStyle>();
	private final int colorArrowSeparationSpace;

	private Rainbow(int colorArrowSeparationSpace) {
		this.colorArrowSeparationSpace = colorArrowSeparationSpace;
	}

	public static Rainbow none() {
		return new Rainbow(0);
	}

	public Rainbow withDefault(Rainbow defaultColor) {
		if (this.size() == 0) {
			return defaultColor;
		}
		return this;
	}

	public static Rainbow build(HtmlColorAndStyle color) {
		if (color == null) {
			throw new IllegalArgumentException();
		}
		final Rainbow result = new Rainbow(0);
		result.colors.add(color);
		return result;
	}

	public static Rainbow build(ISkinParam skinParam, String colorString, int colorArrowSeparationSpace) {
		if (colorString == null) {
			return Rainbow.none();
		}
		final Rainbow result = new Rainbow(colorArrowSeparationSpace);
		for (String s : colorString.split(";")) {
			result.colors.add(HtmlColorAndStyle.build(skinParam, s));
		}
		return result;
	}

	public List<HtmlColorAndStyle> getColors() {
		return Collections.unmodifiableList(colors);
	}

	public HtmlColor getColor() {
		return colors.get(0).getColor();
	}

	public int getColorArrowSeparationSpace() {
		return colorArrowSeparationSpace;
	}

	public int size() {
		return colors.size();
	}

}
