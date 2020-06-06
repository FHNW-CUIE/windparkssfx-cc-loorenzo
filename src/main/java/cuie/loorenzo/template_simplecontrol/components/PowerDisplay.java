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

    private double ICON_SQUARE_SITE = 50;

    private VBox vBox;
    private HBox hBox;
    private Pane power;
    private Rectangle iconBackground;
    private ImageView powerIcon;
    private Label powerLabel, powerVal;

    private DoubleProperty powerValue = new SimpleDoubleProperty();
    private double maxPowerValue;

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
        power = new Pane();
        iconBackground = new Rectangle(ICON_SQUARE_SITE,getBackgroundHeight());
        powerIcon = new ImageView(new Image("images/flash.png"));
        powerLabel = new Label("Leistung (kW): ");
        powerVal = new Label("4000");
        vBox = new VBox(powerLabel, powerVal);
        hBox = new HBox(power, vBox);
    }

    private void layoutParts() {
        iconBackground.getStyleClass().add("power-background");
        powerIcon.getStyleClass().add("power-icon");
        powerIcon.setFitHeight(50);
        powerIcon.setFitWidth(50);
        iconBackground.setScaleX(1);
        iconBackground.setScaleY(-1);
        vBox.setSpacing(10);
        hBox.setSpacing(10);
        power.getChildren().addAll(iconBackground, powerIcon);
        getChildren().addAll(hBox);
    }

    private double getBackgroundHeight(){
        return (ICON_SQUARE_SITE/maxPowerValue) * powerValue.get();
    }

    private void setupEventHandlers() {
    }

    private void setupValueChangeListeners() {
        powerValue.addListener((observable, oldValue, newValue) -> {
            iconBackground.setHeight(getBackgroundHeight());
        });
    }

    private void setupBindings() {
        powerVal.textProperty().bind(this.powerValue.asString());
    }

    public double getPowerValue() {
        return powerValue.get();
    }

    public DoubleProperty powerValueProperty() {
        return powerValue;
    }

    public void setPowerValue(double powerValue) {
        this.powerValue.set(powerValue);
    }
}
