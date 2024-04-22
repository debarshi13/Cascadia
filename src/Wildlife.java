import java.util.ArrayList;
public class Wildlife {
    private ArrayList<String> animals;
    public Wildlife(){
        animals = new ArrayList<String>();
        for(int i = 0; i < 20; i++)
            animals.add("salmon");
        for(int i = 0; i < 20; i++)
            animals.add("elk");
        for(int i = 0; i < 20; i++)
            animals.add("fox");
        for(int i = 0; i < 20; i++)
            animals.add("hawk");
        for(int i = 0; i < 20; i++)
            animals.add("bear");
    }

    public ArrayList<String> getWildlife(){
        return animals;
    }
}
