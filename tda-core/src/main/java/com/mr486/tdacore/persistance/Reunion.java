package com.mr486.tdacore.persistance;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "reunions")
public class Reunion {
    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private Boolean estActive;
    private Integer status;
}
