import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

public class AccountManagerGUI {
    private JFrame frame;
    private JPanel panel;
    private JButton login;
    private JButton signup;
    private JButton exitAndSave;
    private ArrayList<User> allUsers;
    private User currentUser;

    public AccountManagerGUI() {
        allUsers = new ArrayList<User>();
        frame = new JFrame("User Stats Window");
        frame.setSize(300,200);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.NORTH;

        c.gridx = 0;
        c.gridy = 0;
        login = new JButton("Login");
        login.addActionListener(this::actionPerformed);
        panel.add(login, c);

        c.gridy = 2;
        signup = new JButton("Sign up");
        signup.addActionListener(this::actionPerformed);
        panel.add(signup, c);

        c.gridy = 3;
        exitAndSave = new JButton("Exit and Save");
        exitAndSave.addActionListener(this::actionPerformed);
        panel.add(exitAndSave, c);

        frame.add(panel);
        frame.setVisible(true);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == login) {
            String username = JOptionPane.showInputDialog(frame, "Enter username");
            String password = JOptionPane.showInputDialog(frame, "Enter password");

            boolean usernameExist = false;
            int index = -1;
            User user;
            for (int i = 0; i < allUsers.size(); i++) {
                if (allUsers.get(i).getUsername().equals(username)) {
                    usernameExist = true;
                    index = i;
                }
            }
            if (!usernameExist) {
                JOptionPane.showMessageDialog(frame, "Username does not exist");
            }

            if (usernameExist) {
                boolean correctPassword = allUsers.get(index).getPassword().equals(password);
                if (!correctPassword) {
                    JOptionPane.showMessageDialog(frame, "Incorrect Password");
                } else {
                    JOptionPane.showMessageDialog(frame, "Welcome " + username);
                    user = allUsers.get(index);
                    currentUser = user;
                    user.start();
                    user.getProgressBar().updateData();
                }
            }
        } else if (source == signup) {
            String username = JOptionPane.showInputDialog(frame, "Enter username");
            String password = JOptionPane.showInputDialog(frame, "Enter password");
            User user = new User(0, 100, 0, 10);
            JOptionPane.showMessageDialog(frame, "Welcome " + username);
            currentUser = user;
            user.setUsername(username);
            user.setPassword(password);
            allUsers.add(user);
            user.start();
        } else if (source == exitAndSave) {
            save();
            save(currentUser);
            JOptionPane.showMessageDialog(frame, "Saving...Saved!");
            System.exit(0);
        }
    }
    public void save(User user) {
        try {
            FileOutputStream fos = new FileOutputStream("src/" + user.getUsername() + ".txt");
            String username = user.getUsername() + "\n";
            fos.write(username.getBytes());

            String password = user.getPassword() + "\n";
            fos.write(password.getBytes());

            String currentXP = user.getCurrentXP() + "\n";
            fos.write(currentXP.getBytes());

            String maxXP = user.getMaxXP() + "\n";
            fos.write(maxXP.getBytes());

            String level = user.getLevel() + "\n";
            fos.write(level.getBytes());

            String addXPValue = user.getAddXPValue() + "\n";
            fos.write(addXPValue.getBytes());

            String totalXP = user.getTotalXP() + "\n";
            fos.write(totalXP.getBytes());

            ArrayList<String> missions = user.getMissions();
            String strMission = "";
            for (int i = 0; i < missions.size() - 1; i++) {
                String mission = missions.get(i);
                strMission += mission + "|";
            }
            if (missions.size() > 0) {
                strMission += missions.get(missions.size() - 1) + "\n";
            }
            fos.write(strMission.getBytes());

            ArrayList<Integer> XPs = user.getXP();
            String strXP = "";
            for (int i = 0; i < XPs.size() - 1; i++) {
                String XP = XPs.get(i) + "";
                strXP += XP + "|";
            }
            if (XPs.size() > 0) {
                strXP += XPs.get(XPs.size() - 1) + "\n";
            }
            fos.write(strXP.getBytes());

            String XPCurrency = user.getRewards().getXPCurrency() + "\n";
            fos.write(XPCurrency.getBytes());

            ArrayList<String> items = user.getRewards().getInventory();
            String strItems = "";
            for (int i = 0; i < items.size() - 1; i++) {
                String item = items.get(i) + "";
                strItems += item + "|";
            }
            if (items.size() > 0) {
                strItems += items.get(items.size() - 1);
            }
            fos.write(strItems.getBytes());

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void save() {
        FileOutputStream f = null;
        try {
            f = new FileOutputStream("src/allUsernames.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < allUsers.size(); i++){
            String name = allUsers.get(i).getUsername() + "\n";
            try {
                f.write(name.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void retrieve() {
        File file = new File("src/allUsernames.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //retrieves all usernames ever created
        ArrayList<String> usernames = new ArrayList<String>();
        while (sc.hasNextLine()){
            String str = sc.nextLine();
            usernames.add(str);
        }
        //for every username, there is an account
        for (int i = 0; i < usernames.size(); i++){
            String username = usernames.get(i);
            File f = new File("src/" + username + ".txt");
            Scanner sc2 = null;
            try {
                sc2 = new Scanner(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            sc2.nextLine();
            String password = sc2.nextLine();
            int currentXp = Integer.parseInt(sc2.nextLine());
            int maxXP = Integer.parseInt(sc2.nextLine());
            int level = Integer.parseInt(sc2.nextLine());
            int addXPValue = Integer.parseInt(sc2.nextLine());
            int totalXP = Integer.parseInt(sc2.nextLine());

            //creating user
            User user = new User(currentXp, maxXP, level, addXPValue);
            allUsers.add(user);
            user.setUsername(username);
            user.setPassword(password);
            user.setTotalXP(totalXP);

            String strMissions = sc2.nextLine();
            ArrayList<String> missions = new ArrayList<String>();
            int index = strMissions.indexOf("|");
            while (index != -1) {
                String mission = strMissions.substring(0, index);
                missions.add(mission);
                strMissions = strMissions.substring(index + 1);
                index = strMissions.indexOf("|");
            }
            missions.add(strMissions);

            user.setMissions(missions);

            String strXP = sc2.nextLine();
            ArrayList<Integer> XPs = new ArrayList<Integer>();
            int index2 = strXP.indexOf("|");
            while (index2 != -1) {
                String XP = strXP.substring(0, index2);
                XPs.add(Integer.parseInt(XP));
                strXP = strXP.substring(index2 + 1);
                index2 = strXP.indexOf("|");
            }
            XPs.add(Integer.parseInt(strXP));

            int XPCurrency = Integer.parseInt(sc2.nextLine());

            ArrayList<String> inventory = new ArrayList<String>();
            if (sc2.hasNextLine()) {
                String strInventory = sc2.nextLine();
                int index3 = strInventory.indexOf("|");
                while (index3 != -1) {
                    String item = strInventory.substring(0, index3);
                    inventory.add(item);
                    strInventory = strInventory.substring(index3 + 1);
                    index3 = strInventory.indexOf("|");
                }
                inventory.add(strInventory);
            }

            RewardsGUI rewards = new RewardsGUI(user);
            rewards.setInventory(inventory);
            rewards.setXPCurrency(XPCurrency);
            user.setRewards(rewards);

            user.updateLevel();
            user.setXP(XPs);
            sc2.close();
        }
        sc.close();
    }
}
