package wsb.sp_pwgp.tablica;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Choice;

/**
 * @author kmi
 */
@SuppressWarnings("serial")
public class IBClientView extends Frame {

    private IBClientController controller = null;
    private IBClientModel model = null;

    public IBClientView(IBClientController controller, IBClientModel model, String title) {
        super(title);
        this.controller = controller;
        this.model = model;
    }

    public void createView(int colorIndex, int width, int height) {
        model.createModel(IBProtocol.colors[colorIndex], width, height);
        setBackground(Color.lightGray);
        setLayout(new BorderLayout());

        add(model, "West");

        Button clearButton = new Button("clear");
        clearButton.addActionListener((evt) -> model.clear());
        Panel clearPanel = new Panel();
        clearPanel.add(clearButton);

        Button logoutButton = new Button("logout");
        logoutButton.addActionListener((event) -> controller.forceLogout());
        Panel logoutPanel = new Panel();
        logoutPanel.add(logoutButton);

        Panel brushPanel = new Panel();
        Label brushLabel = new Label("Brush Size:");
        Choice brushSizeChoice = new Choice();
        for (int i = 1; i <= 10; i++) {
            brushSizeChoice.add(Integer.toString(i));
        }
        brushSizeChoice.addItemListener(e -> {
            int size = Integer.parseInt(brushSizeChoice.getSelectedItem());
            model.setBrushSize(size);
            controller.send(IBProtocol.BRUSH_SIZE + " " + size);
        });
        brushPanel.add(brushLabel);
        brushPanel.add(brushSizeChoice);

        Panel eastPanel = new Panel(new BorderLayout());
        eastPanel.add(clearPanel, "North");
        eastPanel.add(logoutPanel, "Center");
        eastPanel.add(brushPanel, "South");

        add(eastPanel, "East");
        pack();
        EventQueue.invokeLater(() -> setVisible(true));
    }

    public void updateTitle(String updateString) {
        EventQueue.invokeLater(() -> setTitle(getTitle() + ":c" + updateString));
    }

    public void drawLine(int color, int x1, int y1, int x2, int y2) {
        model.drawLine(IBProtocol.colors[color], x1, y1, x2, y2);
    }

    @Override
    public void dispose() {
        setVisible(false);
        super.dispose();
    }
}
