//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Thing.java
// Course:   CS 300 Spring 2023
//
// Author:   Rishabh Jain
// Email:    rvjain@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         None
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////

import java.io.File;

/**
 * This class models a graphic Thing which can be drawn at a give (x,y) position within the display
 * window of a graphic application.
 */
public class Thing extends Object {

  //PApplet object that represents the display window of this graphic application
  protected static processing.core.PApplet processing;
  private processing.core.PImage image; //image of this graphic thing of type PImage
  protected float x; //x-position of this thing in the display window
  protected float y; //y-position of this thing in the display window

  /**
   * Creates a new Thing located at a specific (x, y) position of the display window.
   *
   * @param x             x-position of this thing in the display window
   * @param y             y-position of this thing in the display window
   * @param imageFilename filename of the image of this thing, for instance "name.png"
   */
  public Thing(float x, float y, String imageFilename) {
    this.x = x;
    this.y = y;
    String imagePath = "images" + File.separator + imageFilename;
    this.image = processing.loadImage(imagePath);
  }

  /**
   * Draws this thing to the display window at its current (x,y) position.
   */
  public void draw() {
    processing.image(image, x, y);
  }

  /**
   * Sets the PApplet object display window where this Thing object will be drawn.
   *
   * @param processing PApplet object that represents the display window
   */
  public static void setProcessing(processing.core.PApplet processing) {
    Thing.processing = processing;
  }

  /**
   * Returns a reference to the image of this thing.
   *
   * @return the image of type PImage of the Thing object
   */
  public processing.core.PImage image() {
    return image;
  }

  /**
   * Checks if the mouse is over this Thing object.
   *
   * @return true if the mouse is over this Thing, otherwise returns false.
   */
  public boolean isMouseOver() {
    return processing.mouseX >= this.x - (this.image.width / 2)
        && processing.mouseX <= this.x + (this.image.width / 2)
        && processing.mouseY >= this.y - (this.image.height / 2)
        && processing.mouseY <= this.y + (this.image.height/2);
  }
}
