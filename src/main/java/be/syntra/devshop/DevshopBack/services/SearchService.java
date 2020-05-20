package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.models.ProductPage;
import be.syntra.devshop.DevshopBack.models.SearchModel;

public interface SearchService {

    ProductPage applySearchModel(SearchModel searchModel);
}
