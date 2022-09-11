import java.io.*;
import java.util.Scanner;
import java.util.zip.Inflater;
import java.util.zip.ZipException;

public class App {
    public static void main(String[] args) throws IOException {
        // check if num of arguments is correct
        if (args.length != 10) {
            System.out.println("Wrong arguments");
            return;
        }

        Scanner inFile = new Scanner(new File(args[0]));
        Scanner maskFile = new Scanner(new File(args[1]));
        int threshold = Integer.parseInt(args[2]);
        BufferedWriter inputImg = new BufferedWriter(new FileWriter(args[3]));
        BufferedWriter AvgOut = new BufferedWriter(new FileWriter(args[4]));
        BufferedWriter AvgThreshold = new BufferedWriter(new FileWriter(args[5]));
        BufferedWriter MedianOut = new BufferedWriter(new FileWriter(args[6]));
        BufferedWriter MedianThreshold = new BufferedWriter(new FileWriter(args[7]));
        BufferedWriter GaussOut = new BufferedWriter(new FileWriter(args[8]));
        BufferedWriter GaussThreshold = new BufferedWriter(new FileWriter(args[9]));

        int numRows, numCols, minVal, maxVal, maskRows, maskCols, maskMin, maskMax;
        numRows = inFile.nextInt();
        numCols = inFile.nextInt();
        minVal = inFile.nextInt();
        maxVal = inFile.nextInt();

        maskRows = maskFile.nextInt();
        maskCols = maskFile.nextInt();
        maskMin = maskFile.nextInt();
        maskMax = maskFile.nextInt();

        //creating this class also allocates all the arrays
        imageProcessing imgProc = new imageProcessing(threshold, numRows, numCols, maskRows, maskCols);
        
        //read through inFile
        int currRow = 0;
        int currCol = 0;
        while (inFile.hasNextInt()) {
            int z = inFile.nextInt();
            //when this while loop is done, the class automatically calls mirrorFraming()
            imgProc.loadImage(z, currRow, currCol);
            currCol++;
            if(currCol == numCols){
                currRow++;
                currCol = 0;
            }
        }
        
        currRow = 0;
        currCol = 0;
        int index = 0;
        //read through maskFile
        while(maskFile.hasNextInt()){
            int zi = maskFile.nextInt();
            imgProc.loadMask(zi, currRow, currCol);
            imgProc.loadMask1DAry(zi, index++);
            currCol++;
            if(currCol == maskCols){
                currRow++;
                currCol = 0;
            }
        }

        //Step 6
        String inputImgStr = "";
        inputImgStr = imgProc.imgReformat(imgProc.mirrorFramedAry, minVal, maxVal, inputImgStr);
        //write inputImgStr to inputImg file
        inputImg.write(inputImgStr);

        //Step 7
        String imgOutStr = "";
        imgProc.computeAvg();
        imgOutStr = imgProc.imgReformat(imgProc.avgAry, imgProc.newMin, imgProc.newMax, imgOutStr);
        imgProc.threshold(imgProc.avgAry, imgProc.thrAry);
        String AvgThresholdStr = "";
        AvgThresholdStr = imgProc.imgReformat(imgProc.thrAry, imgProc.newMin, imgProc.newMax, AvgThresholdStr);
        //write imgOutStr to imgOut file
        AvgOut.write(imgOutStr);
        AvgThreshold.write(AvgThresholdStr);

        //Step 8
        String MedianOutStr = "";
        imgProc.computeMedian();
        MedianOutStr = imgProc.imgReformat(imgProc.medianAry, imgProc.newMin, imgProc.newMax, MedianOutStr);
        imgProc.threshold(imgProc.medianAry, imgProc.thrAry);
        String MedianThresholdStr = "";
        MedianThresholdStr = imgProc.imgReformat(imgProc.thrAry, imgProc.newMin, imgProc.newMax, MedianThresholdStr);
        //write MedianThresholdStr to MedianThreshold file
        MedianOut.write(MedianOutStr);
        MedianThreshold.write(MedianThresholdStr);

        //Step 9
        imgProc.computeGauss();
        String GaussOutStr = "";
        GaussOutStr = imgProc.imgReformat(imgProc.GaussAry, imgProc.newMin, imgProc.newMax, GaussOutStr);
        imgProc.threshold(imgProc.GaussAry, imgProc.thrAry);
        String GaussThresholdStr = "";
        GaussThresholdStr = imgProc.imgReformat(imgProc.thrAry, imgProc.newMin, imgProc.newMax, GaussThresholdStr);
        //write GaussOutStr to GaussOut file and GaussThresholdStr to GaussThreshold file
        GaussOut.write(GaussOutStr);
        GaussThreshold.write(GaussThresholdStr);

        //Step 10
        inFile.close();
        maskFile.close();
        inputImg.close();
        AvgOut.close();
        AvgThreshold.close();
        MedianOut.close();
        MedianThreshold.close();
        GaussOut.close();
        GaussThreshold.close();

    }
}
