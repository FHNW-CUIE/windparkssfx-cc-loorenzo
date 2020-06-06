package cuie.loorenzo.template_simplecontrol;

import java.util.List;
import java.util.Locale;

import cuie.loorenzo.template_simplecontrol.components.CustomTickBar;
import cuie.loorenzo.template_simplecontrol.components.PowerDisplay;
import cuie.loorenzo.template_simplecontrol.components.WindmillRange;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * ToDo: CustomControl kurz beschreiben
 * <p>
 * ToDo: Autoren ergänzen / ersetzen
 *
 * @author
 */
//ToDo: Umbenennen.
public class SimpleControl extends Region {
    // wird gebraucht fuer StyleableProperties
    private static final StyleablePropertyFactory<SimpleControl> FACTORY = new StyleablePropertyFactory<>(Region.getClassCssMetaData());

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return FACTORY.getCssMetaData();
    }

    private static final Locale CH = new Locale("de", "CH");

    private static final double ARTBOARD_WIDTH = 600;
    private static final double ARTBOARD_HEIGHT = 600;

    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;

    private static final double MINIMUM_WIDTH = 25;
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

    private static final double MAXIMUM_WIDTH = 800;

    // ToDo: diese Parts durch alle notwendigen Parts der gewünschten CustomControl ersetzen
    private VBox prodPanel, left;
    private HBox dashboard, hBox1, hBox2, hBox3, hBox4;
    private CustomTickBar bar1;
    private CustomTickBar bar2;
    private CustomTickBar bar3;
    private CustomTickBar bar4;
    private WindmillRange wr;
    private Label title, production, prod1, prod2, prod3, prod4;
    private TextField field1, field2, field3, field4;
    private Pane drawingPane;

    private PowerDisplay powerDisplay;

    public SimpleControl() {
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        initializeAnimations();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeSelf() {
        loadFonts("/fonts/Lato/Lato-Lig.ttf", "/fonts/Lato/Lato-Reg.ttf", "/fonts/ds_digital/DS-DIGI.TTF");
        addStylesheetFiles("style.css");
        getStyleClass().add("simple-control");  // ToDo: an den Namen der Klasse (des CustomControls) anpassen
    }

    private void initializeParts() {
        double center = ARTBOARD_WIDTH * 0.5;
        title = new Label("WindPark blabla");
        title.getStyleClass().add("title");
        production = new Label("Production:");
        prod1 = new Label("2015");
        prod2 = new Label("2016");
        prod3 = new Label("2017");
        prod4 = new Label("2018");
        double maxVal = 6000;
        bar1 = new CustomTickBar(100, 20, 3270.00, maxVal);
        bar2 = new CustomTickBar(100, 20, 4000, maxVal);
        bar3 = new CustomTickBar(100, 20, 5500, maxVal);
        bar4 = new CustomTickBar(100, 20, 6000, maxVal);
        field1 = new TextField();
        field2 = new TextField();
        field3 = new TextField();
        field4 = new TextField();
        hBox1 = new HBox(prod1, bar1, field1);
        hBox1.setSpacing(10);
        hBox2 = new HBox(prod2, bar2, field2);
        hBox2.setSpacing(10);
        hBox3 = new HBox(prod3, bar3, field3);
        hBox3.setSpacing(10);
        hBox4 = new HBox(prod4, bar4, field4);
        hBox4.setSpacing(10);
        wr = new WindmillRange(4000, 6000);
        powerDisplay = new PowerDisplay(4000, 6000);

        prodPanel = new VBox(production, hBox1, hBox2, hBox3, hBox4, powerDisplay);
        left = new VBox(title, prodPanel);
        dashboard = new HBox(left, wr);


    }

    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.getStyleClass().add("drawing-pane");
        drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
    }

    private void initializeAnimations() {
        //ToDo: alle deklarierten Animationen initialisieren
    }

    private void layoutParts() {
        left.setSpacing(30);
        dashboard.setSpacing(30);
        prodPanel.setSpacing(15);
        drawingPane.getChildren().addAll(dashboard);
        getChildren().add(drawingPane);
    }

    private void setupEventHandlers() {
    }

    private void setupValueChangeListeners() {
        //ToDo: durch die Listener auf die Properties des Custom Controls ersetzen
    }

    private void setupBindings() {
        field1.textProperty().bindBidirectional(bar1.barValProperty(), new NumberStringConverter());
        field2.textProperty().bindBidirectional(bar2.barValProperty(), new NumberStringConverter());
        field3.textProperty().bindBidirectional(bar3.barValProperty(), new NumberStringConverter());
        field4.textProperty().bindBidirectional(bar4.barValProperty(), new NumberStringConverter());
        wr.currentValueProperty().bindBidirectional(powerDisplay.powerValueProperty());
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
            //ToDo: ueberpruefen ob die drawingPane immer zentriert werden soll (eventuell ist zum Beispiel linksbuendig angemessener)
            relocateDrawingPaneCentered();
            drawingPane.setScaleX(scalingFactor);
            drawingPane.setScaleY(scalingFactor);
        }
    }

    private void relocateDrawingPaneCentered() {
        drawingPane.relocate((getWidth() - ARTBOARD_WIDTH) * 0.5, (getHeight() - ARTBOARD_HEIGHT) * 0.5);
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

    // alle getter und setter  (generiert via "Code -> Generate... -> Getter and Setter)

    // ToDo: ersetzen durch die Getter und Setter Ihres CustomControls

    public CustomTickBar getBar1() {
        return bar1;
    }

    public void setBar1(CustomTickBar bar1) {
        this.bar1 = bar1;
    }

    public CustomTickBar getBar2() {
        return bar2;
    }

    public void setBar2(CustomTickBar bar2) {
        this.bar2 = bar2;
    }

    public CustomTickBar getBar3() {
        return bar3;
    }

    public void setBar3(CustomTickBar bar3) {
        this.bar3 = bar3;
    }

    public CustomTickBar getBar4() {
        return bar4;
    }

    public void setBar4(CustomTickBar bar4) {
        this.bar4 = bar4;
    }

    public WindmillRange getWr() {
        return wr;
    }

    public void setWr(WindmillRange wr) {
        this.wr = wr;
    }
}