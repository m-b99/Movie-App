package ma.ac.ensias.model.validator;

import ma.ac.ensias.model.entity.Review;

public class ReviewValidator {

    private ReviewValidator(){}

    public static boolean validateReview(Review review) {
        if (review.getText().length() > 3000) {
            return false;
        }
        return true;
    }
}