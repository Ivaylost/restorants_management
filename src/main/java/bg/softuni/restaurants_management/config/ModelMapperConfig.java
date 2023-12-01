package bg.softuni.restaurants_management.config;

import bg.softuni.restaurants_management.model.dto.*;
import bg.softuni.restaurants_management.model.entity.Reservation;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.model.entity.TableEntity;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.model.enums.RoleEnums;
import bg.softuni.restaurants_management.repository.RoleRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class ModelMapperConfig {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ModelMapperConfig(RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();

        Provider<UserEntity> newUserProvider = req -> new UserEntity()
                .setRoles(List.of(roleRepository.findByRole(RoleEnums.USER)));

        Converter<String, String> passwordConverter
                = ctx -> (ctx.getSource() == null)
                ? null
                : passwordEncoder.encode(ctx.getSource());

        modelMapper
                .createTypeMap(UserRegistrationBindingModel.class, UserEntity.class)
                .setProvider(newUserProvider)
                .addMappings(mapper -> mapper
                        .using(passwordConverter)
                        .map(UserRegistrationBindingModel::getPassword, UserEntity::setPassword));

        modelMapper
                .createTypeMap(UserEntity.class, UserDto.class)
                .addMappings(mapper -> {
                    mapper.map(UserEntity::getFullName, UserDto::setFullName);
                });

        modelMapper
                .createTypeMap(Restaurant.class, RestaurantViewDetails.class)
                .addMappings(mapper -> {
                    mapper.map(Restaurant::getTableEntities, RestaurantViewDetails::setTableViewDetails);
                });

        Provider<ReservationViewModel> bindReservationToReservationViewModel = ctx -> {
            Reservation reservation = (Reservation) ctx.getSource();

            ReservationViewModel reservationViewModel = new ReservationViewModel();
            reservationViewModel.setReservationId(reservation.getId());
            reservationViewModel.setTableName(reservationViewModel.getTableName());
            reservationViewModel.setRestaurantName(reservation.getTable().getRestaurant().getName());
            reservationViewModel.setReservationName(reservation.getReservations().name());
            return reservationViewModel;
        };

        modelMapper
                .createTypeMap(Reservation.class, ReservationViewModel.class)
                .setProvider(bindReservationToReservationViewModel);

        return modelMapper;
    }
}
