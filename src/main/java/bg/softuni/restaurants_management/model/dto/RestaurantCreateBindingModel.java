package bg.softuni.restaurants_management.model.dto;

import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.validators.UniqueRestaurantName;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class RestaurantCreateBindingModel {
    @NotEmpty
    @UniqueRestaurantName
    private String name;

    @NotEmpty
    private String lon;

    @NotEmpty
    private String lat;

    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public RestaurantCreateBindingModel setFile(MultipartFile file) {
        this.file = file;
        return this;
    }

    public String getName() {
        return name;
    }

    public Restaurant mapToEntity(String url) {
        Restaurant restaurant = new Restaurant();


        BigDecimal lonAsDecimal = parseToBigDecimal(this.lon);
        BigDecimal latAsDecimal = parseToBigDecimal(this.lat);

        if (lonAsDecimal != null && latAsDecimal != null){
            restaurant.setLon(lonAsDecimal);
            restaurant.setLat(latAsDecimal);
        }

        restaurant.setName(this.name);
        restaurant.setImgUrl(url);

        return restaurant;
    }

    private static BigDecimal parseToBigDecimal(String value){
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e){
            return null;
        }
    }

    public RestaurantCreateBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getLon() {
        return lon;
    }

    public RestaurantCreateBindingModel setLon(String lon) {
        this.lon = lon;
        return this;
    }

    public String getLat() {
        return lat;
    }

    public RestaurantCreateBindingModel setLat(String lat) {
        this.lat = lat;
        return this;
    }
}
