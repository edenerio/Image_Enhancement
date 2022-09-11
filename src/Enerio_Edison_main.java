import java.io.*;
import java.util.Scanner;
import java.util.zip.Inflater;
import java.util.zip.ZipException;

public class Enerio_Edison_main {
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
        imageProcessing imgProc = new imageProcessing(threshold, numRows, numCols, maskRows, maskCols, minVal, maxVal, maskMin, maskMax);
        
        //read through inFile
        int currRow = 0;
        int currCol = 0;
        while (inFile.hasNextInt()) {
            int z = inFile.nextInt();
            imgProc.loadImage(z, currRow, currCol++);
            if(currCol == numCols){
                currRow++;
                currCol = 0;
            }
        }
        
        imgProc.mirrorFraming();

        currRow = 0;
        currCol = 0;
        int index = 0;
        //read through maskFile
        while(maskFile.hasNextInt()){
            int zi = maskFile.nextInt();
            imgProc.loadMask(zi, currRow, currCol++);
            imgProc.loadMask1DAry(zi, index++);
            if(currCol == maskCols){
                currRow++;
                currCol = 0;
            }
        }

        //Step 6
        imgProc.imgReformat(imgProc.getMirrorFramedAry(), imgProc.getMinVal(), imgProc.getMaxVal(), inputImg);

        //Step 7
        imgProc.computeAvg();
        imgProc.imgReformat(imgProc.getAvgAry(), imgProc.getNewMin(), imgProc.getNewMax(), AvgOut);
        imgProc.threshold(imgProc.getAvgAry(), imgProc.getThrAry());
        imgProc.imgReformat(imgProc.getThrAry(), imgProc.getNewMin(), imgProc.getNewMax(), AvgThreshold);

        //Step 8
        imgProc.computeMedian();
        imgProc.imgReformat(imgProc.getMedianAry(), imgProc.getNewMin(), imgProc.getNewMax(), MedianOut);
        imgProc.threshold(imgProc.getMedianAry(), imgProc.getThrAry());
        imgProc.imgReformat(imgProc.getThrAry(), imgProc.getNewMin(), imgProc.getNewMax(), MedianThreshold);

        //Step 9
        imgProc.computeGauss();
        imgProc.imgReformat(imgProc.getGaussAry(), imgProc.getNewMin(), imgProc.getNewMax(), GaussOut);
        imgProc.threshold(imgProc.getGaussAry(), imgProc.getThrAry());
        imgProc.imgReformat(imgProc.getThrAry(), imgProc.getNewMin(), imgProc.getNewMax(), GaussThreshold);

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
