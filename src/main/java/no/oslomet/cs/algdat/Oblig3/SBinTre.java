package no.oslomet.cs.algdat.Oblig3;


import com.sun.source.tree.WhileLoopTree;

import java.util.*;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    //Koder til oppgave 2)
    public boolean inneholder(T verdi) {
        if (verdi == null)return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    //koder til oppgave 2)
    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    //Koder til oppgave 2)
    public boolean tom() {
        return antall == 0;
    }

    //Oppgave 1):
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Det er ikke lov med null-verdier!");

        Node<T> r = rot;
        Node<T> n = null;
        int sammenlikn = 0;

        while (r != null){
            n = r;
            sammenlikn = comp.compare(verdi,r.verdi);
            r = sammenlikn < 0 ? r.venstre : r.høyre;
        }

        r = new Node<>(verdi,n);

        if (n == null){
            rot = r;
        } else if (sammenlikn < 0){
            n.venstre = r;
        }else {
            n.høyre = r;
        }

        antall ++;
        endringer ++;
        return true;
    }


    //Oppgave 6):
    public boolean fjern(T verdi) {
        if (verdi == null){
            return false;
        }

        Node<T> r = rot, n = null;

        while (r != null){
            int sammenlikn = comp.compare(verdi, r.verdi);
            if (sammenlikn < 0){
                n = r;
                r = r.venstre;
            } else if (sammenlikn > 0){
                n = r;
                r = r.høyre;
            }else {
                break;
            }
        }

        if (r == null){
            return false;
        }

        if (r.venstre == null || r.høyre == null){
            Node<T> a = r.venstre != null ? r.venstre : r.høyre;
            if (r == rot){
                rot = a;
            }else if (r == n.venstre){
                n.venstre = a;
                if (a != null){
                    a.forelder = n;
                }
            } else{
                n.høyre = a;
            }

            if (a != null){
                a.forelder = n;
            }
        }else {
            Node<T> b = r, p = r.høyre;

            while (p.venstre != null){
                b = p;
                p = p.venstre;
            }

            r.verdi = p.verdi;

            if (b != r){
                b.venstre = p.høyre;
            }else{
                b.høyre = p.høyre;
            }
        }
        endringer++;
        antall--;
        return  true;
    }

    //Oppgave 6):
    public int fjernAlle(T verdi) {
        int antallFjernet = 0;
        if (!tom()){
            while (fjern(verdi)){
                antallFjernet ++;
            }
        }
        return  antallFjernet;
    }

    //Oppgave 2) hjelpemetodet

    public int antall2(T verdi, Node<T> node) {
        if(node == null){
            return 0;
        }
        return antall2(verdi, node.venstre) + antall2(verdi, node.høyre) + (node.verdi.equals(verdi) ? 1 : 0);
    }


    //Oppgave 2):
    public int antall(T verdi) {
        return antall2(verdi, rot);

        //med reaksjon
        /*
        int antallOpptreden = 0;

        if(verdi == null){
            return 0;
        }

        //r-står for pekkeren
        Node<T> r = rot;
        while(r != null){
            int sammenlikn = comp.compare(verdi, r.verdi);
            if (sammenlikn < 0){
                r = r.venstre;
            }else {
                if (sammenlikn == 0) {
                    antallOpptreden++;
                    r = r.høyre;
                }
            }
        }
        return  antallOpptreden;
         */
    }

    //Oppgave 6):
    public void nullstill()  {
        nullstill(rot);
        rot = null;
        antall = 0;
        endringer = 0;
    }

    //Oppgave 6):
    public void nullstill(Node<T> node) {
        if(node == null)
            return;
        nullstill(node.venstre);
        nullstill(node.høyre);
        node.verdi = null;
        node.venstre = null;
        node.høyre = null;
        node.forelder = null;

    }


    //Oppgave 3):
    private static <T> Node<T> førstePostorden(Node<T> p) {
        if (p == null){
            throw new UnsupportedOperationException("Treet vårt er tomt!");
        }

        while (true){
            if (p.venstre != null){
                p = p.venstre;
            } else if (p.høyre != null){
                p = p.høyre;
            } else {
                return p;
            }
        }

    }

    //Oppgave 3):
    private static <T> Node<T> nestePostorden(Node<T> p) {

        if (p == null){
            throw new UnsupportedOperationException("Treet vårt er tomt!");
        }

        else if (p.forelder == null){
            p = null;
        }

        else if (p == p.forelder.høyre){
            p = p.forelder;
        }

        else if (p == p.forelder.venstre){
            if (p.forelder.høyre == null){
                p = p.forelder;
            }
            else {
                p = førstePostorden(p.forelder.høyre);
            }
        }
        return p;
    }

    //Oppgave 4):
    public void postorden(Oppgave<? super T> oppgave) {
        if (rot == null){
            return;
        }

        Node<T> n = førstePostorden(rot);
        oppgave.utførOppgave(n.verdi);

        Node<T> r = nestePostorden(n);
        while (r != null){
            oppgave.utførOppgave(r.verdi);
            r = nestePostorden(r);
        }
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    //Oppgave 4)
    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    //Oppgave 4):
    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if (p == null){
            return;
        }

        if (p.venstre != null){
            postordenRecursive(p.venstre, oppgave);
        }

        if (p.høyre != null){
            postordenRecursive(p.høyre, oppgave);
        }
        oppgave.utførOppgave(p.verdi);
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    //Oppgave 5):
    public ArrayList<T> serialize() {
        ArrayList<T> liste = new ArrayList<>();
        ArrayDeque<Node> kø = new ArrayDeque<>();

        kø.addLast(rot);
        while (!kø.isEmpty()){
            Node<T> nåværende = kø.removeFirst();

            if (nåværende.venstre != null){
                kø.addLast(nåværende.venstre);
            }

            if (nåværende.høyre != null){
                kø.addLast(nåværende.høyre);
            }

            liste.add(nåværende.verdi);
        }
        return liste;
    }

    //Oppgave 5):
    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        SBinTre<K> tre = new SBinTre<>(c);
        for (int i = 0; i < data.size(); i++){
            tre.leggInn(data.get(i));
        }
        return tre;
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
