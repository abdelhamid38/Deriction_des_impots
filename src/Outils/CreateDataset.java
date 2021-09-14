package Outils;

import java.io.*;

public class CreateDataset {
    
    public static void createCSV(String name){
        StringBuilder builder=new StringBuilder();
        builder.append("imageswidth").append(",").append("imagesheight").append(",").append("Name").append(",").append("XOffset").append(",").append("YOffset").append(",").append("width").append(",").append("height");
        try (FileWriter fileWriter = new FileWriter("C:\\Users\\Abdelhamid\\Documents\\NetBeansProjects\\Deriction_des_impots\\src\\Dataset\\"+name)){

            fileWriter.write(builder.toString());

        }catch (Exception e){

        }
    }
    public static void createCSVforfield(String imagesize,String feildpropertie ,String url){
        StringBuilder builder=new StringBuilder();
        builder.append(imagesize).append(",").append(feildpropertie);
        try (FileWriter fileWriter = new FileWriter(url)){

            fileWriter.write(builder.toString());

        }catch (Exception e){

        }
    }
    public static void addtoCsv(double imgw, double imgh, String Name, float x, float y, float w, float h){

        try {
            FileWriter fileWriter=new FileWriter("C:\\Users\\Abdelhamid\\Documents\\NetBeansProjects\\Deriction_des_impots\\src\\Dataset\\Dataset.csv",true);
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            PrintWriter printWriter=new PrintWriter(bufferedWriter);
            printWriter.println(imgw+","+imgh+","+Name+","+x+","+y+","+w+","+h);
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void addtoCsv(int imgsize,int imglocate,String url){

        try {
            FileWriter fileWriter=new FileWriter(url,true);
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            PrintWriter printWriter=new PrintWriter(bufferedWriter);
            printWriter.println(imgsize+","+imglocate);
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
