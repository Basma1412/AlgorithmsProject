/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author taseneem 21
 */
class WorkingArea {

    public final Map<String,Node> nodes=new HashMap<String, Node>();
    private HashMap<Integer, Edge> edges;
    private ArrayList<Route> dis=new ArrayList<Route>();
    private ArrayList<Integer> cost;
//    private ArrayList<double> dist;
private Random rn;
public boolean flag=false;    
public WorkingArea() {
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
       // this.nodes = new HashMap<String, Node>();
        this.edges = new HashMap<Integer, Edge>();

        for (Node v : vertices) {
         //   this.nodes.put(v.getLabel(), v);
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
    //public boolean containsNode(Node node) {
       // return this.nodes.get(node.getLabel()) != null;
    //}

    /**
     *
     * @param label The specified Node label
     * @return Node The Node with the specified label
     */
  //  public Node getNode(String label) {
       // return nodes.get(label);
//    }

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
    
    /*
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
 /*   public Node removeNode(String label) {
        Node v = nodes.remove(label);

        while (v.getNeighborCount() > 0) {
            this.removeEdge(v.getNeighbor((0)));
        }

        return v;
    }*/

    /**
     *
     * @return Set<String> The unique labels of the WorkingArea's Node objects
     */
/*    public Set<String> nodeKeys() {
        return this.nodes.keySet();
    }*/

    /**
     *
     * @return Set<Edge> The Edges of this graph
     */
    public Set<Edge> getEdges() {
        return new HashSet<Edge>(this.edges.values());
    }

    public void initializeNeighbors() {
    //    nodes=new HashMap<String,Node>();
        for (int i = 0; i < nodes.size() - 1; i++) {
            for (int j = i + 1; j < nodes.size(); j++) {

                if (nodes.get(""+i).inRange(nodes.get(""+j))) {
                    Edge temp = new Edge(nodes.get(""+i), nodes.get(""+j));
                    temp.setWeight(rn.nextInt(10)+1);
                    nodes.get(""+i).addNeighbor(temp); 
                    System.out.println(temp);
//                    System.out.println("the neighbour of node "+i+"is :"+nodes.get(""+i).getNeighbor(i));
                            
            //       Route bestRoute = bestRoute(nodes.get(""+i), nodes.get(""+j).id);
                    //Neighbor n = new Neighbor(nodes.get(""+i),bestRoute);
//                    nodes.get(""+i).addNeighborNode(n); 
//                    nodes.get(""+i).addNeighborNode(new Neighbor(nodes.get(""+j), bestRoute));
                }

            }
        }
    }

    
    public Route bestRoute(Node node, int id) {
        Stack<Node> route = new Stack<Node>();
         Stack<Node> final_route = new Stack<>();

        double temp_power = 0;
        double route_power = 1000000;
        boolean found_destination=false;
        route.add(node);
        node.visited = true;
        flag=true;
        while (!route.isEmpty()) {
            Node element = route.pop();
        found_destination=false;
            ArrayList<Edge> neighbours = node.getNeighbors();

            for (int i = 0; i < neighbours.size(); i++) {
                temp_power = 0;
                Node n = neighbours.get(i).getTwo();
                if (n != null && ( flag==false || !n.visited ) ) {
                    temp_power += neighbours.get(i).getPower();
                    route.add(n);
                    n.visited = true;

                    if (n.id == id) {
                        found_destination=true;
                    

                        break;
                    }
                }
            }

            if (temp_power < route_power && found_destination==true) {
               // final_route = route;
                 final_route = new Stack<>();
                          for(int j=0;j<route.size();j++)
                            final_route.push(route.get(j));
                route_power = temp_power;
            }
        }

        Route path = new Route(final_route, route_power);
        return path;

   /*     for (int i=0; i<nodes.size(); ++i)
	//dis.add() = Integer.MAX_VALUE;
        
	//dis.add(node.getNodeID()) = 0;
         cost.add(Integer.MAX_VALUE);
        cost.set(node.getNodeID(),0);
for(int i=0 ;i<nodes.size();i++){
 
  
for(int j=0;j<nodes.size();j++){
   if(i==j)
continue;
   else{
   if (dist[u]!=Integer.MAX_VALUE && cost.get(j)+nodes.get(j).getNeighbors().get(j).getWeight()<dist[v])
		cost.set(id,cost.get(j)+nodes.get(j).getNeighbors().get(j).getWeight());
			}

   }
}
}*/        
        
    }
    
    public void BellmanFord(int scr){
    //for (int i=0; i<nodes.size(); ++i)
//			dist[i] = Integer.MAX_VALUE;
//		dist[src] = 0;

    
    }
    
    public void getAllRoutes(Node src){
    for (int i=src.getNodeID()+1;i<nodes.size();i++){
 flag=false;
        Route r=bestRoute(src, nodes.get(""+i).getNodeID());
        src.setNode_Neighbour(""+nodes.get(""+i).getNodeID(),r);
        nodes.get(""+i).visited=false;
        nodes.get(""+i).print();
    
    
    }
    
    
    }
    
    public void nodeGeneration(){
      //  nodes=new HashMap<String,Node>();
        rn=new Random();
        
        AtomicInteger count = new AtomicInteger(0);

        int id=0; double power=0;
        Location l;
        int n=0; 
        double anntena=0;
        
    for(int i=0; i<20;i++){
        n=rn.nextInt(6);
        count.incrementAndGet();
       // id=n+100+i;
        id=count.get();
        power=rn.nextDouble()+1;
        anntena=rn.nextDouble();
        //l.setX(n+1);
        //l.setY(n+1);
        l=new Location(n+50,n+59);
        String label=""+i;
    Node node=new Node(label,id,l,power,anntena,false);
    nodes.put(label,node);
        System.out.println("the node "+ label +"has location X: "+l.getX()+"y: "+l.getY()+"id: "+id);
    
    }
    
    initializeNeighbors();
       for(int i=0;i<nodes.size();i++){
           getAllRoutes(nodes.get(""+i));
       
       }

//Route path;
    //for(int j=0; j <nodes.size() ;j++){
    //path=bestRoute(nodes.get(j), nodes.get(j).getNodeID());
       // System.out.println("the path is "+path.route.toString());
    int old_id=0;
    Message m[]=new Message[1000];
    for (int i=0;i<1000;i++){
        n=rn.nextInt(5)+1;
        
     m[i]=new Message(n+1,n+3,i);
    }
    
}
    }



