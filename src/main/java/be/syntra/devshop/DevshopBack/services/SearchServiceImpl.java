package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.SearchModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {
    private final ProductService productService;

    @Autowired
    public SearchServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<Product> applySearchModel(SearchModel searchModel) {
        List<Product> productList;

        productList = (searchModel.isArchivedView())
                ? productService.findAllByArchivedTrue() :
                productService.findAllByArchivedFalse();

        if (StringUtils.hasText((searchModel.getSearchRequest()))) {
            productList = productList.parallelStream()
                    .filter(product -> product.getName()
                            .toLowerCase()
                            .contains(searchModel.getSearchRequest().toLowerCase()))
                    .collect(Collectors.toUnmodifiableList());
        }

        if (StringUtils.hasText(searchModel.getDescription())) {
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

        productList = sortListByName(productList, searchModel.isSortAscendingName());

        productList = sortListByPrice(productList, searchModel.isSortAscendingPrice());

        return productList;
    }

    private List<Product> sortListByName(List<Product> productList, boolean sortAsc) {
        final Comparator<Product> productNameComparator = (sortAsc)
                ? Comparator.comparing(Product::getName)
                : Comparator.comparing(Product::getName).reversed();
        return getSortedList(productList, productNameComparator);
    }

    private List<Product> sortListByPrice(List<Product> productList, boolean sortAsc) {
        final Comparator<Product> productPriceComparator = (sortAsc)
                ? Comparator.comparing(Product::getPrice)
                : Comparator.comparing(Product::getPrice).reversed();
        return getSortedList(productList, productPriceComparator);
    }

    private List<Product> getSortedList(List<Product> products, Comparator<Product> productComparator) {
        return products
                .stream()
                .sorted(productComparator)
                .collect(Collectors.toUnmodifiableList());
    }
}
