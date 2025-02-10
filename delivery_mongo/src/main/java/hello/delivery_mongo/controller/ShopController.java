package hello.delivery_mongo.controller;

import hello.delivery_mongo.controller.dto.CategoryResponse;
import hello.delivery_mongo.controller.dto.CoordinatesRequest;
import hello.delivery_mongo.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/home-categories")
@RestController
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/distinct")
    public ResponseEntity<CategoryResponse> apiGetCategoriesDistinct(@ModelAttribute @Valid CoordinatesRequest request) {
        return ResponseEntity.ok(
                new CategoryResponse(shopService.getDistinctHomeCategories(request.getLongitude(), request.getLatitude()))
        );
    }
}
