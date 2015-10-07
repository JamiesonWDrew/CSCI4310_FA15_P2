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


public class TicTacToeUIInfinite extends JFrame{

    //Layout Panel to Hold MAP(displayes full bord) & PANEL(displays 5 by 5 cells) 
    JPanel map = new JPanel();
    JPanel panel = new JPanel();

    
    
    
    public TicTacToeUIInfinite(){

        //map.setPreferredSize(new Dimension(layout.getWidth()/3, layout.getWidth()/3));
        //panel.setPreferredSize(new Dimension(layout.getWidth()/3, layout.getWidth()/3));
        map.setBackground(Color.RED);
        panel.setBackground(Color.BLUE);
        
        add(map, BorderLayout.EAST );
        add(panel, BorderLayout.CENTER);
        //add(layout, BorderLayout.NORTH);
        //pack();
        //System.out.println(layout.getWidth());
        //System.out.println(map.getWidth());
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        TicTacToeUIInfinite frame = new TicTacToeUIInfinite();
        
        frame.setTitle("Test Map Panel");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        // TODO code application logic here
    }
    
}
