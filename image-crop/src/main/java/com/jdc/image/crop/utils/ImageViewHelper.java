package com.jdc.image.crop.utils;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

public class ImageViewHelper {
	
	private double height;
	private double x;
	private double y;

	public ImageViewHelper(ImageView image) {
		super();
		
		Bounds bounds = image.getBoundsInParent();
		boolean portrait = bounds.getWidth() < bounds.getHeight();
		
		this.height = portrait ? bounds.getWidth() : bounds.getHeight();
		
		if(portrait) {
			x = 0;
			y = (bounds.getHeight() - height) / 2;
		} else {
			y = 0;
			x = (bounds.getWidth() - height) / 2;
		}
	}

	public double getHeight() {
		return height;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
}
