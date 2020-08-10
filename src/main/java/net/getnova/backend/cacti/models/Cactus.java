package net.getnova.backend.cacti.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.getnova.backend.json.JsonBuilder;
import net.getnova.backend.json.JsonSerializable;
import net.getnova.backend.json.JsonUtils;
import net.getnova.backend.sql.model.TableModelAutoId;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Duration;
import java.time.OffsetDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cacti_cactus")
public final class Cactus extends TableModelAutoId implements JsonSerializable {

    @Column(name = "number", nullable = false, updatable = true, length = 128)
    private String number;

    @ManyToOne
    @JoinColumn(name = "genus_id", nullable = true, updatable = true)
    private Genus genus;

    @ManyToOne
    @JoinColumn(name = "specie_id", nullable = true, updatable = true)
    private Specie specie;

    @ManyToOne
    @JoinColumn(name = "form_id", nullable = true, updatable = true)
    private Form form;

    @Column(name = "field_number", nullable = true, updatable = true, length = 128)
    private String fieldNumber;

    @Column(name = "synonyms", nullable = true, updatable = true, length = 1024)
    private String synonyms;

    @Embedded
    private Acquisition acquisition;

    @Embedded
    private State state;

    @Column(name = "flower_color", nullable = true, updatable = true, length = 128)
    private String flowerColor;

    @ManyToOne
    @JoinColumn(name = "care_group_id", nullable = true, updatable = true)
    private CareGroup careGroup;

    @Column(name = "care_group_home", nullable = true, updatable = true, length = 512)
    private String careGroupHome;

    @Column(name = "care_group_soil", nullable = true, updatable = true, length = 512)
    private String careGroupSoil;

    @Column(name = "care_group_grow_time_light", nullable = true, updatable = true, length = 512)
    private String careGroupGrowTimeLight;

    @Column(name = "care_group_grow_time_air", nullable = true, updatable = true, length = 512)
    private String careGroupGrowTimeAir;

    @Column(name = "care_group_grow_time_temperature", nullable = true, updatable = true, length = 512)
    private String careGroupGrowTimeTemperature;

    @Column(name = "care_group_grow_time_humidity", nullable = true, updatable = true, length = 512)
    private String careGroupGrowTimeHumidity;

    @Column(name = "care_group_grow_time_other", nullable = true, updatable = true, length = 1024)
    private String careGroupGrowTimeOther;

    @Column(name = "care_group_rest_time_light", nullable = true, updatable = true, length = 512)
    private String careGroupRestTimeLight;

    @Column(name = "care_group_rest_time_air", nullable = true, updatable = true, length = 512)
    private String careGroupRestTimeAir;

    @Column(name = "care_group_rest_time_temperature", nullable = true, updatable = true, length = 512)
    private String careGroupRestTimeTemperature;

    @Column(name = "care_group_rest_time_humidity", nullable = true, updatable = true, length = 512)
    private String careGroupRestTimeHumidity;

    @Column(name = "care_group_rest_time_other", nullable = true, updatable = true, length = 1024)
    private String careGroupRestTimeOther;

    public Cactus(final String number) {
        this.number = number;
    }

    public JsonElement serialize(final boolean small) {
        if (!small) return this.serialize();
        else return JsonBuilder.create("id", this.getId())
                .add("number", this.getNumber())
                .add("genusId", this.getGenus() == null ? null : this.getGenus().getId())
                .add("specieId", this.getSpecie() == null ? null : this.getSpecie().getId())
                .add("formId", this.getForm() == null ? null : this.getForm().getId())
                .build();
    }

