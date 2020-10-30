package net.getnova.backend.module.cacti.endpoints;

import io.netty.handler.codec.http.HttpResponseStatus;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.getnova.backend.api.annotations.ApiEndpoint;
import net.getnova.backend.api.annotations.ApiEndpointCollection;
import net.getnova.backend.api.annotations.ApiParameter;
import net.getnova.backend.api.data.ApiResponse;
import net.getnova.backend.api.data.ApiType;
import net.getnova.backend.module.cacti.models.Cactus;
import net.getnova.backend.module.cacti.models.CactusHistory;
import net.getnova.backend.module.cacti.repositories.CactusHistoryRepository;
import net.getnova.backend.module.cacti.repositories.CactusRepository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@ApiEndpointCollection(id = "cactus/history", description = "Handle all cacti.", type = ApiType.REST)
public class CactusHistoryEndpointCollection {

  private final CactusRepository cactusRepository;
  private final CactusHistoryRepository cactusHistoryRepository;

  @ApiEndpoint(id = "list", description = "Lists the history of the provided cactus.")
  protected ApiResponse list(@ApiParameter(id = "cactusId", description = "The id of the cactus.") final UUID id) {

    final Optional<Cactus> cactus = this.cactusRepository.findById(id);
    if (cactus.isEmpty()) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "CACTUS");
    return new ApiResponse(HttpResponseStatus.OK, this.cactusHistoryRepository.findAllByCactusOrderByTimestamp(cactus.get()));
  }

  @Transactional
  @ApiEndpoint(id = "add", description = "Add a cactus history entry.")
  protected ApiResponse add(@ApiParameter(id = "cactusId", description = "The id of the cactus.") final UUID cactusId,
                            @ApiParameter(id = "timestamp", description = "The timestamp when the event is happened.") final OffsetDateTime timestamp,
                            @ApiParameter(id = "content", description = "A description of the event.") final String content) {

    final Optional<Cactus> cactus = this.cactusRepository.findById(cactusId);
    if (cactus.isEmpty()) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "CACTUS");
    return new ApiResponse(HttpResponseStatus.OK, this.cactusHistoryRepository.save(new CactusHistory(cactus.get(), timestamp, content)));
  }

  @Transactional
  @ApiEndpoint(id = "update", description = "Update a form.")
  protected ApiResponse update(@ApiParameter(id = "id", description = "The id of the cactus history entry, witch should be updated.") final UUID id,
                               @ApiParameter(id = "timestamp", description = "The timestamp when the event is happened.") final OffsetDateTime timestamp,
                               @ApiParameter(id = "content", description = "A description of the event.") final String content) {

    final CactusHistory cactusHistory = this.cactusHistoryRepository.findById(id).orElse(null);
    if (cactusHistory == null) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "CACTUS_HISTORY");

    cactusHistory.setTimestamp(timestamp);
    cactusHistory.setContent(content);

    return new ApiResponse(HttpResponseStatus.OK, this.cactusHistoryRepository.save(cactusHistory));
  }

  @Transactional
  @ApiEndpoint(id = "delete", description = "Delete a cactus history entry.")
  protected ApiResponse delete(@ApiParameter(id = "id", description = "The id of the cactus history entry, witch should be deleted.") final UUID id) {
    final Optional<CactusHistory> cactusHistory = this.cactusHistoryRepository.findById(id);
    if (cactusHistory.isEmpty()) {
      return new ApiResponse(HttpResponseStatus.NOT_FOUND, "CACTUS_HISTORY");
    }

    this.cactusHistoryRepository.delete(cactusHistory.get());
    return new ApiResponse(HttpResponseStatus.OK);
  }
}
