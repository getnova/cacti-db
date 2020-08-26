package net.getnova.backend.cacti.endpoints;

import net.getnova.backend.api.annotations.ApiEndpoint;
import net.getnova.backend.api.annotations.ApiEndpointCollection;
import net.getnova.backend.api.annotations.ApiParameter;
import net.getnova.backend.api.data.ApiResponse;
import net.getnova.backend.api.data.ApiResponseStatus;
import net.getnova.backend.cacti.models.Form;
import net.getnova.backend.cacti.models.Specie;
import net.getnova.backend.cacti.reposetories.FormRepository;
import net.getnova.backend.cacti.reposetories.SpecieRepository;

import java.util.UUID;

@ApiEndpointCollection(id = "form", description = "Handle all froms.")
public final class FormEndpointCollection {

  private final SpecieRepository specieRepository;
  private final FormRepository formRepository;

  public FormEndpointCollection(final SpecieRepository specieRepository, final FormRepository formRepository) {
    this.specieRepository = specieRepository;
    this.formRepository = formRepository;
  }

  @ApiEndpoint(id = "list", description = "Lists all forms.")
  private ApiResponse list() {
    return new ApiResponse(ApiResponseStatus.OK, this.formRepository.findAll());
  }

  @ApiEndpoint(id = "add", description = "Add a form.")
  private ApiResponse add(@ApiParameter(id = "name", description = "The name of the specie.") final String name,
                          @ApiParameter(id = "specieId", description = "The id of the form.") final UUID id) {

    final Specie specie = this.specieRepository.findById(id).orElse(null);
    if (specie == null) return new ApiResponse(ApiResponseStatus.NOT_FOUND, "SPECIE");

    return new ApiResponse(ApiResponseStatus.OK, this.formRepository.save(new Form(name, specie)));
  }

  @ApiEndpoint(id = "update", description = "Update a form.")
  private ApiResponse update(@ApiParameter(id = "id", description = "The id of the existing form.") final UUID id,
                             @ApiParameter(id = "name", description = "The new name of the form.") final String name) {

    final Form form = this.formRepository.findById(id).orElse(null);
    if (form == null) return new ApiResponse(ApiResponseStatus.NOT_FOUND, "FORM");

    form.setName(name);

    return new ApiResponse(ApiResponseStatus.OK, this.formRepository.save(form));
  }

  @ApiEndpoint(id = "delete", description = "Delete a form.")
  private ApiResponse delete(@ApiParameter(id = "id", description = "The id of the form, witch should be deleted.") final UUID id) {
    final Form form = this.formRepository.findById(id).orElse(null);
    if (form != null) {
      this.formRepository.delete(form);
      return new ApiResponse(ApiResponseStatus.OK);
    } else {
      return new ApiResponse(ApiResponseStatus.NOT_FOUND, "FORM");
    }
  }
}
