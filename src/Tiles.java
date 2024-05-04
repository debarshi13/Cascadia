

import java.util.ArrayList;


public class Tiles {
    private Tile[][] startingTiles;
    private ArrayList<Tile> tiles;
    private ArrayList<String> habitats;
    private ArrayList<String> wildlife;

    public Tiles(){

        startingTiles = new Tile[5][3];
        tiles =  new ArrayList<Tile>();

        startingTiles[0][0] = new Tile();
        habitats = new ArrayList<String>();
        habitats.add("mountain");
        wildlife = new ArrayList<String>();
        wildlife.add("bear");
        startingTiles[0][0].setTile(1, habitats, wildlife, 0 );

        startingTiles[0][1] = new Tile();        
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("swamp");
        wildlife.add("hawk");
        wildlife.add("fox");
        wildlife.add("elk");
        startingTiles[0][1].setTile(2, habitats, wildlife, 60 );
      
        startingTiles[0][2] = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        habitats.add("lake");
        wildlife.add("salmon");
        wildlife.add("bear");
        startingTiles[0][2].setTile(3, habitats, wildlife, 300 );

        startingTiles[1][0] = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("swamp");
        wildlife.add("hawk");
        startingTiles[1][0].setTile(4, habitats, wildlife, 0 );

        startingTiles[1][1] = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("lake");
        wildlife.add("salmon");
        wildlife.add("hawk");
        wildlife.add("elk");
        startingTiles[1][1].setTile(5, habitats, wildlife, 240 );

        startingTiles[1][2] = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("desert");
        wildlife.add("bear");
        wildlife.add("fox");
        startingTiles[1][2].setTile(6, habitats, wildlife, 300 );     

        startingTiles[2][0] = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        wildlife.add("fox");
        startingTiles[2][0].setTile(7, habitats, wildlife, 0 );
        
        startingTiles[2][1] = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("swamp");
        habitats.add("lake");
        wildlife.add("salmon");
        wildlife.add("fox");
        wildlife.add("hawk");
        startingTiles[2][1].setTile(8, habitats, wildlife, 60 );
        
        startingTiles[2][2] = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");  
        habitats.add("forest");
        wildlife.add("bear");
        wildlife.add("elk");
        startingTiles[2][2].setTile(9, habitats, wildlife, 300 );
        
        startingTiles[3][0] = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        wildlife.add("elk");
        startingTiles[3][0].setTile(10, habitats, wildlife, 0 );

        startingTiles[3][1] = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("lake");
        habitats.add("mountain");
        wildlife.add("hawk");
        wildlife.add("bear");
        wildlife.add("elk");
        startingTiles[3][1].setTile(11, habitats, wildlife, 240 );
        
        startingTiles[3][2] = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        habitats.add("swamp");
        wildlife.add("fox");
        wildlife.add("salmon");
        startingTiles[3][2].setTile(12, habitats, wildlife, 300 );
        
        startingTiles[4][0] = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("lake");
        wildlife.add("salmon");
        wildlife.add("elk");
        startingTiles[4][0].setTile(13, habitats, wildlife, 0 );

        startingTiles[4][1] = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("desert");
        wildlife.add("salmon");
        wildlife.add("bear");
        wildlife.add("elk");
        startingTiles[4][1].setTile(14, habitats, wildlife, 240 );
    
        startingTiles[4][2] = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("swamp");
        wildlife.add("fox");
        wildlife.add("hawk");
        startingTiles[4][2].setTile(15, habitats, wildlife, 120 );     


        Tile t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        wildlife.add("hawk");
        t.setTile(16, habitats, wildlife, 0 );
        tiles.add(t);   
        
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        wildlife.add("hawk");
        t.setTile(17, habitats, wildlife, 0 );
        tiles.add(t);      
        
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        wildlife.add("bear");
        t.setTile(18, habitats, wildlife, 0 );
        tiles.add(t);   
        
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        wildlife.add("elk");
        t.setTile(19, habitats, wildlife, 0 );
        tiles.add(t);           

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        wildlife.add("elk");
        t.setTile(20, habitats, wildlife, 0 );
        tiles.add(t);   

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        wildlife.add("bear");
        t.setTile(21, habitats, wildlife, 0 );
        tiles.add(t);   

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        wildlife.add("bear");
        t.setTile(22, habitats, wildlife, 0 );
        tiles.add(t);    
        
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        wildlife.add("elk");
        t.setTile(23, habitats, wildlife, 0 );
        tiles.add(t);           

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        wildlife.add("fox");
        t.setTile(24, habitats, wildlife, 0 );
        tiles.add(t);     
    
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        wildlife.add("fox");
        t.setTile(25, habitats, wildlife, 0 );
        tiles.add(t); 
        
        t = new Tile();        
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        wildlife.add("elk");
        t.setTile(26, habitats, wildlife, 0 );
        tiles.add(t);   

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        wildlife.add("elk");
        t.setTile(27, habitats, wildlife, 0 );
        tiles.add(t); 
 
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        wildlife.add("fox");
        t.setTile(28, habitats, wildlife, 0 );
        tiles.add(t);   
        

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        wildlife.add("salmon");
        t.setTile(29, habitats, wildlife, 0 );
        tiles.add(t);   

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        wildlife.add("salmon");
        t.setTile(30, habitats, wildlife, 0 );
        tiles.add(t);   


        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("swamp");
        wildlife.add("fox");
        t.setTile(31, habitats, wildlife, 0 );
        tiles.add(t);   


        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("swamp");
        wildlife.add("fox");
        t.setTile(32, habitats, wildlife, 0 );
        tiles.add(t);  
        
        
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("swamp");
        wildlife.add("salmon");
        t.setTile(33, habitats, wildlife, 0 );
        tiles.add(t);  
        
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("swamp");
        wildlife.add("salmon");
        t.setTile(34, habitats, wildlife, 0 );
        tiles.add(t);  
        
        
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("swamp");
        wildlife.add("hawk");
        t.setTile(35, habitats, wildlife, 0 );
        tiles.add(t);   

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("lake");
        wildlife.add("hawk");
        t.setTile(36, habitats, wildlife, 0 );
        tiles.add(t);   


        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("lake");
        wildlife.add("hawk");
        t.setTile(37, habitats, wildlife, 0 );
        tiles.add(t);  
        
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("lake");
        wildlife.add("salmon");
        t.setTile(38, habitats, wildlife, 0 );
        tiles.add(t);   


        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("lake");
        wildlife.add("bear");
        t.setTile(39, habitats, wildlife, 0 );
        tiles.add(t);   

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("lake");
        wildlife.add("bear");
        t.setTile(40, habitats, wildlife, 0 );
        tiles.add(t);   

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("forest");
        wildlife.add("hawk");
        wildlife.add("bear");
        wildlife.add("elk");
        t.setTile(41, habitats, wildlife, 0 );
        tiles.add(t);  
        
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("forest");
        wildlife.add("fox");
        wildlife.add("bear");
        wildlife.add("elk");
        t.setTile(42, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("desert");
        wildlife.add("fox");
        wildlife.add("bear");
        wildlife.add("elk");
        t.setTile(43, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("desert");
        wildlife.add("salmon");
        wildlife.add("fox");
        wildlife.add("bear");
        t.setTile(44, habitats, wildlife, 0 );
        tiles.add(t);  
        
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("desert");
        wildlife.add("salmon");
        wildlife.add("fox");
        wildlife.add("elk");
        t.setTile(45, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        habitats.add("swamp");
        wildlife.add("salmon");
        wildlife.add("fox");
        wildlife.add("elk");
        t.setTile(46, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        habitats.add("swamp");
        wildlife.add("salmon");
        wildlife.add("fox");
        wildlife.add("hawk");
        t.setTile(47, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("swamp");
        wildlife.add("fox");
        wildlife.add("hawk");
        wildlife.add("bear");
        t.setTile(48, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("swamp");
        wildlife.add("salmon");
        wildlife.add("bear");
        wildlife.add("elk");
        t.setTile(49, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("swamp");
        wildlife.add("salmon");
        wildlife.add("hawk");
        wildlife.add("elk");
        t.setTile(50, habitats, wildlife, 0 );
        tiles.add(t);  


        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("swamp");
        habitats.add("lake");
        wildlife.add("salmon");
        wildlife.add("hawk");
        wildlife.add("bear");
        t.setTile(51, habitats, wildlife, 0 );
        tiles.add(t);  


        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("lake");
        wildlife.add("hawk");
        wildlife.add("fox");
        wildlife.add("elk");
        t.setTile(52, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("lake");
        habitats.add("mountain");
        wildlife.add("salmon");
        wildlife.add("hawk");
        wildlife.add("bear");
        t.setTile(53, habitats, wildlife, 0 );
        tiles.add(t);  


        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        habitats.add("lake");
        wildlife.add("salmon");
        wildlife.add("fox");
        wildlife.add("bear");
        t.setTile(54, habitats, wildlife, 0 );
        tiles.add(t);  


        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        habitats.add("lake");
        wildlife.add("fox");
        wildlife.add("hawk");
        wildlife.add("bear");
        t.setTile(55, habitats, wildlife, 0 );
        tiles.add(t); 
        
        
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("forest");
        wildlife.add("hawk");
        wildlife.add("bear");
        t.setTile(56, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("forest");
        wildlife.add("hawk");
        wildlife.add("elk");
        t.setTile(57, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("forest");
        wildlife.add("bear");
        wildlife.add("fox");
        t.setTile(58, habitats, wildlife, 0 );
        tiles.add(t);  


        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("forest");
        wildlife.add("elk");
        wildlife.add("fox");
        t.setTile(59, habitats, wildlife, 0 );
        tiles.add(t);  


        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("desert");
        wildlife.add("bear");
        wildlife.add("elk");
        t.setTile(60, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("desert");
        wildlife.add("bear");
        wildlife.add("fox");
        t.setTile(61, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("desert");
        wildlife.add("elk");
        wildlife.add("fox");
        t.setTile(62, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("desert");
        wildlife.add("elk");
        wildlife.add("salmon");
        t.setTile(63, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("desert");
        wildlife.add("fox");
        wildlife.add("salmon");
        t.setTile(64, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        habitats.add("swamp");
        wildlife.add("elk");
        wildlife.add("fox");
        t.setTile(65, habitats, wildlife, 0 );
        tiles.add(t);  


        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        habitats.add("swamp");
        wildlife.add("elk");
        wildlife.add("salmon");
        t.setTile(66, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        habitats.add("swamp");
        wildlife.add("fox");
        wildlife.add("hawk");
        t.setTile(67, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        habitats.add("swamp");
        wildlife.add("salmon");
        wildlife.add("hawk");
        t.setTile(68, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("swamp");
        habitats.add("lake");
        wildlife.add("fox");
        wildlife.add("salmon");
        t.setTile(69, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        habitats.add("swamp");
        wildlife.add("salmon");
        wildlife.add("hawk");
        t.setTile(70, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("swamp");
        habitats.add("lake");
        wildlife.add("salmon");
        wildlife.add("hawk");
        t.setTile(71, habitats, wildlife, 0 );
        tiles.add(t);  
        
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("swamp");
        habitats.add("lake");
        wildlife.add("salmon");
        wildlife.add("bear");
        t.setTile(72, habitats, wildlife, 0 );
        tiles.add(t); 
        
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("swamp");
        habitats.add("lake");
        wildlife.add("hawk");
        wildlife.add("bear");
        t.setTile(73, habitats, wildlife, 0 );
        tiles.add(t);      
        
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("lake");
        habitats.add("mountain");
        wildlife.add("salmon");
        wildlife.add("hawk");
        t.setTile(74, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("lake");
        habitats.add("mountain");
        wildlife.add("salmon");
        wildlife.add("bear");
        t.setTile(75, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("lake");
        habitats.add("mountain");
        wildlife.add("hawk");
        wildlife.add("bear");
        t.setTile(76, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("lake");
        habitats.add("mountain");
        wildlife.add("hawk");
        wildlife.add("elk");
        t.setTile(77, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("lake");
        habitats.add("mountain");
        wildlife.add("bear");
        wildlife.add("elk");
        t.setTile(78, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("desert");
        wildlife.add("hawk");
        wildlife.add("elk");
        t.setTile(79, habitats, wildlife, 0 );
        tiles.add(t);  


        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("desert");
        wildlife.add("hawk");
        wildlife.add("fox");       
        
        
        // t = new Tile();
        // habitats = new ArrayList<String>();
        // wildlife = new ArrayList<String>();
        // habitats.add("desert");
        // habitats.add("swamp");
        // wildlife.add("salmon");
        // wildlife.add("hawk");
        // t.setTile(70, habitats, wildlife, 0 );
        // tiles.add(t);  
        t.setTile(80, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("desert");
        wildlife.add("bear");
        wildlife.add("salmon");
        t.setTile(81, habitats, wildlife, 0 );
        tiles.add(t);         
        
        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("desert");
        wildlife.add("elk");
        wildlife.add("salmon");
        t.setTile(82, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("swamp");
        wildlife.add("hawk");
        wildlife.add("salmon");
        t.setTile(83, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("swamp");
        wildlife.add("bear");
        wildlife.add("salmon");
        t.setTile(84, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("swamp");
        wildlife.add("elk");
        wildlife.add("fox");
        t.setTile(85, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("mountain");
        habitats.add("swamp");
        wildlife.add("elk");
        wildlife.add("hawk");
        t.setTile(86, habitats, wildlife, 0 );
        tiles.add(t);  


        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("swamp");
        wildlife.add("bear");
        wildlife.add("fox");
        t.setTile(87, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("swamp");
        wildlife.add("bear");
        wildlife.add("salmon");
        t.setTile(88, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("swamp");
        wildlife.add("elk");
        wildlife.add("salmon");
        t.setTile(89, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("swamp");
        wildlife.add("elk");
        wildlife.add("hawk");
        t.setTile(90, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("swamp");
        wildlife.add("fox");
        wildlife.add("hawk");
        t.setTile(91, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("lake");
        wildlife.add("bear");
        wildlife.add("salmon");
        t.setTile(92, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("lake");
        wildlife.add("fox");
        wildlife.add("salmon");
        t.setTile(93, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("lake");
        wildlife.add("elk");
        wildlife.add("hawk");
        t.setTile(94, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("lake");
        wildlife.add("elk");
        wildlife.add("bear");
        t.setTile(95, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("forest");
        habitats.add("lake");
        wildlife.add("fox");
        wildlife.add("bear");
        t.setTile(96, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        habitats.add("lake");
        wildlife.add("elk");
        wildlife.add("salmon");
        t.setTile(97, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        habitats.add("lake");
        wildlife.add("elk");
        wildlife.add("hawk");
        t.setTile(98, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        habitats.add("lake");
        wildlife.add("fox");
        wildlife.add("hawk");
        t.setTile(99, habitats, wildlife, 0 );
        tiles.add(t);  

        t = new Tile();
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
        habitats.add("desert");
        habitats.add("lake");
        wildlife.add("fox");
        wildlife.add("bear");
        t.setTile(100, habitats, wildlife, 0 );
        tiles.add(t);  

    }
 
    public Tile[][] getStartingTiles() {
        return startingTiles;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }
}
