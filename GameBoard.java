import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.MouseInfo;

class ConvaysGameBoard extends JPanel implements MouseListener, MouseMotionListener{
    
    private boolean circles = false;
    private boolean[][] population;
    private boolean[][] backupPopulation;
    private int size = 10;
    private int spacing = 1;
    private int border = 5;
    private int fps = 1;
    private ConvaysPattern pattern;
    private Color background = Color.LIGHT_GRAY;
    private Color aliveColor = Color.BLACK;
    private Color deadColor = Color.WHITE;
    private Thread controller; 
    private boolean isRunning = false;
    private boolean patternMode = false;
    
    // constructor
    public ConvaysGameBoard(boolean[][] population){
        new ConvaysGameBoard(population.length, population[0].length);
    }
    
    public ConvaysGameBoard(int lines, int columns){
        this.population = new boolean[lines][columns];
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    
    // getters and setters
    public int getLines() { return this.population.length; }
    public int getColumns() { return this.population[0].length; }
    public void setData(boolean[][] population) { 
        this.population = population; }  
    public void setPattern(ConvaysPattern pattern) { this.pattern = pattern; }
    public boolean[][] getData(){ return this.population; }
    public int getDisplayWidth(){ return this.population[0].length*(this.size+this.spacing)+2*this.border; }
    public int getDisplayHeight(){ return this.population.length*(this.size+this.spacing)+2*this.border; }
    public ConvaysPattern getPattern() { return this.pattern; }
    public void setFPS (int fps) { this.fps = fps; }
    public boolean getPatternMode (){ return this.patternMode; }
    public void setPatternMode (boolean mode){ this.patternMode = mode; }
    public void setAppearance (Color bg, Color alive, Color dead, boolean circles) {
        this.background = bg;
        this.aliveColor = alive;
        this.deadColor = dead;
        this.circles = circles;
    }
    
    public void setDimensions(int size, int spacing, int border){
        this.size = size;
        this.spacing = spacing;
        this.border = border;
    }
    
    public void clearData(){
        this.population = new boolean[this.population.length][this.population[0].length];
    }
    
    @Override
    public void paint(Graphics g){
        g.setColor(this.background);
        g.fillRect(0,0, this.getDisplayWidth(), this.getDisplayHeight());
        for (int i = 0; i<this.population.length; i++){
            for (int j = 0; j<this.population[0].length; j++){
                if (this.circles){
                    g.setColor(this.deadColor);
                    g.fillRect(j*(this.size+this.spacing)+this.border,i*(this.size+this.spacing)+this.border, this.size, this.size);
                    g.setColor((population[i][j])?aliveColor:deadColor);
                    g.fillOval(j*(this.size+this.spacing)+this.border+1,i*(this.size+this.spacing)+this.border+1, this.size-2, this.size-2);
                }
                else{
                    g.setColor((population[i][j])?aliveColor:deadColor);
                    g.fillRect(j*(this.size+this.spacing)+this.border,i*(this.size+this.spacing)+this.border, this.size, this.size);
                }
            }
        }
    }
    
    private void addPattern(int line, int column){
        ConvaysPattern clearBox = new ConvaysPattern(this.pattern.getLines(), this.pattern.getColumns());
        this.setData(ConvaysUtils.addPattern(ConvaysUtils.copyPopulation(this.backupPopulation), clearBox, line-pattern.getLines()/2, column-clearBox.getColumns()/2));
        this.setData(ConvaysUtils.addPattern(this.population, this.pattern, line-pattern.getLines()/2, column-this.pattern.getColumns()/2));
        this.repaint();
    }
    
    public void backup(){
        this.backupPopulation = this.population.clone();
    }
    
    public void tick(){
        this.setData(ConvaysGameRules.nextGeneration(this.getData()));
        this.repaint();
    }
    
    public void stop(){
        this.isRunning = false;
    }
    
    public void go(){
        this.isRunning = true;
        Runnable r = new Runnable(){
            public void run(){
                while(isRunning){
                    tick();
                    try{
                        Thread.sleep(1000/fps);
                    }catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };
        new Thread(r).start();
    }
    
    public void mousePressed(MouseEvent e) {
       if(!this.patternMode){
           int x = (e.getX()-this.border)/(this.size+this.spacing);
           int y = (e.getY()-this.border)/(this.size+this.spacing);
           if(x<0 || x>=this.population[0].length || y<0 || y>=this.population.length) return;
           this.population[y][x] = !this.population[y][x];
           this.repaint();
        }
    }
  
    public void mouseDragged(MouseEvent e) {
       if(!this.patternMode){
           int x = (e.getX()-this.border)/(this.size+this.spacing);
           int y = (e.getY()-this.border)/(this.size+this.spacing);
           if(x<0 || x>=this.population[0].length || y<0 || y>=this.population.length) return;
           this.population[y][x] = true;
           this.repaint();
        }
    }
    
    public void mouseMoved(MouseEvent e) {
        if (this.patternMode){
            int column = (e.getX()-this.border)/(this.size+this.spacing);
            int line = (e.getY()-this.border)/(this.size+this.spacing);
            this.addPattern(line, column);
        }
    }
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}  
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1){
            this.pattern.transposePattern();
            int column = (e.getX()-this.border)/(this.size+this.spacing);
            int line = (e.getY()-this.border)/(this.size+this.spacing);
            this.addPattern(line, column);
        }
        else if (this.patternMode){
            this.backup();
        }
    }
 
}