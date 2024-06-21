package org.example;

public class Monster {
    public int hp;
    public int basicAttack;
    public String name;
    public int strength;


    public int getStrength() {
        return strength;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getBasicAttack() {
        return basicAttack;
    }

    public String getName() {
        return name;
    }

    public Monster(int hp, int basicAttack, String name, int strength){
        this.hp = hp;
        this.basicAttack = basicAttack;
        this.name = name;
        this.strength = strength;
    }
}
