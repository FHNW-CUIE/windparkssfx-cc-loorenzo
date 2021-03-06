package cuie.loorenzo.windpark_dashboard.components;

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

    private final double ICON_SQUARE_SITE = 70;

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
        initializeSelf();
        initializeParts();
        layoutParts();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeSelf() {
        getStyleClass().add("power-display");
    }

    private void initializeParts() {
        this.power = new Pane();
        this.iconBackground = new Rectangle(this.ICON_SQUARE_SITE, getBackgroundHeight());
        this.powerIcon = new ImageView(new Image("images/flash.png"));
        this.powerLabel = new Label("Leistung (kW): ");
        this.powerVal = new Label(this.powerValue.toString());
        this.vBox = new VBox(this.powerLabel, this.powerVal);
        this.hBox = new HBox(this.power, this.vBox);
    }

    private void layoutParts() {
        this.iconBackground.getStyleClass().add("power-background");
        this.powerIcon.getStyleClass().add("power-icon");
        this.powerVal.getStyleClass().add("power-label");
        this.powerVal.getStyleClass().add("power-label");
        this.powerIcon.setFitHeight(this.ICON_SQUARE_SITE);
        this.powerIcon.setFitWidth(this.ICON_SQUARE_SITE);
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

    private void setupValueChangeListeners() {
        this.powerValue.addListener((observable, oldValue, newValue) -> {
            this.iconBackground.setHeight(getBackgroundHeight());
        });
    }

    private void setupBindings() {
        this.powerVal.textProperty().bind(this.powerValue.asString("%.2f"));
    }

    public DoubleProperty powerValueProperty() {
        return this.powerValue;
    }

}
