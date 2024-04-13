package hu.cubix.hr.tomk99.mapper;

import hu.cubix.hr.tomk99.dto.CompanyDto;
import hu.cubix.hr.tomk99.model.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    public List<CompanyDto> companiesToDtos(List<Company> companies);
    public CompanyDto companyToDto(Company company);

    public Company dtoToCompany(CompanyDto companyDto);
}
