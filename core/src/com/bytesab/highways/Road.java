package com.bytesab.highways;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Road implements LogicalObject {
	public boolean drawDebug = false;
	public RoadNode start, mid1, mid2, end;
	public ArrayList<Road> beforeRoads, afterRoads;
	private ArrayList<Vehicle> vehicles, vehRemoved;
	private Bezier<Vector2> curve;

	/**
	 * Path which has vehicles that travel along it
	 * 
	 * @param start
	 * @param mid
	 * @param end
	 */
	public Road(RoadNode start, RoadNode mid1, RoadNode mid2, RoadNode end) {
		this.start = start;
		this.mid1 = mid1;
		this.mid2 = mid2;
		this.end = end;
		this.beforeRoads = new ArrayList<Road>();
		this.afterRoads = new ArrayList<Road>();
		this.vehicles = new ArrayList<Vehicle>();
		this.vehRemoved = new ArrayList<Vehicle>();
		this.curve = new Bezier<Vector2>(start, mid1, mid2, end);
	}

	/**
	 * Path which has vehicles that travel along it
	 * 
	 * @param start
	 * @param mid
	 * @param end
	 * @param drawDebug Flag to draw debug graphics
	 */
	public Road(RoadNode start, RoadNode mid1, RoadNode mid2, RoadNode end, boolean drawDebug) {
		this(start, mid1, mid2, end);
		this.drawDebug = drawDebug;
	}

	@Override
	public void update(long deltaTime) {
		for (Vehicle vehicle : vehicles) {
			updateVehiclePos(vehicle, deltaTime);
		}
		checkForVehRemoved();
	}

	public void render() {
		ShapeRenderer sr = new ShapeRenderer();
		Array<Vector2> p = curve.points;
		sr.begin(ShapeType.Line);
		// @formatter:off
		sr.curve(p.get(0).x, p.get(0).y, 
				 p.get(1).x, p.get(1).y, 
				 p.get(2).x, p.get(2).y, 
				 p.get(3).x, p.get(3).y, 100); 
		// @formatter:on
		sr.end();

		for (Vehicle vehicle : vehicles) {
			vehicle.render();
		}
		if (drawDebug) {
			start.drawPoint();
			mid1.drawPoint();
			mid2.drawPoint();
			end.drawPoint();
		}
	}

	public void addVehicle(Vehicle vehicle) {
		vehicles.add(vehicle);
		vehicle.setCenter(start);
	}

	/**
	 * Link a road after
	 *
	 * @param road
	 */
	public void linkAfterRoad(Road road) {
		this.afterRoads.add(road);
		road.beforeRoads.add(this);
	}

	/**
	 * Link a road before
	 * 
	 * @param road
	 */
	public void linkBeforeRoad(Road road) {
		this.beforeRoads.add(road);
		road.afterRoads.add(this);
	}

	/**
	 * Update vehicle position along path for next frame
	 * 
	 * @param vehicle
	 * @param deltaTime
	 */
	private void updateVehiclePos(Vehicle vehicle, long deltaTime) {
		if (vehicle.posAlong < 1) { // if vehicle has not yet reached end of road
			vehicle.posAlong += 0.001 * vehicle.speed;
			vehicle.setCenter(curve.valueAt(new Vector2(), vehicle.posAlong));
		} else { // if vehicle has finished road
			if (afterRoads.size() > 0) { // if has a next road
				vehRemoved.add(vehicle);
				vehicle.posAlong = 0;
				afterRoads.get(0).addVehicle(vehicle);
			}
		}
	}

	/**
	 * Check if there are any vehicles to be removed. Called at the end the update.
	 */
	private void checkForVehRemoved() {
		for (Vehicle vehicle : vehRemoved) {
			vehicles.remove(vehicle);
		}
		vehRemoved.clear();
	}

	// old linear method
	/*private void updateVehiclePos(Vehicle vehicle, long deltaTime) {
		Vector2 pos = vehicle.getCenter(new Vector2());
		double angle = Math.atan2(end.x - pos.x, end.y - pos.y);
		// @formatter:off
		Vector2 newPos = new Vector2(
				(float) (pos.x + Math.sin(angle) * vehicle.speed),
				(float) (pos.y + Math.cos(angle) * vehicle.speed));
		// @formatter:on
		vehicle.setCenter(newPos);
		System.out.println(curve.approximate(vehicle.getCenter(new Vector2())));
	}*/
}
