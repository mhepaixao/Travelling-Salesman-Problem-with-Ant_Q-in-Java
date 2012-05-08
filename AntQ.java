import java.util.ArrayList;

public class AntQ {
   //CONSTANTS
   private static final double delta = 1;
   private static final double beta = 2;
   private static final double alfa = 0.1;
   private static final double gamma = 0.3;
   private static final double q0 = 0.9;
   private static final int w = 10;

   private static City[] cities;

   private static Edge edges[][];

   private static Agent[] agents;

   public static void main(String[] args){
      int totalIterations = 0;
      int iterationsCounter = 0;

      if(args.length > 0){
         totalIterations = Integer.parseInt(args[0]);
      }
      else{
         totalIterations = 14;
      }

      init();

      while(iterationsCounter <= totalIterations){
         for(int i = 0; i <= cities.length - 1; i++){
            if(i != cities.length - 1){
               for(int j = 0; j <= agents.length - 1; j++){
                  agents[j].moveToNextCity();
               }
            }
            else{
               for(int j = 0; j <= agents.length - 1; j++){
                  agents[j].backToInitialCity();
               }
            }
         }

         iterationsCounter++;
      }
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
      averageValueOfEdges = sumOfEdges / edges.length;
      
      return 1 / (averageValueOfEdges * cities.length);
   }

   private static void initAQValues(double AQ0){
      for(int i = 0; i <= edges.length - 1; i++){
         for(int j = 0; j <= edges.length - 1; j++){
            edges[i][j].setAQValue(AQ0);
         }
      }
   }

   private static void initAgents(){
      //agents = new Agent[cities.length]; 
      agents = new Agent[1]; 

      for(int i = 0; i <= agents.length - 1; i++){
         agents[i] = new Agent(cities[i]);
      }
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
}
