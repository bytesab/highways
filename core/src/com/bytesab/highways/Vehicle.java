package com.bytesab.highways;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Vehicle extends Rectangle {
	private static final long serialVersionUID = -1750705074345725432L;
	public float speed, posAlong;

	public Vehicle(float x, float y, float speed, float posAlong) {
		super(0, 0, 30, 15);
		setCenter(x, y);
		this.speed = speed;
		this.posAlong = posAlong;
	}

	public void render() {
		ShapeRenderer sr = new ShapeRenderer();
		sr.begin(ShapeType.Filled);
		sr.rect(x, y, width, height);
		sr.end();
	}
}
