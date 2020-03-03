package com.jdc.image.crop.utils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquareSelectedArea extends Rectangle{

	private static final double CONTROL_WIDTH = 8;
	private double startX;
	private double startY;
	
	private Group group;
	
	private DoubleProperty xEndProperty;
	private DoubleProperty yEndProperty;
	
	private DoubleProperty height;
	private DoubleProperty width;

	public SquareSelectedArea(Group group, double width, double height) {
		
		this.group = group;
		this.width = new SimpleDoubleProperty(width);
		this.height = new SimpleDoubleProperty(height);
		
		xEndProperty = new SimpleDoubleProperty();
		yEndProperty = new SimpleDoubleProperty();
		
		xEndProperty.bind(xProperty().add(widthProperty()));
		yEndProperty.bind(yProperty().add(heightProperty()));
		
		widthProperty().bind(heightProperty());
		
		setTransparent(this);
		setStroke(Color.WHITE);
		setStrokeWidth(2);
		group.getChildren().add(this);
		
		createDragArea();
		
		createGrayOutToOutsideRectangle();

		createResizableControl();		
	}
	
	private void createDragArea() {
				
		Rectangle drag = new Rectangle();
		
		drag.widthProperty().bind(widthProperty());
		drag.heightProperty().bind(heightProperty());
		drag.xProperty().bind(xProperty());
		drag.yProperty().bind(yProperty());
		
		setTransparent(drag);
		
		group.getChildren().add(drag);
		
		drag.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> drag.getParent().setCursor(Cursor.OPEN_HAND));
		drag.addEventHandler(MouseEvent.MOUSE_EXITED, event -> drag.getParent().setCursor(Cursor.DEFAULT));
		
		drag.setOnMousePressed(event -> {
			startX = event.getX();
			startY = event.getY();
		});
		
		drag.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
			
			double offsetX = event.getX() - startX;
			double offsetY = event.getY() - startY;
			
			double newX = getX() + offsetX;
			double newY = getY() + offsetY;
			
			if(newX >= 0 && (newX + getWidth()) <= width.doubleValue()) {
				setX(newX);
				startX = event.getX();
			}
			
			if(newY >= 0 && (newY + getHeight()) <= height.doubleValue()) {
				setY(newY);
				startY = event.getY();
			}
			
		});
	}
	
	private void createResizableControl() {
		
		Rectangle rec = new Rectangle(CONTROL_WIDTH, CONTROL_WIDTH);
		rec.setFill(Color.WHITE);
		rec.xProperty().bind(xProperty().add(widthProperty()).subtract(rec.widthProperty().divide(2)));
		rec.yProperty().bind(yProperty().add(heightProperty()).subtract(rec.heightProperty().divide(2)));
		
		group.getChildren().add(rec);
		
		rec.setOnMouseEntered(event -> getParent().setCursor(Cursor.SE_RESIZE));
		rec.setOnMouseExited(event -> getParent().setCursor(Cursor.DEFAULT));
		
		rec.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
			startY = getY();
		});

		rec.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
			
			if(event.getY() < height.doubleValue()) {
				double newHeight = event.getY() - startY;
				setHeight(newHeight);
			}
		});
		
	}
	
	private void createGrayOutToOutsideRectangle() {

		Rectangle top = getGrayOutRectangle();
		top.setX(0);
		top.setY(0);
		top.widthProperty().bind(width);
		top.heightProperty().bind(yProperty());
		
		Rectangle bottom = getGrayOutRectangle();
		bottom.setX(0);
		bottom.yProperty().bind(yEndProperty);
		bottom.widthProperty().bind(width);
		bottom.heightProperty().bind(height.subtract(yEndProperty));
		
		Rectangle left = getGrayOutRectangle();
		left.setX(0);
		left.yProperty().bind(yProperty());
		left.widthProperty().bind(xProperty());
		left.heightProperty().bind(yEndProperty.subtract(yProperty()));
		
		Rectangle right = getGrayOutRectangle();
		right.xProperty().bind(xEndProperty);
		right.yProperty().bind(yProperty());
		right.widthProperty().bind(width.subtract(xEndProperty));
		right.heightProperty().bind(yEndProperty.subtract(yProperty()));
		
	}
	
	private Rectangle getGrayOutRectangle() {
		Rectangle rec = new Rectangle();
		rec.setFill(Color.color(0, 0, 0, 0.5));
		group.getChildren().add(rec);
		return rec;
	}

	private void setTransparent(Rectangle rec) {
		rec.setFill(Color.color(1, 1, 1, 0));
	}
}
