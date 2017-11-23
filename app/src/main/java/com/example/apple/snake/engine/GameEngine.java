package com.example.apple.snake.engine;

import android.content.Context;
import android.content.Intent;
import android.graphics.CornerPathEffect;
import android.graphics.Path;
import android.os.CountDownTimer;
import android.support.annotation.RestrictTo;
import android.util.Log;
import android.widget.TextView;

import com.example.apple.snake.MainActivity;
import com.example.apple.snake.R;
import com.example.apple.snake.Score;
import com.example.apple.snake.classes.Coordinate;
import com.example.apple.snake.enums.Direction;
import com.example.apple.snake.enums.GameState;
import com.example.apple.snake.enums.TileType;

import java.util.ArrayList;
import java.util.Dictionary;
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

    public int score = 0;


    private Coordinate getSnakeHead() {
        return snake.get(0);
    }

    public void initGame() {

        AddSnake();
        AddWalls();
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

        snake.get(0).setX(snake.get(0).getX() + x);
        snake.get(0).setY(snake.get(0).getY() + y);
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

    private void AddWalls() {

        for (int x = 0; x < GameWidth; x++) { //Top and bottom walls
            walls.add(new Coordinate(x, 0));
            walls.add(new Coordinate(x, GameWidth-1));
        }

        for (int y = 1; y < GameHeight; y++) { //Left and right walls
            walls.add(new Coordinate(0, y));
            walls.add(new Coordinate(GameWidth-1, y));
        }
    }

    private void AddApples() {
        Coordinate coordinate = null;

        boolean added = false;

        while (!added) {
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
            if(collision) {
                continue;
            }

            for (Coordinate a: apples) {
                if(apples.equals(coordinate)) {
                    collision = true;
                    break;
                }
            }

            added = !collision;
        }
        apples.add(coordinate);
    }

    private void AddPears() {
        Coordinate coordinate = null;

        boolean added = false;

        while (!added) {
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
            if(collision) {
                continue;
            }

            for (Coordinate a: pears) {
                if(pears.equals(coordinate)) {
                    collision = true;
                    break;
                }
            }

            added = !collision;
        }
        pears.add(coordinate);

    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

}