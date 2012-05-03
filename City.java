public class City {
   private double x;
   private double y;

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

   public boolean equals(City city){
      boolean result = false;

      if(city instanceof City){
         if((city.getX() == this.getX()) && (city.getY() == this.getY())){
            result = true;
         }
      }

      return result;
   }

   public String toString(){
      return "(" + getX() + " " + getY() + ")";
   }
}
