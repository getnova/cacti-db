package net.getnova.backend.cacti.endpoints;

import net.getnova.backend.api.annotations.ApiEndpoint;
import net.getnova.backend.api.annotations.ApiEndpointCollection;
import net.getnova.backend.api.annotations.ApiParameter;
import net.getnova.backend.api.data.ApiResponse;
import net.getnova.backend.api.data.ApiResponseStatus;
import net.getnova.backend.cacti.CactiException;
import net.getnova.backend.cacti.models.Cactus;
import net.getnova.backend.cacti.models.CareGroup;
import net.getnova.backend.cacti.models.Form;
import net.getnova.backend.cacti.models.Genus;
import net.getnova.backend.cacti.models.Specie;
import net.getnova.backend.cacti.reposetories.CactusRepository;
import net.getnova.backend.cacti.reposetories.CareGroupRepository;
import net.getnova.backend.cacti.reposetories.FormRepository;
import net.getnova.backend.cacti.reposetories.GenusRepository;
import net.getnova.backend.cacti.reposetories.SpecieRepository;
import net.getnova.backend.json.JsonUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Duration;
import java.time.OffsetDateTime;
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
                            @ApiParameter(id = "subId", description = "The id of the sub type.") final UUID subId,
                            @ApiParameter(id = "subType", description = "The type of the sub type.") final String subType) {

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
        return new ApiResponse(ApiResponseStatus.OK, cactus);
    }

    @ApiEndpoint(id = "update", description = "Update a cactus.")
    private ApiResponse update(@ApiParameter(id = "id", description = "The id of the existing cactus.") final UUID id,
                               @ApiParameter(id = "number", description = "The new/old number of the cactus.") final String number,

                               @ApiParameter(id = "subId", description = "The id of the sub type.") final UUID subId,
                               @ApiParameter(id = "subType", description = "The type of the sub type.") final String subType,

                               @ApiParameter(id = "fieldNumber", description = "todo") final String fieldNumber,
                               @ApiParameter(id = "synonyms", description = "todo") final String synonyms,

                               @ApiParameter(id = "acquisitionTimestamp", description = "todo") final OffsetDateTime acquisitionTimestamp,
                               @ApiParameter(id = "acquisitionAge", description = "todo") final Duration acquisitionAge,
                               @ApiParameter(id = "acquisitionPlace", description = "todo") final String acquisitionPlace,
                               @ApiParameter(id = "acquisitionPlantType", description = "todo") final String acquisitionPlantType,

                               @ApiParameter(id = "stateNoLongerInPossessionTimestamp", description = "todo") final OffsetDateTime stateNoLongerInPossessionTimestamp,
                               @ApiParameter(id = "stateNoLongerInPossessionReason", description = "todo") final String stateNoLongerInPossessionReason,
                               @ApiParameter(id = "stateVitality", description = "todo") final String stateVitality,

                               @ApiParameter(id = "flowerColor", description = "todo") final String flowerColor,

                               @ApiParameter(id = "careGroupId", description = "todo") final String careGroupId,
                               @ApiParameter(id = "careGroupHome", description = "todo") final String careGroupHome,
                               @ApiParameter(id = "careGroupSoil", description = "todo") final String careGroupSoil,

                               @ApiParameter(id = "careGroupGrowTimeLight", description = "todo") final String careGroupGrowTimeLight,
                               @ApiParameter(id = "careGroupGrowTimeAir", description = "todo") final String careGroupGrowTimeAir,
                               @ApiParameter(id = "careGroupGrowTimeTemperature", description = "todo") final String careGroupGrowTimeTemperature,
                               @ApiParameter(id = "careGroupGrowTimeHumidity", description = "todo") final String careGroupGrowTimeHumidity,
                               @ApiParameter(id = "careGroupGrowTimeOther", description = "todo") final String careGroupGrowTimeOther,

                               @ApiParameter(id = "careGroupRestTimeLight", description = "todo") final String careGroupRestTimeLight,
                               @ApiParameter(id = "careGroupRestTimeAir", description = "todo") final String careGroupRestTimeAir,
                               @ApiParameter(id = "careGroupRestTimeTemperature", description = "todo") final String careGroupRestTimeTemperature,
                               @ApiParameter(id = "careGroupRestTimeHumidity", description = "todo") final String careGroupRestTimeHumidity,
                               @ApiParameter(id = "careGroupRestTimeOther", description = "todo") final String careGroupRestTimeOther) {

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
        cactus.setAcquisitionTimestamp(acquisitionTimestamp);
        cactus.setAcquisitionAge(acquisitionAge);
        cactus.setAcquisitionPlace(acquisitionPlace);
        cactus.setAcquisitionPlantType(acquisitionPlantType);
        /* <-- acquisition */

        /* --> state */
        cactus.setStateNoLongerInPossessionTimestamp(stateNoLongerInPossessionTimestamp);
        cactus.setStateNoLongerInPossessionReason(stateNoLongerInPossessionReason);
        cactus.setStateVitality(stateVitality);
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

        cactus.setCareGroupHome(careGroupExist && careGroup.getHome().equals(careGroupHome) ? null : careGroupHome);
        cactus.setCareGroupSoil(careGroupExist && careGroup.getSoil().equals(careGroupSoil) ? null : careGroupSoil);

        /* --> grow time */
        cactus.setCareGroupHome(careGroupExist && careGroup.getGrowTimeLight().equals(careGroupGrowTimeLight) ? null : careGroupGrowTimeLight);
        cactus.setCareGroupHome(careGroupExist && careGroup.getGrowTimeAir().equals(careGroupGrowTimeAir) ? null : careGroupGrowTimeAir);
        cactus.setCareGroupHome(careGroupExist && careGroup.getGrowTimeTemperature().equals(careGroupGrowTimeTemperature) ? null : careGroupGrowTimeTemperature);
        cactus.setCareGroupHome(careGroupExist && careGroup.getGrowTimeHumidity().equals(careGroupGrowTimeHumidity) ? null : careGroupGrowTimeHumidity);
        cactus.setCareGroupHome(careGroupExist && careGroup.getGrowTimeOther().equals(careGroupGrowTimeOther) ? null : careGroupGrowTimeOther);
        /* <-- grow time */

        /* --> rest time */
        cactus.setCareGroupHome(careGroupExist && careGroup.getRestTimeLight().equals(careGroupRestTimeLight) ? null : careGroupRestTimeLight);
        cactus.setCareGroupHome(careGroupExist && careGroup.getRestTimeAir().equals(careGroupRestTimeAir) ? null : careGroupRestTimeAir);
        cactus.setCareGroupHome(careGroupExist && careGroup.getRestTimeTemperature().equals(careGroupRestTimeTemperature) ? null : careGroupRestTimeTemperature);
        cactus.setCareGroupHome(careGroupExist && careGroup.getRestTimeHumidity().equals(careGroupRestTimeHumidity) ? null : careGroupRestTimeHumidity);
        cactus.setCareGroupHome(careGroupExist && careGroup.getRestTimeOther().equals(careGroupRestTimeOther) ? null : careGroupRestTimeOther);
        /* <-- rest time */

        /* <-- care group */

        return new ApiResponse(ApiResponseStatus.OK, this.cactusRepository.save(cactus));
    }

    @ApiEndpoint(id = "delete", description = "Delete a cactus.")
    private ApiResponse delete(@ApiParameter(id = "id", description = "The id of the cactus, witch should be deleted.") final UUID id) {
        return this.cactusRepository.delete(id) ? new ApiResponse(ApiResponseStatus.OK) : new ApiResponse(ApiResponseStatus.NOT_FOUND, "CACTUS");
    }

    private void setSubType(final Cactus cactus, final UUID subId, final String subType) throws CactiException {
        switch (subType) {
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
