package org.example;

import java.io.*;

public class Gladiator {
    public int constitution;
    public int strength;
    public int xp;
    public int dexterity;
    public int level;
    public int charisma;
    public int basicAttack;
    public int basicHp;
    public boolean haveShield;

    public Gladiator(int constitution, int strength, int xp, int dexterity, int level,
                     int charisma, int basicAttack, int basicHp, boolean haveShield) {
        this.constitution = constitution;
        this.strength = strength;
        this.xp = xp;
        this.dexterity = dexterity;
        this.level = level;
        this.charisma = charisma;
        this.basicAttack = basicAttack;
        this.basicHp = basicHp;
        this.haveShield = haveShield;
    }

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(constitution + "," + strength + "," + basicHp + "," + xp + ","
                    + dexterity + "," + level + "," + charisma + "," + basicAttack + ","
                    + haveShield);
        }
    }

    public static Gladiator loadFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            String[] data = line.split(",");
            return new Gladiator(
                    Integer.parseInt(data[0]),
                    Integer.parseInt(data[1]),
                    Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]),
                    Integer.parseInt(data[5]),
                    Integer.parseInt(data[6]),
                    Integer.parseInt(data[7]),
                    Boolean.parseBoolean(data[8])
            );
        }
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getBasicAttack() {
        return basicAttack;
    }

    public int getBasicHp() {
        return basicHp;
    }

    public boolean isHaveShield() {
        return haveShield;
    }

    public void setBasicHp(int basicHp) {
        this.basicHp = basicHp;
    }
}
