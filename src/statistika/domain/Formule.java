/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistika.domain;

abstract public class Formule {
   public double[] obelezje;//intervali (bice ih 2x vise nego prekidnih)
   public int brojObelezja; //koliko ima intervala ili koliko ima razlicith prekidnih obelezja
   boolean prekidanTip;// o kom tipu se radi
   public double[] frekvencija;// frekvencija po intervalu ili po prekiddnoj vrednosti
   
  
   

//metode koje treba da se naprave u zavisnosti da li su
           //prekidni ili neprekidni tipovi obelezja
   public abstract double ukupanN();
   public abstract double xBar();
   public abstract double modus();
   public abstract double median();
   
   
   //Cas 2
   public abstract double razmakVarijacije();
   public abstract double prviKvratil();
   public abstract double cetvtiKvartil();
   public abstract double kvartilnaDevijacija();
  public abstract double srednjaDevijacija();
   public abstract double standradnaDevijacija();
   public abstract double varijansa();
   public abstract double koeficientVarijacije();
   public abstract double koeficientSpljostenosti();
   public abstract double koeficientAsimetrije();
}
