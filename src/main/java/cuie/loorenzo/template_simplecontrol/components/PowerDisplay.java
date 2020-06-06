package cuie.loorenzo.template_simplecontrol.components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class PowerDisplay extends Region {

    private VBox vBox;
    private HBox hBox;

    private Pane power;
    private Rectangle iconBackground;
    private ImageView powerIcon;

    private Label powerLabel, powerVal;

    public PowerDisplay() {
        initializeParts();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeParts() {
        power = new Pane();
        iconBackground = new Rectangle(50,20);
        powerIcon = new ImageView(new Image("images/flash.png"));
        powerLabel = new Label("Leistung (kW): ");
        powerVal = new Label("4000");
        vBox = new VBox(powerLabel, powerVal);
        hBox = new HBox(vBox, power);
    }


    private void layoutParts() {
        iconBackground.getStyleClass().add("power-background");
        powerIcon.getStyleClass().add("power-icon");
        powerIcon.setFitHeight(50);
        powerIcon.setFitWidth(50);
        iconBackground.setScaleX(1);
        iconBackground.setScaleY(-1);
        vBox.setSpacing(10);
        power.getChildren().addAll(iconBackground, powerIcon);
        getChildren().addAll(hBox);
    }

    private void setupEventHandlers() {
    }

    private void setupValueChangeListeners() {
    }

    private void setupBindings() {
    }

}
