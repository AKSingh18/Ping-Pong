/* This class defines a Table. It defines its properties and method for the collision detection and prediction of
   next point of impact of with both the axes.

   The top and bottom edge of the table can be said as its two x-axes with the equation y = 0 and y = height.
   The left and right edge of the table can be said as its two y-axes with the equation x = 0 and x = width.

   Besides few obvious variables, it also contains a criticalX. criticalX is a table object dependent property,
   that is, it depends on its height, width and radius of ball.

   criticalX is the x-coordinate of the two critical points (cp, 0) and (cp, table.width). If the ball collides with
   any point on the top and bottom edge of the table to right of these points critical, then it will hit the right edge
   on its next collision.

   nextY is the y-coordinate where the computer paddle has to move next.
*/

public class Table
{
    final int width;
    final int height;
    final int tableCentreY;
    int userScore;
    int computerScore;
    private int criticalX;
    private int nextY;
    Ball ball;
    Paddle player;
    Paddle computer;

    // CONSTRUCTOR
    public Table(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.tableCentreY = height/2;

        userScore = computerScore = 0;
    }

    // SETTERS
    void setBall(Ball ball)
    {
        this.ball = ball;
        criticalX = (width-2*ball.radius) - (height-2*ball.radius);
    }

    void setPlayer(Paddle player)
    {
        this.player = player;
    }

    void setComputer(Paddle computer)
    {
        this.computer = computer;
    }

    // METHODS

    /* This method restricts the player paddle or computer paddle going out of the screen. It also restricts the computer
       paddle ahead of its set destination.
     */
    void handleBounds()
    {
        if (player.y < 0) player.setPosition(0);
        if (player.y+player.height > height) player.setPosition(height-player.height);

        if (computer.y < 0) computer.setPosition(0);
        if (computer.y+computer.height > height) computer.setPosition(height-computer.height);

        if (computer.y+computer.height/2 == nextY) computer.stepY = 0;
    }

    /* Method to handle the following collisions:
           1: Ball with table
           2: Ball with player paddle
           3: Ball with computer paddle
     */
    void handleCollision()
    {
        // Handle collision of the ball with the table

            // With y-axis
            if (ball.x == 0 || ball.x+2*ball.radius == width)
            {
                ball.stepX = -ball.stepX;

                // On collision with the left edge
                if (ball.x == 0)
                {
                    userScore++;
                    setDestination(getRightEdgeY(ball.stepY < 0, ball.x, ball.y));
                }
                // On collision with the right edge
                else
                {
                    computerScore++;
                    setDestination(tableCentreY);
                }
            }

            // With x-axis
            if (ball.y == 0 || ball.y+2*ball.radius == height) ball.stepY = -ball.stepY;

        // Handle collision of the ball with the player paddle

            // With its top and bottom
            if (ball.x < player.x+player.width && ((ball.y+2*ball.radius >= player.y && ball.y+2*ball.radius <= player.y+ball.radius) || (ball.y >= player.y+player.height-ball.radius && ball.y <= player.y+player.height)))
            {
                if (ball.y+2*ball.radius >= player.y && ball.y+2*ball.radius <= player.y+ball.radius)
                    ball.stepY = ball.stepY < 0 ? ball.stepY : -ball.stepY;
                else
                    ball.stepY = ball.stepY > 0 ? ball.stepY : -ball.stepY;

                setDestination(getRightEdgeY(ball.stepY < 0, ball.x, ball.y));
            }

            // With its front
            else if (ball.x == player.x+player.width && (ball.y+2*ball.radius >= player.y && ball.y <= player.y+player.height))
            {
                ball.stepX = ball.stepX > 0 ? ball.stepX : -ball.stepX;
                setDestination(getRightEdgeY(ball.stepY < 0, ball.x, ball.y));
            }

        // Handle collision of the ball with the computer paddle

            // With its top and bottom
            if (ball.x > computer.x+computer.width && ((ball.y+2*ball.radius >= computer.y && ball.y+2*ball.radius <= computer.y+ball.radius) || (ball.y >= computer.y+computer.height-ball.radius && ball.y <= computer.y+computer.height)))
            {
                if (ball.y+2*ball.radius >= computer.y && ball.y+2*ball.radius <= computer.y+ball.radius)
                    ball.stepY = ball.stepY < 0 ? ball.stepY : -ball.stepY;
                else
                    ball.stepY = ball.stepY > 0 ? ball.stepY : -ball.stepY;

                setDestination(tableCentreY);
            }

            // With its front
            else if (ball.x+2*ball.radius == computer.x && (ball.y+2*ball.radius >= computer.y && ball.y <= computer.y+computer.height))
            {
                ball.stepX = ball.stepX < 0 ? ball.stepX : -ball.stepX;
                setDestination(tableCentreY);
            }
    }

    /* Method to set the next destination of computer paddle and set its movement accordingly
    */
    private void setDestination(int nextY)
    {
        // Set the next destination of the computer paddle
        this.nextY = nextY;

        // Instruct the computer paddle to move depending upon its next destination
        if (nextY < computer.y + computer.height / 2) computer.stepY = -1;
        if (nextY > computer.y + computer.height / 2) computer.stepY = 1;
    }

    /* Method to obtain the x-coordinate where the ball will collide with the y-axis.
       The coordinate depends upon the direction of the ball besides its position on the player paddle.
    */
    int getNextX(boolean isBallGoingUp, int currentX, int currentY)
    {
        if (isBallGoingUp) return currentX+currentY;
        else return (this.height-2*ball.radius) + currentX - currentY;
    }

    /* Method to obtain the y-coordinate where the ball will collide with the  right edge.
       The coordinate depends upon the direction of the ball besides its position on the player paddle.
    */
    int getRightEdgeY(boolean isBallGoingUp, int currentX, int currentY)
    {
        int nextX = getNextX(isBallGoingUp, currentX, currentY);

        // If the point of collision with the y-axis happens outside the width of the table,
        // then straightaway calculate its collision with the right edge
        if (nextX > this.width-2*ball.radius)
        {
            if (isBallGoingUp) return currentY - (this.width-2*ball.radius - currentX);
            else return currentY + this.width-2*ball.radius - currentX;
        }

        // Till the next collision of the ball is behind critical point, keep calculating its next x-coordinate of
        // collision with the y-axis
        while (nextX < criticalX)
        {
            if (isBallGoingUp) nextX = getNextX(false, nextX, 0);
            else nextX = getNextX(true, nextX, height-2*ball.radius);

            isBallGoingUp = !isBallGoingUp;
        }

        // Calculate the y-coordinate of collision of ball with the right edge
        if (isBallGoingUp) return (width-2*ball.radius) - nextX;
        else  return nextX + (height-2*ball.radius) - (width-2*ball.radius);
    }
}
