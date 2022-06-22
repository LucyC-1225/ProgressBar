import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class RewardsGUI {
    private User user;
    private ArrayList<String> inventory;
    private int XPCurrency;
    //home
    private JFrame frame;
    private JPanel panel;
    private JLabel XPCurrencyLabel;
    private JButton inventoryButton;
    private JButton wishButton;
    private JButton simulateButton;
    //inventory Window
    private JPanel panel2;
    private DefaultListModel data;
    private JList listOfItems;
    private JButton back;
    private JButton viewDetails;
    //wish simulation
    private GenshinData genshinData;
    //colors

    public RewardsGUI(User user) {
        this.user = user;
        inventory = new ArrayList<String>();
        frame = new JFrame("Rewards");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        genshinData = new GenshinData();
    }

    public void start() {
        homeWindow();
    }
    public void homeWindow() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        XPCurrencyLabel = new JLabel("XP Currency amount: " + getXPCurrency());
        panel.add(XPCurrencyLabel, c);

        c.gridy = 1;
        inventoryButton = new JButton("Inventory");
        inventoryButton.addActionListener(this::actionPerformed);
        panel.add(inventoryButton, c);

        c.gridy = 2;
        wishButton = new JButton("Wish");
        wishButton.addActionListener(this::actionPerformed);
        panel.add(wishButton, c);

        c.gridy = 3;
        simulateButton = new JButton("Simulate");
        simulateButton.addActionListener(this::actionPerformed);
        panel.add(simulateButton, c);

        frame.add(panel);
        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == inventoryButton) {
            inventoryWindow();
        } else if (source == wishButton) {
            String result = wishSimulation();
            inventory.add(result);
            UIManager um = new UIManager();
            if (findCharacter(result) == null) {
                Weapon weapon = findWeapon(result);
                int rarity = weapon.getRarity();
                if (rarity == 4) {
                    um.put("OptionPane.background", new Color(163, 64, 199));
                } else if (rarity == 5) {
                    um.put("OptionPane.background",new Color(255,170,74));
                }
            } else {
                Character character = findCharacter(result);
                int rarity = character.getRarity();
                if (rarity == 4) {
                    um.put("OptionPane.background", new Color(163, 64, 199));
                } else if (rarity == 5) {
                    um.put("OptionPane.background",new Color(255,170,74));
                }
            }
            JOptionPane.showMessageDialog(frame, result);
            um.put("OptionPane.background", Color.white);
        } else if (source == back) {
            frame.remove(panel2);
            frame.validate();
            frame.repaint();
            homeWindow();
            frame.revalidate();
        } else if (source == viewDetails) {
            String details = "";
            int index = listOfItems.getSelectedIndex();
            String itemName = inventory.get(index);
            UIManager um = new UIManager();
            if (findCharacter(itemName) == null) {
                Weapon weapon = findWeapon(itemName);
                details += "Name: " + weapon.getName() + "\n";
                details += "Rarity: " + weapon.getRarity() + " star\n";
                details += "Type: " + weapon.getType();
                int rarity = weapon.getRarity();
                if (rarity == 4) {
                    um.put("OptionPane.background", new Color(163, 64, 199));
                } else if (rarity == 5) {
                    um.put("OptionPane.background",new Color(255,170,74));
                }
            } else {
                Character character = findCharacter(itemName);
                details += "Name: " + character.getName() + "\n";
                details += "Description: " + character.getDescription() + "\n";
                details += "Rarity: " + character.getRarity() + " star\n";
                details += "Weapon used: " + character.getWeapon() + "\n";
                int rarity = character.getRarity();
                if (rarity == 4) {
                    um.put("OptionPane.background", new Color(163, 64, 199));
                } else if (rarity == 5) {
                    um.put("OptionPane.background",new Color(255,170,74));
                }
            }
            JOptionPane.showMessageDialog(frame, details);
            um.put("OptionPane.background", Color.white);
        } else if (source == simulateButton) {
            int numOfWishes = Integer.parseInt(JOptionPane.showInputDialog(frame, "How many times do you want to wish?"));
            for (int i = 0; i < numOfWishes; i++) {
                String result = wishSimulation();
                inventory.add(result);
                UIManager um = new UIManager();
                if (findCharacter(result) == null) {
                    Weapon weapon = findWeapon(result);
                    int rarity = weapon.getRarity();
                    if (rarity == 4) {
                        um.put("OptionPane.background", new Color(163, 64, 199));
                        JOptionPane.showMessageDialog(frame, result);
                    } else if (rarity == 5) {
                        um.put("OptionPane.background",new Color(255,170,74));
                        JOptionPane.showMessageDialog(frame, result);
                    }
                    um.put("OptionPane.background", Color.white);
                } else {
                    Character character = findCharacter(result);
                    int rarity = character.getRarity();
                    if (rarity == 4) {
                        um.put("OptionPane.background", new Color(163, 64, 199));
                        JOptionPane.showMessageDialog(frame, result);
                    } else if (rarity == 5) {
                        um.put("OptionPane.background",new Color(255,170,74));
                        JOptionPane.showMessageDialog(frame, result);
                    }
                    um.put("OptionPane.background", Color.white);
                }
            }
        }
    }
    public Character findCharacter(String name) {
        ArrayList<Character> characters = genshinData.getCharacters();
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).getName().equals(name)) {
                return characters.get(i);
            }
        }
        return null;
    }
    public Weapon findWeapon(String name) {
        ArrayList<Weapon> weapons = genshinData.getWeapons();
        for (int i = 0; i < weapons.size(); i++) {
            if (weapons.get(i).getName().equals(name)) {
                return weapons.get(i);
            }
        }
        return null;
    }
    public void updateData() {
        XPCurrencyLabel.setText("XP Currency amount: " + getXPCurrency());
    }
    public void inventoryWindow() {
        frame.remove(panel); //remove home panel
        frame.validate();
        frame.repaint(); //after removing you have to validate and then repaint

        panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        data = new DefaultListModel();
        for (int i = 0; i < inventory.size(); i++) {
            data.addElement((i + 1) + " | " + inventory.get(i) + " |");
        }
        listOfItems = new JList(data);
        listOfItems.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listOfItems.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(listOfItems);
        listScroller.setPreferredSize(new Dimension(300, 300));
        panel2.add(listScroller, c);

        c.gridy = 1;
        back = new JButton("Back");
        back.addActionListener(this::actionPerformed);
        panel2.add(back, c);

        c.gridy = 2;
        viewDetails = new JButton("View Details");
        viewDetails.addActionListener(this::actionPerformed);
        panel2.add(viewDetails, c);

        frame.add(panel2);
        frame.revalidate();
    }
    public String wishSimulation() {
        XPCurrency -= 10;
        updateData();
        String wishResult = "";
        int random = (int) (Math.random() * 100) + 1;
        int rarity = 0;
        if (random <= 5) { //5 star - 5 percent chance
            rarity = 5;
        } else if (random <= 20) { //4 star 15 percent chance
            rarity = 4;
        } else {
            rarity = 3;
        }
        ArrayList<Character> characters = genshinData.getCharacters();
        ArrayList<Weapon> weapons = genshinData.getWeapons();
        boolean isCharacter = (int) (Math.random() * 2) == 0;
        if (isCharacter && rarity != 3) {
            int index = (int) (Math.random() * characters.size());
            String charaterName = characters.get(index).getName();
            while (characters.get(index).getRarity() != rarity) {
                index = (int) (Math.random() * characters.size());
                charaterName = characters.get(index).getName();
            }
            return charaterName;
        } else {
            int index = (int) (Math.random() * weapons.size());
            String weaponName = weapons.get(index).getName();
            while (weapons.get(index).getRarity() != rarity) {
                index = (int) (Math.random() * weapons.size());
                weaponName = weapons.get(index).getName();
            }
            return weaponName;
        }
    }
    public String getInventoryInfo() {
        String str = "";
        for (int i = 0; i < inventory.size(); i++) {
            str += (i + 1) + " | " + inventory.get(i) + " |\n";
        }
        return str;
    }
    public ArrayList<String> getInventory() {
        return inventory;
    }

    public int getXPCurrency() {
        return XPCurrency;
    }

    public void setInventory(ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    public void setXPCurrency(int XPCurrency) {
        this.XPCurrency = XPCurrency;
    }
}
