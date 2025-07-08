public class Dealer extends Player {
    public Dealer(Kartenstapel stapel) {
        super();
        this.stapel = stapel;
    }

    public int scoreUpdate(int temp) {
        if (score + temp > 21 && !ace) {
            System.out.println("dealer busted");
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

    public void zug1() {
        Karten temp = karteziehen(stapel.kartenstapel);
        if (temp.kartenwert == 11) {
            ace = true;
        }
        System.out.println("dealer drew: " + temp.name + " of " + temp.suit);
        System.out.println("dealer's score now is: " + scoreUpdate(temp.kartenwert));
    }

    public void zug2() {
        Karten temp = karteziehen(stapel.kartenstapel);
        if (temp.kartenwert == 11) {
            ace = true;
        }
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
            if (temp.kartenwert == 11) {
                ace = true;
            }
            System.out.println("dealer drew: " + temp.name + " of " + temp.suit);
            System.out.println("dealer's score now is: " + scoreUpdate(temp.kartenwert));
            System.out.println("-------");
            zug();
        }
    }
}
