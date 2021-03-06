package gameCommons;

import frog.Frog;
import graphicalElements.Element;
import graphicalElements.IFroggerGraphics;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game {
	public final Random randomGen = new Random();

	// Caracteristique de la partie
	public final int width;
	public final int height;
	public final int minSpeedInTimerLoops;
	public final double defaultDensity;

	// Lien aux objets utilis�s
	protected IEnvironment environment;
	protected Frog frog;
	protected IFroggerGraphics graphic;
	protected long startTime;
	private int score = 0;

	/**
	 * 
	 * @param graphic
	 *            l'interface graphique
	 * @param width
	 *            largeur en cases
	 * @param height
	 *            hauteur en cases
	 * @param minSpeedInTimerLoop
	 *            Vitesse minimale, en nombre de tour de timer avant d�placement
	 * @param defaultDensity
	 *            densite de voiture utilisee par defaut pour les routes
	 */
	public Game(IFroggerGraphics graphic, int width, int height, int minSpeedInTimerLoop, double defaultDensity) {
		super();
		this.graphic = graphic;
		this.width = width;
		this.height = height;
		this.minSpeedInTimerLoops = minSpeedInTimerLoop;
		this.defaultDensity = defaultDensity;
		this.startTime = System.currentTimeMillis();
	}

	public Frog getFrog(){
		return this.frog;
	}

	/**
	 * Lie l'objet frog � la partie
	 * 
	 * @param frog
	 */
	public void setFrog(IFrog frog) {
		this.frog = (Frog)frog;
	}

	/**
	 * Lie l'objet environment a la partie
	 * 
	 * @param environment
	 */
	public void setEnvironment(IEnvironment environment) {
		this.environment = environment;
	}

	/**
	 * 
	 * @return l'interface graphique
	 */
	public IFroggerGraphics getGraphic() {
		return graphic;
	}

	/**
	 * Teste si la partie est perdue et lance un �cran de fin appropri� si tel
	 * est le cas
	 * 
	 * @return true si le partie est perdue
	 */
	public boolean testLose() {
		if(frog.isAlive() && (!this.environment.isSafe(frog.getPosition()) || frog.isGonnaDie())){
			frog.setAlive(false);

			return true;
		}
		return false;
	}

	/**
	 * Teste si la partie est gagnee et lance un �cran de fin appropri� si tel
	 * est le cas
	 * 
	 * @return true si la partie est gagn�e
	 */
	public boolean testWin() {
		if(frog.isAlive() && this.environment.isWinningPosition (this.frog.getPosition())){
			frog.setAlive(false);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Actualise l'environnement, affiche la grenouille et verifie la fin de
	 * partie.
	 */
	public void update() {
		graphic.clear();
		environment.update();

		//this.graphic.add(new Element(frog.getPosition(), Color.GREEN));
		this.graphic.add(frog, 4);

		if(testWin()){
			this.graphic.endGameScreen("Vous avez gagné : "+ "score de :" + score + ",  temps de :" + (System.currentTimeMillis()-this.startTime)/1000 + " sec");
		} else if(testLose()){
			System.out.println(System.currentTimeMillis() + " : " + this.startTime);
			this.graphic.endGameScreen("Vous avez perdu : " + "score de :" + score + ",  temps de :" + (System.currentTimeMillis()-this.startTime)/1000 + " sec");
		}
	}

	public IEnvironment getEnvironment(){
		return this.environment;
	}

	public void addScore(){
		++score;
	}
	public void subScore(){
		--score;
	}
}
