package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.UserSeedDto;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

@Service
public class UserServiceImpl implements UserService {
    private static final String USERS_FILE_PATH = "src/main/resources/files/users.json";
    private final UserRepository userRepository;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public UserServiceImpl(UserRepository userRepository, PictureService pictureService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.userRepository = userRepository;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(USERS_FILE_PATH));
    }

    @Override
    public String importUsers() throws IOException {
        StringBuilder output = new StringBuilder();

        Arrays.stream(gson.fromJson(readFromFileContent(), UserSeedDto[].class))
                .filter(userSeedDto -> {
                    boolean isValid = validationUtil.isValid(userSeedDto)
                            && !isExisting(userSeedDto.getUsername())
                            && pictureService.isExisting(userSeedDto.getProfilePicture());

                    output.append(isValid
                                    ? "Successfully imported User: " + userSeedDto.getUsername()
                                    : "Invalid user")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(userSeedDto -> {
                    User user = modelMapper.map(userSeedDto, User.class);
                    user.setProfilePicture(pictureService.findByPath(userSeedDto.getProfilePicture()));

                    return user;
                })
                .forEach(userRepository::save);

        return output.toString().trim();
    }

    @Override
    public boolean isExisting(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public String exportUsersWithTheirPosts() {
        StringBuilder output = new StringBuilder();

        userRepository
                .findAllByPostsCountDescThenByUserId()
                .forEach(user -> {
                    output
                            .append(String.format("""
                                            User: %s
                                            Post count: %d
                                            """,
                                    user.getUsername(),
                                    user.getPosts().size()));

                    user.getPosts()
                            .stream()
                            .sorted(Comparator.comparingDouble(post -> post.getPicture().getSize()))
                            .forEach(post -> output.append(String.format("""
                                             ==Post Details:
                                             ----Caption: %s
                                             ----Picture Size: %.2f
                                            """,
                                    post.getCaption(),
                                    post.getPicture().getSize()
                            )));
                });

        return output.toString().trim();
    }
}
