package bg.softuni.restaurants_management.service;

import bg.softuni.restaurants_management.model.dto.RestaurantCreateBindingModel;
import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.dto.UploadImgDto;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RestaurantService {
    Restaurant createRestaurant(Restaurant restaurant);

    RestaurantViewDetails getRestaurantViewDetailsByRestaurantId(Long id);

    List<RestaurantViewDetails> getAllRestaurants();
}

