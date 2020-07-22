package net.getnova.backend.cacti.endpoints;

import net.getnova.backend.api.annotations.ApiEndpoint;
import net.getnova.backend.api.annotations.ApiEndpointCollection;
import net.getnova.backend.api.annotations.ApiParameter;
import net.getnova.backend.api.data.ApiResponse;
import net.getnova.backend.api.data.ApiResponseStatus;
import net.getnova.backend.cacti.CactiException;
import net.getnova.backend.cacti.models.Cactus;
import net.getnova.backend.cacti.models.CactusAcquisition;
import net.getnova.backend.cacti.models.CactusState;
import net.getnova.backend.cacti.models.CareGroup;
import net.getnova.backend.cacti.models.Form;
import net.getnova.backend.cacti.models.Genus;
import net.getnova.backend.cacti.models.Specie;
import net.getnova.backend.cacti.reposetories.CactusAcquisitionRepository;
import net.getnova.backend.cacti.reposetories.CactusRepository;
import net.getnova.backend.cacti.reposetories.CactusStateRepository;
import net.getnova.backend.cacti.reposetories.CareGroupRepository;
import net.getnova.backend.cacti.reposetories.FormRepository;
import net.getnova.backend.cacti.reposetories.GenusRepository;
import net.getnova.backend.cacti.reposetories.SpecieRepository;
import net.getnova.backend.json.JsonUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

@Singleton
@ApiEndpointCollection(id = "cactus", description = "Handle all cacti.")
public final class CactusEndpointCollection {

    @Inject
    private GenusRepository genusRepository;

    @Inject
    private SpecieRepository specieRepository;

    @Inject
    private FormRepository formRepository;

    @Inject
    private CareGroupRepository careGroupRepository;

    @Inject
    private CactusRepository cactusRepository;

    @Inject
    private CactusAcquisitionRepository cactusAcquisitionRepository;

    @Inject
    private CactusStateRepository cactusStateRepository;

    @ApiEndpoint(id = "list", description = "Lists all cacti.")
    private ApiResponse list() {
        return new ApiResponse(ApiResponseStatus.OK, JsonUtils.toArray(this.cactusRepository.list(), cactus -> cactus.serialize(true)));
    }

    @ApiEndpoint(id = "get", description = "Return the cactus with the specified id.")
    private ApiResponse get(@ApiParameter(id = "id", description = "The id of the cactus.") final UUID id) {
        final Cactus cactus = this.cactusRepository.find(id);
        if (cactus == null) return new ApiResponse(ApiResponseStatus.NOT_FOUND, "CACTUS");
        return new ApiResponse(ApiResponseStatus.OK, cactus);
    }

    @ApiEndpoint(id = "add", description = "Add a cactus.")
    private ApiResponse add(@ApiParameter(id = "number", description = "The number of the cactus.") final String number,
                            @ApiParameter(id = "subId", description = "The id of the sub type.", required = false) final UUID subId,
                            @ApiParameter(id = "subType", description = "The type of the sub type.", required = false) final String subType) {

        final Cactus cactus = new Cactus(number);

        try {
            this.setSubType(cactus, subId, subType);
        } catch (CactiException e) {
            return new ApiResponse(ApiResponseStatus.NOT_FOUND, e.getMessage());
        }

        return new ApiResponse(ApiResponseStatus.OK, this.cactusRepository.save(cactus));
    }

    @ApiEndpoint(id = "updateNumber", description = "Updates only the number of a cactus.")
    private ApiResponse updateNumber(@ApiParameter(id = "id", description = "The id of the existing cactus.") final UUID id,
                                     @ApiParameter(id = "number", description = "The new/old number of the cactus.") final String number) {

        final Cactus cactus = this.cactusRepository.find(id);
        if (cactus == null) return new ApiResponse(ApiResponseStatus.NOT_FOUND, "CACTUS");

        cactus.setNumber(number);
        return new ApiResponse(ApiResponseStatus.OK, this.cactusRepository.update(cactus));
    }

