/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2023, Arnaud Roques
 *
 * Project Info:  http://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * http://plantuml.com/patreon (only 1$ per month!)
 * http://plantuml.com/paypal
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
 *
 * Original Author:  Arnaud Roques
 * 
 *
 */
package net.sourceforge.plantuml.svek.image;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.plantuml.graphic.UDrawable;
import net.sourceforge.plantuml.ugraphic.UEllipse;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UTranslate;
import net.sourceforge.plantuml.ugraphic.color.HColors;

public class ConnectedCircle implements UDrawable {

	private final double radius;
	private final List<Double> angles = new ArrayList<>();
	private final List<Point2D> points = new ArrayList<>();

	public ConnectedCircle(double radius) {
		this.radius = radius;
	}

	public void drawU(UGraphic ug) {
		final UEllipse circle = new UEllipse(2 * radius, 2 * radius);
		// ug.draw(circle);
		for (Double angle : angles) {
			final double delta = 30;
			final UEllipse part = new UEllipse(2 * radius, 2 * radius, angle - delta, 2 * delta);
			ug.draw(part);
		}
		ug = ug.apply(HColors.GREEN).apply(HColors.GREEN.bg());
		for (Point2D pt : points) {
			final UTranslate tr = new UTranslate(pt);
			// ug.apply(tr).draw(new UEllipse(2, 2));
		}

	}

	public void addSecondaryConnection(Point2D pt) {
		points.add(pt);
		// double angle = Math.atan2(pt.getY() - radius, pt.getX() - radius);
		// double angle = Math.atan2(pt.getX() - radius, pt.getY() - radius);
		double angle = Math.atan2(radius - pt.getY(), pt.getX() - radius);
		angle = angle * 180.0 / Math.PI;
		System.err.println("pt1=" + pt + " " + angle);
		angles.add(angle);

	}

}
