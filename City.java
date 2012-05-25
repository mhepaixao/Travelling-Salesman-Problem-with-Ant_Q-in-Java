/**
 * Class to represent the cities.
 * 
 * It's formed by the index in the cities array of AntQ algorithm, X and Y value of cartesian coordinates.
 * @author: Matheus Paixao 
 */
public class City {
   private int index;
   private double x;
   private double y;

   /**
    * Method to create a city passing the index, X and Y coordinates.
    *
    * @author Matheus Paixao 
    * @param index index of the city
    * @param x X coordinate of the node. 
    * @param y Y coordinate of the node. 
    */
   public City(int index, double x, double y){
      this.index = index;
      this.x = x;
      this.y = y;
   }

   /**
    * Method to create a city passing only the index.
    *
    * The 'x' and 'y' values are set to 0.
    * @author Matheus Paixao 
    * @param index index of the city
    */
   public City(int index){
      this.index = index;
      this.x = 0;
      this.y = 0;
   }

   public int getIndex(){
      return this.index;
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
    * A city is equal to other if they have the same index.
    * @author Matheus Paixao
    * @param city A city to compare.
    * @return true if the cities are equal, false if not.
    */
   public boolean equals(City city){
      boolean result = false;

      if(city instanceof City){
         if(city.getIndex() == this.getIndex()){
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
      return getIndex() + " " + "(" + getX() + " " + getY() + ")";
   }
}
