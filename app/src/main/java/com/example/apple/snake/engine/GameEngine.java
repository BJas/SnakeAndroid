package com.example.apple.snake.engine;

import android.content.Context;

import com.example.apple.snake.classes.Coordinate;
import com.example.apple.snake.enums.Direction;
import com.example.apple.snake.enums.GameState;
import com.example.apple.snake.enums.TileType;
import com.example.apple.snake.levelActivity.CategoryActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by apple on 06.11.17.
 */

public class GameEngine {
    public static final int GameWidth = 28;
    public static final int GameHeight = 28;

    Context context;
    public GameEngine() {}

    private List<Coordinate> walls = new ArrayList<>();
    private List<Coordinate> snake = new ArrayList<>();
    private List<Coordinate> apples = new ArrayList<>();
    private List<Coordinate> pears = new ArrayList<>();

    private Random random = new Random();

    private  boolean increaseTail = false;

    private Direction currentDirection = Direction.East;

    private GameState currentGameState = GameState.Running;

    private CategoryActivity categoryActivity;

    public int score = 0;

    private int level;
    private Coordinate getSnakeHead() {
        return snake.get(0);
    }

    public void initGame() {

        AddSnake();
        AddApples();

    }

    public void UpdateDirection(Direction newDirection) {
        if(Math.abs(newDirection.ordinal() - currentDirection.ordinal()) % 2 == 1) {
            currentDirection = newDirection;
        }
    }

    public void Update() {
        switch (currentDirection) {  //update snake
            case North:
                updateSnake(0, -1);
                break;
            case East:
                updateSnake(1, 0);
                break;
            case South:
                updateSnake(0, 1);
                break;
            case West:
                updateSnake(-1, 0);
                break;
        }

        // Check wall
        for (Coordinate w: walls){
            if(snake.get(0).equals(w)){
                currentGameState = GameState.Lost;

            }
        }

        // Check self collision
        for(int i = 1; i < snake.size(); i++ ) {

            if(getSnakeHead().equals(snake.get(i))) {
                currentGameState = GameState.Lost;
                return;
            }
        }

        // Check apples
        Coordinate appleToRemove  = null;

        for (Coordinate a: apples) {
            if(getSnakeHead().equals(a)) {
                appleToRemove = a;
                increaseTail = true;
                score = score + 10;
            }
        }

        if(appleToRemove != null) {
            apples.remove(appleToRemove);
            AddApples();
        }

        if(score != 0 && score%60 == 0 && pears.size() == 0) {
            AddPears();
        }


        Coordinate pearToRemove = null;

        for (Coordinate p: pears) {
            if(getSnakeHead().equals(p) || score%60 != 0) {
                pearToRemove = p;
                if(getSnakeHead().equals(p)){
                    increaseTail = true;
                    score = score + 20;
                }
            }
        }


        if(pearToRemove != null) {
            pears.remove(pearToRemove);
        }
    }





    public TileType[][] getMap() { //update coordinate
        TileType[][] map = new TileType[GameWidth][GameHeight];

        for (int x = 0; x < GameWidth; x++) {
            for (int y = 0; y < GameHeight; y++) {
                map[x][y] = TileType.Nothing;
            }
        }

        for(Coordinate wall: walls){
            map[wall.getX()][wall.getY()] = TileType.Wall;
        }

        for (Coordinate s: snake) {
            map[s.getX()][s.getY()] = TileType.SnakeTail;
        }

        for(Coordinate a: apples){
            map[a.getX()][a.getY()] = TileType.Apple;
        }

        for(Coordinate a: pears){
            map[a.getX()][a.getY()] = TileType.Pear;
        }

        map[snake.get(0).getX()][snake.get(0).getY()] = TileType.SnakeHead;

        return map;
    }


    private void updateSnake(int x, int y) {

        int newX = snake.get(snake.size()-1).getX();
        int newY = snake.get(snake.size()-1).getY();

        for (int i = snake.size()-1; i > 0; i--) {
            snake.get(i).setX( snake.get(i-1).getX() );
            snake.get(i).setY( snake.get(i-1).getY() );
        }

        if(increaseTail) {
            snake.add(new Coordinate(newX, newY));
            increaseTail = false;
        }

        if(snake.get(0).getY() == 0 && currentDirection.equals(Direction.North)) {
            snake.get(0).setX(snake.get(0).getX() + x);
            snake.get(0).setY(GameHeight + y);
        }
        else if(snake.get(0).getY() == GameHeight-1 && currentDirection.equals(Direction.South)) {
            snake.get(0).setX(snake.get(0).getX() + x);
            snake.get(0).setY(0);
        }
        else if(snake.get(0).getX() == 0 && currentDirection.equals(Direction.West)) {
            snake.get(0).setX(GameWidth + x);
            snake.get(0).setY(snake.get(0).getY() + y);
        }
        else if(snake.get(0).getX() == GameWidth-1 && currentDirection.equals(Direction.East)) {
            snake.get(0).setX(0);
            snake.get(0).setY(snake.get(0).getY() + y);
        }
        else {
            snake.get(0).setX(snake.get(0).getX() + x);
            snake.get(0).setY(snake.get(0).getY() + y);
        }
    }

    private void AddSnake() {
        snake.clear();

        snake.add(new Coordinate(7, 7));
        snake.add(new Coordinate(6, 7));
        snake.add(new Coordinate(5, 7));
        snake.add(new Coordinate(4, 7));
        snake.add(new Coordinate(3, 7));
        snake.add(new Coordinate(2, 7));
    }

