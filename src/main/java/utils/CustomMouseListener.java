package utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * CustomMouseListener — набор готовых слушателей (MouseListener/ActionListener),
 * которые вы можете использовать в разных компонентах.
 */
public class CustomMouseListener {

    /**
     * Слушатель, который делает pathField редактируемым
     * при любом клике (не обязательно двойном).
     */
    public static class PathFieldMouseListener extends MouseAdapter {
        private final JTextField pathField;

        public PathFieldMouseListener(JTextField pathField) {
            this.pathField = pathField;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            pathField.setEditable(true);
            pathField.setFocusable(true);
            pathField.requestFocusInWindow();
        }
    }

    /**
     * Слушатель, который обрабатывает нажатие Enter в pathField
     * и вызывает метод onPathEntered(...) в ToolbarController.
     */
    public static class PathFieldActionListener implements ActionListener {
        private final JTextField pathField;
        private final ToolbarController toolbarController;

        public PathFieldActionListener(JTextField pathField, ToolbarController toolbarController) {
            this.pathField = pathField;
            this.toolbarController = toolbarController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String inputPath = pathField.getText();
            pathField.setEditable(false);
            pathField.setFocusable(false);

            pathField.setEditable(true);
            pathField.setFocusable(true);
            pathField.requestFocusInWindow();
        }
    }

    /**
     * Слушатель для кнопки: при наведении меняет цвет/обводку,
     * при уходе возвращает обратно.
     */
    public static class ButtonHoverMouseListener extends MouseAdapter {
        private final AbstractButton button;
        private final Border normalBorder;
        private final Border hoverBorder;
        private final Color hoverBackground;

        public ButtonHoverMouseListener(AbstractButton button) {
            this.button = button;
            this.normalBorder = BorderFactory.createEmptyBorder();
            this.hoverBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
            this.hoverBackground = Color.LIGHT_GRAY;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBorder(hoverBorder);
            button.setBackground(hoverBackground);
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setBorderPainted(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBorder(normalBorder);
            button.setContentAreaFilled(false);
        }
    }

    /**
     * Слушатель для заголовка таблицы, обрабатывает
     * клик по столбцу и вызывает sortByColumn.
     */
    public static class HeaderMouseListener extends MouseAdapter {
        private final JTableHeader header;
        private final FileTableModel tableModel;

        public HeaderMouseListener(JTableHeader header, FileTableModel tableModel) {
            this.header = header;
            this.tableModel = tableModel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int column = header.columnAtPoint(e.getPoint());
            tableModel.sortByColumn(column);
        }
    }

    /**
     * Слушатель для дерева (SideBarView): при нажатии мыши
     * делаем sideBarView.revalidate().
     */
    public static class TreeClickMouseListener extends MouseAdapter {
        private final JTree tree;
        private final SideBarView sideBarView;

        public TreeClickMouseListener(JTree tree, SideBarView sideBarView) {
            this.tree = tree;
            this.sideBarView = sideBarView;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            TreePath path = tree.getPathForLocation(e.getX(), e.getY());
            if (path != null) {
                sideBarView.revalidate();
            }
        }
    }
}