    @Override
    public JsonElement serialize() {
        final CareGroup careGroup = this.getCareGroup();
        final boolean careGroupNotExist = careGroup == null;

        return JsonBuilder.create("id", this.getId())
                .add("number", this.getNumber())

                .add("genus", this.getGenus())
                .add("specie", this.getSpecie())
                .add("form", this.getForm())

                .add("fieldNumber", this.getFieldNumber())
                .add("synonyms", this.getSynonyms())

                .add("acquisition", this.getAcquisition())

                .add("state", this.getState() == null, () -> JsonBuilder.create("age", this.getAge()),
                        () -> JsonBuilder.create(JsonUtils.fromJson(JsonUtils.toJson(this.getState()), JsonObject.class))
                                .add("age", this.getAge()))

                .add("flowerColor", this.getFlowerColor())

                .add("careGroup", JsonBuilder.create("id", careGroupNotExist, () -> null, () -> careGroup.getId())
                        .add("name", careGroupNotExist, () -> null, () -> careGroup.getName())
                        .add("home", careGroupNotExist, () -> null, () -> careGroup.getHome())
                        .add("soil", careGroupNotExist, () -> null, () -> careGroup.getSoil())

                        .add("growTime", JsonBuilder.create("light", careGroupNotExist
                                || careGroup.getGrowTimeLight() == null, this::getCareGroupGrowTimeLight, () -> careGroup.getGrowTimeLight())
                                .add("air", careGroupNotExist
                                        || careGroup.getGrowTimeAir() == null, this::getCareGroupGrowTimeAir, () -> careGroup.getGrowTimeAir())
                                .add("temperature", careGroupNotExist
                                        || careGroup.getGrowTimeTemperature() == null, this::getCareGroupGrowTimeTemperature, () -> careGroup.getGrowTimeTemperature())
                                .add("humidity", careGroupNotExist
                                        || careGroup.getGrowTimeHumidity() == null, this::getCareGroupGrowTimeHumidity, () -> careGroup.getGrowTimeHumidity())
                                .add("other", careGroupNotExist
                                        || careGroup.getGrowTimeOther() == null, this::getCareGroupGrowTimeOther, () -> careGroup.getGrowTimeOther()))

                        .add("restTime", JsonBuilder.create("light", careGroupNotExist
                                || careGroup.getRestTimeLight() == null, this::getCareGroupRestTimeLight, () -> careGroup.getRestTimeLight())
                                .add("air", careGroupNotExist
                                        || careGroup.getRestTimeAir() == null, this::getCareGroupRestTimeAir, () -> careGroup.getRestTimeAir())
                                .add("temperature", careGroupNotExist
                                        || careGroup.getRestTimeTemperature() == null, this::getCareGroupRestTimeTemperature, () -> careGroup.getRestTimeTemperature())
                                .add("humidity", careGroupNotExist
                                        || careGroup.getRestTimeHumidity() == null, this::getCareGroupRestTimeHumidity, () -> careGroup.getRestTimeHumidity())
                                .add("other", careGroupNotExist
                                        || careGroup.getRestTimeOther() == null, this::getCareGroupRestTimeOther, () -> careGroup.getRestTimeOther())))

                .build();
    }

    public Duration getAge() {
        if (this.getAcquisition() != null && this.getAcquisition().getTimestamp() != null && this.getAcquisition().getAge() != null)
            return Duration.between(
                    this.getAcquisition().getTimestamp().minus(this.getAcquisition().getAge()),
                    this.getState() == null || this.getState().getNoLongerInPossessionTimestamp() == null
                            ? OffsetDateTime.now()
                            : this.getState().getNoLongerInPossessionTimestamp()
            );
        return null;
    }

    @Data
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Acquisition {

        @Column(name = "timestamp", nullable = true, updatable = true)
        private OffsetDateTime timestamp;

        @Column(name = "age", nullable = true, updatable = true)
        private Duration age;

        @Column(name = "place", nullable = true, updatable = true, length = 512)
        private String place;

        @Column(name = "plant_type", nullable = true, updatable = true, length = 512)
        private String plantType;
    }

    @Data
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class State {

        @Column(name = "no_longer_in_possession_timestamp", nullable = true, updatable = true)
        private OffsetDateTime noLongerInPossessionTimestamp;

        @Column(name = "no_longer_in_possession_reason", nullable = true, updatable = true)
        private String noLongerInPossessionReason;

        @Column(name = "vitality", nullable = true, updatable = true, length = 128)
        private String vitality;
    }
}
