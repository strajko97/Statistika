/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistika.domain;

import java.util.Scanner;

/**
 *
 * @author Paun
 */
public class NeprekidniTip extends Formule {

    public double srednjaVrednostIntervala[];
    public double ci[];

    public NeprekidniTip(int n) {
        obelezje = new double[n * 2];
        frekvencija = new double[n];
        srednjaVrednostIntervala = new double[n];
        ci=new double[n];
       // setIntervals();
       // setFrequency();
       // setSrednjaVrednostIntervala();
       // setCumulativeFrequency();
    }

    private void setIntervals() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < obelezje.length; i++) {
            if (i == 2) {//kada sam odredio prvi interval 
                double razlikaGranicaIntervala = 0;
                System.out.println("Da li hocete da vam automastki popunim na osnovu prethodnog intevala?");
                System.out.println("1.Da 2.Ne");
                int odg = sc.nextInt();
                if (odg == 1) {
                    razlikaGranicaIntervala = obelezje[1] - obelezje[0];
                    for (i = 2; i < obelezje.length; i++) {
                        obelezje[i] = obelezje[i - 1];
                        i++;
                        obelezje[i] = obelezje[i - 1] + razlikaGranicaIntervala;
                    }
                }
                return;
            }
            System.out.println("Unesite donju granicu za " + ((i / 2) + 1) + " interval");
            obelezje[i] = sc.nextDouble();
            i++;
            System.out.println("Unesite gornju granicu za " + ((i + 1) / 2) + " interval");
            obelezje[i] = sc.nextDouble();
        }
    }

    private void setFrequency() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < frekvencija.length; i++) {
            System.out.println("Unesite frekvenciju za " + (i + 1) + " inteval:");
            frekvencija[i] = sc.nextDouble();
        }
    }

    private void setSrednjaVrednostIntervala() {
        //malo je sprecificno za nacin kretanja da bi se svi indeksi poklopili
        for (int i = 0; i < srednjaVrednostIntervala.length * 2; i += 2) {
            srednjaVrednostIntervala[i / 2] = (obelezje[i] + obelezje[i + 1]) / 2.0;
        }
        srednjaVrednostIntervala[srednjaVrednostIntervala.length - 1] = (obelezje[obelezje.length - 1] + obelezje[obelezje.length - 2]) / 2.0;
        //da mi ne bi preskakao poslednji clan nakon for petlje ga dodajem
    }
 
    private void setCumulativeFrequency() {
        double suma=0;
        for(int i=0;i<ci.length;i++){
           suma=suma+frekvencija[i];
           ci[i]=suma;
        }
    }
    
    public void prikaziObelezje() {
        System.out.println("intevali: | fi  |  xi  |  ci  ");
                
        for (int i = 0; i < obelezje.length; i = i + 2) {
            System.out.print(obelezje[i] + " - " + obelezje[i + 1] + " | " + frekvencija[i / 2] + "  |  " + srednjaVrednostIntervala[i / 2]+"  |  "+ci[i/2]);
            System.out.println();
        }
    }

    public double ukupanN() {
        return ci[ci.length-1];
    }

    @Override
    public double xBar() {
        double suma=0;
        for(int i=0;i<srednjaVrednostIntervala.length;i++){
            suma=suma+srednjaVrednostIntervala[i]*frekvencija[i];
        }
        
          return suma/ukupanN();
    }

    @Override
    public double modus() {
       double max=0;
      
        int i=0;
        for( i=0;i<frekvencija.length;i++){
            if(frekvencija[i]>max){
                max=frekvencija[i];       
            }
        }
        return srednjaVrednostIntervala[i-1];
    }

    @Override
    public double median() {
        int polovinaN=(int) (ukupanN()/2);
        double fIntevala;
        double ciPrethodnogIntervala;
        
       //treba da odredim gornju i donju granicu intevala u kome se medijana nalazi
       double donjaGranica,gornjaGranica;
       //kako ih pronalazim - moram da idem do Ci i vracam inteval gde je prvi ci >n/2
       int i=0;
       for(i=0;i<ci.length;i++){
           if(ci[i]>=polovinaN){
              // System.out.println(ci[i]);
              // System.out.println(i);
               break;
           //dosao sam do i 
       }
       }
        //ovo i mi sluzi da odredim gonju i gornju granicu intevala
        //obelezje je niz sa duplo vecim brojem elemenata
        //donja granica je uvek paran broj, a gornja neparan pa zato ide +1
        donjaGranica=obelezje[i*2];
        gornjaGranica=obelezje[(i*2+1)];
       // System.out.println(donjaGranica+" - "+gornjaGranica);
        fIntevala=frekvencija[i];
        //System.out.println(fIntevala);
        ciPrethodnogIntervala=ci[i-1];
        //System.out.println(ciPrethodnogIntervala);
        
        //return donjaGranica+((polovinaN-ciPrethodnogIntervala)/fIntevala)*(gornjaGranica-donjaGranica);
        return Math.round((donjaGranica+((polovinaN-ciPrethodnogIntervala)/fIntevala)*(gornjaGranica-donjaGranica))*1000.0)/1000.0;
    }

    @Override
    public double razmakVarijacije() {
        return obelezje[obelezje.length-1]-obelezje[0];
    // return 0;
    }

    @Override
    public double prviKvratil() {
       double cetvrtinaIntervala= ukupanN()/4;
        double fIntevala;
        double ciPrethodnogIntervala;
        double gornjaGranicaIntervala,donjaGranicaIntevala;
        
      
        int i=0;
        for(i=0;i<ci.length;i++){
            if(ci[i]>=cetvrtinaIntervala){
                break;
            }
        }
        // i predstavlja redni broj intervala u kome se nalazi n/4ti element
        donjaGranicaIntevala=obelezje[i*2];
        gornjaGranicaIntervala=obelezje[i*2+1];
        ciPrethodnogIntervala=ci[i-1];
        fIntevala=frekvencija[i];
       /*System.out.println("gRONJA GRANICA "+gornjaGranicaIntervala);
        System.out.println("Donja granica "+donjaGranicaIntevala);
        System.out.println("F intevala "+fIntevala);
        System.out.println("C prethnog intevala "+ciPrethodnogIntervala);
        System.out.println("Cetvritna intervala "+cetvrtinaIntervala);
        System.out.println("rez: "+(donjaGranicaIntevala+((cetvrtinaIntervala-ciPrethodnogIntervala)/fIntevala)*(gornjaGranicaIntervala-donjaGranicaIntevala)));
*/
        // donjaGranicaIntevala+((cetvrtinaIntervala-ciPrethodnogIntervala)/fIntevala)*(gornjaGranicaIntervala-donjaGranicaIntevala);
        return Math.round((donjaGranicaIntevala+((cetvrtinaIntervala-ciPrethodnogIntervala)/fIntevala)*(gornjaGranicaIntervala-donjaGranicaIntevala))*1000.0)/1000.0;
        
        
    
    }

   

    @Override
    public double cetvtiKvartil() {
    double trecaCetvrtinaIntervala= (3*ukupanN())/4;
        double fIntevala;
        double ciPrethodnogIntervala;
        double gornjaGranicaIntervala,donjaGranicaIntevala;
        
      
        int i=0;
        for(i=0;i<ci.length;i++){
            if(ci[i]>=trecaCetvrtinaIntervala){
                break;
            }
        }
        // i predstavlja redni broj intervala u kome se nalazi n/4ti element
        donjaGranicaIntevala=obelezje[i*2];
        gornjaGranicaIntervala=obelezje[i*2+1];
        ciPrethodnogIntervala=ci[i-1];
        fIntevala=frekvencija[i];
       /*System.out.println("gRONJA GRANICA "+gornjaGranicaIntervala);
        System.out.println("Donja granica "+donjaGranicaIntevala);
        System.out.println("F intevala "+fIntevala);
        System.out.println("C prethnog intevala "+ciPrethodnogIntervala);
        System.out.println("Cetvritna intervala "+cetvrtinaIntervala);
        System.out.println("rez: "+(donjaGranicaIntevala+((cetvrtinaIntervala-ciPrethodnogIntervala)/fIntevala)*(gornjaGranicaIntervala-donjaGranicaIntevala)));
*/
        //return donjaGranicaIntevala+((trecaCetvrtinaIntervala-ciPrethodnogIntervala)/fIntevala)*(gornjaGranicaIntervala-donjaGranicaIntevala);
         return Math.round((donjaGranicaIntevala+((trecaCetvrtinaIntervala-ciPrethodnogIntervala)/fIntevala)*(gornjaGranicaIntervala-donjaGranicaIntevala))*1000.0)/1000.0;
        
        
   
    }

    @Override
    public double kvartilnaDevijacija() {
        return Math.round(((cetvtiKvartil()-prviKvratil())/2)*1000.0)/1000.0;
    }

    @Override
    public double srednjaDevijacija() {
        int i=0;
        double suma=0;
        for(i=0;i<srednjaVrednostIntervala.length;i++){
            suma=suma+Math.abs(srednjaVrednostIntervala[i]-xBar())*frekvencija[i];
        }
        //return suma/ci[ci.length-1];
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
        for(int i=0;i<srednjaVrednostIntervala.length;i++){
            suma=suma+(srednjaVrednostIntervala[i]*srednjaVrednostIntervala[i]*frekvencija[i]);
        }
      //  return (suma/ci[ci.length-1])-xBar()*xBar();  
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
      for(i=0;i<srednjaVrednostIntervala.length;i++){
          suma=suma+Math.pow(srednjaVrednostIntervala[i]-xBar(),n)*frekvencija[i];
      }
      
     // return suma/ukupanN();
      return Math.round((suma/ukupanN())*1000.0)/1000.0;
  }
    
    @Override
    public double koeficientSpljostenosti() {
         //return ntiMoment(4)/Math.pow(standradnaDevijacija(), 4);
            return Math.round((ntiMoment(4)/Math.pow(standradnaDevijacija(), 4))*1000.0)/1000.0;
    }

    @Override
    public double koeficientAsimetrije() {
       // return ntiMoment(3)/Math.pow(standradnaDevijacija(), 3);
       return Math.round((ntiMoment(3)/Math.pow(standradnaDevijacija(), 3))*1000.0)/1000.0;
    }

  
   

}
