package cuie.loorenzo.template_simplecontrol.demo;

import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import cuie.loorenzo.template_simplecontrol.SimpleControl;
import javafx.util.converter.NumberStringConverter;

import java.util.Locale;

public class DemoPane extends BorderPane {

    private final PresentationModel pm;

    // declare the custom control
    private SimpleControl cc;

    // all controls
    private Slider      slider;

    public DemoPane(PresentationModel pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        setupBindings();
    }

    private void initializeControls() {
        setPadding(new Insets(10));

        cc = new SimpleControl();

        slider = new Slider();
        slider.setShowTickLabels(true);
    }

    private void layoutControls() {
        VBox controlPane = new VBox(new Label("Range Properties"),slider);
        controlPane.setPadding(new Insets(0, 50, 0, 50));
        controlPane.setSpacing(10);

        setCenter(cc);
        setRight(controlPane);
    }

    private void setupBindings() {
        slider.valueProperty().bindBidirectional(pm.pmValueProperty());
        cc.valueProperty().bindBidirectional(pm.pmValueProperty());
        cc.getBar1().barValProperty().bindBidirectional(pm.pmValueProperty());

    }

}
