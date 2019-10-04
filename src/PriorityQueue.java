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
    {
        int currCost = element.getCurrCostSoFar();
        Node currNode = head;
        Node prev = null;
        boolean found = false;
        Node inserting;


        if(head != null)
        {

            while (currNode != null && !found)
            {

                int currNodeCost = currNode.data.getCurrCostSoFar();
                boolean compareCost = currCost > currNodeCost;

                if (!compareCost) {

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
                    else
                    {

                        double currDistance;
                        double currNodeDistance;

                        while ( currCost == currNodeCost)
                        {
                            currDistance = getDistance(element,goalElement);
                            currNodeDistance = getDistance(currNode.data, goalElement);

                            if(currDistance <= currNodeDistance)
                            {
                                break;
                            }
                            else
                            {
                                prev = currNode;
                                currNode = currNode.next;

                                if(currNode == null) {
                                    break;
                                }
                                else
                                {
                                    currNodeCost = currNode.data.getCurrCostSoFar();
                                }

                            }

                        }

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

                }
                else
                {
                    prev = currNode;
                    currNode = currNode.next;
                }

            }

            if(currNode == null &&  !found)
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
    {
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
    {

        BoardElement retValue = null;

        if(head != null)
        {
            retValue = head.data;
            head = head.next;
        }

        return retValue;
    }



    private class Node
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
