import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.text.Position;

/**
 * Panel in which drawing occurs.
 * 
 * @author CS2334. Modified by: ?????
 * @version 2018-04-16
 */

public class DrawPanel extends JPanel
{
    /**
     * Makes Eclipse happy
     */
    private static final long serialVersionUID = 1L;
    /** The list of shapes to be drawn. */
    private ArrayList<Shape> shapeList = new ArrayList<Shape>();
    /** A temporary shape that is also drawn */
    private Shape tempShape;
    /** Index of chosen shape being edited */
    private int shapeIndex;

    /** Coordinates for button-down event. */
    private int x0;
    private int y0;
    /** Coordinates for button-up event. */
    private int x1;
    private int y1;
    /** Indicate if a shape is currently being drawn. */
    private boolean drawingFlag;

    /** Reference to the frame in which the panel is a member. */
    private DrawFrame frame;

    //////////////////////////////////////////////////////////////
    /**
     * Class defines the behavior inside of the drawing panel in response to
     * mouse events
     * 
     */
    public class MouseHandler extends MouseAdapter
    {
        /**
         * Respond to a button-down event.
         * 
         * @param e Mouse event
         */
        @Override
        public void mousePressed(MouseEvent e)
        {
            // Remember this starting coordinate (look at documentation for mouse events)
            // You can store these in x0 and y0
            // TODO
        	Point initialMousePos = new Point(e.getPoint());
        	x0 = initialMousePos.x;
        	y0 = initialMousePos.y;

            // are we in edit mode?
            if (frame.isEditing())
            {
                // create point where the mouse was clicked
                // TODO
            	
                // find which shape was clicked
            	
            	
                // loop through shapes in stack fashion, LIFO
                // TODO
            	int index = 0;
            	for (Shape shape : shapeList)
                {	
                    if (shape.contains(initialMousePos))
                    {
                        // if the shape contains the point, set the shapeIndex
                        // to be the index in the shapeList
                    	shapeIndex = index;

                        // TODO: find if the shape is filled
                        boolean isFilled = shape.isFilled(); 
                        // TODO: set fillBox to match the status of the shape
                       frame.controlPanel.fillBox.setSelected(isFilled);
                       
                        // TODO: get color of the shape
                        Color color = shape.getColor();
                        // TODO: set the color of the frame to match the shape's color
                        frame.controlPanel.colorChooser.setBackground(color);
                        // TODO: break out of the for loop
                      
                        break;
                        
                    }
                    index++;
                }
            }
            else if (frame.isDeleting()) // are we in delete mode?
            {
                // TODO: create point where the mouse was clicked
                
                // find which shape was clicked
                // loop through shapes in stack fashion, LIFO
            	int index = 0;
                for (Shape shape : shapeList)
                {
                    if (shape.contains(initialMousePos))
                    {
                        // If the shape contains the point, prompt the user for
                        // a confirmation to delete
                        int ret = JOptionPane.showConfirmDialog(
                                        frame.getControlPanel()
                                                        .getActionPanel(),
                                        "Delete chosen shape?", "",
                                        JOptionPane.YES_NO_OPTION);
                        // TODO: Check answer, remove shape if yes 
                        if (ret == 0) {
                        	removeShape(index);
                        }
                        // You may need to review JOptionPane documentation

                        // TODO: break out of for loop
                        break;
                    }
                    index ++;
                }
            }
            else // we're drawing a shape
            {
                // TODO: Indicate that drawing of a shape has begun (look at what flags may be set)
            	drawingFlag = true;
            	
            	
            }
        }

        /**
         * Respond to a button-up event.
         * 
         * @param e Mouse event
         */
        @Override
        public void mouseReleased(MouseEvent e)
        {Point finalMousePos = new Point(e.getPoint());
        	x1 = finalMousePos.x;
        	y1 = finalMousePos.y;
            // Are we currently drawing?
            if (drawingFlag)
            {
                // Yes - we are currently drawing

                // Coordinates of the cursor (x0/y0 are already being used, what should you use?)
                // TODO
            	

                // Indicate that we are no longer drawing
                // TODO
            	drawingFlag = false;
                // We no longer need a temporary shape (set to null)
                // TODO
            	tempShape = null;
                // Create the shape given the current state
                // TODO
            	
                // Add the shape to the panel list if the shape exists
            	addShape(createShape());
                // TODO
                
                //repaint
                repaint();
            }
        }