    @ApiEndpoint(id = "update", description = "Update a cactus.")
    private ApiResponse update(@ApiParameter(id = "id", description = "The id of the existing cactus.") final UUID id,
                               @ApiParameter(id = "number", description = "The new/old number of the cactus.") final String number,

                               @ApiParameter(id = "subId", description = "The id of the sub type.", required = false) final UUID subId,
                               @ApiParameter(id = "subType", description = "The type of the sub type.", required = false) final String subType,

                               @ApiParameter(id = "fieldNumber", description = "todo", required = false) final String fieldNumber,
                               @ApiParameter(id = "synonyms", description = "todo", required = false) final String synonyms,

                               @ApiParameter(id = "acquisitionTimestamp", description = "todo", required = false) final OffsetDateTime acquisitionTimestamp,
                               @ApiParameter(id = "acquisitionPlantAge", description = "todo", required = false) final Long acquisitionPlantAge,
                               @ApiParameter(id = "acquisitionPlace", description = "todo", required = false) final String acquisitionPlace,
                               @ApiParameter(id = "acquisitionPlantType", description = "todo", required = false) final String acquisitionPlantType,

                               @ApiParameter(id = "stateNoLongerInPossessionTimestamp", description = "todo", required = false) final OffsetDateTime stateNoLongerInPossessionTimestamp,
                               @ApiParameter(id = "stateNoLongerInPossessionReason", description = "todo", required = false) final String stateNoLongerInPossessionReason,
                               @ApiParameter(id = "stateVitality", description = "todo", required = false) final String stateVitality,

                               @ApiParameter(id = "flowerColor", description = "todo", required = false) final String flowerColor,

                               @ApiParameter(id = "careGroupId", description = "todo", required = false) final String careGroupId,
                               @ApiParameter(id = "careGroupHome", description = "todo", required = false) final String careGroupHome,
                               @ApiParameter(id = "careGroupSoil", description = "todo", required = false) final String careGroupSoil,

                               @ApiParameter(id = "careGroupGrowTimeLight", description = "todo", required = false) final String careGroupGrowTimeLight,
                               @ApiParameter(id = "careGroupGrowTimeAir", description = "todo", required = false) final String careGroupGrowTimeAir,
                               @ApiParameter(id = "careGroupGrowTimeTemperature", description = "todo", required = false) final String careGroupGrowTimeTemperature,
                               @ApiParameter(id = "careGroupGrowTimeHumidity", description = "todo", required = false) final String careGroupGrowTimeHumidity,
                               @ApiParameter(id = "careGroupGrowTimeOther", description = "todo", required = false) final String careGroupGrowTimeOther,

                               @ApiParameter(id = "careGroupRestTimeLight", description = "todo", required = false) final String careGroupRestTimeLight,
                               @ApiParameter(id = "careGroupRestTimeAir", description = "todo", required = false) final String careGroupRestTimeAir,
                               @ApiParameter(id = "careGroupRestTimeTemperature", description = "todo", required = false) final String careGroupRestTimeTemperature,
                               @ApiParameter(id = "careGroupRestTimeHumidity", description = "todo", required = false) String careGroupRestTimeHumidity,
                               @ApiParameter(id = "careGroupRestTimeOther", description = "todo", required = false) final String careGroupRestTimeOther) {

        final Cactus cactus = this.cactusRepository.find(id);
        if (cactus == null) return new ApiResponse(ApiResponseStatus.NOT_FOUND, "CACTUS");

        cactus.setNumber(number);

        try {
            this.setSubType(cactus, subId, subType);
        } catch (CactiException e) {
            return new ApiResponse(ApiResponseStatus.NOT_FOUND, e.getMessage());
        }

        cactus.setFieldNumber(fieldNumber);
        cactus.setSynonyms(synonyms);

        /* --> acquisition */
        if (acquisitionTimestamp == null
                && acquisitionPlantAge == null
                && acquisitionPlace == null
                && acquisitionPlantType == null) {
            if (cactus.getAcquisition() != null)
                this.cactusAcquisitionRepository.delete(cactus.getAcquisition().getId());
            cactus.setAcquisition(null);
        } else {

            CactusAcquisition acquisition = cactus.getAcquisition();
            if (acquisition == null) {
                acquisition = new CactusAcquisition();
                cactus.setAcquisition(acquisition);
                acquisition.setCactus(cactus);
            }

            acquisition.setTimestamp(acquisitionTimestamp);
            acquisition.setAge(acquisitionPlantAge == null ? null : Duration.of(acquisitionPlantAge * 30, ChronoUnit.DAYS));
            acquisition.setPlace(acquisitionPlace);
            acquisition.setPlantType(acquisitionPlantType);

            this.cactusAcquisitionRepository.saveOrUpdate(acquisition);
        }
        /* <-- acquisition */

        /* --> state */
        if (stateNoLongerInPossessionTimestamp == null
                && stateNoLongerInPossessionReason == null
                && stateVitality == null) {
            if (cactus.getState() != null) this.cactusStateRepository.delete(cactus.getState().getId());
            cactus.setState(null);
        } else {
            CactusState state = cactus.getState();
            if (state == null) {
                state = new CactusState();
                cactus.setState(state);
                state.setCactus(cactus);
            }

            state.setNoLongerInPossessionTimestamp(stateNoLongerInPossessionTimestamp);
            state.setNoLongerInPossessionReason(stateNoLongerInPossessionReason);
            state.setVitality(stateVitality);

            this.cactusStateRepository.saveOrUpdate(state);
        }
        /* <-- state */

        cactus.setFlowerColor(flowerColor);

        /* --> care group */
        CareGroup careGroup = null;
        if (careGroupId != null) {
            careGroup = this.careGroupRepository.find(careGroupId);
            if (careGroup == null) return new ApiResponse(ApiResponseStatus.NOT_FOUND, "CARE_GROUP");
            cactus.setCareGroup(careGroup);
        }

        final boolean careGroupExist = careGroup != null;

        cactus.setCareGroupHome(careGroupExist && Objects.equals(careGroup.getHome(), careGroupHome) ? null : careGroupHome);
        cactus.setCareGroupSoil(careGroupExist && Objects.equals(careGroup.getSoil(), careGroupSoil) ? null : careGroupSoil);

        /* --> grow time */
        cactus.setCareGroupHome(careGroupExist && Objects.equals(careGroup.getGrowTimeLight(), careGroupGrowTimeLight) ? null : careGroupGrowTimeLight);
        cactus.setCareGroupHome(careGroupExist && Objects.equals(careGroup.getGrowTimeAir(), careGroupGrowTimeAir) ? null : careGroupGrowTimeAir);
        cactus.setCareGroupHome(careGroupExist && Objects.equals(careGroup.getGrowTimeTemperature(), careGroupGrowTimeTemperature) ? null : careGroupGrowTimeTemperature);
        cactus.setCareGroupHome(careGroupExist && Objects.equals(careGroup.getGrowTimeHumidity(), careGroupGrowTimeHumidity) ? null : careGroupGrowTimeHumidity);
        cactus.setCareGroupHome(careGroupExist && Objects.equals(careGroup.getGrowTimeOther(), careGroupGrowTimeOther) ? null : careGroupGrowTimeOther);
        /* <-- grow time */

        /* --> rest time */
        cactus.setCareGroupHome(careGroupExist && Objects.equals(careGroup.getRestTimeLight(), careGroupRestTimeLight) ? null : careGroupRestTimeLight);
        cactus.setCareGroupHome(careGroupExist && Objects.equals(careGroup.getRestTimeAir(), careGroupRestTimeAir) ? null : careGroupRestTimeAir);
        cactus.setCareGroupHome(careGroupExist && Objects.equals(careGroup.getRestTimeTemperature(), careGroupRestTimeTemperature) ? null : careGroupRestTimeTemperature);
        cactus.setCareGroupHome(careGroupExist && Objects.equals(careGroup.getRestTimeHumidity(), careGroupRestTimeHumidity) ? null : careGroupRestTimeHumidity);
        cactus.setCareGroupHome(careGroupExist && Objects.equals(careGroup.getRestTimeOther(), careGroupRestTimeOther) ? null : careGroupRestTimeOther);
        /* <-- rest time */

        /* <-- care group */

        return new ApiResponse(ApiResponseStatus.OK, this.cactusRepository.update(cactus));
    }

