//package utils.icon;
//
//import javax.swing.*;
//import javax.swing.filechooser.FileSystemView;
//import javax.swing.table.TableCellRenderer;
//import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.TreeCellRenderer;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//
///**
// * UnifiedIconRenderer отвечает за отображение иконок в таблице файлов и в дереве сайдбара.
// */
//public class UnifiedIconRenderer extends JLabel implements TableCellRenderer, TreeCellRenderer {
//
//    private final FileSystemView fsv = FileSystemView.getFileSystemView();
//
//    public UnifiedIconRenderer() {
//        setOpaque(true);
//    }
//
//    @Override
//    public Component getTableCellRendererComponent(JTable table, Object value,
//                                                   boolean isSelected, boolean hasFocus,
//                                                   int row, int column) {
//        setText(value == null ? "" : value.toString());
//        setIcon(null);
//        if (isSelected) {
//            setBackground(Constants.TABLE_SELECTION_BG != null ? Constants.TABLE_SELECTION_BG : table.getSelectionBackground());
//            setForeground(Constants.TABLE_SELECTION_FG != null ? Constants.TABLE_SELECTION_FG : table.getSelectionForeground());
//        } else {
//            setBackground(Constants.DEFAULT_TABLE_BG);
//            setForeground(Constants.DEFAULT_TABLE_FG);
//        }
//
//        if (column == 0 && table.getModel() instanceof FileTableModel model) {
//            File file = model.getFileAt(row);
//            setIcon(getIconForFile(file));
//        }
//
//        return this;
//    }
//
//    @Override
//    public Component getTreeCellRendererComponent(JTree tree, Object value,
//                                                  boolean sel, boolean expanded,
//                                                  boolean leaf, int row, boolean hasFocus) {
//        String text = "";
//        Icon icon = null;
//
//        if (value instanceof DefaultMutableTreeNode node) {
//            text = node.getUserObject().toString();
//
//            if(text.contains("\\")) {
//                icon = IconUtils.getIcon("drive");
//            } else {
//                icon = switch (text) {
//                    case SidebarNodes.HOME -> IconUtils.getIcon(SidebarNodes.HOME);
//                    case SidebarNodes.DESKTOP -> IconUtils.getIcon(SidebarNodes.DESKTOP);
//                    case SidebarNodes.DOWNLOADS -> IconUtils.getIcon(SidebarNodes.DOWNLOADS);
//                    case SidebarNodes.DOCUMENTS -> IconUtils.getIcon(SidebarNodes.DOCUMENTS);
//                    case SidebarNodes.GALLERY -> IconUtils.getIcon(SidebarNodes.GALLERY);
//                    case SidebarNodes.MUSIC -> IconUtils.getIcon(SidebarNodes.MUSIC);
//                    case SidebarNodes.VIDEOS -> IconUtils.getIcon(SidebarNodes.VIDEOS);
//                    case SidebarNodes.THIS_PC -> IconUtils.getIcon(SidebarNodes.THIS_PC);
//                    default -> IconUtils.getIcon("folder");
//                };
//            }
//        }
//
//        setText(text);
//        setIcon(icon);
//
//        if (sel) {
//            setBackground(Constants.TREE_SELECTION_BG);
//            setForeground(Constants.TREE_SELECTION_FG);
//        } else {
//            setBackground(Constants.DEFAULT_TREE_BG);
//            setForeground(Constants.DEFAULT_TREE_FG);
//        }
//
//        return this;
//    }
//
//    private Icon getIconForFile(File file) {
//        if (file.isDirectory()) return IconUtils.getIcon("folder");
//        String ext = getExtension(file);
//
//        if (AllowedExtensions.LNK.getIconName().equalsIgnoreCase(ext)) {
//            Icon systemIcon = fsv.getSystemIcon(file);
//            if (systemIcon != null) {
//                Image image = iconToImage(systemIcon);
//                Image scaled = image.getScaledInstance(Constants.ICON_SIZE, Constants.ICON_SIZE, Image.SCALE_SMOOTH);
//                return new ImageIcon(scaled);
//            }
//        }
//
//        AllowedExtensions allowed = AllowedExtensions.fromExtension(ext);
//        if (allowed == null) {
//            return IconUtils.getIcon("file");
//        }
//
//        String iconName = switch (allowed) {
//            case DOC, DOCX -> AllowedExtensions.DOC.getIconName();
//            case TXT -> AllowedExtensions.TXT.getIconName();
//            case PNG, JPG, JPEG -> AllowedExtensions.PNG.getIconName();
//            case PPT, PPTX -> AllowedExtensions.PPT.getIconName();
//            case MP3 -> AllowedExtensions.MP3.getIconName();
//            case MP4 -> AllowedExtensions.MP4.getIconName();
//            case RAR -> AllowedExtensions.RAR.getIconName();
//            case WAV -> AllowedExtensions.WAV.getIconName();
//            case ZIP -> AllowedExtensions.ZIP.getIconName();
//            case APK -> AllowedExtensions.APK.getIconName();
//            default -> "file";
//        };
//
//        return IconUtils.getIcon(iconName);
//    }
//
//    private String getExtension(File file) {
//        String name = file.getName();
//        int dot = name.lastIndexOf('.');
//        if (dot > 0 && dot < name.length() - 1) {
//            return name.substring(dot + 1).toLowerCase();
//        }
//        return "";
//    }
//
//    private Image iconToImage(Icon icon) {
//        if (icon instanceof ImageIcon ii) {
//            return ii.getImage();
//        } else {
//            int w = icon.getIconWidth();
//            int h = icon.getIconHeight();
//            BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g = image.createGraphics();
//            icon.paintIcon(null, g, 0, 0);
//            g.dispose();
//            return image;
//        }
//    }
//
//    private static final class Constants {
//        static final int ICON_SIZE = 30;
//
//        static final Color TABLE_SELECTION_BG = UIManager.getColor("Table.selectionBackground");
//        static final Color TABLE_SELECTION_FG = UIManager.getColor("Table.selectionForeground");
//        static final Color DEFAULT_TABLE_BG   = Color.WHITE;
//        static final Color DEFAULT_TABLE_FG   = Color.BLACK;
//
//        static final Color TREE_SELECTION_BG = UIManager.getColor("Tree.selectionBackground");
//        static final Color TREE_SELECTION_FG = UIManager.getColor("Tree.selectionForeground");
//        static final Color DEFAULT_TREE_BG   = Color.WHITE;
//        static final Color DEFAULT_TREE_FG   = Color.BLACK;
//    }
//}