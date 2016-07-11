import java.lang.Thread;
import java.lang.InterruptedException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import java.awt.BorderLayout; 
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.Color;

class ConvaysMain{
    
    // SETTINGS (RECOMENDED FOR SMALL SCREENS)
    private static final int LINES = 175;    // Numbrt of lines on the board (50)
    private static final int COLUMNS = 300;  // Number of columns on the board (75)
    private static final int SIZE = 3;     // Size of a single square cell in px (12)
    private static final int SPACING = 1;   // Spacing between individual cells in px (1)
    private static final int BORDER = 5;    // Border around the board in px (5)
    private static final double DENSITY = 0.1;      // Population density for random fill (0.1)
    private static final boolean CIRCLES = false;    // Use circles for living cells (false defaults to full squares) (true)
    private static final Color BACKGROUND = new Color(0,20,0);     // 8-bit RGB color for background (0,20,0)
    private static final Color ALIVE_COLOR = new Color(80,200,80);  // 8-bit RGB color for living cells (80,200,80)
    private static final Color DEAD_COLOR = new Color(10,30,10);    // 8-bit RGB color for dead cells (10,30,10)
    
    private static ConvaysGameBoard gameBoard;
    
    // GUI
    private static JButton b_Go;
    private static JButton b_Stop;
    private static JButton b_Step;
    private static JButton b_Random;
    private static JButton b_Clear;
    private static JButton b_Cannon;
    private static JLabel l_FPS;
    private static JSpinner s_FPS;
    private static SpinnerNumberModel snm_FPS;
    
    public static void main(String[] args){
       
       // init gameboard
       gameBoard = new ConvaysGameBoard(LINES,COLUMNS);
       gameBoard.setDimensions(SIZE, SPACING, BORDER);
       gameBoard.setAppearance(BACKGROUND, ALIVE_COLOR, DEAD_COLOR, CIRCLES);
       gameBoard.setPattern(ConvaysUtils.GLIDER_CANNON);
       
       // setup option pane
       JPanel controlPane = new JPanel();
       controlPane.setLayout(new GridBagLayout());
       b_Go = new JButton("go");
       b_Step = new JButton("step");
       b_Stop = new JButton("stop");
       b_Random = new JButton("randomize");
       b_Cannon = new JButton("glider cannon");
       b_Clear = new JButton("clear");
       snm_FPS = new SpinnerNumberModel(10,1,100,1);
       s_FPS = new JSpinner(snm_FPS);
       l_FPS = new JLabel("steps per second: ");
       controlPane.add(l_FPS);
       controlPane.add(s_FPS);
       controlPane.add(b_Go); 
       controlPane.add(b_Stop);
       controlPane.add(b_Step);
       controlPane.add(b_Random);
       controlPane.add(b_Cannon);
       controlPane.add(b_Clear);
       
       // add Button Listeners
       b_Step.addActionListener(ae -> 
       {
           gameBoard.tick();
       }
       );
       
       b_Clear.addActionListener(ae -> 
       {
           gameBoard.clearData();
           gameBoard.repaint();
       }
       );
       
       b_Random.addActionListener(ae -> 
       {
           gameBoard.setPatternMode(false);
           gameBoard.setData(ConvaysUtils.getRandomPopulation(gameBoard.getData(), DENSITY));
           gameBoard.repaint();
       }
       );
       
       b_Go.addActionListener(ae -> 
       {
           gameBoard.setFPS( (int) snm_FPS.getNumber());
           if (gameBoard.getPatternMode()){
               gameBoard.setPatternMode(false);
               b_Cannon.setText("glider cannon");
           }
           disableGUI(b_Stop);
           gameBoard.go();
       }
       );
       
       b_Stop.addActionListener(ae -> 
       {
           gameBoard.stop();
           enableGUI();
       }
       );
       
       b_Cannon.addActionListener(ae -> 
       {   
           // save old board and set Pattern
           gameBoard.backup();
           gameBoard.setPattern(ConvaysUtils.GLIDER_CANNON);
           b_Cannon.setText("done");
           if(gameBoard.getPatternMode()){
               b_Cannon.setText("glider cannon");
               gameBoard.setPatternMode(false);
               enableGUI();
               return;
           }
           gameBoard.setPatternMode(true);
           disableGUI(b_Cannon);
       }
       );
       
       // setup window
       JFrame f = new JFrame("Convay's Game of Life "+COLUMNS+"x"+LINES);
       f.getContentPane().setLayout(new BorderLayout());
       f.getContentPane().add(gameBoard, BorderLayout.CENTER);
       f.setSize(gameBoard.getDisplayWidth(), gameBoard.getDisplayHeight()+52);
       f.setResizable(false);
       f.getContentPane().add(controlPane, BorderLayout.PAGE_END);
       f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       f.setVisible(true);
       
    }  
    
    public static void disableGUI(JButton except){
          b_Go.setEnabled(false);
          b_Step.setEnabled(false);
          b_Stop.setEnabled(false);
          b_Random.setEnabled(false);
          b_Clear.setEnabled(false);
          b_Cannon.setEnabled(false);
          s_FPS.setEnabled(false);
          except.setEnabled(true);
    }
       
    public static void enableGUI(){
          b_Go.setEnabled(true);
          b_Step.setEnabled(true);
          b_Stop.setEnabled(true);
          b_Random.setEnabled(true);
          b_Clear.setEnabled(true);
          b_Cannon.setEnabled(true);
          s_FPS.setEnabled(true);
    }
    
}
