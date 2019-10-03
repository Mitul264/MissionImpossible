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

public class BoardElement      // Object that stores board elements
{

    private int x;  // x coordinate of this element
    private int y;  // y coordinate of this element
    private char item;  // if its an @ or a wall # etc

    public BoardElement(int x, int y, char item) {
        this.x = x;
        this.y = y;
        this.item = item;
    }

    public char getItem() {
        return item;
    }

    public BoardElement copy()
    {
        return new BoardElement(x,y,item);
    }
}
