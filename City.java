/**
 * Class to represent the cities.
 * 
 * It's formed by the id, X and Y value of cartesian coordinates.
 * @author: Matheus Paixao 
 */
public class City {
   private int id;
   private double x;
   private double y;

   /**
    * Method to create a city passing the id, X and Y coordinates.
    *
    * @author Matheus Paixao 
    * @param id id of the city
    * @param x X coordinate of the node. 
    * @param y Y coordinate of the node. 
    */
   public City(int id, double x, double y){
      this.id = id;
      this.x = x;
      this.y = y;
   }

   private int getId(){
      return this.id;
   }

   public double getX(){
      return this.x;
   }

   public double getY(){
      return this.y;
   }

   /**
    * Method to compare if a city is equal to other one.
    *
    * A city is equal to other if they have the same id.
    * @author Matheus Paixao
    * @param city A city to compare.
    * @return true if the cities are equal, false if not.
    */
   public boolean equals(City city){
      boolean result = false;

      if(city instanceof City){
         if(city.getId() == this.getId()){
            result = true;
         }
      }

      return result;
   }

   /**
    * Method to format the city in String format.
    *
    * @author Matheus Paixao.
    * @return The city in String format.
    */
   public String toString(){
      return getId() + " " + "(" + getX() + " " + getY() + ")";
   }
}
