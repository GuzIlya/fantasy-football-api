package by.guz.fantasy.football.api;

import by.guz.fantasy.football.dto.FixtureDto;
import by.guz.fantasy.football.dto.PlayerStatisticsDto;
import by.guz.fantasy.football.service.FixtureService;
import by.guz.fantasy.football.service.PlayerStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/fixtures")
public class FixtureApiController {

    private final FixtureService fixtureService;
    private final PlayerStatisticsService playerStatisticsService;

    @GetMapping
    public ResponseEntity<List<FixtureDto.Response.Default>> getAllFixtures() {
        List<FixtureDto.Response.Default> fixtures = fixtureService.getAllFixtures();
        return new ResponseEntity<>(fixtures, OK);
    }


    @GetMapping("/{fixtureId}")
    public ResponseEntity<FixtureDto.Response.Default> getFixtureById(@PathVariable Long fixtureId) {
        FixtureDto.Response.Default fixtures = fixtureService.getFixtureById(fixtureId);
        return new ResponseEntity<>(fixtures, OK);
    }


    @GetMapping("/{fixtureId}/players")
    public ResponseEntity<List<PlayerStatisticsDto.Response.Default>> getPlayerStatisticsByFixtureId(@PathVariable Long fixtureId) {
        List<PlayerStatisticsDto.Response.Default> players = playerStatisticsService.getPlayerStatisticsByFixtureId(fixtureId);
        return new ResponseEntity<>(players, OK);
    }
}
