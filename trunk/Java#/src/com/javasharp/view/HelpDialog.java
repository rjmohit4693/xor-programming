package com.javasharp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.DefaultCaret;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import com.javasharp.model.util.FileReader;

public class HelpDialog
    extends JDialog
    implements TreeSelectionListener
{
    private static final long serialVersionUID = 1L;
    private JTree             tree;
    private JEditorPane       helpPane;
    
    
    public HelpDialog(JFrame parent)
    {
        super(parent, "Help", false);
        setSize(600, 400);
        setMinimumSize(new Dimension(400, 300));
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Java#");
        createNodes(top);
        tree = new JTree(top);
        for (int i = 0; i < tree.getRowCount(); i++)
        {
            tree.expandRow(i);
        }
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);
        // treeView.setPreferredSize(new Dimension(175, 300));
        helpPane = new JEditorPane("text/html", "Welcome to Java# Help. Click a link to begin.");
        DefaultCaret caret = (DefaultCaret)helpPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        helpPane.setEditable(false);
        this.add(new JScrollPane(tree), BorderLayout.WEST);
        this.add(new JScrollPane(helpPane), BorderLayout.CENTER);
        setLocationRelativeTo(parent);
    }
    
    
    private void createNodes(DefaultMutableTreeNode top)
        throws Exception
    {
        DefaultMutableTreeNode book = null;
        
        FileReader fr = new FileReader("res\\help\\faq.html");
        String loadedString = fr.read();
        
        book = new DefaultMutableTreeNode(new BookInfo("FAQ", loadedString));
        top.add(book);
        
        fr = new FileReader("res\\help\\features.html");
        loadedString = fr.read();
        
        book = new DefaultMutableTreeNode(new BookInfo("Features", loadedString));
        top.add(book);
        
        fr = new FileReader("res\\help\\shortcuts.html");
        loadedString = fr.read();
        
        book = new DefaultMutableTreeNode(new BookInfo("Shortcuts", loadedString));
        top.add(book);
        
        fr = new FileReader("res\\help\\toolbar.html");
        loadedString = fr.read();
        
        book = new DefaultMutableTreeNode(new BookInfo("Toolbar", loadedString));
        top.add(book);
    }
    
    
    public void valueChanged(TreeSelectionEvent e)
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        
        if (node == null)
        {
            return;
        }
        
        Object nodeInfo = node.getUserObject();
        if (node.isLeaf())
        {
            BookInfo book = (BookInfo)nodeInfo;
            display(book.URL);
        }
        else
        {
            // display(helpURL);
        }
    }
    
    
    private void display(String info)
    {
        helpPane.setText(info);
    }
    
    
    private class BookInfo
    {
        private String name;
        private String URL;
        
        
        public BookInfo(String name, String URL)
        {
            this.name = name;
            this.URL = URL;
        }
        
        
        public String toString()
        {
            return name;
        }
    }
    
}
