package algorithm.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

class WorkingArea {

    public ArrayList<Node> nodes = new ArrayList<Node>();
    private HashMap<Integer, Edge> edges;
    private ArrayList<Route> dis = new ArrayList<Route>();
    private ArrayList<Integer> cost;
    private Random rn;
    public boolean flag = false;

    public WorkingArea() {
        this.queue = new LinkedList<Integer>();
//       this.nodes = new HashMap<String, Node>();
//        this.nodes=new ArrayList<Node>();
        this.edges = new HashMap<Integer, Edge>();
    }

    /**
     * This constructor accepts an ArrayList<Vertex> and populates this.nodes.
     * If multiple Node objects have the same label, then the last Node with the
     * given label is used.
     *
     * @param vertices The initial Vertices to populate this Graph
     */
    public WorkingArea(ArrayList<Node> vertices) {
        this.queue = new LinkedList<Integer>();

        this.nodes = vertices;

    }

    public boolean addEdge(Node one, Node two) {
        return addEdge(one, two, 1);
    }

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

    public boolean containsEdge(Edge e) {
        if (e.getOne() == null || e.getTwo() == null) {
            return false;
        }

        return this.edges.containsKey(e.hashCode());
    }

    public Edge removeEdge(Edge e) {
        e.getOne().removeNeighbor(e);
        e.getTwo().removeNeighbor(e);
        return this.edges.remove(e.hashCode());
    }

    public Set<Edge> getEdges() {
        return new HashSet<Edge>(this.edges.values());
    }

    public void initializeNeighbors() {
        for (int i = 0; i < nodes.size() - 1; i++) {
            for (int j = 0; j < nodes.size(); j++) {

                if (i == j) {
                    continue;
                }
                Node current = nodes.get(i);

                Node current2 = nodes.get(j);
                if (current.inRange(nodes.get(j))) {
                    Edge temp = new Edge(nodes.get(i), nodes.get(j));

                    Edge temp2 = new Edge(nodes.get(j), nodes.get(i));
                    temp.setWeight(rn.nextInt(10) + 1);
                    if (!current.neighborhood.contains(temp)) {

                        current.neighborhood.add(temp);
                        current2.neighborhood.add(temp2);

                    }

                    System.out.println(temp);
                    System.out.println(temp2);
                }

            }
        }
    }

//    public Route bestRoute(Node node, int id) {
//        ArrayList<String> route = new ArrayList<String>();
//        ArrayList<String> final_route = new ArrayList<String>();
//
//        double temp_power = 0;
//        double route_power = 1000000;
//        boolean found_destination = false;
//        route.add(node.getNodeID() + "");
//        node.visited = true;
//        node.countvisits++;
//        while (!route.isEmpty()) {
//            String element = route.get(route.size() - 1);
//            route.remove(route.size() - 1);
//            found_destination = false;
//            ArrayList<Edge> neighbours = node.getNeighbors();
//
//            for (int i = 0; i < neighbours.size(); i++) {
//                temp_power = 0;
//                Node n = neighbours.get(i).getTwo();
//
//                if (n != null && (n.countvisits <= count)) {
//                    temp_power += neighbours.get(i).getPower();
//                    route.add(n.getNodeID() + "");
//                    n.visited = true;
//                    n.countvisits++;
//
//                    if (n.id == id) {
//                        found_destination = true;
//
//                        break;
//                    }
//                }
//            }
//
//            if (temp_power < route_power && found_destination == true) {
//
//                final_route = new ArrayList<>();
//                for (int j = 0; j < route.size(); j++) {
//                    final_route.add(route.get(j));
//                }
//                route_power = temp_power;
//            }
//        }
//
//        Route path = new Route(final_route, route_power);
//        return path;
//
//    }
    private Queue<Integer> queue;

    public void bfs(Node node, int destination_id) {

        String output = "";
        boolean dest_found = false;
        double power = 0;
        int ii = node.getNodeID();
        queue.add(ii);
        node.visited = true;
        if (node.getNodeID()==destination_id)
        {
            power=0;
            output+=destination_id+" ";
        }
      else
        {while (!queue.isEmpty()) {

            int element = queue.remove();
            output += (element + "     ");
            if (element == destination_id) {
                dest_found = true;
                break;

            }
            ArrayList<Edge> neighbours = node.getNeighbors();
            for (int i = 0; i < neighbours.size(); i++) {
                Node n = neighbours.get(i).two;
                if (n != null && !n.visited) {
                    queue.add(n.getNodeID());
                    n.visited = true;

                }
            }

        }
        }
        if (dest_found) {
            System.out.println(" The route is " + output);
        } else {

            System.out.println(" No route could  be found");
        }
    }

