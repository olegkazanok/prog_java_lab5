package bsu.rfe.java.teacher;

import java.io.IOException;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import javax.swing.Action;
import java.awt.Component;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.Toolkit;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class MainFrame extends JFrame
{
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JFileChooser fileChooser;
    private JMenuItem resetGraphicsMenuItem;
    private GraphicsDisplay display;
    private boolean fileLoaded;
    
    public MainFrame() {
        super("\u041e\u0431\u0440\u0430\u0431\u043e\u0442\u043a\u0430 \u0441\u043e\u0431\u044b\u0442\u0438\u0439 \u043e\u0442 \u043c\u044b\u0448\u0438");
        this.fileChooser = null;
        this.display = new GraphicsDisplay();
        this.fileLoaded = false;
        this.setSize(700, 500);
        final Toolkit kit = Toolkit.getDefaultToolkit();
        this.setLocation((kit.getScreenSize().width - 700) / 2, (kit.getScreenSize().height - 500) / 2);
        this.setExtendedState(6);
        final JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        final JMenu fileMenu = new JMenu("\u0424\u0430\u0439\u043b");
        menuBar.add(fileMenu);
        final Action openGraphicsAction = new AbstractAction("\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u0444\u0430\u0439\u043b \u0441 \u0433\u0440\u0430\u0444\u0438\u043a\u043e\u043c") {
            @Override
            public void actionPerformed(final ActionEvent event) {
                if (MainFrame.this.fileChooser == null) {
                    MainFrame.access$1(MainFrame.this, new JFileChooser());
                    MainFrame.this.fileChooser.setCurrentDirectory(new File("."));
                }
                MainFrame.this.fileChooser.showOpenDialog(MainFrame.this);
                MainFrame.this.openGraphics(MainFrame.this.fileChooser.getSelectedFile());
            }
        };
        fileMenu.add(openGraphicsAction);
        final Action resetGraphicsAction = new AbstractAction("\u041e\u0442\u043c\u0435\u043d\u0438\u0442\u044c \u0432\u0441\u0435 \u0438\u0437\u043c\u0435\u043d\u0435\u043d\u0438\u044f") {
            @Override
            public void actionPerformed(final ActionEvent event) {
                MainFrame.this.display.reset();
            }
        };
        (this.resetGraphicsMenuItem = fileMenu.add(resetGraphicsAction)).setEnabled(false);
        this.getContentPane().add(this.display, "Center");
    }
    
    protected void openGraphics(final File selectedFile) {
        try {
            final DataInputStream in = new DataInputStream(new FileInputStream(selectedFile));
            final ArrayList<Double[]> graphicsData = new ArrayList<Double[]>(50);
            while (in.available() > 0) {
                final Double x = in.readDouble();
                final Double y = in.readDouble();
                graphicsData.add(new Double[] { x, y });
            }
            if (graphicsData.size() > 0) {
                this.fileLoaded = true;
                this.resetGraphicsMenuItem.setEnabled(true);
                this.display.displayGraphics(graphicsData);
            }
        }
        catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "\u0423\u043a\u0430\u0437\u0430\u043d\u043d\u044b\u0439 \u0444\u0430\u0439\u043b \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d", "\u041e\u0448\u0438\u0431\u043a\u0430 \u0437\u0430\u0433\u0440\u0443\u0437\u043a\u0438 \u0434\u0430\u043d\u043d\u044b\u0445", 2);
        }
        catch (IOException ex2) {
            JOptionPane.showMessageDialog(this, "\u041e\u0448\u0438\u0431\u043a\u0430 \u0447\u0442\u0435\u043d\u0438\u044f \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442 \u0442\u043e\u0447\u0435\u043a \u0438\u0437 \u0444\u0430\u0439\u043b\u0430", "\u041e\u0448\u0438\u0431\u043a\u0430 \u0437\u0430\u0433\u0440\u0443\u0437\u043a\u0438 \u0434\u0430\u043d\u043d\u044b\u0445", 2);
        }
    }
    
    public static void main(final String[] args) {
        final MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
    
    static /* synthetic */ void access$1(final MainFrame mainFrame, final JFileChooser fileChooser) {
        mainFrame.fileChooser = fileChooser;
    }
}
