package pastTest;

public class TestDemo{

  public static void main(String[] args){

  GraphNode n1 = new GraphNode("123a");
  GraphNode n2 = new DirectedGraphNode("a1", "b2", "c3");
  DirectedGraphNode n3 = new DirectedGraphNode("d4", "e5", "f6");
  GraphNode n4 = n2;
  GraphNode n5 = n3;

  System.out.println("Q1");
  System.out.println(n2);
  System.out.println("Q2");
  System.out.println(n4 == n2);
  System.out.println("Q3");
  System.out.println(n2.address);
  System.out.println("Q4");
  System.out.println(n3.address);
  System.out.println("Q5");
  System.out.println(n1.getAddress());
  System.out.println("Q6");
  System.out.println(n2.getAddress());
  System.out.println("Q7");
  System.out.println(n4.getAddress());
  System.out.println("Q8");
  System.out.println(n1.getNumNodes());
  System.out.println("Q9");
  System.out.println(n2.getNumNodes());
  System.out.println("Q10");
  System.out.println(n3.getNumNodes());
  System.out.println("Q11");
  //n1.printDirectedGN();
  System.out.println("Q12");
  //n2.printDirectedGN();
  System.out.println("Q13");
  ((DirectedGraphNode)n2).printDirectedGN();
  System.out.println("Q14");
  System.out.println(" ");
  
  int x = 2;
  int y = 2;
  Integer z1 = new Integer(2);
  Integer z2 = new Integer(2);
  Integer z3 = z1;

  System.out.println(x == y);
  System.out.println(x == z1);
  System.out.println(z1 == z2);
  System.out.println(z1 == z3);
  System.out.println(" ");

  System.out.println(z1.equals(z2));
  System.out.println(z1.equals(x));
  //System.out.println(x.equals(z1));

  }
}