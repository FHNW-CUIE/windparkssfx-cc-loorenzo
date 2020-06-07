package cuie.loorenzo.windpark_dashboard.components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.converter.NumberStringConverter;

public class ProductionBar extends Region {

    private final double BAR_WIDTH = 100;
    private final double BAR_HEIGHT = 20;

    private Group ticks;
    private Rectangle bar;
    private Rectangle emptyBar;
    private Rectangle transparentBar;
    private TextField prodVal;
    private Label year;
    private HBox row;
    private Pane tickBar;

    private final double maxVal;
    private final DoubleProperty barVal = new SimpleDoubleProperty();

    public ProductionBar(String yearLabel, double currentVal, double maxVal) {
        this.barVal.set(currentVal);
        this.maxVal = maxVal;
        initializeSelf();
        initializeParts(yearLabel);
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeSelf() {
        getStyleClass().add("production-bar");
    }

    private void initializeParts(String yearLabel) {
        this.bar = new Rectangle(getBarWidth(), this.BAR_HEIGHT);
        this.bar.getStyleClass().add("bar");
        this.emptyBar = new Rectangle(this.BAR_WIDTH, this.BAR_HEIGHT);
        this.emptyBar.getStyleClass().add("empty-bar");
        this.ticks = createTicks();
        this.transparentBar = new Rectangle(this.BAR_WIDTH, this.BAR_HEIGHT);
        this.transparentBar.getStyleClass().add("transparent-bar");
        this.year = new Label(yearLabel);
        this.prodVal = new TextField(this.barVal.toString());
        this.tickBar = new Pane();
        this.row = new HBox(this.year, this.tickBar, this.prodVal);
        this.row.setSpacing(10);
    }

    private Group createTicks() {
        Group ticks = new Group();
        double nrLines = this.BAR_WIDTH / 5;
        for (int i = 0; i < nrLines; i++) {
            double x = this.bar.getX() + (i * 5);
            Line line = new Line(x, this.bar.getY(), x, this.bar.getY() + this.bar.getHeight());
            line.getStyleClass().add("tick");
            ticks.getChildren().add(line);
        }
        return ticks;
    }

    private void layoutParts() {
        this.tickBar.getChildren().addAll(this.emptyBar, this.bar, this.ticks, this.transparentBar);
        this.getChildren().addAll(this.row);
    }

    private void setupEventHandlers() {
        this.transparentBar.setOnMouseDragged((MouseEvent e) -> {
            if (e.getX() > this.BAR_WIDTH) {
                this.bar.setWidth(this.BAR_WIDTH);
                this.setBarVal(this.maxVal);
            } else if (e.getX() < 0) {
                this.bar.setWidth(0);
                this.setBarVal(0);
            } else {
                this.bar.setWidth(e.getX());
                this.setBarVal((this.maxVal / this.BAR_WIDTH) * e.getX());
            }
        });
    }

    private void setupValueChangeListeners() {
        this.barVal.addListener((observable, oldValue, newValue) -> {
            this.bar.setWidth(getBarWidth());
        });
    }

    private void setupBindings() {
        this.prodVal.textProperty().bindBidirectional(this.barVal, new NumberStringConverter());
    }

    public double getBarWidth() {
        if (getBarVal() > this.maxVal) {
            return this.BAR_WIDTH;
        }
        return (this.BAR_WIDTH / this.maxVal) * getBarVal();
    }

    public double getBarVal() {
        return this.barVal.get();
    }

    public DoubleProperty barValProperty() {
        return this.barVal;
    }

    public void setBarVal(double barVal) {
        this.barVal.set(barVal);
    }
}
