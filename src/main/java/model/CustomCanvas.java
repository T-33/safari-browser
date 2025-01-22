//package model;
//
//import model.layoutengine.layoutboxes.LayoutBox;
//import model.layoutengine.rendernodes.RenderNode;
//import model.layoutengine.rendernodes.RenderTextNode;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class CustomCanvas extends JPanel {
//
//    private RenderNode root;
//    private LayoutBox rootLayout;
//
//    public CustomCanvas(RenderNode node, LayoutBox layoutBox) {
//        this.root = node;
//        this.rootLayout = layoutBox;
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        drawLayout(g, root, rootLayout);
//    }
//
//    private void drawLayout(Graphics g, RenderNode node, LayoutBox layoutBox) {
//        g.setColor(Color.BLUE);
//        g.drawRect((int) layoutBox.getX(), (int) layoutBox.getY(), (int) layoutBox.getWidth(), (int) layoutBox.getHeight());
//
//        if(node instanceof RenderTextNode) {
//            g.setColor(Color.BLACK);
//            g.drawString(((RenderTextNode) node).getText(), (int) layoutBox.getX() + 5, (int) layoutBox.getY() + 10);
//        }
//
//        for (RenderNode childNode : node.getChildren()) {
//            LayoutBox childLayout = node.getLayoutBoxes().get(childNode);
//
//            if(childLayout != null) {
//                drawLayout(g, childNode, childLayout);
//            }
//        }
//    }
//}
