package nl.novi.serviceconnect.helpper;

import nl.novi.serviceconnect.dtos.*;
import nl.novi.serviceconnect.models.*;

public class Mapper {
    public static ServiceOutputDto fromServiceToDto(Service service){
        ServiceOutputDto dto = new ServiceOutputDto();
        dto.id = service.getId();
        dto.name = service.getName();
        dto.description = service.getDescription();
        dto.price = service.getPrice();
        dto.state = service.getState();
        dto.serviceCategory = service.getCategory();
        dto.username = service.getUser() != null ? service.getUser().getUsername() : null;
        return dto;
    }

    public static Service fromDtoToService(ServiceInputDto dto){
        Service service = new Service();
        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setPrice(dto.getPrice());
        service.setState(dto.getState());
        service.setCategory(dto.getCategory());
        return  service;
    }

    public static ServiceRequestOutputDto fromServiceRequestToDto(ServiceRequest serviceRequest){
        ServiceRequestOutputDto dto = new ServiceRequestOutputDto();
        dto.id = serviceRequest.getId();
        dto.username = serviceRequest.getUser() != null ? serviceRequest.getUser().getUsername() : null;
        dto.message = serviceRequest.getMessage();
        dto.requestState = serviceRequest.getState();
        dto.service = fromServiceToDto(serviceRequest.getService());
        return dto;
    }

    public static ServiceRequest fromDtoToServiceRequest(ServiceRequestInputDto inputDto){
        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setMessage(inputDto.getMessage());
        serviceRequest.setState(inputDto.getState());
        serviceRequest.setService(inputDto.getService());
        return  serviceRequest;
    }

    public static TransactionOutputDto fromTransactionToDto(Transaction transaction){
        TransactionOutputDto dto = new TransactionOutputDto();
        dto.id = transaction.getId();
        dto.transactionDate = transaction.getTransactionDate();
        dto.isPayed = transaction.getPayed();
        dto.invoice = transaction.getInvoice();
        dto.serviceRequestId = transaction.getServiceRequest() != null ? transaction.getServiceRequest().getId() : null;
        return dto;
    }

    public static Transaction fromDtoToTransaction(TransactionInputDto dto){
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setPayed(dto.getPayed());
        return  transaction;
    }

    public static ServiceCategoryOutputDto fromCategoryToDto(ServiceCategory category){
        ServiceCategoryOutputDto dto = new ServiceCategoryOutputDto();
        dto.id = category.getId();
        dto.name = category.getName();
        dto.description = category.getDescription();
        return dto;
    }

    public static ServiceCategory fromDtoToCategory(ServiceCategoryInputDto dto){
        ServiceCategory category = new ServiceCategory();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return  category;
    }

    public static UserDto fromUser(User user){
        UserDto dto = new UserDto();
        dto.username = user.getUsername();
        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.email = user.getEmail();
        dto.authorities = user.getAuthorities();
        return dto;
    }

    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        return user;
    }
}
