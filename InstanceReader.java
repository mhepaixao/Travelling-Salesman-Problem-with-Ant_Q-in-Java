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
   private Pattern pattern;

   public String getIntanceType(){
      return this.instanceType;
   }

   public void setInstanceType(String instanceType){
      this.instanceType = instanceType;
   }

   /**
    * Method to get the list of nodes.
    *
    * It is used two auxiliary objects. dynamicListOfCities is an ArrayList of Node
    * and is used to create the nodes in a dynamic form. The AntQ algorithm uses an 
    * array of nodes but the toArray method of ArrayList class returns an Object array. So 
    * temporaryCities is used to get the objects of dynamicListOfCities and cast them 
    * to Node objects.
    *
    * The instance file has to be in one of the formats that follows:
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
      Object[] temporaryCities = null; //auxiliary array to get the Object array and cast to Node
      ArrayList<City> dynamicListOfCities = new ArrayList<City>(); 

      File instance = getInstance();

      if(instance != null){
         try{
            String instanceLine = null;
            String[] values;
            City city = null;
            double x = 0;
            double y = 0;
            int instanceLineCounter = 0; //will serve as a city id

            BufferedReader reader = new BufferedReader(new FileReader(instance));
            while(reader.ready()){
               instanceLine = reader.readLine();

               pattern = Pattern.compile("[a-z][a-z]");
               Matcher twoLettersMatcher = pattern.matcher(instanceLine) ;
               pattern = Pattern.compile("[0-9]");
               Matcher numbersMatcher = pattern.matcher(instanceLine) ;

               //if it isn't a text line and has numbers
               if((twoLettersMatcher.find() == false) && (numbersMatcher.find() == true)){
                  pattern = Pattern.compile("\\s{2,}");
                  Matcher spacesMatcher = pattern.matcher(instanceLine);
                  instanceLine = spacesMatcher.replaceAll(" ").trim(); //replace all spaces for just one

                  values = instanceLine.split(" ");
                  if(values.length > 3){
                     //distance matrix format
                     setInstanceType("matrix");
                  }
                  else{
                     setInstanceType("coordinates");

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
      
      temporaryCities = dynamicListOfCities.toArray();
      cities = new City[temporaryCities.length];
      for(int i = 0; i <= cities.length - 1; i++){
         cities[i] = (City) temporaryCities[i]; //cast the Object array to a City array
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
   private File getInstance(){
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
