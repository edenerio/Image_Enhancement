import java.util.Arrays;
import java.io.*;

public class imageProcessing {
    private int numRows, numCols, minVal, maxVal, maskRows, maskCols, maskMin, maskMax, newMin, newMax;
    private int thrVal;
    private int mirrorFramedAry[][];
    private int avgAry[][];
    private int medianAry[][];
    private int GaussAry[][];
    private int thrAry[][];
    private int mask2Dary[][];
    private int neighbor1DAry[];
    private int mask1DAry[];

    imageProcessing(int thrVal, int numRows, int numCols, int maskRows, int maskCols, int minVal, int maxVal, int maskMin, int maskMax) {
        this.thrVal = thrVal;
        this.numRows = numRows;
        this.numCols = numCols;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.newMin = minVal;
        this.newMax = maxVal;
        this.maxVal = maxVal;
        this.maskMin = maskMin;
        this.maskMax = maskMax;
        this.mirrorFramedAry = new int[numRows + 2][numCols + 2];
        this.avgAry = new int[numRows + 2][numCols + 2];
        this.medianAry = new int[numRows + 2][numCols + 2];
        this.GaussAry = new int[numRows + 2][numCols + 2];
        this.thrAry = new int[numRows + 2][numCols + 2];
        this.mask2Dary = new int[maskRows][maskCols];
        this.neighbor1DAry = new int[9];
        this.mask1DAry = new int[9];
    }

    public void threshold(int[][] ary1, int[][] ary2) {
        this.newMin = 0;
        this.newMax = 1;
        for (int i = 1; i < this.numRows+2; i++) {
            for (int j = 1; j < this.numCols+2; j++) {
                if (ary1[i][j] >= this.thrVal) {
                    ary2[i][j] = 1;
                } else {
                    ary2[i][j] = 0;
                }
            }
        }
    }

    public void imgReformat (int[][] inAry, int minVal, int maxVal, BufferedWriter outImg) throws IOException {
        outImg.write(Integer.toString(this.numRows));
        outImg.write(" ");
        outImg.write(Integer.toString(this.numCols));
        outImg.write(" ");
        outImg.write(Integer.toString(this.newMin));
        outImg.write(" ");
        outImg.write(Integer.toString(this.newMax));
        outImg.write(" ");
        String str = Integer.toString(newMax);
        outImg.write("\n");
        int width = str.length();
        
        
        for(int r=1; r<=this.numRows; r++){
            for(int c=1; c<=this.numCols;c++){
                outImg.write(Integer.toString(inAry[r][c]));
                String str2 = Integer.toString(inAry[r][c]);
                int WW = str2.length();
                outImg.write(" ");
                while(WW < width){
                    outImg.write(" ");
                    WW++;
                }
            }
            outImg.write("\n");
        }
    }

    public void mirrorFraming() {
        // The algo of Mirror framing
        //fill all 4 sides in 1 loop
        int row = this.numRows+1;
        int col = this.numCols+1;
        for(int i=1; i<=row; i++){
            this.mirrorFramedAry[i][0] = this.mirrorFramedAry[i][1];
            this.mirrorFramedAry[i][col] = this.mirrorFramedAry[i][col-1];
        }

        for(int i=0; i<=col; i++){
            this.mirrorFramedAry[0][i] = this.mirrorFramedAry[1][i];
            this.mirrorFramedAry[row][i] = this.mirrorFramedAry[row-1][i];
        }
    }

    public void loadImage(int x, int row, int col) {
        // Read from input file and load onto mirrorFramedAry begin at [1][1]
        this.mirrorFramedAry[row+1][col+1]=x;
    }

    public void loadMask(int x, int r, int c) {
        // load maskFile onto mask2DAry
        this.mask2Dary[r][c] = x;
    }

    public void loadMask1DAry(int x, int i) {
        // Load 9 px of mask into mask1DAry using 2 loops
        this.mask1DAry[i] = x;
    }

