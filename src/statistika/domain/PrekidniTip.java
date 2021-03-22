/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistika.domain;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import java.util.Scanner;

/**
 *
 * @author Paun
 */
public class PrekidniTip extends Formule {

   public double[] ci;
   
    public PrekidniTip(int n) {

        obelezje = new double[n];
        frekvencija = new double[n];
        ci = new double[n];
      // setInterval();
       // setFrequency();
       // setCi();
    }
  
    
    
    public void setInterval() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < frekvencija.length; i++) {
            System.out.println("Uniste " + (i + 1) + " vrednost prekidnog oblezja:");
            obelezje[i] = sc.nextDouble();
        }
    }

    public void setFrequency() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < frekvencija.length; i++) {
            System.out.println("Unesite frekvenciju za " + (i + 1) + " obelezje: ");
            frekvencija[i] = sc.nextDouble();
        }
    }

    public void setCi() {

        double suma = 0;

        for (int i = 0; i < ci.length; i++) {
            suma = suma + frekvencija[i];
            ci[i] = suma;
        }
    }

    public double ukupanN() {
        double suma = 0;
        for (int i = 0; i < frekvencija.length; i++) {
            suma = suma + frekvencija[i];
        }
        return suma;
    }

    @Override
    public double xBar() {
        double suma = 0;

        for (int i = 0; i < frekvencija.length; i++) {
            suma = suma + frekvencija[i] * obelezje[i];
        }
        return Math.round((suma / ukupanN())*1000.0)/1000.0;
    }

    public void prikaziObelezje() {
        System.out.println("X         fi        ci");
        for (int i = 0; i < frekvencija.length; i++) {
            System.out.print(obelezje[i] + "  |   " + frekvencija[i] + "  |   " + ci[i]);
            System.out.println();

        }
    }

    @Override
    public double modus() {
        double maxPonavljanja = 0;
        double modus = -1;
        for (int i = 0; i < frekvencija.length; i++) {
            if (frekvencija[i] > maxPonavljanja) {
                maxPonavljanja = frekvencija[i];
                modus = obelezje[i];
            }
        }
        return modus;
    }

    @Override
    public double median() {
        double pom = 0;

        if (!paranostN()) {
            for (int i = 0; i < ci.length; i++) {
                if (ci[i] > (ukupanN() + 1) / 2) {
                    return obelezje[i];
                }
            }
        }

        for (int i = 0; i < ci.length; i++) {
            if (ci[i] >= ukupanN() / 2) {//isptiujem iz kumulativne n/2 clan, ako jeste potvrdjujem da je on
                pom = obelezje[i];
                //System.out.println(pom);
                break;
            }
        }
        for (int i = 0; i < ci.length; i++) {
            if (ci[i] >= (ukupanN() / 2) + 1) {//ispitujem iz prvu kumulativnu koja je >= od n+1/2
                //System.out.println(obelezje[i]);
                pom = pom + obelezje[i];
                return pom / 2;
            }
        }

        return 0;
    }

    public boolean paranostN() {
        return ukupanN() % 2.0 == 0;
    }

    @Override
    public double razmakVarijacije() {
        return obelezje[obelezje.length - 1] - obelezje[0];
    }

    @Override
    public double prviKvratil() {
        int i = 0;
        int indeksElement1, indeksElement2;//n/4ti element i n/4)+1ti element
        indeksElement1 = (int) ci[ci.length - 1] / 4;
        indeksElement2 = indeksElement1 + 1;
        double suma = 0;
        // System.out.println(indeksElement1+"ti element");
        //System.out.println(indeksElement2+"ti element");

         //Moram da se setam kroz ci i prvi ci koji je >= idenksa Taj i odogovara Xi
        for (i = 0; i < ci.length; i++) {
            if (ci[i] >= indeksElement1) {
                suma = suma + obelezje[i];
                // System.out.println(suma);
                break;
            }
        }
        for (; i < ci.length; i++) {
            if (ci[i] >= indeksElement2) {
                suma = suma + obelezje[i];
                //  System.out.println(suma);
                break;
            }
        }

        return Math.round((suma / 2)*1000.0)/1000.0;
    }



    @Override
    public double cetvtiKvartil() {
    int i = 0;
        int indeksElement1, indeksElement2;//n/4ti element i n/4)+1ti element
        indeksElement1 = (int) (ci[ci.length - 1]) *3/ 4;
        indeksElement2 = indeksElement1 + 1;
        double suma = 0;
        // System.out.println(indeksElement1+"ti element");
        //System.out.println(indeksElement2+"ti element");

        //Moram da se setam kroz ci i prvi ci koji je >= idenksa Taj i odogovara Xi
        for (i = 0; i < ci.length; i++) {
            if (ci[i] >= indeksElement1) {
                suma = suma + obelezje[i];
                // System.out.println(suma);
                break;
            }
        }
        for (; i < ci.length; i++) {
            if (ci[i] >= indeksElement2) {
                suma = suma + obelezje[i];
                //  System.out.println(suma);
                break;
            }
        }
    return Math.round((suma / 2)*1000.0)/1000.0;
       // return suma / 2;   
    }

    @Override
    public double kvartilnaDevijacija() {
        return (cetvtiKvartil()-prviKvratil())/2;
    }

    @Override
    public double srednjaDevijacija() {
        //strogo pozitivna
        int i=0;
        double suma=0;
        for(i=0;i<obelezje.length;i++){
            suma=suma+Math.abs(obelezje[i]-xBar())*frekvencija[i];
        }
       // return suma/ci[ci.length-1];
    return Math.round((suma/ci[ci.length-1])*1000.0)/1000.;
    }

    @Override
    public double standradnaDevijacija() {
       // return Math.sqrt(varijansa());
       return Math.round((Math.sqrt(varijansa()))*1000.0)/1000.0;
    }

    @Override
    public double varijansa() {
        double suma=0;
        for(int i=0;i<obelezje.length;i++){
            suma=suma+(obelezje[i]*obelezje[i]*frekvencija[i]);
        }
       // return (suma/ci[ci.length-1])-xBar()*xBar();
    return Math.round(((suma/ci[ci.length-1])-xBar()*xBar())*1000.0)/1000.0;
    
    }

    @Override
    public double koeficientVarijacije() {
        //return (standradnaDevijacija()/xBar())*100;
        return Math.round(((standradnaDevijacija()/xBar())*100)*1000.0)/1000.0;
    }
    
  public double ntiMoment(int n){
      int i=0;
      double suma=0;
      for(i=0;i<obelezje.length;i++){
          suma=suma+Math.pow(obelezje[i]-xBar(),n)*frekvencija[i];
      }
      
      //return suma/ukupanN();
      return Math.round((suma/ukupanN())*1000.0)/1000.0;
  }

    @Override
    public double koeficientSpljostenosti() {
        //ovde sam ga delio sa n
        //nekad se deli sa n-1
        //return ntiMoment(4)/Math.pow(standradnaDevijacija(), 4);
        return Math.round((ntiMoment(4)/Math.pow(standradnaDevijacija(), 4))*1000.0)/1000.0;
    }

    @Override
    public double koeficientAsimetrije() {
       //isto kao i za prethodnu delimo sa n
      // return ntiMoment(3)/Math.pow(standradnaDevijacija(), 3);
     return Math.round((ntiMoment(3)/Math.pow(standradnaDevijacija(), 3))*1000.0)/1000.0;
    }
  
  

}
