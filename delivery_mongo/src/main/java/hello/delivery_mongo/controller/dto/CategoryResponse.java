package hello.delivery_mongo.controller.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryResponse {
    private final List<String> categories;

    public CategoryResponse(List<String> categories) {
        this.categories = categories;
    }
}
