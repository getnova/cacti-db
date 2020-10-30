package net.getnova.backend.module.cacti.endpoints;

import io.netty.handler.codec.http.HttpResponseStatus;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.getnova.backend.api.annotations.ApiEndpoint;
import net.getnova.backend.api.annotations.ApiEndpointCollection;
import net.getnova.backend.api.annotations.ApiParameter;
import net.getnova.backend.api.data.ApiResponse;
import net.getnova.backend.api.data.ApiType;
import net.getnova.backend.module.cacti.models.Genus;
import net.getnova.backend.module.cacti.models.Specie;
import net.getnova.backend.module.cacti.repositories.GenusRepository;
import net.getnova.backend.module.cacti.repositories.SpecieRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@ApiEndpointCollection(id = "specie", description = "Handle all genres.", type = ApiType.REST)
public class SpecieEndpointCollection {

  private final GenusRepository genusRepository;
  private final SpecieRepository specieRepository;

  @ApiEndpoint(id = "list", description = "Lists all species.")
  protected ApiResponse list() {
    return new ApiResponse(HttpResponseStatus.OK, this.specieRepository.findAllByOrderByName());
  }

  @Transactional
  @ApiEndpoint(id = "add", description = "Add a specie.")
  protected ApiResponse add(@ApiParameter(id = "name", description = "The name of the specie.") final String name,
                            @ApiParameter(id = "genusId", description = "The id of the genus.") final UUID genusId) {

    final Optional<Genus> genus = this.genusRepository.findById(genusId);
    if (genus.isEmpty()) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "GENUS");

    return new ApiResponse(HttpResponseStatus.OK, this.specieRepository.save(new Specie(name, genus.get())));
  }

  @Transactional
  @ApiEndpoint(id = "update", description = "Update a specie.")
  protected ApiResponse update(@ApiParameter(id = "id", description = "The id of the existing specie.") final UUID id,
                               @ApiParameter(id = "name", description = "The new name of the specie.") final String name) {

    final Specie specie = this.specieRepository.findById(id).orElse(null);
    if (specie == null) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "SPECIE");

    specie.setName(name);

    return new ApiResponse(HttpResponseStatus.OK, this.specieRepository.save(specie));
  }

  @Transactional
  @ApiEndpoint(id = "delete", description = "Delete a specie.")
  protected ApiResponse delete(@ApiParameter(id = "id", description = "The id of the specie, witch should be deleted.") final UUID id) {
    final Optional<Specie> specie = this.specieRepository.findById(id);
    if (specie.isEmpty()) {
      return new ApiResponse(HttpResponseStatus.NOT_FOUND, "FORM");
    }

    this.specieRepository.delete(specie.get());
    return new ApiResponse(HttpResponseStatus.OK);
  }
}
