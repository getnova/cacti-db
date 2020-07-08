package net.getnova.backend.cacti.endpoints;

import net.getnova.backend.api.annotations.ApiEndpoint;
import net.getnova.backend.api.annotations.ApiEndpointCollection;
import net.getnova.backend.api.data.ApiResponse;
import net.getnova.backend.api.data.ApiResponseStatus;
import net.getnova.backend.cacti.reposetories.CareGroupRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@ApiEndpointCollection(id = "careGroup", description = "Handle all care groups.")
public final class CareGroupEndpointCollection {

    @Inject
    private CareGroupRepository careGroupRepository;

    @ApiEndpoint(id = "list", description = "Lists all care groups.")
    private ApiResponse list() {
        return new ApiResponse(ApiResponseStatus.OK, this.careGroupRepository.list());
    }
}
