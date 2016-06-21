package movielistorderer

import java.security.PublicKey;
import java.util.Collections
import java.util.List

import com.jakebellotti.model.Authorable;
import com.jakebellotti.model.ListOrderer
import com.jakebellotti.model.movie.MovieEntryWrapper

/**
 *
 * @author Jake Bellotti
 * @since Jun 21, 2016
 */
class MovieAscendingAlphabeticalListOrderer extends ListOrderer<MovieEntryWrapper> implements Authorable {

	@Override
	public String getName() {
		return "Alphabetically (Ascending A-Z)";
	}

	@Override
	public List<MovieEntryWrapper> order(List<MovieEntryWrapper> list) {
		Collections.sort(list, { a, b -> a.toString().toLowerCase().compareTo(b.toString().toLowerCase())})
		return list;
	}

	@Override
	public String getAuthorName() {
		return "Jake Bellotti";
	}
}
