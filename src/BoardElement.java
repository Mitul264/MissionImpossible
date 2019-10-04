import java.util.ArrayList;
import java.util.List;

/** * BoardElement.java
 *
 *  COMP 3190
 *  INSTRUCTOR Dylan fries
 *  ASSIGNMENT Assignment 1 Question 2
 *  @author Mitul Patel, 7851781
 *  @version 25/09/2019
 */

class BoardElement      // Object that stores board elements
{

    private int x;  // x coordinate of this element
    private int y;  // y coordinate of this element
    private char item;  // if its an @ or a wall # etc
    private List<Coordinates> coordinates;
    private boolean visited;
    private boolean isQueued;
    private int currCostSoFar;


    public BoardElement(int x, int y, char item) {
        this.x = x;
        this.y = y;
        this.item = item;
        coordinates = null;
        visited = false;
        currCostSoFar = 0;
    }

    public void updateCoordinates(List<Coordinates> prevList)
    {
        if(prevList != null)
        {
            coordinates = new ArrayList<Coordinates>(prevList);
            coordinates.add(new Coordinates(x, y));
        }
        else
        {
            coordinates = new ArrayList<Coordinates>();
            coordinates.add(new Coordinates(x, y));
        }
    }

    public void updateCost(int x)
    {
        currCostSoFar += x;
    }

    public void setAsVisited()
    {
        visited = true;
    }

    public boolean getVisited()
    {
        return visited;
    }

    public void setItem(char item) {
        this.item = item;
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    public char getItem() {
        return item;
    }

    public BoardElement copy()
    {
        return new BoardElement(x,y,item);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCurrCostSoFar() {
        return currCostSoFar;
    }

    public void incrementNumVisits()
    {
        if(item == ' ' || item == '.'|| item == ':'|| item == '!'|| item == '$' || item == '@'|| item == '#')
        {
            item = '1';
        }
        else if (item == 57)
        {
            item = 'A';
        }
        else
        {
            item++;
        }
    }

    public void decrementItem()
    {
        if(item == ' ' || item == '.'|| item == ':'|| item == '!'|| item == '$' || item == '@'|| item == '#')
        {
            item = '1';
        }
        else if (item == 65)
        {
            item = '9';
        }
        else
        {
            item--;
        }
    }

    public boolean isEqual(BoardElement other)
    {
        boolean retValue = false;

        if(x == other.getX() && y == other.getY())
        {
            retValue = true;
        }

        return retValue;
    }
}


class Coordinates
{
    private int coordinateX;
    private int coordinateY;

    Coordinates(int x, int y)
    {
        coordinateX = x;
        coordinateY = y;
    }


    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }
}
