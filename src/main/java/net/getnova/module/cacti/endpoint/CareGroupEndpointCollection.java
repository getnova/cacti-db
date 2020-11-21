package net.getnova.module.cacti.endpoint;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.RequiredArgsConstructor;
import net.getnova.framework.api.annotations.ApiEndpoint;
import net.getnova.framework.api.annotations.ApiEndpointCollection;
import net.getnova.framework.api.data.ApiResponse;
import net.getnova.framework.api.data.ApiType;
import net.getnova.module.cacti.repository.CareGroupRepository;

@RequiredArgsConstructor
@ApiEndpointCollection(id = "careGroup", description = "Handle all care groups.", type = ApiType.REST)
public class CareGroupEndpointCollection {

  private final CareGroupRepository careGroupRepository;

  @ApiEndpoint(id = "list", description = "Lists all care groups.")
  public ApiResponse list() {
    return new ApiResponse(HttpResponseStatus.OK, this.careGroupRepository.findAllByOrderById());
  }
}
