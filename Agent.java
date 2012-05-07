import java.util.Random;

public class Agent {
   private City citiesToVisit[];
   private City currentCity;

   public Agent(City[] cities){
      this.citiesToVisit = new City[cities.length];

      for(int i = 0; i <= cities.length - 1; i++){
         this.citiesToVisit[i] = cities[i];
      }
   }

   public City getCurrentCity(){
      return this.currentCity;
   }

   //public City[] getCitiesToVisit(){
      //return this.citiesToVisit;
   //}

   public void setCurrentCity(City currentCity){
      this.currentCity = getCorrespondentCity(currentCity);
      citiesToVisit[getCurrentCityIndex()] = null;
   }

   private City getCorrespondentCity(City city){
      City correspondentCity = null;

      for(int i = 0; i <= citiesToVisit.length - 1; i++){
         if(city.equals(citiesToVisit[i])){
            correspondentCity = citiesToVisit[i];
            break;
         }
      }

      return correspondentCity;
   }

   public int getCurrentCityIndex(){
      int index = citiesToVisit.length + 1;

      for(int i = 0; i <= citiesToVisit.length - 1; i++){
         if(getCurrentCity().equals(citiesToVisit[i])){
            index = i;
            break;
         }
      }

      return index;
   }

   public void chooseNextCity(){
      double q = getRandomNumber();

      if(q <= AntQ.getQ0()){

      }
      else{

      }
   }

   private double getRandomNumber(){
      Random random = new Random();
      return random.nextDouble();
   }
}
