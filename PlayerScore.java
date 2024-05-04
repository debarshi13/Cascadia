public class PlayerScore {
    private int playerIdx;
    private int bonusScore;

    public PlayerScore(int idx, int s)
    {
        playerIdx = idx;
        bonusScore = s;
    }

    public int getPlayerIdx ()
    {
        return playerIdx;
    }

    public int getPlayerScore ()
    {
        return bonusScore;
    }

    @Override
    public String toString()
    {
        return "Player and score ===> [ " + playerIdx + " , " + bonusScore + " ]";
    }
}

