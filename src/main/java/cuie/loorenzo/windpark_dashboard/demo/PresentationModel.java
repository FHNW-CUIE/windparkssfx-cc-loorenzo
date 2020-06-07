package cuie.loorenzo.windpark_dashboard.demo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class PresentationModel {
    private final DoubleProperty pmValue = new SimpleDoubleProperty();

    public double getPmValue() {
        return this.pmValue.get();
    }

    public DoubleProperty pmValueProperty() {
        return this.pmValue;
    }

    public void setPmValue(double pmValue) {
        this.pmValue.set(pmValue);
    }

}
