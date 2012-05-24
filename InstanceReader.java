import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;

/**
 * Class used to read the instance.
 * 
 * This class extends JFrame because the user chooses the instance by a JFileChooser.
 * @author: Matheus Paixao 
 */
public class InstanceReader extends JFrame {
   private String instanceType;
   private File instance;
   private Matcher twoLettersMatcher;
   private Matcher numbersMatcher;
   private Matcher spacesMatcher;

   public InstanceReader(){
      instance = readInstance();
   }

   private File getInstance(){
      return this.instance;
   }

   /**
    * Method to get the type of the instance.
    *
    * In TSP problem the instance can be in matrix format or in cartesian coordinates format.
    * @author Matheus Paixao
    * @return the type of the instance.
    * @see regex package
    * @see getInstance
    */
   public String getIntanceType(){
      String instanceType = null;
      String instanceLine = null;
      String values[] = null;

      try{
         BufferedReader reader = new BufferedReader(new FileReader(getInstance()));
         while(reader.ready()){
            instanceLine = reader.readLine();

            twoLettersMatcher = Pattern.compile("[a-z][a-z]").matcher(instanceLine) ;
            numbersMatcher = Pattern.compile("[0-9]").matcher(instanceLine) ;

            //if it isn't a text line and has numbers
            if((twoLettersMatcher.find() == false) && (numbersMatcher.find() == true)){
               spacesMatcher = Pattern.compile("\\s{2,}").matcher(instanceLine);
               instanceLine = spacesMatcher.replaceAll(" ").trim(); //replace all spaces for just one

               values = instanceLine.split(" ");
               if(values.length > 3){
                  instanceType = "matrix";
               }
               else{
                  instanceType = "coordinates";
               }
            }
         }
      }
      catch(Exception e){
         System.out.println("Get instance type error");
      }

      return instanceType;
   }

   public void setInstanceType(String instanceType){
      this.instanceType = instanceType;
   }

   /**
    * Method to get the list of cities when the instace is in cartesian coordinates format.
    *
    * It is used an auxiliary object. dynamicListOfCities is an ArrayList of City
    * and is used to create the cities in a dynamic form. The AntQ algorithm uses an 
    * array of cities. So, after create all the cities the dynamicListOfCities is casted
    * to a simple City array.
    *
    * The cartesian coordinates have to be in one of the formats that follows:
    * 1) n Xe+P Ye+P, where n is the line number, X is the x cartesian value x*(10^P) and Y is the y cartesian value y*(10^P)  
    * 2) n X Y, where n is the line number, X is the x cartesian value and Y is the y cartesian value  
    * 3) Xe+P Ye+P, where X is the x cartesian value x*(10^P) and Y is the y cartesian value y*(10^P)  
    * 4) X Y, where X is the x cartesian value and Y is the y cartesian value  
    *
    * @author Matheus Paixao
    * @return a City array containing all the cities of the instance.
    * @see getInstance
    * @see toArray method from ArrayList class
    */
   public City[] getCitiesList(){
      City[] cities = null; //array to return
      ArrayList<City> dynamicListOfCities = new ArrayList<City>(); 
      File instance = getInstance();

      if(instance != null){
         try{
            String instanceLine = null;
            String[] values;
            City city = null;
            int instanceLineCounter = 0; //will serve as a city id

            BufferedReader reader = new BufferedReader(new FileReader(instance));
            while(reader.ready()){
               instanceLine = reader.readLine();

               Matcher twoLettersMatcher = Pattern.compile("[a-z][a-z]").matcher(instanceLine) ;
               Matcher numbersMatcher = Pattern.compile("[0-9]").matcher(instanceLine) ;

               //if it isn't a text line and has numbers
               if((twoLettersMatcher.find() == false) && (numbersMatcher.find() == true)){
                  Matcher spacesMatcher = Pattern.compile("\\s{2,}").matcher(instanceLine);
                  instanceLine = spacesMatcher.replaceAll(" ").trim(); //replace all spaces for just one

                  values = instanceLine.split(" ");
                  if(values.length == 3){
                     if(instanceLine.contains("e")){
                        city = getExpCartesianCity(instanceLineCounter, values[1], values[2]); //format 1
                     }
                     else{
                        city = getCartesianCity(instanceLineCounter, values[1], values[2]); //format 2
                     }
                  }
                  else{
                     if(instanceLine.contains("e")){
                        city = getExpCartesianCity(instanceLineCounter, values[0], values[1]); //format 3
                     }
                     else{
                        city = getCartesianCity(instanceLineCounter, values[0], values[1]); //format 4
                     }
                  }

                  instanceLineCounter++;
               }

               if(city != null){
                  dynamicListOfCities.add(city);
               }
            }
         }
         catch(Exception e){
            e.printStackTrace();
         }

      }
      
      //cast the dynamicListOfCities to a City array
      cities = new City[dynamicListOfCities.size()];
      for(int i = 0; i <= cities.length - 1; i++){
         cities[i] = dynamicListOfCities.get(i);
      }

      return cities;
   }

   /**
    * Method to get the instance file.
    *
    * Uses a JFileChooser to select the instace file.
    * @author Matheus Paixao
    * @return the instance file.
    */
   private File readInstance(){
      JFileChooser instanceChooser = new JFileChooser();
      File instance = null;

      Integer choose = instanceChooser.showOpenDialog(this);
      if (choose.equals(JFileChooser.APPROVE_OPTION)) {
         instance = instanceChooser.getSelectedFile();
      }

      return instance;
   }

   /**
    * Method to get the city when the values are in exponencial format.
    *
    * @author Matheus Paixao
    * @param id the id of the city.
    * @param value1 the x cartesian value in exponencial format.
    * @param value2 the y cartesian value in exponencial format.
    * @return the city with the x and y coordinates.
    */
   private City getExpCartesianCity(int id, String value1, String value2){
      String[]  xValues = value1.split("e+");
      double x = Double.parseDouble(xValues[0]);
      double xPower = Double.parseDouble(xValues[1]);

      String[]  yValues = value2.split("e+");
      double y = Double.parseDouble(yValues[0]);
      double yPower = Double.parseDouble(yValues[1]);

      return new City(id, x * Math.pow(10, xPower), y * Math.pow(10, yPower));
   }

   /**
    * Method to get the city when the values are just in String format.
    *
    * @author Matheus Paixao
    * @param id the id of the city.
    * @param value1 the x cartesian value in String format.
    * @param value2 the y cartesian value in String format.
    * @return the city with the x and y coordinates.
    */
   private City getCartesianCity(int id, String value1, String value2){
      double x = Double.parseDouble(value1);
      double y = Double.parseDouble(value2);

      return new City(id, x, y);
   }
}
