public class Debug
{
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";

    public static void showPrediction(int nextX, int nextY)
    {
        System.out.print(PURPLE);
        System.out.printf("Collision prediction: (%03d, %03d)", nextX, nextY);
        System.out.println(RESET);
    }

    public static void showCollision(int nextX, int nextY)
    {
        System.out.print(GREEN);
        System.out.printf("Collision detected: (%03d, %03d)", nextX, nextY);
        System.out.println(RESET);
    }
}
