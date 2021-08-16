import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/* This class defines a ball. It defines its properties and method for the movement of the ball */

public class Ball
{
    Circle circle;
    final int radius;
    int x;
    int y;
    int stepX = -1;
    int stepY = -1;

    // CONSTRUCTOR
    Ball(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.radius = 8;

        //Create a circle and set its attributes
        circle = new Circle();
        circle.setFill(Color.web("#C239B3"));
        circle.setRadius(radius);
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    // METHODS

    /* Method to move the ball by one step. Initially the ball will move diagonally upwards in the left direction
       since stepX = stepY = -1 initially.
     */
    void move()
    {
        x += stepX;
        y += stepY;

        circle.relocate(x, y);
    }
}
