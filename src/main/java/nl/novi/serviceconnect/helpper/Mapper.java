package nl.novi.serviceconnect.helpper;

import nl.novi.serviceconnect.dtos.*;
import nl.novi.serviceconnect.models.Service;
import nl.novi.serviceconnect.models.ServiceCategory;
import nl.novi.serviceconnect.models.ServiceRequest;
import nl.novi.serviceconnect.models.Transaction;

import java.util.List;

public class Mapper {
    public static ServiceOutputDto fromServiceToDto(Service service){
        ServiceOutputDto dto = new ServiceOutputDto();
        dto.id = service.getId();
        dto.name = service.getName();
        dto.description = service.getDescription();
        dto.price = service.getPrice();
        dto.state = service.getState();
        dto.serviceCategory = service.getCategory();
        return dto;
    }

    public static Service fromDtoToService(ServiceInputDto dto){
        Service service = new Service();
        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setPrice(dto.getPrice());
        service.setState(dto.getState());
        service.setCategory(dto.getServiceCategory());
        return  service;
    }

    public static ServiceRequestOutputDto fromServiceRequestToDto(ServiceRequest serviceRequest){
        ServiceRequestOutputDto dto = new ServiceRequestOutputDto();
        dto.id = serviceRequest.getId();
        dto.message = serviceRequest.getMessage();
        dto.requestState = serviceRequest.getState();
        dto.service = serviceRequest.getService();
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
        transaction.setInvoice(dto.getInvoice());
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
}
