package cuie.loorenzo.template_simplecontrol.components;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

import java.util.ArrayList;
import java.util.List;

public class WindmillRange extends Region {

    private static final double SPIN_FACTOR = 5;

    private static final double ARTBOARD_WIDTH = 300;
    private static final double ARTBOARD_HEIGHT = 300;

    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;

    private static final double MINIMUM_WIDTH = 75;
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

    private static final double MAXIMUM_WIDTH = 800;

    private Canvas background;
    private Pane drawingPane;

    ImageView image = new ImageView(new Image("images/wind-turbine.png"));

    //NUmber Range

    private Circle backgroundCircle;
    private Arc bar;
    private Circle thumb;
    private Text valueDisplay;
    private Group ticks;
    private List<Text> tickLabels;
    private final DoubleProperty currentValue = new SimpleDoubleProperty();
    private final double maxValue;

    public WindmillRange(double currentValue, double maxValue) {
        this.currentValue.set(currentValue);
        this.maxValue = maxValue;
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBinding();
    }

    private void initializeSelf() {
        getStyleClass().add("windmill-range");
    }

    private void initializeParts() {

        double center = ARTBOARD_WIDTH * 0.5;
        int width = 15;
        double radius = center - width + 2;

        backgroundCircle = new Circle(center, center, radius);
        backgroundCircle.getStyleClass().add("background-circle");

        bar = new Arc(center, center, radius, radius, 90.0, -180.0);
        bar.getStyleClass().add("bar");
        bar.setType(ArcType.OPEN);

        thumb = new Circle(center, center + center - width, 13);
        thumb.getStyleClass().add("thumb");

        valueDisplay = createCenteredText(center, center, "value-display");
        ticks = createTicks(center, center, 120, 360.0, 28, -1, 0, "tick");

        tickLabels = new ArrayList<>();

        int labelCount = 8;
        for (int i = 0; i < labelCount; i++) {
            double r = 95;
            double angle = i * 360.0 / labelCount;

            Point2D p = pointOnCircle(center, center, r, angle);
            Text tickLabel = createCenteredText(p.getX(), p.getY(), "tick-label");

            tickLabels.add(tickLabel);
        }
        updateTickLabels();


        background = new Canvas(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        background.setMouseTransparent(true);
        animation().start();
        drawCanvas();
    }

    private void drawCanvas() {
        GraphicsContext gc = background.getGraphicsContext2D();
        double width = background.getWidth();
        double height = background.getHeight();
        gc.clearRect(0, 0, width, height);
        double startPoint = ARTBOARD_WIDTH / 2;
        double startPointY = ARTBOARD_HEIGHT / 2;
        gc.setFill(Color.WHITE);
        gc.beginPath();
        gc.moveTo(startPoint - 3, startPointY);
        gc.lineTo(startPoint + 3, startPointY);
        gc.lineTo(startPoint + 13, ARTBOARD_HEIGHT);
        gc.lineTo(startPoint - 13, ARTBOARD_HEIGHT);
        gc.closePath();
        gc.fill();
    }


    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.getStyleClass().add("drawing-pane");
    }

    private void layoutParts() {
        image.setFitHeight(300);
        image.setFitWidth(300);
        drawingPane.getChildren().addAll(background, image, backgroundCircle, bar, valueDisplay, ticks, thumb);
        getChildren().addAll(drawingPane);
    }

    private void setupEventHandlers() {
        thumb.setOnMouseDragged(event -> {
            double center = ARTBOARD_WIDTH * 0.5;
            setCurrentValue(radialMousePositionToValue(event.getX(), event.getY(),
                    center, center, 0, this.maxValue));
        });
    }

    private void setupValueChangeListeners() {
        currentValue.addListener((observable, oldValue, newValue) -> {
            updateThumbAndBar();
        });
    }

    private void setupBinding() {

    }

