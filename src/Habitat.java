import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Habitat {
	private BufferedImage tileImage;
	private BufferedImage desertTileImage, desertLakeTileImage, desertSwampTileImage, forestTileImage, forestDesertTileImage, forestLakeTileImage, forestSwampTileImage, lakeTileImage, lakeMountainTileImage, mountainTileImage, mountainDesertTileImage, mountainForestTileImage, mountainSwampTileImage, swampTileImage, swampLakeTileImage;
	private BufferedImage starterTile1, starterTile2;
	private int defaultAmount;
	private FileInputStream habitatData;
	private ArrayList<Integer> habitatDataList;
	private String biomeType;
	private boolean starter, victory;
	private XSSFWorkbook wb;
	private BufferedImage possibleImage;
	private ArrayList<String> habitatNames = new ArrayList<>();
	public Habitat(int habNum) {
		habitatDataList = new ArrayList<>();
		habitatNames.add("Desert");
		habitatNames.add("Lake");
		habitatNames.add("Forest");
		habitatNames.add("Swamp");
		habitatNames.add("Mountain");
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
			starterTile1 = ImageIO.read(new File("src/images/starterTile1.png"));
			swampLakeTileImage = ImageIO.read(new File("src/images/swamp+lake.png"));
			habitatData = new FileInputStream(new File("src/habitatData/habitatData.xlsx"));
			
			wb = new XSSFWorkbook(habitatData);
			XSSFSheet sheet=wb.getSheetAt(0);  
			FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();
			for (Cell cell : sheet.getRow(habNum)) {
					
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					
					habitatDataList.add((int) cell.getNumericCellValue());
					
				} else {
					
					XSSFDrawing drawing = sheet.createDrawingPatriarch();

			        //loop through all of the shapes in the drawing area
			        for(XSSFShape shape : drawing.getShapes()){
			            if(shape instanceof Picture){
			                //convert the shape into a picture
			                XSSFPicture picture = (XSSFPicture)shape;
			                byte[] pictureData = picture.getPictureData().getData();
			                possibleImage = ImageIO.read(new ByteArrayInputStream(pictureData));
			            }
			        }
					
				}
			
			}
			
		} catch (IOException e) {
		
			System.out.println("error");
		}
		defaultAmount = habitatDataList.get(7);
		int idx = 0;
		if (habitatDataList.get(5) == 0) {
			ArrayList<Integer> subList = new ArrayList<>();
			for (int i = 0 ; i < 5 ; i++) {
				
				subList.add(habitatDataList.get(i));

			}
			biomeType = (habitatNames.get(subList.indexOf(1)) + " " + habitatNames.get(subList.lastIndexOf(1)));
			if (habNum == 3) { 
				
				System.out.println(subList);
				
			}
			tileImage = possibleImage; 
			
		}
		if (habitatDataList.get(5) == 1) {

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
		}
		if (habNum == 1) {
			
			System.out.println(idx);
			
		}
		
		if (habNum == 16) {
			
			tileImage = starterTile1;
			biomeType = "specialCase1";
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
	public int getAmount() {
		
		return habitatDataList.get(7);
		
	}
	public String toString() {
		
		return biomeType;
		
	}
	
}
