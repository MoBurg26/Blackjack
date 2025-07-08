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
        System.out.println("-------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            players[i].zug();
        }
        int allBusted = 0;
        for (int i = 0; i < n; i++) {
            if (players[i].busted) {
                allBusted = allBusted + 1;
            }
        }
        if (allBusted < n) {
            System.out.println("dealer has: " + dealer.score);
            dealer.zug2();
            if (!dealer.busted || !dealer.blackjack) {
                dealer.zug();
                if (!dealer.busted) {
                    for (int i = 0; i < n; i++) {
                        if (!players[i].busted) {
                            if (dealer.score == players[i].score) {
                                if (!players[i].blackjack && !dealer.blackjack || players[i].blackjack && dealer.blackjack) {
                                    System.out.println("it's a push");
                                    players[i].money = players[i].money + players[i].bet;
                                } else if (players[i].blackjack && !dealer.blackjack) {
                                    System.out.println(players[i].name + " wins");
                                    players[i].money = players[i].money + players[i].bet * 2 + players[i].bet * 0.5;
                                } else {
                                    System.out.println("dealer wins");
                                }
                            } else if (dealer.score > players[i].score) {
                                System.out.println("dealer wins");
                            } else {
                                System.out.println(players[i].name + " wins");
                                players[i].money = players[i].money + players[i].bet * 2;
                                if (players[i].blackjack) {
                                    players[i].money = players[i].money + players[i].bet * 0.5;
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < n; i++) {
                        if (!players[i].busted) {
                            System.out.println(players[i].name + " wins");
                            players[i].money = players[i].money + players[i].bet * 2;
                            if (players[i].blackjack) {
                                players[i].money = players[i].money + players[i].bet * 0.5;
                            }
                        }
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
            }
            edit(players, n, scan, stapel);
            for (int i = 0; i < n; i++) {
                if (players[i].bankrupt()) {
                    System.out.println("we round up to 1€ for " + players[i].name + " the brokey");
                    players[i].money = 1;
                }
            }
            System.out.println("-------------------------------------------------------------");
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
}
//vmtl von array zu dynarray wechseln damit rauslöschen