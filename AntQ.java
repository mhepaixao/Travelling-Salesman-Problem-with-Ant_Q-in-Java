import java.util.ArrayList;

public class AntQ {
   private static ArrayList<City> cities;
   private static int citiesNumber;

   private static double distanceMatrix[][];
   private static double AQ[][];

   private static void init(){
      InstanceReader instanceReader = new InstanceReader();
      cities = instanceReader.getCitiesList();
      citiesNumber = cities.size();

      fillDistanceMatrix();

      double AQ0 = getAQ0();
      AQ = new double[citiesNumber][citiesNumber];
   }

   private static void fillDistanceMatrix(){
      distanceMatrix = new double[citiesNumber][citiesNumber];

      for(int i = 0; i <= citiesNumber - 1; i++){
         for(int j = 0; j <= citiesNumber - 1; j++){
            distanceMatrix[i][j] = distance(cities.get(i), cities.get(j));
         }
      }
   }

   private static double distance(City city1, City city2){
      return Math.sqrt(Math.pow(city1.getX() - city2.getX(), 2) + 
                        Math.pow(city1.getY() - city2.getY(), 2));
   }

   private static void printDistanceMatrix(){
      for(int i = 0; i <= citiesNumber - 1; i++){
         for(int j = 0; j <= citiesNumber - 1; j++){
            System.out.print(Math.ceil(distanceMatrix[i][j]) + " ");
         }
            System.out.println("");
      }
   }

   private static double getAQ0(){
      double sumOfEdges = 0;
      int numberOfEdges = 0;
      double averageValueOfEdges = 0;

      for(int i = 0; i <= citiesNumber - 1; i++){
         for(int j = 0; j <= citiesNumber - 1; j++){
            sumOfEdges += distanceMatrix[i][j];
            numberOfEdges++;
         }
      }
      averageValueOfEdges = sumOfEdges / numberOfEdges;
      
      return 1 / averageValueOfEdges;
   }

   public static void main(String[] args){
      init();
   }
}
