import java.util.ArrayList;
import java.util.HashMap;

public class AntQ {
   private static ArrayList<City> cities;

   private static Edge edges[][];

   private static ArrayList<Agent> agents;

   public static void main(String[] args){
      init();
   }

   private static void init(){
      InstanceReader instanceReader = new InstanceReader();
      cities = instanceReader.getCitiesList();

      createEdges();
      initAQValues(getAQ0());

      initAgents();
   }

   private static void createEdges(){
      edges = new Edge[cities.size()][cities.size()];

      for(int i = 0; i <= cities.size() - 1; i++){
         for(int j = 0; j <= cities.size() - 1; j++){
            edges[i][j] = new Edge(cities.get(i), cities.get(j));
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
      
      return 1 / (averageValueOfEdges * cities.size());
   }

   private static void initAQValues(double AQ0){
      for(int i = 0; i <= edges.length - 1; i++){
         for(int j = 0; j <= edges.length - 1; j++){
            edges[i][j].setAQValue(AQ0);
         }
      }
   }

   private static void initAgents(){
      agents = new ArrayList<Agent>(); 

      for(int i = 0; i <= cities.size() - 1; i++){
         agents.add(new Agent(cities));
         agents.get(i).setCurrentCity(cities.get(i));
      }
   }
}
