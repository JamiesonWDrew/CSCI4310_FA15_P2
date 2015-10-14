/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeuiinfinite;

//Imports
import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;


public class TicTacToeUIInfinite extends JFrame{
    //Width = Columns, Height = Rows
    private int width = 200;
    private int height = 200;
    
    private Cell[][] cells;
    private Cell[][] cellsDisplay;
    
    JPanel map;
    JPanel panel;
    
    //Labels for Statsu of the game and Row/Col display for map. Panel to organize map&panel display on frame
    JLabel jlbStatus;
    JLabel jlbRowColMap;
    JPanel organize;
    

    
    
    
    public TicTacToeUIInfinite(){ 
        
    //Array of all Cells information
    cells = new Cell[height][width];
    //Array 5by5 to display content of cells. 
    cellsDisplay = new Cell[5][5];    
    

    //Layout Panel to Hold MAP(displayes full bord) & PANEL(displays 5 by 5 cells) 
    map = new JPanel();
    panel = new JPanel();
    
    //Labels for Statsu of the game and Row/Col display for map. Panel to organize map&panel display on frame
    jlbStatus = new JLabel("First Plyaer's turn");
    jlbRowColMap = new JLabel("Row:    Column: ");
    organize = new JPanel();
        
        //Set Layout for Organize and Panel in order to arange map and panel(cells 5 by 5)
        organize.setLayout(new GridLayout(1, 2, 3, 0));
        panel.setLayout(new GridLayout(5, 5, 0, 0));
        
        //??????i = 1 and i < 6 causes outoffbound?
        //rC = row in cells, cC = column in Cells, the initial start on the map
        //Center is 99, 99 so need to start at 97(97 - 101)
        int rC = 97;
        int cC = 97;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                panel.add(cellsDisplay[i][j] = new Cell(rC, cC));   
                cC++;
            }
            rC++;
            cC = 97;
        }
        
        //cells Array
        // Need to look this up (for [Cell[] cell : cells)]????
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++)
                cells[i][j] = new Cell(i, j, " ");
        }
            
        
        
                
        //        
        //map.setPreferredSize(new Dimension(layout.getWidth()/3, layout.getWidth()/3));
        //panel.setPreferredSize(new Dimension(layout.getWidth()/3, layout.getWidth()/3));
        
        //Set Map color in order to see better Black/White
        map.setBackground(Color.orange);
        
        //Border for map and panel
        map.setBorder(new LineBorder(Color.BLACK, 1));
        panel.setBorder(new LineBorder(Color.BLACK, 1));
        //panel.setBackground(Color.BLUE);
        //Adds Listeners. 1 for Mouse click. 2 for mouse Motion to see Map coordinates(Row/Column)
        map.addMouseListener(new CellsLocation());
        map.addMouseMotionListener(new CellsLocation());
        
        
        
        //Add map/panel to organize, and display it on JFrame
        organize.add(map);
        organize.add(panel);
        add(organize, BorderLayout.CENTER);
        add(jlbStatus, BorderLayout.SOUTH);
        add(jlbRowColMap, BorderLayout.NORTH);
        
    }
    
    
    
    
    public class Cell extends JPanel {
                
        private String token = " ";
        int rowIndex;
        int colIndex;
        
        //cellsDisplay constructor
        public Cell(int row, int col){
            setBorder(new LineBorder(Color.black, 1));
            addMouseListener(new MyMouseListener());
            //Each cell's row/column
            rowIndex = row;
            colIndex = col;
            
            System.out.println(rowIndex + ", " + colIndex);
        }
        
        //cells construcor. Passing token here since Cell(int, int) already exists ^
        public Cell(int row, int col, String tok){
            int cellsRow = row;
            int cellsCol = col;
            String cellsToken = tok;
            
            //Testing display
            setBorder(new LineBorder(Color.black, 1));
            //Testing cells content
            //System.out.print(cellsRow + ", " + cellsCol + ", " + cellsToken +"||||");
            
        }
        
        
        
        
        //Draw X/O
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            
            switch (token) {
                case "X":
                    g.drawLine(10, 10, getWidth() - 10, getHeight() - 10);
                    g.drawLine(getWidth() - 10, 10, 10, getHeight() - 10);
                    
                    break;
                case "O":
                    g.drawOval(10, 10, getWidth() - 20, getHeight() - 20);
                    break;
                case " ":
                    super.paintComponent(g);
            }
            
            
        }
        
        
        
        //Mouse Listener to perform actions depending on turns. 
        class MyMouseListener extends MouseAdapter {
            
        @Override
        public void mouseClicked(MouseEvent e){
            
            //Test: check if rowIndex/colIndex showing right conect
            System.out.println("" + (rowIndex) + ", " + (colIndex));
            token = "X";
            repaint();
            //Set token in cells to players turn
            transferDataCells(rowIndex, colIndex, token);
            
            
            //System.out.println();
            //System.out.println("1" + token + "1");
        }
        }
        
        //Set Token for to player's turn
        public void setToken(String tok){
            token = tok;
        }
        //Get Token for methods
        public String getToken(){
            return token;
        }
        
        //Change displayCells content to match where map was clicked
        public void changeCells(int rDisplay, int cDisplay, int rCell, int cCell, String t){
            //Change Row/Column/Token to adjust to map
            cellsDisplay[rDisplay][cDisplay].rowIndex = rCell;
            cellsDisplay[rDisplay][cDisplay].colIndex = cCell;
            cellsDisplay[rDisplay][cDisplay].token = t;
            cellsDisplay[rDisplay][cDisplay].repaint();
                    
            //Test Display
            //System.out.println(cellsDisplay[rDisplay][cDisplay].rowIndex + ", " + cellsDisplay[rDisplay][cDisplay].colIndex);
            
        }
        
        
        //Method to transfor content from cellsDisplay to cellsto be used by the map for future display
        public void transferDataCells(int rInd, int cInd, String t){
            
            cells[rInd][cInd].token = t;
            System.out.println(cells[rInd][cInd].token);
        }
        
        //Paint dots on map. White = O, Black = X
        public void paintOnMap(String t, int r, int c){
            String tok = t;
            
        }
    }
    
    class CellsLocation extends MouseAdapter{
        
        @Override
        public void mouseClicked(MouseEvent e){
            //Testing to see if display right Width/Height and coordinates for MAP Panel
            System.out.println("W = " + map.getWidth() + ", H = " + map.getHeight());
            System.out.println(e.getX() + ", " + e.getY());
            //Have to parse Height/Width to Double or Small Int X/Y coordinates / Big Int Widht/Hight = 0
            //Times by 200 to get it in perspective of an Array 200 by 200
            //Then turn back to int so it will have no decimals, simply as row/col number
            int r = (int)(e.getY()/(double)map.getHeight()*200);
            int c = (int)(e.getX()/(double)map.getWidth()*200);
            System.out.println(r + ", " + c);
            
           
            
            
            r = r - 3;
            c = c - 3;
            
            //Row/Column - 3 Cells = Min and +2 Max
            //If both Row/Column < 0, then set them both to 0 on Map(min possible start)
            //5 cells (0, 1, 2, 3, 4)
            //If both Row/Column > 195, then set them both to 195 on Map(max possible start)
            //5 cells (195, 196, 197, 198, 199)
            if(r > 195)
                r = 195;
            if(c > 195)
                c = 195;
            if(r < 0)
                r = 0;
            if(c < 0)
                c = 0;
            
            //Send Row and Column to adjMap method
            adjMap(r, c);
        }
        
        //MotionListener to get Map coordinates.
        @Override
        public void mouseMoved(MouseEvent e){
            //Get it to display as Row and Column
            int r = (int)(e.getY()/(double)map.getHeight()*200);
            int c = (int)(e.getX()/(double)map.getWidth()*200);
            //jlbRowColMap .setText("Row: " + e.getY() + " Column: " + e.getX());
            System.out.println(map.getWidth() + ", " + map.getHeight());
            jlbRowColMap .setText("Row: " + r + " Column: " + c);
        }
        
        //Takes starting Row and starting Column from CellsLocation.MouseClicked and 
        public void adjMap(int r, int c){
            //IndexOutOfBounds when  Row/Col < 3 || > 195, but is resolved in mouseClicked();
            //Reset Column for the loop
            int resetC = c;
            try{
                System.out.println("THIS ROW/COL: " + r + ", " + c);
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < 5; j++){
                        //For testing repaint();
                        //cells[r][c].token = "O";
                        cells[r][c].changeCells(i, j, r, c, cells[r][c].token);
                        c++;
                    }
                    r++;
                    c = resetC;
                }
            }catch(IndexOutOfBoundsException ex){
                System.err.println("IndexOutOfBounds: " + ex.getMessage());
            }
        }
        
        //Realized we need to exend JPanel in order to use Graphics
        //Unable to figure out how to draw from graphics component in Cell onto Map Panel
        //This class is used to simply draw Black Dot = X, White Dot = O
        class DrawMap extends JPanel{
            String tok;
            public DrawMap(String t){
                
            }
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                
                switch(tok){
                    case "X":
                        g.drawLine(100, 100, 100, 100);
                }
            
                
            }
        }
    
   
    }
        
        
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        TicTacToeUIInfinite frame = new TicTacToeUIInfinite();
        
        frame.setTitle("Test Map Panel");
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        // TODO code application logic here
    }
    
}