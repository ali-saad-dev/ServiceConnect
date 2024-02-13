package nl.novi.serviceconnect.core.dtos;

public class CategoryServiceCountDto {

    private String categoryName;

    private Long numberOfServices;

    public CategoryServiceCountDto() {}

    public CategoryServiceCountDto(String categoryName, Long numberOfServices) {
        this.categoryName = categoryName;
        this.numberOfServices = numberOfServices;
    }

    public String getCategoryName() { return categoryName; }
    public Long getNumberOfServices() { return numberOfServices; }
}
