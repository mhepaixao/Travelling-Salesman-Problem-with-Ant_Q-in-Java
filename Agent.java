import java.util.Random;

public class Agent {
   private City citiesToVisit[];
   private City currentCity;
   private int currentCityIndex;

   public Agent(City[] cities){
      this.citiesToVisit = new City[cities.length];

      for(int i = 0; i <= cities.length - 1; i++){
         this.citiesToVisit[i] = cities[i];
      }
   }

   public City getCurrentCity(){
      return this.currentCity;
   }

   private int getCurrentCityIndex(){
      return this.currentCityIndex;
   }

   //public City[] getCitiesToVisit(){
      //return this.citiesToVisit;
   //}

   public void setCurrentCity(City currentCity){
      this.currentCity = getCorrespondentCity(currentCity);
      this.currentCityIndex = getCityIndex(getCurrentCity());
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

   private int getCityIndex(City city){
      int index = citiesToVisit.length + 1;

      for(int i = 0; i <= citiesToVisit.length - 1; i++){
         if(city.equals(citiesToVisit[i])){
            index = i;
            break;
         }
      }

      return index;
   }

   public void chooseNextCity(){
      double q = getRandomNumber();

      if(q <= AntQ.getQ0()){
         setCurrentCity(getMaxActionChoiceCity());
      }
      else{

      }
   }

   private double getRandomNumber(){
      Random random = new Random();
      return random.nextDouble();
   }

   private City getMaxActionChoiceCity(){
      City maxActionChoiceCity = getFirstCityToVisit();
      City city = null;

      for(int i = 0; i <= citiesToVisit.length - 1; i++){
         if(citiesToVisit[i] != null){
            city = citiesToVisit[i];
            if(getActionChoice(city) > getActionChoice(maxActionChoiceCity)){
               maxActionChoiceCity = city;
            }
         }
      }

      return maxActionChoiceCity;
   }

   private City getFirstCityToVisit(){
      City firstCityToVisit = null;

      for(int i = 0; i <= citiesToVisit.length - 1; i++){
         if(citiesToVisit[i] != null){
            firstCityToVisit = citiesToVisit[i];
            break;
         }
      }

      return firstCityToVisit;
   }

   private double getActionChoice(City city){
      Edge edges[][] = AntQ.getEdges();
      Edge edge = edges[getCurrentCityIndex()][getCityIndex(city)];
      double edgeAQValue = edge.getAQValue();
      double edgeHeuristcValue = edge.getEdgeHeuristicValue();

      return Math.pow(edgeAQValue, AntQ.getGamma()) * Math.pow(edgeHeuristcValue, AntQ.getBeta());
   }
}
