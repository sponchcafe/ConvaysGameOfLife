import java.util.Random;

public class Utils
{
    private static final int[] GLIDER_CANNON_STRIPS = {24,1,33,1,1,1,23,2,6,2,12,2,11,1,3,1,4,2,12,4,8,1,5,1,3,2,14,2,8,1,3,1,1,2,4,1,1,1,21,1,5,1,7,1,22,1,3,1,32,2,22};
    public static final Pattern GLIDER_CANNON = new Pattern(9,36,GLIDER_CANNON_STRIPS,false, true);
    // console printout of a population matrix
    public static void printField(boolean[][] population){   
        System.out.printf("+");
        for (int j = 0; j<population[0].length; j++, System.out.printf(" –"));
        System.out.printf(" +");
        System.out.println();
        for (int i = 0; i<population.length; i++){
            System.out.printf("|");
            for (int j = 0; j<population[0].length; j++){
                if (population[i][j]) System.out.printf(" X");
                else System.out.printf("  ");
            }
            System.out.printf(" |\n");
        }
        System.out.printf("+");
        for (int j = 0; j<population[0].length; j++, System.out.printf(" –"));
        System.out.printf(" +\n");
    }
    
    // fill a matrix with random living cells, density specifies the fraction of living cells
    public static boolean[][] getRandomPopulation(boolean[][] population, double density){
        Random r = new Random();
        for (int i = 0; i<population.length; i++){
            for (int j = 0; j<population[0].length; j++){
                population[i][j] = (r.nextInt(population.length*population[0].length)
                < density*population.length*population[0].length)? true : false ;
            }
        }
        return population;
    }
    
    public static boolean[][] addPattern(boolean[][] population, Pattern pattern, int line, int column){
        // check if pattern fits into population
        if(line<0 || column<0 || population.length<line+pattern.getLines() || population[0].length<column+pattern.getColumns()) return population;
        
        // add pattern lines
        boolean[][] patternPopulation = pattern.getPatternPopulation();
        for (int i = 0; i<pattern.getLines(); i++) {
            for (int j = 0; j<pattern.getColumns(); j++) {
                population[line+i][column+j] = patternPopulation[i][j];
            }
        }
        return population;
    }
    
    public static boolean[][] copyPopulation(boolean[][] population){
        boolean[][] copy = new boolean[population.length][population[0].length];
        for (int i=0; i<population.length; i++){
            for (int j=0; j<population[0].length; j++){
                copy[i][j] = population[i][j];
            }
        }
        return copy; 
    }
}
