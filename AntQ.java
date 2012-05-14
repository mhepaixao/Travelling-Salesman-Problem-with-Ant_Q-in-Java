import java.util.ArrayList;

public class AntQ {
   //CONSTANTS
   private static final double delta = 1;
   private static final double beta = 2;
   private static final double alfa = 0.1;
   private static final double gamma = 0.3;
   private static final double q0 = 0.9;
   private static final double w = 10.0;

   private static City[] cities;

   private static Edge edges[][];

   private static Agent[] agents;

   public static void main(String[] args){
      int totalIterations = 0;
      int iterationsCounter = 0;
      Edge[] iterationBestTour = null; 
      Edge[] globalBestTour = null; 
      int hereCounter = 0;

      if(args.length > 0){
         totalIterations = Integer.parseInt(args[0]);
      }
      else{
         totalIterations = 14;
      }

      init();

      while(iterationsCounter <= totalIterations){
         for(int i = 0; i <= cities.length - 1; i++){
            City nextCity = null;

            if(i != cities.length - 1){
               for(int j = 0; j <= agents.length - 1; j++){
                  nextCity = agents[j].chooseNextCity();
                  agents[j].setNextCity(nextCity);
                  agents[j].addCityToTour(agents[j].getNextCity());
                  if(i == cities.length - 2){
                     agents[j].addInitialCityToCitiesToVisit();
                  }
               }
            }
            else{
               for(int j = 0; j <= agents.length - 1; j++){
                  nextCity = agents[j].getInitialCity();
                  agents[j].setNextCity(nextCity);
                  agents[j].addCityToTour(agents[j].getNextCity());
               }
            }

            for(int j = 0; j <= agents.length - 1; j++){
               updateAQValue(agents[j].getLastTourEdge(), getMaxAQValue(agents[j], agents[j].getNextCity()));

               if(i == cities.length - 1){
                  agents[j].loadCitiesToVisit();
               }
               agents[j].setCurrentCity(agents[j].getNextCity());
               agents[j].removeCityFromCitiesToVisit(agents[j].getNextCity());
            }
         }

         iterationBestTour = getIterationBestTour();

         for(int i = 0; i <= agents.length - 1; i++){
            agents[i].clearTour();
         }

         for(int i = 0; i <= iterationBestTour.length - 1; i++){
            updateReinforcementLearningValue(iterationBestTour[i], calculateTourValue(iterationBestTour));
         }

         for(int i = 0; i < cities.length - 1; i++){
            for(int j = 0; j <= cities.length - 1; j++){
               if(i != j){
                  updateAQValue(edges[i][j], 0);
               }
            }
         }

         for(int i = 0; i <= iterationBestTour.length - 1; i++){
            clearReinforcementLearningValue(iterationBestTour[i]);
         }

         if(iterationsCounter == 0){
            globalBestTour = iterationBestTour;
         }
         else{
            if(calculateTourValue(iterationBestTour) < calculateTourValue(globalBestTour)){
               System.out.println("here");
               hereCounter++;
               globalBestTour = iterationBestTour;
            }
         }

         iterationsCounter++;
      }
      System.out.println("here counter: " + hereCounter);
      System.out.println("Best tour value: " + calculateTourValue(globalBestTour));
      System.exit(0);
   }
   
   private static void init(){
      InstanceReader instanceReader = new InstanceReader();
      cities = instanceReader.getCitiesList();

      createEdges();
      initAQValues(getAQ0());

      initAgents();
   }

   private static void createEdges(){
      edges = new Edge[cities.length][cities.length];

      for(int i = 0; i <= cities.length - 1; i++){
         for(int j = 0; j <= cities.length - 1; j++){
            edges[i][j] = new Edge(cities[i], cities[j]);
         }
      }
   }

   private static double getAQ0(){
      double sumOfEdges = 0;
      double averageValueOfEdges = 0;

      for(int i = 0; i <= edges.length - 1; i++){
         for(int j = 0; j <= edges.length - 1; j++){
            sumOfEdges += edges[i][j].getEdgeValue();
         }
      }
      averageValueOfEdges = sumOfEdges / getNumberOfEdges();
      
      return 1 / (averageValueOfEdges * cities.length);
   }

   private static int getNumberOfEdges(){
      int numberOfEdges = 0;

      for(int i = 0; i <= cities.length - 1; i++){
         for(int j = 0; j <= cities.length - 1; j++){
            if(i != j){
               numberOfEdges++;
            }
         }
      }

      return numberOfEdges;
   }

