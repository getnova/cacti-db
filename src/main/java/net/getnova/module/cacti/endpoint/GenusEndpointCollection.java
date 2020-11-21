package net.getnova.module.cacti.endpoint;

import io.netty.handler.codec.http.HttpResponseStatus;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.getnova.framework.api.annotations.ApiEndpoint;
import net.getnova.framework.api.annotations.ApiEndpointCollection;
import net.getnova.framework.api.annotations.ApiParameter;
import net.getnova.framework.api.data.ApiResponse;
import net.getnova.framework.api.data.ApiType;
import net.getnova.framework.api.handler.websocket.WebsocketApiModule;
import net.getnova.framework.api.handler.websocket.WebsocketApiNotification;
import net.getnova.module.cacti.model.Genus;
import net.getnova.module.cacti.repository.GenusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import reactor.core.publisher.Flux;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@ApiEndpointCollection(id = "genus", description = "Handle all genres.", type = ApiType.ALL)
public class GenusEndpointCollection {

  @Lazy
  @Autowired
  private WebsocketApiModule websocketApiModule;
  private final GenusRepository genusRepository;

  @ApiEndpoint(id = "list", description = "Lists all genres.")
  public ApiResponse list() {
    return new ApiResponse(HttpResponseStatus.OK, this.genusRepository.findAllByOrderByName());
  }

  @Transactional
  @ApiEndpoint(id = "add", description = "Add a genus.")
  public ApiResponse add(@ApiParameter(id = "name", description = "The name of the genus.") final String name) {
    return new ApiResponse(HttpResponseStatus.OK, this.genusRepository.save(new Genus(name)));
  }

  @Transactional
  @ApiEndpoint(id = "update", description = "Update a genus.")
  public ApiResponse update(@ApiParameter(id = "id", description = "The id of the existing genus.") final UUID id,
                            @ApiParameter(id = "name", description = "The new name of the genus.") final String name) {
    final Genus genus = this.genusRepository.findById(id).orElse(null);
    if (genus == null) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "GENUS");

    genus.setName(name);
    return new ApiResponse(HttpResponseStatus.OK, this.genusRepository.save(genus));
  }

  @Transactional
  @ApiEndpoint(id = "delete", description = "Delete a genus.")
  public ApiResponse delete(@ApiParameter(id = "id", description = "The id of the genus, witch should be deleted.") final UUID id) {
    final Optional<Genus> genus = this.genusRepository.findById(id);
    if (genus.isEmpty()) {
      return new ApiResponse(HttpResponseStatus.NOT_FOUND, "FORM");
    }

    this.genusRepository.delete(genus.get());
    return new ApiResponse(HttpResponseStatus.OK);
  }

  @ApiEndpoint(id = "hello", description = "")
  public ApiResponse hello() {
    Flux.fromIterable(websocketApiModule.getContexts())
      .flatMap(context -> new WebsocketApiNotification("topic", "data").send(context.getOutbound()))
      .subscribe();
    return new ApiResponse(HttpResponseStatus.OK, "hello");
  }
}
