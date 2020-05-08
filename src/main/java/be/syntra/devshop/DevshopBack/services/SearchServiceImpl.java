package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.SearchModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {
    private SearchModel searchModel = null;
    private ProductService productService;

    @Autowired
    public SearchServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void setSearchModel(SearchModel searchModel) {
        this.searchModel = searchModel;
    }

    @Override
    public List<Product> applySearchModel(SearchModel searchModel) {
        final Comparator<Product> productNameComparator = (searchModel.isSortAscendingName())
                ? Comparator.comparing(Product::getName)
                : Comparator.comparing(Product::getName).reversed();
        final Comparator<Product> productPriceComparator = (searchModel.isSortAscendingPrice())
                ? Comparator.comparing(Product::getPrice)
                : Comparator.comparing(Product::getPrice).reversed();
        List<Product> productList;

        if (searchModel.isArchivedView()) {
            productList = productService.findAllByArchivedTrue();
        } else {
            productList = productService.findAllByArchivedFalse();
        }

        if (null != searchModel.getSearchRequest()) {
            productList = productList.parallelStream()
                    .filter(product -> product.getName()
                            .toLowerCase()
                            .contains(searchModel.getSearchRequest().toLowerCase()))
                    .collect(Collectors.toUnmodifiableList());
        }

        if (null != searchModel.getDescription()) {
            productList = productList.parallelStream()
                    .filter(product -> product.getDescription()
                            .toLowerCase()
                            .contains(searchModel.getDescription().toLowerCase()))
                    .collect(Collectors.toUnmodifiableList());
        }

        if (searchModel.isActiveFilters()) {
            productList = productList.parallelStream()
                    .filter(product -> product.getPrice()
                            .compareTo(searchModel.getPriceLow()) >= 0 && product.getPrice().compareTo(searchModel.getPriceHigh()) <= 0)
                    .collect(Collectors.toUnmodifiableList());
        }

        if (searchModel.isSortAscendingName()) {
            productList = getSortedList(productList, productNameComparator);
        }

        if (searchModel.isSortAscendingPrice()) {
            productList = getSortedList(productList, productPriceComparator);
        }

        return productList;
    }

    private List<Product> getSortedList(List<Product> products, Comparator<Product> productComparator) {
        return products
                .stream()
                .sorted(productComparator)
                .collect(Collectors.toUnmodifiableList());
    }
}
