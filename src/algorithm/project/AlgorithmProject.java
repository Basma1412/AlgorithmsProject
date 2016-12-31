package algorithm.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

class WorkingArea {

    private HashMap<String, Node> nodes;
    private HashMap<Integer, Edge> edges;

    public WorkingArea() {
        this.nodes = new HashMap<String, Node>();
        this.edges = new HashMap<Integer, Edge>();
    }
    
    /*hello I am tasneem */

    /**
     * This constructor accepts an ArrayList<Vertex> and populates this.nodes.
     * If multiple Node objects have the same label, then the last Node with the
     * given label is used.
     *
     * @param vertices The initial Vertices to populate this Graph
     */
    public WorkingArea(ArrayList<Node> vertices) {
        this.nodes = new HashMap<String, Node>();
        this.edges = new HashMap<Integer, Edge>();

        for (Node v : vertices) {
            this.nodes.put(v.getLabel(), v);
        }

    }

    /**
     * This method adds am edge between Vertices one and two of power 1, if no
     * Edge between these Vertices already exists in the WorkingArea.
     *
     * @param one The first vertex to add
     * @param two The second vertex to add
     * @return true iff no Edge relating one and two exists in the WorkingArea
     */
    public boolean addEdge(Node one, Node two) {
        return addEdge(one, two, 1);
    }

    /**
     * Accepts two nodes and a power, and adds the edge ({one, two}, power) iff
     * no Edge relating one and two exists in the WorkingArea.
     *
     * @param one The first Node of the Edge
     * @param two The second Node of the Edge
     * @param weight The power of the Edge
     * @return true iff no Edge already exists in the WorkingArea
     */
    public boolean addEdge(Node one, Node two, int weight) {
        if (one.equals(two)) {
            return false;
        }

        //ensures the Edge is not in the WorkingArea
        Edge e = new Edge(one, two, weight);
        if (edges.containsKey(e.hashCode())) {
            return false;
        } //and that the Edge isn't already incident to one of the nodes
        else if (one.containsNeighbor(e) || two.containsNeighbor(e)) {
            return false;
        }

        edges.put(e.hashCode(), e);
        one.addNeighbor(e);
        two.addNeighbor(e);
        return true;
    }

    /**
     *
     * @param e The Edge to look up
     * @return true iff this WorkingArea contains the Edge e
     */
    public boolean containsEdge(Edge e) {
        if (e.getOne() == null || e.getTwo() == null) {
            return false;
        }

        return this.edges.containsKey(e.hashCode());
    }

    /**
     * This method removes the specified Edge from the WorkingArea, including as
     * each vertex's incidence neighborhood.
     *
     * @param e The Edge to remove from the WorkingArea
     * @return Edge The Edge removed from the WorkingArea
     */
    public Edge removeEdge(Edge e) {
        e.getOne().removeNeighbor(e);
        e.getTwo().removeNeighbor(e);
        return this.edges.remove(e.hashCode());
    }

    /**
     *
     * @param node The Node to look up
     * @return true iff this WorkingArea contains node
     */
    public boolean containsNode(Node node) {
        return this.nodes.get(node.getLabel()) != null;
    }

    /**
     *
     * @param label The specified Node label
     * @return Node The Node with the specified label
     */
    public Node getNode(String label) {
        return nodes.get(label);
    }

    /**
     * This method adds a Node to the graph. If a Node with the same label as
     * the parameter exists in the WorkingArea, the existing Node is overwritten
     * only if overwriteExisting is true. If the existing Node is overwritten,
     * the Edges incident to it are all removed from the WorkingArea.
     *
     * @param node
     * @param overwriteExisting
     * @return true iff node was added to the WorkingArea
     */
    public boolean addNode(Node node, boolean overwriteExisting) {
        Node current = this.nodes.get(node.getLabel());
        if (current != null) {
            if (!overwriteExisting) {
                return false;
            }

            while (current.getNeighborCount() > 0) {
                this.removeEdge(current.getNeighbor(0));
            }
        }

        nodes.put(node.getLabel(), node);
        return true;
    }

    /**
     *
     * @param label The label of the Node to remove
     * @return Node The removed Node object
     */
    public Node removeNode(String label) {
        Node v = nodes.remove(label);

        while (v.getNeighborCount() > 0) {
            this.removeEdge(v.getNeighbor((0)));
        }

        return v;
    }

