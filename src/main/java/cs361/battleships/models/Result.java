package cs361.battleships.models;

import java.util.List;
import java.util.PrimitiveIterator;

public class Result {

	//Private
	private Square location;
	private Ship currentShip;
	private AtackStatus currentStatus;

	//Constructors
	Result(Square square) {

		location = new Square(square.getRow(), square.getColumn(), false);
		currentShip = new Ship("None", 0);
		currentStatus = AtackStatus.INVALID;
	}

	Result(Square square, Ship ship) {
		location = square;//new Square(square.getRow(), square.getColumn(), true);
		currentShip = ship;
		currentStatus = AtackStatus.INVALID;
	}

	//Public
	public AtackStatus getStatus(int shipCount) {
		//checks if the spot has been hit and is occupied if so it continues
		if(location.checkHit()){
			int check_sunk = 0;//keeps track of weather or not the ship sinks
			//goes through the ship locations to see which one was hit
			for(int i = 0; i < currentShip.getLength(); i++){
				//finds which part of the ship was hit
				if(location.compareLocation().equals(currentShip.getOccupiedSquares().get(i).compareLocation())){
					currentShip.getOccupiedSquares().get(i).checkHit();

				}
				//checks which spots have been hits to see if the ship sinks
				if(currentShip.getOccupiedSquares().get(i).getHit() > 0){
					check_sunk++;
				}
			}
			//to see if the ship sunk or was simply hit
			if(check_sunk == currentShip.getLength()){
				shipCount--;//drops the amount of ships this actual count will
							// have to be manipulated outside this function this
							//is just to keep track if the game is over or not
				if(shipCount == 0){
					currentStatus = AtackStatus.SURRENDER;
					return currentStatus; // game over
				}else {
					currentStatus = AtackStatus.SUNK;
					return currentStatus;
				}
			}else {
				currentStatus = AtackStatus.HIT;
				return currentStatus;
			}
		}
		//checks to see if it is a miss or the spot has been hit twice
		if(location.checkValid()) {
			currentStatus = AtackStatus.MISS;
			return currentStatus;
		}
		currentStatus = AtackStatus.INVALID;
		return currentStatus;
	}

	public AtackStatus getResult(){
		return currentStatus;
	}

	public Ship getShip() {
		return currentShip;
	}

	public void setShip(Ship ship) {
		currentShip = ship;
		location.setOccupied(true);
	}

	public Square getLocation() {
		return location;
	}

	public void setLocation(Square square) {
		location = square;
	}
}
