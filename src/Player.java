import java.util.Random;
import java.util.Scanner;

public class Player {
    String name;
    int score = 0;
    boolean ace = false;
    Scanner scan = new Scanner(System.in);
    boolean busted = false;
    Kartenstapel stapel;
    double money = 100;
    boolean blackjack = false;
    int bet = 0;

    public Player(Kartenstapel stapel, String name) {
        this.stapel = stapel;
        this.name = name;
    }
    public Player(Kartenstapel stapel, String name, double money) {
        this.stapel = stapel;
        this.name = name;
        this.money = money;
    }

    public Player() {
    }


    public int scoreUpdate(int temp) {
        if (temp == 11) {
            ace = true;
        }
        if (score + temp > 21 && !ace) {
            System.out.println(name + " busted");
            busted = true;
            return score = score + temp;
        } else {
            if (ace && (score + temp) > 21) {
                score = score - 10;
                ace = false;
            }
            return score = score + temp;
        }
    }

    public void zug() {
        Karten temp = karteziehen(stapel.kartenstapel);
        System.out.println(name + " drew: " + temp.name + " of " + temp.suit);
        System.out.println(name + "'s score now is: " + scoreUpdate(temp.kartenwert));
        System.out.println("-------");
        if (score == temp.kartenwert) {
            temp = karteziehen(stapel.kartenstapel);
            System.out.println(name + " drew: " + temp.name + " of " + temp.suit);
            System.out.println(name + "'s score now is: " + scoreUpdate(temp.kartenwert));
            if (score == 21) {
                System.out.println("blackjack");
                blackjack = true;
            } /*else if (score / 2 == temp.kartenwert) {
                System.out.println("do you want to split y/n");
                if (scan.nextLine().equals("y")) {
                    Splitplayer splitplayer1 = new Splitplayer(score / 2);
                    Splitplayer splitplayer2 = new Splitplayer(score / 2);
                }
            }*/ else {
                System.out.println("-------\n" + name + "'s turn:");
            }
        }
        if (score < 21) {
            if (bet <= money) {
                System.out.println("do you want to hit (h), stay (s) or double (d)?");
                String cont = scan.next();
                if (cont.equals("h")) {
                    zug();
                } else if (cont.equals("s")) {
                    System.out.println("-------");
                } else if (cont.equals("d")) {
                    money = money - bet;
                    bet = bet * 2;
                    Karten last = karteziehen(stapel.kartenstapel);
                    System.out.println(name + " drew: " + last.name + " of " + last.suit);
                    System.out.println(name + "'s score now is: " + scoreUpdate(last.kartenwert));
                    System.out.println("-------");
                }
            } else {
                System.out.println("do you want to hit (h) or stay (s)?");
                String cont = scan.next();
                if (cont.equals("h")) {
                    zug();
                } else if (cont.equals("s")) {
                    System.out.println("-------");
                }
            }
        }
    }

    public Karten karteziehen(Karten[] kartenstapel) {
        Random rnd = new Random();
        int r = rnd.nextInt(kartenstapel.length);
        Karten temp = kartenstapel[r];
        kartenstapel[r] = null;
        if (temp != null) {
            return temp;
        }
        return karteziehen(kartenstapel);
    }

    public int bet() {
        System.out.println(name + " please enter you're bet");
        bet = scan.nextInt();
        if (0 < bet && bet <= money) {
            money = money - bet;
        } else {
            System.out.println("please try again");
            bet();
        }
        return bet;
    }

    public boolean bankrupt() {
        if (money < 1) {
            System.out.println(name + " is broke");
            return true;
        }
        return false;
    }
}