        /**
         * Address a mouse movement event (movement only when the button is
         * down)
         * 
         * @param e Mouse event
         */
        @Override
        public void mouseDragged(MouseEvent e)
        {
            // TODO just comments
            // Are we currently drawing a shape?
            if (drawingFlag)
            {
                // Yes
                // Note the current coordinates
                // TODO
            	x1 = e.getPoint().x;
            	y1 = e.getPoint().y;

                // Create a temporary shape (look at what variables we have)
                // TODO
            	tempShape = createShape();

                // repaint
                repaint();
            }
        }

        /**
         * Given the current state of the control panel and the selected points
         * in the draw panel, create a new shape.
         * 
         * @return The newly created shape
         */
        private Shape createShape()
        {
            // Identify the width and height:
            int xdist = x1 - x0; // Distance from start mouse point to current point
            int ydist = y1 - y0;
            int width = xdist*2; // Width/height are twice the distance from ovals, rectangles, and diamonds.
            int height = ydist*2;
            Point center = new Point(x0,y0);
            // Create a new object, depending on what is selected
            // TODO give them diamond, comments else
            
            if (frame.isOval())
            {
                Shape newShape = new Oval(center, width, height, frame.getColor(), frame.isFilled());
                return newShape;
                
            }
            else if (frame.isRectangle())
            {
                // TODO: create and return a Rectangle
            	return  new Rectangle(center,width,height,frame.getColor(),frame.isFilled());
                
            }
            else if (frame.isTriangle())
            {
                // TODO: create and return a Triangle
            	return new RightTriangle(center,width,height,frame.getColor(),frame.isFilled());
                
            }
            else if (frame.isDiamond())
            {
                // TODO: create and return diamond
            	return new Diamond(center,width,height,frame.getColor(),frame.isFilled());
            	
                
            }
            // Should not get here, but be safe
            return null;
        }
    }

    //////////////////////////////////////////////////////////////
    /**
     * Constructor: Create the draw panel
     * 
     * @param frame The Frame in which the panel is embedded
     */
    public DrawPanel(DrawFrame frame)
    {
        
        this.frame = frame;

        // Border for the frame
        Border blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);

        // Initially, we are currently not editing a shape
        drawingFlag = false;

        // Set the panel to be focusable: we will receive mouse events
        this.setFocusable(true);

        // Create a handler for the mouse events
        MouseHandler listener = new MouseHandler();

        // Listen for button events
        this.addMouseListener(listener);

        // Also listen for mouse movement events
        this.addMouseMotionListener(listener);
    }

    /**
     * This method adds shapes to the set of shapes.
     * 
     * @param shape The shape to be added to the list of shapes.
     */
    public void addShape(Shape shape)
    {
        shapeList.add(shape);
    }

    /**
     * This method draws the shapes in the set of shapes.
     * 
     * @param g A Graphics object
     */
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        
        // TODO: Draw each shape on the list
        for (int i = 0; i < shapeList.size(); ++i) {
        	shapeList.get(i).draw(g2d);
        	
        }
        if (tempShape != null) {
        	tempShape.draw(g2d);
        }
        // TODO: If there is a temporary shape, then draw it, too
        
    }

    /**
     * Return the list of shapes
     * 
     * @return The list of shapes
     */
    protected ArrayList<Shape> getShapeList()
    {
        return shapeList;
    }

    /**
     * Set the list of shapes to a new set
     * 
     * @param shapeList to copy into our panel
     */
    protected void setShapeList(ArrayList<Shape> shapeList)
    {
        if (shapeList != null)
        {
            this.shapeList = shapeList;
            repaint();
        }
    }

    /**
     * Remove all shapes from the list.
     */
    protected void clearShapeList()
    {
        shapeList.clear();
        repaint();
    }

    /**
     * Removed the selected shape from the shape list.
     * 
     * @param index The index of the selected shape.
     */
    protected void removeShape(int index)
    {
        shapeList.remove(index);
        repaint();
    }

    /**
     * Getter for shape index.
     * 
     * @return Index of the shape chosen by the user.
     */
    public int getShapeIndex()
    {
        return shapeIndex;
    }

}
