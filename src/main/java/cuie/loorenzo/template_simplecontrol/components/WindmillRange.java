package cuie.loorenzo.template_simplecontrol.components;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WindmillRange extends Region {

    private static final double ARTBOARD_WIDTH = 300;
    private static final double ARTBOARD_HEIGHT = 300;

    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;

    private static final double MINIMUM_WIDTH = 75;
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

    private static final double MAXIMUM_WIDTH = 800;

    private Canvas background;
    private Pane drawingPane;
    private double scalingFactor = 1.0;

    ImageView image = new ImageView(new Image("images/wind-turbine.png"));

    public WindmillRange() {
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
        background = new Canvas(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        background.setMouseTransparent(true);
        drawingPane = new Pane();
        drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.getStyleClass().add("drawing-pane");
        animation().start();
        drawCanvas();
    }

    private void drawCanvas() {
        GraphicsContext gc = background.getGraphicsContext2D();
        double width = background.getWidth();
        double height = background.getHeight();
        double lineWidth = 4 * scalingFactor;

        gc.clearRect(0, 0, width, height);
        double startPoint = ARTBOARD_WIDTH / 2;
        double startPointY = ARTBOARD_HEIGHT / 2;
        gc.setFill(Color.WHITE);
        gc.beginPath();
        gc.moveTo(startPoint-3, startPointY);
        gc.lineTo(startPoint + 3, startPointY);
        gc.lineTo(startPoint + 13, ARTBOARD_HEIGHT);
        gc.lineTo(startPoint - 13, ARTBOARD_HEIGHT);
        gc.closePath();
        gc.fill();
    }


    private void initializeDrawingPane() {

    }

    private void layoutParts() {
        image.setFitHeight(300);
        image.setFitWidth(300);
        getChildren().addAll(drawingPane, background, image);
    }

    private void setupEventHandlers() {

    }

    private void setupValueChangeListeners() {

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
                image.setRotate(image.getRotate() + 1);
            }
        };
    }

    private void resize() {
        //TODO
    }

    // some handy functions

    //ToDo: diese Funktionen anschauen und für die Umsetzung des CustomControls benutzen


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

}
