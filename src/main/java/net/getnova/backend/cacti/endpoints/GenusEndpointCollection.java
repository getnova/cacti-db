package net.getnova.backend.cacti.endpoints;

import net.getnova.backend.api.annotations.ApiEndpoint;
import net.getnova.backend.api.annotations.ApiEndpointCollection;
import net.getnova.backend.api.annotations.ApiParameter;
import net.getnova.backend.api.data.ApiResponse;
import net.getnova.backend.api.data.ApiResponseStatus;
import net.getnova.backend.cacti.models.Genus;
import net.getnova.backend.cacti.reposetories.GenusRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
@ApiEndpointCollection(id = "genus", description = "Handle all genres.")
public final class GenusEndpointCollection {

    @Inject
    private GenusRepository genusRepository;

    @ApiEndpoint(id = "list", description = "Lists all genres.")
    private ApiResponse list() {
        return new ApiResponse(ApiResponseStatus.OK, this.genusRepository.list());
    }

    @ApiEndpoint(id = "add", description = "Add a genus.")
    private ApiResponse add(@ApiParameter(id = "name", description = "The name of the genus.") final String name) {
        return new ApiResponse(ApiResponseStatus.OK, this.genusRepository.save(new Genus(name)));
    }

    @ApiEndpoint(id = "update", description = "Update a genus.")
    private ApiResponse update(@ApiParameter(id = "id", description = "The id of the existing genus.") final UUID id,
                               @ApiParameter(id = "name", description = "The new name of the genus.") final String name) {
        final Genus genus = this.genusRepository.find(id);
        genus.setName(name);
        return new ApiResponse(ApiResponseStatus.OK, this.genusRepository.save(genus));
    }

    @ApiEndpoint(id = "delete", description = "Delete a genus.")
    private ApiResponse delete(@ApiParameter(id = "id", description = "The id of the genus, witch should be deleted.") final UUID id) {
        return this.genusRepository.delete(id) ? new ApiResponse(ApiResponseStatus.OK) : new ApiResponse(ApiResponseStatus.NOT_FOUND, "GENUS");
    }
}
