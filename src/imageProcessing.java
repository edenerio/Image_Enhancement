import java.util.Arrays;

public class imageProcessing {
    int numRows, numCols, minVal, maxVal, maskRows, maskCols, maskMin, maskMax, newMin, newMax;
    int thrVal;
    int mirrorFramedAry[][];
    int avgAry[][];
    int medianAry[][];
    int GaussAry[][];
    int thrAry[][];
    int mask2Dary[][];
    int neighbor1DAry[];
    int mask1DAry[];

    imageProcessing(int thrVal, int numRows, int numCols) {
        this.thrVal = thrVal;
        this.numRows = numRows;
        this.numCols = numCols;
        this.mirrorFramedAry = new int[numRows + 2][numCols + 2];
        this.avgAry = new int[numRows + 2][numCols + 2];
        this.medianAry = new int[numRows + 2][numCols + 2];
        this.GaussAry = new int[numRows + 2][numCols + 2];
        this.thrAry = new int[numRows + 2][numCols + 2];
        this.mask2Dary = new int[numRows][numCols];
        this.neighbor1DAry = new int[9];
        this.mask1DAry = new int[9];
    }

    void threshold(int[][] ary1, int[][] ary2) {
        for (int i = 1; i <= this.numRows; i++) {
            for (int j = 1; j <= this.numCols; j++) {
                if (ary1[i][j] >= this.thrVal) {
                    ary2[i][j] = 1;
                } else {
                    ary2[i][j] = 0;
                }
            }
        }
    }

    void imgReformat(int[][] inAry, int minVal, int maxVal, String outImg) {
        /* 
        outImg[0] = this.numRows;
        outImg[1] = this.numCols;
        outImg[2] = minVal;
        outImg[3] = maxVal;
        */
        String str = Integer.toString(newMax);
        int width = str.length();
        
        for(int r=1; r<this.numRows; r++){
            for(int c=1; c<this.numCols;c++){
                //outImg[]
            }
        }
    }

    int[][] mirrorFraming(int[][] arr) {
        // The algo of Mirror framing
        //fill all 4 sides in 1 loop
        int row = this.numRows+1;
        int col = this.numCols+1;
        for(int i=1; i<=row; i++){
            arr[i][0] = arr[i][1];
            arr[i][col] = arr[i][col-1];
        }

        for(int i=1; i<=col; i++){
            arr[0][i] = arr[1][i];
            arr[row][i] = arr[row-1][i];
        }

        return arr;
    }

    void loadImage(int x, int row, int col) {
        // Read from input file and load onto mirrorFramedAry begin at [1][1]
        if(row!=this.numRows || col!=this.numCols){
            this.mirrorFramedAry[row+1][col+1]=x;
        }
        else{
            this.mirrorFramedAry = this.mirrorFraming(this.mirrorFramedAry);
        }
    }

    void loadMask(int[][] x) {
        // load maskFile onto mask2DAry
        this.mask2Dary = x;
    }

    void loadMask1DAry(int[] x) {
        // Load 9 px of mask into mask1DAry using 2 loops
        this.mask1DAry = x;
    }

    void loadNiehgbor1DAry(int r, int c) {
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

    int[] sort(int[] n) {
        // sort
        Arrays.sort(n);
        return n;
    }

    void computeAvg() {
        // process the mirrorFramedAry begin at [1][1];
        // keep track of newMin and newMax
        // algo in specs
        this.newMin = 9999;
        this.newMax = 0;
        for(int i=1; i<this.numRows; i++){
            for(int j=1; j<this.numCols; j++){
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

    void computeMedian() {
        // process the mirrorFramedAry begin at [1][1];
        // keep track of newMin and newMax
        // algo in specs
        this.newMin = 9999;
        this.newMax = 0;
        for(int i=1; i<this.numRows; i++){
            for(int j=1; j<this.numCols; j++){
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

    void computeGauss() {
        // process the mirrorFramedAry begin at [1][1];
        // keep track of newMin and newMax
        // algo in specs
        this.newMin = 9999;
        this.newMax = 0;
        for(int i=1; i<this.numRows; i++){
            for(int j=1; j<this.numCols; j++){
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

    int convolution(int[] neighbor1DAry, int[] mask1DAry) {
        // algo in specs
        int result = 0;
        for(int i=0; i<9; i++){
            result+=neighbor1DAry[i]*mask1DAry[i];
        }
        return result;
    }
}
