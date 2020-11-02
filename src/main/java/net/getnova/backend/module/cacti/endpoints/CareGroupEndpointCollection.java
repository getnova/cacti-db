package net.getnova.backend.module.cacti.endpoints;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.RequiredArgsConstructor;
import net.getnova.backend.api.annotations.ApiEndpoint;
import net.getnova.backend.api.annotations.ApiEndpointCollection;
import net.getnova.backend.api.data.ApiResponse;
import net.getnova.backend.api.data.ApiType;
import net.getnova.backend.module.cacti.repositories.CareGroupRepository;

@RequiredArgsConstructor
@ApiEndpointCollection(id = "careGroup", description = "Handle all care groups.", type = ApiType.REST)
public class CareGroupEndpointCollection {

  private final CareGroupRepository careGroupRepository;

  @ApiEndpoint(id = "list", description = "Lists all care groups.")
  public ApiResponse list() {
    return new ApiResponse(HttpResponseStatus.OK, this.careGroupRepository.findAllByOrderById());
  }
}
