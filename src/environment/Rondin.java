package environment;

import gameCommons.Game;
import graphicalElements.Element;
import util.Case;
import util.Direction;
import util.SpriteCase;
import util.SpriteLoader;

import javax.management.relation.RelationNotFoundException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Rondin {
    private Game game;
    private Case leftPosition;
    private boolean leftToRight;
    private final int length = 5;
    private final Color colorLtR = Color.BLACK;
    private final Color colorRtL = Color.BLUE;
    private ArrayList<SpriteCase> roadCases = new ArrayList<>();
    public static final ArrayList<ArrayList<BufferedImage>> spriteCar = new ArrayList<>();

    public ArrayList<Integer> getRondinPosition(){
        ArrayList<Integer> res = new ArrayList<>();
        for(int i = 0 ; i < length ; i++) {
            Integer c = (this.leftPosition.absc - i);
            res.add(c);
        }
        return res;
    }


    public Rondin(Game game, Case leftPosition, boolean leftToRight){
        this.game = game;
        this.leftPosition = leftPosition;
        this.leftToRight = leftToRight;

       /* if (spriteCar.size() < 3){
            // Pour de la taille 1 jusqu'a 3
            for(int j = 1; j < 4; j++){
                ArrayList<BufferedImage> newList = new ArrayList<BufferedImage>();

                // *2 car on fait le côté droit, et le côté gauche
                for(int k = 1; k <= j*2; k++){
                    newList.add(SpriteLoader.getPicture("car" + j + "_" + k + ".png"));
                }

                spriteCar.add(newList);
            }
        }

        for(int i = 0; i < length; i++){
            // Initialiser toute la liste
            SpriteCase c = new SpriteCase(leftPosition.absc + i, leftPosition.ord, spriteCar.get(length-1).get(i + (leftToRight ? length : 0)));
            roadCases.add(c);
            game.getGraphic().add(c, 3);
        }*/
    }



    //TODO : ajout de methodes
    public boolean inBounds(Case p){
        int absc = this.leftPosition.absc;
        return ((this.leftPosition.ord == p.ord) && (absc <= p.absc && p.absc <= absc + length));
    }

    public boolean isInRondin(Case p){
        if(inBounds(p)){
            return true;
        }
        return false;
    }

    public int getLength(){return length;}

    public boolean getLeftToRight(){return this.leftToRight;}

    public boolean update(boolean moving){
        if(moving){
            this.leftPosition = new Case(this.leftPosition.absc + (leftToRight ? 1 : -1), this.leftPosition.ord);
            /*for(int i = 0; i < length; i++){
                Case c = new Case(this.leftPosition.absc - i, this.leftPosition.ord);
                this.position.remove(i);
                this.position.add(c);
                System.out.println(position.size());
            }*/
            removeSprites();
            if(this.leftPosition.absc >= game.width || this.leftPosition.absc < -length){
                return true;
            } /*else {
                for(int i = 0; i < length; i++){
                    // Initialiser toute la liste
                    SpriteCase c = new SpriteCase(leftPosition.absc + i, leftPosition.ord, spriteCar.get(length-1).get(i + (leftToRight ? length : 0)));
                    roadCases.add(c);
                    game.getGraphic().add(c, 3);
                }
            }*/
        }

        addToGraphics();
        return false;
    }

    /* Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant a la voiture*/
    private void addToGraphics() {
        for (int i = 0; i < length; i++) {
            Color color = colorRtL;
            if (this.leftToRight){
                color = colorLtR;
            }
            game.getGraphic()
                    .add(new Element(leftPosition.absc + i, leftPosition.ord, color));
        }
    }
    public Rondin getRondin(){return this;}

    public int getlength(){return length;}

    public void removeSprites(){
        /*for(SpriteCase e : roadCases){
            game.getGraphic().remove(e, 3);
        }*/
    }
}
