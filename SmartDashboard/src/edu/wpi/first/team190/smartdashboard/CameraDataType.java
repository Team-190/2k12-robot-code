package edu.wpi.first.team190.smartdashboard;

import edu.wpi.first.smartdashboard.types.NamedDataType;
import edu.wpi.first.smartdashboard.types.named.SubsystemType;

/**
 *
 * @author Mitchell
 */
public class CameraDataType extends NamedDataType {

    public static final String LABEL = "Camera";

    private CameraDataType() {
        super(LABEL);
    }

    public static NamedDataType get() {
        if (NamedDataType.get(LABEL) != null) {
            return NamedDataType.get(LABEL);
        } else {
            return new CameraDataType();
        }
    }
}
