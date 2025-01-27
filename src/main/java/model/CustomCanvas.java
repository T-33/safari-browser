package model;

import model.layoutengine.layoutboxes.BoxType;
import model.layoutengine.layoutboxes.LayoutBox;
import model.layoutengine.layoutboxes.LayoutTextBox;
import javax.swing.*;
import java.awt.*;

public class CustomCanvas extends JPanel {
    private LayoutBox rootLayout;

    public CustomCanvas(LayoutBox layoutBox) {
        this.rootLayout = layoutBox;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawLayout(g, rootLayout);
    }

    private void drawLayout(Graphics g, LayoutBox layoutBox) {

        if(layoutBox.getBoxType() == BoxType.BLOCK) {
            g.setColor(Color.BLUE);
            g.drawRect((int) layoutBox.getX(), (int) layoutBox.getY(), (int) layoutBox.getWidth(), (int) layoutBox.getHeight());
        }
        if (layoutBox instanceof LayoutTextBox layoutTextBox) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Aerial", Font.PLAIN, 12));
            g.drawString(layoutTextBox.getText(), (int) layoutBox.getX(), (int) layoutBox.getY());
        }

        for (LayoutBox childNode : layoutBox.getChildren()) {
            drawLayout(g, childNode);
        }
    }
}
