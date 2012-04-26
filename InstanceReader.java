import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.ArrayList;

public class InstanceReader extends JFrame {
   public ArrayList<City> getCitiesList(){
      ArrayList<City> cities = new ArrayList<City>();
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

               x = Double.parseDouble(instanceLine.split(" ")[0]);
               y = Double.parseDouble(instanceLine.split(" ")[1]);
               city = new City(x, y);

               cities.add(city);
            }
         }
         catch(Exception e){
            e.printStackTrace();
         }
      }

      return cities;
   }

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
