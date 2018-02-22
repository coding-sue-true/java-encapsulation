package com.company;

public class Main {

    public static void main(String[] args) {

//        This is a 'wrong' way of writing code, encapsulation it's not applied. We can easily change variables
//          values and override them, and that it's not good for a bigger software.

//        Player player = new Player();
//        player.name = "Tim";
//        player.health = 20;
//        player.weapon = "Sword";
//
//        int damage = 10;
//        player.loseHealth(damage);
//        System.out.println("Remaining health = " + player.healthRemaining());
//
//        damage = 11;
//        player.loseHealth(damage);
//        System.out.println("Remaining health = " + player.healthRemaining());



        EnhancedPlayer player = new EnhancedPlayer("Tim", 200, "sword");
        System.out.println("Initial health is " + player.getHealth());
    }
}
