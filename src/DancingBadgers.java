//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    DancingBadgers.java
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

import processing.core.PApplet;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * This is the main class for the p05 Dancing Bangers III program
 */
public class DancingBadgers extends PApplet {

  // array storing badgers dance show steps
  private static DanceStep[] badgersDanceSteps =
      new DanceStep[] {DanceStep.LEFT, DanceStep.RIGHT, DanceStep.RIGHT, DanceStep.LEFT,
          DanceStep.DOWN, DanceStep.LEFT, DanceStep.RIGHT, DanceStep.RIGHT, DanceStep.LEFT,
          DanceStep.UP};
  // array storing the positions of the dancing badgers at the start of the dance show
  private static float[][] startDancePositions =
      new float[][] {{300, 250}, {364, 250}, {428, 250}, {492, 250}, {556, 250}};

  private static processing.core.PImage backgroundImage; // the background image
  // maximum number of Badger objects allowed in the basketball court
  private static int badgersCountMax;
  private boolean danceShowOn; // tells whether the dance show is on
  private static Random randGen; // generator of random numbers
  private static ArrayList<Thing> things; // ArrayList storing Thing objects

  /**
   * Sets the size of the display window of this graphic application
   */
  @Override //settings in class processing.core.PApplet
  public void settings() { this.size(800, 600);}

  /**
   * Sets the title and defines the initial environment properties of this graphic application. This
   * method initializes all the data fields defined in this class.
   */
  @Override //setup in class processing.core.PApplet
  public void setup() {
    this.getSurface().setTitle("P5 Dancing Badgers"); // displays the title of the screen
    this.textAlign(3, 3); // sets the alignment of the text
    this.imageMode(3); // interprets the x and y position of an image to its center
    this.focused = true; // confirms that this screen is "focused", meaning
    // it is active and will accept mouse and keyboard input.

    Thing.setProcessing(this);
    Badger.setProcessing(this);

    backgroundImage = loadImage("images" + File.separator + "background.png");
    badgersCountMax = 5;
    randGen = new Random();

    //creates the thing arrayList
    things = new ArrayList<>();
    //Adds objects to the things arrayList
    Thing thing1 = new Thing(50, 50, "target.png");
    Thing thing2 = new Thing(750, 550, "target.png");
    Thing thing3 = new Thing(750, 50, "shoppingCounter.png");
    Thing thing4 = new Thing(50, 550, "shoppingCounter.png");
    things.add(thing1);
    things.add(thing2);
    things.add(thing3);
    things.add(thing4);
    things.add(new StarshipRobot(thing1, thing3, 3));
    things.add(new StarshipRobot(thing2, thing4, 5));
    things.add(new Basketball(50, 300));
    things.add(new Basketball(750, 300));
  }

  /**
   * Callback method that draws and updates the application display window. This method runs in an
   * infinite loop until the program exits. Overrides: draw in class processing.core.PApplet.
   */
  public void draw() {
    background(color(255, 218, 185));
    image(backgroundImage, width/2, height/2);

    for (Thing i : things) {
      i.draw();
    }
  }

  /**
   * Callback method called each time the user presses the mouse. This method iterates through the
   * list of things. If the mouse is over a Clickable object, it calls its mousePressed method, then
   * returns. Overrides: mousePressed in class processing.core.PApplet.
   */
  public void mousePressed() {
    // implementation code here
    for (Thing i : things) {

      if (i instanceof Badger && i.isMouseOver()) {
        ((Clickable) i).mousePressed();
        return;
      } else if (i instanceof Clickable && i.isMouseOver()) {
        ((Clickable) i).mousePressed();
      }
    }
  }

  /**
   * Callback method called each time the mouse is released. This method calls the mousePressed()
   * method on every Clickable object stored in the things list. Overrides: mouseReleased in class
   * processing.core.PApplet.
   */
  public void mouseReleased() {
    for (Thing i : things) {
      if (i instanceof Clickable && i.isMouseOver()) {
        ((Clickable) i).mouseReleased();
      }
    }
  }

  /**
   * Gets the number of Badger objects present in the basketball arena.
   *
   * @return the number of Badger objects present in the basketball arena.
   */
  public int badgersCount() {
    int returnValue = 0;
    for (Thing i : things) {
      if (i instanceof Badger) {
        returnValue++;
      }
    }
    return returnValue;
  }

  /**
   * Sets the badgers start dance positions. The start dance positions of the badgers are provided
   * in the startDancePositions array. The array startDancePositions contains badgersCountMax dance
   * positions. If there are fewer Badger objects in the basketball arena, they will be assigned the
   * first positions.
   */
  private void setStartDancePositions() {
    int index = 0;
    for (Thing i : things) {
      if (i instanceof Badger) {
        float[] position = startDancePositions[index];
        i.x = position[0];
        i.y = position[1];
        index++;
      }
    }
  }

  /**
   * Callback method called each time the user presses a key. b-key: If the b-key is pressed and the
   * danceShow is NOT on, a new badger is added to the list of things. Up to badgersCountMax can be
   * added to the basketball arena. c-key: If the c-key is pressed, the danceShow is terminated
   * (danceShowOn set to false), and ALL MovingThing objects are removed from the list of things.
   * This key removes MovingThing objects ONLY. d-key: This key starts the dance show if the
   * danceShowOn was false, and there is at least one Badger object in the basketball arena.
   * Starting the dance show, sets the danceShowOn to true, sets the start dance positions of the
   * Badger objects, and calls the startDancing() method on every Badger object present in the list
   * of things. Pressing the d-key when the danceShowOn is true or when there are no Badger objects
   * present in the basketball arena has no effect. r-key: If the danceShow is NOT on and the d-key
   * is pressed when the mouse is over a Badger object, this badger is removed from the list of
   * things. If the mouse is over more than one badger, the badger at the lowest index position will
   * be deleted. s-key: If the s-key is pressed, the danceShow is terminated and all the Badger
   * objects stop dancing. Pressing the s-key does not remove anything. Overrides: keyPressed in
   * class processing.core.PApplet.
   */
  public void keyPressed() {

    //Index for badgers position in start dance position array
    switch (Character.toUpperCase(key)) {
      case 'B':
        randGen = new Random();
        if (!danceShowOn && badgersCount() < badgersCountMax) {
          things.add(new Badger(randGen.nextFloat(width), randGen.nextFloat(height), badgersDanceSteps));
        }
        break;
      case 'C':
        danceShowOn = false;
        //ArrayList to store things after removing movingThings
        for (int i = things.size() - 1; i >= 0; i--) {
          if (things.get(i) instanceof MovingThing) {
            things.remove(i);
          }
        }
        break;
      case 'D':
        if (!danceShowOn && badgersCount() > 0) {
          danceShowOn = true;
          setStartDancePositions();
          for (Thing i : things) {
            if (i instanceof Badger) {
              ((Badger) i).startDancing();
            }
          }
        }
      case 'R':
        if (!danceShowOn) {
          for (Thing i : things) {
            if (i instanceof Badger && i.isMouseOver()) {
              things.remove(i);
              break;
            }
          }
        }
        break;
      case 'S':
      danceShowOn = false;
      for (Thing i : things) {
        if (i instanceof Badger) {
          ((Badger) i).stopDancing();
        }
      }
    }
  }

  /**
   * Driver method to run this graphic application
   */
  public static void main(String[] args) {
    PApplet.main("DancingBadgers");
  }
}
