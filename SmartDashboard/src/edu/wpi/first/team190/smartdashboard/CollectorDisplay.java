package edu.wpi.first.team190.smartdashboard;

import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import edu.wpi.first.wpilibj.networking.NetworkListener;
import edu.wpi.first.wpilibj.networking.NetworkTable;
import java.awt.*;
import java.util.NoSuchElementException;

/**
 *
 * @author Mitchell
 */
public class CollectorDisplay extends Widget{
    
    public static final DataType[] TYPES = { CollectorSubsystemType.get() };

    private NetworkTable table;
    @Override
    public void init() {
        setPreferredSize(new Dimension(150, 260));
    }

    @Override
    public void propertyChanged(Property property) {
    }
    
    private NetworkListener listener = new NetworkListener() {

        @Override
        public void valueChanged(String key, Object value) {
            repaint();
        }

        @Override
        public void valueConfirmed(String key, Object value) {
        }
    };
    
    @Override
    public void setValue(Object value) {
        if (table != null) {
            table.removeListenerFromAll(listener);
        }
        table = (NetworkTable) value;
        table.addListenerToAll(listener);

        listener.valueChanged(null, null);
    }
    
    private void drawBall(Graphics g, String name, int position){
        try{
            if(table.getBoolean(name)){
                g.setColor(Color.RED);
                g.fillOval(5, 200-25*position, 50, 50);
            }
        } catch(NoSuchElementException e){
            g.setColor(Color.ORANGE);
            g.fillOval(5, 200-25*position, 50, 50);
        }
    }
    
    final static BasicStroke THICK_LINE = new BasicStroke(3.0f);
    final static BasicStroke DEFAULT_LINE = new BasicStroke();
    private void drawStage(Graphics g, String name, int position){
        int screenPos = 175-50*position;
        try{
            double speed = table.getDouble(name);
            g.setColor(Color.BLACK);
            g.drawString(Double.toString(speed), 80, screenPos);
            ((Graphics2D)g).setStroke(THICK_LINE);
            if(speed>0){
                g.setColor(Color.GREEN);
                g.drawLine(70, screenPos-20, 70, screenPos+20);
                g.drawLine(65, screenPos-15, 70, screenPos-20);
                g.drawLine(75, screenPos-15, 70, screenPos-20);
            }
            else if(speed<0){
                g.setColor(Color.RED);
                g.drawLine(70, screenPos-20, 70, screenPos+20);
                g.drawLine(65, screenPos+15, 70, screenPos+20);
                g.drawLine(75, screenPos+15, 70, screenPos+20);
            }
        } catch(NoSuchElementException e){
            g.setColor(Color.ORANGE);
            g.fillOval(60, screenPos-5, 10, 10);
        }
        ((Graphics2D)g).setStroke(DEFAULT_LINE);
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 150, 260);
        g.setColor(Color.BLACK);
        g.drawRect(0, 45, 60, 160);
        g.setColor(Color.WHITE);
        g.drawRect(5, 50, 50, 150);
        
        if(table==null){
            g.drawString("No Network Table", 5, 12);
            return;
        }
        
        drawBall(g, "Start", 0);
        drawBall(g, "PStart", 1);
        drawBall(g, "S1", 2);
        drawBall(g, "S12", 3);
        drawBall(g, "S2", 4);
        drawBall(g, "S23", 5);
        drawBall(g, "S3", 6);
        drawBall(g, "PShooter", 7);
        drawBall(g, "Shooter", 8);
        
        drawStage(g, "Stage 1", 0);
        drawStage(g, "Stage 2", 1);
        drawStage(g, "Stage 3", 2);
        
        g.setColor(Color.BLACK);
        g.drawLine(0, 100, 60, 100);
        g.drawLine(0, 150, 60, 150);
        
        try{
            g.drawString("Ball Count: "+table.getInt("Ball Count"), 70, 15);
        } catch(NoSuchElementException e){
            g.setColor(Color.ORANGE);
            g.drawString("Ball Count: ?", 70, 15);
        }
    }
    
    
    

}
