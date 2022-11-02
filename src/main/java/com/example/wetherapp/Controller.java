package com.example.wetherapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Text feels;

    @FXML
    private Button getData;

    @FXML
    private Text information;

    @FXML
    private Text max;

    @FXML
    private Text min;

    @FXML
    private Text pressure;

    @FXML
    private Text temperature;

    @FXML
    void initialize() {
        getData.setOnAction(event -> {
            //afca35e0a5606c9a045fa6592eb962cf
            String userCity = city.getText().trim();
            String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" + userCity + "&appid=afca35e0a5606c9a045fa6592eb962cf");
            if (output.equals("The city wasn't found :( Check input")){
                temperature.setText("The city wasn't found :(" + "\n Check input");
                max.setText(" ");
                min.setText(" ");
                pressure.setText(" ");
                feels.setText(" ");
            }
            else if (!output.isEmpty()){
                JSONParser parser = new JSONParser();
                try {
                    JSONObject object = (JSONObject) parser.parse(output);
                    JSONObject infoObject = (JSONObject) object.get("main");
                    temperature.setText( "Temperature: " + String.format("%.2f",(Double.parseDouble(infoObject.get("temp").toString()) - 273.15)));
                    max.setText("Max: " + String.format("%.2f", (Double.parseDouble(infoObject.get("temp_max").toString()) - 273.15)));
                    min.setText("Min: " + String.format("%.2f", (Double.parseDouble(infoObject.get("temp_min").toString()) - 273.15)));
                    pressure.setText("Pressure: " + infoObject.get("pressure").toString());
                    feels.setText("Feels like " + String.format("%.2f", (Double.parseDouble(infoObject.get("feels_like").toString()) - 273.15)));
                } catch (ParseException e) {
                    temperature.setText("Smth went wrong!");
                    max.setText(" ");
                    min.setText(" ");
                    pressure.setText(" ");
                    feels.setText(" ");
                }
            }
        });
    }

    private static String getUrlContent(String urlAddress){
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAddress);
            URLConnection urlCon = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch (Exception e){
            return "The city wasn't found :( Check input";
        }
        return content.toString();
    }

}
