/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.project;

/**
 *
 * @author taseneem 21
 */
class Neighbor {

    Route route;
    Node node;

    public Neighbor(Node node, Route route) {
        this.node = node;
        this.route = route;
    }

    public Route getRoute() {
        return this.route;
    }

    public Node getNode() {
        return this.node;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void getNode(Node node) {
        this.node = node;
    }
}
