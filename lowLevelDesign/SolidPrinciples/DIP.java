package SolidPrinciples;
/**
 * DIP Principle: 
 * 1. High-level modules should not depend on low-level modules. Both should depend on abstractions.
 * 2. Abstractions should not depend on details. Details should depend on abstractions.
 */

interface RecommendationStrategy {
    public void recommend();
}

// LOW LEVEL MODULES
class TrendingRecommendation implements RecommendationStrategy {
    public void recommend() {
        // Implementation logic
    }
}

class GenreRecommendation implements RecommendationStrategy {
    public void recommend() {
        // Implementation logic
    }
}

class RecentRecommendation implements RecommendationStrategy {
    // Adhering to the interface ensures this class is "pluggable".
    // We avoid specific names like recentRecommend() to stay decoupled.
    public void recommend() {
        // Implementation logic
    }
}

class RecommendationAlgorithm {
    private RecommendationStrategy rs;

    // Dependency Injection: The tool is handed to the algorithm via constructor.
    public RecommendationAlgorithm(RecommendationStrategy strategy) {
        this.rs = strategy;
    }

    public void recommend() {
        rs.recommend();
    }
}

public class DIP {

    // HIGH LEVEL MODULES
    public static void main(String[] args) {
        
        /* * STEP 1: Tight Coupling (The Bad Way)
         * ----------------------------------
         * TrendingRecommendation tr = new TrendingRecommendation();
         * tr.recommend();
         * * RecentRecommendation rr = new RecentRecommendation();
         * rr.recentRecommend();
         * * As the func name was not same, we had to replace each line that used 
         * an earlier way of recommendation. High and Low level modules are tightly 
         * coupled; if anything changes underneath, everyone using it must change.
         */

        /* * STEP 2: Polymorphism (The Better Way)
         * ------------------------------------
         * RecommendationStrategy rs = new TrendingRecommendation();
         * rs.recommend(); 
         * * Not tightly coupled anymore. Tomorrow if a new algo comes, we just 
         * change the object creation statement here.
         * ANALOGY: The main method (client) still acts as both the MANAGER 
         * (deciding to recommend) and the FACTORY (knowing exactly which 
         * concrete class to instantiate).
         */

        /* * STEP 3: Complete Inversion (The Best Way - DIP)
         * ----------------------------------------------
         * ANALOGY: The RecommendationAlgorithm class acts as a "Black Box." 
         * You hand it a tool, and it just knows how to use it. The logic of 
         * RUNNING the recommendation is now separated from the logic of 
         * CREATING the recommendation.
         */
        RecommendationAlgorithm algo = new RecommendationAlgorithm(new TrendingRecommendation());
        algo.recommend();
    }
}