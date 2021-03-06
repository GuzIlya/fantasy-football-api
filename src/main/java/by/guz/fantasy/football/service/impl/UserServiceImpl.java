package by.guz.fantasy.football.service.impl;

import by.guz.fantasy.football.dto.PlayerDto;
import by.guz.fantasy.football.dto.UserDto;
import by.guz.fantasy.football.dto.UserStatisticsDto;
import by.guz.fantasy.football.entity.PlayerEntity;
import by.guz.fantasy.football.entity.UserEntity;
import by.guz.fantasy.football.entity.UserPlayerEntity;
import by.guz.fantasy.football.exception.ConflictException;
import by.guz.fantasy.football.exception.NotFoundException;
import by.guz.fantasy.football.repository.PlayerRepository;
import by.guz.fantasy.football.repository.UserPlayerRepository;
import by.guz.fantasy.football.repository.UserRepository;
import by.guz.fantasy.football.repository.UserStatisticsRepository;
import by.guz.fantasy.football.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static by.guz.fantasy.football.exception.Constants.*;
import static by.guz.fantasy.football.mapper.PlayerMapper.PLAYER_MAPPER;
import static by.guz.fantasy.football.mapper.UserMapper.USER_MAPPER;
import static by.guz.fantasy.football.mapper.UserStatisticsMapper.USER_STATISTICS_MAPPER;
import static by.guz.fantasy.football.util.SecurityUtil.getUserId;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final UserPlayerRepository userPlayerRepository;
    private final UserStatisticsRepository userStatisticsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto.Response.Default> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(USER_MAPPER::toUserDtoDefault)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto.Response.Default getUserById(Long userId) {
        UserEntity existing = userRepository.findOneById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return USER_MAPPER.toUserDtoDefault(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto.Response.Default getCurrentUser() {
        UserEntity user = userRepository.findOneById(getUserId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return USER_MAPPER.toUserDtoDefault(user);
    }

    @Override
    @Transactional
    public PlayerDto.Response.Default purchasePlayerToCurrentUser(Long playerId) {

        UserEntity user = userRepository.findOneById(getUserId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        PlayerEntity player = playerRepository.findOneById(playerId)
                .orElseThrow(() -> new NotFoundException(PLAYER_NOT_FOUND));

        if (user.getCash() < player.getCost())
            throw new ConflictException(NOT_ENOUGH_CASH_CONFLICT);

        user.setCash(user.getCash() - player.getCost());
        userRepository.saveAndFlush(user);

        UserPlayerEntity userPlayer = UserPlayerEntity.builder()
                .user(UserEntity.builder().id(getUserId()).build())
                .player(PlayerEntity.builder().id(playerId).build())
                .purchasePrice(player.getCost())
                .build();
        userPlayerRepository.saveAndFlush(userPlayer);

        return PLAYER_MAPPER.toPlayerDtoDefault(player);
    }

    @Override
    @Transactional
    public void sellPlayerToCurrentUser(Long playerId) {
        UserEntity user = userRepository.findOneById(getUserId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        playerExistsOrException(playerId);

        UserPlayerEntity purchaseDetails = userPlayerRepository.findByUserIdAndPlayerId(getUserId(), playerId)
                .orElseThrow(() -> new NotFoundException(USER_PLAYER_NOT_FOUND));

        user.setCash(user.getCash() + purchaseDetails.getPurchasePrice());
        userRepository.saveAndFlush(user);

        userPlayerRepository.deleteOneByProjectIdAndUserId(playerId, getUserId());
    }

    @Override
    public List<UserStatisticsDto.Response.Default> getAllUserStatistics() {
        return userStatisticsRepository.findAllOrderedByRank()
                .stream()
                .map(USER_STATISTICS_MAPPER::toUserStatisticsDtoDefault)
                .collect(Collectors.toList());
    }

    private void userExistsOrException(final Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
    }

    private void playerExistsOrException(final Long playerId) {
        if (!playerRepository.existsById(playerId)) {
            throw new NotFoundException(PLAYER_NOT_FOUND);
        }
    }
}
