package swantech;

/**
 * @author Ifetayo on 25/04/2015.
 */
public class Profile {
    private String playerName;
    private String ipAddress;
    private int numOfWins;
    private int numOfLoses;
    private int numODraws;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getNumOfWins() {
        return numOfWins;
    }

    public void setNumOfWins(int numOfWins) {
        this.numOfWins = numOfWins;
    }

    public int getNumOfLoses() {
        return numOfLoses;
    }

    public void setNumOfLoses(int numOfLoses) {
        this.numOfLoses = numOfLoses;
    }

    public int getNumODraws() {
        return numODraws;
    }

    public void setNumODraws(int numODraws) {
        this.numODraws = numODraws;
    }
}
