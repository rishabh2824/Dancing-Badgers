//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    MovingThing.java
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

/**
 * This class models moving thing objects. A moving thing is defined by its speed and to which
 * direction it is facing (right or left).
 */
public class MovingThing extends Thing implements Comparable<MovingThing> {

  protected boolean isFacingRight; //indicates whether this MovingThing is facing right or not
  protected int speed; //movement speed of this MovingThing

  /**
   * Creates a new MovingThing and sets its speed, image file, and initial x and y position. A
   * MovingThing object is initially facing right.
   *
   * @param x             the x-coordinate of the moving thing
   * @param y             the y-coordinate of the moving thing
   * @param speed         the speed of the moving thing
   * @param imageFileName the fileName of the moving thing
   */
  public MovingThing(float x, float y, int speed, String imageFileName) {
    super(x,y,imageFileName);
    this.speed = speed;
    this.isFacingRight = true;
  }

  /**
   * Draws this MovingThing at its current position. The implementation details of this method is
   * fully provided in the write-up of p05.
   */
  public void draw() {
    // draw this MovingThing at its current position
    processing.pushMatrix();
    processing.rotate(0.0f);
    processing.translate(x, y);
    if (!isFacingRight) {
      processing.scale(-1.0f, 1.0f);
    }
    processing.image(image(), 0.0f, 0.0f);
    processing.popMatrix();
  }

  /**
   * Compares this object with the specified MovingThing for order, in the increasing order of their
   * speeds.
   *
   * @param other the MovingThing object to be compared.
   * @return zero if this object and other have the same speed, a negative integer if the speed of
   * this moving object is less than the speed of other, and a positive integer otherwise.
   */
  public int compareTo(MovingThing other) {
    return this.speed - other.speed;
  }
}
