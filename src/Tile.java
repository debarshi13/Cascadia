

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.io.*;

public class Tile {
    private int tileNum;
    private ArrayList<String> habitats;
    private ArrayList<String> wildlife;
    private int rotation;
    public Tile(){
        habitats = new ArrayList<String>();
        wildlife = new ArrayList<String>();
    } 
    public void setTile(int n, ArrayList<String> h, ArrayList<String> l, int r){
        tileNum = n;
        habitats = h;
        wildlife = l;
        rotation = r;
    }
    public void setRotation(int r){
        rotation = r;
    }
    public int getTileNum(){
        return tileNum;
    }
    public int getRotation(){
        return rotation;
    }
    public ArrayList<String> getHabitats(){
        return habitats;
    }
    public ArrayList<String> getWildlife(){
        return wildlife;
    }
}
