package net.getnova.backend.module.cacti.endpoints;

import io.netty.handler.codec.http.HttpResponseStatus;
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
import java.util.UUID;

@RequiredArgsConstructor
@ApiEndpointCollection(id = "cactus/history", description = "Handle all cacti.", type = ApiType.WEBSOCKET)
public final class CactusHistoryEndpointCollection {

  private final CactusRepository cactusRepository;
  private final CactusHistoryRepository cactusHistoryRepository;

  @ApiEndpoint(id = "list", description = "Lists the history of the provided cactus.")
  private ApiResponse list(@ApiParameter(id = "cactusId", description = "The id of the cactus.") final UUID id) {

    final Cactus cactus = this.cactusRepository.findById(id).orElse(null);
    if (cactus == null) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "CACTUS");

    return new ApiResponse(HttpResponseStatus.OK, this.cactusHistoryRepository.findByCactusOrderByTimestamp(cactus));
  }

  @ApiEndpoint(id = "add", description = "Add a cactus history entry.")
  private ApiResponse add(@ApiParameter(id = "cactusId", description = "The id of the cactus.") final UUID cactusId,
                          @ApiParameter(id = "timestamp", description = "The timestamp when the event is happened.") final OffsetDateTime timestamp,
                          @ApiParameter(id = "content", description = "A description of the event.") final String content) {

    final Cactus cactus = this.cactusRepository.findById(cactusId).orElse(null);
    if (cactus == null) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "CACTUS");

    return new ApiResponse(HttpResponseStatus.OK, this.cactusHistoryRepository.save(new CactusHistory(cactus, timestamp, content)));
  }

  @ApiEndpoint(id = "update", description = "Update a form.")
  private ApiResponse update(@ApiParameter(id = "id", description = "The id of the cactus history entry, witch should be updated.") final UUID id,
                             @ApiParameter(id = "timestamp", description = "The timestamp when the event is happened.") final OffsetDateTime timestamp,
                             @ApiParameter(id = "content", description = "A description of the event.") final String content) {

    final CactusHistory cactusHistory = this.cactusHistoryRepository.findById(id).orElse(null);
    if (cactusHistory == null) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "CACTUS_HISTORY");

    cactusHistory.setTimestamp(timestamp);
    cactusHistory.setContent(content);

    return new ApiResponse(HttpResponseStatus.OK, this.cactusHistoryRepository.save(cactusHistory));
  }

  @ApiEndpoint(id = "delete", description = "Delete a cactus history entry.")
  private ApiResponse delete(@ApiParameter(id = "id", description = "The id of the cactus history entry, witch should be deleted.") final UUID id) {
    final CactusHistory cactusHistory = this.cactusHistoryRepository.findById(id).orElse(null);
    if (cactusHistory != null) {
      this.cactusHistoryRepository.delete(cactusHistory);
      return new ApiResponse(HttpResponseStatus.OK);
    } else {
      return new ApiResponse(HttpResponseStatus.NOT_FOUND, "CACTUS_HISTORY");
    }
  }
}
