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
@Table(name = "joueurs")
public class Joueur {

    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private UUID reunionUuid;
    private Integer amiId;
}
