package hello.delivery_mongo.domain;

import lombok.Getter;

@Getter
public enum CategoryCode {
    CHICKEN("chicken"),
    BURGER("burger"),
    PIZZA("pizza"),
    SANDWICH("sandwich"),
    KOREAN("korean"),
    JAPANESE("japanese"),
    SALAD("salad"),
    CAFE("cafe");

    private final String value;

    CategoryCode(String value) {
        this.value = value;
    }
}