    /**
     *
     * @return Set<String> The unique labels of the WorkingArea's Node objects
     */
    public Set<String> nodeKeys() {
        return this.nodes.keySet();
    }

    /**
     *
     * @return Set<Edge> The Edges of this graph
     */
    public Set<Edge> getEdges() {
        return new HashSet<Edge>(this.edges.values());
    }

    public void initializeNeighbors() {
        for (int i = 0; i < nodes.size() - 1; i++) {
            for (int j = i + 1; j < nodes.size(); j++) {

                if (nodes.get(i).inRange(nodes.get(j))) {
                    Edge temp = new Edge(nodes.get(i), nodes.get(j));
                    nodes.get(i).addNeighbor(temp);
                }

            }
        }
    }

    public void bestRoute(Node node, Message message) {
        Stack<Node> route = new Stack<Node>();
        double temp_power=0;
        double route_power = 1000000;
        Stack<Node> final_route=new Stack<>();
        route.add(node);
        node.visited = true;
        while (!route.isEmpty()) {
            Node element = route.pop();
               
            ArrayList<Edge> neighbours = node.getNeighbors();

            for (int i = 0; i < neighbours.size(); i++) {
                 temp_power=0;
                Node n = neighbours.get(i).getTwo();
                if (n != null && !n.visited) {
                    temp_power+=neighbours.get(i).getPower();
                    route.add(n);
                    n.visited = true;
                    if (n.id==message.receiver_id)
                    {
                        break;
                    }
                }
            }
            
            if (temp_power<route_power)
            {
                final_route=route;
                route_power=temp_power;
            }
        }

    }
}

class Route
{
    Stack<Node> route;
    double power;
    
    public Route(Stack<Node> route,double power)
    {
        this.route=route;
        this.power=power;
    }
    
    public Stack<Node> getRoute()
    {
        return this.route;
    }
    
    public double getPower()
    {
        return this.power;
    }
    
    public void setPower(double power)
    {
        this.power=power;
    }
    
    
    public void setRoute( Stack<Node> route)
    {
        this.route=route;
    }
}

class Edge {

    Node one, two;
    private double power;

    /**
     *
     * @param one The first vertex in the Edge
     * @param two The second vertex in the Edge
     */
    public Edge(Node one, Node two) {
        this(one, two, 1);
    }

    /**
     *
     * @param one The first vertex in the Edge
     * @param two The second vertex of the Edge
     * @param weight The power of this Edge
     */
    public Edge(Node one, Node two, double power) {
        this.one = one;
        this.two = two;
        this.power = power;

    }

    /**
     *
     * @param current
     * @return The neighbor of current along this Edge
     */
    public Node getNeighbor(Node current) {
        if (!(current.equals(one) || current.equals(two))) {
            return null;
        }

        return (current.equals(one)) ? two : one;
    }

    /**
     *
     * @return Node this.one
     */
    public Node getOne() {
        return this.one;
    }

    /**
     *
     * @return Node this.two
     */
    public Node getTwo() {
        return this.two;
    }

    /**
     *
     * @return int The power of this Edge
     */
    public double getPower() {
        return this.power;
    }

    /**
     *
     * @param weight The new power of this Edge
     */
    public void setWeight(double weight) {
        this.power = weight;
    }

    /**
     * Note that the compareTo() method deviates from the specifications in the
     * Comparable interface. A return value of 0 does not indicate that
     * this.equals(other). The equals() method checks the Node endpoints, while
     * the compareTo() is used to compare Edge weights
     *
     * @param other The Edge to compare against this
     * @return int this.power - other.power
     */
//    public double compareTo(Edge other) {
//        return this.power - other.power;
//    }
    /**
     *
     * @return String A String representation of this Edge
     */
    public String toString() {
        return "({" + one + ", " + two + "}, " + power + ")";
    }

    /**
     *
     * @return int The hash code for this Edge
     */
    public int hashCode() {
        return (one.getLabel() + two.getLabel()).hashCode();
    }

    /**
     *
     * @param other The Object to compare against this
     * @return ture iff other is an Edge with the same Vertices as this
     */
    public boolean equals(Object other) {
        if (!(other instanceof Edge)) {
            return false;
        }

        Edge e = (Edge) other;

        return e.one.equals(this.one) && e.two.equals(this.two);
    }

