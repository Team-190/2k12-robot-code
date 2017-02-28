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
public class LightsDisplay extends Widget{
    
    public static final DataType[] TYPES = { LightsSubsystemType.get() };

    private NetworkTable table;
    @Override
    public void init() {
        setPreferredSize(new Dimension(90, 100));
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
    
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        if(table==null)
            return;
        
        try{
        if(table.getBoolean("Green"))
            g.setColor(Color.GREEN);
        else
            g.setColor(Color.GRAY);
        g.fillRect(5, 5, 20, 90);
        
        if(table.getBoolean("Red"))
            g.setColor(Color.RED);
        else
            g.setColor(Color.GRAY);
        g.fillRect(35, 5, 20, 90);
        
        if(table.getBoolean("Blue"))
            g.setColor(Color.BLUE);
        else
            g.setColor(Color.GRAY);
        g.fillRect(65, 5, 20, 90);
        } catch(Exception e){
            g.setColor(Color.RED);
            g.fillRect(5, 5, 80, 20);
        }
    }
    
    
    

}
