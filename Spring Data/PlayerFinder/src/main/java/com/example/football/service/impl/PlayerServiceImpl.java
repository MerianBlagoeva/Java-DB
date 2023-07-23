package com.example.football.service.impl;

import com.example.football.models.dto.PlayerSeedRootDto;
import com.example.football.models.entity.Player;
import com.example.football.repository.PlayerRepository;
import com.example.football.service.PlayerService;
import com.example.football.service.StatService;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final String PLAYERS_FILE_PATH = "src/main/resources/files/xml/players.xml";
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final StatService statService;
    private final TeamService teamService;
    private final TownService townService;

    public PlayerServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, StatService statService, TeamService teamService, TownService townService) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.statService = statService;
        this.teamService = teamService;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        StringBuilder output = new StringBuilder();

        xmlParser.fromFile(PLAYERS_FILE_PATH, PlayerSeedRootDto.class)
                .getPlayers()
                .stream()
                .filter(playerSeedDto -> {
                    boolean isValid = validationUtil.isValid(playerSeedDto)
                            && !isExisting(playerSeedDto.getEmail());

                    output.append(isValid
                                    ? String.format("Successfully imported Player %s %s - %s",
                                    playerSeedDto.getFirstName(), playerSeedDto.getLastName(),
                                    playerSeedDto.getPosition().name())
                                    : "Invalid Player")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(playerSeedDto -> {
                    Player player = modelMapper.map(playerSeedDto, Player.class);
                    player.setStat(statService.findById(playerSeedDto.getStat().getId()));
                    player.setTown(townService.findByName(playerSeedDto.getTown().getName()).orElse(null));
                    player.setTeam(teamService.findByName(playerSeedDto.getTeam().getName()));

                    return player;
                })
                .forEach(playerRepository::save);

        return output.toString().trim();
    }

    private boolean isExisting(String email) {
        return playerRepository.existsByEmail(email);
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder output = new StringBuilder();

        playerRepository
                .findAllByBirthDateBetweenOrdered(LocalDate.of(1995, 1, 1), LocalDate.of(2003, 1, 1))
                .forEach(player -> output
                        .append(String.format("""
                                        Player - %s %s
                                            Position - %s
                                            Team - %s
                                            Stadium - %s
                                        """,
                                player.getFirstName(), player.getLastName(),
                                player.getPosition().name(),
                                player.getTeam().getName(),
                                player.getTeam().getStadiumName())));

        return output.toString().trim();
    }
}
