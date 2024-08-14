//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    StarshipRobot.java
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
 * This class models StarshipRobot objects which can be triggered to move or do some actions.
 */
public class StarshipRobot extends MovingThing{
  private Thing destination; //destination point of this StarshipRobot at its current journey
  // delivering food to badgers
  private Thing source; //source point of this StarshipRobot at its current journey delivering
  // food to badgers

  /**
   * Creates a new StarshipRobot and sets its source, destination, and speed. The start point of
   * this StarshipRobot is set to the (x,y) position of source. When created, a StarshipRobot object
   * must face its destination.
   * [HINT] checking whether source.x is less than destination.x can help determining whether a
   * starship robot is facing right or not.
   * @param source Thing object representing the start point of this StarshipRobot
   * @param destination Thing object representing the destination point of this StarshipRobot
   * @param speed  movement speed of this StarshipRobot
   */
  public StarshipRobot(Thing source, Thing destination, int speed) {
    super(source.x, source.y, speed, "starshipRobot.png");
    this.speed = speed;
    this.source = source;
    this.destination = destination;
    if (source.x < destination.x) {
      super.isFacingRight = true;
    } else {
      super.isFacingRight = false;
    }
  }

  /**
   * Draws this StarshipRobot to the display window while it is in motion delivering food.
   * This method first prompts this StarshipRobot to go. Then, it draws it to the display window.
   * Think of partial overriding to draw this StarshipRobot as its image is not directly accessed
   * from here.
   * The super.draw() can do so!
   */
  public void draw() {
    super.draw();
    go();

  }

  /**
   * Checks whether this StarshipRobot is over a specific Thing
   * @param thing a given Thing object
   * @return true if this StarshipRobot is over the Thing object passed as input, otherwise, returns false.
   */
  public boolean isOver(Thing thing) {
    float x1 = this.x - this.image().width / 2;
    //Finds the x-coordinate of the top corner of first object
    float x2 = this.x + this.image().width / 2;
    //Finds the x-coordinate of the bottom corner of the second object
    float x3 = thing.x - thing.image().width / 2;
    //Finds the x-coordinate of the top corner of the second object
    float x4 = thing.x + thing.image().width / 2;

    //Finds the y-coordinate of the bottom corner of the first object
    float y1 = this.y - this.image().height / 2;
    //Finds the y-coordinate of the top corner of the first object
    float y2 = this.y + this.image().height / 2;
    //Finds the y-coordinate of the bottom corner of the second object
    float y3 = thing.y - thing.image().height / 2;
    //Finds the y-coordinate of the top corner of the second object
    float y4 = thing.y + thing.image().height / 2;

    //Checks if the images overlap
    if ((x1 < x4) && (x3 < x2) && (y1 < y4) && (y3 < y2)) {
      return true;
    }
    return false;
  }

  /**
   * Helper method to move this StarshipRobot towards its destination
   */
  private void moveTowardsDestination() {
    //The x-distance left to be covered
    float dx = destination.x - this.x;
    //The y-distance left to be covered
    float dy = destination.y - this.y;
    //The actual distance left
    int distance = (int) Math.sqrt(dx * dx + dy * dy);
    //The new current x-destination
    float newX = this.x + (speed * dx / distance);
    //The new current y-destination
    float newY = this.y + (speed * dy / distance);
    //Sets the new current destinations
    this.x = newX;
    this.y = newY;
  }

  /**
   * If the starship robot is over its destination, this method:
   * - switches the source and destination,
   * - switches the value of isFacingRight to its opposite (!isFacingRight), so that the starship
   * robot faces the opposite direction.
   */
  public void go() {
    if (isOver(destination)) {
      Thing temp = destination;
      destination = source;
      source = temp;
      isFacingRight = !isFacingRight;
    }
    moveTowardsDestination();
  }
}
