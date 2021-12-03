package environment;

import gameCommons.Game;
import gameCommons.Main;
import graphicalElements.Element;
import util.Case;
import util.Sprite;
import util.SpriteCase;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Lane {
	private Game game;
	private int ord;
	private int speed;
	private ArrayList<Car> cars = new ArrayList<>();
	private boolean leftToRight;
	private double density;
	private int tic = 0;
	private ArrayList<ICaseSpecial> specialCases = new ArrayList<>();
	private ArrayList<SpriteCase> roadCases = new ArrayList<>();
	private ArrayList<Rondin> rondins = new ArrayList<>();
	public static final BufferedImage bottomSprite = SpriteLoader.getPicture("roadbottom.png");
	public static final BufferedImage topSprite = SpriteLoader.getPicture("roadtop.png");
	public static final BufferedImage concreteSprite = SpriteLoader.getPicture("concrete.png");


	public Lane(Game game, int ord) {
		this(game, ord, new Random().nextInt(3) + 1);
	}

	public Lane(Game game, int ord, int speed) {
		this.game = game;
		this.ord = ord;

		// aléatoire
		Random r = new Random();
		this.speed = speed;
		this.leftToRight = r.nextBoolean();
		this.density = r.nextDouble() % 0.01 + 0.025;
		if (this.ord < game.height / 2) {
			if (speed != 0) {
				for (int i = 0; i < game.width; i++) {
					ICaseSpecial c = Main.getSpecialCase(i, ord);

					if (c != null) {
						specialCases.add(c);
						game.getGraphic().add(c, 3);
					}
				}
			}

			for (int i = 0; i < game.width; i++) {
				BufferedImage sprite;

				if (speed == 0) {
					sprite = concreteSprite;
				} else {
					if (ord % 2 == 0) {
						sprite = topSprite;
					} else {
						sprite = bottomSprite;
					}
				}

				SpriteCase c = new SpriteCase(i, ord, sprite);
				roadCases.add(c);
				game.getGraphic().add(c, 0);
			}
		} else {
			if(this.ord > game.height / 2 	&& this.ord <= game.height) {
				for (int i = 0; i <= (game.height/2) +1; i++) {
					Case c = new Case(-1, ord);
					rondins.add(new Rondin(game, c, false));
				}
			}
		}
	}


	public void update() {
		if (this.ord < game.height / 2){
			for (int i = 0; i < cars.size(); i++) {
				Car c = cars.get(i);

			if (c.update(speed != 0 && tic % speed == 0)) {
				cars.remove(c);
				--i;
			}
		}

		mayAddCar();
	} else{
			if(this.ord >= game.height / 2) {
				for (int i = 0; i < rondins.size(); i++) {
					Rondin r = rondins.get(i);

					if (r.update(speed != 0 && tic % speed == 0)) {
						rondins.remove(r);
						--i;
					}

				}
				mayAddRondin();
				for (Rondin r : rondins) {
					if(!(this.game.getFrog().update(r.inBounds(this.game.getFrog().getPosition())))){
						this.game.getFrog().setLeftToRight((r.getLeftToRight()));
						this.game.getFrog().update(r.inBounds(this.game.getFrog().getPosition()));
					}
				}

			}
	}

}




	// TODO : ajout de methodes
	public ArrayList<Car> getCars(){
		return cars;
	}

	public boolean isSafe(Case c) {
		if (c.ord < game.height / 2) {
			for (Car car : cars) {
				if (car.inBounds(c)) {
					return false;
				}
			}
			return true;

		} else {
			if (c == this.game.getFrog().getPosition() && c.ord >= game.height / 2) {
				for (Rondin r : rondins) {

					if (r.isInRondin(c)) {
						System.out.println("true");
						return true;
					}
				}
				return false;


			}
			for (Car car : cars) {
				if (car.inBounds(c)) {
					return false;
				}
			}
			return true;

		}

	}


	/*
	 * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase() 
	 */

	/**
	 * Ajoute une voiture au d�but de la voie avec probabilit� �gale � la
	 * densit�, si la premi�re case de la voie est vide
	 */
	private void mayAddCar() {
		if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
			double rnd = game.randomGen.nextDouble();

			if (rnd < density) {
				cars.add(new Car(game, getBeforeFirstCase(), leftToRight));
			}
		}
	}

	private void mayAddRondin(){
		if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
			double rnd = game.randomGen.nextDouble();

			if (rnd < density) {
				rondins.add(new Rondin(game, getBeforeFirstCase(), leftToRight));
			}
		}
	}

	protected Case getFirstCase() {
		if (leftToRight)
			return new Case(0, ord);

		return new Case(game.width - 1, ord);
	}

	protected Case getBeforeFirstCase() {
		if (leftToRight)
			return new Case(-1, ord);

		return new Case(game.width, ord);
	}

	public ICaseSpecial getSpecialCases(Case frogCase){
		for(ICaseSpecial spec : specialCases){
			if(spec.getPosition().absc == frogCase.absc){

				if(spec.deleteOnUse()){
					specialCases.remove(spec);
					game.getGraphic().remove(spec, 3);
				}

				return spec;
			}
		}

		return null;
	}

	public int getOrd(){
		return ord;
	}
}
