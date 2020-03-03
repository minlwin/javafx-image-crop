package com.jdc.image.crop;

import com.jdc.image.crop.impl.SquareImageCrop;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class ImageCropFactory {

	
	public static ImageCropper getCropper(Shape shape, ImageView image, Group group) {
		
		ImageCropper cropper = null;
		
		switch (shape) {
		case Square:
			cropper = new SquareImageCrop(image, group);
			break;
		case Rectangle:
			
			break;
		case Circle:
			
			break;

		default:
			break;
		}
		
		return cropper;
	}
}
