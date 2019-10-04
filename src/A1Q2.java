import java.util.*;

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


    private static int numStatesVisited = 0;

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
        int agentX = 0;
        int agentY = 0;

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

                    BoardElement newElement;

                    if(item == '@')
                    {
                        agentX = x;
                        agentY = y;
                        newElement = new BoardElement(x,y,' ');
                        agent = newElement;
                    }
                    else
                    {
                        newElement = new BoardElement(x,y,item);

                    }


                    if(Character.isLetter(item))
                    {
                        bombElements.add(newElement);
                    }

                    board[x][y] = newElement;
                }
            }

            // Do all my computation here... ...

            System.out.println("\nRUNNING, PLEASE WAIT... ...");

            if(agent == null)
            {
                System.out.println("NO AGENT EXISTS!");
            }
            else
            {
                BoardElement[][] boardGame = runGame(board, sort(bombElements), agentX,agentY);
                print(boardGame);
            }
            // end of all computation
            bombElements.clear();
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
        String line = "\n    ";

        for(int y = 0; y < rows; y++)
        {

            for(int x = 0; x < columns; x++)
            {
                line += board[x][y].getItem();
            }

            System.out.println("    " + line);
            line = "";
        }

    }

    private static int getCost( BoardElement element)
    {
        int retValue = 1;

        if(element.getItem() == '.')
        {
            retValue = 2;
        }
        else if(element.getItem() == ':')
        {
            retValue = 3;
        }
        else if(element.getItem() == '!')
        {
            retValue = 4;
        }else if(element.getItem() == '$')
        {
            retValue = 5;
        }
        else if(element.getItem() == '#')
        {
            retValue = -1;
        }

        return retValue;
    }


    private static BoardElement[][] runGame(BoardElement[][] board, BoardElement[] bombElements, int agentLocationX, int agentLocationY)
    {   //------------------------------------------------------
        // runGame
        //
        // PURPOSE: runs the game and searches and outputs the overall result
        //-----------------------------------------------------
        int columns = board.length;
        int rows = board[0].length;
        PriorityQueue priorityQueue = new PriorityQueue();
        BoardElement[][] retBoard = copy(board);
        List<BoardElement> bombsDiffused = new ArrayList<BoardElement>();
        BoardElement currGoalState = null;
        BoardElement currentState = null;
        List<BoardElement> bombsExploded = new ArrayList<BoardElement>();
        BoardElement[][] currBoard = copy(board);
        int currAgentLocationX = agentLocationX;
        int currAgentLocationY = agentLocationY;
        int startSearchCost = 0;
        BoardElement startState;

        fixPrintOutput(retBoard,bombElements,"");

        // keep looping for all bombs one by one
        for(int currBombIndex = 0; currBombIndex < bombElements.length; currBombIndex++)
        {
            int bombX = bombElements[currBombIndex].getX();
            int bombY = bombElements[currBombIndex].getY();
            currGoalState = currBoard[bombX][bombY];
            currentState = currBoard[currAgentLocationX][currAgentLocationY];
            startState = currBoard[currAgentLocationX][currAgentLocationY];
            currentState.updateCoordinates(null);
            boolean found = false;

            priorityQueue.enqueue(currentState,currGoalState);

            while ( !priorityQueue.isEmpty() && !found )
            {
                currentState = priorityQueue.dequeue();    // enqueue the first one and get the current state.

                currAgentLocationX = currentState.getX();
                currAgentLocationY = currentState.getY();

                if (currentState.isEqual(currGoalState))
                {
                    found = true;
                }
                else    //  we havent yet found the goal state. so add all the neighbouring coordinates
                {
                    enqueueNeighbouringStates(priorityQueue,currAgentLocationX,currAgentLocationY,currentState,currGoalState,currBoard);
                }

            }


            if(found && currentState.getCurrCostSoFar() <= timer(currGoalState))
            {

                currBoard = copy(board);    // new fresh board
                startSearchCost = currentState.getCurrCostSoFar();
                currBoard[currAgentLocationX][currAgentLocationY].updateCost(startSearchCost);
                bombsDiffused.add(currGoalState);
                updateBoardWithValues(retBoard,currentState.getCoordinates(),bombElements);
            }
            else // goal state unreachable
            {
                // modify agents location to initial startpoint, copy for new board item, make sure curr agent has the right cost
                bombsExploded.add(currGoalState);
                currAgentLocationX = startState.getX();
                currAgentLocationY = startState.getY();
                currBoard = copy(board);    // new fresh board
                currBoard[currAgentLocationX][currAgentLocationY].updateCost(startSearchCost);

            }

            priorityQueue.clearQueue();

        }

        retBoard[agentLocationX][agentLocationY].setItem('@');  // ensure it prints out the agents start locaion
        fixPrintOutput(retBoard,bombsExploded.toArray(BoardElement[]::new),"Exploded");
        fixPrintOutput(retBoard,bombsDiffused.toArray(BoardElement[]::new),"Diffused");


        System.out.println("\n    Number of Bombs disarmed: " + bombsDiffused.size());
        System.out.println("    Number of Bombs exploded: " + bombsExploded.size());
        System.out.println("    Cost of Plan            : " + startSearchCost);
        System.out.println("    States visited          : " + numStatesVisited);

        printBombs(bombsDiffused.toArray(BoardElement[]::new),"\n    BOMBS DISARMED:");
        printBombs(bombsExploded.toArray(BoardElement[]::new),"    BOMBS EXPLODED:");


        // make  ALL print statements on the bombs visited, numstates visited etc

        return retBoard;
    }


    private static void fixPrintOutput(BoardElement[][] boardElements, BoardElement[] bombs, String what)
    {

        for(int i = 0; i < bombs.length; i++)
        {
            if(what.equals(""))
            {
                boardElements[bombs[i].getX()][bombs[i].getY()].setItem(' ');

            }else if (what.equals("Exploded"))
            {
                char item = bombs[i].getItem();
                boardElements[bombs[i].getX()][bombs[i].getY()].setItem(item);

            }
            else
            {
                boardElements[bombs[i].getX()][bombs[i].getY()].decrementItem();

            }

        }

    }

    private static void printBombs(BoardElement[] bombs, String message)
    {
        System.out.print(message + " { ");
        for(int i = 0; i < bombs.length; i++ )
        {
            System.out.print(bombs[i].getItem() + "  ");
        }
        System.out.println("}");

    }


    private static void enqueueNeighbouringStates(PriorityQueue priorityQueue, int currAgentLocationX, int currAgentLocationY, BoardElement currentState, BoardElement currGoalState, BoardElement[][] currBoard)
    {
        BoardElement addBoard = null;

        addBoard = currBoard[currAgentLocationX][currAgentLocationY + 1];
        addCoordinatesToQueue(priorityQueue,addBoard,currentState,currGoalState);

        addBoard = currBoard[currAgentLocationX][currAgentLocationY - 1];
        addCoordinatesToQueue(priorityQueue,addBoard,currentState,currGoalState);

        addBoard = currBoard[currAgentLocationX + 1][currAgentLocationY];
        addCoordinatesToQueue(priorityQueue,addBoard,currentState,currGoalState);

        addBoard = currBoard[currAgentLocationX - 1][currAgentLocationY];
        addCoordinatesToQueue(priorityQueue,addBoard,currentState,currGoalState);

        addBoard = currBoard[currAgentLocationX - 1][currAgentLocationY - 1];
        addCoordinatesToQueue(priorityQueue,addBoard,currentState,currGoalState);

        addBoard = currBoard[currAgentLocationX + 1][currAgentLocationY + 1];
        addCoordinatesToQueue(priorityQueue,addBoard,currentState,currGoalState);

        addBoard = currBoard[currAgentLocationX + 1][currAgentLocationY - 1];
        addCoordinatesToQueue(priorityQueue,addBoard,currentState,currGoalState);

        addBoard = currBoard[currAgentLocationX - 1][currAgentLocationY + 1];
        addCoordinatesToQueue(priorityQueue,addBoard,currentState,currGoalState);
    }



    private static void updateBoardWithValues(BoardElement[][] retBoard, List<Coordinates> coordinates, BoardElement[] bombs)
    {
        Coordinates coordinate;
        boolean isBomb = false;

        for(int i = 0; i < coordinates.size(); i++)
        {
            coordinate = coordinates.get(i);
            retBoard[coordinate.getCoordinateX()][coordinate.getCoordinateY()].incrementNumVisits();

        }
    }


    private static int timer(BoardElement goalState)
    {
        int retValue = 0;

        char ch = goalState.getItem();
        retValue = ch - 64;
        retValue *= 10;

        return retValue;
    }




    private static void addCoordinatesToQueue(PriorityQueue priorityQueue, BoardElement addBoard, BoardElement currentState, BoardElement currGoalState){

        int addCost = 0;
        numStatesVisited++;

        if(!addBoard.getVisited())
        {
            addCost = getCost(addBoard);
            addBoard.setAsVisited();
            if(addCost != -1)   // if its not a wall
            {
                addBoard.updateCost( addCost + currentState.getCurrCostSoFar() );
                addBoard.updateCoordinates( currentState.getCoordinates() );
                priorityQueue.enqueue( addBoard, currGoalState );
            }
        }

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
