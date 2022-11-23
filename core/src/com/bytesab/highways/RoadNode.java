package com.bytesab.highways;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class RoadNode extends Vector2{
	private static final long serialVersionUID = -1266291767269640602L;

	/**
	 * The nodes which make up a vehicle path
	 * 
	 * @param x
	 * @param y
	 * @param connectedNodes Nodes which this node is connected to
	 */
	public RoadNode(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Debug method for rendering nodes on screen
	 */
	public void drawPoint() {
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.circle(x, y, 5);
		shapeRenderer.end();
	}
}
