package pl.shop.review.controller;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.shop.common.model.Review;
import pl.shop.review.controller.dto.ReviewDto;
import pl.shop.review.service.ReviewService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public Review addReview(@RequestBody @Valid ReviewDto reviewDto){
        return reviewService.addReview(Review.builder()
                .authorName(clearContent(reviewDto.authorName()))
                .content(clearContent(reviewDto.content()))
                .productId(reviewDto.productId())
                .build());
    }

    private String clearContent(String text) {
        return Jsoup.clean(text, Safelist.none());
    }
}
