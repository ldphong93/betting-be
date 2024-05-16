package com.betting.backend.model.entity;

import com.betting.backend.Utils.ListToStringConverter;
import com.betting.backend.Utils.MatchResult;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "bet_detail")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BetDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "match_id")
    private Integer matchId;

    @Column(name = "bet_info")
    @Convert(converter = ListToStringConverter.class)
    private List<String> betInfo;

    @Column(name = "result")
    @Enumerated(EnumType.STRING)
    private MatchResult result;

    @Column(name = "is_finalized")
    private boolean isFinalized;
}
