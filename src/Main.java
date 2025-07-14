import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Kartenstapel stapel = new Kartenstapel();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number of Players (1-5)");
        int n = scan.nextInt();
        Player[] players = new Player[n];
        Dealer dealer = new Dealer(stapel);
        for (int i = 0; i < n; i++) {
            System.out.println("player " + (i + 1) + " enter your name");
            Player player1 = new Player(stapel, scan.next());
            players[i] = player1;
        }
        game(n, players, dealer);
    }

    public static void game(int n, Player[] players, Dealer dealer) {
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < n; i++) {
            players[i].bet();
        }
        dealer.zug1();
        for (int i = 0; i < n; i++) {
            players[i].initialzug();
            if (players[i].split) {
                Playerhands splithand1 = new Playerhands(players[i].score / 2, players[i].name + "'s first splithand", players[i].stapel, players[i].ace, players[i].bet, 0);
                players[i].hands[0] = splithand1;
                Playerhands splithand2 = new Playerhands(players[i].score / 2, players[i].name + "'s second splithand", splithand1.stapel, players[i].ace, players[i].bet, 0);
                players[i].hands[1] = splithand2;
                splithand1.zug1();
                splithand1.zug();
                splithand2.zug1();
                splithand2.zug();
            } else if (!players[i].doubled) {
                players[i].zug();
            }
        }
        if (!allBusted(players, n)) {
            System.out.println("dealer has: " + dealer.score);
            dealer.zug2();
            if (!dealer.busted || !dealer.blackjack) {
                dealer.zug();
                for (int i = 0; i < n; i++) {
                    if (!players[i].split) {
                        scoreCheck(players[i], dealer);
                    } else {
                        scoreCheck(players[i].hands[0], dealer);
                        scoreCheck(players[i].hands[1], dealer);
                        players[i].money = players[i].money + players[i].hands[1].money + players[i].hands[0].money;
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.println(players[i].name + "'s money is at: " + players[i].money + "€ now");
        }
        System.out.println("Do you want to play again?  y/n");
        if (scan.next().equals("y")) {
            Kartenstapel stapel = new Kartenstapel();
            dealer.score = 0;
            for (int i = 0; i < n; i++) {
                Player temp = players[i];
                players[i] = new Player(stapel, temp.name, temp.money);
                dealer = new Dealer(stapel);
            }
            edit(players, n, scan, stapel);
            for (int i = 0; i < n; i++) {
                if (players[i].bankrupt()) {
                    System.out.println("we round up to 1€ for " + players[i].name + " the brokey");
                    players[i].money = 1;
                }
            }
            game(n, players, dealer);
        }
    }

    public static void deletePlayer(Player[] players, int n, Scanner scan) {
        if (n != 1) {
            System.out.println("witch player (1-" + n + ")?");
            switch (scan.nextInt()) {
                case 1:
                    for (int i = 0; i < n - 1; i++) {
                        players[i] = players[i + 1];
                    }
                    n = n - 1;
                    break;
                case 2:
                    for (int i = 1; i < n - 1; i++) {
                        players[i] = players[i + 1];
                    }
                    n = n - 1;
                    break;
                case 3:
                    for (int i = 2; i < n - 1; i++) {
                        players[i] = players[i + 1];
                    }
                    n = n - 1;
                    break;
                case 4:
                    for (int i = 3; i < n - 1; i++) {
                        players[i] = players[i + 1];
                    }
                    n = n - 1;
                    break;
                case 5:
                    for (int i = 4; i < n - 1; i++) {
                        players[i] = players[i + 1];
                    }
                    n = n - 1;
            }
        } else {
            System.out.println("minimum amount of players is already reached");
        }
    }

    public static void addPlayer(Player[] players, int n, Scanner scan, Kartenstapel stapel) {
        if (n < 5) {
            n++;
            Player[] players2 = new Player[n];
            players = players2;
            System.out.println("new player enter your name");
            players[n - 1] = new Player(stapel, scan.next());
        } else {
            System.out.println("maximum amount of players already reached");
        }
    }

    public static void edit(Player[] players, int n, Scanner scan, Kartenstapel stapel) {
        System.out.println("Do you want to change the players? y/n");
        if (scan.next().equals("y")) {
            System.out.println("Do you want to delete (d) or add (a) a Player?");
            String cont = scan.next();
            if (cont.equals("d")) {
                deletePlayer(players, n, scan);
            } else if (cont.equals("a")) {
                addPlayer(players, n, scan, stapel);
            }
            edit(players, n, scan, stapel);
        }
    }

    public static boolean allBusted(Player[] players, int n) {
        int b = 0;
        for (int i = 0; i < n; i++) {
            if (players[i].busted) {
                b = b + 1;
            }
            if (b == n) {
                return true;
            }
        }
        return false;
    }

    public static void scoreCheck(Player player, Dealer dealer) {
        if (!dealer.busted) {
            if (!player.busted) {
                if (dealer.score == player.score) {
                    if (!player.blackjack && !dealer.blackjack || player.blackjack && dealer.blackjack) {
                        System.out.println("it's a push");
                        player.money = player.money + player.bet;
                    } else if (player.blackjack && !dealer.blackjack) {
                        System.out.println(player.name + " wins");
                        player.money = player.money + player.bet * 2 + player.bet * 0.5;
                    } else {
                        System.out.println("dealer wins");
                    }
                } else if (dealer.score > player.score) {
                    System.out.println("dealer wins");
                } else {
                    System.out.println(player.name + " wins");
                    player.money = player.money + player.bet * 2;
                    if (player.blackjack) {
                        player.money = player.money + player.bet * 0.5;
                    }
                }
            }
        } else {
            if (!player.busted) {
                System.out.println(player.name + " wins");
                player.money = player.money + player.bet * 2;
                if (player.blackjack) {
                    player.money = player.money + player.bet * 0.5;
                }

            }
        }
    }
}
//vmtl von array zu dynarray wechseln damit rauslöschen