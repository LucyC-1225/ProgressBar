import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class HQGUI {
    User user;
    private JFrame frame;
    private JPanel panel;

    //home window
    private JButton report;
    private JButton viewMissions;
    private JButton editMissions;

    //Viewing all missions window
    private JTextArea allMissions;
    private JButton back;
    private JPanel panel2;

    //editing missions window
    private JPanel panel3;
    private JList listOfMissions;
    private JButton delete;
    private JButton edit;
    private DefaultListModel data;

    public HQGUI(User user) {
        this.user = user;
        frame = new JFrame("HQ Window");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }
    public void start() {
        homeWindow();
    }
    public void homeWindow() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        //report button
        c.gridx = 0;
        c.gridy = 0;
        report = new JButton("Report a mission");
        report.addActionListener(this::actionPerformed);
        panel.add(report, c);

        //viewMissions button
        c.gridy = 1;
        viewMissions = new JButton("View completed missions");
        viewMissions.addActionListener(this::actionPerformed);
        panel.add(viewMissions, c);

        //editMissions button - bring to new window
        c.gridy = 2;
        editMissions = new JButton("Edit a mission");
        editMissions.addActionListener(this::actionPerformed);
        panel.add(editMissions, c);


        frame.add(panel);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == report) {
            String mission = JOptionPane.showInputDialog(frame, "Report in the format: \"Date: description\"");
            int XP = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter XP earned"));
            user.reportMission(mission, XP);
            JOptionPane.showMessageDialog(frame, "Mission reported, thank you");
        } else if (source == viewMissions) {
            loadAllMissions();
        } else if (source == editMissions) {
            loadEditMission();
        } else if (source == delete) {
            int index = listOfMissions.getSelectedIndex();
            data.remove(index);
            user.removeMission(index);
        } else if (source == edit) {
            int index = listOfMissions.getSelectedIndex();
            String newDescription = JOptionPane.showInputDialog(frame, "Report in the format: \"Date: description\"");
            int newXP = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter XP earned"));
            user.editMission(index, newDescription, newXP);
            data.set(index, (index + 1) + " | " + newDescription + " | +" + newXP + "XP |");
        } else if (source == back) {
            if (panel2 != null) {
                frame.remove(panel2);
            }
            if (panel3 != null) {
                frame.remove(panel3);
            }
            frame.validate();
            frame.repaint();
            homeWindow();
            frame.revalidate();
            /*
            1. remove the component from frame
            2. validate the frame
            3. repaint the frame
            4. add the component that you want to the frame
            5. revalidate the frame
             */
        }
    }
    public void loadAllMissions() {
        frame.remove(panel); //remove home panel
        frame.validate();
        frame.repaint(); //after removing you have to validate and then repaint

        panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        allMissions = new JTextArea(20, 35);
        allMissions.setText(user.getMissionInfo());
        allMissions.setEditable(false);
        panel2.add(allMissions, c);

        c.gridy = 1;
        back = new JButton("Back");
        back.addActionListener(this::actionPerformed);
        panel2.add(back, c);

        frame.add(panel2);
        frame.revalidate();
    }
    public void loadEditMission() {
        //list, user select what they want to edit / delete (both buttons). the mission number can be the index
        //for editing,
        frame.remove(panel);
        frame.validate();
        frame.repaint();

        panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        data = new DefaultListModel();
        for (int i = 0; i < user.getMissions().size(); i++) {
            data.addElement((i + 1) + " | " + user.getMissions().get(i) + " | +" + user.getXP().get(i) + "XP |");
        }
        listOfMissions = new JList(data);
        listOfMissions.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listOfMissions.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(listOfMissions);
        listScroller.setPreferredSize(new Dimension(250, 80));
        panel3.add(listScroller, c);

        c.gridy = 1;
        delete = new JButton("Delete");
        panel3.add(delete, c);
        delete.addActionListener(this::actionPerformed);

        c.gridy = 2;
        edit = new JButton("Edit");
        panel3.add(edit, c);
        edit.addActionListener(this::actionPerformed);

        back = new JButton("Back");
        c.gridy =  3;
        back.addActionListener(this::actionPerformed);
        panel3.add(back, c);

        frame.add(panel3);
        frame.revalidate();
    }
}
