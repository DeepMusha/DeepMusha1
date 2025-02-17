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
package net.sourceforge.plantuml.real;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sourceforge.plantuml.log.Logme;

class RealMax extends AbstractReal implements Real {

	private final List<Real> all = new ArrayList<>();
	private final Throwable creationPoint;

	RealMax(Collection<Real> reals) {
		super(line(reals));
		this.all.addAll(reals);
		this.creationPoint = new Throwable();
		this.creationPoint.fillInStackTrace();
	}

	static RealLine line(Collection<Real> reals) {
		return ((AbstractReal) reals.iterator().next()).getLine();
	}

	public String getName() {
		return "max " + all;
	}

	@Override
	double getCurrentValueInternal() {
		double result = all.get(0).getCurrentValue();
		for (int i = 1; i < all.size(); i++) {
			Throwable t = new Throwable();
			t.fillInStackTrace();
			final int stackLength = t.getStackTrace().length;
			if (stackLength > 1000) {
				System.err.println("The faulty RealMax " + getName());
				System.err.println("has been created here:");
				printCreationStackTrace();
				throw new IllegalStateException("Infinite recursion?");
			}
			final double v = all.get(i).getCurrentValue();
			if (v > result) {
				result = v;
			}
		}
		return result;
	}

	public Real addFixed(double delta) {
		return new RealDelta(this, delta);
	}

	public Real addAtLeast(double delta) {
		throw new UnsupportedOperationException();
	}

	public void ensureBiggerThan(Real other) {
		throw new UnsupportedOperationException();
	}

	public void printCreationStackTrace() {
		Logme.error(creationPoint);
	}

}
