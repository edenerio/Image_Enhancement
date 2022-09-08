import java.io.*;
import java.util.Scanner;
import java.util.zip.Inflater;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner inFile = new Scanner(new File(args[0]));
        Scanner maskFile = new Scanner(new File(args[1]));
        int threshold = Integer.parseInt(args[2]);
        Scanner inputImg = new Scanner(new File(args[3]));
        OutputStream AvgOut = new FileOutputStream(args[4]);
        OutputStream AvgThreshold = new FileOutputStream(args[5]);
        OutputStream MedianOut = new FileOutputStream(args[6]);
        OutputStream MedianThreshold = new FileOutputStream(args[7]);
        OutputStream GaussOut = new FileOutputStream(args[8]);
        OutputStream GaussThreshold = new FileOutputStream(args[9]);

        // check if num of arguments is correct
        if (args.length != 10) {
            System.out.println("insufficient arguments");
        }

        int numRows, numCols, minVal, maxVal;
        numRows = inFile.nextInt();
        numCols = inFile.nextInt();
        minVal = inFile.nextInt();
        maxVal = inFile.nextInt();

        imageProcessing myIpClass = new imageProcessing(threshold, numRows, numCols);

        while (inFile.hasNextInt()) {

        }

    }
}
