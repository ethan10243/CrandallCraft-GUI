package WOW_Project;

public class Stopwatch { 

    private long start;

    /**
     * Initializes a new stopwatch.
     */
    public Stopwatch()
    {
    	start = 0;
    } 
    
    public void start()
    {
    	start = System.currentTimeMillis();
    }
    
    public void reset()
    {
    	start = 0;
    }
    
    public double getElapsedTime()
    {
    	if(start == 0) return 0;
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

} 

