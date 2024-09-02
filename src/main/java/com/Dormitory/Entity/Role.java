package com.Dormitory.Entity;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.ManyToMany;
import lombok.experimental.FieldDefaults;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Role {

      @Id
      String name;
      String description;

      @ManyToMany
      Set<Permission> permissions;



}