    public Node getConnectedNode() {
        return this.two;
    }
}

class Node {

    private ArrayList<Edge> neighborhood;
    private String label;
    int id;
    Location location;
    double batteryPower;
    double antennaPower;
    boolean visited;

    /**
     *
     * @param label The unique label associated with this Vertex
     */
    public Node(String label) {
        this.label = label;
        this.neighborhood = new ArrayList<>();
    }

    public Node(String label, int id, Location location, double batteryPower, double antennaPower, boolean visited) {
        this.label = label;
        this.neighborhood = new ArrayList<Edge>();
        this.id = id;
        this.location = location;
        this.batteryPower = batteryPower;
        this.antennaPower = antennaPower;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public boolean inRange(Node mobile) {
        double locX = this.location.getX();
        double locY = this.location.getY();

        double locX2 = mobile.location.getX();
        double locY2 = mobile.location.getY();

        double distance;

        double param1 = locX2 - locX;
        double param2 = locY2 - locY;

        distance = Math.sqrt((Math.pow(param1, 2)) + (Math.pow(param2, 2)));

        return distance <= 20;

    }

    public void broadcast(Message msg) {
        if (this.batteryPower > 0) {
            this.batteryPower--;

        } else {

        }
    }

    public void forward(Message msg) {
    }

    public void send(Message msg) {

    }

    public void receive(Message msg) {
        if (this.id == msg.receiver_id) {
            confirmReceiving();
        } else {
            forward(msg);
        }
    }

    public void confirmReceiving() {

    }

    /**
     * This method adds an Edge to the incidence neighborhood of this graph iff
     * the edge is not already present.
     *
     * @param edge The edge to add
     */
    public void addNeighbor(Edge edge) {
        if (this.neighborhood.contains(edge)) {
            return;
        }

        this.neighborhood.add(edge);
    }

    /**
     *
     * @param other The edge for which to search
     * @return true iff other is contained in this.neighborhood
     */
    public boolean containsNeighbor(Edge other) {
        return this.neighborhood.contains(other);
    }

    /**
     *
     * @param index The index of the Edge to retrieve
     * @return Edge The Edge at the specified index in this.neighborhood
     */
    public Edge getNeighbor(int index) {
        return this.neighborhood.get(index);
    }

    /**
     *
     * @param index The index of the edge to remove from this.neighborhood
     * @return Edge The removed Edge
     */
    Edge removeNeighbor(int index) {
        return this.neighborhood.remove(index);
    }

    /**
     *
     * @param e The Edge to remove from this.neighborhood
     */
    public void removeNeighbor(Edge e) {
        this.neighborhood.remove(e);
    }

    /**
     *
     * @return int The number of neighbors of this Node
     */
    public int getNeighborCount() {
        return this.neighborhood.size();
    }

    /**
     *
     * @return String The label of this Node
     */
    public String getLabel() {
        return this.label;
    }

    /**
     *
     * @return String A String representation of this Node
     */
    public String toString() {
        return "Node " + label;
    }

    /**
     *
     * @return The hash code of this Node's label
     */
    public int hashCode() {
        return this.label.hashCode();
    }

    /**
     *
     * @param other The object to compare
     * @return true iff other instanceof Node and the two Node objects have the
     * same label
     */
    public boolean equals(Object other) {
        if (!(other instanceof Node)) {
            return false;
        }

        Node v = (Node) other;
        return this.label.equals(v.label);
    }

    /**
     *
     * @return ArrayList<Edge> A copy of this.neighborhood. Modifying the
     * returned ArrayList will not affect the neighborhood of this Node
     */
    public ArrayList<Edge> getNeighbors() {
        return new ArrayList<Edge>(this.neighborhood);
    }

}

class Location {

    double x;
    double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}

class Message {

    int sender_id;
    int receiver_id;
    int sequence_num;
    String message;

    public Message(int sender_id, int receiver_id, int sequence_num) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.sequence_num = sequence_num;
    }

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSender_id() {
        return this.sender_id;
    }

    public int getReceiver_id() {
        return this.receiver_id;
    }

    public int getSequence_num() {
        return this.sequence_num;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public void setSequence_num(int seqeunce) {
        this.sequence_num = seqeunce;
    }

}

public class AlgorithmProject {

    public static void main(String[] args) {
    }

}
