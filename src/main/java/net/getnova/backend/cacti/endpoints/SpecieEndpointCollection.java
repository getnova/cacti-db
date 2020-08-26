package net.getnova.backend.cacti.endpoints;

import net.getnova.backend.api.annotations.ApiEndpoint;
import net.getnova.backend.api.annotations.ApiEndpointCollection;
import net.getnova.backend.api.annotations.ApiParameter;
import net.getnova.backend.api.data.ApiResponse;
import net.getnova.backend.api.data.ApiResponseStatus;
import net.getnova.backend.cacti.models.Genus;
import net.getnova.backend.cacti.models.Specie;
import net.getnova.backend.cacti.reposetories.GenusRepository;
import net.getnova.backend.cacti.reposetories.SpecieRepository;

import java.util.UUID;

@ApiEndpointCollection(id = "specie", description = "Handle all genres.")
public final class SpecieEndpointCollection {

  private final GenusRepository genusRepository;
  private final SpecieRepository specieRepository;

  public SpecieEndpointCollection(final GenusRepository genusRepository, final SpecieRepository specieRepository) {
    this.genusRepository = genusRepository;
    this.specieRepository = specieRepository;
  }

  @ApiEndpoint(id = "list", description = "Lists all species.")
  private ApiResponse list() {
    return new ApiResponse(ApiResponseStatus.OK, this.specieRepository.findAll());
  }

  @ApiEndpoint(id = "add", description = "Add a specie.")
  private ApiResponse add(@ApiParameter(id = "name", description = "The name of the specie.") final String name,
                          @ApiParameter(id = "genusId", description = "The id of the genus.") final UUID genusId) {

    final Genus genus = this.genusRepository.findById(genusId).orElse(null);
    if (genus == null) return new ApiResponse(ApiResponseStatus.NOT_FOUND, "GENUS");

    return new ApiResponse(ApiResponseStatus.OK, this.specieRepository.save(new Specie(name, genus)));
  }

  @ApiEndpoint(id = "update", description = "Update a specie.")
  private ApiResponse update(@ApiParameter(id = "id", description = "The id of the existing specie.") final UUID id,
                             @ApiParameter(id = "name", description = "The new name of the specie.") final String name) {

    final Specie specie = this.specieRepository.findById(id).orElse(null);
    if (specie == null) return new ApiResponse(ApiResponseStatus.NOT_FOUND, "SPECIE");

    specie.setName(name);

    return new ApiResponse(ApiResponseStatus.OK, this.specieRepository.save(specie));
  }

  @ApiEndpoint(id = "delete", description = "Delete a specie.")
  private ApiResponse delete(@ApiParameter(id = "id", description = "The id of the specie, witch should be deleted.") final UUID id) {
    final Specie specie = this.specieRepository.findById(id).orElse(null);
    if (specie != null) {
      this.specieRepository.delete(specie);
      return new ApiResponse(ApiResponseStatus.OK);
    } else {
      return new ApiResponse(ApiResponseStatus.NOT_FOUND, "FORM");
    }
  }
}
