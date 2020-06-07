package cuie.loorenzo.windpark_dashboard.demo;

import cuie.loorenzo.windpark_dashboard.DashboardControl;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

public class DemoPane extends BorderPane {

    private final PresentationModel pm;

    private DashboardControl dashboard;

    private Slider slider;
    private TextField prod2015, prod2016, prod2017, prod2018, power;
    private Label l1, l2, l3, l4, l5;
    private HBox h1, h2, h3, h4, h5, h6, h7;
    private VBox v1;

    public DemoPane(PresentationModel pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        setupBindings();
    }

    private void initializeControls() {
        setPadding(new Insets(10));
        this.prod2015 = new TextField();
        this.prod2016 = new TextField();
        this.prod2017 = new TextField();
        this.prod2018 = new TextField();
        this.power = new TextField();
        this.l1 = new Label("Produktion 2015 (MWh)");
        this.l2 = new Label("Produktion 2016 (MWh)");
        this.l3 = new Label("Produktion 2017 (MWh)");
        this.l4 = new Label("Produktion 2018 (MWh)");
        this.l5 = new Label("Leistung (kW)");
        this.h1 = new HBox(this.l1, this.prod2015);
        this.h2 = new HBox(this.l2, this.prod2016);
        this.h3 = new HBox(this.l3, this.prod2017);
        this.h4 = new HBox(this.l4, this.prod2018);
        this.h5 = new HBox(this.h1, this.h2);
        this.h6 = new HBox(this.h3, this.h4);
        this.h7 = new HBox(this.l5, this.power);
        this.v1 = new VBox(this.h7, this.h5, this.h6);
        this.dashboard = new DashboardControl("Haldenstein", 4278, 4372, 4137, 4920, 6000, 3000, 8000);
        this.slider = new Slider();
        this.slider.setShowTickLabels(true);
    }

    private void layoutControls() {
        this.v1.setPadding(new Insets(50, 30, 50, 30));
        this.v1.setSpacing(10);
        this.h1.setSpacing(10);
        this.h2.setSpacing(10);
        this.h3.setSpacing(10);
        this.h4.setSpacing(10);
        this.h5.setSpacing(10);
        this.h6.setSpacing(10);
        this.h7.setSpacing(65);
        setCenter(this.dashboard);
        // setBottom(this.v1);
    }

    private void setupBindings() {
        this.power.textProperty().bindBidirectional(this.dashboard.getPowerDisplay().powerValueProperty(), new NumberStringConverter());
        this.prod2015.textProperty().bindBidirectional(this.dashboard.getBar1().barValProperty(), new NumberStringConverter());
        this.prod2016.textProperty().bindBidirectional(this.dashboard.getBar2().barValProperty(), new NumberStringConverter());
        this.prod2017.textProperty().bindBidirectional(this.dashboard.getBar3().barValProperty(), new NumberStringConverter());
        this.prod2018.textProperty().bindBidirectional(this.dashboard.getBar4().barValProperty(), new NumberStringConverter());
    }

}
