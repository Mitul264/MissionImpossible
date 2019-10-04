/** * BoardElement.java
 *
 *  COMP 3190
 *  INSTRUCTOR Dylan fries
 *  ASSIGNMENT Assignment 1 Question 2
 *  @author Mitul Patel, 7851781
 *  @version 25/09/2019
 */

public class PriorityQueue
{

    private Node head;

    public PriorityQueue()
    {
        head = null;
    }

    public void enqueue(BoardElement element, BoardElement goalElement)
    {   //------------------------------------------------------
        // enqueue
        //
        // PURPOSE: enqueues based on priority. First priority is cost, second priority is distance
        //-----------------------------------------------------

        int currCost = element.getCurrCostSoFar();  // current cost so far
        Node currNode = head;
        Node prev = null;
        boolean found = false;
        Node inserting;


        if(head != null)    // if queue is not empty
        {

            while (currNode != null && !found)  // while we dont find a suitable position to insert
            {

                int currNodeCost = currNode.data.getCurrCostSoFar();
                boolean compareCost = currCost > currNodeCost;

                if (!compareCost) { // if we have found a position to insert

                    if(currCost != currNodeCost)
                    {
                        if (prev != null)
                        {
                            inserting = new Node(element, currNode);
                            prev.next = inserting;
                        } else {
                            inserting = new Node(element, currNode);
                            head = inserting;
                        }

                        found = true;

                    }
                    else    // position insert is not found
                    {

                        double currDistance;
                        double currNodeDistance;

                        while ( currCost == currNodeCost)   // cost is the same so insert based on coordinates
                        {
                            currDistance = getDistance(element,goalElement);
                            currNodeDistance = getDistance(currNode.data, goalElement);

                            if(currDistance <= currNodeDistance)    // cost is not the same anymore
                            {
                                break;
                            }
                            else    // cost is still the same
                            {
                                prev = currNode;
                                currNode = currNode.next;

                                if(currNode == null) {  // we have reached the end
                                    break;
                                }
                                else    // end not reached
                                {
                                    currNodeCost = currNode.data.getCurrCostSoFar();
                                }

                            }

                        }

                        if (prev != null)   // if we are not inserting to an empty queue
                        {
                            inserting = new Node(element, currNode);
                            prev.next = inserting;

                        } else {    // inserting in an empty queue
                            inserting = new Node(element, currNode);
                            head = inserting;
                        }

                        found = true;
                    }

                }
                else    // else keep looping
                {
                    prev = currNode;
                    currNode = currNode.next;
                }

            }

            if(currNode == null &&  !found)     // we reached the end. The item inserting has greatest cost
            {
                prev.next = new Node(element, null);
            }

        }
        else
        {
            head = new Node(element,null);
        }

    }

    public boolean isEmpty(){ return head == null;}
    public void clearQueue()
    {
        head = null;
    }


    private double getDistance(BoardElement first, BoardElement second)
    {   //------------------------------------------------------
        // getDistance
        //
        // PURPOSE: gives eucledian distance between two coordinates
        //-----------------------------------------------------
        double retValue = Integer.MAX_VALUE;

        double x1 = first.getX();
        double x2 = second.getX();
        double y1 = first.getY();
        double y2 = second.getY();

        double xSQ = (x2-x1)*(x2-x1);
        double ySQ = (y2-y1)*(y2-y1);

        retValue = Math.sqrt((xSQ+ySQ));

        return retValue;
    }


    public BoardElement dequeue()
    {   //------------------------------------------------------
        // dequeue
        //
        // PURPOSE: dequeues from start
        //-----------------------------------------------------

        BoardElement retValue = null;

        if(head != null)
        {
            retValue = head.data;
            head = head.next;
        }

        return retValue;
    }



    private class Node  // private node class
    {
        private BoardElement data;
        private Node next;

        Node(BoardElement data, Node next)
        {
            this.data = data;
            this.next = next;

        }


    }

}
