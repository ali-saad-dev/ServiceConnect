package nl.novi.serviceconnect.controllers;

import nl.novi.serviceconnect.dtos.CategoryServiceCountDto;
import nl.novi.serviceconnect.services.IQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/querys")
public class QueryController {

    private final IQueryService queryService;

    public QueryController(IQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/count-per-category")
    public ResponseEntity<List<CategoryServiceCountDto>> countServicesPerCategory() {
        List<CategoryServiceCountDto> result = queryService.countServicesPerCategory();
        return ResponseEntity.ok(result);
    }
}
