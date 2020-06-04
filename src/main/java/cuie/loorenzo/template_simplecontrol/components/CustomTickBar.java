package cuie.loorenzo.template_simplecontrol.components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class CustomTickBar extends Region {

    private Group ticks;
    private Rectangle bar;
    private Rectangle emptyBar;
    private double width;
    private double height;

    private final DoubleProperty barVal = new SimpleDoubleProperty();

    public CustomTickBar(double width, double height, double val) {
        this.width = width;
        this.height = height;
        this.barVal.set(val);
        initializeParts();
        initializeDrawingPane();
        initializeAnimations();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBindings();
    }


    private void initializeParts() {
        bar = new Rectangle(getBarVal(), this.height);
        bar.getStyleClass().add("bar");
        emptyBar = new Rectangle(this.width, this.height);
        emptyBar.getStyleClass().add("empty-bar");
        ticks = createTicks();
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

    private void initializeDrawingPane() {
    }

    private void initializeAnimations() {
    }

    private void layoutParts() {
        this.getChildren().addAll(emptyBar, bar, ticks);
    }

    private void setupEventHandlers() {
    }

    private void setupValueChangeListeners() {
        barVal.addListener((observable, oldValue, newValue) -> {

        });
    }

    private void setupBindings() {
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
