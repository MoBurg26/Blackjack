public class Playerhands extends Player {

    public Playerhands(double score, String name, Kartenstapel stapel, Boolean ace, double bet, double money) {
        super();
        this.name = name;
        this.stapel = stapel;
        this.ace = ace;
        this.bet = bet;
        this.money = money;
        scoreUpdate(score);
    }

    public void zug1() {
        Karten temp = karteziehen(stapel.kartenstapel);
        System.out.println(name + " drew: " + temp.name + " of " + temp.suit);
        System.out.println(name + " score now is: " + scoreUpdate(temp.kartenwert));
        if (score == 21) {
            blackjack = true;
        }
    }
}
