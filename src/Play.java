import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

// Main class which sets all the objects into motion

public class Play extends Application
{
    Table table;
    final int tableWidth = 800;
    final int tableHeight = 500;
    final int paddleHeight = 100;
    final int paddleWidth = 10;

    Text userScore;
    Text computerScore;

    /* Set up the table and add the player, ball and computer to it */
    @Override
    public void init() throws Exception
    {
        super.init();

        table = new Table(tableWidth, tableHeight);
        Ball ball = new Ball(table.width/2, table.height/2);

        // Create a player racket
        // player racket is controlled by handling key events on the primary scene
        Paddle player = new Paddle(5, table.height/2- paddleHeight /2, paddleWidth, paddleHeight, 5);

        // Create a computer racket
        Paddle computer = new Paddle(table.width-5- paddleWidth, table.height/2- paddleHeight /2, paddleWidth, paddleHeight, 0);

        // Set the objects
        table.setComputer(computer);
        table.setPlayer(player);
        table.setBall(ball);

        // Configure userScore
        userScore = new Text();
        userScore.setX(tableWidth*0.22);
        userScore.setY(50);
        userScore.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        userScore.setStrokeWidth(2);
        userScore.setFill(Color.web("#C239B3"));

        // Configure computerScore
        computerScore = new Text();
        computerScore.setX(tableWidth*0.72);
        computerScore.setY(50);
        computerScore.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        computerScore.setStrokeWidth(2);
        computerScore.setFill(Color.web("#C239B3"));

    }

    @Override
    public void start(Stage primaryStage)
    {
        Pane rootPane = new Pane();

        //Disable maximize button of primary stage
        primaryStage.setResizable(false);

        //Give the stage an icon
        Image image = new Image("Resources/Icons/icons8-game-controller-16.png");
        primaryStage.getIcons().add(image);

        // Create a divider line to add to the middle of the primary scene
        Line divider = new Line(table.width/2.0, 0, table.width/2.0, table.height);
        divider.setStroke(Color.web("#C239B3"));
        divider.setStrokeWidth(2.5);
        divider.getStrokeDashArray().addAll(5d);

        // Add all the children to the root pane
        rootPane.getChildren().addAll(table.ball.circle, divider, table.player.rectangle, table.computer.rectangle, userScore, computerScore);

        // Create a timeline to move the ball, handle the bounds and collision
        Timeline timelineTable = new Timeline(new KeyFrame(Duration.millis(4), actionEvent ->
        {
            updateScore();
            table.ball.move();
            table.handleBounds();
            table.handleCollision();
        }));
        timelineTable.setCycleCount(Animation.INDEFINITE);

        // Create a timeline to handle the bounds and move the computer
        Timeline timelineComputer = new Timeline(new KeyFrame(Duration.millis(5), actionEvent ->
        {
            table.handleBounds();
            table.computer.move();
        }));
        timelineComputer.setCycleCount(Animation.INDEFINITE);

        // Configure the stage and scene
        primaryStage.setTitle("Ping Pong");

        Scene primaryScene = new Scene(rootPane, table.width, table.height);
        primaryScene.setFill(Color.web("0x2B2B2B"));

        // Handle the user events to move the player racket
        primaryScene.setOnKeyPressed(keyEvent ->
        {
            if (keyEvent.getCode() == KeyCode.DOWN)
            {
                if (table.player.stepY < 0) table.player.reverseDirection();
                table.player.move();
            }
            else if (keyEvent.getCode() == KeyCode.UP)
            {
                if (table.player.stepY > 0) table.player.reverseDirection();
                table.player.move();
            }
        });

        // Set the scene and show it
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        // Start the game loop
        timelineTable.play();
        timelineComputer.play();
    }

    /* Method to update the current score*/
    private void updateScore()
    {
        userScore.setText(String.valueOf(table.userScore));
        computerScore.setText(String.valueOf(table.computerScore));
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
