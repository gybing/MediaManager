package movielistorderer

import java.util.Collections;
import java.util.List

import com.jakebellotti.model.Authorable;
import com.jakebellotti.model.ListOrderer;
import com.jakebellotti.model.movie.MovieEntryWrapper;

/**
 *
 * @author Jake Bellotti
 * @since Jun 21, 2016
 */
class MovieIMDBScoreListOrderer extends ListOrderer<MovieEntryWrapper> implements Authorable {

	@Override
	public String getName() {
		return "By IMDB Score (Descending)";
	}

	@Override
	public List<MovieEntryWrapper> order(List<MovieEntryWrapper> list) {
		Collections.sort(list, { a, b ->
			Double aScore = new Double(-1);
			Double bScore = new Double(-1);
			if (a.getMovieEntry().getDefinition().isPresent()) {
				aScore = a.getMovieEntry().getDefinition().get().getImdbRating();
			}
			if (b.getMovieEntry().getDefinition().isPresent()) {
				bScore = b.getMovieEntry().getDefinition().get().getImdbRating();
			}
			return bScore.compareTo(aScore);
		});
		return list;
	}

	@Override
	public String getAuthorName() {
		return "Jake Bellotti";
	}
}
