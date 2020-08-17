package net.getnova.backend.cacti.models;

import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.getnova.backend.json.JsonBuilder;
import net.getnova.backend.json.JsonSerializable;
import net.getnova.backend.sql.model.TableModelAutoId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cacti_specie")
public class Specie extends TableModelAutoId implements JsonSerializable {

  @Column(name = "name", nullable = false, updatable = true, length = 128)
  private String name;

  @ManyToOne
  @JoinColumn(name = "genus_id", nullable = false, updatable = false)
  private Genus genus;

  @Override
  public final JsonElement serialize() {
    return JsonBuilder.create("id", this.getId())
      .add("name", this.getName())
      .add("genusId", this.getGenus().getId())
      .build();
  }
}
