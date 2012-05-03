public class Edge {
   private City city1;
   private City city2;

   public Edge(City city1, City city2){
      this.city1 = city1;
      this.city2 = city2;
   }

   public City getCity1(){
      return this.city1;
   }

   public City getCity2(){
      return this.city2;
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

   public double getEdgeValue(){
      City city1 = this.getCity1();
      City city2 = this.getCity2();

      return Math.sqrt(Math.pow(city1.getX() - city2.getX(), 2) + 
            Math.pow(city1.getY() - city2.getY(), 2));
   }
}
