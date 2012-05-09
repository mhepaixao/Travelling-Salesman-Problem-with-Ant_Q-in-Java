import java.util.Random;

public class Agent {
   private City initialCity;
   private City citiesToVisit[];
   private City currentCity;
   //private Edge tour[];
   public Edge tour[];

   public Agent(City initialCity){
      this.citiesToVisit = new City[AntQ.getCities().length];

      loadCitiesToVisit();

      this.initialCity = initialCity;
      setCurrentCity(getInitialCity());
      removeCityFromCitiesToVisit(getInitialCity());

      tour = new Edge[this.citiesToVisit.length];
   }

   public City getInitialCity(){
      return this.initialCity;
   }

   public City getCurrentCity(){
      return this.currentCity;
   }

   public City[] getCitiesToVisit(){
      return this.citiesToVisit;
   }

   public void loadCitiesToVisit(){
      City cities[] = AntQ.getCities();

      for(int i = 0; i <= cities.length - 1; i++){
         this.citiesToVisit[i] = cities[i];
      }
   }

   public void setCurrentCity(City currentCity){
      this.currentCity = getCorrespondentCity(currentCity);
   }

   public void removeCityFromCitiesToVisit(City city){
      citiesToVisit[AntQ.getCityIndex(city)] = null;
   }

   public void addInitialCityToCitiesToVisit(){
      City initialCity = getInitialCity();
      citiesToVisit[AntQ.getCityIndex(initialCity)] = initialCity;
   }

   public void addCityToTour(City city){
      Edge[][] edges = AntQ.getEdges();
      int currentCityIndex = AntQ.getCityIndex(getCurrentCity());
      int cityIndex = AntQ.getCityIndex(city);

      insertEdge(edges[currentCityIndex][cityIndex]);
   }
   
   private void insertEdge(Edge edge){
      for(int i = 0; i <= tour.length - 1; i++){
         if(tour[i] == null){
            tour[i] = edge;
            break;
         }
      }
   }

   public void clearTour(){
      for(int i = 0; i <= tour.length - 1; i ++){
         tour[i] = null;
      }
   }

   public Edge getLastTourEdge(){
      Edge lastTourEdge = null;

      for(int i = tour.length - 1; i >= 0; i--){
         if(tour[i] != null){
            lastTourEdge = tour[i];
            break;
         }
      }

      return lastTourEdge;
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

   public City getNextCity(){
      double q = getRandomNumber();
      City nextCity = null;

      if(q <= AntQ.getQ0()){
         nextCity = getMaxActionChoiceCity();
      }
      else{
         nextCity = getPseudoRandomProportionalCity();
      }

      return nextCity;
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

   private City getPseudoRandomProportionalCity(){
      City maxProbabilityCity = getFirstCityToVisit();
      City city = null;

      for(int i = 0; i <= citiesToVisit.length - 1; i++){
         if(citiesToVisit[i] != null){
            city = citiesToVisit[i];
            if(getProbability(city) > getProbability(maxProbabilityCity)){
               maxProbabilityCity = city;
            }
         }
      }

      return maxProbabilityCity;
   }

   private double getProbability(City city){
      return getActionChoice(city) / getActionChoiceSum();
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

   private double getActionChoiceSum(){
      double actionChoiceSum = 0;

      for(int i = 0; i <= citiesToVisit.length - 1; i++){
         if(citiesToVisit[i] != null){
            actionChoiceSum += getActionChoice(citiesToVisit[i]);
         }
      }

      return actionChoiceSum;
   }

   private double getActionChoice(City city){
      Edge edges[][] = AntQ.getEdges();
      Edge edge = edges[AntQ.getCityIndex(getCurrentCity())][AntQ.getCityIndex(city)];
      double edgeAQValue = edge.getAQValue();
      double edgeHeuristcValue = edge.getEdgeHeuristicValue();

      return Math.pow(edgeAQValue, AntQ.getGamma()) * Math.pow(edgeHeuristcValue, AntQ.getBeta());
   }
}
