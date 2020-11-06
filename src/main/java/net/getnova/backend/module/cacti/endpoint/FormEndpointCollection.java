package net.getnova.backend.module.cacti.endpoint;

import io.netty.handler.codec.http.HttpResponseStatus;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.getnova.backend.api.annotations.ApiEndpoint;
import net.getnova.backend.api.annotations.ApiEndpointCollection;
import net.getnova.backend.api.annotations.ApiParameter;
import net.getnova.backend.api.data.ApiResponse;
import net.getnova.backend.api.data.ApiType;
import net.getnova.backend.module.cacti.model.Form;
import net.getnova.backend.module.cacti.model.Specie;
import net.getnova.backend.module.cacti.repository.FormRepository;
import net.getnova.backend.module.cacti.repository.SpecieRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@ApiEndpointCollection(id = "form", description = "Handle all froms.", type = ApiType.REST)
public class FormEndpointCollection {

  private final SpecieRepository specieRepository;
  private final FormRepository formRepository;

  @ApiEndpoint(id = "list", description = "Lists all forms.")
  public ApiResponse list() {
    return new ApiResponse(HttpResponseStatus.OK, this.formRepository.findAllByOrderByName());
  }

  @Transactional
  @ApiEndpoint(id = "add", description = "Add a form.")
  public ApiResponse add(@ApiParameter(id = "name", description = "The name of the specie.") final String name,
                         @ApiParameter(id = "specieId", description = "The id of the form.") final UUID id) {

    final Optional<Specie> specie = this.specieRepository.findById(id);
    if (specie.isEmpty()) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "SPECIE");

    return new ApiResponse(HttpResponseStatus.OK, this.formRepository.save(new Form(name, specie.get())));
  }

  @Transactional
  @ApiEndpoint(id = "update", description = "Update a form.")
  public ApiResponse update(@ApiParameter(id = "id", description = "The id of the existing form.") final UUID id,
                            @ApiParameter(id = "name", description = "The new name of the form.") final String name) {

    final Form form = this.formRepository.findById(id).orElse(null);
    if (form == null) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "FORM");

    form.setName(name);

    return new ApiResponse(HttpResponseStatus.OK, this.formRepository.save(form));
  }

  @Transactional
  @ApiEndpoint(id = "delete", description = "Delete a form.")
  public ApiResponse delete(@ApiParameter(id = "id", description = "The id of the form, witch should be deleted.") final UUID id) {
    final Optional<Form> form = this.formRepository.findById(id);
    if (form.isEmpty()) {
      return new ApiResponse(HttpResponseStatus.NOT_FOUND, "FORM");
    }

    this.formRepository.delete(form.get());
    return new ApiResponse(HttpResponseStatus.OK);
  }
}
