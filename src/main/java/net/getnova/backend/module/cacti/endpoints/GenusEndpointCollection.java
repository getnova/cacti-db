package net.getnova.backend.module.cacti.endpoints;

import lombok.RequiredArgsConstructor;
import net.getnova.backend.api.annotations.ApiEndpoint;
import net.getnova.backend.api.annotations.ApiEndpointCollection;
import net.getnova.backend.api.annotations.ApiParameter;
import net.getnova.backend.api.data.ApiResponse;
import net.getnova.backend.api.data.ApiResponseStatus;
import net.getnova.backend.api.data.ApiType;
import net.getnova.backend.module.cacti.models.Genus;
import net.getnova.backend.module.cacti.repositories.GenusRepository;

import java.util.UUID;

@RequiredArgsConstructor
@ApiEndpointCollection(id = "genus", description = "Handle all genres.", type = ApiType.WEBSOCKET)
public final class GenusEndpointCollection {

  private final GenusRepository genusRepository;

  @ApiEndpoint(id = "list", description = "Lists all genres.")
  private ApiResponse list() {
    return new ApiResponse(ApiResponseStatus.OK, this.genusRepository.findByOrderByName());
  }

  @ApiEndpoint(id = "add", description = "Add a genus.")
  private ApiResponse add(@ApiParameter(id = "name", description = "The name of the genus.") final String name) {
    return new ApiResponse(ApiResponseStatus.OK, this.genusRepository.save(new Genus(name)));
  }

  @ApiEndpoint(id = "update", description = "Update a genus.")
  private ApiResponse update(@ApiParameter(id = "id", description = "The id of the existing genus.") final UUID id,
                             @ApiParameter(id = "name", description = "The new name of the genus.") final String name) {
    final Genus genus = this.genusRepository.findById(id).orElse(null);
    if (genus == null) return new ApiResponse(ApiResponseStatus.NOT_FOUND, "GENUS");

    genus.setName(name);
    return new ApiResponse(ApiResponseStatus.OK, this.genusRepository.save(genus));
  }

  @ApiEndpoint(id = "delete", description = "Delete a genus.")
  private ApiResponse delete(@ApiParameter(id = "id", description = "The id of the genus, witch should be deleted.") final UUID id) {
    final Genus genus = this.genusRepository.findById(id).orElse(null);
    if (genus != null) {
      this.genusRepository.delete(genus);
      return new ApiResponse(ApiResponseStatus.OK);
    } else {
      return new ApiResponse(ApiResponseStatus.NOT_FOUND, "FORM");
    }
  }
}
