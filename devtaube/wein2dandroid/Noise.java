package devtaube.wein2dandroid;

public class Noise
{
    // variables
    private double[][] noiseMap;

    public static final int RANDOM = 0;
    public static final int SMOOTH = 1;

    // regenerate values
    public void generateValues(int noiseType, int sizeX, int sizeY)
    {
        fillNoiseTable(noiseType, sizeX, sizeY, 1);
    }
    public void generateValues(int noiseType, int sizeX, int sizeY, double noiseMultiplier)
    {
        fillNoiseTable(noiseType, sizeX, sizeY, noiseMultiplier);
    }

    private void fillNoiseTable(int noiseType, int sizeX, int sizeY, double noiseMultiplier)
    {
        noiseMap = new double[sizeX][sizeY];
        for(int ix = 0; ix < sizeX; ix++) { for(int iy = 0; iy < sizeY; iy++)
        {
            noiseMap[ix][iy] = generateNoiseValue(noiseType, ix, iy, noiseMultiplier);
        }}
    }

    // get value from noisemap
    public double getNoiseValue(int x, int y)
    {
        return noiseMap[x][y];
    }

    // get noise map
    public double[][] getNoiseMap()
    {
        return noiseMap;
    }

    // generate noise value
    private double generateNoiseValue(int noiseType, int x, int y, double noiseMultiplier)
    {
        switch(noiseType)
        {
            case 1:
                if(x == 0 && y == 0) return 0.5;
                else
                {
                    // get average noise value
                    double noiseMapValue = 0;
                    if(x == 0) noiseMapValue = noiseMap[0][y - 1];
                    else if(y == 0) noiseMapValue = noiseMap[x - 1][0];
                    else noiseMapValue = (noiseMap[x - 1][y] + noiseMap[x][y - 1]) / 2;
                    // mutate that noise value
                    int changeValue = (int) Math.floor(Math.random() * (5 * noiseMultiplier));
                    switch(changeValue)
                    {
                        case 0: if(noiseMapValue > 0.1) return noiseMapValue - 0.05;
                        case 1: if(noiseMapValue <= 0.9) return noiseMapValue + 0.05;
                        default: return noiseMapValue;
                    }
                }
            default:
                return Math.random();
        }
    }
}
