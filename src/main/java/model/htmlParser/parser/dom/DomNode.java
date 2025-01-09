package model.htmlParser.parser.dom;

import java.util.ArrayList;
import java.util.List;

/**
 * Базовый класс для всех узлов DOM-дерева.
 * Может быть расширен (DomElement, DomText и т.д.).
 */
public abstract class DomNode {
    protected List<DomNode> children = new ArrayList<>();

    /**
     * Добавление дочернего узла.
     *
     * @param child дочерний узел
     */
    public void addChild(DomNode child) {
        if (child != null) {
            children.add(child);
        }
    }

    /**
     * Получить всех дочерних узлов.
     *
     * @return список дочерних узлов
     */
    public List<DomNode> getChildren() {
        return children;
    }
}
