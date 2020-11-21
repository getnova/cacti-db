package net.getnova.module.cacti.endpoint;

import io.netty.handler.codec.http.HttpResponseStatus;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.getnova.framework.api.annotations.ApiEndpoint;
import net.getnova.framework.api.annotations.ApiEndpointCollection;
import net.getnova.framework.api.annotations.ApiParameter;
import net.getnova.framework.api.data.ApiResponse;
import net.getnova.framework.api.data.ApiType;
import net.getnova.module.cacti.model.Genus;
import net.getnova.module.cacti.model.Specie;
import net.getnova.module.cacti.repository.GenusRepository;
import net.getnova.module.cacti.repository.SpecieRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@ApiEndpointCollection(id = "specie", description = "Handle all genres.", type = ApiType.REST)
public class SpecieEndpointCollection {

  private final GenusRepository genusRepository;
  private final SpecieRepository specieRepository;

  @ApiEndpoint(id = "list", description = "Lists all species.")
  public ApiResponse list() {
    return new ApiResponse(HttpResponseStatus.OK, this.specieRepository.findAllByOrderByName());
  }

  @Transactional
  @ApiEndpoint(id = "add", description = "Add a specie.")
  public ApiResponse add(@ApiParameter(id = "name", description = "The name of the specie.") final String name,
                         @ApiParameter(id = "genusId", description = "The id of the genus.") final UUID genusId) {

    final Optional<Genus> genus = this.genusRepository.findById(genusId);
    if (genus.isEmpty()) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "GENUS");

    return new ApiResponse(HttpResponseStatus.OK, this.specieRepository.save(new Specie(name, genus.get())));
  }

  @Transactional
  @ApiEndpoint(id = "update", description = "Update a specie.")
  public ApiResponse update(@ApiParameter(id = "id", description = "The id of the existing specie.") final UUID id,
                            @ApiParameter(id = "name", description = "The new name of the specie.") final String name) {

    final Specie specie = this.specieRepository.findById(id).orElse(null);
    if (specie == null) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "SPECIE");

    specie.setName(name);

    return new ApiResponse(HttpResponseStatus.OK, this.specieRepository.save(specie));
  }

  @Transactional
  @ApiEndpoint(id = "delete", description = "Delete a specie.")
  public ApiResponse delete(@ApiParameter(id = "id", description = "The id of the specie, witch should be deleted.") final UUID id) {
    final Optional<Specie> specie = this.specieRepository.findById(id);
    if (specie.isEmpty()) {
      return new ApiResponse(HttpResponseStatus.NOT_FOUND, "FORM");
    }

    this.specieRepository.delete(specie.get());
    return new ApiResponse(HttpResponseStatus.OK);
  }
}
