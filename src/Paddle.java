import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/* This class defines a racket. It defines its properties and an abstract method for the movement of the racket. */

public class Paddle
{
    Rectangle rectangle;
    final int width;
    final int height;
    int x;
    int y;
    int stepY;

    // CONSTRUCTOR
    public Paddle(int x, int y, int width, int height, int stepY)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.stepY = stepY;


        //Create a rectangle and set its attributes
        rectangle = new Rectangle(x, y, width, height);
        rectangle.setFill(Color.web("#C239B3"));
    }

    // METHODS

    // Method to move the racket
    void move()
    {
        y += stepY;

        rectangle.relocate(x, y);
    }

    // Method to the reverse the current direction
    void reverseDirection()
    {
        stepY = -stepY;
    }

    public void setPosition(int y)
    {
        this.y = y;
        rectangle.relocate(x, y);
    }
}
