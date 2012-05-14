import java.util.Random;

/**
 * Class to describe the behavior of the agents, or ants, in the goal
 * to find best tours over the nodes.
 *
 * The initialCity variable stores the initial city of the agent. It's necessary when the agent 
 * finish its tour and has to go back to the beginning.
 *
 * The currentCity variable stores the current city of the agent. It's used in the state transition rule.
 *
 * Each agent has to know its nextCity to go before really go. It's used basically in AntQ class.
 *
 * The citiesToVisit array store the nodes that the agent didn't visit yet. The null values are visited nodes.
 *
 * The tour array is the path, the sequency of nodes, done by the agent.
 *
 * @author Matheus Paixão
 */
public class Agent {
   private City initialCity;
   private City currentCity;
   private City nextCity;
   private City citiesToVisit[];
   //private Edge tour[];
   public Edge tour[];

   /**
    * Method to create an agent with its initial city.
    *
    * Create the citiesToVisit array with the same size of the cities array of AntQ.
    * Create the tour array with the same size of the citiesToVisit.
    * Fill the citiesToVisit array with City objects equals to the cities array of AntQ.
    * Set the initial city the current city and remove the initial city of the cities to be visited.
    * @author Matheus Paixão
    * @param initialCity the city that will be the initial city of the agent.
    * @see loadCitiesToVisit
    * @see removeCityFromCitiesToVisit
    */
   public Agent(City initialCity){
      this.citiesToVisit = new City[AntQ.getCities().length];
      tour = new Edge[getCitiesToVisit().length];

      loadCitiesToVisit();

      this.initialCity = initialCity;
      setCurrentCity(getInitialCity());
      removeCityFromCitiesToVisit(getInitialCity());
   }

   public City getInitialCity(){
      return this.initialCity;
   }

   public City getCurrentCity(){
      return this.currentCity;
   }

   public void setNextCity(City city){
      this.nextCity = city;
   }

   public City getNextCity(){
      return this.nextCity;
   }

   public City[] getCitiesToVisit(){
      return this.citiesToVisit;
   }

   public void setCurrentCity(City currentCity){
      this.currentCity = getCorrespondentCity(currentCity);
   }

   public Edge[] getTour(){
      return this.tour;
   }

   /**
    * Method to fill the citiesToVisit array with City objects that 
    * are equal to the cities from the AntQ algorithm.
    *
    * @author Matheus Paixão
    */
   public void loadCitiesToVisit(){
      City cities[] = AntQ.getCities();

      for(int i = 0; i <= cities.length - 1; i++){
         this.citiesToVisit[i] = cities[i];
      }
   }

   /**
    * Method to remove a city from cities to be visited.
    *
    * Get the index of the city in the cities array in AntQ and
    * set the correspondent city in citiesToVisit to null.
    * @author Matheus Paixão
    * @param city the city to be removed from citiesToVisit.
    * @see getCityIndex in AntQ class.
    */
   public void removeCityFromCitiesToVisit(City city){
      citiesToVisit[AntQ.getCityIndex(city)] = null;
   }

   /**
    * Method to add the initial city to the cities to be visited.
    *
    * Get the index of the initial city of the agent in the cities array in AntQ
    * and set the correspondent position of the citiesToVisit with the initial city.
    * It's used when the agent have visited all the nodes and has to go back to the first one.
    * @author Matheus Paixão
    * @see getCityIndex in AntQ class.
    */
   public void addInitialCityToCitiesToVisit(){
      City initialCity = getInitialCity();
      citiesToVisit[AntQ.getCityIndex(initialCity)] = initialCity;
   }

   /**
    * Method to add a new city to the tour.
    *
    * Insert an edge where the city 1 is the current city and the city 2 is the city to be added.
    * @author Matheus Paixão
    * @param city city to be added to the tour.
    * @see insertEdge
    */
   public void addCityToTour(City city){
      Edge[][] edges = AntQ.getEdges();
      int currentCityIndex = AntQ.getCityIndex(getCurrentCity());
      int cityIndex = AntQ.getCityIndex(city);

      insertEdge(edges[currentCityIndex][cityIndex]);
   }
   
   /**
    * Method to insert an edge to the agent tour.
    *
    * Insert an edge equal to the edge from the AntQ algorihtm is inserted in the last null position of the tour.
    * @author Matheus Paixão
    * @param edge the edge from the edges matrix of AntQ algorithm to be inserted in the tour.
    */
   private void insertEdge(Edge edge){
      for(int i = 0; i <= tour.length - 1; i++){
         if(tour[i] == null){
            tour[i] = new Edge(edge.getCity1(), edge.getCity2());
            break;
         }
      }
   }

   /**
    * Method to get the last edge added to the agent tour.
    *
    * @author Matheus Paixão
    * @return the last edge added to the agent tour.
    */
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

   /**
    * Method to clear the agent tour.
    *
    * It's used when an agent finish a tour (visit all cities) and has to start another one.
    * @author Matheus Paixão
    */
   public void clearTour(){
      for(int i = 0; i <= tour.length - 1; i ++){
         tour[i] = null;
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

   public City chooseNextCity(){
      double q = getRandomNumber();
      City nextCity = null;

      if(q <= AntQ.getQ0()){
         nextCity = getMaxActionChoiceCity();
      }
      else{
         //nextCity = getPseudoRandomProportionalCity();
         nextCity = getPseudoRandom();
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

   private City getPseudoRandom(){
      double probabilities[] = new double[citiesToVisit.length];
      double maxProbability = 0;
      City maxProbabilityCity = null;
      
      for(int i = 0; i <= probabilities.length - 1; i++){
         if(citiesToVisit[i] != null){
            probabilities[i] = getRandomNumber();
            if(probabilities[i] > maxProbability){
               maxProbability = probabilities[i];
               maxProbabilityCity = citiesToVisit[i];
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

   private double getActionChoice(City city){
      Edge edges[][] = AntQ.getEdges();
      Edge edge = edges[AntQ.getCityIndex(getCurrentCity())][AntQ.getCityIndex(city)];
      double edgeAQValue = edge.getAQValue();
      double edgeHeuristicValue = edge.getEdgeHeuristicValue();

      return Math.pow(edgeAQValue, AntQ.getDelta()) * Math.pow(edgeHeuristicValue, AntQ.getBeta());
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
}
