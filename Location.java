public class Location {

    private int row_idx = -1;
    private int col_idx = -1;
    private int tileNum = -1;
    private boolean used = false;
    public Location(int i, int j, int n) {
        row_idx = i;
        col_idx = j;
        tileNum = n;
        used = false;
    }

    public Location(int i, int j) {
        row_idx = i;
        col_idx = j;
        tileNum = -1;
        used = false;
    }
         
    public void setUsed(boolean u) {
        used = u;
    }

    public boolean getUsed() {
        return used;
    }
    public void setRow (int i)
    {
        row_idx = i;
    }

    public void setCol (int j)
    {
        col_idx = j;
    }

    public int getRow()
    {
        return row_idx;
    }

    public int getCol()
    {
        return col_idx;
    }

    public void setTileNum(int n)
    {
        tileNum = n;
    }

    public int getTileNum()
    {
        return tileNum;
    }

    @Override
    public String toString()
    {
        return "([ " + row_idx + " , " + col_idx + " ]" + " tileNum: " + tileNum +") ";
    }
 }