    public RouteandPower loopforneighbors(Node node, String destination_id) {
        ArrayList<Edge> neighbours = node.getNeighbors();
        RouteandPower routeandpower = new RouteandPower("", 10000000);
        boolean found = false;
        for (int i = 0; i < neighbours.size(); i++) {
            Node n = neighbours.get(i).two;
            RouteandPower temp = checkmyneighbors(n, destination_id);

            if (temp != null) {
                if (temp.power < routeandpower.power) {
                    routeandpower = temp;
                    found = true;
                }
            }

        }

        if (!found) {
            routeandpower = new RouteandPower("", 10000000);

        }
        return routeandpower;
    }

    public RouteandPower checkmyneighbors(Node node, String destination_id) {
        RouteandPower randp;
        boolean neighbor = false;
        String output = "";
        boolean dest_found = false;
        double power = 0;
        for (int i = 0; i < node.neighborhood.size(); i++) {
            String d_id = node.neighborhood.get(i).two.getNodeID() + "";
            if (d_id.equals(destination_id)) {
                neighbor = true;
                power++;
                output += node.getNodeID() + "";
                dest_found = true;
                break;
            }
        }

        if (dest_found) {
            randp = new RouteandPower(output, power);
            return randp;
        } else {
            return null;
        }

    }

    public void printRoute(Node node, String destination_id) {

        boolean neighbor = false;
        String output = "";
        boolean dest_found = false;
        double power = 0;
        for (int i = 0; i < node.neighborhood.size(); i++) {
            String d_id = node.neighborhood.get(i).two.getNodeID() + "";
            if (d_id.equals(destination_id)) {
                neighbor = true;
                power = 2;
                output += node.getNodeID() + "";
                dest_found = true;
                break;
            }
        }
        if (!neighbor) {

            RouteandPower r = loopforneighbors(node, destination_id);
            output = r.output;
            power = r.power;
            if (power >= 100000) {
                dest_found = false;
            }

        }

//        Stack<String> ids = new Stack<String>();
//        ids.add(node.getNodeID() + " , ");
//        node.visited = true;
//        node.countvisits++;
//     
//        while (!ids.isEmpty()) {
//            String element = ids.pop();
//              output+=(element + "\t");
//              power++;
//            if (element.equals(destination_id))
//            {
//                dest_found=true;
//                break;
//              
//            }
//         
//            ArrayList<Edge> neighbours = node.getNeighbors();
//            for (int i = 0; i < neighbours.size(); i++) {
//                Node n = neighbours.get(i).two;
//                if (n != null&& !n.visited) {
//                    ids.add(n.getNodeID() +"");
//                    n.visited = true;
//                    n.countvisits++;
//                }
//            }
//
//        }
        if (dest_found) {
            System.out.println(output);
            System.out.println("The needed power is " + (power - 1));
        } else {
            System.out.println("No way to reach the needed destination");
        }
    }

    public void getAllRoutes(Node src) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getNodeID() == src.getNodeID()) {
                continue;
            }
            flag = false;
//            Route r = bestRoute(src, nodes.get(i).getNodeID(), i);
//            src.setNode_Neighbour("" + nodes.get(i).getNodeID(), r);
            nodes.get(i).visited = false;
            nodes.get(i).print();

        }

    }

    public void nodeGeneration() {
        rn = new Random();

        AtomicInteger count = new AtomicInteger(0);

        int id = 0;
        double power = 0;
        Location l;
        int n = 0;
        double anntena = 0;

        for (int i = 0; i < 5; i++) {
            n = rn.nextInt(6);
            count.incrementAndGet();

            id = count.get();
            power = rn.nextDouble() + 1;
            anntena = rn.nextDouble();

            l = new Location(n + 50, n + 59);
            String label = "" + i;
            Node node = new Node(label, id, l, power, anntena, false);
            nodes.add(node);
            System.out.println("the node " + label + "has location X: " + l.getX() + "y: " + l.getY() + "id: " + id);

        }

        initializeNeighbors();

        int node_id = 0;
        String dest = ("5");
//
//        printRoute(nodes.get(node_id), dest);

        bfs(nodes.get(node_id),3);

        int old_id = 0;
        Message m[] = new Message[1000];
        for (int i = 0; i < 1000; i++) {
            n = rn.nextInt(5) + 1;

            m[i] = new Message(n + 1, n + 3, i);
        }

    }
}
