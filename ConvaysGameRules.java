public class ConvaysGameRules
{
    // Compute one gamestep of Convay's game of life: 
    // The initial population is altered according to the rules of the game
    public static boolean[][] nextGeneration(boolean[][] population){
        boolean[][] next = new boolean[population.length][population[0].length];
        for (int i = 0; i<population.length; i++){
            for (int j = 0; j<population[0].length; j++){
                int neighbours = getNeighbours(population,i,j);
                if (neighbours < 2 || neighbours > 3) next[i][j] = false;
                else if (neighbours == 3) next[i][j] = true;
                else next[i][j] = population[i][j];
                //randomizer
                //next[i][j] = (Math.random()>0.01) ? next[i][j]:!next[i][j];
            }
        }
        return next;
    }
   
    
    // Get the amount of living neighbours of a cell at position (i,j) in a population
    // the neighbours are the 8 cells surrounding the cell
    private static int getNeighbours(boolean[][] population, int i, int j){
        int count = 0;
        for (int di = -1; di <=1; di++){
            for (int dj = -1; dj<=1; dj++){
                if (di == 0 && dj == 0) continue;
                count += isAlive(population,i+di,j+dj)? 1:0;
            }
        }
        return count;
    }
    
    
    /*
    // Game rules for a border of always dead cells around the game board
    private static boolean isAlive(boolean[][] population, int i, int j){
        if (i<0 || j<0 || i>=population.length || j>=population[0].length) return false;
        else return population[i][j];
    }
    */
    
    
   
    // Game rules for a pseudo-infinite toroid gameboard
    private static boolean isAlive(boolean[][] population, int i, int j){
        if (i>=population.length) i=0;
        if (i<0) i=population.length-1;
        if (j>=population[0].length) j=0;
        if (j<0) j=population[0].length-1;
        return population[i][j];
    }
    
}
