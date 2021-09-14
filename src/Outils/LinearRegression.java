package Outils;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public class LinearRegression {
    private static final List<Integer> wimg = asList(902,1200,1500,850,790, 750, 800); // Consecutive hours developer codes
    private static final List<Integer> Xoffset = asList(212, 279, 352,201,185, 176, 187); // Number of bugs produced

    public static Double predictForValue(int predictForDependentVariable, List<Integer> x, List<Integer> y) {
        if (x.size() != y.size())
            throw new IllegalStateException("Must have equal X and Y data points");

        Integer numberOfDataValues = x.size();

        List<Double> xSquared = x
                .stream()
                .map(position -> Math.pow(position, 2))
                .collect(Collectors.toList());

        List<Integer> xMultipliedByY = IntStream.range(0, numberOfDataValues)
                .map(i -> x.get(i) * y.get(i))
                .boxed()
                .collect(Collectors.toList());

        Integer xSummed = x
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Integer ySummed = y
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double sumOfXSquared = xSquared
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Integer sumOfXMultipliedByY = xMultipliedByY
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        int slopeNominator = numberOfDataValues * sumOfXMultipliedByY - ySummed * xSummed;
        Double slopeDenominator = numberOfDataValues * sumOfXSquared - Math.pow(xSummed, 2);
        Double slope = slopeNominator / slopeDenominator;

        System.out.println(slope);

        double interceptNominator = ySummed - slope * xSummed;
        double interceptDenominator = numberOfDataValues;
        Double intercept = interceptNominator / interceptDenominator;

        return (slope * predictForDependentVariable) + intercept;
    }

    public static List<Data> ConvetCSVtolist(String url) throws IOException {
        List<Data> list=new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(new File(url)));
        try {
            // Read first line
            String line = br.readLine();
            // Make sure file has correct headers
            if (line == null) throw new IllegalArgumentException("File is empty");
            if (!line.equals("imagesize,feildpropertie"))
                throw new IllegalArgumentException("File has wrong columns:" + line);
            // Run through following lines
            while ((line = br.readLine()) != null) {
                // Break line into entries using comma
                String[] items = line.split(",");
                try {
                    // If there are too many entries, throw a dummy exception, if
                    // there are too few, the same exception will be thrown later
                    if (items.length>4) throw new ArrayIndexOutOfBoundsException();
                    // add data to list
                    Data data=new Data();
                    data.setImgsize(Integer.parseInt(items[0]));
                    data.setFieldpro(Integer.parseInt(items[1]));
                    list.add(data);
                } catch (ArrayIndexOutOfBoundsException|NumberFormatException|NullPointerException e) {
                    // Caught errors indicate a problem with data format -> Print warning and continue
                    System.out.println("Invalid line: "+ line);
                }
            }
            return list;

        }finally {
            br.close();
        }
    }

    public static void getlist(List<Data> data,List<Integer> imgsize,List<Integer> fieldpro){
        for (Data data1:data){
            imgsize.add((int) data1.getImgsize());
            fieldpro.add((int) data1.getFieldpro());
        }
    }
    public static void getlist(List<Data> data,List<Integer> fieldpro){
        for (Data data1:data){
            fieldpro.add((int) data1.getFieldpro());
        }
    }

    public static Rectangle posinnement(String folderName, ImageView imageView) throws IOException {

        List<Data> listh=ConvetCSVtolist("C:\\Users\\Abdelhamid\\Documents\\NetBeansProjects\\Deriction_des_impots\\src\\Dataset\\G51\\Dataset\\"+folderName+"\\Height.csv");
        List<Data> listw=ConvetCSVtolist("C:\\Users\\Abdelhamid\\Documents\\NetBeansProjects\\Deriction_des_impots\\src\\Dataset\\G51\\Dataset\\"+folderName+"\\Width.csv");
        List<Data> listx=ConvetCSVtolist("C:\\Users\\Abdelhamid\\Documents\\NetBeansProjects\\Deriction_des_impots\\src\\Dataset\\G51\\Dataset\\"+folderName+"\\Xoffset.csv");
        List<Data> listy=ConvetCSVtolist("C:\\Users\\Abdelhamid\\Documents\\NetBeansProjects\\Deriction_des_impots\\src\\Dataset\\G51\\Dataset\\"+folderName+"\\Yoffset.csv");

        final List<Integer> wimg=new ArrayList<Integer>();
        final List<Integer> Xoffset=new ArrayList<Integer>();

        final List<Integer> himg=new ArrayList<Integer>();
        final List<Integer> Yoffset=new ArrayList<Integer>();

        final List<Integer> wfield=new ArrayList<Integer>();

        final List<Integer> hfield=new ArrayList<Integer>();

        getlist(listx,wimg,Xoffset);

        getlist(listy,himg,Yoffset);

        getlist(listw,wfield);

        getlist(listh,hfield);

        double x=LinearRegression.predictForValue((int) imageView.getFitWidth(),wimg,Xoffset);
        double w=LinearRegression.predictForValue((int) imageView.getFitWidth(),wimg,wfield);
        double y=LinearRegression.predictForValue((int) imageView.getFitHeight(),himg,Yoffset);
        double h=LinearRegression.predictForValue((int) imageView.getFitHeight(),himg,hfield);

        javafx.scene.shape.Rectangle rectangle=new javafx.scene.shape.Rectangle();
        rectangle.setLayoutX(x);
        rectangle.setLayoutY(y);
        rectangle.setHeight(h);
        rectangle.setWidth(w);

        return rectangle;

    }

    public static void main(String[] args) throws IOException {
        final List<Integer> wimg = asList(902,1200,1500,850,790, 750, 800); // Consecutive hours developer codes
        final List<Integer> Xoffset = asList(212, 279, 352,201,185, 176, 187);
        //System.out.println(predictForValue(851,wimg,Xoffset));

        List<Data> listh=ConvetCSVtolist("C:\\Users\\Abdelhamid\\Documents\\NetBeansProjects\\Deriction_des_impots\\src\\Dataset\\G51\\Dataset\\Adress_bailleur\\Xoffset.csv");

        List<Integer> listw=new ArrayList<>();
        List<Integer> listx=new ArrayList<>();

        for (Data data:listh) {
            listw.add((int) data.getImgsize());
            listx.add((int) data.getFieldpro());

            System.out.println(data.getImgsize()+".."+data.getFieldpro());

        }

        System.out.println(predictForValue(879,listw,listx));


    }
}