    //resize by scaling
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        resize();
    }

    private AnimationTimer animation() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                double valFactor = (currentValue.get() / maxValue);
                image.setRotate(image.getRotate() + (SPIN_FACTOR * valFactor));
            }
        };
    }

    private void resize() {
        //TODO
    }

    // some handy functions

    //ToDo: diese Funktionen anschauen und für die Umsetzung des CustomControls benutzen

    private void updateTickLabels() {
        int labelCount = tickLabels.size();
        double step = this.maxValue / labelCount;
        for (int i = 0; i < labelCount; i++) {
            Text tickLabel = tickLabels.get(i);
            tickLabel.setText(String.format("%.0f", i * step));
        }
    }

    private void updateThumbAndBar() {
        double angle = valueToAngle(getCurrentValue(), 0, this.maxValue);
        bar.setLength(Math.min(-0.05, -angle));
        double center = ARTBOARD_WIDTH * 0.5;
        Point2D thumbCenter = pointOnCircle(center, center, center - 15, angle);
        thumb.setCenterX(thumbCenter.getX());
        thumb.setCenterY(thumbCenter.getY());
    }

    private double percentageToValue(double percentage, double minValue, double maxValue) {
        return ((maxValue - minValue) * percentage) + minValue;
    }

    private double valueToPercentage(double value, double minValue, double maxValue) {
        return (value - minValue) / (maxValue - minValue);
    }

    private double valueToAngle(double value, double minValue, double maxValue) {
        return percentageToAngle(valueToPercentage(value, minValue, maxValue));
    }

    private double radialMousePositionToValue(double mouseX, double mouseY, double cx, double cy, double minValue, double maxValue) {
        double percentage = angleToPercentage(angle(cx, cy, mouseX, mouseY));

        return percentageToValue(percentage, minValue, maxValue);
    }

    private double angleToPercentage(double angle) {
        return angle / 360.0;
    }

    private double percentageToAngle(double percentage) {
        return 360.0 * percentage;
    }

    private double angle(double cx, double cy, double x, double y) {
        double deltaX = x - cx;
        double deltaY = y - cy;
        double radius = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
        double nx = deltaX / radius;
        double ny = deltaY / radius;
        double theta = Math.toRadians(90) + Math.atan2(ny, nx);

        return Double.compare(theta, 0.0) >= 0 ? Math.toDegrees(theta) : Math.toDegrees((theta)) + 360.0;
    }

    private Point2D pointOnCircle(double cX, double cY, double radius, double angle) {
        return new Point2D(cX - (radius * Math.sin(Math.toRadians(angle - 180))),
                cY + (radius * Math.cos(Math.toRadians(angle - 180))));
    }

    private Text createCenteredText(String styleClass) {
        return createCenteredText(ARTBOARD_WIDTH * 0.5, ARTBOARD_HEIGHT * 0.5, styleClass);
    }

    private Text createCenteredText(double cx, double cy, String styleClass) {
        Text text = new Text();
        text.getStyleClass().add(styleClass);
        text.setTextOrigin(VPos.CENTER);
        text.setTextAlignment(TextAlignment.CENTER);
        double width = cx > ARTBOARD_WIDTH * 0.5 ? ((ARTBOARD_WIDTH - cx) * 2.0) : cx * 2.0;
        text.setWrappingWidth(width);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setY(cy);
        text.setX(cx - (width / 2.0));

        return text;
    }

    private Group createTicks(double cx, double cy, int numberOfTicks, double overallAngle, double tickLength, double indent, double startingAngle, String styleClass) {
        Group group = new Group();

        double degreesBetweenTicks = overallAngle == 360 ?
                overallAngle / numberOfTicks :
                overallAngle / (numberOfTicks - 1);
        double outerRadius = Math.min(cx, cy) - indent;
        double innerRadius = Math.min(cx, cy) - indent - tickLength;

        for (int i = 0; i < numberOfTicks; i++) {
            double angle = 180 + startingAngle + i * degreesBetweenTicks;

            Point2D startPoint = pointOnCircle(cx, cy, outerRadius, angle);
            Point2D endPoint = pointOnCircle(cx, cy, innerRadius, angle);

            Line tick = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
            tick.getStyleClass().add(styleClass);
            group.getChildren().add(tick);
        }

        return group;
    }

    // compute sizes

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

    public double getCurrentValue() {
        return currentValue.get();
    }

    public DoubleProperty currentValueProperty() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue.set(currentValue);
    }

}
