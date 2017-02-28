package edu.wpi.first.team190.smartdashboard;

import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import edu.wpi.first.wpilibj.networking.NetworkListener;
import edu.wpi.first.wpilibj.networking.NetworkTable;
import java.awt.*;
import java.util.NoSuchElementException;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Mitchell
 */
public class CameraDataDisplay extends Widget{
    
    public static final DataType[] TYPES = { CameraDataType.get() };
    
    private JLabel cameraLabel;
    private JTextField timestampField;
    private JTextField offsetField;
    private JTextField distanceField;

    private NetworkTable table;
    @Override
    public void init() {
        setPreferredSize(new Dimension(90, 100));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 2;
        c.weightx = 0;
        c.fill = GridBagConstraints.BOTH;
        add(new JLabel("Camera Data"), c);
        c.gridy = 1;
        c.weightx = 1.0;
        add(cameraLabel = new JLabel("Not Initialized..."), c);
        c.gridwidth = 1;
        
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        add(new JLabel("Timestamp: "), c);
        c.gridx = 1;
        c.weightx = 1.0;
        add(timestampField = new JTextField(""), c);
        timestampField.setEditable(false);
        
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        add(new JLabel("Offset: "), c);
        c.gridx = 1;
        c.weightx = 1.0;
        add(offsetField = new JTextField(""), c);
        offsetField.setEditable(false);
        
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 0;
        add(new JLabel("Distance: "), c);
        c.gridx = 1;
        c.weightx = 1.0;
        add(distanceField = new JTextField(""), c);
        distanceField.setEditable(false);
        
    }

    @Override
    public void propertyChanged(Property property) {
    }
    
    private NetworkListener listener = new NetworkListener() {

        @Override
        public void valueChanged(String key, Object value) {
            if(table!=null){
                try{
                cameraLabel.setText(table.getString("Current"));
                timestampField.setText(table.getDouble("Timestamp")+"");
                offsetField.setText(table.getDouble("Offset")+"");
                distanceField.setText(table.getDouble("Distance")+"");
                } catch(NoSuchElementException e){}
            }
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

}
