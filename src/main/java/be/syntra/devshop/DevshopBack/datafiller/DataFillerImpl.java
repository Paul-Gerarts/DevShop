package be.syntra.devshop.DevshopBack.datafiller;

import be.syntra.devshop.DevshopBack.services.DataFillerService;
import be.syntra.devshop.DevshopBack.services.ProductServiceImpl;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED)
@NoArgsConstructor
public class DataFillerImpl implements DataFillerService, ApplicationRunner {

    private ProductServiceImpl productService;

    @Autowired
    public DataFillerImpl(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @Override
    public void importData() {
        productService.initialize();
    }

    @Override
    public void run(ApplicationArguments args) {
        importData();
    }
}


