package be.syntra.devshop.DevshopBack.datafiller;

import be.syntra.devshop.DevshopBack.services.DataFillerService;
import be.syntra.devshop.DevshopBack.services.DataFillerServiceImpl;
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
public class DataFillerExecutor implements DataFillerService, ApplicationRunner {

    private DataFillerServiceImpl dataFiller;

    @Autowired
    public DataFillerExecutor(DataFillerServiceImpl dataFiller) {
        this.dataFiller = dataFiller;
    }

    @Override
    public void importData() {
        dataFiller.initialize();
    }

    @Override
    public void run(ApplicationArguments args) {
        importData();
    }
}


