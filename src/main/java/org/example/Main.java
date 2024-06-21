package org.example;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import static java.lang.Math.sqrt;

public class Main{
    public static Gladiator gladiator = null;
    public static Scanner scanner = new Scanner(System.in);
    private static int NeedXpToNextLevel = 100;
    private static final String[] nameMon = {"dragon", "tiger", "alien",
            "panther", "elf", "lion", "phoenix"};

    public static void chooseGladiator(Scanner scanner) {
        System.out.println("\n1. Bestiary\n2. Velit\n3. Hoplomach");
        System.out.print("Choose type gladiator: ");
        int сhoice1 = scanner.nextInt();
        switch (сhoice1) {
            case 1 -> {
                gladiator = new Bestiary(1, 2, 0, 4, 1,
                        1, 12, 40, false);
                showAttributes(gladiator);
            }
            case 2 -> {
                gladiator = new Velit(1, 3, 0, 0, 1,
                        1, 10, 30, true);
                showAttributes(gladiator);

            }
            case 3 -> {
                gladiator = new Hoplomach(1, 3, 0, 2, 1,
                        2, 14, 35, false);
                showAttributes(gladiator);
            }

        }
    }
    public static void main(String[] args) {
        System.out.println("1. Start new game\n2. Load game");
        System.out.print("Choose what to do: ");
        int choice1 = scanner.nextInt();

        if(choice1 == 1) {
            chooseGladiator(scanner);
        } else if(choice1 == 2) {
            try {
                gladiator = Gladiator.loadFromFile("gladiator_save.txt");
                System.out.println("Game loaded successfully.");
            } catch (IOException e) {
                System.out.println("Failed to load game, starting a new one.");
                chooseGladiator(scanner);
            }
        }

        while (true) {
            System.out.println("\n1. Travel\n2. Show attributes \n3. Save game and exit");
            System.out.print("Choose what to do: ");
            int choice2 = scanner.nextInt();
            switch (choice2) {
                case 1 -> {
                    travel(gladiator);
                }
                case 2 -> {
                    showAttributes(gladiator);
                }
                case 3 -> {
                    System.out.println("Game saved successfully.");
                    try {
                        gladiator.saveToFile("gladiator_save.txt");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;

                }
            }
        }
    }

    public static void showAttributes(Gladiator gladiator){
        System.out.printf(
                """
                        \n1. Constitution (%d)
                        2. Strength (%d)
                        3. HP (%d)
                        4. XP (%d)
                        5. Dexterity (%d)
                        6. Level (%d)
                        7. Charisma (%d)
                        8. BasicAttack (%d)
                        9. HaveShield (%s)\n""",
                gladiator.getConstitution(),
                gladiator.getStrength(),
                gladiator.getBasicHp(),
                gladiator.getXp(),
                gladiator.getDexterity(),
                gladiator.getLevel(),
                gladiator.getCharisma(),
                gladiator.getBasicAttack(),
                gladiator.isHaveShield()
        );
    }

    public static void travel(Gladiator gladiator) {
        Monster monster = new Monster(
                randNumber(15, 20 * gladiator.getLevel()),
                randNumber(5, 10 + gladiator.getLevel()),
                nameMon[randNumber(0, nameMon.length)],
                randNumber(1, 3)
        );

        System.out.printf("You meet: %s, attack: %d, hp: %d \n", monster.getName(),
                monster.getBasicAttack(), monster.getHp());
        System.out.println("\n1. Fight\n2. Agree/Appease\n3. Run away");
        System.out.print("Choose: ");
        int choice3 = scanner.nextInt();

        switch (choice3) {
            case 1 -> fight(monster, gladiator);
            case 2 -> agreeOrAppease(monster, gladiator);
            case 3 -> RunAway(monster, gladiator);
        }
    }

    public static void agreeOrAppease(Monster monster, Gladiator gladiator) {
        int number = randNumber(0, 100);
        int number1 = randNumber(0, 50) + gladiator.getCharisma();
        if (number1 >= number) {
            System.out.println("You win. You get 10xp");
            gladiator.setXp(gladiator.getXp() + 10);
        } else {
            System.out.println("NOOOO. FIGHT!");
            fight(monster, gladiator);
        }
    }

    public static void RunAway(Monster monster, Gladiator gladiator) {
        int number = randNumber(0, 100);
        int number1 = randNumber(0, 50) + gladiator.getCharisma();
        if (number1 >= number) {
            System.out.println("You were able to successfully escape from the monster. " +
                    "You get 5xp");
            gladiator.setXp(gladiator.getXp() + 5);
        } else {
            System.out.println("NOOOO. FIGHT!");
            fight(monster, gladiator);
        }
    }

    public static void fight(Monster monster, Gladiator gladiator) {
        int gladHp = gladiator.getBasicHp() + gladiator.getConstitution() * 5;
        double combatEffect = sqrt(monster.getHp() * monster.getBasicAttack());
        int xp = (int) (combatEffect / 2);
        while (monster.hp > 0 && gladHp > 0) {
            System.out.println("\n1. Attack\n2. Block");
            System.out.print("Choose actions: ");
            int choiceMonster = randNumber(1, 2);
            int damageGlad = 0;
            int damageMons = 0;
            int choice4 = scanner.nextInt();
            if (choice4 == 1) {
                damageGlad = (randNumber(gladiator.getBasicAttack(),
                        gladiator.getBasicAttack() + gladiator.getStrength()));
            }
            if (choiceMonster == 1) {
                damageMons = (randNumber(monster.getBasicAttack(),
                        monster.getBasicAttack() + monster.getStrength()));
            }
            if (choice4 == 2) {
                if (gladiator.isHaveShield()) {
                    damageMons /= 5;
                } else {
                    damageMons /= 2;
                }
                System.out.println("You placed a block");
            }
            if (choiceMonster == 2) {
                damageGlad /= 2;
                System.out.println("Monster placed a block");
            }
            gladHp -= damageMons;
            monster.setHp(monster.getHp() - damageGlad);
            System.out.println("\nYou applied " + damageGlad + " damage");
            if (damageMons >= 0) {
                System.out.println("The enemy hurt you " + damageMons + " damage\n");
            }
            if (damageMons < 0 && gladiator.isHaveShield()) {
                System.out.println("The enemy hurt you " + damageMons +
                        " damage. Because of the shield\n");
            }

            if (gladHp <= 0) {
                gladHp = 0;
                System.out.println("\nYou lose! You lost " + xp + "xp");
                gladiator.setXp((gladiator.getXp() - xp));
            }
            if (monster.getHp() <= 0) {
                monster.setHp(0);
                System.out.println("\nYou win! You gain " + xp + "xp");
                gladiator.setXp((gladiator.getXp() + xp));
                levelUp(gladiator);
            }

            System.out.printf(
                    """
                            1. Your HP %d
                            2. Enemy HP %d\n""",
                    gladHp,
                    monster.getHp()
            );
        }
    }

    private static void upgradeAttributes(Gladiator gladiator) {
        System.out.printf(
                """
                         1. Constitution (%d)
                         2. Strength (%d)
                         3. Dexterity (%d)
                         4. Charisma (%d)
                        What do you want to upgrade? Write number:""" + " ",
                gladiator.getConstitution(),
                gladiator.getStrength(),
                gladiator.getDexterity(),
                gladiator.getCharisma()
        );
        int choice5 = scanner.nextInt();

        switch (choice5) {
            case 1 -> gladiator.setConstitution(gladiator.getConstitution() + 1);
            case 2 -> gladiator.setStrength(gladiator.getStrength() + 1);
            case 3 -> gladiator.setDexterity(gladiator.getDexterity() + 1);
            case 4 -> gladiator.setCharisma(gladiator.getCharisma() + 1);
        }
    }

    private static void levelUp(Gladiator gladiator) {
        if (gladiator.getXp() >= NeedXpToNextLevel) {
            gladiator.setXp(0);
            gladiator.setLevel(gladiator.getLevel() + 1);
            System.out.println("\nCongratulations! You leveled up to "
                    + gladiator.getLevel() + " level");
            int points = 5;
            NeedXpToNextLevel *= 1.4;
            while (points > 0) {
                upgradeAttributes(gladiator);
                points--;
            }
        }
    }

    public static int randNumber(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }
}