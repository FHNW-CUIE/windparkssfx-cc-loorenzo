package cuie.loorenzo.windpark_dashboard;

import cuie.loorenzo.windpark_dashboard.components.PowerDisplay;
import cuie.loorenzo.windpark_dashboard.components.ProductionBar;
import cuie.loorenzo.windpark_dashboard.components.Windmill;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * A Custom Control (Dashboard) which displays and interacts with the production and power values of the windpark application
 *
 * @author Lorenzo Pafumi
 */

public class DashboardControl extends Region {

    private static final double ARTBOARD_WIDTH = 600;
    private static final double ARTBOARD_HEIGHT = 350;
    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;
    private static final double MINIMUM_WIDTH = 25;
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;
    private static final double MAXIMUM_WIDTH = 800;

    private VBox prodPanel, left, right;
    private HBox dashboard;
    private ProductionBar bar1;
    private ProductionBar bar2;
    private ProductionBar bar3;
    private ProductionBar bar4;
    private Windmill windmill;
    private Label title, production;
    private Pane drawingPane;
    private PowerDisplay powerDisplay;

    public DashboardControl(String name, double prod2015, double prod2016, double prod2017, double prod2018, double prodMaxVal, double powerVal, double powerMaxVal) {
        initializeSelf();
        initializeParts(name, prod2015, prod2016, prod2017, prod2018, prodMaxVal, powerVal, powerMaxVal);
        initializeDrawingPane();
        layoutParts();
        setupBindings();
    }

    private void initializeSelf() {
        loadFonts("/fonts/ds_digital/DS-DIGI.TTF");
        addStylesheetFiles("style.css");
        getStyleClass().add("windpark");
    }

    private void initializeParts(String name, double prod2015, double prod2016, double prod2017, double prod2018, double prodMaxVal, double powerVal, double powerMaxVal) {
        this.title = new Label(name);
        this.title.getStyleClass().add("title");
        this.production = new Label("Production:");
        this.bar1 = new ProductionBar("2015", prod2015, prodMaxVal);
        this.bar2 = new ProductionBar("2016", prod2016, prodMaxVal);
        this.bar3 = new ProductionBar("2017", prod2017, prodMaxVal);
        this.bar4 = new ProductionBar("2018", prod2018, prodMaxVal);
        this.windmill = new Windmill(powerVal, powerMaxVal);
        this.powerDisplay = new PowerDisplay(powerVal, powerMaxVal);
        this.prodPanel = new VBox(this.production, this.bar1, this.bar2, this.bar3, this.bar4, this.powerDisplay);
        this.left = new VBox(this.title, this.prodPanel);
        this.right = new VBox(new Pane(), this.windmill);
        this.dashboard = new HBox(this.left, this.right);
    }

    private void initializeDrawingPane() {
        this.drawingPane = new Pane();
        this.drawingPane.getStyleClass().add("drawing-pane");
        this.drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        this.drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        this.drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
    }

    private void layoutParts() {
        this.left.setSpacing(30);
        this.right.setSpacing(30);
        this.dashboard.setSpacing(30);
        this.prodPanel.setSpacing(15);
        this.drawingPane.getChildren().addAll(this.dashboard);
        getChildren().add(this.drawingPane);
    }

    private void setupBindings() {
        this.windmill.currentValueProperty().bindBidirectional(this.powerDisplay.powerValueProperty());
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        resize();
    }

    private void resize() {
        Insets padding = getPadding();
        double availableWidth = getWidth() - padding.getLeft() - padding.getRight();
        double availableHeight = getHeight() - padding.getTop() - padding.getBottom();
        double width = Math.max(Math.min(Math.min(availableWidth, availableHeight * ASPECT_RATIO), MAXIMUM_WIDTH), MINIMUM_WIDTH);
        double scalingFactor = width / ARTBOARD_WIDTH;

        if (availableWidth > 0 && availableHeight > 0) {
            relocateDrawingPaneCentered();
            this.drawingPane.setScaleX(scalingFactor);
            this.drawingPane.setScaleY(scalingFactor);
        }
    }

    private void relocateDrawingPaneCentered() {
        this.drawingPane.relocate((getWidth() - ARTBOARD_WIDTH) * 0.5, (getHeight() - ARTBOARD_HEIGHT) * 0.5);
    }

    private void loadFonts(String... font) {
        for (String f : font) {
            Font.loadFont(getClass().getResourceAsStream(f), 0);
        }
    }

    private void addStylesheetFiles(String... stylesheetFile) {
        for (String file : stylesheetFile) {
            String stylesheet = getClass().getResource(file).toExternalForm();
            getStylesheets().add(stylesheet);
        }
    }

    @Override
    protected double computeMinWidth(double height) {
        Insets padding = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return MINIMUM_WIDTH + horizontalPadding;
    }

    @Override
    protected double computeMinHeight(double width) {
        Insets padding = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return MINIMUM_HEIGHT + verticalPadding;
    }

    @Override
    protected double computePrefWidth(double height) {
        Insets padding = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return ARTBOARD_WIDTH + horizontalPadding;
    }

    @Override
    protected double computePrefHeight(double width) {
        Insets padding = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return ARTBOARD_HEIGHT + verticalPadding;
    }

    public ProductionBar getBar1() {
        return this.bar1;
    }

    public ProductionBar getBar2() {
        return this.bar2;
    }

    public ProductionBar getBar3() {
        return this.bar3;
    }

    public ProductionBar getBar4() {
        return this.bar4;
    }

    public PowerDisplay getPowerDisplay() {
        return this.powerDisplay;
    }

}