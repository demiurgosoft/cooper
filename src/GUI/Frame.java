package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;

/**
 * Clase para la generación de la interfaz.
 * V 1.0 - Implementación base.
 * V 1.1 - Mejora y método de pintado específico, eliminación de variables innecesarias.
 * V 1.2 - Creación del zoom en la interfaz a petición de Vicente (Machaca nº1)
 * 
 * @author Alberto Meana, Alba Ríos
 */
public class Frame extends JPanel {

    private static int NUM_ROWS;
    private static int NUM_COLUMNS;
    private static int rectWidth;
    private static int rectHeight;
    
    private static Color[][] grid;
    private boolean init = false;
    
    private static int SCALE = 5;

    /**
     * 
     * Constructor por defecto de la interfaz.
     * Inicializa los parámetros y el grid a blanco.
     * 
     * @author Alberto Meana, Alba Ríos
     */
    public Frame( int rows, int columns ){
        
        this.NUM_ROWS = rows;
        this.NUM_COLUMNS = columns; 
        
        int preferredWidth = this.NUM_COLUMNS * this.SCALE;
        int preferredHeight = this.NUM_ROWS * this.SCALE;
        
        this.grid = new Color[rows][columns];
        
        for (int i = 0; i < this.NUM_ROWS; i++) {
            
            for (int j = 0; j < this.NUM_COLUMNS; j++) {
        
                this.grid[i][j] = Color.WHITE;
                
            }
        }
        
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        
    }
    
    /**
     * Sobrecarga de la función de pintado del grid de la interfaz en función de
     * lo que valga en el grid.
     * 
     * @param g Componente del JFrame
     * @author Alberto Meana
     */
    @Override
    public void paintComponent( Graphics g ) {
        
        super.paintComponent( g );
        
        // Borrado y config del grid
        if( !this.init ){
            
            g.clearRect(0, 0, getWidth(), getHeight());
            this.init = true;
        
        }
        
        this.rectWidth = getWidth() / this.NUM_COLUMNS;
        this.rectHeight = getHeight() / this.NUM_ROWS;
        
        // Pintado del grid segun lo que ponga en "grid"
        for (int i = 0; i < NUM_ROWS; i++) {
            
            for (int j = 0; j < NUM_COLUMNS; j++) {
             
                int x = i * this.rectWidth;
                int y = j * this.rectHeight;
                g.setColor( this.grid[i][j] ); // <- especificación del color.
                g.fillRect( x, y, this.rectWidth, this.rectHeight );
                
            }
        }
        
        this.addMouseListener( new MyMouseListener() );
        
    }
    
    /**
     * Método que colorea una casilla del grid.
     * 
     * @param coordinateX Coordenada x del grid
     * @param coordinateY Coordenada y del grid
     * @param color Color al que pintar la casilla
     * @author Alberto Meana
     */
    public void specificPaint( int coordinateX, int coordinateY, Color color ){
        
        this.grid[coordinateX][coordinateY] = color;
        
        repaint();
        
    }
    
    /**
     * Clase que implementa el zoom.
     * Modifica el factor de escala del JPanel y la ventana.
     * V1.0 de momento funciona on click.
     * 
     * @author Alberto Meana
     */
    private class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked( MouseEvent me ) {
            
            SCALE = (( SCALE += 1 ) % 5) +1 ;
            
            int preferredWidth = NUM_COLUMNS * SCALE;
            int preferredHeight = NUM_ROWS * SCALE;
  
            
            setSize( new Dimension ( preferredWidth, preferredHeight ));
            
            rectWidth = getWidth() / (NUM_COLUMNS * SCALE);
            rectHeight = getHeight() / (NUM_ROWS * SCALE);
            
            specificPaint( 55, 55, Color.YELLOW );
            
            
            
            repaint();
            
        }   
    }   
}
