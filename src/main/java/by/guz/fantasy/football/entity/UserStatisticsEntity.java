package by.guz.fantasy.football.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_statistics")
@EqualsAndHashCode(callSuper = false, of = "id")
public class UserStatisticsEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    private Long userId;

    @Column(name = "rank")
    private Long rank;

    @Column(name = "prev_rank")
    private Long prevRank;

    @Column(name = "pts", columnDefinition = "NUMERIC(20,2)")
    private BigDecimal pts;

    @Column(name = "prev_pts", columnDefinition = "NUMERIC(20,2)")
    private BigDecimal prevPts;

    @Column(name = "rounds_participated")
    private Integer roundsParticipated;
}
