package exercises;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

import static java.lang.Math.*;
import static java.lang.System.*;

/*
 * Program for Conway's game of life.  See https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
 *
 * This is a graphical program using JavaFX to draw on the screen.
 * There is quite a bit of "drawing" code to make this happen (far below).
 * You don't need to implement (or understand) any of it.
 * NOTE: To run tests must uncomment in init() method, see comment
 *
 * Use small step + functional decomposition!
 *
 * See:
 * - UseEnum
 * - Matrices
 */
public class Ex4GameOfLife extends Application {

    // Enum type for state of Cells
    enum Cell {
        DEAD, ALIVE;
    }

    final Random rand = new Random();

    // This is the *only* accepted modifiable instance variable in program
    Cell[][] world;

    // Implement this method (using functional decomp.)
    // Every involved method should be tested, see below, method test()
    // This method is automatically called by a JavaFX timer (don't need to call)
    void updateWorld() {
        // TODO generate a world with 900 cells
        // TODO update the "world"
        world = changeWorld(world);
        //world[1][1] = Cell.ALIVE;
    }

    // -------- Write methods below this --------------

    Cell[][] changeWorld (Cell[][] w){
        Cell[][] copy = new Cell[w.length][w.length];   // Same size as w

        for (int col = 0; col < copy.length; col++) {
            for (int row = 0; row < copy.length; row++) {
                int nAlive = getLivingNeighbours (w, row, col);
                if (nAlive == 3) {
                    copy[row][col] = Cell.ALIVE;
                }else if (nAlive > 3) {
                    copy[row][col] = Cell.DEAD;
                }else if (nAlive < 2) {
                    copy[row][col] = Cell.DEAD;
                }else {
                    copy[row][col] = w[col][row];   // Unchanged!
                }
            }
        }

        //copy[0][0] = Cell.ALIVE;
        return copy;    // For now...
    }

    int getLivingNeighbours (Cell[][] world, int row, int col) {
        int count = 0;
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (!(row == r && col == c) && isValidLocation(world.length, r, c)) {
                    if (world[r][c] == Cell.ALIVE) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    boolean isValidLocation (int size, int row, int col) {
        return row >= 0 && col >= 0 && row < size && col < size;
    }


    @Override
    public void init() {
        // test();        // <--------------- Uncomment to test!
        int nLocations = 900;
        double distribution = 0.4;   // % of locations holding a living Cell
        world = new Cell[][]{
                {Cell.ALIVE, Cell.ALIVE, Cell.DEAD},
                {Cell.ALIVE, Cell.DEAD, Cell.DEAD},
                {Cell.DEAD, Cell.DEAD, Cell.ALIVE},
        };


        // TODO get distribution, shuffle and convert to matrix (use above)

    }


    // ---------- Testing -----------------
    // Here you run your tests i.e. call your logic methods
    // to see that they really work
    void test() {
        // Hard coded test world
        Cell[][] world = {
                {Cell.ALIVE, Cell.ALIVE, Cell.DEAD},
                {Cell.ALIVE, Cell.DEAD, Cell.DEAD},
                {Cell.DEAD, Cell.DEAD, Cell.ALIVE},

        };

        // TODO test methods here

        exit(0);
    }

    // -------- Below is JavaFX stuff, nothing to do --------------

    // --------- Rendering ----------------------
    // GraphicsContext object supplied by JavaFX
    void renderWorld(GraphicsContext g) {
        g.clearRect(0, 0, width, height);
        int size = world.length;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int x = 10 * col + 50;
                int y = 10 * row + 50;
                renderCell(g, x, y, world[row][col]);
            }
        }
    }

    void renderCell(GraphicsContext g, int x, int y, Cell cell) {
        if (cell == Cell.ALIVE) {
            g.setFill(Color.DARKOLIVEGREEN);
        } else {
            g.setFill(Color.WHITE);
        }
        g.fillOval(x, y, 10, 10);
    }

    final int width = 400;   // Size of window
    final int height = 400;
    long previousTime = nanoTime();   // Get time right now
    final long INTERVAL = 700000000;

    // Must have public before more later.
    @Override
    public void start(Stage primaryStage) throws Exception {

        // JavaFX stuff
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        root.getChildren().addAll(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Create a timer
        AnimationTimer timer = new AnimationTimer() {
            // This method called by FX at a certain rate, parameter is the current time
            public void handle(long currentNanoTime) {
                long elapsedNanos = currentNanoTime - previousTime;
                if (elapsedNanos > INTERVAL) {
                    updateWorld();
                    renderWorld(gc);
                    previousTime = currentNanoTime;
                }
            }
        };
        // Create a scene and connect to the stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game of Life");
        primaryStage.show();
        timer.start();  // Start simulation

    }

    public static void main(String[] args) {
        launch(args);   // Launch JavaFX
    }
}