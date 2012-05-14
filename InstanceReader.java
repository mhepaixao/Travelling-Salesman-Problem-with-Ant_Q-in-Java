import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.ArrayList;

/**
 * Class used to read the instance.
 * 
 * This class extends JFrame because the user chooses the instance by a JFileChooser.
 * @author: Matheus Paixão 
 */
public class InstanceReader extends JFrame {

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
            City city = null;
            double x = 0;
            double y = 0;

            BufferedReader reader = new BufferedReader(new FileReader(instance));
            while(reader.ready()){
               instanceLine = reader.readLine();

               //instance formart 1:
               x = Double.parseDouble(instanceLine.split(" ")[0]);
               y = Double.parseDouble(instanceLine.split(" ")[1]);

               city = new City(x, y);

               dynamicListOfCities.add(city);
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
}
