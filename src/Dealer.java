public class Dealer extends Player {
    public Dealer(Kartenstapel stapel) {
        super();
        this.stapel = stapel;
    }

    public double scoreUpdate(double temp) {
        if (temp==11){
            ace = true;
        }
        if ((score + temp) > 21) {
            if (!ace) {
                busted = true;
                return score = score + temp;
            } else {
                System.out.println("dealer busted");
                return score = score + temp - 10;
            }
        }
        return score = score + temp;
    }

    public void zug1() {
        Karten temp = karteziehen(stapel.kartenstapel);
        System.out.println("dealer drew: " + temp.name + " of " + temp.suit);
        System.out.println("dealer's score now is: " + scoreUpdate(temp.kartenwert));
    }

    public void zug2() {
        Karten temp = karteziehen(stapel.kartenstapel);
        System.out.println("dealer drew: " + temp.name + " of " + temp.suit);
        System.out.println("dealer's score now is: " + scoreUpdate(temp.kartenwert));
        if (score == 21) {
            blackjack = true;
            System.out.println("dealer has a blackjack");
        }
    }

    public void zug() {
        if (score < 17) {
            Karten temp = karteziehen(stapel.kartenstapel);
            System.out.println("dealer drew: " + temp.name + " of " + temp.suit);
            System.out.println("dealer's score now is: " + scoreUpdate(temp.kartenwert));
            zug();
        }
    }
}
