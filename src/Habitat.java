import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.FormulaEvaluator;  
import org.apache.poi.ss.usermodel.Row; 
import javax.imageio.ImageIO;

public class Habitat {
	private BufferedImage tileImage;
	private BufferedImage desertTileImage, desertLakeTileImage, desertSwampTileImage, forestTileImage, forestDesertTileImage, forestLakeTileImage, forestSwampTileImage, lakeTileImage, lakeMountainTileImage, mountainTileImage, mountainDesertTileImage, mountainForestTileImage, mountainSwampTileImage, swampTileImage, swampLakeTileImage;
	private BufferedImage starterTile1, starterTile2;
	private int defaultAmount;
	private FileInputStream habitatData;
	private ArrayList<Integer> habitatDataList;
	private String biomeType;
	private boolean starter, victory;
	private HSSFWorkbook wb;
	
	public Habitat(int habNum) {
		habitatDataList = new ArrayList<>();
		
		try {
			desertTileImage = ImageIO.read(new File("src/images/desert.png"));
			desertLakeTileImage = ImageIO.read(new File("src/images/desert+lake.png"));
			desertSwampTileImage = ImageIO.read(new File("src/images/desert+swamp.png"));
			forestTileImage = ImageIO.read(new File("src/images/forest.png"));
			forestDesertTileImage = ImageIO.read(new File("src/images/forest+desert.png"));
			forestLakeTileImage = ImageIO.read(new File("src/images/forest+lake.png"));
			forestSwampTileImage = ImageIO.read(new File("src/images/forest+swamp.png"));
			lakeTileImage = ImageIO.read(new File("src/images/lake.png"));
			lakeMountainTileImage = ImageIO.read(new File("src/images/lake+mountain.png"));
			mountainTileImage = ImageIO.read(new File("src/images/mountain.png"));
			mountainDesertTileImage = ImageIO.read(new File("src/images/mountain+desert.png"));
			mountainForestTileImage = ImageIO.read(new File("src/images/mountain+forest.png"));
			mountainSwampTileImage = ImageIO.read(new File("src/images/mountain+swamp.png"));
			swampTileImage = ImageIO.read(new File("src/images/swamp.png"));
			swampLakeTileImage = ImageIO.read(new File("src/images/swamp+lake.png"));
			habitatData = new FileInputStream(new File("src/habitatData/habitatData.xls"));
			wb = new HSSFWorkbook(habitatData);
			HSSFSheet sheet=wb.getSheetAt(0);  
			FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();
			for (Cell cell : sheet.getRow(habNum)) {
				
				habitatDataList.add((int) cell.getNumericCellValue());
				
			}
			
			
		} catch (IOException e) {
		
			System.out.println("error");
		}
		defaultAmount = habitatDataList.get(7);
		int idx = 0;
		for (int i = 0 ; i < 5; i++) { idx += habitatDataList.get(i); }
		

		switch (habitatDataList.indexOf(1)) {
		
		case 0:
			biomeType = "Desert";
			tileImage = desertTileImage;
			break;
		case 1:
			biomeType = "Lake";
			tileImage = lakeTileImage;
			break;
		case 2: 
			biomeType = "Forest";
			tileImage = forestTileImage;
			break;
		case 3:
			biomeType = "Swamp";
			tileImage = swampTileImage;
			break;
		case 4:
			biomeType = "Mountain";
			tileImage = mountainTileImage;
			break;
		} 
		if (habNum == 1) {
			
			System.out.println(idx);
			
		}
			//idk y this part aint working
		
		if ( idx == 2 ) {
			
			switch (habitatDataList.lastIndexOf(1)) {
			
			case 0:
				biomeType = biomeType.concat(" Desert");
				
				break;
			case 1:
				biomeType = biomeType.concat(" Lake");
				
				break;
			case 2: 
				biomeType = biomeType.concat(" Forest");
				break;
			case 3:
				biomeType = biomeType.concat(" Swamp");
				break;
			case 4:
				biomeType = biomeType.concat(" Mountain");
				break;
			}
			
			
		} else {
			
			//manually assign starters theres only like 4
			
			
		}
		
	

		
		
		 
	}
	public BufferedImage getImg() {
		
		return tileImage;
		
	}
	public String getBiome() {
		
		return biomeType;
		
	}
	public ArrayList<Integer> getHabitatData() {
		
		return habitatDataList;
		
	}
	
}
