package net.getnova.module.cacti.model;

import com.google.gson.JsonElement;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.getnova.framework.jpa.model.TableModelAutoId;
import net.getnova.framework.json.JsonBuilder;
import net.getnova.framework.json.JsonSerializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cacti_genus")
public class Genus extends TableModelAutoId implements JsonSerializable {

  @Column(name = "name", nullable = false, updatable = true, length = 128)
  private String name;

  @Override
  public JsonElement serialize() {
    return JsonBuilder.create("id", this.getId())
      .add("name", this.getName())
      .build();
  }
}
