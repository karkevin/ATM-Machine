package pastTest;

public class DirectedGraphNode extends GraphNode{

  public String address;
  private String previous;
  private String next;
  public static int numNodes;

  public DirectedGraphNode(String address, String p, String n){
    super(address);
    previous = p;
    next = n;
  }

  public String getAddress(){
    return address;
  }

  public static int getNumNodes(){
    return numNodes;
  }

  public String toString(){
    return "This directed graph node is at: " + address;
  }

  public void printDirectedGN(){
    System.out.println("This is a directed graph node between " + previous + " and " + next);
  }

  public boolean equals(Object o){
    if(((GraphNode)o).address!=null){
      return (o instanceof DirectedGraphNode) && (((GraphNode)o).getAddress() == this.address);
    } else {
      return false;
    }
  }
}
