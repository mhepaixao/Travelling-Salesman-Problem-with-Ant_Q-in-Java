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
 * @author: Matheus Paixão 
 */
public class InstanceReader extends JFrame {
   Pattern pattern;

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
    * 1) X Y : in this format each line is a node and X an Y are its coordinates.  
    *
    * @author Matheus Paixão
    * @return a Node array containing all the nodes of the instance.
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

            BufferedReader reader = new BufferedReader(new FileReader(instance));
            while(reader.ready()){
               instanceLine = reader.readLine();

               pattern = Pattern.compile("[a-z][a-z]");
               Matcher twoLettersMatcher = pattern.matcher(instanceLine) ;
               pattern = Pattern.compile("[0-9]");
               Matcher numbersMatcher = pattern.matcher(instanceLine) ;

               if((twoLettersMatcher.find() == false) && (numbersMatcher.find() == true)){
                  pattern = Pattern.compile("\\s{2,}");
                  Matcher spacesMatcher = pattern.matcher(instanceLine);
                  instanceLine = spacesMatcher.replaceAll(" ").trim();

                  values = instanceLine.split(" ");
                  if(values.length > 3){
                     //distance matrix format
                  }
                  else{
                     if(values.length == 3){
                        if(instanceLine.contains("e")){
                           city = getExpCartesianCity(values[1], values[2]);
                        }
                        else{
                           city = getCartesianCity(values[1], values[2]);
                        }
                     }
                     else{
                        if(instanceLine.contains("e")){
                           city = getExpCartesianCity(values[0], values[1]);
                        }
                        else{
                           city = getCartesianCity(values[0], values[1]);
                        }
                     }
                  }

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
         cities[i] = (City) temporaryCities[i]; //cast the Object array to a Node array
      }

      return cities;
   }

   /**
    * Method to get the instance file.
    *
    * Uses a JFileChooser to select the instace file.
    * @author Matheus Paixão
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

   private City getExpCartesianCity(String value1, String value2){
      String[]  xValues = value1.split("e+");
      double x = Double.parseDouble(xValues[0]);
      double xPower = Double.parseDouble(xValues[1]);

      String[]  yValues = value2.split("e+");
      double y = Double.parseDouble(yValues[0]);
      double yPower = Double.parseDouble(yValues[1]);

      return new City(x * Math.pow(10, xPower), y * Math.pow(10, yPower));
   }

   private City getCartesianCity(String value1, String value2){
      double x = Double.parseDouble(value1);
      double y = Double.parseDouble(value2);

      return new City(x, y);
   }
}
