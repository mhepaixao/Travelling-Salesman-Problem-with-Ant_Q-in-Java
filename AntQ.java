import java.util.ArrayList;

public class AntQ {
   private static ArrayList<City> cities;
   private static int numberOfCities;

   private static ArrayList<Agent> agents;

   private static double distanceMatrix[][];
   private static double AQ[][];

   public static void main(String[] args){
      init();
   }

   private static void init(){
      InstanceReader instanceReader = new InstanceReader();
      cities = instanceReader.getCitiesList();
      numberOfCities = cities.size();

      fillDistanceMatrix();

      double AQ0 = getAQ0();
      initAQMatrix(AQ0);

      initAgents();
   }

   private static void fillDistanceMatrix(){
      distanceMatrix = new double[numberOfCities][numberOfCities];

      for(int i = 0; i <= numberOfCities - 1; i++){
         for(int j = 0; j <= numberOfCities - 1; j++){
            distanceMatrix[i][j] = distance(cities.get(i), cities.get(j));
         }
      }
   }

   private static double distance(City city1, City city2){
      return Math.sqrt(Math.pow(city1.getX() - city2.getX(), 2) + 
                        Math.pow(city1.getY() - city2.getY(), 2));
   }

   private static void printDistanceMatrix(){
      for(int i = 0; i <= numberOfCities - 1; i++){
         for(int j = 0; j <= numberOfCities - 1; j++){
            System.out.print(Math.ceil(distanceMatrix[i][j]) + " ");
         }
            System.out.println("");
      }
   }

   private static double getAQ0(){
      double sumOfEdges = 0;
      int numberOfEdges = 0;
      double averageValueOfEdges = 0;

      for(int i = 0; i <= numberOfCities - 1; i++){
         for(int j = 0; j <= numberOfCities - 1; j++){
            sumOfEdges += distanceMatrix[i][j];
            numberOfEdges++;
         }
      }
      averageValueOfEdges = sumOfEdges / numberOfEdges;
      
      return 1 / (averageValueOfEdges * numberOfCities);
   }

   private static void initAQMatrix(double AQ0){
      AQ = new double[numberOfCities][numberOfCities];

      for(int i = 0; i <= numberOfCities - 1; i++){
         for(int j = 0; j <= numberOfCities - 1; j++){
            AQ[i][j] = AQ0;
         }
      }
   }

   private static void initAgents(){
      agents = new ArrayList<Agent>(); 

      for(int i = 0; i <= numberOfCities - 1; i++){
         agents.add(new Agent(cities));
         agents.get(i).setCurrentCity(cities.get(i));
      }
   }
}
