import java.util.*;
import java.io.*;
import java.awt.*;
import java.io.BufferedReader.*;
public class Player {
	
	private int natureTokens, numTiles;
	private ArrayList<Habitat> habitats; //will store them in graph l8r
	public Player(int playerNum) {
		
		 habitats = new ArrayList<>();

		 switch (playerNum) {
		 
		 	case 1:
		 		//16 - 20 is for starter tiles
		 		habitats.add(new Habitat(16));
		 		break;
		 	
		 
		 }
		 
		 

	}
	public ArrayList<Habitat> getHabitats() {
		
		return habitats;
		
	}
}
