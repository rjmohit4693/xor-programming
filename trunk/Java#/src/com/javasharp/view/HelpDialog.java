package com.javasharp.view;

import com.javasharp.model.util.FileUtils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.text.DefaultCaret;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class HelpDialog
    extends JDialog
{
    private static final long   serialVersionUID = 1L;
    private static final String DEFAULT_HELP     = "Welcome to Java# Help. Click a link to begin.";
    private static final String ERROR_MESSAGE    = "Error loading help!";
    private static final Path   HELP_PATH        = Paths.get("res/help");
    
    
    public HelpDialog(Frame parent)
    {
        super(parent, "Help", false);
        setSize(600, 400);
        setMinimumSize(new Dimension(500, 400));
        
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Java#");
        JTree tree = new JTree(top);
        final JEditorPane helpPane = new JEditorPane("text/html", DEFAULT_HELP);
        
        try
        {
            addNodes(top);
        }
        catch (Exception ex)
        {
            helpPane.setText(ERROR_MESSAGE);
        }
        
        for (int i = 0; i < tree.getRowCount(); i++)
        {
            tree.expandRow(i);
        }
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener((e) -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
            if (node == null)
            {
                return;
            }
            
            if (node.isLeaf())
            {
                HelpItem item = (HelpItem)node.getUserObject();
                try
                {
                    helpPane.setPage(item.path.toUri().toURL());
                    return;
                }
                catch (Exception ex)
                {
                    // Continue so the text is the default
                }
            }
            helpPane.setText(DEFAULT_HELP);
        });
        
        DefaultCaret caret = (DefaultCaret)helpPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        helpPane.setEditable(false);
        
        this.add(new JScrollPane(tree), BorderLayout.WEST);
        this.add(new JScrollPane(helpPane), BorderLayout.CENTER);
        setLocationRelativeTo(parent);
    }
    
    
    private void addNodes(DefaultMutableTreeNode top)
        throws IOException
    {
        Files.list(HELP_PATH).forEach((path) -> {
            String fileName = path.getFileName().toString();
            if (fileName.endsWith(FileUtils.HTML_FILE_EXTENSION))
            {
                String name = FileUtils.getFileNameNoExtension(fileName);
                top.add(new DefaultMutableTreeNode(new HelpItem(name, path)));
            }
        });
    }
    
    
    private static final class HelpItem
    {
        private final String name;
        private final Path   path;
        
        
        public HelpItem(String name, Path path)
        {
            this.name = name;
            this.path = path;
        }
        
        
        public String toString()
        {
            return name;
        }
    }
    
}
