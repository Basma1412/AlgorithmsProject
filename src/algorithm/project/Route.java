/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.project;

import java.util.Stack;

/**
 *
 * @author taseneem 21
 */
class Route {

    Stack<Node> route;
    double power;

    public Route(Stack<Node> route, double power) {
        this.route = route;
        this.power = power;
    }

    public Stack<Node> getRoute() {
        return this.route;
    }

    public double getPower() {
        return this.power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public void setRoute(Stack<Node> route) {
        this.route = route;
    }
}
