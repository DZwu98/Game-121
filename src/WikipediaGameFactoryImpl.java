import edu.upenn.cis121.project.data.AbstractIdentifiable;
import edu.upenn.cis121.project.engine.WikipediaGame;
import edu.upenn.cis121.project.graph.DirectedGraph;
import edu.upenn.cis121.project.engine.WikipediaGameFactory;

/**
 * DO NOT MODIFY THIS FILE.
 *
 * @author davix
 */
public class WikipediaGameFactoryImpl implements WikipediaGameFactory {

    /**
     * Creates an instance of a solver for the Wikipedia game for the specified graph.
     *
     * @param g   the graph
     * @param <V> the type of the vertices of the graph
     * @return a new instance of a Wikipedia game solver
     */
    @Override
    public <V extends AbstractIdentifiable> WikipediaGame<V> create(DirectedGraph<V> g) {
        return new WikipediaGameImpl<>(g);
    }
}
