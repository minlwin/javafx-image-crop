package com.jdc.image.crop.impl;

import com.jdc.image.crop.ImageCropper;
import com.jdc.image.crop.utils.ImageViewHelper;
import com.jdc.image.crop.utils.SquareSelectedArea;

import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class SquareImageCrop implements ImageCropper{
	
	private ImageView imageView;
	private SquareSelectedArea selectedArea;

	public SquareImageCrop(ImageView imageView, Group group) {
		super();
		this.imageView = imageView;
		
		ImageViewHelper helper = new ImageViewHelper(imageView);
		Bounds bound = imageView.getBoundsInParent();
		
		selectedArea = new SquareSelectedArea(group, bound.getWidth(), bound.getHeight());
		selectedArea.setHeight(helper.getHeight());
		selectedArea.setX(helper.getX());
		selectedArea.setY(helper.getY());
	}


	@Override
	public Image getImage() {
		
		Bounds bounds = selectedArea.getBoundsInParent();
        int width = (int) bounds.getWidth();
        int height = (int) bounds.getHeight();

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        parameters.setViewport(new Rectangle2D(bounds.getMinX(), bounds.getMinY(), width, height));

        return imageView.snapshot(parameters, new WritableImage(width, height));
	}

}
