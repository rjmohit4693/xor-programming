package com.javasharp.view;

import com.javasharp.model.util.FileUtils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
    private static final String DEFAULT_HELP     = "Welcome to Java# Help. Click a topic to begin.";
    private static final String ERROR_MESSAGE    = "Error loading help!";
    private static final File   HELP_FILE        = new File("res/help");


    public HelpDialog(Frame parent)
    {
        super(parent, "Help", false);
        setSize(600, 400);
        setMinimumSize(new Dimension(500, 400));

        DefaultMutableTreeNode top = new DefaultMutableTreeNode(new HelpItem(JavaSharp.PROGRAM_NAME));
        JTree tree = new JTree(top);
        final JEditorPane helpPane = new JEditorPane("text/html", DEFAULT_HELP);

        try
        {
            addNodes(HELP_FILE, top);
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

            HelpItem item = (HelpItem)node.getUserObject();
            try
            {
                helpPane.setPage(item.url);
                return;
            }
            catch (IOException ex)
            {
                helpPane.setText(DEFAULT_HELP);
            }
        });

        DefaultCaret caret = (DefaultCaret)helpPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        helpPane.setEditable(false);

        this.add(new JScrollPane(tree), BorderLayout.WEST);
        this.add(new JScrollPane(helpPane), BorderLayout.CENTER);
        setLocationRelativeTo(parent);
    }


    private void addNodes(File curFile, DefaultMutableTreeNode node)
    {
        File[] fileList = curFile.listFiles();
        if (fileList == null)
        {
            return;
        }

        for (File f : fileList)
        {
            String fileName = f.getName().toString();
            if (f.isDirectory())
            {
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(new HelpItem(fileName));
                addNodes(f, child);
                node.add(child);
            }
            else if (fileName.endsWith(FileUtils.HTML_FILE_EXTENSION))
            {
                String nameNoExtension = FileUtils.getFileNameNoExtension(fileName);
                try
                {
                    HelpItem item = new HelpItem(nameNoExtension, f.toURI().toURL());
                    node.add(new DefaultMutableTreeNode(item));
                }
                catch (MalformedURLException e)
                {
                    // If exception is caught, just continue and a node is not added
                }
            }
        }
    }


    private static final class HelpItem
    {
        private final String name;
        private final URL    url;


        public HelpItem(String name)
        {
            this(name, null);
        }


        public HelpItem(String name, URL url)
        {
            this.name = name;
            this.url = url;
        }


        public String toString()
        {
            return name;
        }
    }

}
