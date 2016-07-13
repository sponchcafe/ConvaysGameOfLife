public class Pattern{

    private int[] strips = {};
    private int lines;
    private int columns;
    private boolean start = false;
    
    public Pattern(int lines, int columns){
        this.lines = lines;
        this.columns = columns;
    }
       
    public Pattern(int lines, int columns, int[] strips, boolean start, boolean transposed){
        this.strips = strips;
        this.start = start;
        this.lines = lines;
        this.columns = columns;
        if (transposed){
            this.transposePattern();
        }
    }
    
    public void transposePattern(){
         for (int i=0; i<this.strips.length/2; i++){
                int temp = this.strips[i];
                this.strips[i] = this.strips[strips.length-1-i];
                this.strips[strips.length-1-i] = temp;
            }     
    }
    
    public void setStrips(int[] strips){
        this.strips = strips;
    }
    
    public int getLines() { return this.lines; }
    public int getColumns() { return this.columns; }
    
    public boolean[][] getPatternPopulation(){
        boolean state = false;
        boolean[][] pattern = new boolean[this.lines][this.columns];
        int column = 0;
        for (int strip = 0; strip<this.strips.length; strip++){
            for (int i=0; i<strips[strip]; i++){
                pattern[column/this.columns][column%this.columns] = state;
                column++;
            }
            state = !state;
        }
        return pattern;
    }
}
    

