/**
 * Class to represent the edges between the nodes.
 * 
 * It's formed by two nodes.
 * The edge value is the distance between these nodes.
 * The edge heuristic value (in TSP problem) is the inverse of the edge value.
 * Each edge has its AntQ value to represent how 'good' is the edge.
 * The edge still has the respective reinforcement learning value, used to update its AntQ value.
 * @author: Matheus Paixão 
 */
public class Edge {
   private City city1;
   private City city2;
   private double edgeValue;
   private double edgeHeuristicValue;
   private double AQValue;
   private double reinforcementLearningValue;

   /**
    * Method to create an edge passing two nodes.
    *
    * The edge value and edge heuristic value are calculated only in the creation of the edge.
    * The AntQ Value and the reinforcement learning value are initiated with 0.
    * @author Matheus Paixão 
    * @param city1 First city of the edge. 
    * @param city2 Second city of the edge. 
    * @see calculateEdgeValue
    * @see calculateEdgeHeuristicValue
    */
   public Edge(City city1, City city2){
      this.city1 = city1;
      this.city2 = city2;

      edgeValue = calculateEdgeValue();
      edgeHeuristicValue = calculateEdgeHeuristicValue();

      this.AQValue = 0;

      this.reinforcementLearningValue = 0;
   }

   public City getCity1(){
      return this.city1;
   }

   public City getCity2(){
      return this.city2;
   }

   public double getEdgeValue(){
      return this.edgeValue;
   }

   public double getEdgeHeuristicValue(){
      return this.edgeHeuristicValue;
   }

   public double getAQValue(){
      return this.AQValue;
   }

   public void setAQValue(double AQValue){
      this.AQValue = AQValue;
   }

   public double getReinforcementLearningValue(){
      return this.reinforcementLearningValue;
   }

   public void setReinforcementLearningValue(double reinforcementLearningValue){
      this.reinforcementLearningValue = reinforcementLearningValue;
   }

   /**
    * Method to compare if an edge is equal to other one.
    *
    * An edge is equal to other if its nodes are equal.
    * @author Matheus Paixão
    * @param edge An Edge to compare.
    * @return true if the edges are equal, false if not.
    * @see equals method of City class 
    */
   public boolean equals(Edge edge){
      boolean result = false;

      if(edge instanceof Edge){
         City city1 = edge.getCity1();
         City city2 = edge.getCity2();
         if((city1.equals(this.getCity1())) && (city2.equals(this.getCity2()))){
            result = true;
         }
      }

      return result;
   }

   /**
    * Method to calculate the value of an edge.
    *
    * The edge value is calculated using the distance between two points equation from analytic geometry.
    * @author Matheus Paixão
    * @return The edge value (distance between the two nodes).
    */
   private double calculateEdgeValue(){
      return Math.sqrt(Math.pow(getCity1().getX() - getCity2().getX(), 2) + 
            Math.pow(getCity1().getY() - getCity2().getY(), 2));
   }

   /**
    * Method to calculate the heuristic value of an edge.
    *
    * In TSP problem the heuristic value is the inverse of the edge value (distance).
    * @author Matheus Paixão
    * @return The edge heuristic value (inverse of the distance).
    */
   private double calculateEdgeHeuristicValue(){
      return 1 / getEdgeValue();
   }

   /**
    * Method to format the edge in String format.
    *
    * @author Matheus Paixão.
    * @return The edge in String format.
    */
   public String toString(){
      return getCity1().toString() + " " + getCity2().toString();
   }
}
