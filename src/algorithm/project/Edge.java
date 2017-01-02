/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.project;

class Edge {

    Node one, two;
    private double power;
 private int weight;
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
    public void setWeight(int weight) {
        this.weight = weight;
    }
    
     public int getWeight() {
       return  this.weight ;
    }

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

