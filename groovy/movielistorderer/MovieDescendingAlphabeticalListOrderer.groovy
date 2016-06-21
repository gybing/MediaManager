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
class MovieDescendingAlphabeticalListOrderer extends ListOrderer<MovieEntryWrapper> implements Authorable {
	
	@Override
	public String getName() {
		return "Alphabetically (Descending Z-A)";
	}

	@Override
	public List<MovieEntryWrapper> order(List<MovieEntryWrapper> list) {
		Collections.sort(list, { a, b  -> b.toString().toLowerCase().compareTo(a.toString().toLowerCase())});
		return list;
	}

	@Override
	public String getAuthorName() {
		return "Jake Bellotti";
	}

}
