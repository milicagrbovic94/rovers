package com.test.rovers.sevices;

import com.test.rovers.exception.MarsException;
import com.test.rovers.model.Plateau;
import com.test.rovers.model.Point;
import com.test.rovers.model.Position;
import com.test.rovers.model.Rover;
import com.test.rovers.model.constant.MarsConstants;
import com.test.rovers.model.side.*;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MarsService {

    public Plateau plateau;

    public void setDimensions(String dimensions) {
        try {
            String[] dimensionArray = dimensions.trim().split(" ");
            if (Integer.parseInt(dimensionArray[0]) > 0 && Integer.parseInt(dimensionArray[0]) < 1000 &&
                    Integer.parseInt(dimensionArray[1]) > 0 && Integer.parseInt(dimensionArray[1]) < 1000) {
                plateau = new Plateau(Integer.parseInt(dimensionArray[0]), Integer.parseInt(dimensionArray[1]), new ArrayList<>());
            } else {
                throw new RuntimeException("Dimensions of plateau have to be between 0 and 1001");
            }
        } catch (Exception e) {
            throw new MarsException("Please input a valid data");
        }
    }

    public int deployRover(String position) {
        try {
            String[] positionArray = position.trim().split(" ");
            if (isPositionAvailable(Integer.parseInt(positionArray[0]), Integer.parseInt(positionArray[1]))) {
                plateau.getRovers().add(new Rover(new Position(new Point(Integer.parseInt(positionArray[0]), Integer.parseInt(positionArray[1])), getSide(positionArray[2]))));
                return Rover.index - 1;
            } else {
                throw new RuntimeException("The position is not available");
            }
        } catch (Exception e) {
            throw new MarsException("Please input a valid data");
        }
    }

    public List<Rover> getRovers() {
        return this.plateau.getRovers();
    }

    public void deleteRoverById(int id) {
        Rover rover = getRoverById(id);
        plateau.getRovers().remove(rover);
    }

    public Rover getRoverById(int id) {
        Rover rover = plateau.getRovers().stream().filter(currentRover -> id == currentRover.getId()).findFirst().orElse(null);
        if (rover != null) {
            return rover;
        } else {
            throw new MarsException("Rover doesn't exist");
        }
    }

    public Rover executeCommands(String commandsString) {
        String[] commandsArray = commandsString.trim().split(" ");
        Rover rover = getRoverById(Integer.parseInt(commandsArray[0]));
        Position oldPosition = (Position) rover.getPosition().clone();
        String[] commands = commandsArray[1].split("");
        boolean isValid = true;
        for (String command : commands) {
            if (isValid) {
                switch (command) {
                    case MarsConstants.LEFT:
                        rover.goLeft();
                        break;
                    case MarsConstants.RIGHT:
                        rover.goRight();
                        break;
                    case MarsConstants.MOVE:
                        int newX = rover.getPosition().calculateNewX();
                        int newY = rover.getPosition().calculateNewY();
                        if (isXValid(newX) && isYValid(newY) && isPositionAvailable(newX, newY)) {
                            rover.updateX(newX);
                            rover.updateY(newY);
                        } else {
                            isValid = false;
                            rover.setPosition(oldPosition);
                            throw new MarsException("Moves cannot be performed, current position of the rover is " + oldPosition.getPoint().getX() + " " + oldPosition.getPoint().getY() + " " + oldPosition.getDirection().getCode());
                        }
                        break;
                    default:
                        throw new MarsException("Command does not exist");
                }
            }
        }
        return rover;
    }

    private boolean isXValid(int x) {
        return x >= 0 && x < this.plateau.getX();
    }

    private boolean isYValid(int y) {
        return y >= 0 && y < this.plateau.getY();
    }

    private boolean isPositionAvailable(int x, int y){
        return !plateau.getRovers().stream().anyMatch(currentRover -> currentRover.getPosition().getPoint().getX() == x && currentRover.getPosition().getPoint().getY() == y);
    }

    public Direction getSide(String sideCode) {
        switch (sideCode) {
            case MarsConstants.EAST:
                return East.getInstance();
            case MarsConstants.WEST:
                return West.getInstance();
            case MarsConstants.NORTH:
                return North.getInstance();
            case MarsConstants.SOUTH:
                return South.getInstance();
            default:
                throw new MarsException("Direction does not exist");
        }
    }

    private void addPath(List<List<Pair<String, Point>>> paths, LinkedList<Pair<String, Position>> linkedList, int newX, int newY, Position previousPosition, String previousSteps) {
        if (newX >= 0 && newX < plateau.getX() && newY >= 0 && newY < plateau.getY() && paths.get(newX).get(newY) == null) {
            Position newPosition = new Position(new Point(newX, newY), previousPosition.getDirection());
            String path = previousSteps + MarsConstants.MOVE;
            linkedList.add(new Pair(path, newPosition));
            paths.get(newX).set(newY, new Pair(path, new Point(previousPosition.getPoint().getX(), previousPosition.getPoint().getY())));
        }
    }

    public String calculateShortestPath(int roverId, int x, int y) {
        try {
            Rover rover = getRoverById(roverId);

            List<List<Pair<String, Point>>> paths = new ArrayList<List<Pair<String, Point>>>();
            for (int i = 0; i < plateau.getX(); i++) {
                ArrayList<Pair<String, Point>> list = new ArrayList<Pair<String, Point>>();
                for (int j = 0; j < plateau.getY(); j++) {
                    list.add(null);
                }
                paths.add(list);
            }

            plateau.getRovers().forEach(existingRover -> {
                paths.get(existingRover.getPosition().getPoint().getX()).set(existingRover.getPosition().getPoint().getY(), new Pair("", new Point(existingRover.getPosition().getPoint().getX(), existingRover.getPosition().getPoint().getY())));
            });

            LinkedList<Pair<String, Position>> linkedList = new LinkedList<Pair<String, Position>>();
            linkedList.add(new Pair("", rover.getPosition()));

            while (!linkedList.isEmpty() && paths.get(x).get(y) == null) {
                Pair<String, Position> entry = linkedList.poll();

                // Go forward
                int newX = entry.getValue().calculateNewX();
                int newY = entry.getValue().calculateNewY();
                addPath(paths, linkedList, newX, newY, entry.getValue(), "");

                // Go left
                Position newPosition = (Position) entry.getValue().clone();
                newPosition.goLeft();
                addPath(paths, linkedList, newPosition.calculateNewX(), newPosition.calculateNewY(), newPosition, MarsConstants.LEFT);

                // Go right
                newPosition = (Position) entry.getValue().clone();
                newPosition.goRight();
                addPath(paths, linkedList, newPosition.calculateNewX(), newPosition.calculateNewY(), newPosition, MarsConstants.RIGHT);
            }

            int i = x;
            int j = y;
            Pair<String, Point> destination = paths.get(i).get(j);
            if (destination == null) {
                throw new MarsException("Given source and destination are not connected");
            } else {
                StringBuilder sb = new StringBuilder();
                while (i != rover.getPosition().getPoint().getX() || j != rover.getPosition().getPoint().getY()) {
                    sb.insert(0, destination.getKey());
                    i = destination.getValue().getX();
                    j = destination.getValue().getY();
                    destination = paths.get(i).get(j);
                }

                return sb.toString();
            }
        } catch (Exception e) {
            throw new MarsException("Please input a valid data: rover's_id x_position y_position (e.g. 1 1 4)");

        }
    }


}
