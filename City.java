/**
 * Class to represent the nodes.
 * 
 * It's formed by the X and Y value of cartesian coordinates.
 * @author: Matheus Paixao 
 */
public class City {
   private double x;
   private double y;

   /**
    * Method to create an node passing the X and Y coordinates.
    *
    * @author Matheus Paixao 
    * @param x X coordinate of the node. 
    * @param y Y coordinate of the node. 
    */
   public City(double x, double y){
      this.x = x;
      this.y = y;
   }

   public double getX(){
      return this.x;
   }

   public double getY(){
      return this.y;
   }

   /**
    * Method to compare if a node is equal to other one.
    *
    * A node is equal to other if its X and Y values are equal.
    * @author Matheus Paixao
    * @param city A node to compare.
    * @return true if the nodes are equal, false if not.
    */
   public boolean equals(City city){
      boolean result = false;

      if(city instanceof City){
         if((city.getX() == this.getX()) && (city.getY() == this.getY())){
            result = true;
         }
      }

      return result;
   }

   /**
    * Method to format the node in String format.
    *
    * @author Matheus Paixao.
    * @return The node in String format.
    */
   public String toString(){
      return "(" + getX() + " " + getY() + ")";
   }
}
