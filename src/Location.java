public class Location {

    private int row_idx = -1;
    private int col_idx = -1;
    public Location(int i, int j) {
        row_idx = i;
        col_idx = j;
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

    @Override
    public String toString()
    {
        return "[ " + row_idx + " , " + col_idx + " ]";
    }
 }
