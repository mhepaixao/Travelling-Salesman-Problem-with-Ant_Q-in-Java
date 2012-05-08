import java.util.Random;

public class Agent {
   private City initialCity;
   private City citiesToVisit[];
   private City currentCity;
   private int currentCityIndex;
   private Edge tour[];

   public Agent(City initialCity){
      this.citiesToVisit = new City[AntQ.getCities().length];

      loadCitiesToVisit();

      this.initialCity = initialCity;
      setCurrentCity(this.initialCity);

      tour = new Edge[this.citiesToVisit.length];
   }

   private City getInitialCity(){
      return this.initialCity;
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

   public void backToInitialCity(){
      City initialCity = getInitialCity();
      
      loadCitiesToVisit();
      addCityToTour(initialCity);

      setCurrentCity(initialCity);
   }

   private void loadCitiesToVisit(){
      City cities[] = AntQ.getCities();

      for(int i = 0; i <= cities.length - 1; i++){
         this.citiesToVisit[i] = cities[i];
      }
   }

   public void setCurrentCity(City currentCity){
      this.currentCity = getCorrespondentCity(currentCity);
      this.currentCityIndex = getCityIndex(getCurrentCity());
      citiesToVisit[getCurrentCityIndex()] = null;
   }

   private void addCityToTour(City city){
      Edge[][] edges = AntQ.getEdges();
      int currentCityIndex = getCurrentCityIndex();
      int cityIndex = getCityIndex(city);

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

   public void moveToNextCity(){
      double q = getRandomNumber();
      City nextCity = null;

      if(q <= AntQ.getQ0()){
         nextCity = getMaxActionChoiceCity();
      }
      else{
         nextCity = getPseudoRandomProportionalCity();
      }

      addCityToTour(nextCity);
      setCurrentCity(nextCity);
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
      Edge edge = edges[getCurrentCityIndex()][getCityIndex(city)];
      double edgeAQValue = edge.getAQValue();
      double edgeHeuristcValue = edge.getEdgeHeuristicValue();

      return Math.pow(edgeAQValue, AntQ.getGamma()) * Math.pow(edgeHeuristcValue, AntQ.getBeta());
   }
}
