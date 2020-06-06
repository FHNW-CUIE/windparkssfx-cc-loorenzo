package cuie.loorenzo.template_simplecontrol.components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class PowerDisplay extends Region {

    private final double ICON_SQUARE_SITE = 50;

    private VBox vBox;
    private HBox hBox;
    private Pane power;
    private Rectangle iconBackground;
    private ImageView powerIcon;
    private Label powerLabel, powerVal;

    private final DoubleProperty powerValue = new SimpleDoubleProperty();
    private final double maxPowerValue;

    public PowerDisplay(double powerVal, double maxPowerVal) {
        this.powerValue.set(powerVal);
        this.maxPowerValue = maxPowerVal;
        initializeParts();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeParts() {
        this.power = new Pane();
        this.iconBackground = new Rectangle(this.ICON_SQUARE_SITE, getBackgroundHeight());
        this.powerIcon = new ImageView(new Image("images/flash.png"));
        this.powerLabel = new Label("Leistung (kW): ");
        this.powerVal = new Label("4000");
        this.vBox = new VBox(this.powerLabel, this.powerVal);
        this.hBox = new HBox(this.power, this.vBox);
    }

    private void layoutParts() {
        this.iconBackground.getStyleClass().add("power-background");
        this.powerIcon.getStyleClass().add("power-icon");
        this.powerIcon.setFitHeight(50);
        this.powerIcon.setFitWidth(50);
        this.iconBackground.setScaleX(1);
        this.iconBackground.setScaleY(-1);
        this.vBox.setSpacing(10);
        this.hBox.setSpacing(10);
        this.power.getChildren().addAll(this.iconBackground, this.powerIcon);
        getChildren().addAll(this.hBox);
    }

    private double getBackgroundHeight() {
        return (this.ICON_SQUARE_SITE / this.maxPowerValue) * this.powerValue.get();
    }

    private void setupEventHandlers() {
    }

    private void setupValueChangeListeners() {
        this.powerValue.addListener((observable, oldValue, newValue) -> {
            this.iconBackground.setHeight(getBackgroundHeight());
        });
    }

    private void setupBindings() {
        this.powerVal.textProperty().bind(this.powerValue.asString("%.2f"));
    }

    public double getPowerValue() {
        return this.powerValue.get();
    }

    public DoubleProperty powerValueProperty() {
        return this.powerValue;
    }

    public void setPowerValue(double powerValue) {
        this.powerValue.set(powerValue);
    }
}
