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

    void imgReformat(int[][] inAry, int minVal, int maxVal, int[] outImg) {
        String str = Integer.toString(newMax);
        int width = str.length();
        

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

    void loadImage(int x, int row, int col, boolean done) {
        // Read from input file and load onto mirrorFramedAry begin at [1][1]
        if(!done){
            this.mirrorFramedAry[row][col]=x;
        }
        else{
            this.mirrorFramedAry = mirrorFraming(this.mirrorFramedAry);
        }
    }

    void loadMask() {
        // load maskFile onto mask2DAry
    }

    void loadMask1DAry() {
        // Load 9 px of mask into mask1DAry using 2 loops
    }

    void loadNiehgbor1DAry() {
        // Load the 3x3 neighbors of mirrorFramedAry(i,j) into neighbor1DAry using 2
        // loops
    }

    void sort() {
        // sort neighborAry
    }

    void computeAvg() {
        // process the mirrorFramedAry begin at [1][1];
        // keep track of newMin and newMax
        // algo in specs
    }

    void computeMedian() {
        // process the mirrorFramedAry begin at [1][1];
        // keep track of newMin and newMax
        // algo in specs
        this.newMin = 9999;
        this.newMax = 0;
        for(int i=1; i<this.numRows; i++){
            for(int j=1; j<this.numCols; j++){
                //loadNeighbor1Dary(i,j,neighbor1DAry)
                //sort(neighborAry)
                //medianAry[i,j] <- neighborAry[4]
                /*
                 * if newMin>medianAry[i,j]{
                 *      newMin = medianAry[i,j]
                 * }
                 * if newMax<medianAry[i,j]{
                 *      newMax = medianAry[i,j]
                 * }
                 * 
                 */
            }
        }
    }

    void computeGauss() {
        // process the mirrorFramedAry begin at [1][1];
        // keep track of newMin and newMax
        // algo in specs
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
