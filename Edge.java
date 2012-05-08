public class Edge {
   private City city1;
   private City city2;
   private double edgeValue;
   private double edgeHeuristicValue;
   private double AQValue;

   public Edge(City city1, City city2){
      this.city1 = city1;
      this.city2 = city2;

      edgeValue = calculateEdgeValue(city1, city2);
      edgeHeuristicValue = calculateEdgeHeuristicValue();

      this.AQValue = 0;
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

   private double calculateEdgeValue(City city1, City city2){
      return Math.sqrt(Math.pow(city1.getX() - city2.getX(), 2) + 
            Math.pow(city1.getY() - city2.getY(), 2));
   }

   private double calculateEdgeHeuristicValue(){
      return 1 / edgeValue;
   }

   public String toString(){
      return getCity1().toString() + " " + getCity2().toString();
   }
}
