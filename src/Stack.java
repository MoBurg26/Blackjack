/**
 * Generische Klasse Stack<ContentType>
 * Konform programmiert mit den Methoden des Landes Niedersachsen bezueglich der gegebenen ergaenzenden Informationen
 * zu Abitur 2026. <br />
 * <p>
 * Objekte der generischen Klasse Stack (Keller, Stapel) verwalten beliebige
 * Objekte vom Typ ContentType nach dem Last-In-First-Out-Prinzip, d. h., das
 * zuletzt abgelegte Objekt wird als Erstes wieder entnommen. Alle Methoden
 * haben eine konstante Laufzeit, unabhaengig von der Anzahl der verwalteten
 * Objekte.
 *
 * @param <ContentType>
 * @author Birgit Komander (und 12IF1)
 */
public class Stack<ContentType> {
    /* Anfang der privaten Attribute der Klasse Stack */
    private Node head;

    /**
     * Konstruktor Stack<ContentType>
     */
    public Stack() {
        this.head = null;
    }

    /**
     * Methode isEmpty() <br />
     * Wenn der Stapel kein Element enthaelt, wird der Wert wahr zurueckgegeben, sonst der Wert falsch.
     *
     * @return Wahrheitswert
     */
    public boolean isEmpty() {
        if (head == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Methode top(): Inhaltstyp <br />
     * Der Inhalt des obersten Elements des Stapels wird zurueckgegeben, das Element aber nicht entnommen.
     */
    public ContentType top() {
        if (this.isEmpty()) {
            return null;
        } else {
            return this.head.getContent();
        }
    }

    /**
     * Methode push(inhalt: Inhaltstyp) <br />
     * Ein neues Element mit dem uebergebenen Inhalt wird oben auf den Stapel gelegt.
     *
     * @param content: Inhaltstyp
     */
    public void push(ContentType content) {
        Stack.Node node = new Stack.Node(content);
        node.setNext(this.head);
        this.head = node;
    }

    /**
     * Methode pop(): Inhaltstyp <br />
     * Das oberste Element des Stapels wird entnommen. Der Inhalt dieses Elements wird zurueckgegeben.
     *
     * @return Inhaltstyp
     */
    public ContentType pop() {
        if (this.isEmpty()) {
            return null;
        } else {
            ContentType content = this.head.getContent();
            this.head = this.head.getNext();
            return content;
        }
    }

    public void printStack() {
        if (this.isEmpty()) {
            System.out.println("Stack ist leer.");
            return;
        }
        Stack<ContentType> temp = new Stack<ContentType>();
        System.out.println("Stack:");

        ContentType head = top();
        ContentType tail = null;

        while (!this.isEmpty()) {
            ContentType item = this.pop();
            System.out.println(item);
            temp.push(item);
            tail = item;
        }

        while (!temp.isEmpty()) {
            this.push(temp.pop());
        }

        System.out.println("Top: " + head);
        System.out.println("Bottom: " + tail);
    }

    /* --------- Anfang der privaten inneren Klasse -------------- */
    public class Node {
        /* Anfang private Attribute der Klasse */
        private ContentType content = null;
        private Node nextNode = null;

        /**
         * Ein neues Objekt vom Typ StackNode<ContentType> wird erschaffen. <br />
         * Der Inhalt wird per Parameter gesetzt. Der Verweis ist leer.
         *
         * @param content der Inhalt des Knotens
         */
        public Node(ContentType content) {
            this.content = content;
            this.nextNode = null;
        }

        /**
         * Der Verweis wird auf das Objekt, das als Parameter uebergeben wird,
         * gesetzt.
         *
         * @param next der Nachfolger des Knotens
         */
        public void setNext(Node next) {
            this.nextNode = next;
        }

        /**
         * @return das Objekt, auf das der aktuelle Verweis zeigt
         */
        public Node getNext() {
            return this.nextNode;
        }

        /**
         * @return das Inhaltsobjekt vom Typ ContentType
         */
        public ContentType getContent() {
            return this.content;
        }
    }
    /* ----------- Ende der privaten inneren Klasse -------------- */
}

