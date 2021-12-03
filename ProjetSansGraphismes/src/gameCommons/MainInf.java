package gameCommons;

import environment.EnvInf;

import environment.ICaseSpecial;
import frog.FrogInf;
import graphicalElements.FroggerGraphic;
import graphicalElements.IFroggerGraphics;
import specialCase.Bonus;
import specialCase.Ice;
import specialCase.Trap;
import specialCase.Tunnel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MainInf {
    public static ICaseSpecial getSpecialCase(int abs, int ord){
        Random r = new Random ();

        if(r.nextFloat () < 0.8){
            return null;
        } else {
            switch(r.nextInt(4)){
                case 0:
                    return new Bonus(abs, ord);
                case 1:
                    return new Ice(abs, ord);
                case 2:
                    return new Trap(abs, ord);
                case 3:
                    return new Tunnel(abs, ord);
                default:
                    return null;
            }
        }
    }
    public static void main(String[] args) {



        //Caract�ristiques du jeu
        int width = 36;
        int height = 30;
        int tempo = 100;
        int minSpeedInTimerLoops = 3;
        double defaultDensity = 0.2;

        //Cr�ation de l'interface graphique
        IFroggerGraphics graphic = new FroggerGraphic(width, height);
        //Cr�ation de la partie
        GameInf game = new GameInf(graphic, width, height, minSpeedInTimerLoops, defaultDensity);
        //Cr�ation et liaison de la grenouille
        IFrog frog = new FrogInf(game);
        game.setFrog(frog);
        graphic.setFrog(frog);
        //Cr�ation et liaison de l'environnement
        EnvInf env = new EnvInf(game);
        game.setEnvironment(env);

        //Boucle principale : l'environnement s'actualise tous les tempo milisecondes
        Timer timer = new Timer(tempo, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.update();
                graphic.repaint();
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }
}
