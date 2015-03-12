/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaboratolinguaggiecompilatori;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

public class ClipboardPopupMenu extends MouseAdapter {

    private Clipboard clipBoard;
    private JPopupMenu popup;
    private JTextComponent textComponent;
    private JMenuItem copy;
    private JMenuItem paste;
    private JMenuItem clear;
    private JMenuItem cut;
    private TransferHandler handler;

    public ClipboardPopupMenu(JTextComponent textComponent) {
        popup = new JPopupMenu();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
        this.textComponent = textComponent;
        handler = textComponent.getTransferHandler();
        createMenu();
    }

    protected void createMenu() {
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        cut = new JMenuItem("Cut");
        clear = new JMenuItem("Clear");
    }

    public void addPasteFunction() {
        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Transferable clipboardContent = clipBoard.getContents(this);
                if (clipboardContent != null
                        && (clipboardContent.isDataFlavorSupported(DataFlavor.stringFlavor))) {
                    try {
                        textComponent.setText(textComponent.getText() + clipboardContent.getTransferData(DataFlavor.stringFlavor));
                    } catch (Exception ex) {
                    }
                }
            }
        });
        popup.add(paste);
    }

    public void addCopyFunction() {
        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handler.exportToClipboard(textComponent, clipBoard, TransferHandler.COPY);
            }
        });
        popup.add(copy);
    }

    public void addClearFunction() {
        popup.addSeparator();
        popup.add(clear);
    }

    public void addCutFunction() {
        cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handler.exportToClipboard(textComponent, clipBoard, TransferHandler.MOVE);
            }
        });
        popup.add(cut);
    }

    public void addPasteFunction(String nameItem) {
        paste.setText(nameItem);
        addPasteFunction();
    }

    public void addCopyFunction(String nameItem) {
        copy.setText(nameItem);
        addCopyFunction();
    }

    public void addClearFunction(String nameItem) {
        clear.setText(nameItem);
        addClearFunction();
    }

    public void addCutFunction(String menuItem) {
        cut.setText(menuItem);
        addCutFunction();
    }

    public void mousePressed(MouseEvent e) {
        showPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        showPopup(e);
    }

    private void showPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            if (textComponent.getText().length() == 0) {
                copy.setEnabled(false);
                cut.setEnabled(false);
            } else {
                copy.setEnabled(true);
                copy.setEnabled(true);
            }
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    public JPopupMenu getPopup() {
        return popup;
    }

    public static ClipboardPopupMenu installForComponent(JTextComponent c) {
        ClipboardPopupMenu cpb = new ClipboardPopupMenu(c);
        c.addMouseListener(cpb);
        return cpb;
    }
}
