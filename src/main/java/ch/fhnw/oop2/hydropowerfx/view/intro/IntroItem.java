package ch.fhnw.oop2.hydropowerfx.view.intro;

import javafx.scene.Node;
import javafx.scene.shape.Shape;

public class IntroItem {
    public enum POSITION {TOP, LEFT, RIGHT, BOTTOM}
    public enum DIRECTION {UP, LEFT, RIGHT, DOWM}
    public enum ARROW { UPRIGHT, UPLEFT, RIGHTUP, RIGHTDOWN, DOWMLEFT, DOWNRIGHT, LEFTUP, LEFTDOWN }

    private Node node;
    private Shape nodeShape;
    private String text;
    private ARROW arrow;

    public IntroItem(Node node,ARROW arrow, String text) {
        this.node = node;
        this.text = text;
        this.arrow = arrow;
    }

    public Node getNode() {
        return node;
    }

    public Shape getNodeShape() {
        return nodeShape;
    }

    public void setNodeShape(Shape nodeShape) {
        this.nodeShape = nodeShape;
    }

    public String getText() {
        return text;
    }

    public ARROW getArrow() {
        return arrow;
    }
}
