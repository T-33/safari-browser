package utils.icon;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Управляет загрузкой иконок из ресурсов.
 */
public class IconUtils {
    private static final String ICONS_PATH = "/icons";

    private static final Map<String, Map<Dimension, Icon>> iconsCache = new HashMap<>();

    /**
     * Получает иконку по имени. Иконки кэшируются для оптимизации.
     */
    public static Icon getIcon(String name, int width, int height) {
        Dimension size = new Dimension(width, height);
        iconsCache.putIfAbsent(name, new HashMap<>());
        Map<Dimension, Icon> sizeMap = iconsCache.get(name);

        if (sizeMap.containsKey(size)) {
            return sizeMap.get(size);
        }

        URL imgURL = IconUtils.class.getResource(String.format("%s/%s.png", ICONS_PATH, name));
        if (imgURL == null) {
            System.err.println("The icon was not found: " + name);
            return null;
        }

        ImageIcon originalIcon = new ImageIcon(imgURL);

        Image scaledImage = Optional.ofNullable(originalIcon.getImage())
                .map(img -> img.getScaledInstance(width, height, Image.SCALE_SMOOTH))
                .orElseThrow(() ->
                        new RuntimeException("Failed to load or scale image for icon: " + name)
                );

        Icon icon = new ImageIcon(scaledImage);
        sizeMap.put(size, icon);
        return icon;
    }

    public static Icon getIcon(String name) {
        return getIcon(name, 30, 30);
    }
}