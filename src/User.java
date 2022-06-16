import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private int currentXP;
    private int maxXP;
    private int level;
    private int addXPValue;
    private int totalXP;
    private ArrayList<String> missions;
    private ArrayList<Integer> XP;
    private BarGUI progressBar;
    private HQGUI HQ;
    private RewardsGUI rewards; //rewardsGUI

    public User(int currentXP, int maxXP, int level, int addXPValue) {
        this.currentXP = currentXP;
        this.maxXP = maxXP;
        this.level = level;
        this.addXPValue = addXPValue;
        missions = new ArrayList<String>();
        XP = new ArrayList<Integer>();
        progressBar = new BarGUI(this, true);
        HQ = new HQGUI(this);
        rewards = new RewardsGUI(this);
    }
    public void start() {
        progressBar.start();
        HQ.start();
        rewards.start();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getCurrentXP() {
        return currentXP;
    }

    public int getMaxXP() {
        return maxXP;
    }

    public int getLevel() {
        return level;
    }

    public int getAddXPValue() {
        return addXPValue;
    }

    public ArrayList<String> getMissions() {
        return missions;
    }

    public ArrayList<Integer> getXP() {
        return XP;
    }

    public BarGUI getProgressBar() {
        return progressBar;
    }

    public HQGUI getHQ() {
        return HQ;
    }

    public int getTotalXP() {
        return totalXP;
    }

    public RewardsGUI getRewards() {
        return rewards;
    }

    public void setRewards(RewardsGUI rewards) {
        this.rewards = rewards;
    }

    public void setTotalXP(int totalXP) {
        this.totalXP = totalXP;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCurrentXP(int currentXP) {
        this.currentXP = currentXP;
    }

    public void setMaxXP(int maxXP) {
        this.maxXP = maxXP;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setAddXPValue(int addXPValue) {
        this.addXPValue = addXPValue;
    }

    public void setMissions(ArrayList<String> missions) {
        this.missions = missions;
    }

    public void setXP(ArrayList<Integer> XP) {
        this.XP = XP;
    }

    public void updateLevel() {
        int fakeLevel = 0;
        int fakeTotalXP = totalXP;
        while (fakeTotalXP - (fakeLevel * 10 + 100) >= 0) {
            fakeTotalXP -= (fakeLevel) * 10 + 100;
            fakeLevel++;
        }
        currentXP = fakeTotalXP;
        maxXP = (fakeLevel) * 10 + 100;
        level = fakeLevel;
    }

    public void reportMission(String mission, int XP) {
        missions.add(mission);
        this.XP.add(XP);
        currentXP += XP;
        totalXP += XP;
        rewards.setXPCurrency(rewards.getXPCurrency() + XP);
        progressBar.updateData();
        rewards.updateData();
    }
    public String getMissionInfo() {
        String str = "";
        str += "Mission Number | Mission Detail | XP earned |\n...\n";
        for (int i = 0; i < missions.size(); i++) {
            str += (i + 1) + " | " + missions.get(i) + " | +" + XP.get(i) + "XP |\n";
        }
        return str;
    }
    public void removeMission(int index) {
        missions.remove(index);
        int removed = XP.remove(index);
        currentXP -= removed;
        totalXP -= removed;
        rewards.setXPCurrency(rewards.getXPCurrency() - removed);
        progressBar.updateData();
        rewards.updateData();
    }
    public void editMission(int index, String description, int XP) {
        missions.set(index, description);
        currentXP -= this.XP.get(index); //it's possible for negative XP to exist
        totalXP -= this.XP.get(index);
        rewards.setXPCurrency(rewards.getXPCurrency() - this.XP.get(index));
        this.XP.set(index, XP);
        currentXP += XP;
        totalXP += XP;
        rewards.setXPCurrency(rewards.getXPCurrency() + XP);
        progressBar.updateData();
        rewards.updateData();
    }
}
