package cuie.loorenzo.windpark_dashboard.demo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class PresentationModel {

    private final DoubleProperty prod2015 = new SimpleDoubleProperty();
    private final DoubleProperty prod2016 = new SimpleDoubleProperty();
    private final DoubleProperty prod2017 = new SimpleDoubleProperty();
    private final DoubleProperty prod2018 = new SimpleDoubleProperty();
    private final DoubleProperty power = new SimpleDoubleProperty();

    public double getProd2015() {
        return this.prod2015.get();
    }

    public DoubleProperty prod2015Property() {
        return this.prod2015;
    }

    public void setProd2015(double prod2015) {
        this.prod2015.set(prod2015);
    }

    public double getProd2016() {
        return this.prod2016.get();
    }

    public DoubleProperty prod2016Property() {
        return this.prod2016;
    }

    public void setProd2016(double prod2016) {
        this.prod2016.set(prod2016);
    }

    public double getProd2017() {
        return this.prod2017.get();
    }

    public DoubleProperty prod2017Property() {
        return this.prod2017;
    }

    public void setProd2017(double prod2017) {
        this.prod2017.set(prod2017);
    }

    public double getProd2018() {
        return this.prod2018.get();
    }

    public DoubleProperty prod2018Property() {
        return this.prod2018;
    }

    public void setProd2018(double prod2018) {
        this.prod2018.set(prod2018);
    }

    public double getPower() {
        return this.power.get();
    }

    public DoubleProperty powerProperty() {
        return this.power;
    }

    public void setPower(double power) {
        this.power.set(power);
    }
}
