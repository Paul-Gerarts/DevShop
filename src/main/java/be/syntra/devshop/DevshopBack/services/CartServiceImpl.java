package be.syntra.devshop.DevshopBack.services;


import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.exceptions.UserNotFoundException;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.repositories.UserRepository;
import be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility;
import org.springframework.stereotype.Service;



@Service
public class CartServiceImpl implements CartService {
    private UserRepository userRepository;

    public CartServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public CartDto saveFinalizedCart(CartDto cartDto, Long userId) {
        User user = userRepository.findById(userId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s could not be found", userId)));
        user.getArchivedCarts()
                .add(CartMapperUtility.convertToCart(cartDto));
        userRepository.save(user);

        return cartDto;
    }

}