   private static void initAQValues(double AQ0){
      for(int i = 0; i <= edges.length - 1; i++){
         for(int j = 0; j <= edges.length - 1; j++){
            if(i == j){
               edges[i][j].setAQValue(0);
            }
            else{
               edges[i][j].setAQValue(AQ0);
            }
         }
      }
   }

   private static void initAgents(){
      agents = new Agent[cities.length]; 
      //agents = new Agent[1]; 

      for(int i = 0; i <= agents.length - 1; i++){
         agents[i] = new Agent(cities[i]);
      }
   }

   public static int getCityIndex(City city){
      int index = cities.length + 1;

      for(int i = 0; i <= cities.length - 1; i++){
         if(city.equals(cities[i])){
            index = i;
            break;
         }
      }

      return index;
   }

   private static void updateAQValue(Edge edge, double maxAQValue){
      int city1Index = getCityIndex(edge.getCity1());
      int city2Index = getCityIndex(edge.getCity2());
      Edge edgeToUpdate = edges[city1Index][city2Index];

      edgeToUpdate.setAQValue((1 - alfa) * edgeToUpdate.getAQValue() + alfa * (edgeToUpdate.getReinforcementLearningValue() + gamma * maxAQValue));
   }

   public static double getMaxAQValue(Agent agent, City nextCity){
      double maxAQValue = 0;
      double edgeAQValue = 0;
      int nextCityIndex = getCityIndex(nextCity);
      City[] citiesToVisit = agent.getCitiesToVisit();

      for(int i = 0; i <= citiesToVisit.length - 1; i++){
         if((citiesToVisit[i] != null) && (!nextCity.equals(citiesToVisit[i]))){
            edgeAQValue = edges[nextCityIndex][getCityIndex(citiesToVisit[i])].getAQValue();
            if(edgeAQValue > maxAQValue){
               maxAQValue = edgeAQValue;
            }
         }
      }

      //What is the maxAQValue of the penultimate edge?
      //if(maxAQValue == 0){

      //}

      return maxAQValue;
   }

   private static Edge[] getIterationBestTour(){
      Edge[] iterationBestTourTemp = agents[0].getTour();
      Edge[] iterationBestTour = new Edge[iterationBestTourTemp.length];
      Edge[] tour = null;
      double iterationBestTourValue = calculateTourValue(iterationBestTourTemp);
      double tourValue = 0;

      for(int i = 0; i <= agents.length - 1; i++){
         tour = agents[i].getTour();
         tourValue = calculateTourValue(tour);
         if(calculateTourValue(tour) < iterationBestTourValue){
            iterationBestTourValue = tourValue;
            iterationBestTourTemp = tour;
         }
      }

      for(int i = 0; i <= iterationBestTourTemp.length - 1; i++){
         iterationBestTour[i] = iterationBestTourTemp[i];
      }

      return iterationBestTour;
   }

   private static void updateReinforcementLearningValue(Edge edge, double tourValue){
      int city1Index = getCityIndex(edge.getCity1());
      int city2Index = getCityIndex(edge.getCity2());

      //System.out.println("tour value: " +tourValue);
      //System.out.println("before update: " +edges[city1Index][city2Index].getReinforcementLearningValue());
      edges[city1Index][city2Index].setReinforcementLearningValue(w / tourValue);
      //System.out.println("after update: " +edges[city1Index][city2Index].getReinforcementLearningValue());
   }

   private static void clearReinforcementLearningValue(Edge edge){
      int city1Index = getCityIndex(edge.getCity1());
      int city2Index = getCityIndex(edge.getCity2());

      edges[city1Index][city2Index].setReinforcementLearningValue(0);
   }

   private static double calculateTourValue(Edge[] tour){
      double tourValue = 0;

      for(int i = 0; i <= tour.length - 1; i++){
         tourValue += tour[i].getEdgeValue();
      }

      return tourValue;
   }

   public static double getQ0(){
      return q0;
   }

   public static City[] getCities(){
      return cities;
   }

   public static Edge[][] getEdges(){
      return edges;
   }

   public static double getGamma(){
      return gamma;
   }

   public static double getBeta(){
      return beta;
   }

   public static double getDelta(){
      return delta;
   }
}