    private void loadNiehgbor1DAry(int r, int c) {
        // Load the 3x3 neighbors of mirrorFramedAry(i,j) into neighbor1DAry using 2 loops
        int x=0;
        for(int i=r-1; i<r+2; i++){
            this.neighbor1DAry[x++] = this.mirrorFramedAry[i][c-1];
            this.neighbor1DAry[x++] = this.mirrorFramedAry[i][c+1];
        }
        for(int i=r-1; i<r+2; i+=2){
            this.neighbor1DAry[x++] = this.mirrorFramedAry[i][c];
        }
        this.neighbor1DAry[x]=this.mirrorFramedAry[r][c];
    }

    private int[] sort(int[] n) {
        // sort
        Arrays.sort(n);
        return n;
    }

    public void computeAvg() {
        // process the mirrorFramedAry begin at [1][1];
        // keep track of newMin and newMax
        // algo in specs
        this.newMin = 9999;
        this.newMax = 0;
        for(int i=1; i<=this.numRows; i++){
            for(int j=1; j<=this.numCols; j++){
                this.loadNiehgbor1DAry(i,j);
                this.neighbor1DAry = this.sort(this.neighbor1DAry);
                this.avgAry[i][j] = this.neighbor1DAry[4];
                if(this.newMin > this.avgAry[i][j]){
                    this.newMin = this.avgAry[i][j];
                }
                if(this.newMax<this.avgAry[i][j]){
                    this.newMax = this.avgAry[i][j];
                }
            }
        }
    }

    public void computeMedian() {
        // process the mirrorFramedAry begin at [1][1];
        // keep track of newMin and newMax
        // algo in specs
        this.newMin = 9999;
        this.newMax = 0;
        for(int i=1; i<=this.numRows; i++){
            for(int j=1; j<=this.numCols; j++){
                this.loadNiehgbor1DAry(i,j);
                this.neighbor1DAry = this.sort(this.neighbor1DAry);
                this.medianAry[i][j] = this.neighbor1DAry[4];
                if(this.newMin > this.medianAry[i][j]){
                    this.newMin = this.medianAry[i][j];
                }
                if(this.newMax<this.medianAry[i][j]){
                    this.newMax = this.medianAry[i][j];
                }
            }
        }
    }

    public void computeGauss() {
        // process the mirrorFramedAry begin at [1][1];
        // keep track of newMin and newMax
        // algo in specs
        this.newMin = 9999;
        this.newMax = 0;
        for(int i=1; i<=this.numRows; i++){
            for(int j=1; j<=this.numCols; j++){
                this.loadNiehgbor1DAry(i, j);
                this.GaussAry[i][j] = this.convolution(this.neighbor1DAry, this.mask1DAry);
                if(this.newMin > this.GaussAry[i][j]){
                    this.newMin = this.GaussAry[i][j];
                }
                if(this.newMax < this.GaussAry[i][j]){
                    this.newMax = this.GaussAry[i][j];
                }
            }
        }
    }

    private int convolution(int[] neighbor1DAry, int[] mask1DAry) {
        // algo in specs
        int result = 0;
        for(int i=0; i<9; i++){
            result+=neighbor1DAry[i]*mask1DAry[i];
        }
        return result;
    }

    //get methods
    public int[][] getMirrorFramedAry(){
        return this.mirrorFramedAry;
    }
    public int[][] getAvgAry(){
        return this.avgAry;
    }
    public int[][] getMedianAry(){
        return this.medianAry;
    }
    public int[][] getGaussAry(){
        return this.GaussAry;
    }
    public int[][] getThrAry(){
        return this.thrAry;
    }
    public int[][] getMask2DAry(){
        return this.mask2Dary;
    }
    public int[] getNeighbor1DAry(){
        return this.neighbor1DAry;
    }
    public int[] getMask1DAry(){
        return this.mask1DAry;
    }

    public int getNumRows(){
        return this.numRows;
    }
    public int getNumCols(){
        return this.numCols;
    }
    public int getMinVal(){
        return this.minVal;
    }
    public int getMaxVal(){
        return this.maxVal;
    }
    public int getMaskRows(){
        return this.maskRows;
    }
    public int getMaskCols(){
        return this.maskCols;
    }
    public int getMaskMin(){
        return this.maskMin;
    }
    public int getMaskMax(){
        return this.maskMax;
    }
    public int getNewMin(){
        return this.newMin;
    }
    public int getNewMax(){
        return this.newMax;
    }
    public int getThrVal(){
        return this.thrVal;
    }
}
