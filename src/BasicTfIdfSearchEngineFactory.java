import edu.upenn.cis121.project.engine.SearchEngineFactory;

/**
 * A {@code BasicTfIdfSearchEngineFactory} constructs instances of a
 * {@link BasicTfIdfSearchEngine}. This class must provide a public default no-argument
 * constructor. This means that you do not need to write any constructor at all; if you do
 * write other constructors, be sure you have one that is public and takes no arguments.
 *
 * @author davix
 */
public class BasicTfIdfSearchEngineFactory implements SearchEngineFactory {

    /**
     * Constructs a new instance of a {@link SearchEngine}.
     *
     * @return a new {@link SearchEngine} instance
     */
    @Override
    public BasicTfIdfSearchEngine newSearchEngine() {
        // TODO: implement.
    	BasicTfIdfSearchEngine s = new BasicTfIdfSearchEngine();
        return s;
    }
}
