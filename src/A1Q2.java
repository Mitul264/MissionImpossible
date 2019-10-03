import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** * A1Q1.java
 *
 *  COMP 3190
 *  INSTRUCTOR Dylan fries
 *  ASSIGNMENT Assignment 1 Question 2
 *  @author Mitul Patel, 7851781
 *  @version 25/09/2019
 */

public class A1Q2
{
    public static void main(String[] args) {

        mainHelper();
    }

    public static void mainHelper()
    {   //------------------------------------------------------
        // mainHelper
        //
        // PURPOSE:  helps parse input and call the search.
        //-----------------------------------------------------

        Scanner scanner = new Scanner(System.in);
        int rows = 0;
        int columns = 0;
        System.out.println("________________________________________________________________________________________________");

        System.out.println("\nPlease enter ******ONLY ONE****** square grid. (Press Enter alone to Quit):\n");
        String line = scanner.nextLine().strip();
        BoardElement[][] board;
        List<BoardElement> bombElements = new ArrayList<>();
        BoardElement agent = null;


        while (!line.equals(""))
        {
            String tokens[] = line.split(",");
            rows = Integer.parseInt(tokens[0]);
            columns = Integer.parseInt(tokens[1]);
            board = new BoardElement[columns][rows];

            for(int y = 0; y < rows; y++)
            {
                line = scanner.nextLine().strip();

                for(int x = 0; x < columns; x++)
                {
                    char item = line.charAt(x);

                    BoardElement newElement = new BoardElement(x,y,line.charAt(x));

                    if(item == '@')
                    {
                        agent = newElement;

                    }
                    else if(Character.isLetter(item))
                    {
                        bombElements.add(newElement);
                    }

                    board[x][y] = newElement;
                }
            }

            // Do all my computation here... ...

            System.out.println("\n RUNNING, PLEASE WAIT... ...");

            if(agent==null)
            {
                System.out.println("NO AGENT EXISTS!");
            }
            else
            {
                print(runGame(board, bombElements, agent));

            }
            // end of all computation

            System.out.println("\nPlease enter ******ONLY ONE****** (Press Enter alone to Quit):\n");
            line = scanner.nextLine();
        }

        //Close scanner when finished with it:
        scanner.close();
    }




    private static void print(BoardElement[][] board)
    {   //------------------------------------------------------
        // print
        //
        // PURPOSE: prints the board
        //-----------------------------------------------------
        int columns = board.length;
        int rows = board[0].length;
        String line = "\n";

        for(int y = 0; y < rows; y++)
        {

            for(int x = 0; x < columns; x++)
            {
                line += board[x][y].getItem();
            }

            System.out.println(line);
            line = "";
        }

    }


    private static BoardElement[][] runGame(BoardElement[][] board, List<BoardElement> bombElements, BoardElement agent)
    {   //------------------------------------------------------
        // runGame
        //
        // PURPOSE: runs the game and searches and outputs the overall result
        //-----------------------------------------------------
        int columns = board.length;
        int rows = board[0].length;
        BoardElement[][] retBoard = copy(board);
        boolean[][] currBoardVisits = new boolean[columns][rows];     // keeps track if a board item was visited or not.

        //*********CODE HERE************************



        return retBoard;
    }

    private static BoardElement[] sort(List<BoardElement> bombs)
    {
        int numBombs = bombs.size();
        BoardElement[] tempBombs = new BoardElement[numBombs];
        BoardElement smallValue;
        int j;

        for(int i = 0;i < numBombs; i++){   // goes through the array to sort elements from the srart to end.

            smallValue = bombs.get(i); // stores the value to be moved to a sorted position temporaryly

            j = i-1;  // the previous value that has to be compared

            while(j>=0 && tempBombs[j].getItem() > smallValue.getItem()){
                tempBombs[j+1]=tempBombs[j];      // move the item to the next block
                j--;                    // pointer now moves to the previous item
            }

            tempBombs[j+1]=smallValue;
        }

        return tempBombs;
    }


    public static BoardElement[][] copy(BoardElement[][] board)
    {   //------------------------------------------------------
        // copy
        //
        // PURPOSE: gives a deep copy of a board
        //-----------------------------------------------------
        int columns = board.length;
        int rows = board[0].length;
        BoardElement[][] retBoard = new BoardElement[columns][rows];

        for(int y = 0; y < rows; y++)
        {

            for(int x = 0; x < columns; x++)
            {
                retBoard[x][y] = board[x][y].copy();
            }
        }
        return retBoard;
    }





}
