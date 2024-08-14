//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Badger.java
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
 * This class models a Badger object in the P05 Dancing Badgers III programming assignment
 */
public class Badger extends MovingThing implements Clickable {
  private DanceStep[] danceSteps; //array storing this Badger's dance show steps
  private boolean isDancing; //indicates whether this badger is dancing or not
  private boolean isDragging; //indicates whether this badger is being dragged or not
  private float[] nextDancePosition; //stores the next dance (x, y) position of this Badger.
  private static int oldMouseX; //old x-position of the mouse
  private static int oldMouseY; //old y-position of the mouse
  private int stepIndex; //index position of the current dance step of this badger

  /**
   * Creates a new Badger object positioned at a specific position of the display window and whose
   * moving speed is 2. When created, a new badger is not dragging and is not dancing. This
   * constructor also sets the danceSteps of the created Badger to the one provided as input and
   * initializes stepIndex to 1.
   *
   * @param x          x-position of this Badger object within the display window
   * @param y          y-position of this Badger object within the display window
   * @param danceSteps perfect-size array storing the dance steps of this badger
   */
  public Badger(float x, float y, DanceStep[] danceSteps) {
    super(x, y, 2, "badger.png");
    this.isDragging = false;
    this.isDancing = false;
    this.danceSteps = danceSteps;
    this.stepIndex = 1;
  }

  /**
   * Draws this badger to the display window. Also, this method: - calls the drag() behavior if this
   * Badger is dragging - calls the dance() behavior if this Badger is dancing To draw the badger to
   * the screen, think of using partial overriding (super.draw()) as the image of the Badger is not
   * directly visible here.
   */
  public void draw() {
    super.draw();
    if (isDragging()) {
      drag();
    } else if (isDancing) {
      dance();
    }
  }

  /**
   * Checks whether this badger is being dragged
   *
   * @return true if the badger is being dragged, false otherwise
   */
  public boolean isDragging() {
    return isDragging;
  }

  /**
   * Helper method to drag this Badger object to follow the mouse moves
   */
  private void drag() {
    int dx = processing.mouseX - oldMouseX;
    int dy = processing.mouseY - oldMouseY;
    x += dx;
    y += dy;

    if (x > 0) {
      x = Math.min(x, processing.width);
    } else {
      x = 0;
    }
    if (y > 0) {
      y = Math.min(y, processing.height);
    } else {
      y = 0;
    }
    oldMouseX = processing.mouseX;
    oldMouseY = processing.mouseY;
  }

  /**
   * Starts dragging this badger
   */
  public void startDragging() {
    oldMouseX = processing.mouseX;
    oldMouseY = processing.mouseY;
    this.isDragging = true;
    drag();
  }

  /**
   * Stops dragging this Badger object
   */
  public void stopDragging() {this.isDragging = false;}

  /**
   * Defines the behavior of this Badger when it is clicked. If the mouse is over this badger and
   * this badger is NOT dancing, this method starts dragging this badger.
   */
  public void mousePressed() {
    if (isMouseOver() && !isDancing) {
      this.startDragging();
      oldMouseX = processing.mouseX;
      oldMouseY = processing.mouseY;
    }
  }

  /**
   * Defines the behavior of this Badger when the mouse is released. If the mouse is released, this
   * badger stops dragging.
   */
  public void mouseReleased() {stopDragging();}

  /**
   * This helper method moves this badger one speed towards its nextDancePosition. Then, it checks
   * whether this Badger is facing right and updates the isFacingRight data field accordingly. After
   * making one move dance, a badger is facing right if the x-move towards its next dance position
   * is positive, otherwise, it is facing left.
   *
   * @return true if this Badger almost reached its next dance position, meaning that the distance
   * to its next dance position is less than 2 times its speed. Otherwise, return false.
   */
  private boolean makeMoveDance() {
    //The x-distance left to be covered
    float dx =  nextDancePosition[0] - this.x;
    //The y-distance left to be covered
    float dy = nextDancePosition[1] - this.y;
    //The actual distance left
    int distance = (int) Math.sqrt(dx * dx + dy * dy);
    //The new current x-destination
    float newX = this.x + (speed * dx / distance);
    //The new current y-destination
    float newY = this.y + (speed * dy / distance);
    //Sets the new current destinations
    this.x = newX;
    this.y = newY;

    isFacingRight = dx > 0;
    return distance < speed*2;
  }

  /**
   * Implements the dance behavior of this Badger. This method prompts the Badger to make one move
   * dance. If the makeMoveDance method call returns true (meaning the badger almost reached its
   * nextDancePosition), this method MUST: - update its next dance position (see
   * DanceStep.getPositionAfter()), - increment the stepIndex. Note that the danceSteps array is a
   * circular indexing array. The stepIndex should be incremented by one and then wrapped around
   * with respect to the length of the array.
   */
  private void dance() {
    if (makeMoveDance()) {
      nextDancePosition = danceSteps[stepIndex].getPositionAfter(x, y);
      if (stepIndex == danceSteps.length - 1) {
        stepIndex = 0;
      } else {
        stepIndex++;
      }
    }
  }

  /**
   * Prompts this badger to start dancing. This method: - updates the isDancing data field - stops
   * dragging this badger - sets stepIndex to zero - Resets the nextDancePosition
   */
  public void startDancing() {
    this.isDancing = true;
    this.stopDragging();
    this.stepIndex = 0;
    this.nextDancePosition = danceSteps[0].getPositionAfter(x, y);
    dance();
  }

  /**
   * Prompts this badger to stop dancing. Sets the isDancing data field to false.
   */
  public void stopDancing() {
    this.isDancing = false;
  }
}
