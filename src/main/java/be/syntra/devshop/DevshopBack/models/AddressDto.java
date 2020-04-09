package be.syntra.devshop.DevshopBack.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private String street;
    private String number;
    private String boxNumber;
    private String postalCode;
    private String city;
    private String country;
}
