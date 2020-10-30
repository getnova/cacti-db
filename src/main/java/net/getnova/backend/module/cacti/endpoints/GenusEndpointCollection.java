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
import net.getnova.backend.module.cacti.repositories.GenusRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@ApiEndpointCollection(id = "genus", description = "Handle all genres.", type = ApiType.REST)
public class GenusEndpointCollection {

  private final GenusRepository genusRepository;

  @ApiEndpoint(id = "list", description = "Lists all genres.")
  protected ApiResponse list() {
    return new ApiResponse(HttpResponseStatus.OK, this.genusRepository.findAllByOrderByName());
  }

  @Transactional
  @ApiEndpoint(id = "add", description = "Add a genus.")
  protected ApiResponse add(@ApiParameter(id = "name", description = "The name of the genus.") final String name) {
    return new ApiResponse(HttpResponseStatus.OK, this.genusRepository.save(new Genus(name)));
  }

  @Transactional
  @ApiEndpoint(id = "update", description = "Update a genus.")
  protected ApiResponse update(@ApiParameter(id = "id", description = "The id of the existing genus.") final UUID id,
                               @ApiParameter(id = "name", description = "The new name of the genus.") final String name) {
    final Genus genus = this.genusRepository.findById(id).orElse(null);
    if (genus == null) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "GENUS");

    genus.setName(name);
    return new ApiResponse(HttpResponseStatus.OK, this.genusRepository.save(genus));
  }

  @Transactional
  @ApiEndpoint(id = "delete", description = "Delete a genus.")
  protected ApiResponse delete(@ApiParameter(id = "id", description = "The id of the genus, witch should be deleted.") final UUID id) {
    final Optional<Genus> genus = this.genusRepository.findById(id);
    if (genus.isEmpty()) {
      return new ApiResponse(HttpResponseStatus.NOT_FOUND, "FORM");
    }

    this.genusRepository.delete(genus.get());
    return new ApiResponse(HttpResponseStatus.OK);
  }
}
