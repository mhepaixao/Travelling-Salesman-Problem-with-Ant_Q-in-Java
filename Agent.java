import java.util.ArrayList;

public class Agent {
   private ArrayList<City> citiesToVisit;
   private City currentCity;

   public Agent(ArrayList<City> cities){
      this.citiesToVisit = new ArrayList<City>();

      for(int i = 0; i <= cities.size() - 1; i++){
         this.citiesToVisit.add(cities.get(i));
      }
   }

   public ArrayList<City> getCitiesToVisit(){
      return this.citiesToVisit;
   }

   public void setCurrentCity(City currentCity){
      this.currentCity = currentCity;
      citiesToVisit.remove(getCurrentCity());
   }
   
   public City getCurrentCity(){
      return this.currentCity;
   }
}
