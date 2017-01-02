/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.project;

import java.util.ArrayList;
import java.util.Stack;

class Route {

    ArrayList<String> route;
    double power;

    public Route(ArrayList<String> route, double power) {
        this.route = route;
        this.power = power;
    }

    public ArrayList<String> getRoute() {
        return this.route;
    }

    public double getPower() {
        return this.power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public void setRoute(ArrayList<String> route) {
        this.route = route;
    }

    public void addToRoute(String node) {
        this.power++;
        this.route.add(node);
    }

    public String toString() {
        String s = "Nodes: ";
        for (int i = 0; i < route.size(); i++) {
            s += route.get(i);
            s += ",";

        }
        return s;
    }
}