    public void AddWalls(int levelId) {

        switch (levelId){          //Top and bottom walls
            case 0:
                for (int x = 0; x < GameWidth; x++) {
                    Level1(x);
                }
                for (int y = 0; y < GameHeight; y++){
                    LevelByY1(y);
                }
                break;
            case 1:
                for (int x = 0; x < GameWidth; x++) {
                    Level1(x);
                    Level2(x);
                }
                for (int y = 0; y < GameHeight; y++){
                    LevelByY1(y);
                }
                break;
            case 2:
                for (int x = 0; x < GameWidth; x++) {
                    Level1(x);
                    Level3(x);
                }
                for (int y = 0; y < GameHeight; y++){
                    LevelByY1(y);
                }
                break;
            case 3:
                for (int x = 0; x < GameWidth; x++) {
                    Level2(x);
                    Level4(x);
                }
                for (int y = 0; y < GameHeight; y++){
                    LevelByY2(y);
                }
                break;
            case 4:
                for (int x = 0; x < GameWidth; x++) {
                    Level3(x);
                    Level5(x);
                }
                for (int y = 0; y < GameHeight; y++){
                    LevelByY2(y);
                }
                break;
            case 5:
                for (int x = 0; x < GameWidth; x++) {
                    Level6(x);
                }
                for (int y = 0; y < GameHeight; y++){
                    LevelByY2(y);
                    LevelByY3(y);
                }
                break;
            }
        }


    //Level 1 2 3
    private void LevelByY1(Integer y) {
        if(y < Math.round(GameHeight / 2)) {
            walls.add(new Coordinate(0, y));
        }
        if(y >= Math.round(GameHeight / 2)) {
            walls.add(new Coordinate(GameWidth-1, y));
        }
    }

    //Level 4 5
    private void LevelByY2(Integer y) {
        if(y < GameHeight) {
            walls.add(new Coordinate(0, y));
            walls.add(new Coordinate(GameWidth-1, y));
        }
    }

    //Level 6
    private void LevelByY3(Integer y) {
        if(y < Math.round(GameHeight / 4)) {
            walls.add(new Coordinate(Math.round(GameWidth / 3), y));
            walls.add(new Coordinate(2 * Math.round(GameWidth / 3), y));
        }
        if(y >= 3 * Math.round(GameHeight / 4)) {
            walls.add(new Coordinate(Math.round(GameWidth / 3), y));
            walls.add(new Coordinate(2 * Math.round(GameWidth / 3), y));
        }
    }

    //Level #1
    private void Level1(Integer x) {

        if(x < Math.round(GameWidth / 2)) {
            walls.add(new Coordinate(x, 0));
        }
        if(x >= Math.round(GameWidth / 2)) {
            walls.add(new Coordinate(x, GameWidth - 1));
        }
    }

    //Level #2
    private void Level2(Integer x) {

        if (x > 8 && x < GameWidth-9) {
            walls.add(new Coordinate(x, Math.round(GameWidth / 2) - 1));
        }
    }

    //Level #3
    private void Level3(Integer x) {

        if (x > 8 && x < GameWidth-9) {
            walls.add(new Coordinate(x, Math.round(GameWidth / 3) - 3));
            walls.add(new Coordinate(x, 2 * Math.round(GameWidth / 3) + 2));
        }
    }

    private void Level4(Integer x) {


        if (x < GameWidth-Math.round(GameWidth / 2)) {
            walls.add(new Coordinate(x, Math.round(GameWidth / 3) - 3));
        }
        if (Math.round(GameWidth / 2) <= x) {
            walls.add(new Coordinate(x, 2 * Math.round(GameWidth / 3) + 2));
        }
    }

    private void Level5(Integer x) {
        if(x < Math.round(GameWidth / 3)) {
            walls.add(new Coordinate(x, 0));
            walls.add(new Coordinate(x, Math.round(GameWidth / 2)));
            walls.add(new Coordinate(x, GameWidth - 1));
        }
        if(x >= 2* Math.round(GameWidth / 3)) {
            walls.add(new Coordinate(x, 0));
            walls.add(new Coordinate(x, Math.round(GameWidth / 2)));
            walls.add(new Coordinate(x, GameWidth - 1));
        }
    }

    private  void Level6(Integer x) {
        if(x < Math.round(GameWidth / 3) - 1) {
            walls.add(new Coordinate(x, Math.round(GameWidth / 2)));
        }
        if(x >= 2 * Math.round(GameWidth / 3) + 1) {
            walls.add(new Coordinate(x, Math.round(GameWidth / 2)));
        }
    }

    private void AddApples() {
        Coordinate coordinate = null;

        boolean added = false;
        apples.add(checkCollision(added, coordinate));
    }


    private void AddPears() {
        Coordinate coordinate = null;

        boolean added = false;
        checkCollision(added, coordinate);

        pears.add(checkCollision(added, coordinate));

    }

    private Coordinate checkCollision(Boolean added, Coordinate coordinate) {
        while (!added) { //while true
            int x = 1 + random.nextInt(GameWidth - 2);
            int y = 1 + random.nextInt(GameHeight - 2);

            coordinate = new Coordinate(x, y);
            boolean collision = false;
            for(Coordinate s: snake){
                if(s.equals(coordinate)){
                    collision = true;
                    break;
                }
            }

            for(Coordinate w: walls){
                if(w.equals(coordinate)){
                    collision = true;
                    break;
                }
            }

            if(collision) {
                continue;
            }

            added = !collision;
        }
        return coordinate;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

}
