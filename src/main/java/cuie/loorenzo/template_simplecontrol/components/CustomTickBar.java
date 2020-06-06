package cuie.loorenzo.template_simplecontrol.components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class CustomTickBar extends Region {

    private Group ticks;
    private Rectangle bar;
    private Rectangle emptyBar;
    private Rectangle transparentBar;
    private double width;
    private double height;
    private double maxVal;

    private final DoubleProperty barVal = new SimpleDoubleProperty();

    public CustomTickBar(double width, double height, double val, double maxVal) {
        this.width = width;
        this.height = height;
        this.barVal.set(val);
        this.maxVal = maxVal;
        initializeParts();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeParts() {
        bar = new Rectangle(getBarWidth(), this.height);
        bar.getStyleClass().add("bar");
        emptyBar = new Rectangle(this.width, this.height);
        emptyBar.getStyleClass().add("empty-bar");
        ticks = createTicks();
        transparentBar = new Rectangle(this.width, this.height);
        transparentBar.getStyleClass().add("transparent-bar");
    }

    private Group createTicks() {
        Group ticks = new Group();
        double a = this.width / 5;
        for (int i = 0; i < a; i++) {
            double x = bar.getX() + (i * 5);
            Line l = new Line(x, bar.getY(), x, bar.getY() + bar.getHeight());
            l.getStyleClass().add("tick");
            ticks.getChildren().add(l);
        }
        return ticks;
    }

    private void layoutParts() {
        this.getChildren().addAll(emptyBar, bar, ticks, transparentBar);
    }

    private void setupEventHandlers() {
        transparentBar.setOnMouseDragged((MouseEvent e) -> {
            if (e.getX() > this.width) {
                this.bar.setWidth(this.width);
                this.setBarVal(this.maxVal);
            } else if (e.getX() < 0) {
                this.bar.setWidth(0);
                this.setBarVal(0);
            } else {
                this.bar.setWidth(e.getX());
                this.setBarVal((this.maxVal / this.width) * e.getX());
            }
        });
    }

    private void setupValueChangeListeners() {
        barVal.addListener((observable, oldValue, newValue) -> {
            this.bar.setWidth(getBarWidth());
        });
    }

    private void setupBindings() {
    }

    public double getBarWidth() {
        if (getBarVal() > maxVal) {
            return this.width;
        }
        return (this.width / maxVal) * getBarVal();
    }

    public double getBarVal() {
        return barVal.get();
    }

    public DoubleProperty barValProperty() {
        return barVal;
    }

    public void setBarVal(double barVal) {
        this.barVal.set(barVal);
    }
}
