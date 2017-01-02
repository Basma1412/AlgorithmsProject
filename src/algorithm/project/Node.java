/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.project;

import java.util.ArrayList;


class Node {

    private ArrayList<Edge> neighborhood;
    private ArrayList<Neighbor> neighbors;
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
        this.neighbors = new ArrayList<>();
    }

    public Node(String label, int id, Location location, double batteryPower, double antennaPower, boolean visited) {
        this.label = label;
        this.neighborhood = new ArrayList<Edge>();
        this.id = id;
        this.location = location;
        this.batteryPower = batteryPower;
        this.antennaPower = antennaPower;
    }

    public int getNodeID() {

        return id;
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

        return distance <= 5;

    }

    public Route findDestination_Neighbors(ArrayList<Neighbor> neighbors, int destination_id) {
        /*     int s=0;
         int e=neighbors.size()-1;
         while(s<=e){
         int m=neighbors.size()/2;
         if(neighbors.get(m).getNode().getNodeID()==destination_id){
         return neighbors.get(id).getRoute();
        
         }
         else if(destination_id  < neighbors.get(m).getNode().getNodeID()){
         e=m-1;
        
         } 
         else {
         s=m+1;
         }
         }*/

        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors.get(i).getNode().getNodeID() == destination_id) {
                return neighbors.get(i).getRoute();
            }

        }

        return null;
    }

    public Route findDestination(int destination_id) {

        Route route = null;

        if (this.neighbors.size() == 0) {
            route = null;
        } else {
            route = findDestination_Neighbors(this.neighbors, destination_id);
            if (route == null) {

                for (int i = 0; i < neighbors.size(); i++) {
                    route = findDestination_Neighbors(neighbors.get(i).getNode().get_Neighbors(), destination_id);

                }

            }

        }
        return route;
    }

    public void broadcast(Message msg) {
        if (this.batteryPower > 0) {
            this.batteryPower--;

        } else {

        }
    }


    public void send(Message msg) {

        Route messagepath=findDestination_Neighbors(this.getAllNeighbors(),msg.receiver_id);
        if (messagepath!=null)
        {
            Node receiver = messagepath.route.pop();
            receiver.receive(msg, messagepath);
        }
        else 
        {
            System.out.println(" Message was lost");
        }
        
    }

    public void receive(Message msg,Route route) {
        
        int myid=this.id;
        double mypower=route.power;
        ArrayList<Integer> ids=new ArrayList<>();
        for (int i=0 ; i<route.route.size();i++)
        {
            ids.add(route.route.get(i).id);
        }
        
            acknowledge(myid,mypower,ids);
        
    }

    public void acknowledge(int myid,double mypower,ArrayList<Integer> ids) {

        System.out.println("Received by"+myid+" , total power is : "+mypower+" and the path is ");
        
        for (int i =0 ; i<ids.size();i++)
        {
            System.out.print(ids.get(i)+" ,");   
        }
           System.out.print("End"); 
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

    public void addNeighborNode(Neighbor neighbor) {
        try{
        if (this.neighbors.contains(neighbor)) {
            return;
        }
        }
        catch (Exception e)
        {
          System.out.println(e.toString());
        }
        this.neighbors.add(neighbor);
    }

    /**
     *
     * @param other The edge for which to search
     * @return true iff other is contained in this.neighborhood
     */
    public boolean containsNeighbor(Edge other) {
        return this.neighborhood.contains(other);
    }

    public boolean containsNeighborNode(Node other) {
        return this.neighbors.contains(other);
    }

    /**
     *
     * @param index The index of the Edge to retrieve
     * @return Edge The Edge at the specified index in this.neighborhood
     */
    public Edge getNeighbor(int index) {
        return this.neighborhood.get(index);
    }

    public Neighbor getNeighborNode(int index) {
        return this.neighbors.get(index);
    }

    /**
     *
     * @param index The index of the edge to remove from this.neighborhood
     * @return Edge The removed Edge
     */
    Edge removeNeighbor(int index) {
        return this.neighborhood.remove(index);
    }

    Neighbor removeNeighborNode(int index) {
        return this.neighbors.remove(index);
    }

    /**
     *
     * @param e The Edge to remove from this.neighborhood
     */
    public void removeNeighbor(Edge e) {
        this.neighborhood.remove(e);
    }

    public void removeNeighborNode(Neighbor e) {
        this.neighbors.remove(e);
    }

    /**
     *
     * @return int The number of neighbors of this Node
     */
    public int getNeighborCount() {
        return this.neighborhood.size();
    }

    public int getNeighborNodesCount() {
        return this.neighbors.size();
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

    public ArrayList<Neighbor> get_Neighbors() {
        return this.neighbors;
    }

    public ArrayList<Neighbor> getAllNeighbors() {
        return new ArrayList<Neighbor>(this.neighbors);
    }

}



