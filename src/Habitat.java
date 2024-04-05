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
	private int defaultAmount;
	private FileInputStream habitatData;
	private ArrayList<Integer> habitatDataList;
	private String biomeType;
	private boolean starter, victory;
	private HSSFWorkbook wb;
	
	public Habitat(int habNum) {
		habitatDataList = new ArrayList<>();
		
		try {
			tileImage = ImageIO.read(new File("src/images/background.png"));
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
			break;
		case 1:
			biomeType = "Lake";
			break;
		case 2: 
			biomeType = "Forest";
			break;
		case 3:
			biomeType = "Swamp";
			break;
		case 4:
			biomeType = "Mountain";
			break;
		} 
			
		if ( idx == 2 ) {
			
			switch (habitatDataList.lastIndexOf(1)) {
				
			case 0:
				biomeType.concat(" Desert");
				break;
			case 1:
				biomeType.concat(" Lake");
				break;
			case 2: 
				biomeType.concat(" Forest");
				break;
			case 3:
				biomeType.concat(" Swamp");
				break;
			case 4:
				biomeType.concat(" Mountain");
				break;
			}
			
		} else {
			
			//manually assign starters theres only like 4
			
			
		}
		

		
		
		 
	}
	public String getBiome() {
		
		return biomeType;
		
	}
	public ArrayList<Integer> getHabitatData() {
		
		return habitatDataList;
		
	}
	
}