    @ApiEndpoint(id = "delete", description = "Delete a cactus.")
    private ApiResponse delete(@ApiParameter(id = "id", description = "The id of the cactus, witch should be deleted.") final UUID id) {
        return this.cactusRepository.delete(id) ? new ApiResponse(ApiResponseStatus.OK) : new ApiResponse(ApiResponseStatus.NOT_FOUND, "CACTUS");
    }

    private void setSubType(final Cactus cactus, final UUID subId, final String subType) throws CactiException {
        if (subId == null || subType == null) {
            cactus.setForm(null);
            cactus.setSpecie(null);
            cactus.setGenus(null);
        } else switch (subType) {
            case "GENUS" -> {
                final Genus genus = this.genusRepository.find(subId);
                if (genus == null) throw new CactiException("GENUS");
                cactus.setForm(null);
                cactus.setSpecie(null);
                cactus.setGenus(genus);
            }
            case "SPECIE" -> {
                final Specie specie = this.specieRepository.find(subId);
                if (specie == null) throw new CactiException("SPECIE");
                cactus.setForm(null);
                cactus.setSpecie(specie);
                cactus.setGenus(specie.getGenus());
            }
            case "FORM" -> {
                final Form form = this.formRepository.find(subId);
                if (form == null) throw new CactiException("FORM");
                cactus.setForm(form);
                cactus.setSpecie(form.getSpecie());
                cactus.setGenus(form.getSpecie().getGenus());
            }
            default -> throw new CactiException("SUBTYPE");
        }
    }
}
