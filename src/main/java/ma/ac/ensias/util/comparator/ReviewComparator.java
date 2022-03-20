package ma.ac.ensias.util.comparator;

import ma.ac.ensias.model.entity.Review;

import java.util.Comparator;

public class ReviewComparator implements Comparator<Review> {
    @Override
    public int compare(Review first, Review second) {
        return second.getId().compareTo(first.getId());
    }
}
