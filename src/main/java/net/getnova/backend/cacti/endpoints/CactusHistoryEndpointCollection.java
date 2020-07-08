package net.getnova.backend.cacti.endpoints;

import net.getnova.backend.api.annotations.ApiEndpoint;
import net.getnova.backend.api.annotations.ApiEndpointCollection;
import net.getnova.backend.api.annotations.ApiParameter;
import net.getnova.backend.api.data.ApiResponse;
import net.getnova.backend.api.data.ApiResponseStatus;
import net.getnova.backend.cacti.models.Cactus;
import net.getnova.backend.cacti.models.CactusHistory;
import net.getnova.backend.cacti.reposetories.CactusHistoryRepository;
import net.getnova.backend.cacti.reposetories.CactusRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.OffsetDateTime;
import java.util.UUID;

@Singleton
@ApiEndpointCollection(id = "cactus/history", description = "Handle all cacti.")
public final class CactusHistoryEndpointCollection {

    @Inject
    private CactusRepository cactusRepository;

    @Inject
    private CactusHistoryRepository cactusHistoryRepository;

    @ApiEndpoint(id = "list", description = "Lists the history of the provided cactus.")
    private ApiResponse list(@ApiParameter(id = "cactusId", description = "The id of the cactus.") final UUID id) {

        final Cactus cactus = this.cactusRepository.find(id);
        if (cactus == null) return new ApiResponse(ApiResponseStatus.NOT_FOUND, "CACTUS");

        return new ApiResponse(ApiResponseStatus.OK, this.cactusHistoryRepository.list(cactus));
    }

    @ApiEndpoint(id = "add", description = "Add a cactus history entry.")
    private ApiResponse add(@ApiParameter(id = "cactusId", description = "The id of the cactus.") final UUID cactusId,
                            @ApiParameter(id = "timestamp", description = "The timestamp when the event is happened.") final OffsetDateTime timestamp,
                            @ApiParameter(id = "content", description = "A description of the event.") final String content) {

        final Cactus cactus = this.cactusRepository.find(cactusId);
        if (cactus == null) return new ApiResponse(ApiResponseStatus.NOT_FOUND, "CACTUS");

        return new ApiResponse(ApiResponseStatus.OK, this.cactusHistoryRepository.save(new CactusHistory(cactus, timestamp, content)));
    }

    @ApiEndpoint(id = "update", description = "Update a form.")
    private ApiResponse update(@ApiParameter(id = "id", description = "The id of the cactus history entry, witch should be updated.") final UUID id,
                               @ApiParameter(id = "timestamp", description = "The timestamp when the event is happened.") final OffsetDateTime timestamp,
                               @ApiParameter(id = "content", description = "A description of the event.") final String content) {

        final CactusHistory cactusHistory = this.cactusHistoryRepository.find(id);
        if (cactusHistory == null) return new ApiResponse(ApiResponseStatus.NOT_FOUND, "CACTUS_HISTORY");

        cactusHistory.setTimestamp(timestamp);
        cactusHistory.setContent(content);

        return new ApiResponse(ApiResponseStatus.OK, this.cactusHistoryRepository.save(cactusHistory));
    }

    @ApiEndpoint(id = "delete", description = "Delete a cactus history entry.")
    private ApiResponse delete(@ApiParameter(id = "id", description = "The id of the cactus history entry, witch should be deleted.") final UUID id) {
        return this.cactusHistoryRepository.delete(id) ? new ApiResponse(ApiResponseStatus.OK) : new ApiResponse(ApiResponseStatus.NOT_FOUND, "CACTUS_HISTORY");
    }
}
