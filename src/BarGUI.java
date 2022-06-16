import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BarGUI extends JFrame implements ActionListener {
    boolean isAdministrator;
    private JFrame frame;
    private JPanel panel;
    private JProgressBar bar;
    private JButton addXP;
    private JLabel levelLabel;
    private JLabel currentXPLabel;
    private JLabel maxXPLabel;
    private JButton changeMaxXP;
    private JLabel addXPValueLabel;
    private JButton changeaddXPValue;
    private User user;

    public BarGUI(User user, boolean isAdministrator) {
        this.user = user;
        this.isAdministrator = isAdministrator;
    }
    public void start() {
        frame = new JFrame("User Stats Window");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //frame.setLayout(new GridLayout(1, 1)); //BRUH u dont need this at all

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        //level label
        c.gridx = 0;
        c.gridy = 0;
        //c.fill = GridBagConstraints.HORIZONTAL; do not do this unless you want all horizontal values to be same
        levelLabel = new JLabel("Current Level: " + user.getLevel());
        panel.add(levelLabel, c);

        //progress bar
        c.gridy = 1;
        c.ipady = 20; //large
        c.ipadx = 80; //large
        bar = new JProgressBar(SwingConstants.HORIZONTAL);
        bar.setStringPainted(true);
        bar.setValue(0);
        panel.add(bar, c);

        c.ipadx = 0; //reset
        c.ipady = 0; //reset

        //Current XP Label
        c.gridy = 2;
        currentXPLabel = new JLabel("Current XP: " + user.getCurrentXP());
        panel.add(currentXPLabel, c);

        //Map XP Label
        c.gridy = 3;
        maxXPLabel = new JLabel("Max XP: " + user.getMaxXP());
        panel.add(maxXPLabel, c);

        //add XP Value Label
        c.gridy = 4;
        addXPValueLabel = new JLabel("Add XP Value: " + user.getAddXPValue());
        panel.add(addXPValueLabel, c);

        //add XP Button
        c.gridy = 5;
        addXP = new JButton("Add XP");
        addXP.addActionListener(this);
        panel.add(addXP, c);

        //Change Max XP button
        c.gridy = 6;
        changeMaxXP = new JButton("Change Max XP");
        changeMaxXP.addActionListener(this);
        panel.add(changeMaxXP, c);

        //change add XP value Button
        c.gridy = 7;
        changeaddXPValue = new JButton("Change Add XP Value");
        changeaddXPValue.addActionListener(this);
        panel.add(changeaddXPValue, c);

        frame.add(panel);
        frame.setVisible(true);
        /*
        1. create frame with size
        2. create panel with GridBagLayout and instantiate a GridBagConstraints object
        3. before creating your first component, set gridx and gridY. If you want to
        stack vertically, you're going to have to change gridY (weird)
        4. Create first component and do panel.add(componentName, GridBagConstraintsName)
        5. Repeat steps 3-4 for the other components
        6. add your panel to your frame
        7. set your frame to true
        note that if you want a space between your components....
        BarPanel.add(Box.createRigidArea(new Dimension(0, 100))); treat this as another component
        Also if you want to resize your components you have to do ipadX and ipadY. Setting the size or bounds
        is useless in this manager since it seems to be overridden
         */
        if (!isAdministrator) {
            addXP.setEnabled(false);
            changeMaxXP.setEnabled(false);
            changeaddXPValue.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == addXP) {
            user.setCurrentXP(user.getCurrentXP() + user.getAddXPValue());
            currentXPLabel.setText("Current XP: " + user.getCurrentXP());
            int percentage = (int) ((double) user.getCurrentXP() / user.getMaxXP() * 100);
            bar.setValue(percentage);
            if (bar.getValue() == 100) { //when xp is maxed out
                bar.setValue(0);
                user.setLevel(user.getLevel() + 1);
                levelLabel.setText("Current Level: " + user.getLevel());
                user.setCurrentXP(0);
                currentXPLabel.setText("Current XP: " + user.getCurrentXP());
            }
        } else if (source == changeMaxXP) {
            JFrame frame = new JFrame("Change Max XP");
            user.setMaxXP(Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the new Max XP")));
            maxXPLabel.setText("Max XP: " + user.getMaxXP());
        } else if (source == changeaddXPValue) {
            JFrame frame = new JFrame("Change Max XP");
            user.setAddXPValue(Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the new Add XP Value")));
            addXPValueLabel.setText("Add XP Value: " + user.getAddXPValue());
        }
    }

    public void updateData() {
        user.updateLevel();
        currentXPLabel.setText("Current XP: " + user.getCurrentXP());
        user.setMaxXP((user.getLevel()) * 10 + 100);
        maxXPLabel.setText("Max XP: " + user.getMaxXP());
        addXPValueLabel.setText("Add XP Value: " + user.getAddXPValue());
        levelLabel.setText("Current Level: " + user.getLevel());
        currentXPLabel.setText("Current XP: " + user.getCurrentXP());

        int percentage = (int) ((double) user.getCurrentXP() / user.getMaxXP() * 100);
        bar.setValue(percentage);
    }
}
