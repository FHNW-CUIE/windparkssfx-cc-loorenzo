package cuie.loorenzo.windpark_dashboard.demo;

import cuie.loorenzo.windpark_dashboard.DashboardControl;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class DemoPane extends BorderPane {

    private final PresentationModel pm;

    // declare the custom control
    private DashboardControl cc;

    // all controls
    private Slider slider;

    public DemoPane(PresentationModel pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        setupBindings();
    }

    private void initializeControls() {
        setPadding(new Insets(10));
        this.cc = new DashboardControl("Haldenstein", 4278, 4372, 4137, 4920, 6000, 3000, 8000);
        this.slider = new Slider();
        this.slider.setShowTickLabels(true);
    }

    private void layoutControls() {
        VBox controlPane = new VBox(new Label("Range Properties"), this.slider);
        controlPane.setPadding(new Insets(0, 50, 0, 50));
        controlPane.setSpacing(10);

        setCenter(this.cc);
        setRight(controlPane);
    }

    private void setupBindings() {
        this.slider.valueProperty().bindBidirectional(this.pm.pmValueProperty());
    }

}
