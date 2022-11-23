package com.bytesab.highways;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Highways extends ApplicationAdapter {
	private ArrayList<Road> roads = new ArrayList<Road>();
	private long lastTime;

	@Override
	public void create() {
		RoadNode conNodeTemp = new RoadNode(600, 400);
		roads.add(new Road(new RoadNode(200, 200), new RoadNode(300, 350), new RoadNode(500, 400), conNodeTemp, true));
		roads.add(new Road(conNodeTemp, new RoadNode(780, 400), new RoadNode(400, 100), new RoadNode(200, 100), true));
		roads.get(0).linkAfterRoad(roads.get(1));
		roads.get(0).addVehicle(new Vehicle(200, 200, 1, 0));
		roads.get(0).addVehicle(new Vehicle(200, 200, 1, .3f));
		roads.get(0).addVehicle(new Vehicle(200, 200, 1, .5f));
		roads.get(0).addVehicle(new Vehicle(200, 200, 1, .7f));
		lastTime = TimeUtils.nanoTime();
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0.3f, 0.3f, 1);

		long deltaTime = TimeUtils.nanoTime() - lastTime;

		for (Road road : roads) {
			road.update(deltaTime);
			road.render();
		}

		lastTime = TimeUtils.nanoTime();
	}

	@Override
	public void dispose() {

	}
}
